package lupos.s11outputResult

import java.io.PrintWriter
import java.io.StringWriter
import kotlinx.coroutines.async
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import lupos.s00misc.DateHelper
import lupos.s00misc.Lock
import lupos.s00misc.MyMapIntInt
import lupos.s00misc.Partition
import lupos.s00misc.SanityCheck
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s03resultRepresentation.Value
import lupos.s03resultRepresentation.ValueBnode
import lupos.s03resultRepresentation.ValueBoolean
import lupos.s03resultRepresentation.ValueDateTime
import lupos.s03resultRepresentation.ValueDecimal
import lupos.s03resultRepresentation.ValueDouble
import lupos.s03resultRepresentation.ValueError
import lupos.s03resultRepresentation.ValueFloat
import lupos.s03resultRepresentation.ValueInteger
import lupos.s03resultRepresentation.ValueIri
import lupos.s03resultRepresentation.ValueLanguageTaggedLiteral
import lupos.s03resultRepresentation.ValueSimpleLiteral
import lupos.s03resultRepresentation.ValueTypedLiteral
import lupos.s03resultRepresentation.ValueUndef
import lupos.s03resultRepresentation.Variable
import lupos.s04logicalOperators.iterator.ColumnIterator
import lupos.s04logicalOperators.noinput.OPNothing
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.OPBaseCompound
import lupos.s04logicalOperators.Query
import lupos.s09physicalOperators.partition.POPMergePartition

object QueryResultToEmptyStream {

    inline suspend fun writeAllRows(variables: Array<String>, columns: Array<ColumnIterator>, dictionary: ResultSetDictionary, lock: Lock?, output: PrintWriter) {
        val rowBuf = IntArray(variables.size)
        loop@ while (true) {
            for (variableIndex in 0 until variables.size) {
                val valueID = columns[variableIndex].next()
                if (valueID == ResultSetDictionary.nullValue) {
                    break@loop
                }
                rowBuf[variableIndex] = valueID
            }
        }
        for (closeIndex in 0 until columns.size) {
            columns[closeIndex]!!.close()
        }
    }

    suspend fun writeNodeResult(variables: Array<String>, node: OPBase, output: PrintWriter, parent: Partition = Partition()) {
        if (Partition.k > 1 && node is POPMergePartition) {
            val jobs = Array<Deferred<Int>?>(Partition.k) { null }
            val lock = Lock()
            for (p in 0 until Partition.k) {
                jobs[p] = GlobalScope.async<Int> {
                    val child = node.children[0].evaluate(Partition(parent, node.partitionVariable, p, GlobalScope))
                    val columns = variables.map { child.columns[it]!! }.toTypedArray()
                    writeAllRows(variables, columns, node.query.dictionary, lock, output)
                    1
                }
            }
            for (p in 0 until Partition.k) {
                jobs[p]!!.await()
            }
        } else {
            val child = node.evaluate(parent)
            val columns = variables.map { child.columns[it]!! }.toTypedArray()
            writeAllRows(variables, columns, node.query.dictionary, null, output)
        }
    }

    suspend operator fun invoke(rootNode: OPBase, output: PrintWriter) {
        val nodes: Array<OPBase>
        var columnProjectionOrder = listOf<List<String>>()
        if (rootNode is OPBaseCompound) {
            nodes = Array(rootNode.children.size) { rootNode.children[it] }
            columnProjectionOrder = rootNode.columnProjectionOrder
        } else {
            nodes = arrayOf<OPBase>(rootNode)
        }
        for (i in 0 until nodes.size) {
            val node = nodes[i]
            if (node !is OPNothing) {
                val columnNames: List<String>
                if (columnProjectionOrder[i].size > 0) {
                    columnNames = columnProjectionOrder[i]
                    SanityCheck.check { columnNames.containsAll(node.getProvidedVariableNames()) }
                } else {
                    columnNames = node.getProvidedVariableNames()
                }
                val variables = columnNames.toTypedArray()
                if (variables.size == 1 && variables[0] == "?boolean") {
                    val child = node.evaluate(Partition())
		    child.columns["?boolean"]!!.next()
                } else {
                    val bnodeMap = MyMapIntInt()
                    var bnodeMapSize = 0
                    if (variables.size == 0) {
                        val child = node.evaluate(Partition())
                        child.count()
                    } else {
                        writeNodeResult(variables, node, output)
                    }
                }
            }
        }
    }
}
