package lupos.s00misc

/* this File is autogenerated by generate-buildfile.kts */
/* DO NOT MODIFY DIRECTLY */
/* Substitutions :: Int,,,IntArray, */
class MyListInt {
    @JvmField
    var size = 0
    @JvmField
    var capacity = 10
    @JvmField
    var data: IntArray

    inline fun reserve(capacity: Int) {
        if (this.capacity < capacity) {
            this.capacity = capacity
            val tmp = IntArray(capacity)
            data.copyInto(tmp)
            data = tmp
        }
    }

    constructor() {
        data = IntArray(capacity)
    }

    constructor(value: Int) {
        data = IntArray(capacity)
        data[size++] = value
    }

    constructor(initialCapacity: Int, init: (Int) -> Int) {
        capacity = initialCapacity
        size = capacity
        data = IntArray(capacity) { init(it) }
    }

    fun clear() {
        size = 0
    }

    fun add(value: Int) {
        if (size + 1 >= capacity) {
            reserve(capacity * 2)
        }
        data[size++] = value
    }

    inline operator fun get(idx: Int) = data.get(idx) as Int
    inline operator fun set(idx: Int, key: Int) = data.set(idx, key)
    fun remove(value: Int): Boolean {
        for (idx in 0 until size) {
            if (data[idx] == value) {
                removeAt(idx)
                return true
            }
        }
        return false
    }

    fun removeAt(idx: Int): Int {
        val res = data[idx]
        require(idx < size)
        for (i in idx until size) {
            data[i] = data[i + 1]
        }
        size--
        return res as Int
    }

    fun add(idx: Int, value: Int) {
        if (size + 1 >= capacity) {
            reserve(capacity * 2)
        }
        if (idx < size) {
            size++
            for (i in size downTo idx + 1) {
                data[i] = data[i - 1]
            }
            data[idx] = value
        } else {
            data[size++] = value
        }
    }

    inline operator fun iterator() = MyListIntIterator(this)
    class MyListIntIterator(@JvmField val data: MyListInt) : Iterator<Int> {
        var index = 0
        override fun hasNext() = index < data.size
        override fun next() = data.data[index++] as Int
    }
}
