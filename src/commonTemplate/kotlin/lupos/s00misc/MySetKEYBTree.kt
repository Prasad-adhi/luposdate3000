package lupos.s00misc
import lupos.s00misc.Coverage
class MySetKEYBTreeGDEF(val t: Int) {
    var root: MySetKEYBTreeNodeGUSE? = null
    var size = 0
    constructor() : this(B_TREE_BRANCHING_FACTOR)
    class MySetKEYBTreeNodeIteratorGDEF(val node: MySetKEYBTreeNodeGUSE) : Iterator<KEY> {
        var i = 0
        var childIterator = node.C[0]!!.iterator()
        override fun hasNext(): Boolean {
Coverage.funStart(15051)
            if (node.leaf) {
Coverage.ifStart(15052)
                return i < node.n
            } else {
Coverage.statementStart(15053)
                return i < node.n || (i == node.n && childIterator.hasNext())
            }
Coverage.statementStart(15054)
        }
        override fun next(): KEY {
Coverage.funStart(15055)
            if (node.leaf) {
Coverage.ifStart(15056)
                return node.keys[i++] as KEY
            } else {
Coverage.statementStart(15057)
                if (childIterator.hasNext()) {
Coverage.ifStart(15058)
                    return childIterator.next()
                } else {
Coverage.statementStart(15059)
                    childIterator = node.C[i + 1]!!.iterator()
Coverage.statementStart(15060)
                    return node.keys[i++] as KEY
                }
Coverage.statementStart(15061)
            }
Coverage.statementStart(15062)
        }
    }
    class MySetKEYBTreeNodeGDEF(val t: Int, val leaf: Boolean) {
        val keys = ARRAYTYPE(2 * t - 1) ARRAYINITIALIZER
        val C = Array<MySetKEYBTreeNodeGUSE?>(2 * t) { null }
        var n = 0
        fun free() {
Coverage.funStart(15063)
            /*later when buffer-manager is used*/
Coverage.statementStart(15064)
        }
        fun iterator() = MySetKEYBTreeNodeIteratorGUSE(this)
        fun findKEY(k: KEY): Int {
Coverage.funStart(15065)
            var idx = 0
Coverage.statementStart(15066)
            while (idx < n && (keys[idx] as KEY) < k) {
Coverage.whileLoopStart(15067)
                idx++
Coverage.statementStart(15068)
            }
Coverage.statementStart(15069)
            return idx
        }
        fun remove(k: KEY): KEY? {
Coverage.funStart(15070)
            val idx = findKEY(k)
Coverage.statementStart(15071)
            val key = keys[idx] as KEY
Coverage.statementStart(15072)
            if (idx < n && key == k) {
Coverage.ifStart(15073)
                if (leaf) {
Coverage.ifStart(15074)
                    removeFromLeaf(idx)
Coverage.statementStart(15075)
                } else {
Coverage.ifStart(15076)
                    removeFromNonLeaf(idx)
Coverage.statementStart(15077)
                }
Coverage.statementStart(15078)
                return key
            } else if (!leaf) {
Coverage.statementStart(15079)
                val flag = idx == n
Coverage.statementStart(15080)
                if (C[idx]!!.n < t) {
Coverage.ifStart(15081)
                    fill(idx)
Coverage.statementStart(15082)
                }
Coverage.statementStart(15083)
                if (flag && idx > n) {
Coverage.ifStart(15084)
                    return C[idx - 1]!!.remove(k)
                } else {
Coverage.statementStart(15085)
                    return C[idx]!!.remove(k)
                }
Coverage.statementStart(15086)
            } else {
Coverage.ifStart(15087)
                return null
            }
Coverage.statementStart(15088)
        }
        fun removeFromLeaf(idx: Int) {
Coverage.funStart(15089)
            for (i in idx + 1 until n) {
Coverage.forLoopStart(15090)
                keys[i - 1] = keys[i]
Coverage.statementStart(15091)
            }
Coverage.statementStart(15092)
            n--
Coverage.statementStart(15093)
        }
        fun removeFromNonLeaf(idx: Int) {
Coverage.funStart(15094)
            val k = keys[idx] as KEY
Coverage.statementStart(15095)
            if (C[idx]!!.n >= t) {
Coverage.ifStart(15096)
                val pred = getPred(idx)
Coverage.statementStart(15097)
                keys[idx] = pred
Coverage.statementStart(15098)
                C[idx]!!.remove(pred)
Coverage.statementStart(15099)
            } else if (C[idx + 1]!!.n >= t) {
Coverage.ifStart(15100)
                val succ = getSucc(idx)
Coverage.statementStart(15101)
                keys[idx] = succ
Coverage.statementStart(15102)
                C[idx + 1]!!.remove(succ)
Coverage.statementStart(15103)
            } else {
Coverage.ifStart(15104)
                merge(idx)
Coverage.statementStart(15105)
                C[idx]!!.remove(k)
Coverage.statementStart(15106)
            }
Coverage.statementStart(15107)
        }
        fun getPred(idx: Int): KEY {
Coverage.funStart(15108)
            var cur = C[idx]!!
Coverage.statementStart(15109)
            while (!cur.leaf) {
Coverage.whileLoopStart(15110)
                cur = cur.C[cur.n]!!
Coverage.statementStart(15111)
            }
Coverage.statementStart(15112)
            return cur.keys[cur.n - 1] as KEY
        }
        fun getSucc(idx: Int): KEY {
Coverage.funStart(15113)
            var cur = C[idx + 1]!!
Coverage.statementStart(15114)
            while (!cur.leaf) {
Coverage.whileLoopStart(15115)
                cur = cur.C[0]!!
Coverage.statementStart(15116)
            }
Coverage.statementStart(15117)
            return cur.keys[0] as KEY
        }
        fun fill(idx: Int) {
Coverage.funStart(15118)
            if (idx != 0 && C[idx - 1]!!.n >= t) {
Coverage.ifStart(15119)
                borrowFromPrev(idx)
Coverage.statementStart(15120)
            } else if (idx != n && C[idx + 1]!!.n >= t) {
Coverage.ifStart(15121)
                borrowFromNext(idx)
Coverage.statementStart(15122)
            } else if (idx != n) {
Coverage.ifStart(15123)
                merge(idx)
Coverage.statementStart(15124)
            } else {
Coverage.ifStart(15125)
                merge(idx - 1)
Coverage.statementStart(15126)
            }
Coverage.statementStart(15127)
        }
        fun borrowFromPrev(idx: Int) {
Coverage.funStart(15128)
            val child = C[idx]!!
Coverage.statementStart(15129)
            val sibling = C[idx - 1]!!
Coverage.statementStart(15130)
            var i = child.n - 1
Coverage.statementStart(15131)
            while (i >= 0) {
Coverage.whileLoopStart(15132)
                child.keys[i + 1] = child.keys[i]
Coverage.statementStart(15133)
                i--
Coverage.statementStart(15134)
            }
Coverage.statementStart(15135)
            if (!child.leaf) {
Coverage.ifStart(15136)
                i = child.n
Coverage.statementStart(15137)
                while (i >= 0) {
Coverage.whileLoopStart(15138)
                    child.C[i + 1] = child.C[i]
Coverage.statementStart(15139)
                    i--
Coverage.statementStart(15140)
                }
Coverage.statementStart(15141)
                child.keys[0] = keys[idx - 1]
Coverage.statementStart(15142)
                if (!child.leaf) {
Coverage.ifStart(15143)
                    child.C[0] = sibling.C[sibling.n]
Coverage.statementStart(15144)
                }
Coverage.statementStart(15145)
                keys[idx - 1] = sibling.keys[sibling.n - 1]
Coverage.statementStart(15146)
                child.n++
Coverage.statementStart(15147)
                sibling.n--
Coverage.statementStart(15148)
            }
Coverage.statementStart(15149)
        }
        fun borrowFromNext(idx: Int) {
Coverage.funStart(15150)
            val child = C[idx]!!
Coverage.statementStart(15151)
            val sibling = C[idx + 1]!!
Coverage.statementStart(15152)
            child.keys[child.n] = keys[idx]
Coverage.statementStart(15153)
            if (!child.leaf) {
Coverage.ifStart(15154)
                child.C[child.n + 1] = sibling.C[0]
Coverage.statementStart(15155)
            }
Coverage.statementStart(15156)
            keys[idx] = sibling.keys[0]
Coverage.statementStart(15157)
            for (i in 1 until sibling.n) {
Coverage.forLoopStart(15158)
                sibling.keys[i - 1] = sibling.keys[i]
Coverage.statementStart(15159)
            }
Coverage.statementStart(15160)
            if (!sibling.leaf) {
Coverage.ifStart(15161)
                for (i in 1 until sibling.n + 1) {
Coverage.forLoopStart(15162)
                    sibling.C[i - 1] = sibling.C[i]
Coverage.statementStart(15163)
                }
Coverage.statementStart(15164)
            }
Coverage.statementStart(15165)
            child.n++
Coverage.statementStart(15166)
            sibling.n--
Coverage.statementStart(15167)
        }
        fun merge(idx: Int) {
Coverage.funStart(15168)
            val child = C[idx]!!
Coverage.statementStart(15169)
            val sibling = C[idx + 1]!!
Coverage.statementStart(15170)
            child.keys[t - 1] = keys[idx]
Coverage.statementStart(15171)
            for (i in 0 until sibling.n) {
Coverage.forLoopStart(15172)
                child.keys[i + t] = sibling.keys[i]
Coverage.statementStart(15173)
            }
Coverage.statementStart(15174)
            if (!child.leaf) {
Coverage.ifStart(15175)
                for (i in 0 until sibling.n + 1) {
Coverage.forLoopStart(15176)
                    child.C[i + t] = sibling.C[i]
Coverage.statementStart(15177)
                }
Coverage.statementStart(15178)
            }
Coverage.statementStart(15179)
            for (i in idx + 1 until n) {
Coverage.forLoopStart(15180)
                keys[i - 1] = keys[i]
Coverage.statementStart(15181)
            }
Coverage.statementStart(15182)
            for (i in idx + 2 until n + 1) {
Coverage.forLoopStart(15183)
                C[i - 1] = C[i]
Coverage.statementStart(15184)
            }
Coverage.statementStart(15185)
            child.n += sibling.n + 1
Coverage.statementStart(15186)
            n--
Coverage.statementStart(15187)
            sibling.free()
Coverage.statementStart(15188)
        }
        fun forEach(action: (KEY) -> Unit) {
Coverage.funStart(15189)
            for (i in 0 until n) {
Coverage.forLoopStart(15190)
                if (!leaf) {
Coverage.ifStart(15191)
                    C[i]!!.forEach(action)
Coverage.statementStart(15192)
                }
Coverage.statementStart(15193)
                action(keys[i] as KEY)
Coverage.statementStart(15194)
            }
Coverage.statementStart(15195)
            if (!leaf) {
Coverage.ifStart(15196)
                C[n]!!.forEach(action)
Coverage.statementStart(15197)
            }
Coverage.statementStart(15198)
        }
        fun search(k: KEY): Boolean {
Coverage.funStart(15199)
            var i = 0
Coverage.statementStart(15200)
            while (i < n && k > (keys[i] as KEY)) {
Coverage.whileLoopStart(15201)
                i++
Coverage.statementStart(15202)
            }
Coverage.statementStart(15203)
            if ((keys[i] as KEY) == k) {
Coverage.ifStart(15204)
                return true
            } else if (leaf) {
Coverage.statementStart(15205)
                return false
            } else {
Coverage.statementStart(15206)
                return C[i]!!.search(k)
            }
Coverage.statementStart(15207)
        }
        fun insertNonFull(k: KEY, onCreate: () -> Unit = {}, onExists: (KEY) -> Unit = {}) {
Coverage.funStart(15208)
            var i = n - 1
Coverage.statementStart(15209)
            var found = false
Coverage.statementStart(15210)
            for (i in 0 until n) {
Coverage.forLoopStart(15211)
                if (keys[i] as KEY == k) {
Coverage.ifStart(15212)
                    onExists(keys[i] as KEY)
Coverage.statementStart(15213)
                    found = true
Coverage.statementStart(15214)
                    break
                }
Coverage.statementStart(15215)
            }
Coverage.statementStart(15216)
            if (!found) {
Coverage.ifStart(15217)
                if (leaf) {
Coverage.ifStart(15218)
                    while (i >= 0 && (keys[i] as KEY > k)) {
Coverage.whileLoopStart(15219)
                        keys[i + 1] = keys[i]
Coverage.statementStart(15220)
                        i--
Coverage.statementStart(15221)
                    }
Coverage.statementStart(15222)
                    keys[i + 1] = k
Coverage.statementStart(15223)
                    n++
Coverage.statementStart(15224)
                } else {
Coverage.ifStart(15225)
                    while (i >= 0 && (keys[i] as KEY) > k) {
Coverage.whileLoopStart(15226)
                        i--
Coverage.statementStart(15227)
                    }
Coverage.statementStart(15228)
                    if (C[i + 1]!!.n == 2 * t - 1) {
Coverage.ifStart(15229)
                        splitChild(i + 1, C[i + 1]!!)
Coverage.statementStart(15230)
                        if ((keys[i + 1] as KEY) < k) {
Coverage.ifStart(15231)
                            i++
Coverage.statementStart(15232)
                        }
Coverage.statementStart(15233)
                    }
Coverage.statementStart(15234)
                    C[i + 1]!!.insertNonFull(k, onCreate, onExists)
Coverage.statementStart(15235)
                }
Coverage.statementStart(15236)
            }
Coverage.statementStart(15237)
        }
        fun splitChild(i: Int, y: MySetKEYBTreeNodeGUSE) {
Coverage.funStart(15238)
            val z = MySetKEYBTreeNodeGUSE(y.t, y.leaf)
Coverage.statementStart(15239)
            z.n = t - 1
Coverage.statementStart(15240)
            for (j in 0 until t - 1) {
Coverage.forLoopStart(15241)
                z.keys[j] = y.keys[j + t]
Coverage.statementStart(15242)
            }
Coverage.statementStart(15243)
            if (leaf == false) {
Coverage.ifStart(15244)
                for (j in 0 until t) {
Coverage.forLoopStart(15245)
                    z.C[j] = y.C[j + t]
Coverage.statementStart(15246)
                }
Coverage.statementStart(15247)
            }
Coverage.statementStart(15248)
            y.n = t - 1
Coverage.statementStart(15249)
            var j = n
Coverage.statementStart(15250)
            while (j >= i + 1) {
Coverage.whileLoopStart(15251)
                C[j + 1] = C[j]
Coverage.statementStart(15252)
                j--
Coverage.statementStart(15253)
            }
Coverage.statementStart(15254)
            C[i + 1] = z
Coverage.statementStart(15255)
            j = n - 1
Coverage.statementStart(15256)
            while (j >= i) {
Coverage.whileLoopStart(15257)
                keys[j + 1] = keys[j]
Coverage.statementStart(15258)
                j--
Coverage.statementStart(15259)
            }
Coverage.statementStart(15260)
            keys[i] = y.keys[t - 1]
Coverage.statementStart(15261)
            n++
Coverage.statementStart(15262)
        }
    }
    fun add(k: KEY, onCreate: () -> Unit = {}, onExists: (KEY) -> Unit = {}) {
Coverage.funStart(15263)
        if (root == null) {
Coverage.ifStart(15264)
            root = MySetKEYBTreeNodeGUSE(t, true)
Coverage.statementStart(15265)
            root!!.keys[0] = k
Coverage.statementStart(15266)
            root!!.n = 1
Coverage.statementStart(15267)
            size++
Coverage.statementStart(15268)
            onCreate()
Coverage.statementStart(15269)
        } else if (root!!.n == 2 * t - 1) {
Coverage.ifStart(15270)
            val s = MySetKEYBTreeNodeGUSE(t, false)
Coverage.statementStart(15271)
            s.C[0] = root
Coverage.statementStart(15272)
            s.splitChild(0, root!!)
Coverage.statementStart(15273)
            var i = 0
Coverage.statementStart(15274)
            if ((s.keys[0] as KEY) < k) {
Coverage.ifStart(15275)
                i++
Coverage.statementStart(15276)
            }
Coverage.statementStart(15277)
            s.C[i]!!.insertNonFull(k, {
Coverage.statementStart(15278)
                size++
Coverage.statementStart(15279)
                onCreate()
Coverage.statementStart(15280)
            }, onExists)
Coverage.statementStart(15281)
            root = s
Coverage.statementStart(15282)
        } else {
Coverage.ifStart(15283)
            root!!.insertNonFull(k, {
Coverage.statementStart(15284)
                size++
Coverage.statementStart(15285)
                onCreate()
Coverage.statementStart(15286)
            }, onExists)
Coverage.statementStart(15287)
        }
Coverage.statementStart(15288)
    }
    fun forEach(action: (KEY) -> Unit) {
Coverage.funStart(15289)
        if (root != null) {
Coverage.ifStart(15290)
            root!!.forEach(action)
Coverage.statementStart(15291)
        }
Coverage.statementStart(15292)
    }
    fun contains(k: KEY): Boolean {
Coverage.funStart(15293)
        if (root == null) {
Coverage.ifStart(15294)
            return false
        } else {
Coverage.statementStart(15295)
            return root!!.search(k)
        }
Coverage.statementStart(15296)
    }
    fun remove(k: KEY): KEY? {
Coverage.funStart(15297)
        if (root != null) {
Coverage.ifStart(15298)
            val res = root!!.remove(k)
Coverage.statementStart(15299)
            if (res != null) {
Coverage.ifStart(15300)
                size--
Coverage.statementStart(15301)
            }
Coverage.statementStart(15302)
            if (root!!.n == 0) {
Coverage.ifStart(15303)
                val tmp = root!!
Coverage.statementStart(15304)
                if (root!!.leaf) {
Coverage.ifStart(15305)
                    root == null
Coverage.statementStart(15306)
                } else {
Coverage.ifStart(15307)
                    root = root!!.C[0]
Coverage.statementStart(15308)
                }
Coverage.statementStart(15309)
                tmp.free()
Coverage.statementStart(15310)
            }
Coverage.statementStart(15311)
            return res
        }
Coverage.statementStart(15312)
        return null
    }
    fun appendAssumeSorted(key: KEY) {
Coverage.funStart(15313)
        add(key, {}, {})
Coverage.statementStart(15314)
    }
    fun iterator(): Iterator<KEY> {
Coverage.funStart(15315)
        if (root != null) {
Coverage.ifStart(15316)
            return root!!.iterator()
        } else {
Coverage.statementStart(15317)
            return EmptyIteratorGUSE()
        }
Coverage.statementStart(15318)
    }
    class EmptyIteratorGDEF : Iterator<KEY> {
        override fun hasNext() = false
        override fun next(): KEY = throw Exception("unreachable")
    }
}
