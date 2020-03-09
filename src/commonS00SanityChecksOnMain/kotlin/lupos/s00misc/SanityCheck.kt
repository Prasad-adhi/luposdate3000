package lupos.s00misc

object SanityCheck {
    inline fun <T> helper(action: () -> T): T? = action()
    inline fun check(value: () -> Boolean) = require(value())
    inline fun checkFalse(value: () -> Boolean) = require(!value())
    inline fun <T> checkEQ(a: () -> T, b: () -> T) = require(a() == b())
    inline fun <T> checkNEQ(a: () -> T, b: () -> T) = require(a() != b())
    inline fun <T> checkNULL(value: () -> T) = require(value() == null)
    inline fun <T> checkNNULL(value: () -> T) = require(value() != null)
    inline fun checkUnreachable(): Nothing = throw Exception("this should be unreachable")
}
