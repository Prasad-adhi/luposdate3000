package lupos.s04logicalOperators.iterator

import lupos.s00misc.Coverage
import lupos.s03resultRepresentation.Value
import lupos.s04logicalOperators.iterator.ColumnIteratorNext

class ColumnIteratorChildIterator() : ColumnIterator() {
    val childs = mutableListOf(ColumnIterator())
    var onNoMoreElements: () -> Unit = ::_onNoMoreElements

    init {
        next = object : ColumnIteratorNext("ColumnIteratorChildIterator.next") {
            override fun invoke(): Value? {
                var res: Value? = null
                while (childs.size > 0) {
                    res = childs[0].next()
                    if (res == null) {
                        childs.removeAt(0)
                    } else {
                        break
                    }
                }
                if (res == null) {
                    onNoMoreElements()
                    if (childs.size == 0) {
                        close()
                    }
                    res = next()
                }
                return res
            }
        }
        close = {
            onNoMoreElements = ::_onNoMoreElements
            for (child in childs) {
                child.close()
            }
            _close()
        }
    }

    fun _onNoMoreElements() {
        close()
    }
}
