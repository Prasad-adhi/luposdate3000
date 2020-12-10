package lupos.s04logicalOperators.iterator
import lupos.s00misc.MERGE_SORT_MIN_ROWS
import lupos.s00misc.SanityCheck
import kotlin.jvm.JvmField
open class RowIteratorMerge(@JvmField val a: RowIterator, @JvmField val b: RowIterator, @JvmField val comparator: Comparator<Int>, @JvmField val compCount: Int) : RowIterator() {
    companion object {
        /*suspend*/ operator fun invoke(a: RowIterator, comparator: Comparator<Int>, compCount: Int, columns: Array<String>): RowIterator {
            SanityCheck.check { columns.size == a.columns.size }
            var buf1 = IntArray(columns.size * MERGE_SORT_MIN_ROWS)
            var buf2 = IntArray(columns.size * MERGE_SORT_MIN_ROWS)
            var done = false
            val resultList = mutableListOf<RowIterator?>()
            val columnMapping = IntArray(columns.size)
            for (i in columns.indices) {
                for (j in columns.indices) {
                    if (a.columns[j] == columns[i]) {
                        columnMapping[i] = j
                    }
                }
            }
            while (!done) {
                var i = 0
                while (i < buf1.size) {
                    val next = a.next()
                    if (next < 0) {
                        done = true
                        a.close()
                        break
                    }
                    for (j in 0 until columns.size) {
                        buf1[i++] = a.buf[next + columnMapping[j]]
                    }
                }
                val total = i / columns.size
                var off: Int
                var shift = 0
                var size = 1 shl shift
                var count: Int
                var mid: Int
                while (size / 2 < total) {
                    off = 0
                    shift++
                    size = 1 shl shift
                    while (off < total) {
                        count = if (off + size <= total) {
                            size
                        } else {
                            total - off
                        }
                        mid = size / 2
                        val aEnd = (off + mid) * columns.size
                        val bEnd = (off + count) * columns.size
                        var a2 = off * columns.size
                        var b = aEnd
                        var c = a2
                        if (count < mid) {
                            b = a2
                            a2 = aEnd
                        }
                        loop@ while (a2 < aEnd && b < bEnd) {
                            for (l in columns.indices) {
                                var cmp: Int
                                var j = 0
                                while (j < compCount) {
                                    cmp = comparator.compare(buf1[a2 + l], buf1[b + l])
                                    if (cmp < 0) {
                                        for (k in columns.indices) {
                                            buf2[c++] = buf1[a2++]
                                        }
                                        continue@loop
                                    } else if (cmp > 0) {
                                        for (k in columns.indices) {
                                            buf2[c++] = buf1[b++]
                                        }
                                        continue@loop
                                    }
                                    j++
                                }
                                while (j < columns.size) {
                                    cmp = buf1[a2 + l] - buf1[b + l]
                                    if (cmp < 0) {
                                        for (k in columns.indices) {
                                            buf2[c++] = buf1[a2++]
                                        }
                                        continue@loop
                                    } else if (cmp > 0) {
                                        for (k in columns.indices) {
                                            buf2[c++] = buf1[b++]
                                        }
                                        continue@loop
                                    }
                                    j++
                                }
                            }
                            for (j in columns.indices) {
                                buf2[c++] = buf1[a2++]
                            }
                            for (j in columns.indices) {
                                buf2[c++] = buf1[b++]
                            }
                        }
                        while (a2 < aEnd) {
                            buf2[c++] = buf1[a2++]
                        }
                        while (b < bEnd) {
                            buf2[c++] = buf1[b++]
                        }
                        off += size
                    }
                    val t = buf1
                    buf1 = buf2
                    buf2 = t
                }
                val it = RowIteratorBuf(buf1, columns, i)
                if (i > 0 || resultList.size == 0) {
                    if (resultList.size == 0) {
                        resultList.add(it)
                    } else if (resultList[0] == null) {
                        resultList[0] = it
                    } else {
                        resultList[0] = RowIteratorMerge(resultList[0]!!, it, comparator, compCount)
                        if (resultList[resultList.size - 1] != null) {
                            resultList.add(null)
                        }
                        var j = 1
                        while (j < resultList.size) {
                            if (resultList[j] == null) {
                                resultList[j] = resultList[j - 1]
                                resultList[j - 1] = null
                                break
                            } else {
                                resultList[j] = RowIteratorMerge(resultList[j]!!, resultList[j - 1]!!, comparator, compCount)
                                resultList[j - 1] = null
                            }
                            j++
                        }
                    }
                }
                buf1 = IntArray(columns.size * MERGE_SORT_MIN_ROWS)
            }
            var j = 1
            while (j < resultList.size) {
                if (resultList[j] == null) {
                    resultList[j] = resultList[j - 1]
                } else if (resultList[j - 1] != null) {
                    resultList[j] = RowIteratorMerge(resultList[j]!!, resultList[j - 1]!!, comparator, compCount)
                }
                j++
            }
            SanityCheck.check { resultList.size > 0 }
            return resultList[resultList.size - 1]!!
        }
    }
    @JvmField
    var flag: Int = 3
    @JvmField
    var aIdx: Int = -1
    @JvmField
    var bIdx: Int = -1
    init {
        SanityCheck {
            SanityCheck.check { a.columns.size == b.columns.size }
            for (i in a.columns.indices) {
                SanityCheck.check { a.columns[i] == b.columns[i] }
            }
        }
        columns = a.columns
        close = {
            a.close()
            b.close()
            _close()
        }
        next = {
            var res = -1
            when (flag) {
                1 -> { // call next on a, b is empty
                    res = a.next()
                    buf = a.buf
                    if (res < 0) {
                        a.close()
                        flag = 0
                    }
                }
                2 -> { // call next on b, a is empty
                    res = b.next()
                    buf = b.buf
                    if (res < 0) {
                        b.close()
                        flag = 0
                    }
                }
                4 -> { // call next on a, b is not empty
                    aIdx = a.next()
                    if (aIdx < 0) {
                        buf = b.buf
                        res = bIdx
                        flag = 2
                    } else {
                        compare(
                            {
                                buf = a.buf
                                res = aIdx
                                flag = 4
                            },
                            {
                                buf = b.buf
                                res = bIdx
                                flag = 5
                            }
                        )
                    }
                }
                5 -> { // call next on b, a is not empty
                    bIdx = b.next()
                    if (bIdx < 0) {
                        buf = a.buf
                        res = aIdx
                        flag = 1
                    } else {
                        compare(
                            {
                                buf = a.buf
                                res = aIdx
                                flag = 4
                            },
                            {
                                buf = b.buf
                                res = bIdx
                                flag = 5
                            }
                        )
                    }
                }
                3 -> { // call next on both
                    aIdx = a.next()
                    bIdx = b.next()
                    if (aIdx < 0 && bIdx < 0) {
                        res = -1
                        a.close()
                        b.close()
                        flag = 0
                    } else if (bIdx < 0) {
                        buf = a.buf
                        b.close()
                        res = aIdx
                        flag = 1
                    } else if (aIdx < 0) {
                        buf = b.buf
                        a.close()
                        res = bIdx
                        flag = 2
                    } else {
                        compare(
                            {
                                buf = a.buf
                                res = aIdx
                                flag = 4
                            },
                            {
                                buf = b.buf
                                res = bIdx
                                flag = 5
                            }
                        )
                    }
                }
            }
            res
        }
    }
    private inline fun compare(crossinline actionA: () -> Unit, crossinline actionB: () -> Unit) {
        var i = 0
        while (i < compCount) {
            val cmp = comparator.compare(a.buf[aIdx + i], b.buf[bIdx + i])
            if (cmp < 0) {
                actionA()
                return
            } else if (cmp > 0) {
                actionB()
                return
            }
            i++
        }
        while (i < columns.size) {
            val cmp = a.buf[aIdx + i] - b.buf[bIdx + i]
            if (cmp < 0) {
                actionA()
                return
            } else if (cmp > 0) {
                actionB()
                return
            }
            i++
        }
        actionA()
    }
}
