package lupos.s00misc

import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze
import lupos.s00misc.Coverage
import lupos.s04logicalOperators.Query

class ThreadSafeMutableAny<T>(val value: T) {
    val global_value = AtomicReference(value.freeze())
    fun get(): T {
        return global_value.value
    }

    fun set(value: T) {
        global_value.value = value.freeze()
    }
}
