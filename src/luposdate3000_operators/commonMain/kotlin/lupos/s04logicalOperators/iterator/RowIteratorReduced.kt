package lupos.s04logicalOperators.iterator

open class RowIteratorReduced(val child: RowIterator) : RowIterator() {
    var first = true

    init {
        columns = child.columns
        buf = IntArray(columns.size)
        close = {
            child.close()
            _close()
        }
        next = {
            var off = child.next()
            var res = -1
            if (first) {
                if (off == -1) {
                    close()
                } else {
                    for (i in columns.indices) {
                        buf[i] = child.buf[off + i]
                    }
                    res = 0
                }
                first = false
            } else {
                loop@ while (true) {
                    if (off == -1) {
                        close()
                        break@loop
                    }
                    for (i in columns.indices) {
                        if (buf[i] != child.buf[off + i]) {
                            for (j in columns.indices) {
                                buf[j] = child.buf[off + j]
                            }
                            res = 0
                            break@loop
                        }
                    }
                    off = child.next()
                }
            }
            /*return*/res
        }
    }
}
