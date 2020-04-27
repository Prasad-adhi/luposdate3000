package lupos.s00misc

import lupos.s00misc.Coverage

class MySetDoubleBinary {
    @JvmField
    var data = MyListDouble()
    var size: Int = 0
        get() = data.size

    inline fun clear() {
        data.clear()
    }

    operator fun iterator(): Iterator<Double> {
        return data.iterator()
    }

    constructor() {
    }

    constructor(value: Double) : this() {
        data.add(value)
    }

    fun appendAssumeSorted(value: Double) {
        data.add(value)
    }

    inline fun internal(value: Double, crossinline onCreate: (it: Int) -> Unit = {}, crossinline onExists: (it: Int) -> Unit = {}) {
        if (data.size == 0) {
            onCreate(0)
        } else if (data.size == 1) {
            if (data[0] < value) {
                onCreate(1)
            } else if (data[0] > value) {
                onCreate(0)
            } else {
                onExists(0)
            }
        } else {
            var l = 0
            var r = data.size - 1
            while (r - l > 1) {
                var m = (r + l) / 2
                if (data[m] < value) {
                    l = m
                } else if (data[m] > value) {
                    r = m
                } else {
                    onExists(m)
                    return
                }
            }
            if (data[r] < value) {
                onCreate(r + 1)
            } else if (data[l] > value) {
                onCreate(l)
            } else if (data[r] > value && data[l] < value) {
                onCreate(r)
            } else if (data[l] == value) {
                onExists(l)
            } else {
                onExists(r)
            }
        }
    }

    inline fun remove(value: Double, crossinline onExists: (it: Int) -> Unit = {}) {
        internal(value, {
        }, {
            onExists(it)
            data.removeAt(it)
        })
    }

    inline fun contains(value: Double): Boolean {
        var res = false
        internal(value, {
        }, {
            res = true
        })
        return res
    }

    inline fun find(value: Double, crossinline onExists: (it: Int) -> Unit) {
        internal(value, {
        }, {
            onExists(it)
        })
    }

    inline fun add(value: Double, crossinline onCreate: (it: Int) -> Unit = {}, crossinline onExists: (it: Int) -> Unit = {}) {
        internal(value, {
            data.add(it, value)
            onCreate(it)
        }, {
            onExists(it)
        })
    }

    inline fun toList(): MyListDouble {
        return data
    }
}
