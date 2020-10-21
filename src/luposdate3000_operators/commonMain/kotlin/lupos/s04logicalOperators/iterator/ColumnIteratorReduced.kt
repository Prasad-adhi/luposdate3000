package lupos.s04logicalOperators.iterator

import kotlin.jvm.JvmField
import lupos.s03resultRepresentation.ResultSetDictionaryExt

class ColumnIteratorReduced(@JvmField val child: ColumnIterator) : ColumnIterator() {
    @JvmField
    var last: Int = ResultSetDictionaryExt.nullValue

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

    override suspend fun next(): Int {
        if (label == 1) {
            var res = child.next()
            while (res != ResultSetDictionaryExt.nullValue && last == res) {
                res = child.next()
            }
            last = res
            if (res == ResultSetDictionaryExt.nullValue) {
                _close()
            }
            return res
        } else {
            return ResultSetDictionaryExt.nullValue
        }
    }
}
