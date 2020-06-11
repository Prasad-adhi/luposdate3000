/* this File is autogenerated by generate-buildfile.kts */
/* DO NOT MODIFY DIRECTLY */
package lupos.s00misc

import lupos.s00misc.Coverage

/* Substitutions :: Long,,,LongArray, */
class MyListLong {
    class MyListLongPage(val version: Int) {
        var next: MyListLongPage? = null
        var size = 0/*local*/
        val data = MyListLongSmall()
    }

    @JvmField
    var version = 0

    @JvmField
    var pagecount = 1

    @JvmField
    var size = 0

    @JvmField
    var page = MyListLongPage(version)

    @JvmField
    var lastpage = page
    fun clear() {
        version++
        size = 0
        page = MyListLongPage(version)
        pagecount = 1
        lastpage = page
    }

    fun shrinkToFit() {
        if (pagecount > 5) {
            if (pagecount * ARRAY_LIST_BLOCK_CAPACITY > size * 2) {
                var c = 1
                val b = MyListLongPage(version)
                var t = b
                var it = iterator()
                while (it.hasNext()) {
                    var j = 0
                    while (it.hasNext() && j < ARRAY_LIST_BLOCK_CAPACITY) {
                        t.data[j] = it.next()
                        j++
                    }
                    t.size = j
                    if (it.hasNext()) {
                        t.next = MyListLongPage(version)
                        t = t.next!!
                        c++
                    }
                }
                pagecount = c
                page = b
                lastpage = t
            }
        }
    }

    inline fun reserve(capacity: Int) {
    }

    constructor() {
    }

    constructor(value: Long) {
        size = 1
        page.size = 1
        page.data[0] = value
    }

    constructor(initialCapacity: Int, init: (Int) -> Long) {
        size = initialCapacity
        var i = 0
        var tmp = page
        while (i < size) {
            var j = tmp.size
            while (tmp.size < ARRAY_LIST_BLOCK_CAPACITY && i < size) {
                tmp.data[j] = init(i++)
                j++
            }
            tmp.size = j
            if (i < size) {
                val p = MyListLongPage(version)
                pagecount++
                p.next = tmp.next
                tmp.next = p
                tmp = p
            }
        }
        lastpage = tmp
    }

    fun set(location: MyListLongFastAccess, value: Long) {
        if (location.version == version) {
            location.page.data[location.idx] = value
        } else {
            this[location.globalIdx] = value
        }
    }

    class MyListLongFastAccess(val page: MyListLongPage, val idx: Int, val version: Int, val globalIdx: Int)

    fun getNullPointer() = MyListLongFastAccess(page, 0, version - 1, 0)
    fun addAndGetPointer(value: Long): MyListLongFastAccess {
        if (lastpage.size < ARRAY_LIST_BLOCK_CAPACITY) {
            lastpage.data[lastpage.size] = value
            lastpage.size++
        } else {
            val next = MyListLongPage(version)
            lastpage.next = next
            lastpage = next
            lastpage.data[lastpage.size] = value
            lastpage.size++
            pagecount++
        }
        size++
        shrinkToFit()
        return MyListLongFastAccess(lastpage, lastpage.size - 1, version, size - 1)
    }

    fun add(value: Long) {
        if (lastpage.size < ARRAY_LIST_BLOCK_CAPACITY) {
            lastpage.data[lastpage.size] = value
            lastpage.size++
        } else {
            val next = MyListLongPage(version)
            lastpage.next = next
            lastpage = next
            lastpage.data[lastpage.size] = value
            lastpage.size++
            pagecount++
        }
        size++
        shrinkToFit()
    }

    inline operator fun get(idx: Int): Long {
        SanityCheck.check({ idx < size }, { "a" })
        var tmp = page
        var offset = 0
        while (offset + tmp.size <= idx) {
            offset += tmp.size
            SanityCheck.check({ tmp.next != null }, { "b" })
            tmp = tmp.next!!
        }
        return tmp.data[idx - offset] as Long
    }

