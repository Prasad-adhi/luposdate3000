package lupos.s00misc

import lupos.s00misc.Coverage

class MyMapDoubleIntBinaryTree() {
    @JvmField
    var keys = MySetDouble()
    @JvmField
    var values = MyListInt()
    var size: Int = 0
        get() = keys.size

    constructor(data: Pair<Double, Int>) : this() {
        set(data.first, data.second)
    }

    inline operator fun get(key: Double): Int? {
        var res: Int? = null
        keys.find(key, { res = values[it] })
        return res
    }

    inline operator fun set(key: Double, value: Int) {
        keys.add(key, { values.add(it, value) }, { values[it] = value })
    }

    inline fun getOrCreate(key: Double, crossinline onCreate: () -> Int): Int {
        var value: Int? = null
        keys.add(key, {
            value = onCreate()
            values.add(it, value!!)
        }, {
            value = values[it]
        })
        return value!!
    }

    fun appendAssumeSorted(key: Double, value: Int): Int {
        keys.add(key)
        values.add(value)
        return value
    }

    fun clear() {
        keys.clear()
        values.clear()
    }

    inline fun iterator() = MyMapDoubleIntBinaryTreeIterator(this)
    class MyMapDoubleIntBinaryTreeIterator(val data: MyMapDoubleIntBinaryTree) {
        var index = 0
        fun hasNext() = index < data.values.size
        fun next() = data.keys.data[index++]
        fun value() = data.values[index - 1]
    }

    fun safeToFile(filename: String) {
        File(filename).dataOutputStream { out ->
            out.writeInt(size)
            for (i in 0 until size) {
                out.writeDouble(keys.data[i])
            }
            for (i in 0 until size) {
                out.writeInt(values[i])
            }
        }
    }

    fun readFromFile(filename: String) {
        File(filename).dataInputStream { fis ->
            var size = fis.readInt()
            for (i in 0 until size) {
                keys.appendAssumeSorted(fis.readDouble())
            }
            for (i in 0 until size) {
                values.add(fis.readInt())
            }
        }
    }
}
