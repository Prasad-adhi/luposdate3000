package lupos.s00misc
import lupos.s00misc.Coverage
/* this File is autogenerated by generate-buildfile.kts */
/* DO NOT MODIFY DIRECTLY */


/* Substitutions :: Long,Generic,<Generic>,<Generic>,,<Generic>,Long,Generic */
class MyMapLongGenericBinaryTree<Generic>() {
    @JvmField
    var keys = MySetLongBinaryTree()
    @JvmField
    var values = MyListGeneric<Generic>()
    var size: Int = 0
        get() = keys.size

    inline fun reserve(capacity: Int) {
        keys.reserve(capacity)
        values.reserve(capacity)
    }

    constructor(data: Pair<Long, Generic>) : this() {
        set(data.first, data.second)
    }

    inline operator fun get(key: Long): Generic? {
        var res: Generic? = null
        keys.find(key, { res = values[it] })
        return res
    }

    inline operator fun set(key: Long, value: Generic) {
        keys.add(key, { values.add(it, value) }, { values[it] = value })
    }

    inline fun getOrCreate(key: Long, crossinline onCreate: () -> Generic): Generic {
        var value: Generic? = null
        keys.add(key, {
            value = onCreate()
            values.add(it, value!!)
        }, {
            value = values[it]
        })
        return value!!
    }

    fun withFastInitializer(action: (MyMapLongGenericBinaryTree<Generic>) -> Unit) = action(this)
    fun appendAssumeSorted(key: Long, value: Generic): Generic {
        keys.add(key)
        values.add(value)
        return value
    }

    fun clear() {
        keys.clear()
        values.clear()
    }

    inline fun iterator() = MyMapLongGenericBinaryTreeIterator(this)
    inline fun forEach(crossinline action: (Long, Generic) -> Unit) {
        val iteratorK = keys.iterator()
        val iteratorV = values.iterator()
        while (iteratorK.hasNext()) {
            val k = iteratorK.next()
            val v = iteratorV.next()
            action(k, v)
        }
    }

    class MyMapLongGenericBinaryTreeIterator<Generic>(val data: MyMapLongGenericBinaryTree<Generic>) {
        var index = 0
        fun hasNext() = index < data.values.size
        fun next() = data.keys.data[index++]
        fun value() = data.values[index - 1]
    }

    fun safeToFile(filename: String) {
        throw Exception("not Implemented")
    }
}