    fun removeInternal(prev: MyListLongPage, tmp: MyListLongPage, i: Int) {
        if (tmp.size == 1) {
            if (tmp == page) {
                if (tmp.next != null) {
//first page must not be null, and therefore is allowed to be empty
                    page = tmp.next!!
                }
            } else {
//remove page in the middle
                SanityCheck.check({ prev != tmp }, { "c" })
                prev.next = tmp.next
            }
        } else {
            for (j in i until tmp.size - 1) {
                tmp.data[j] = tmp.data[j + 1]
            }
        }
        tmp.size--
        size--
    }

    fun remove(value: Long): Boolean {
        var i = 0
        var tmp = page
        var prev = page
        while (i < size) {
            var j = 0
            while (j < tmp!!.size) {
                if (tmp!!.data[j] == value) {
                    removeInternal(prev, tmp, j)
                    return true
                }
                j++
                i++
            }
            prev = tmp
            tmp = tmp.next!!
        }
        return false
    }

    fun removeAt(idx: Int): Long {
        SanityCheck.check({ idx < size }, { "d" })
        var prev = page
        var tmp = page
        var offset = 0
        while (offset + tmp.size < idx) {
            offset += tmp.size
            prev = tmp
            tmp = tmp.next!!
        }
        var i = idx - offset
        var res = tmp.data[i] as Long
        removeInternal(prev, tmp, i)
        return res
    }

    inline operator fun set(idx: Int, value: Long) {
        SanityCheck.check({ idx <= size }, { "e" })
        if (idx == size) {
            if (lastpage.size < ARRAY_LIST_BLOCK_CAPACITY) {
                lastpage.data[lastpage.size] = value
                lastpage.size++
            } else {
                lastpage.next = MyListLongPage(version)
                pagecount++
                lastpage = lastpage.next!!
                lastpage.size = 1
                lastpage.data[0] = value
            }
            size++
        } else {
            var tmp = page
            var offset = 0
            var t = idx
            while (t >= tmp.size) {
                offset += tmp.size
                t = idx - offset
                tmp = tmp.next!!
            }
            tmp.data[t] = value
        }
        shrinkToFit()
    }

    fun add(idx: Int, value: Long) {
        SanityCheck.check({ idx <= size }, { "f" })
        if (idx == size) {
            if (lastpage.size < ARRAY_LIST_BLOCK_CAPACITY) {
                lastpage.data[lastpage.size] = value
                lastpage.size++
            } else {
                lastpage.next = MyListLongPage(version)
                pagecount++
                lastpage = lastpage.next!!
                lastpage.data[lastpage.size] = value
                lastpage.size++
            }
        } else {
            var tmp = page
            var offset = 0
            var t = idx
            while (t > tmp.size) {
                offset += tmp.size
                t = idx - offset
                tmp = tmp.next!!
            }
            if (t == tmp.size && tmp.size < ARRAY_LIST_BLOCK_CAPACITY) {
                tmp.data[t] = value
                tmp.size++
            } else {
                if (t == tmp.size) {
                    offset += tmp.size
                    t = idx - offset
                    tmp = tmp.next!!
                }
                if (tmp.size < ARRAY_LIST_BLOCK_CAPACITY) {
                    for (i in tmp.size downTo t + 1) {
                        tmp.data[i] = tmp.data[i - 1]
                    }
                    tmp.data[t] = value
                    tmp.size++
                } else {
                    var p = MyListLongPage(version)
                    pagecount++
                    p.next = tmp.next
                    tmp.next = p
                    var j = 0
                    for (i in t until ARRAY_LIST_BLOCK_CAPACITY) {
                        p.data[j] = tmp.data[i]
                        j++
                    }
                    tmp.size = t + 1
                    p.size = j
                    tmp.data[t] = value
                    if (lastpage == tmp) {
                        lastpage = p
                    }
                }
            }
        }
        size++
        shrinkToFit()
    }

