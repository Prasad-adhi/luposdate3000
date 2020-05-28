package lupos.s00misc
import lupos.s00misc.Coverage
/* Substitutions :: VALUE,GDEF,GUSE */
class MySetVALUEBinaryTreeGDEF {
    @JvmField
    var data = MyListVALUEGUSE()
    var size: Int = 0
        get() = data.size
    inline fun clear() {
Coverage.funStart(15319)
        data.clear()
Coverage.statementStart(15320)
    }
    operator fun iterator(): Iterator<VALUE> {
Coverage.funStart(15321)
        return data.iterator()
    }
    fun forEach(action: (VALUE) -> Unit) {
Coverage.funStart(15322)
        var it = iterator()
Coverage.statementStart(15323)
        while (it.hasNext()) {
Coverage.whileLoopStart(15324)
            val v = it.next()
Coverage.statementStart(15325)
            action(v)
Coverage.statementStart(15326)
        }
Coverage.statementStart(15327)
    }
    constructor() {
    }
    constructor(value: VALUE) : this() {
        data.add(value)
    }
    fun appendAssumeSorted(value: VALUE) {
Coverage.funStart(15328)
        data.add(value)
Coverage.statementStart(15329)
    }
    inline fun reserve(capacity: Int) {
Coverage.funStart(15330)
        data.reserve(capacity)
Coverage.statementStart(15331)
    }
    inline fun internal(value: VALUE, crossinline onCreate: (it: Int) -> Unit = {}, crossinline onExists: (it: Int) -> Unit = {}) {
Coverage.funStart(15332)
        if (data.size == 0) {
Coverage.ifStart(15333)
            onCreate(0)
Coverage.statementStart(15334)
        } else if (data.size == 1) {
Coverage.ifStart(15335)
            val d = data[0]
Coverage.statementStart(15336)
            if (d < value) {
Coverage.ifStart(15337)
                onCreate(1)
Coverage.statementStart(15338)
            } else if (d > value) {
Coverage.ifStart(15339)
                onCreate(0)
Coverage.statementStart(15340)
            } else {
Coverage.ifStart(15341)
                onExists(0)
Coverage.statementStart(15342)
            }
Coverage.statementStart(15343)
        } else {
Coverage.ifStart(15344)
            var l = 0
Coverage.statementStart(15345)
            var r = data.size - 1
Coverage.statementStart(15346)
            while (r - l > 1) {
Coverage.whileLoopStart(15347)
                var m = (r + l) / 2
Coverage.statementStart(15348)
                val d = data[m]
Coverage.statementStart(15349)
                if (d < value) {
Coverage.ifStart(15350)
                    l = m
Coverage.statementStart(15351)
                } else if (d > value) {
Coverage.ifStart(15352)
                    r = m
Coverage.statementStart(15353)
                } else {
Coverage.ifStart(15354)
                    onExists(m)
Coverage.statementStart(15355)
                    return
                }
Coverage.statementStart(15356)
            }
Coverage.statementStart(15357)
            val dl = data[l]
Coverage.statementStart(15358)
            val dr = data[r]
Coverage.statementStart(15359)
            if (dr < value) {
Coverage.ifStart(15360)
                onCreate(r + 1)
Coverage.statementStart(15361)
            } else if (dl > value) {
Coverage.ifStart(15362)
                onCreate(l)
Coverage.statementStart(15363)
            } else if (dl == value) {
Coverage.ifStart(15364)
                onExists(l)
Coverage.statementStart(15365)
            } else if (dr > value && dl < value) {
Coverage.ifStart(15366)
                onCreate(r)
Coverage.statementStart(15367)
            } else {
Coverage.ifStart(15368)
                onExists(r)
Coverage.statementStart(15369)
            }
Coverage.statementStart(15370)
        }
Coverage.statementStart(15371)
    }
    inline fun remove(value: VALUE, crossinline onExists: (it: Int) -> Unit = {}) {
Coverage.funStart(15372)
        internal(value, {
Coverage.statementStart(15373)
        }, {
Coverage.statementStart(15374)
            onExists(it)
Coverage.statementStart(15375)
            data.removeAt(it)
Coverage.statementStart(15376)
        })
Coverage.statementStart(15377)
    }
    inline fun contains(value: VALUE): Boolean {
Coverage.funStart(15378)
        var res = false
Coverage.statementStart(15379)
        internal(value, {
Coverage.statementStart(15380)
        }, {
Coverage.statementStart(15381)
            res = true
Coverage.statementStart(15382)
        })
Coverage.statementStart(15383)
        return res
    }
    inline fun find(value: VALUE, crossinline onExists: (it: Int) -> Unit) {
Coverage.funStart(15384)
        internal(value, {
Coverage.statementStart(15385)
        }, {
Coverage.statementStart(15386)
            onExists(it)
Coverage.statementStart(15387)
        })
Coverage.statementStart(15388)
    }
    inline fun add(value: VALUE, crossinline onCreate: (it: Int) -> Unit = {}, crossinline onExists: (it: Int) -> Unit = {}) {
Coverage.funStart(15389)
        if (data.size > 0 && value > data[data.size - 1]) {
Coverage.ifStart(15390)
            val it = data.size
Coverage.statementStart(15391)
            data.add(it, value)
Coverage.statementStart(15392)
            onCreate(it)
Coverage.statementStart(15393)
        } else {
Coverage.ifStart(15394)
            internal(value, {
Coverage.statementStart(15395)
                data.add(it, value)
Coverage.statementStart(15396)
                onCreate(it)
Coverage.statementStart(15397)
            }, {
Coverage.statementStart(15398)
                onExists(it)
Coverage.statementStart(15399)
            })
Coverage.statementStart(15400)
        }
Coverage.statementStart(15401)
    }
    inline fun toList(): MyListVALUEGUSE {
Coverage.funStart(15402)
        return data
    }
}
