package lupos.s05tripleStore

import lupos.s05tripleStore.index_IDTriple.*
import kotlin.jvm.JvmField
import lupos.s00misc.*
import lupos.s00misc.CoroutinesHelper
import lupos.s00misc.Coverage
import lupos.s00misc.EIndexPattern
import lupos.s00misc.ELoggerType
import lupos.s00misc.EModifyType
import lupos.s00misc.GlobalLogger
import lupos.s00misc.SanityCheck
import lupos.s00misc.ThreadSafeMutableList
import lupos.s00misc.ThreadSafeMutableSet
import lupos.s03resultRepresentation.*
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s03resultRepresentation.Value
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04logicalOperators.iterator.*
import lupos.s04logicalOperators.Query

class TripleStoreIndex_IDTriple : TripleStoreIndex {
    var firstLeaf = NodeManager.NodeNullPointer
    override fun safeToFolder(filename: String) {
        throw Exception("not implemented")
    }

    override fun loadFromFolder(filename: String) {
        throw Exception("not implemented")
    }

    class IteratorS(it: TripleIterator) : ColumnIterator() {
        init {
            next = {
                var tmp: Value? = null
                if (it.hasNext()) {
                    tmp = it.nextS()
                }
                /*return*/tmp
            }
        }
    }

    class IteratorP(it: TripleIterator) : ColumnIterator() {
        init {
            next = {
                var tmp: Value? = null
                if (it.hasNext()) {
                    tmp = it.nextP()
                }
                /*return*/tmp
            }
        }
    }

    class IteratorO(it: TripleIterator) : ColumnIterator() {
        init {
            next = {
                var tmp: Value? = null
                if (it.hasNext()) {
                    tmp = it.nextO()
                }
                /*return*/tmp
            }
        }
    }

    override fun getIterator(query: Query, filter: List<Value>, projection: List<String>): ColumnIteratorRow {
        val columns = mutableMapOf<String, ColumnIterator>()
        for (s in projection) {
            if (s != "_") {
                columns[s] = ColumnIterator()
            }
        }
        var res = ColumnIteratorRow(columns)
        if (filter.size == 0) {
            if (firstLeaf != NodeManager.NodeNullPointer) {
                val node = (NodeManager.getNode(firstLeaf) as NodeLeaf)
                SanityCheck.check { filter.size == 0 }
                if (projection[0] != "_") {
                    columns[projection[0]] = ColumnIteratorDebug(0, projection[0], IteratorS(node.iterator()))
                    if (projection[1] != "_") {
                        columns[projection[1]] = ColumnIteratorDebug(0, projection[1], IteratorP(node.iterator()))
                        if (projection[2] != "_") {
                            columns[projection[2]] = ColumnIteratorDebug(0, projection[2], IteratorO(node.iterator()))
                        }
                    } else {
                        SanityCheck.check { projection[2] == "_" }
                    }
                } else {
                    SanityCheck.check { projection[1] == "_" }
                    SanityCheck.check { projection[2] == "_" }
                }
            }
            return res
        }
        throw Exception("not implemented")
    }

    override fun import(dataImport: IntArray, count: Int, order: IntArray) {
        val iteratorImport = BulkImportIterator(dataImport, count, order)
        var iteratorStore: TripleIterator
        if (firstLeaf == NodeManager.NodeNullPointer) {
            iteratorStore = EmptyIterator()
        } else {
            iteratorStore = (NodeManager.getNode(firstLeaf) as NodeLeaf).iterator()
        }
        val iterator = MergeIterator(iteratorImport, iteratorStore)
        if (iterator.hasNext()) {
            var newFirstLeaf = NodeManager.NodeNullPointer
            var node2: NodeLeaf? = null
            NodeManager.allocateNodeLeaf { n, i ->
                newFirstLeaf = i
                node2 = n
            }
            var node = node2!!
            node.initializeWith(iterator)
            while (iterator.hasNext()) {
                NodeManager.allocateNodeLeaf { n, i ->
                    node.setNextNode(i)
                    node = n
                }
                node.initializeWith(iterator)
            }
            NodeManager.freeNodeAndAllRelated(firstLeaf)
            firstLeaf = newFirstLeaf
        }
    }

    override fun insert(a: Value, b: Value, c: Value) {
        throw Exception("not implemented")
    }

    override fun remove(a: Value, b: Value, c: Value) {
        throw Exception("not implemented")
    }

    override fun clear() {
        NodeManager.freeNodeAndAllRelated(firstLeaf)
        firstLeaf = NodeManager.NodeNullPointer
    }

    override fun printContents() {
    }
}