    fun debug(): String {
        var res = StringBuilder()
        var totalsize = 0
        res.append("Long debug $size [")
        var tmp = page
        while (true) {
            totalsize += tmp.size
            res.append("" + tmp.size + "{")
            for (i in 0 until tmp.size) {
                res.append("" + tmp.data[i] + ", ")
            }
            res.append("}, ")
            if (tmp.next == null) {
                break
            }
            tmp = tmp.next!!
        }
        res.append("]")
        SanityCheck.check({ totalsize == size }, { "g" })
        SanityCheck.check({ tmp == lastpage }, { "h" })
        return res.toString()
    }

    inline fun forEach(crossinline action: (Long) -> Unit) {
        var tmp = page
        while (true) {
            for (i in 0 until tmp.size) {
                action(tmp.data[i] as Long)
            }
            if (tmp.next == null) {
                break
            } else {
                tmp = tmp.next!!
            }
        }
    }

    inline fun iterator(startidx: Int): MyListLongIterator {
        return MyListLongIterator(this, startidx)
    }

    inline operator fun iterator(): MyListLongIterator {
        return MyListLongIterator(this, 0)
    }

    class MyListLongIterator(@JvmField val data: MyListLong, startidx: Int) : Iterator<Long> {
        var tmp = data.page
        var idx = 0

        init {
            var i = 0
            while (i + tmp.size < startidx) {
                i += tmp.size
                tmp = tmp.next!!
            }
            idx = startidx - i
        }

        override fun hasNext() = idx < tmp.size || tmp.next != null
        override fun next(): Long {
            if (idx == tmp.size) {
                tmp = tmp.next!!
                idx = 0
            }
            val res = tmp.data[idx] as Long
            idx++
            return res
        }
    }

    class MyListLongSmall {
        @JvmField
        var size = 0

        @JvmField
        var capacity = 1

        @JvmField
        var data: LongArray
        inline fun reserve(capacity: Int) {
            SanityCheck.check({ capacity <= ARRAY_LIST_BLOCK_CAPACITY }, { "i" })
            if (this.capacity < capacity) {
                this.capacity = capacity
                val tmp = LongArray(capacity) 
                        data.copyInto(tmp)
                data = tmp
            }
        }

        constructor() {
            data = LongArray(capacity) 
        }

        constructor(value: Long) {
            data = LongArray(capacity) 
                    data[size] = value
            size++
        }

        constructor(initialCapacity: Int, init: (Int) -> Long) {
            capacity = initialCapacity
            size = capacity
            data = LongArray(capacity) { init(it) }
        }

        fun clear() {
            size = 0
        }

        fun add(value: Long) {
            if (size >= capacity) {
                reserve(capacity * 2)
            }
            data[size] = value
            size++
        }

        inline operator fun get(idx: Int): Long {
            return data.get(idx) as Long
        }

        inline operator fun set(idx: Int, value: Long) {
            SanityCheck.check({ idx <= size }, { "j" })
            if (idx == size) {
                add(value)
            } else {
                data.set(idx, value)
            }
        }

        fun remove(value: Long): Boolean {
            for (idx in 0 until size) {
                if (data[idx] == value) {
                    removeAt(idx)
                    return true
                }
            }
            return false
        }

        fun removeAt(idx: Int): Long {
            val res = data[idx]
            SanityCheck.check({ idx < size }, { "k" })
            for (i in idx until size) {
                data[i] = data[i + 1]
            }
            size--
            return res as Long
        }

        fun add(idx: Int, value: Long) {
            if (size >= capacity) {
                reserve(capacity * 2)
            }
            if (idx < size) {
                size++
                for (i in size downTo idx + 1) {
                    data[i] = data[i - 1]
                }
                data[idx] = value
            } else {
                data[size] = value
                size++
            }
        }

        inline operator fun iterator(): MyListLongSmallIterator {
            return MyListLongSmallIterator(this)
        }
    }

    class MyListLongSmallIterator(@JvmField val data: MyListLongSmall) : Iterator<Long> {
        var index = 0
        override fun hasNext(): Boolean {
            return index < data.size
        }

        override fun next(): Long {
            val res = data.data[index] as Long
            index++
            return res
        }
    }
}
