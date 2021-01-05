package lupos.s04logicalOperators.iterator
import lupos.s03resultRepresentation.ResultSetDictionaryExt
import kotlin.jvm.JvmField
object ColumnIteratorMultiValue {
    operator fun invoke(values: IntArray): ColumnIteratorMultiValue3 = ColumnIteratorMultiValue3(values, values.size)
    operator fun invoke(values: IntArray, size: Int): ColumnIteratorMultiValue3 = ColumnIteratorMultiValue3(values, size)
    operator fun invoke(values: MutableList<Int>): ColumnIteratorMultiValue1 = ColumnIteratorMultiValue1(values)
    operator fun invoke(iterator: Iterator<Int>): ColumnIteratorMultiValue2 = ColumnIteratorMultiValue2(iterator)
}
class ColumnIteratorMultiValue1(@JvmField public val values: MutableList<Int>) : ColumnIterator() {
    @JvmField
    var index: Int = 0
    override /*suspend*/ fun close() {
        index = values.size
    }
    override /*suspend*/ fun next(): Int {
        return if (index == values.size) {
            ResultSetDictionaryExt.nullValue
        } else {
            values[index++]
        }
    }
}
class ColumnIteratorMultiValue3(@JvmField public val values: IntArray, @JvmField public val size: Int) : ColumnIterator() {
    @JvmField
    var index: Int = 0
    override /*suspend*/ fun close() {
        index = size
    }
    override /*suspend*/ fun next(): Int {
        return if (index == size) {
            ResultSetDictionaryExt.nullValue
        } else {
            values[index++]
        }
    }
}
class ColumnIteratorMultiValue2(@JvmField public val iterator: Iterator<Int>) : ColumnIterator() {
    @JvmField
    var label: Int = 1
    override /*suspend*/ fun close() {
        label = 0
    }
    override /*suspend*/ fun next(): Int {
        return if (label != 0 && iterator.hasNext()) {
            iterator.next()
        } else {
            ResultSetDictionaryExt.nullValue
        }
    }
}
