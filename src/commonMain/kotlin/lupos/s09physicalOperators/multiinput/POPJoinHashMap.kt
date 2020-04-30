package lupos.s09physicalOperators.multiinput

import kotlin.jvm.JvmField
import kotlinx.coroutines.channels.Channel
import lupos.s00misc.*
import lupos.s00misc.CoroutinesHelper
import lupos.s00misc.Coverage
import lupos.s00misc.EOperatorID
import lupos.s00misc.XMLElement
import lupos.s01io.*
import lupos.s03resultRepresentation.*
import lupos.s03resultRepresentation.Variable
import lupos.s04logicalOperators.*
import lupos.s04logicalOperators.iterator.*
import lupos.s09physicalOperators.POPBase

class POPJoinHashMap(query: Query, projectedVariables: List<String>, childA: OPBase, childB: OPBase, @JvmField val optional: Boolean) : POPBase(query, projectedVariables, EOperatorID.POPJoinHashMapID, "POPJoinHashMap", arrayOf(childA, childB), ESortPriority.JOIN) {
    override fun toSparql(): String {
        if (optional) {
            return "OPTIONAL{" + children[0].toSparql() + children[1].toSparql() + "}"
        }
        return children[0].toSparql() + children[1].toSparql()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is POPJoinHashMap) {
            return false
        }
        if (optional != other.optional) {
            return false
        }
        for (i in children.indices) {
            if (!children[i].equals(other.children[i])) {
                return false
            }
        }
        return true
    }

    class MapKey(@JvmField val data: Array<Value>) {
        override fun hashCode(): Int {
            var res = 0
            for (i in 0 until data.size) {
                res += data[i].hashCode()
            }
            return res
        }

        override fun equals(other: Any?): Boolean {
            SanityCheck.check{other is MapKey}
            for (i in 0 until data.size) {
                if (data[i] != (other as MapKey).data[i]) {
                    return false
                }
            }
            return true
        }

        fun equalsFuzzy(other: Any?): Boolean {
            SanityCheck.check{other is MapKey}
            for (i in 0 until data.size) {
                if (data[i] != ResultSetDictionary.undefValue && (other as MapKey).data[i] != ResultSetDictionary.undefValue && data[i] != (other as MapKey).data[i]) {
                    return false
                }
            }
            return true
        }
    }

    class MapRow(columns: Int) {
        val columns = Array(columns) { MyListValue() }
        var count = 0
    }

    override suspend fun evaluate(): ColumnIteratorRow {
//--- obtain child columns
        val childA = children[0].evaluate()
        val childB = children[1].evaluate()
        val columnsINAO = mutableListOf<ColumnIterator>()//only in childA
        val columnsINBO = mutableListOf<ColumnIterator>()//only in childB
        val columnsINAJ = mutableListOf<ColumnIterator>()//join columnA
        val columnsINBJ = mutableListOf<ColumnIterator>()//join columnB
        val outO = Array(2) { mutableListOf<ColumnIteratorChildIterator>() }//only in one of the childs
        val outJ = mutableListOf<ColumnIteratorChildIterator>()
        val outIterators = mutableListOf<ColumnIteratorChildIterator>()
        val outMap = mutableMapOf<String, ColumnIterator>()
        var res: ColumnIteratorRow?
        val tmp = mutableListOf<String>()
        var t: ColumnIteratorChildIterator
        tmp.addAll(children[1].getProvidedVariableNames())
        for (name in children[0].getProvidedVariableNames()) {
            if (tmp.contains(name)) {
                columnsINAJ.add(0, childA.columns[name]!!)
                columnsINBJ.add(0, childB.columns[name]!!)
                t = ColumnIteratorChildIterator()
                if (projectedVariables.contains(name)) {
                    outMap[name] = ColumnIteratorDebug(uuid, name, t)
                    outIterators.add(0, t)
                    outJ.add(0, t)
                }
                tmp.remove(name)
            } else {
                t = ColumnIteratorChildIterator()
                outIterators.add(t)
                outMap[name] = ColumnIteratorDebug(uuid, name, t)
                outO[0].add(t)
                columnsINAO.add(childA.columns[name]!!)
            }
        }
        for (name in tmp) {
            t = ColumnIteratorChildIterator()
            outIterators.add(t)
            outMap[name] = ColumnIteratorDebug(uuid, name, t)
            outO[1].add(t)
            columnsINBO.add(childB.columns[name]!!)
        }
        var emptyColumnsWithJoin = outO[0].size == 0 && outO[1].size == 0 && outJ.size == 0 && columnsINAJ.size != 0
        if (emptyColumnsWithJoin) {
            t = ColumnIteratorChildIterator()
            outJ.add(t)
            outIterators.add(t)
        }
        val mapWithoutUndef = mutableMapOf<MapKey, MapRow>()
        val mapWithUndef = mutableMapOf<MapKey, MapRow>()
        var currentKey: Array<Value>? = null
        var nextKey: Array<Value>?
        var map: MutableMap<MapKey, MapRow> = mapWithUndef
        var nextMap: MutableMap<MapKey, MapRow>
        var key: MapKey
        var oldArr: MapRow?
        var count: Int
        var countA: Int
        var countB: Int
//---check for_ empty columns
        if (columnsINAJ.size == 0) {
            if (columnsINAO.size == 0 && columnsINBO.size == 0) {
                res = ColumnIteratorRow(outMap)
                res.count = childA.count * childB.count
            } else {
                if (columnsINAO.size == 0) {
//---no columns from a
                    if (childA.count > 0) {
                        for (columnIndex in 0 until columnsINBO.size) {
                            outO[1][columnIndex].childs.add(ColumnIteratorRepeatIterator(childA.count, columnsINBO[columnIndex]))
                        }
                    }
                } else if (columnsINBO.size == 0) {
//---no columns from b
                    if (childB.count > 0) {
                        for (columnIndex in 0 until columnsINAO.size) {
                            outO[0][columnIndex].childs.add(ColumnIteratorRepeatIterator(childB.count, columnsINAO[columnIndex]))
                        }
                    }
                } else {
//---cartesian product
//---insert second child into simple list
                    val data = Array(columnsINBO.size) { MyListValue() }
                    loopC@ while (true) {
                        for (columnIndex in 0 until columnsINBO.size) {
                            val value = columnsINBO[columnIndex].next()
                            if (value == null) {
                                break@loopC
                            }
                            data[columnIndex].add(value)
                        }
                    }
                    count = data[0].size
                    if (count > 0 || optional) {
                        for (iterator in outIterators) {
//this is just function pointer assignment. this loop does not calculate anything
                            iterator.close = {
                                iterator._close()
                                for (variable in children[0].getProvidedVariableNames()) {
                                    childA.columns[variable]!!.close()
                                }
                                for (variable in children[1].getProvidedVariableNames()) {
                                    childB.columns[variable]!!.close()
                                }
                            }
                            iterator.onNoMoreElements = {
                                var done = false
                                for (columnIndex in 0 until columnsINAO.size) {
                                    val value = columnsINAO[columnIndex].next()
                                    if (value == null) {
                                        SanityCheck.check{columnIndex == 0}
                                        done = true
                                        break
                                    }
                                    outO[0][columnIndex].childs.add(ColumnIteratorRepeatValue(count, value))
                                }
                                if (!done) {
                                    for (columnIndex in 0 until columnsINBO.size) {
                                        outO[1][columnIndex].childs.add(ColumnIteratorMultiValue(data[columnIndex]))
                                    }
                                }
                            }
                        }
                    }
                }
                res = ColumnIteratorRow(outMap)
            }
        } else {
            //---join on at least one column
//--- insert second child into hash table
            while (true) {
                if (currentKey != null) {
                    count = 1
                } else {
                    count = 0
                }
                loopB@ while (true) {
                    nextKey = Array(columnsINBJ.size) { ResultSetDictionary.undefValue }
                    nextMap = mapWithoutUndef
                    for (columnIndex in 0 until columnsINBJ.size) {
                        val value = columnsINBJ[columnIndex].next()
                        if (value == null) {
                            nextKey = null
                            break@loopB
                        }
                        nextKey!![columnIndex] = value
                        if (value == ResultSetDictionary.undefValue) {
                            nextMap = mapWithUndef
                        }
                    }
                    if (currentKey == null) {
                        currentKey = nextKey
                        map = nextMap
                    } else if (nextKey == null || MapKey(nextKey) != MapKey(currentKey)) {
                        break
                    }
                    count++
                }
                if (currentKey == null) {
                    break
                }
                key = MapKey(currentKey)
                oldArr = map[key]
                if (oldArr == null) {
                    oldArr = MapRow(columnsINBO.size)
                    map[key] = oldArr
                }
                oldArr.count += count
                for (columnIndex in 0 until columnsINBO.size) {
//TODO dont use kotlin lists here, use pages instead
                    for (j in 0 until count) {
                        oldArr.columns[columnIndex].add(columnsINBO[columnIndex].next()!!)
                    }
                }
                currentKey = nextKey
                map = nextMap
            }
//--- iterate first child
//--- calculate next "cartesian product"
            for (iterator in outIterators) {
//this is just function pointer assignment. this loop does not calculate anything
                iterator.close = {
                    iterator._close()
                    for (variable in children[0].getProvidedVariableNames()) {
                        childA.columns[variable]!!.close()
                    }
                    for (variable in children[1].getProvidedVariableNames()) {
                        childB.columns[variable]!!.close()
                    }
                }
                iterator.onNoMoreElements = {
                    var done = false
                    while (!done) {
                        if (currentKey == null) {
                            countA = 0
                        } else {
                            countA = 1
                        }
                        loopA@ while (true) {
                            nextKey = Array(columnsINAJ.size) { ResultSetDictionary.undefValue }
                            nextMap = mapWithoutUndef
                            for (columnIndex in 0 until columnsINAJ.size) {
                                val value = columnsINAJ[columnIndex].next()
                                if (value == null) {
                                    SanityCheck.check{columnIndex == 0}
                                    nextKey = null
                                    break@loopA
                                }
                                nextKey!![columnIndex] = value
                                if (value == ResultSetDictionary.undefValue) {
                                    nextMap = mapWithUndef
                                }
                            }
                            if (currentKey == null) {
                                map = nextMap
                                currentKey = nextKey
                            } else if (nextKey == null || MapKey(nextKey!!) != MapKey(currentKey!!)) {
                                break
                            }
                            countA++
                        }
                        if (currentKey == null) {
                            done = true
                            iterator.close()
                        } else {
                            key = MapKey(currentKey!!)
                            var others = mutableListOf<Pair<MapKey, MapRow>>()
//search for_join-partners
                            if (map == mapWithoutUndef) {
                                val tmp2 = mapWithoutUndef[key]
                                if (tmp2 != null) {
                                    others.add(Pair(key, tmp2))
                                }
                                for ((k, v) in mapWithUndef) {
                                    if (k.equalsFuzzy(key)) {
                                        others.add(Pair(k, v))
                                    }
                                }
                            } else {
                                for ((k, v) in mapWithoutUndef) {
                                    if (k.equalsFuzzy(key)) {
                                        others.add(Pair(k, v))
                                    }
                                }
                                for ((k, v) in mapWithUndef) {
                                    if (k.equalsFuzzy(key)) {
                                        others.add(Pair(k, v))
                                    }
                                }
                            }
                            val dataOA = Array(columnsINAO.size) { MyListValue() }
                            for (columnIndex in 0 until columnsINAO.size) {
                                for (i in 0 until countA) {
                                    dataOA[columnIndex].add(columnsINAO[columnIndex].next()!!)
                                }
                            }
                            if (others.size == 0) {
                                if (optional) {
//optional clause without match
                                    done = true
                                    countB = 1
                                    val dataJ: Array<Value?> = Array(outJ.size) { currentKey!![it] }
                                    val dataO: Array<Array<MyListValue>> = arrayOf(dataOA, Array(outO[1].size) { MyListValue(ResultSetDictionary.undefValue) })
                                    POPJoin.crossProduct(dataO, dataJ, outO, outJ, countA, countB)
                                }
                            } else {
                                done = true
                                for (otherIndex in 0 until others.size) {
                                    countB = others[otherIndex].second.count
                                    val dataJ: Array<Value?> = Array(outJ.size) {
                                        var res2: Value?
                                        if (currentKey!![it] != ResultSetDictionary.undefValue) {
                                            res2 = currentKey!![it]
                                        } else {
                                            res2 = others[otherIndex].first.data[it]
                                        }
/*return*/res2
                                    }
                                    val dataO: Array<Array<MyListValue>> = arrayOf(dataOA, others[otherIndex].second.columns)
                                    POPJoin.crossProduct(dataO, dataJ, outO, outJ, countA, countB)
                                }
                            }
                            map = nextMap
                            currentKey = nextKey
                        }
                    }
                }
            }
            res = ColumnIteratorRow(outMap)
            if (emptyColumnsWithJoin) {
                res.hasNext = {
                    /*return*/outJ[0].next() != null
                }
            }
        }
        return res
    }

    override fun toXMLElement() = super.toXMLElement().addAttribute("optional", "" + optional)
    override fun cloneOP() = POPJoinHashMap(query, projectedVariables, children[0].cloneOP(), children[1].cloneOP(), optional)
}
