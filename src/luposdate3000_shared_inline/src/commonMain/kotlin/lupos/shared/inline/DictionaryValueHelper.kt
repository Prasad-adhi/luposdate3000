/*
 * This file is part of the Luposdate3000 distribution (https://github.com/luposdate3000/luposdate3000).
 * Copyright (c) 2020-2021, Institute of Information Systems (Benjamin Warnke and contributors of LUPOSDATE3000), University of Luebeck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package lupos.shared.inline
import lupos.shared.IMyInputStream
import lupos.shared.IMyOutputStream
import lupos.shared.dynamicArray.ByteArrayWrapper
import lupos.shared.inline.dynamicArray.ByteArrayWrapperExt
/*


DictionaryValueHelper.DictionaryValueTypeArray

DictionaryValueType

import lupos.shared.DictionaryValueHelper
import lupos.shared.DictionaryValueType
import lupos.shared.DictionaryValueTypeArray

*/

internal object DictionaryValueHelperInt {
    public const val booleanTrueValue: Int = (0x00000000) /*lowest 5 values*/ /*required to be 0 for_ truth table loopups*/
    public const val booleanFalseValue: Int = (0x00000001) /*lowest 5 values*/ /*required to be 1 for_ truth table loopups*/
    public const val errorValue: Int = (0x00000002) /*lowest 5 values*/ /*required to be 2 for_ truth table loopups*/
    public const val undefValue: Int = (0x00000003) /*lowest 5 values*/
    public const val nullValue: Int = (0x00000004) /*lowest 5 values*/ /*symbol for no more results, previously 'null'*/
    public const val flagLocal: Int = 0x40000000.toInt()
    public const val flagNoBNode: Int = 0x20000000.toInt()
    public const val maskValue: Int = 0x1FFFFFFF.toInt()
    public const val NULL: Int = 0
    public const val FIRST_BNODE: Int = 5
    internal inline fun DictionaryValueTypeArray(size: Int, init: (Int) -> Int): IntArray = IntArray(size) { init(it) }
    internal inline fun DictionaryValueTypeArray(size: Int): IntArray = IntArray(size)
    internal inline fun DictionaryValueTypeArrayOf() = intArrayOf()
    internal inline fun DictionaryValueTypeArrayOf(a: Int) = intArrayOf(a)
    internal inline fun DictionaryValueTypeArrayOf(a: Int, b: Int) = intArrayOf(a, b)
    internal inline fun DictionaryValueTypeArrayOf(a: Int, b: Int, c: Int) = intArrayOf(a, b, c)
    internal inline fun isLocalValue(value: Int): Boolean = (value and flagLocal) == flagLocal
    internal inline fun isBnode(value: Int): Boolean = (value and flagNoBNode) != flagNoBNode
    internal inline fun toByteArray(buffer: ByteArray, off: Int, value: Int) = ByteArrayHelper.writeInt4(buffer, off, value)
    internal inline fun toByteArrayX(buffer: ByteArrayWrapper, off: Int, value: Int, count: Int) = ByteArrayHelper.writeIntX(ByteArrayWrapperExt.getBuf(buffer), off, value, count)
    internal inline fun toByteArrayX(buffer: ByteArray, off: Int, value: Int, count: Int) = ByteArrayHelper.writeIntX(buffer, off, value, count)
    internal inline fun toByteArray(buffer: ByteArrayWrapper, off: Int, value: Int) = ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), off, value)
    internal inline fun fromByteArray(buffer: ByteArray, off: Int): Int = ByteArrayHelper.readInt4(buffer, off)
    internal inline fun fromByteArrayX(buffer: ByteArray, off: Int, bytes: Int): Int = ByteArrayHelper.readIntX(buffer, off, bytes)
    internal inline fun fromByteArray(buffer: ByteArrayWrapper, off: Int): Int = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
    internal inline fun fromByteArrayX(buffer: ByteArrayWrapper, off: Int, bytes: Int): Int = ByteArrayHelper.readIntX(ByteArrayWrapperExt.getBuf(buffer), off, bytes)
    internal inline fun getSize(): Int = 8
    internal inline fun toInt(value: Int): Int = value // adapter for places, where always Int are used
    internal inline fun fromInt(value: Int): Int = value // adapter for places, where always Int are used
    internal inline fun numberOfBytesUsed(value: Int): Int = (((32 + 7 - IntegerExt.numberOfLeadingZeros(value))) shr 3)
    internal inline fun fromStream(stream: IMyInputStream): Int = stream.readInt()
    internal inline fun toStream(stream: IMyOutputStream, value: Int) = stream.writeInt(value)
}
internal object DictionaryValueHelperLong {
    public const val booleanTrueValue: Long = (0x00000000) /*lowest 5 values*/ /*required to be 0 for_ truth table loopups*/
    public const val booleanFalseValue: Long = (0x00000001) /*lowest 5 values*/ /*required to be 1 for_ truth table loopups*/
    public const val errorValue: Long = (0x00000002) /*lowest 5 values*/ /*required to be 2 for_ truth table loopups*/
    public const val undefValue: Long = (0x00000003) /*lowest 5 values*/
    public const val nullValue: Long = (0x00000004) /*lowest 5 values*/ /*symbol for no more results, previously 'null'*/
    public const val flagLocal: Long = 0x40000000
    public const val flagNoBNode: Long = 0x20000000
    public const val maskValue: Long = 0x1FFFFFFF
    public const val NULL: Long = 0L
    public const val FIRST_BNODE: Long = 5L
    internal inline fun DictionaryValueTypeArray(size: Int, init: (Int) -> Long): LongArray = LongArray(size) { init(it) }
    internal inline fun DictionaryValueTypeArray(size: Int): LongArray = LongArray(size)
    internal inline fun DictionaryValueTypeArrayOf() = longArrayOf()
    internal inline fun DictionaryValueTypeArrayOf(a: Long) = longArrayOf(a)
    internal inline fun DictionaryValueTypeArrayOf(a: Long, b: Long) = longArrayOf(a, b)
    internal inline fun DictionaryValueTypeArrayOf(a: Long, b: Long, c: Long) = longArrayOf(a, b, c)
    internal inline fun isLocalValue(value: Long): Boolean = (value and flagLocal) == flagLocal
    internal inline fun isBnode(value: Long): Boolean = (value and flagNoBNode) != flagNoBNode
    internal inline fun toByteArray(buffer: ByteArray, off: Int, value: Long) = ByteArrayHelper.writeLong8(buffer, off, value)
    internal inline fun toByteArray(buffer: ByteArrayWrapper, off: Int, value: Long) = ByteArrayHelper.writeLong8(ByteArrayWrapperExt.getBuf(buffer), off, value)
    internal inline fun toByteArrayX(buffer: ByteArrayWrapper, off: Int, value: Long, count: Int) = ByteArrayHelper.writeLongX(ByteArrayWrapperExt.getBuf(buffer), off, value, count)
    internal inline fun toByteArrayX(buffer: ByteArray, off: Int, value: Long, count: Int) = ByteArrayHelper.writeLongX(buffer, off, value, count)
    internal inline fun fromByteArray(buffer: ByteArray, off: Int): Long = ByteArrayHelper.readLong8(buffer, off)
    internal inline fun fromByteArray(buffer: ByteArrayWrapper, off: Int): Long = ByteArrayHelper.readLong8(ByteArrayWrapperExt.getBuf(buffer), off)
    internal inline fun fromByteArrayX(buffer: ByteArrayWrapper, off: Int, bytes: Int): Long = ByteArrayHelper.readLongX(ByteArrayWrapperExt.getBuf(buffer), off, bytes)
    internal inline fun fromByteArrayX(buffer: ByteArray, off: Int, bytes: Int): Long = ByteArrayHelper.readLongX(buffer, off, bytes)
    internal inline fun getSize(): Int = 8
    internal inline fun toInt(value: Long): Int = value.toInt() // adapter for places, where always Int are used
    internal inline fun fromInt(value: Int): Long = value.toLong() // adapter for places, where always Int are used
    internal inline fun numberOfBytesUsed(value: Long): Int = (((64 + 7 - LongExt.numberOfLeadingZeros(value))) shr 3)
    internal inline fun fromStream(stream: IMyInputStream): Long = stream.readLong()
    internal inline fun toStream(stream: IMyOutputStream, value: Long) = stream.writeLong(value)
}
