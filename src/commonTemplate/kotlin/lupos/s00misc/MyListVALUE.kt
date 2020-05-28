package lupos.s00misc
import lupos.s00misc.Coverage
/* Substitutions :: VALUE,GDEF,GUSE,ARRAYTYPE,ARRAYINITIALIZER */
class MyListVALUEGDEF {
    class MyListVALUEPageGDEF(val version: Int) {
        var next: MyListVALUEPageGDEF? = null
        var size = 0/*local*/
        val data = MyListVALUESmallGDEF()
    }
    @JvmField
    var version = 0
    @JvmField
    var pagecount = 1
    @JvmField
    var size = 0
    @JvmField
    var page = MyListVALUEPageGDEF(version)
    @JvmField
    var lastpage = page
    fun shrinkToFit() {
Coverage.funStart(14283)
        if (pagecount > 5) {
Coverage.ifStart(14284)
            if (pagecount * ARRAY_LIST_BLOCK_CAPACITY > size * 2) {
Coverage.ifStart(14285)
                var c = 1
Coverage.statementStart(14286)
                val b = MyListVALUEPageGDEF(version)
Coverage.statementStart(14287)
                var t = b
Coverage.statementStart(14288)
                var it = iterator()
Coverage.statementStart(14289)
                while (it.hasNext()) {
Coverage.whileLoopStart(14290)
                    var j = 0
Coverage.statementStart(14291)
                    while (it.hasNext() && j < ARRAY_LIST_BLOCK_CAPACITY) {
Coverage.whileLoopStart(14292)
                        t.data[j] = it.next()
Coverage.statementStart(14293)
                        j++
Coverage.statementStart(14294)
                    }
Coverage.statementStart(14295)
                    t.size = j
Coverage.statementStart(14296)
                    if (it.hasNext()) {
Coverage.ifStart(14297)
                        t.next = MyListVALUEPageGDEF(version)
Coverage.statementStart(14298)
                        t = t.next!!
Coverage.statementStart(14299)
                        c++
Coverage.statementStart(14300)
                    }
Coverage.statementStart(14301)
                }
Coverage.statementStart(14302)
                pagecount = c
Coverage.statementStart(14303)
                page = b
Coverage.statementStart(14304)
                lastpage = t
Coverage.statementStart(14305)
            }
Coverage.statementStart(14306)
        }
Coverage.statementStart(14307)
    }
    inline fun reserve(capacity: Int) {
Coverage.funStart(14308)
    }
    constructor() {
    }
    constructor(value: VALUE) {
        size = 1
        page.size = 1
        page.data[0] = value
    }
    constructor(initialCapacity: Int, init: (Int) -> VALUE) {
        size = initialCapacity
        var i = 0
        var tmp = page
        while (i < size) {
Coverage.whileLoopStart(14309)
            var j = tmp.size
            while (tmp.size < ARRAY_LIST_BLOCK_CAPACITY && i < size) {
Coverage.whileLoopStart(14310)
                tmp.data[j] = init(i++)
                j++
            }
            tmp.size = j
            if (i < size) {
Coverage.ifStart(14311)
                val p = MyListVALUEPageGDEF(version)
                pagecount++
                p.next = tmp.next
                tmp.next = p
                tmp = p
            }
        }
        lastpage = tmp
    }
    fun clear() {
Coverage.funStart(14312)
        version++
Coverage.statementStart(14313)
        size = 0
Coverage.statementStart(14314)
        page = MyListVALUEPageGDEF(version)
Coverage.statementStart(14315)
        pagecount = 1
Coverage.statementStart(14316)
        lastpage = page
Coverage.statementStart(14317)
    }
    fun set(location: MyListVALUEFastAccessGUSE, value: VALUE) {
Coverage.funStart(14318)
        if (location.version == version) {
Coverage.ifStart(14319)
            location.page.data[location.idx] = value
Coverage.statementStart(14320)
        } else {
Coverage.ifStart(14321)
            this[location.globalIdx] = value
Coverage.statementStart(14322)
        }
Coverage.statementStart(14323)
    }
    class MyListVALUEFastAccessGDEF(val page: MyListVALUEPageGUSE, val idx: Int, val version: Int, val globalIdx: Int)
    fun getNullPointer() = MyListVALUEFastAccess(page, 0, version - 1, 0)
    fun addAndGetPointer(value: VALUE): MyListVALUEFastAccessGUSE {
Coverage.funStart(14324)
        if (lastpage.size < ARRAY_LIST_BLOCK_CAPACITY) {
Coverage.ifStart(14325)
            lastpage.data[lastpage.size] = value
Coverage.statementStart(14326)
            lastpage.size++
Coverage.statementStart(14327)
        } else {
Coverage.ifStart(14328)
            val next = MyListVALUEPageGDEF(version)
Coverage.statementStart(14329)
            lastpage.next = next
Coverage.statementStart(14330)
            lastpage = next
Coverage.statementStart(14331)
            lastpage.data[lastpage.size] = value
Coverage.statementStart(14332)
            lastpage.size++
Coverage.statementStart(14333)
            pagecount++
Coverage.statementStart(14334)
        }
Coverage.statementStart(14335)
        size++
Coverage.statementStart(14336)
        shrinkToFit()
Coverage.statementStart(14337)
        return MyListVALUEFastAccess(lastpage, lastpage.size - 1, version, size - 1)
    }
    fun add(value: VALUE) {
Coverage.funStart(14338)
        if (lastpage.size < ARRAY_LIST_BLOCK_CAPACITY) {
Coverage.ifStart(14339)
            lastpage.data[lastpage.size] = value
Coverage.statementStart(14340)
            lastpage.size++
Coverage.statementStart(14341)
        } else {
Coverage.ifStart(14342)
            val next = MyListVALUEPageGDEF(version)
Coverage.statementStart(14343)
            lastpage.next = next
Coverage.statementStart(14344)
            lastpage = next
Coverage.statementStart(14345)
            lastpage.data[lastpage.size] = value
Coverage.statementStart(14346)
            lastpage.size++
Coverage.statementStart(14347)
            pagecount++
Coverage.statementStart(14348)
        }
Coverage.statementStart(14349)
        size++
Coverage.statementStart(14350)
        shrinkToFit()
Coverage.statementStart(14351)
    }
    inline operator fun get(idx: Int): VALUE {
Coverage.funStart(14352)
        SanityCheck.check({ idx < size }, { "a" })
Coverage.statementStart(14353)
        var tmp = page
Coverage.statementStart(14354)
        var offset = 0
Coverage.statementStart(14355)
        while (offset + tmp.size <= idx) {
Coverage.whileLoopStart(14356)
            offset += tmp.size
Coverage.statementStart(14357)
            SanityCheck.check({ tmp.next != null }, { "b" })
Coverage.statementStart(14358)
            tmp = tmp.next!!
Coverage.statementStart(14359)
        }
Coverage.statementStart(14360)
        return tmp.data[idx - offset] as VALUE
    }
    fun removeInternal(prev: MyListVALUEPageGDEF, tmp: MyListVALUEPageGDEF, i: Int) {
Coverage.funStart(14361)
        if (tmp.size == 1) {
Coverage.ifStart(14362)
            if (tmp == page) {
Coverage.ifStart(14363)
                if (tmp.next != null) {
Coverage.ifStart(14364)
//first page must not be null, and therefore is allowed to be empty
Coverage.statementStart(14365)
                    page = tmp.next!!
Coverage.statementStart(14366)
                }
Coverage.statementStart(14367)
            } else {
Coverage.ifStart(14368)
//remove page in the middle
Coverage.statementStart(14369)
                SanityCheck.check({ prev != tmp }, { "c" })
Coverage.statementStart(14370)
                prev.next = tmp.next
Coverage.statementStart(14371)
            }
Coverage.statementStart(14372)
        } else {
Coverage.ifStart(14373)
            for (j in i until tmp.size - 1) {
Coverage.forLoopStart(14374)
                tmp.data[j] = tmp.data[j + 1]
Coverage.statementStart(14375)
            }
Coverage.statementStart(14376)
        }
Coverage.statementStart(14377)
        tmp.size--
Coverage.statementStart(14378)
        size--
Coverage.statementStart(14379)
    }
    fun remove(value: VALUE): Boolean {
Coverage.funStart(14380)
        var i = 0
Coverage.statementStart(14381)
        var tmp = page
Coverage.statementStart(14382)
        var prev = page
Coverage.statementStart(14383)
        while (i < size) {
Coverage.whileLoopStart(14384)
            var j = 0
Coverage.statementStart(14385)
            while (j < tmp!!.size) {
Coverage.whileLoopStart(14386)
                if (tmp!!.data[j] == value) {
Coverage.ifStart(14387)
                    removeInternal(prev, tmp, j)
Coverage.statementStart(14388)
                    return true
                }
Coverage.statementStart(14389)
                j++
Coverage.statementStart(14390)
                i++
Coverage.statementStart(14391)
            }
Coverage.statementStart(14392)
            prev = tmp
Coverage.statementStart(14393)
            tmp = tmp.next!!
Coverage.statementStart(14394)
        }
Coverage.statementStart(14395)
        return false
    }
    fun removeAt(idx: Int): VALUE {
Coverage.funStart(14396)
        SanityCheck.check({ idx < size }, { "d" })
Coverage.statementStart(14397)
        var prev = page
Coverage.statementStart(14398)
        var tmp = page
Coverage.statementStart(14399)
        var offset = 0
Coverage.statementStart(14400)
        while (offset + tmp.size < idx) {
Coverage.whileLoopStart(14401)
            offset += tmp.size
Coverage.statementStart(14402)
            prev = tmp
Coverage.statementStart(14403)
            tmp = tmp.next!!
Coverage.statementStart(14404)
        }
Coverage.statementStart(14405)
        var i = idx - offset
Coverage.statementStart(14406)
        var res = tmp.data[i] as VALUE
Coverage.statementStart(14407)
        removeInternal(prev, tmp, i)
Coverage.statementStart(14408)
        return res
    }
    inline operator fun set(idx: Int, value: VALUE) {
Coverage.funStart(14409)
        SanityCheck.check({ idx <= size }, { "e" })
Coverage.statementStart(14410)
        if (idx == size) {
Coverage.ifStart(14411)
            if (lastpage.size < ARRAY_LIST_BLOCK_CAPACITY) {
Coverage.ifStart(14412)
                lastpage.data[lastpage.size] = value
Coverage.statementStart(14413)
                lastpage.size++
Coverage.statementStart(14414)
            } else {
Coverage.ifStart(14415)
                lastpage.next = MyListVALUEPageGDEF(version)
Coverage.statementStart(14416)
                pagecount++
Coverage.statementStart(14417)
                lastpage = lastpage.next!!
Coverage.statementStart(14418)
                lastpage.size = 1
Coverage.statementStart(14419)
                lastpage.data[0] = value
Coverage.statementStart(14420)
            }
Coverage.statementStart(14421)
            size++
Coverage.statementStart(14422)
        } else {
Coverage.ifStart(14423)
            var tmp = page
Coverage.statementStart(14424)
            var offset = 0
Coverage.statementStart(14425)
            var t = idx
Coverage.statementStart(14426)
            while (t >= tmp.size) {
Coverage.whileLoopStart(14427)
                offset += tmp.size
Coverage.statementStart(14428)
                t = idx - offset
Coverage.statementStart(14429)
                tmp = tmp.next!!
Coverage.statementStart(14430)
            }
Coverage.statementStart(14431)
            tmp.data[t] = value
Coverage.statementStart(14432)
        }
Coverage.statementStart(14433)
        shrinkToFit()
Coverage.statementStart(14434)
    }
    fun add(idx: Int, value: VALUE) {
Coverage.funStart(14435)
        SanityCheck.check({ idx <= size }, { "f" })
Coverage.statementStart(14436)
        if (idx == size) {
Coverage.ifStart(14437)
            if (lastpage.size < ARRAY_LIST_BLOCK_CAPACITY) {
Coverage.ifStart(14438)
                lastpage.data[lastpage.size] = value
Coverage.statementStart(14439)
                lastpage.size++
Coverage.statementStart(14440)
            } else {
Coverage.ifStart(14441)
                lastpage.next = MyListVALUEPageGDEF(version)
Coverage.statementStart(14442)
                pagecount++
Coverage.statementStart(14443)
                lastpage = lastpage.next!!
Coverage.statementStart(14444)
                lastpage.data[lastpage.size] = value
Coverage.statementStart(14445)
                lastpage.size++
Coverage.statementStart(14446)
            }
Coverage.statementStart(14447)
        } else {
Coverage.ifStart(14448)
            var tmp = page
Coverage.statementStart(14449)
            var offset = 0
Coverage.statementStart(14450)
            var t = idx
Coverage.statementStart(14451)
            while (t > tmp.size) {
Coverage.whileLoopStart(14452)
                offset += tmp.size
Coverage.statementStart(14453)
                t = idx - offset
Coverage.statementStart(14454)
                tmp = tmp.next!!
Coverage.statementStart(14455)
            }
Coverage.statementStart(14456)
            if (t == tmp.size && tmp.size < ARRAY_LIST_BLOCK_CAPACITY) {
Coverage.ifStart(14457)
                tmp.data[t] = value
Coverage.statementStart(14458)
                tmp.size++
Coverage.statementStart(14459)
            } else {
Coverage.ifStart(14460)
                if (t == tmp.size) {
Coverage.ifStart(14461)
                    offset += tmp.size
Coverage.statementStart(14462)
                    t = idx - offset
Coverage.statementStart(14463)
                    tmp = tmp.next!!
Coverage.statementStart(14464)
                }
Coverage.statementStart(14465)
                if (tmp.size < ARRAY_LIST_BLOCK_CAPACITY) {
Coverage.ifStart(14466)
                    for (i in tmp.size downTo t + 1) {
Coverage.forLoopStart(14467)
                        tmp.data[i] = tmp.data[i - 1]
Coverage.statementStart(14468)
                    }
Coverage.statementStart(14469)
                    tmp.data[t] = value
Coverage.statementStart(14470)
                    tmp.size++
Coverage.statementStart(14471)
                } else {
Coverage.ifStart(14472)
                    var p = MyListVALUEPageGDEF(version)
Coverage.statementStart(14473)
                    pagecount++
Coverage.statementStart(14474)
                    p.next = tmp.next
Coverage.statementStart(14475)
                    tmp.next = p
Coverage.statementStart(14476)
                    var j = 0
Coverage.statementStart(14477)
                    for (i in t until ARRAY_LIST_BLOCK_CAPACITY) {
Coverage.forLoopStart(14478)
                        p.data[j] = tmp.data[i]
Coverage.statementStart(14479)
                        j++
Coverage.statementStart(14480)
                    }
Coverage.statementStart(14481)
                    tmp.size = t + 1
Coverage.statementStart(14482)
                    p.size = j
Coverage.statementStart(14483)
                    tmp.data[t] = value
Coverage.statementStart(14484)
                    if (lastpage == tmp) {
Coverage.ifStart(14485)
                        lastpage = p
Coverage.statementStart(14486)
                    }
Coverage.statementStart(14487)
                }
Coverage.statementStart(14488)
            }
Coverage.statementStart(14489)
        }
Coverage.statementStart(14490)
        size++
Coverage.statementStart(14491)
        shrinkToFit()
Coverage.statementStart(14492)
    }
    fun debug(): String {
Coverage.funStart(14493)
        var res = StringBuilder()
Coverage.statementStart(14494)
        var totalsize = 0
Coverage.statementStart(14495)
        res.append("VALUEGDEF debug $size [")
Coverage.statementStart(14496)
        var tmp = page
Coverage.statementStart(14497)
        while (true) {
Coverage.whileLoopStart(14498)
            totalsize += tmp.size
Coverage.statementStart(14499)
            res.append("" + tmp.size + "{")
Coverage.statementStart(14500)
            for (i in 0 until tmp.size) {
Coverage.forLoopStart(14501)
                res.append("" + tmp.data[i] + ", ")
Coverage.statementStart(14502)
            }
Coverage.statementStart(14503)
            res.append("}, ")
Coverage.statementStart(14504)
            if (tmp.next == null) {
Coverage.ifStart(14505)
                break
            }
Coverage.statementStart(14506)
            tmp = tmp.next!!
Coverage.statementStart(14507)
        }
Coverage.statementStart(14508)
        res.append("]")
Coverage.statementStart(14509)
        SanityCheck.check({ totalsize == size }, { "g" })
Coverage.statementStart(14510)
        SanityCheck.check({ tmp == lastpage }, { "h" })
Coverage.statementStart(14511)
        return res.toString()
    }
    inline fun forEach(crossinline action: (VALUE) -> Unit) {
Coverage.funStart(14512)
        var tmp = page
Coverage.statementStart(14513)
        while (true) {
Coverage.whileLoopStart(14514)
            for (i in 0 until tmp.size) {
Coverage.forLoopStart(14515)
                action(tmp.data[i] as VALUE)
Coverage.statementStart(14516)
            }
Coverage.statementStart(14517)
            if (tmp.next == null) {
Coverage.ifStart(14518)
                break
            } else {
Coverage.statementStart(14519)
                tmp = tmp.next!!
Coverage.statementStart(14520)
            }
Coverage.statementStart(14521)
        }
Coverage.statementStart(14522)
    }
    inline fun iterator(startidx: Int): MyListVALUEIteratorGUSE {
Coverage.funStart(14523)
        return MyListVALUEIterator(this, startidx)
    }
    inline operator fun iterator(): MyListVALUEIteratorGUSE {
Coverage.funStart(14524)
        return MyListVALUEIterator(this, 0)
    }
    class MyListVALUEIteratorGDEF(@JvmField val data: MyListVALUEGUSE, startidx: Int) : Iterator<VALUE> {
        var tmp = data.page
        var idx = 0
        init {
Coverage.funStart(14525)
            var i = 0
Coverage.statementStart(14526)
            while (i + tmp.size < startidx) {
Coverage.whileLoopStart(14527)
                i += tmp.size
Coverage.statementStart(14528)
                tmp = tmp.next!!
Coverage.statementStart(14529)
            }
Coverage.statementStart(14530)
            idx = startidx - i
Coverage.statementStart(14531)
        }
        override fun hasNext() = idx < tmp.size || tmp.next != null
        override fun next(): VALUE {
Coverage.funStart(14532)
            if (idx == tmp.size) {
Coverage.ifStart(14533)
                tmp = tmp.next!!
Coverage.statementStart(14534)
                idx = 0
Coverage.statementStart(14535)
            }
Coverage.statementStart(14536)
            val res = tmp.data[idx] as VALUE
Coverage.statementStart(14537)
            idx++
Coverage.statementStart(14538)
            return res
        }
    }
    class MyListVALUESmallGDEF {
        @JvmField
        var size = 0
        @JvmField
        var capacity = 1
        @JvmField
        var data: ARRAYTYPE
        inline fun reserve(capacity: Int) {
Coverage.funStart(14539)
            SanityCheck.check({ capacity <= ARRAY_LIST_BLOCK_CAPACITY }, { "i" })
Coverage.statementStart(14540)
            if (this.capacity < capacity) {
Coverage.ifStart(14541)
                this.capacity = capacity
Coverage.statementStart(14542)
                val tmp = ARRAYTYPE(capacity) ARRAYINITIALIZER
Coverage.statementStart(14543)
                        data.copyInto(tmp)
Coverage.statementStart(14544)
                data = tmp
Coverage.statementStart(14545)
            }
Coverage.statementStart(14546)
        }
        constructor() {
            data = ARRAYTYPE(capacity) ARRAYINITIALIZER
        }
        constructor(value: VALUE) {
            data = ARRAYTYPE(capacity) ARRAYINITIALIZER
                    data[size] = value
            size++
        }
        constructor(initialCapacity: Int, init: (Int) -> VALUE) {
            capacity = initialCapacity
            size = capacity
            data = ARRAYTYPE(capacity) { init(it) }
        }
        fun clear() {
Coverage.funStart(14547)
            size = 0
Coverage.statementStart(14548)
        }
        fun add(value: VALUE) {
Coverage.funStart(14549)
            if (size >= capacity) {
Coverage.ifStart(14550)
                reserve(capacity * 2)
Coverage.statementStart(14551)
            }
Coverage.statementStart(14552)
            data[size] = value
Coverage.statementStart(14553)
            size++
Coverage.statementStart(14554)
        }
        inline operator fun get(idx: Int): VALUE {
Coverage.funStart(14555)
            return data.get(idx) as VALUE
        }
        inline operator fun set(idx: Int, value: VALUE) {
Coverage.funStart(14556)
            SanityCheck.check({ idx <= size }, { "j" })
Coverage.statementStart(14557)
            if (idx == size) {
Coverage.ifStart(14558)
                add(value)
Coverage.statementStart(14559)
            } else {
Coverage.ifStart(14560)
                data.set(idx, value)
Coverage.statementStart(14561)
            }
Coverage.statementStart(14562)
        }
        fun remove(value: VALUE): Boolean {
Coverage.funStart(14563)
            for (idx in 0 until size) {
Coverage.forLoopStart(14564)
                if (data[idx] == value) {
Coverage.ifStart(14565)
                    removeAt(idx)
Coverage.statementStart(14566)
                    return true
                }
Coverage.statementStart(14567)
            }
Coverage.statementStart(14568)
            return false
        }
        fun removeAt(idx: Int): VALUE {
Coverage.funStart(14569)
            val res = data[idx]
Coverage.statementStart(14570)
            SanityCheck.check({ idx < size }, { "k" })
Coverage.statementStart(14571)
            for (i in idx until size) {
Coverage.forLoopStart(14572)
                data[i] = data[i + 1]
Coverage.statementStart(14573)
            }
Coverage.statementStart(14574)
            size--
Coverage.statementStart(14575)
            return res as VALUE
        }
        fun add(idx: Int, value: VALUE) {
Coverage.funStart(14576)
            if (size >= capacity) {
Coverage.ifStart(14577)
                reserve(capacity * 2)
Coverage.statementStart(14578)
            }
Coverage.statementStart(14579)
            if (idx < size) {
Coverage.ifStart(14580)
                size++
Coverage.statementStart(14581)
                for (i in size downTo idx + 1) {
Coverage.forLoopStart(14582)
                    data[i] = data[i - 1]
Coverage.statementStart(14583)
                }
Coverage.statementStart(14584)
                data[idx] = value
Coverage.statementStart(14585)
            } else {
Coverage.ifStart(14586)
                data[size] = value
Coverage.statementStart(14587)
                size++
Coverage.statementStart(14588)
            }
Coverage.statementStart(14589)
        }
        inline operator fun iterator(): MyListVALUESmallIteratorGUSE {
Coverage.funStart(14590)
            return MyListVALUESmallIterator(this)
        }
    }
    class MyListVALUESmallIteratorGDEF(@JvmField val data: MyListVALUESmallGUSE) : Iterator<VALUE> {
        var index = 0
        override fun hasNext(): Boolean {
Coverage.funStart(14591)
            return index < data.size
        }
        override fun next(): VALUE {
Coverage.funStart(14592)
            val res = data.data[index] as VALUE
Coverage.statementStart(14593)
            index++
Coverage.statementStart(14594)
            return res
        }
    }
}
