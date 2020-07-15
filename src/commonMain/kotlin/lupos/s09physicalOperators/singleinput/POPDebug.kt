package lupos.s09physicalOperators.singleinput

import kotlin.jvm.JvmField
import lupos.s00misc.Coverage
import lupos.s00misc.EOperatorID
import lupos.s00misc.ESortPriority
import lupos.s00misc.Partition
import lupos.s00misc.SanityCheck
import lupos.s00misc.XMLElement
import lupos.s03resultRepresentation.Value
import lupos.s03resultRepresentation.ValueComparatorASC
import lupos.s03resultRepresentation.ValueComparatorDESC
import lupos.s03resultRepresentation.Variable
import lupos.s04arithmetikOperators.noinput.AOPVariable
import lupos.s04logicalOperators.iterator.ColumnIterator
import lupos.s04logicalOperators.iterator.ColumnIteratorMerge
import lupos.s04logicalOperators.iterator.IteratorBundle
import lupos.s04logicalOperators.iterator.RowIterator
import lupos.s04logicalOperators.iterator.RowIteratorMerge
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s09physicalOperators.POPBase

enum class EPOPDebugMode {
    NONE,
    DEBUG1,
    DEBUG2
}

class POPDebug(query: Query, projectedVariables: List<String>, child: OPBase) : POPBase(query, projectedVariables, EOperatorID.POPDebugID, "POPDebug", arrayOf(child), ESortPriority.SAME_AS_CHILD) {
    override fun equals(other: Any?): Boolean = other is POPDebug && children[0] == other.children[0]
    override fun cloneOP() = POPDebug(query, projectedVariables, children[0].cloneOP())
    override fun getRequiredVariableNames(): List<String> = listOf<String>()
    override fun getProvidedVariableNames(): List<String> = children[0].getProvidedVariableNames()
    override fun getProvidedVariableNamesInternal(): List<String> = (children[0] as POPBase).getProvidedVariableNamesInternal()
    override fun toSparql(): String = children[0].toSparql()
    override suspend fun evaluate(parent: Partition): IteratorBundle {
        val child = children[0].evaluate(parent)
        when (ITERATOR_DEBUG_MODE) {
            EPOPDebugMode.NONE -> {
                return child
            }
            EPOPDebugMode.DEBUG1 -> {
                val target = children[0].getProvidedVariableNames()
                SanityCheck.println({ "POPDebug-child-mode ... ${uuid} ${children[0].uuid} ${child.mode}" })
                if (child.hasColumnMode()) {
                    val columnMode = mutableListOf<String>()
                    for ((k, v) in child.columns) {
                        columnMode.add(k)
                    }
                    SanityCheck { columnMode.containsAll(target) }
                    SanityCheck { target.containsAll(columnMode) }
                } else if (child.hasRowMode()) {
                    val rowMode = child.rows.columns.toMutableList()
                    SanityCheck { rowMode.containsAll(target) }
                    SanityCheck { target.containsAll(rowMode) }
                }
                return child
            }
            EPOPDebugMode.DEBUG2 -> {
                val target = children[0].getProvidedVariableNames()
                SanityCheck.println({ "POPDebug-child-mode ... ${uuid} ${children[0].uuid} ${child.mode}" })
                if (child.hasColumnMode()) {
                    val outMap = mutableMapOf<String, ColumnIterator>()
                    val columnMode = mutableListOf<String>()
                    for ((k, v) in child.columns) {
                        columnMode.add(k)
                        val iterator = ColumnIterator()
                        var counter = 0
                        SanityCheck.println({ "$uuid $k opened" })
                        iterator.next = {
                            SanityCheck.println({ "$uuid $k next call" })
                            val res = v.next()
                            if (res == null) {
                                SanityCheck.println({ "$uuid $k next return closed $counter ${parent.data} null" })
                            } else {
                                counter++
                                SanityCheck.println({ "$uuid $k next return $counter ${parent.data} ${res.toString(16)}" })
                            }
                            /*return*/ res
                        }
                        iterator.close = {
                            SanityCheck.println({ "$uuid $k closed $counter ${parent.data}" })
                            v.close()
                            iterator._close()
                        }
                        outMap[k] = iterator
                    }
                    SanityCheck { columnMode.containsAll(target) }
                    SanityCheck { target.containsAll(columnMode) }
                    return IteratorBundle(outMap)
                } else if (child.hasRowMode()) {
                    val rowMode = child.rows.columns.toMutableList()
                    SanityCheck { rowMode.containsAll(target) }
                    SanityCheck { target.containsAll(rowMode) }
                    val iterator = RowIterator()
                    var counter = 0
                    iterator.columns = child.rows.columns
                    iterator.next = {
                        SanityCheck.println({ "$uuid next call" })
                        val res = child.rows.next()
                        iterator.buf = child.rows.buf
                        if (res < 0) {
                            SanityCheck.println({ "$uuid next return closed $counter ${parent.data} null" })
                        } else {
                            counter++
                            SanityCheck.println({ "$uuid next return $counter ${parent.data} ${iterator.buf.map { it.toString(16) }}" })
                        }
                        /*return*/ res
                    }
                    iterator.close = {
                        SanityCheck.println({ "$uuid closed $counter ${parent.data}" })
                        child.rows.close()
                        iterator._close()
                    }
                    return IteratorBundle(iterator)
                } else {
                    return child
                }
            }
        }
    }
}
