package lupos.s00misc
internal object SanityCheckOff {
    internal inline fun println(crossinline s: () -> Any?) {}
    internal inline operator fun invoke(crossinline action: () -> Unit) {}
    /*suspend*/ internal inline fun suspended(crossinline action: /*suspend*/ () -> Unit) {}
    internal inline fun <T> helper(crossinline action: () -> Unit): T? = null
    internal inline fun check(crossinline value: () -> Boolean, crossinline msg: () -> String) {}
    internal inline fun check(crossinline value: () -> Boolean) {}
    internal inline fun checkUnreachable(): Nothing = throw UnreachableException()
}
