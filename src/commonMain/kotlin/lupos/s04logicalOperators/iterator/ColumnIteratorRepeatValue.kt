package lupos.s04logicalOperators.iterator

import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s03resultRepresentation.Value

class ColumnIteratorRepeatValue(@JvmField val count: Int, @JvmField val value: Value) : ColumnIterator() {
    @JvmField
    var index = 0
    override suspend fun close() {
        index = count
    }

    override suspend fun next(): Value {
        if (index == count) {
            return ResultSetDictionary.nullValue
        } else {
            index++
            return value
        }
    }
}
