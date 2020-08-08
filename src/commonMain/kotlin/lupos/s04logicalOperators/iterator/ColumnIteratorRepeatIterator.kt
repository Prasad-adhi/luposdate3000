package lupos.s04logicalOperators.iterator

import lupos.s03resultRepresentation.MyListValue
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s03resultRepresentation.Value

class ColumnIteratorRepeatIterator(@JvmField val count: Int, @JvmField val child: ColumnIterator) : ColumnIterator() {
    @JvmField
    var index = 0

    @JvmField
    var index2 = 0

    //TODO use pages instead
    @JvmField
    val data = MyListValue()

    @JvmField
    var label = 1
    inline suspend fun _close() {
        if (label != 0) {
            label = 0
            child.close()
        }
    }

    override suspend fun close() {
        _close()
    }

    override suspend fun next(): Value {
        when (label) {
            1 -> {
                val tmp = child.next()
                if (tmp == ResultSetDictionary.nullValue) {
                    child.close()
                    if (data.size == 0 || count == 1) {
                        label = 0
                        return ResultSetDictionary.nullValue
                    } else {
                        index = 2
                        label = 2
                        return data[index2++]
                    }
                } else {
                    data.add(tmp)
                    return tmp
                }
            }
            2 -> {
                if (index2 < data.size) {
                    return data[index2++]
                } else if (index < count) {
                    index++
                    index2 = 0
                    return data[index2++]
                } else {
                    label = 0
                    return ResultSetDictionary.nullValue
                }
            }
            else -> {
                return ResultSetDictionary.nullValue
            }
        }
    }
}
