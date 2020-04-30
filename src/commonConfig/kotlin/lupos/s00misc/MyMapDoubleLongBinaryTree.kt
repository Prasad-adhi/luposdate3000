/* this File is autogenerated by generate-buildfile.kts */
/* DO NOT MODIFY DIRECTLY */
package lupos.s00misc

import lupos.s00misc.Coverage

/* Substitutions :: Double,Long,,,,,Double,Long */
class MyMapDoubleLongBinaryTree() {
    @JvmField
    var keys = MySetDoubleBinaryTree()
    @JvmField
    var values = MyListLong()
    var size: Int = 0
        get() = keys.size

    inline fun reserve(capacity: Int) {
        keys.reserve(capacity)
        values.reserve(capacity)
    }

    constructor(data: Pair<Double, Long>) : this() {
        set(data.first, data.second)
    }

    inline operator fun get(key: Double): Long? {
        var res: Long? = null
        keys.find(key, { res = values[it] })
        return res
    }

    inline operator fun set(key: Double, value: Long) {
        keys.add(key, { values.add(it, value) }, { values[it] = value })
    }

    inline fun getOrCreate(key: Double, crossinline onCreate: () -> Long): Long {
        var value: Long? = null
        keys.add(key, {
            value = onCreate()
            values.add(it, value!!)
        }, {
            value = values[it]
        })
        return value!!
    }

    fun appendAssumeSorted(key: Double, value: Long): Long {
        keys.add(key)
        values.add(value)
        return value
    }

    fun clear() {
        keys.clear()
        values.clear()
    }

    inline fun iterator() = MyMapDoubleLongBinaryTreeIterator(this)
    inline fun forEach(crossinline action: (Double, Long) -> Unit) {
        val iteratorK = keys.iterator()
        val iteratorV = values.iterator()
        while (iteratorK.hasNext()) {
            val k = iteratorK.next()
            val v = iteratorV.next()
            action(k, v)
        }
    }

    class MyMapDoubleLongBinaryTreeIterator(val data: MyMapDoubleLongBinaryTree) {
        var index = 0
        fun hasNext() = index < data.values.size
        fun next() = data.keys.data[index++]
        fun value() = data.values[index - 1]
    }

    fun safeToFile(filename: String) {
        
        File(filename).dataOutputStream { out ->
            out.writeInt(size)
            val iteratorK = keys.iterator()
            while (iteratorK.hasNext()) {
                out.writeDouble(iteratorK.next())
            }
            val iteratorV = values.iterator()
            while (iteratorV.hasNext()) {
                out.writeLong(iteratorV.next())
            }
        }
        
    }

    fun loadFromFile(filename: String) {
        
        File(filename).dataInputStream { fis ->
            var size = fis.readInt()
            for (i in 0 until size) {
                keys.appendAssumeSorted(fis.readDouble())
            }
            for (i in 0 until size) {
                values.add(fis.readLong())
            }
        }
        
    }
}
