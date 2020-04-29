package lupos.s05tripleStore

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

class TripleStoreIndex_MapMapList : TripleStoreIndex {
    @JvmField
    val data = MyMapIntGeneric<MyMapIntGeneric<MySetInt>>()

    override fun safeToFolder(filename: String) {
        File(filename).dataOutputStream { out ->
            out.writeInt(data.values.size)
            val iterator0 = data.iterator()
            while (iterator0.hasNext()) {
                out.writeInt(iterator0.next())
                val value0 = iterator0.value()
                out.writeInt(value0.values.size)
                val iterator1 = value0.iterator()
                while (iterator1.hasNext()) {
                    out.writeInt(iterator1.next())
                    val value1 = iterator1.value()
                    out.writeInt(value1.size)
                    value1.forEach {
                        out.writeInt(it)
                    }
                }
            }
        }
    }

    override fun loadFromFolder(filename: String) {
        File(filename).dataInputStream { it ->
            val size0 = it.readInt()
            for (i0 in 0 until size0) {
                val key0 = it.readInt()
                val tmp0 = data.appendAssumeSorted(key0, MyMapIntGeneric<MySetInt>())
                val size1 = it.readInt()
                for (i1 in 0 until size1) {
                    val key1 = it.readInt()
                    val tmp1 = tmp0.appendAssumeSorted(key1, MySetInt())
                    val size2 = it.readInt()
                    for (i2 in 0 until size2) {
                        val key2 = it.readInt()
                        tmp1.appendAssumeSorted(key2)
                    }
                }
            }
        }
    }

    override fun getIterator(query: Query, filter: MyListValue, projection: Array<String>): ColumnIteratorRow {
        require(filter.size >= 0 && filter.size <= 3)
        require(projection.size + filter.size == 3)
        val columns = mutableMapOf<String, ColumnIterator>()
        for (sIndex in 0 until projection.size) {
            val s = projection[sIndex]
            if (s != "_") {
                columns[s] = ColumnIterator()
            }
        }
        var res = ColumnIteratorRow(columns)
        if (filter.size > 0) {
            val tmp = data[filter[0]]
            if (tmp != null) {
                if (filter.size > 1) {
                    val tmp1 = tmp[filter[1]]
                    if (tmp1 != null) {
                        if (filter.size > 2) {
                            if (tmp1.contains(filter[2])) {
                                res.count = 1
                            } else {
                                res.count = 0
                            }
                        } else {
                            if (projection[0] == "_") {
                                res.count = tmp1.size
                            } else {
                                columns[projection[0]] = ColumnIteratorDebug(-1, projection[0], ColumnIteratorMultiValue(tmp1.iterator()))
                            }
                        }
                    }
                } else {
                    val columnsArr = arrayOf(ColumnIteratorChildIterator(), ColumnIteratorChildIterator())
                    if (projection[0] != "_") {
                        columns[projection[0]] = ColumnIteratorDebug(-2, projection[0], columnsArr[0])
                    }
                    if (projection[1] != "_") {
                        columns[projection[1]] = ColumnIteratorDebug(-3, projection[1], columnsArr[1])
                    }
                    var iter = tmp.iterator()
                    for (iterator in columnsArr) {
                        iterator.onNoMoreElements = {
                            if (iter.hasNext()) {
                                val key = iter.next()
                                val value = iter.value()
                                if (projection[0] != "_") {
                                    columnsArr[0].childs.add(ColumnIteratorRepeatValue(value.size, key))
                                }
                                if (projection[1] != "_") {
                                    columnsArr[1].childs.add(ColumnIteratorMultiValue(value.iterator()))
                                }
                            }
                        }
                    }
                }
            }
        } else {
            val columnsArr = arrayOf(ColumnIteratorChildIterator(), ColumnIteratorChildIterator(), ColumnIteratorChildIterator())
            if (projection[0] != "_") {
                columns[projection[0]] = ColumnIteratorDebug(-4, projection[0], columnsArr[0])
            }
            if (projection[1] != "_") {
                columns[projection[1]] = ColumnIteratorDebug(-5, projection[1], columnsArr[1])
            }
            if (projection[2] != "_") {
                columns[projection[2]] = ColumnIteratorDebug(-6, projection[2], columnsArr[2])
            }
            var iter = data.iterator()
            if (iter.hasNext()) {
                var key1 = iter.next()
                var value1 = iter.value()
                var iter2 = value1.iterator()
                for (iterator in columnsArr) {
                    iterator.onNoMoreElements = {
                        while (true) {
                            if (iter2.hasNext()) {
                                val key2 = iter2.next()
                                val value2 = iter2.value()
                                if (projection[0] != "_") {
                                    columnsArr[0].childs.add(ColumnIteratorRepeatValue(value2.size, key1))
                                }
                                if (projection[1] != "_") {
                                    columnsArr[1].childs.add(ColumnIteratorRepeatValue(value2.size, key2))
                                }
                                if (projection[2] != "_") {
                                    columnsArr[2].childs.add(ColumnIteratorMultiValue(value2.iterator()))
                                }
                                break
                            } else {
                                if (iter.hasNext()) {
                                    key1 = iter.next()
                                    value1 = iter.value()
                                    iter2 = value1.iterator()
                                } else {
                                    break
                                }
                            }
                        }
                    }
                }
            }
        }
        return res
    }

    override fun import(dataImport: MyMapLongGeneric<MySetInt>) {
        val iterator = dataImport.iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            val value = iterator.value()
            val s = (key shr 32).toInt()
            val p = key.toInt()
            val tmpS = data.getOrCreate(s, { MyMapIntGeneric<MySetInt>() })
            val tmpP = tmpS.getOrCreate(p, { MySetInt() })
            val oiterator = value.iterator()
            while (oiterator.hasNext()) {
                tmpP.add(oiterator.next())
            }
        }
    }

    override fun insert(a: Value, b: Value, c: Value) {
        val tmp = data[a]
        if (tmp == null) {
            data[a] = MyMapIntGeneric(b to MySetInt(c))
        } else {
            val tmp2 = tmp[b]
            if (tmp2 == null) {
                tmp[b] = MySetInt(c)
            } else {
                tmp2.add(c)
            }
        }
    }

    override fun remove(a: Value, b: Value, c: Value) {
        val tmp = data[a]
        if (tmp != null) {
            val tmp2 = tmp[b]
            if (tmp2 != null) {
                tmp2.remove(c)
            }
        }
    }

    override fun clear() {
        data.clear()
    }
}
