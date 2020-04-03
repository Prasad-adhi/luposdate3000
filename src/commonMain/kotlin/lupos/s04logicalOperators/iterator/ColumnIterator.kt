package lupos.s04logicalOperators.iterator

import lupos.s00misc.Coverage
import lupos.s03resultRepresentation.*

open class ColumnIterator() {
    var next: suspend () -> Value? = ::_next
    var close: () -> Unit = ::_close
    fun _close() {
        next = ::_next
        close = ::_close
    }

    suspend fun _next(): Value? = null
}
