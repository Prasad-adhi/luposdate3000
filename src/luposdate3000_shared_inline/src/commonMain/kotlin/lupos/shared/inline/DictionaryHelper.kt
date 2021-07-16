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

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import lupos.shared.DictionaryHelperLarge
import lupos.shared.DictionaryValueHelper
import lupos.shared.DictionaryValueType
import lupos.shared.ETripleComponentType
import lupos.shared.ETripleComponentTypeExt
import lupos.shared.SanityCheck
import lupos.shared.ValueBnode
import lupos.shared.ValueDateTime
import lupos.shared.ValueDecimal
import lupos.shared.ValueDefinition
import lupos.shared.ValueDouble
import lupos.shared.ValueFloat
import lupos.shared.ValueInteger
import lupos.shared.ValueIri
import lupos.shared.ValueLanguageTaggedLiteral
import lupos.shared.ValueSimpleLiteral
import lupos.shared.ValueTypedLiteral
import lupos.shared.XMLElement
import lupos.shared.dictionary.DictionaryExt
import lupos.shared.dynamicArray.ByteArrayWrapper
import lupos.shared.inline.dynamicArray.ByteArrayWrapperExt
internal object DictionaryHelper {
    @Suppress("NOTHING_TO_INLINE")
    internal inline fun helper_intToString(value: BigInteger): String {
        return helper_intCheckString(value.toString())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun helper_intCheckString(value: String): String {
        var v = value
        var negative = false
        if (v.startsWith('+')) {
            v = v.substring(1)
        } else if (v.startsWith('-')) {
            negative = true
            v = v.substring(1)
        }
        while (v.startsWith('0')) {
            v = v.substring(1)
        }
        if (v.length == 0) {
            v = "0"
        }
        if (negative) {
            v = '-' + v
        }
        return v
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun helper_intToByteArray(value: BigInteger): ByteArray {
        return helper_intToString(value).encodeToByteArray()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun helper_intFromString(value: String): BigInteger {
        return BigInteger.parseString(value, 10)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun helper_intFromByteArray(value: ByteArray): BigInteger {
        return helper_intFromString(value.decodeToString())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun helper_decimalToString(value: BigDecimal): String {
        return helper_decimalCheckString(value.toStringExpanded())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun helper_decimalCheckString(value: String): String {
        var v = value
        var negative = false
        if (v.startsWith('+')) {
            v = v.substring(1)
        } else if (v.startsWith('-')) {
            negative = true
            v = v.substring(1)
        }
        while (v.startsWith('0')) {
            v = v.substring(1)
        }
        if (v.contains('.')) {
            while (v.endsWith('0')) {
                v = v.substring(0, v.length - 1)
            }
        } else {
            v = v + '.'
        }
        if (v.endsWith(".")) {
            v = v + '0'
        }
        if (v.startsWith('.')) {
            v = '0' + v
        }
        if (negative) {
            v = '-' + v
        }
        return v
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun helper_decimalToByteArray(value: BigDecimal): ByteArray {
        return helper_decimalToString(value).encodeToByteArray()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun helper_decimalFromString(value: String): BigDecimal {
        return BigDecimal.parseString(value, 10)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun helper_decimalFromByteArray(value: ByteArray): BigDecimal {
        return helper_decimalFromString(value.decodeToString())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun headerSize(): Int = 1

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun headerEncode(buffer: ByteArrayWrapper, type: ETripleComponentType, flag: Int) {
        SanityCheck.check({ /*SOURCE_FILE_START*/"D:/ideaprojects/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:143"/*SOURCE_FILE_END*/ }, { (type and ETripleComponentTypeExt.values_mask) == type }, { "DictionaryHelper.headerEncode type is bad ${type.toString(16)} ... ${ETripleComponentTypeExt.values_mask.toString(16)} " })
        SanityCheck.check({ /*SOURCE_FILE_START*/"D:/ideaprojects/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:144"/*SOURCE_FILE_END*/ }, { (flag and ETripleComponentTypeExt.values_mask_inversed) == flag }, { "DictionaryHelper.headerEncode flag is bad" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"D:/ideaprojects/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:145"/*SOURCE_FILE_END*/ }, { (type or flag) <= 0xff }, { "DictionaryHelper.headerEncode can not be encoded in 1 byte" })
        ByteArrayHelper.writeInt1(ByteArrayWrapperExt.getBuf(buffer), 0, type or flag)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun headerDecodeType(buffer: ByteArrayWrapper): ETripleComponentType {
        val res = ByteArrayHelper.readInt1(ByteArrayWrapperExt.getBuf(buffer), 0) and ETripleComponentTypeExt.values_mask
        return res
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun headerDecodeFlag(buffer: ByteArrayWrapper): Int {
        val res = ByteArrayHelper.readInt1(ByteArrayWrapperExt.getBuf(buffer), 0) and ETripleComponentTypeExt.values_mask_inversed
        return res
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun errorToByteArray(buffer: ByteArrayWrapper) {
        ByteArrayWrapperExt.setSize(buffer, headerSize(), false)
        headerEncode(buffer, ETripleComponentTypeExt.ERROR, 0)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun undefToByteArray(buffer: ByteArrayWrapper) {
        ByteArrayWrapperExt.setSize(buffer, headerSize(), false)
        headerEncode(buffer, ETripleComponentTypeExt.UNDEF, 0)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun dateTimeToByteArray(buffer: ByteArrayWrapper, str: String) {
        DictionaryHelperLarge.dateTimeToByteArray(buffer, str)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun dateTimeToByteArray(buffer: ByteArrayWrapper) {
        val dateNow = DateHelper()
        DictionaryHelperLarge.dateTimeToByteArray(buffer, helper_intFromString(dateNow.year().toString()), dateNow.month(), dateNow.day(), dateNow.hours(), dateNow.minutes(), helper_decimalFromString(dateNow.seconds().toString()), -99, -99, false)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun dateTimeToByteArray(buffer: ByteArrayWrapper, year: BigInteger, month: Int, day: Int, hours: Int, minutes: Int, seconds: BigDecimal, timezoneHours: Int, timezoneMinutes: Int, hasTimeZone: Boolean) {
        DictionaryHelperLarge.dateTimeToByteArray(buffer, year, month, day, hours, minutes, seconds, timezoneHours, timezoneMinutes, hasTimeZone)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDateTime_Year(buffer: ByteArrayWrapper): BigInteger {
        if (headerDecodeFlag(buffer) == 0x80) {
            val componentAll = ByteArrayHelper.readLong7(ByteArrayWrapperExt.getBuf(buffer), headerSize())
            var year = ((componentAll shr 37) and 0x7FFFF)
            if ((componentAll and (1L shl 36)) != (1L shl 36)) {
                year = -year
            }
            return year.toBigInteger()
        } else {
            var off = 0
            off += headerSize()
            val l1 = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 4
            off += 4
            off += 4
            off += 4
            off += 4
            off += 4
            off += 4
            val buf1 = ByteArray(l1)
            ByteArrayWrapperExt.getBuf(buffer).copyInto(buf1, 0, off, off + l1)
            off += l1
            val l2 = ByteArrayWrapperExt.getSize(buffer) - l1 - headerSize() - 28
            off += l2
            SanityCheck.check({ /*SOURCE_FILE_START*/"D:/ideaprojects/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:214"/*SOURCE_FILE_END*/ }, { off == ByteArrayWrapperExt.getSize(buffer) }, { "$off vs ${ByteArrayWrapperExt.getSize(buffer)}" })
            val year = helper_intFromByteArray(buf1)
            return year
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDateTime_Month(buffer: ByteArrayWrapper): BigInteger {
        if (headerDecodeFlag(buffer) == 0x80) {
            val componentAll = ByteArrayHelper.readLong7(ByteArrayWrapperExt.getBuf(buffer), headerSize())
            return ((componentAll shr 32) and 0xF).toBigInteger()
        } else {
            var off = 0
            off += headerSize()
            off += 4
            val month = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            return BigInteger(month)
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDateTime_Day(buffer: ByteArrayWrapper): BigInteger {
        if (headerDecodeFlag(buffer) == 0x80) {
            val componentAll = ByteArrayHelper.readLong7(ByteArrayWrapperExt.getBuf(buffer), headerSize())
            return ((componentAll shr 27) and 0x1F).toBigInteger()
        } else {
            var off = 0
            off += headerSize()
            off += 4
            off += 4
            val day = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            return BigInteger(day)
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDateTime_Hours(buffer: ByteArrayWrapper): BigInteger {
        if (headerDecodeFlag(buffer) == 0x80) {
            val componentAll = ByteArrayHelper.readLong7(ByteArrayWrapperExt.getBuf(buffer), headerSize())
            val hours = (componentAll and 0x7FFFFFF) / (1000 * 60 * 60)
            return hours.toBigInteger()
        } else {
            var off = 0
            off += headerSize()
            off += 4
            off += 4
            off += 4
            val hours = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            return BigInteger(hours)
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDateTime_Minutes(buffer: ByteArrayWrapper): BigInteger {
        if (headerDecodeFlag(buffer) == 0x80) {
            val componentAll = ByteArrayHelper.readLong7(ByteArrayWrapperExt.getBuf(buffer), headerSize())
            val minutes = ((componentAll and 0x7FFFFFF) / (1000 * 60)) % 60
            return minutes.toBigInteger()
        } else {
            var off = 0
            off += headerSize()
            off += 4
            off += 4
            off += 4
            off += 4
            val minutes = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            return BigInteger(minutes)
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDateTime_Seconds(buffer: ByteArrayWrapper): BigDecimal {
        if (headerDecodeFlag(buffer) == 0x80) {
            val componentAll = ByteArrayHelper.readLong7(ByteArrayWrapperExt.getBuf(buffer), headerSize())
            val milliseconds = ((componentAll and 0x7FFFFFF)) % (60 * 1000)
            return milliseconds.toBigDecimal() / 1000
        } else {
            var off = 0
            off += headerSize()
            val l1 = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 4
            off += 4
            off += 4
            off += 4
            off += 4
            off += 4
            off += 4
            off += l1
            val l2 = ByteArrayWrapperExt.getSize(buffer) - l1 - headerSize() - 28
            val buf2 = ByteArray(l2)
            ByteArrayWrapperExt.getBuf(buffer).copyInto(buf2, 0, off, off + l2)
            buf2.copyInto(ByteArrayWrapperExt.getBuf(buffer), off)
            off += l2
            SanityCheck.check({ /*SOURCE_FILE_START*/"D:/ideaprojects/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:307"/*SOURCE_FILE_END*/ }, { off == ByteArrayWrapperExt.getSize(buffer) })
            val seconds = helper_decimalFromByteArray(buf2)
            return seconds
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDateTimeAsTyped_Content(buffer: ByteArrayWrapper): String {
        return DictionaryHelperLarge.byteArrayToDateTimeAsTyped_Content(buffer)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDateTime_TZ(buffer: ByteArrayWrapper): String {
        if (headerDecodeFlag(buffer) == 0x80) {
            return ""
        } else {
            var off = 0
            off += headerSize()
            off += 4
            off += 4
            off += 4
            off += 4
            off += 4
            val timezoneHours = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 4
            val timezoneMinutes = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            if (timezoneHours == 0 && timezoneMinutes == 0) {
                return "Z"
            }
            if (timezoneHours == -99 && timezoneMinutes == -99) {
                return ""
            }
            return "-${timezoneHours.toString().padStart(2, '0')}:${timezoneMinutes.toString().padStart(2, '0')}"
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDateTime_TimeZone(buffer: ByteArrayWrapper): String {
        if (headerDecodeFlag(buffer) == 0x80) {
            return ""
        } else {
            var off = 0
            off += headerSize()
            off += 4
            off += 4
            off += 4
            off += 4
            off += 4
            val timezoneHours = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 4
            val timezoneMinutes = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            if (timezoneHours == 0 && timezoneMinutes == 0) {
                return "\"PT0S\"^^<http://www.w3.org/2001/XMLSchema#dayTimeDuration>"
            }
            if (timezoneHours >= 0 && timezoneMinutes == 0) {
                return "\"-PT${timezoneHours}H\"^^<http://www.w3.org/2001/XMLSchema#dayTimeDuration>"
            }
            return ""
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun booleanToByteArray(buffer: ByteArrayWrapper, value: Boolean) {
        ByteArrayWrapperExt.setSize(buffer, headerSize(), false)
        if (value) {
            headerEncode(buffer, ETripleComponentTypeExt.BOOLEAN, 0x80)
        } else {
            headerEncode(buffer, ETripleComponentTypeExt.BOOLEAN, 0x00)
        }
        SanityCheck.check({ /*SOURCE_FILE_START*/"D:/ideaprojects/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:376"/*SOURCE_FILE_END*/ }, { byteArrayToBoolean(buffer) == value })
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToBoolean(buffer: ByteArrayWrapper): Boolean {
        val flag = headerDecodeFlag(buffer)
        SanityCheck.check({ /*SOURCE_FILE_START*/"D:/ideaprojects/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:382"/*SOURCE_FILE_END*/ }, { flag == 0x0 || flag == 0x80 }, { "0x${flag.toString(16)}" })
        return flag == 0x80
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun integerToByteArray(buffer: ByteArrayWrapper, value: String) {
        val buf1 = helper_intCheckString(value).encodeToByteArray()
        ByteArrayWrapperExt.setSize(buffer, headerSize() + buf1.size, false)
        headerEncode(buffer, ETripleComponentTypeExt.INTEGER, 0)
        buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun integerToByteArray(buffer: ByteArrayWrapper, value: BigInteger) {
        integerToByteArray(buffer, helper_intToString(value))
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToInteger_S(buffer: ByteArrayWrapper): String {
        val l1 = ByteArrayWrapperExt.getSize(buffer) - headerSize()
        val buf = ByteArray(l1)
        ByteArrayWrapperExt.getBuf(buffer).copyInto(buf, 0, headerSize(), headerSize() + l1)
        return buf.decodeToString()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToInteger_I(buffer: ByteArrayWrapper): BigInteger {
        return helper_intFromString(byteArrayToInteger_S(buffer))
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun decimalToByteArray(buffer: ByteArrayWrapper, value: String) {
        val buf1 = helper_decimalCheckString(value).encodeToByteArray()
        ByteArrayWrapperExt.setSize(buffer, headerSize() + buf1.size, false)
        headerEncode(buffer, ETripleComponentTypeExt.DECIMAL, 0)
        buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun decimalToByteArray(buffer: ByteArrayWrapper, value: BigDecimal) {
        decimalToByteArray(buffer, helper_decimalToString(value))
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDecimal_I(buffer: ByteArrayWrapper): BigDecimal {
        val res = helper_decimalFromString(byteArrayToDecimal_S(buffer))
        return res
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDecimal_S(buffer: ByteArrayWrapper): String {
        val l1 = ByteArrayWrapperExt.getSize(buffer) - headerSize()
        val buf = ByteArray(l1)
        ByteArrayWrapperExt.getBuf(buffer).copyInto(buf, 0, headerSize(), headerSize() + l1)
        return buf.decodeToString()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun doubleToByteArray(buffer: ByteArrayWrapper, value: Double) {
        ByteArrayWrapperExt.setSize(buffer, headerSize() + 8, false)
        headerEncode(buffer, ETripleComponentTypeExt.DOUBLE, 0)
        ByteArrayHelper.writeDouble8(ByteArrayWrapperExt.getBuf(buffer), headerSize(), value)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun doubleToByteArray(buffer: ByteArrayWrapper, value: String) {
        doubleToByteArray(buffer, value.toDouble())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDouble_I(buffer: ByteArrayWrapper): Double {
        return ByteArrayHelper.readDouble8(ByteArrayWrapperExt.getBuf(buffer), headerSize())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDouble_S(buffer: ByteArrayWrapper): String {
        return byteArrayToDouble_I(buffer).toString()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun floatToByteArray(buffer: ByteArrayWrapper, value: Double) {
        ByteArrayWrapperExt.setSize(buffer, headerSize() + 8, false)
        headerEncode(buffer, ETripleComponentTypeExt.FLOAT, 0)
        ByteArrayHelper.writeDouble8(ByteArrayWrapperExt.getBuf(buffer), headerSize(), value)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun floatToByteArray(buffer: ByteArrayWrapper, value: String) {
        floatToByteArray(buffer, value.toDouble())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToFloat_I(buffer: ByteArrayWrapper): Double {
        return ByteArrayHelper.readDouble8(ByteArrayWrapperExt.getBuf(buffer), headerSize())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToFloat_S(buffer: ByteArrayWrapper): String {
        return byteArrayToFloat_I(buffer).toString()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun langToByteArray(buffer: ByteArrayWrapper, content: String, lang: String) {
        val buf1 = lang.encodeToByteArray()
        val buf2 = content.encodeToByteArray()
        ByteArrayWrapperExt.setSize(buffer, headerSize() + 4 + buf1.size + buf2.size, false)
        headerEncode(buffer, ETripleComponentTypeExt.STRING_LANG, 0)
        ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), headerSize() + buf1.size + buf2.size, buf1.size)
        buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize())
        buf2.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize() + buf1.size)
        SanityCheck.check({ /*SOURCE_FILE_START*/"D:/ideaprojects/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:492"/*SOURCE_FILE_END*/ }, { content == byteArrayToLang_Content(buffer) }, { "$content vs ${byteArrayToLang_Content(buffer)}" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"D:/ideaprojects/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:493"/*SOURCE_FILE_END*/ }, { lang == byteArrayToLang_Lang(buffer) }, { "$lang vs ${byteArrayToLang_Lang(buffer)}" })
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToLang_Content(buffer: ByteArrayWrapper): String {
        val l1 = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), ByteArrayWrapperExt.getSize(buffer) - 4)
        val l2 = ByteArrayWrapperExt.getSize(buffer) - headerSize() - 4 - l1
        val buf = ByteArray(l2)
        ByteArrayWrapperExt.getBuf(buffer).copyInto(buf, 0, headerSize() + l1, headerSize() + l1 + l2)
        return buf.decodeToString()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToLang_Lang(buffer: ByteArrayWrapper): String {
        val l1 = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), ByteArrayWrapperExt.getSize(buffer) - 4)
        val buf = ByteArray(l1)
        ByteArrayWrapperExt.getBuf(buffer).copyInto(buf, 0, headerSize(), headerSize() + l1)
        return buf.decodeToString()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun typedToByteArray(buffer: ByteArrayWrapper, content: String, type: String) {
        DictionaryHelperLarge.typedToByteArray(buffer, content, type)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToTyped_Content(buffer: ByteArrayWrapper): String {
        val l1 = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), ByteArrayWrapperExt.getSize(buffer) - 4)
        val l2 = ByteArrayWrapperExt.getSize(buffer) - headerSize() - 4 - l1
        val buf = ByteArray(l2)
        ByteArrayWrapperExt.getBuf(buffer).copyInto(buf, 0, headerSize() + l1, headerSize() + l1 + l2)
        return buf.decodeToString()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToTyped_Type(buffer: ByteArrayWrapper): String {
        val l1 = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), ByteArrayWrapperExt.getSize(buffer) - 4)
        val buf = ByteArray(l1)
        ByteArrayWrapperExt.getBuf(buffer).copyInto(buf, 0, headerSize(), headerSize() + l1)
        return buf.decodeToString()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun bnodeToByteArray(buffer: ByteArrayWrapper, value: String) {
        val buf1 = value.encodeToByteArray()
        ByteArrayWrapperExt.setSize(buffer, headerSize() + 4 + buf1.size, false)
        headerEncode(buffer, ETripleComponentTypeExt.BLANK_NODE, 0)
        ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), headerSize(), buf1.size)
        buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize() + 4)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun bnodeToByteArray(buffer: ByteArrayWrapper, value: DictionaryValueType) {
        ByteArrayWrapperExt.setSize(buffer, headerSize() + DictionaryValueHelper.getSize(), false)
        headerEncode(buffer, ETripleComponentTypeExt.BLANK_NODE, 0x80)
        DictionaryValueHelper.toByteArray(buffer, headerSize(), value)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToBnode_I(buffer: ByteArrayWrapper): DictionaryValueType {
        if (headerDecodeFlag(buffer) == 0x80) {
            return DictionaryValueHelper.fromByteArray(buffer, headerSize())
        } else {
            throw Exception("this is not ready to be used as instanciated value")
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToBnode_S(buffer: ByteArrayWrapper): String {
        if (headerDecodeFlag(buffer) == 0x80) {
            throw Exception("this is not ready to be used as import value")
        } else {
            val l1 = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), headerSize())
            val buf = ByteArray(l1)
            ByteArrayWrapperExt.getBuf(buffer).copyInto(buf, 0, headerSize() + 4, headerSize() + 4 + l1)
            return buf.decodeToString()
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToBnode_A(buffer: ByteArrayWrapper): String {
        if (headerDecodeFlag(buffer) == 0x80) {
            return "_:" + DictionaryValueHelper.fromByteArray(buffer, headerSize()).toString()
        } else {
            val l1 = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), headerSize())
            val buf = ByteArray(l1)
            ByteArrayWrapperExt.getBuf(buffer).copyInto(buf, 0, headerSize() + 4, headerSize() + 4 + l1)
            return buf.decodeToString()
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun iriToByteArray(buffer: ByteArrayWrapper, value: String) {
        val buf1 = value.encodeToByteArray()
        ByteArrayWrapperExt.setSize(buffer, headerSize() + buf1.size, false)
        headerEncode(buffer, ETripleComponentTypeExt.IRI, 0)
        buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToIri(buffer: ByteArrayWrapper): String {
        val l1 = ByteArrayWrapperExt.getSize(buffer) - headerSize()
        val buf = ByteArray(l1)
        ByteArrayWrapperExt.getBuf(buffer).copyInto(buf, 0, headerSize(), headerSize() + l1)
        return buf.decodeToString()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToString(buffer: ByteArrayWrapper): String {
        val l1 = ByteArrayWrapperExt.getSize(buffer) - headerSize()
        val buf = ByteArray(l1)
        ByteArrayWrapperExt.getBuf(buffer).copyInto(buf, 0, headerSize(), headerSize() + l1)
        return buf.decodeToString()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun stringToByteArray(buffer: ByteArrayWrapper, value: String) {
        val buf1 = value.encodeToByteArray()
        ByteArrayWrapperExt.setSize(buffer, headerSize() + buf1.size, false)
        headerEncode(buffer, ETripleComponentTypeExt.STRING, 0)
        buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun sparqlToByteArray(buffer: ByteArrayWrapper, value: String?) {
        DictionaryHelperLarge.sparqlToByteArray(buffer, value)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun removeQuotesFromString(s: String): String {
        var c = s[0]
        var cntLeft = 1
        var cntRight = 0
        if (c != '\'' && c != '"' || c != s[s.length - 1]) {
            throw Exception("invalid quoted string >$s<")
        }
        while (cntLeft < s.length && s[cntLeft] == c) {
            cntLeft++
        }
        while (cntRight < s.length && s[s.length - cntRight - 1] == c) {
            cntRight++
        }
        if (cntLeft >= 3 && cntRight >= 3 && s.length >= 6) {
            return s.substring(3, s.length - 3)
        }
        return s.substring(1, s.length - 1)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun valueDefinitionToByteArray(buffer: ByteArrayWrapper, value: ValueDefinition) {
        sparqlToByteArray(buffer, value.valueToString())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToType(buffer: ByteArrayWrapper): ETripleComponentType {
        val res = headerDecodeType(buffer)
        SanityCheck.check({ /*SOURCE_FILE_START*/"D:/ideaprojects/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:649"/*SOURCE_FILE_END*/ }, { res >= 0 }, { "$res" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"D:/ideaprojects/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:650"/*SOURCE_FILE_END*/ }, { res < ETripleComponentTypeExt.values_size }, { "$res" })
        return res
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToXMLElement(buffer: ByteArrayWrapper): XMLElement {
        val type = byteArrayToType(buffer)
        return when (type) {
            ETripleComponentTypeExt.BLANK_NODE -> XMLElement("ValueBnode").addAttribute("dictvalue", byteArrayToBnode_I(buffer).toString())
            ETripleComponentTypeExt.BOOLEAN -> XMLElement("ValueBoolean").addAttribute("value", byteArrayToBoolean(buffer).toString())
            ETripleComponentTypeExt.UNDEF -> XMLElement("ValueUndef")
            ETripleComponentTypeExt.ERROR -> XMLElement("ValueError")
            ETripleComponentTypeExt.DOUBLE -> XMLElement("ValueDouble").addAttribute("value", byteArrayToDouble_S(buffer))
            ETripleComponentTypeExt.FLOAT -> XMLElement("ValueFloat").addAttribute("value", byteArrayToFloat_S(buffer))
            ETripleComponentTypeExt.INTEGER -> XMLElement("ValueInteger").addAttribute("value", byteArrayToInteger_S(buffer))
            ETripleComponentTypeExt.DECIMAL -> XMLElement("ValueDecimal").addAttribute("value", byteArrayToDecimal_S(buffer))
            ETripleComponentTypeExt.IRI -> XMLElement("ValueIri").addAttribute("value", byteArrayToIri(buffer))
            ETripleComponentTypeExt.STRING -> XMLElement("ValueSimpleLiteral").addAttribute("delimiter", "\"").addAttribute("content", byteArrayToString(buffer))
            ETripleComponentTypeExt.STRING_LANG -> XMLElement("ValueLanguageTaggedLiteral").addAttribute("delimiter", "\"").addAttribute("content", byteArrayToLang_Content(buffer)).addAttribute("language", byteArrayToLang_Lang(buffer))
            ETripleComponentTypeExt.STRING_TYPED -> XMLElement("ValueTypedLiteral").addAttribute("delimiter", "\"").addAttribute("content", byteArrayToTyped_Content(buffer)).addAttribute("type_iri", byteArrayToTyped_Type(buffer))
            ETripleComponentTypeExt.DATE_TIME -> XMLElement("ValueDateTime").addAttribute("value", byteArrayToDateTimeAsTyped_Content(buffer))
            else -> throw Exception("unreachable $type")
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToSparql(buffer: ByteArrayWrapper): String {
        val type = byteArrayToType(buffer)
        return when (type) {
            ETripleComponentTypeExt.UNDEF -> "UNDEF"
            ETripleComponentTypeExt.ERROR -> "ERROR"
            ETripleComponentTypeExt.BLANK_NODE -> byteArrayToBnode_A(buffer)
            ETripleComponentTypeExt.BOOLEAN -> {
                if (byteArrayToBoolean(buffer)) {
                    "\"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>"
                } else {
                    "\"false\"^^<http://www.w3.org/2001/XMLSchema#boolean>"
                }
            }
            ETripleComponentTypeExt.DOUBLE -> "\"" + byteArrayToDouble_S(buffer) + "\"^^<http://www.w3.org/2001/XMLSchema#double>"
            ETripleComponentTypeExt.FLOAT -> "\"" + byteArrayToFloat_S(buffer) + "\"^^<http://www.w3.org/2001/XMLSchema#float>"
            ETripleComponentTypeExt.INTEGER -> "\"" + byteArrayToInteger_S(buffer) + "\"^^<http://www.w3.org/2001/XMLSchema#integer>"
            ETripleComponentTypeExt.DECIMAL -> "\"" + byteArrayToDecimal_S(buffer) + "\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
            ETripleComponentTypeExt.IRI -> "<" + byteArrayToIri(buffer) + ">"
            ETripleComponentTypeExt.STRING -> "\"" + byteArrayToString(buffer) + "\""
            ETripleComponentTypeExt.STRING_LANG -> "\"" + byteArrayToLang_Content(buffer) + "\"@" + byteArrayToLang_Lang(buffer)
            ETripleComponentTypeExt.STRING_TYPED -> "\"" + byteArrayToTyped_Content(buffer) + "\"^^<" + byteArrayToTyped_Type(buffer) + ">"
            ETripleComponentTypeExt.DATE_TIME -> "\"" + byteArrayToDateTimeAsTyped_Content(buffer) + "\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
            else -> throw Exception("unreachable $type")
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToValueDefinition(buffer: ByteArrayWrapper): ValueDefinition {
        val type = byteArrayToType(buffer)
        return when (type) {
            ETripleComponentTypeExt.UNDEF -> DictionaryExt.undefValue2
            ETripleComponentTypeExt.ERROR -> DictionaryExt.errorValue2
            ETripleComponentTypeExt.BLANK_NODE -> ValueBnode("" + byteArrayToBnode_I(buffer))
            ETripleComponentTypeExt.BOOLEAN -> {
                if (byteArrayToBoolean(buffer)) {
                    DictionaryExt.booleanTrueValue2
                } else {
                    DictionaryExt.booleanFalseValue2
                }
            }
            ETripleComponentTypeExt.DOUBLE -> ValueDouble(byteArrayToDouble_I(buffer))
            ETripleComponentTypeExt.FLOAT -> ValueFloat(byteArrayToFloat_I(buffer))
            ETripleComponentTypeExt.INTEGER -> ValueInteger(byteArrayToInteger_I(buffer))
            ETripleComponentTypeExt.DECIMAL -> ValueDecimal(byteArrayToDecimal_I(buffer))
            ETripleComponentTypeExt.IRI -> ValueIri(byteArrayToIri(buffer))
            ETripleComponentTypeExt.STRING -> ValueSimpleLiteral("\"", byteArrayToString(buffer))
            ETripleComponentTypeExt.STRING_LANG -> ValueLanguageTaggedLiteral("\"", byteArrayToLang_Content(buffer), byteArrayToLang_Lang(buffer))
            ETripleComponentTypeExt.STRING_TYPED -> ValueTypedLiteral("\"", byteArrayToTyped_Content(buffer), byteArrayToTyped_Type(buffer))
            ETripleComponentTypeExt.DATE_TIME -> ValueDateTime(byteArrayToSparql(buffer))
            else -> throw Exception("unreachable $type")
        }
    }

    internal inline fun byteArrayToCallback(
        buffer: ByteArrayWrapper,
        crossinline onBNode: (value: DictionaryValueType) -> Unit,
        crossinline onBoolean: (value: Boolean) -> Unit,
        crossinline onLanguageTaggedLiteral: (content: String, lang: String) -> Unit,
        crossinline onSimpleLiteral: (content: String) -> Unit,
        crossinline onTypedLiteral: (content: String, type: String) -> Unit,
        crossinline onDecimal: (value: String) -> Unit,
        crossinline onFloat: (value: Double) -> Unit,
        crossinline onDouble: (value: Double) -> Unit,
        crossinline onInteger: (value: String) -> Unit,
        crossinline onIri: (value: String) -> Unit,
        crossinline onError: () -> Unit,
        crossinline onUndefined: () -> Unit
    ) {
        val type = byteArrayToType(buffer)
        when (type) {
            ETripleComponentTypeExt.FLOAT -> onFloat(byteArrayToFloat_I(buffer))
            ETripleComponentTypeExt.DOUBLE -> onDouble(byteArrayToDouble_I(buffer))
            ETripleComponentTypeExt.INTEGER -> onInteger(byteArrayToInteger_S(buffer))
            ETripleComponentTypeExt.DECIMAL -> onDecimal(byteArrayToDecimal_S(buffer))
            ETripleComponentTypeExt.UNDEF -> onUndefined()
            ETripleComponentTypeExt.ERROR -> onError()
            ETripleComponentTypeExt.BLANK_NODE -> onBNode(byteArrayToBnode_I(buffer))
            ETripleComponentTypeExt.BOOLEAN -> onBoolean(byteArrayToBoolean(buffer))
            ETripleComponentTypeExt.IRI -> onIri(byteArrayToIri(buffer))
            ETripleComponentTypeExt.STRING -> onSimpleLiteral(byteArrayToString(buffer))
            ETripleComponentTypeExt.STRING_LANG -> onLanguageTaggedLiteral(byteArrayToLang_Content(buffer), byteArrayToLang_Lang(buffer))
            ETripleComponentTypeExt.STRING_TYPED -> onTypedLiteral(byteArrayToTyped_Content(buffer), byteArrayToTyped_Type(buffer))
            ETripleComponentTypeExt.DATE_TIME -> onTypedLiteral(byteArrayToDateTimeAsTyped_Content(buffer), "http://www.w3.org/2001/XMLSchema#dateTime")
            else -> throw Exception("unreachable $type")
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayCompareAny(a: ByteArrayWrapper, b: ByteArrayWrapper): Int {
        val typeA = byteArrayToType(a)
        val typeB = byteArrayToType(b)
        if (typeA != typeB) {
            if (typeA == ETripleComponentTypeExt.UNDEF) {
                return -1
            } else if (typeB == ETripleComponentTypeExt.UNDEF) {
                return 1
            } else if (typeA == ETripleComponentTypeExt.ERROR) {
                return -1
            } else if (typeB == ETripleComponentTypeExt.ERROR) {
                return 1
            } else if (typeA == ETripleComponentTypeExt.BLANK_NODE) {
                return -1
            } else if (typeB == ETripleComponentTypeExt.BLANK_NODE) {
                return 1
            } else if (typeA == ETripleComponentTypeExt.IRI) {
                return -1
            } else if (typeB == ETripleComponentTypeExt.IRI) {
                return 1
            } else if (typeA == ETripleComponentTypeExt.STRING) {
                return -1
            } else if (typeB == ETripleComponentTypeExt.STRING) {
                return 1
            } else {
                return typeA - typeB
            }
        } else {
            if (typeA == ETripleComponentTypeExt.UNDEF || typeA == ETripleComponentTypeExt.ERROR) {
                return 0
            } else if (typeA == ETripleComponentTypeExt.BLANK_NODE) {
                return a.compareTo(b)
            } else if (typeA == ETripleComponentTypeExt.BOOLEAN) {
                return a.compareTo(b)
            } else if (typeA == ETripleComponentTypeExt.DATE_TIME) {
                return a.compareTo(b)
            } else if (typeA == ETripleComponentTypeExt.DECIMAL) {
                val av = byteArrayToDecimal_I(a)
                val bv = byteArrayToDecimal_I(b)
                return av.compareTo(bv)
            } else if (typeA == ETripleComponentTypeExt.DOUBLE) {
                val av = byteArrayToDouble_I(a)
                val bv = byteArrayToDouble_I(b)
                return av.compareTo(bv)
            } else if (typeA == ETripleComponentTypeExt.FLOAT) {
                val av = byteArrayToFloat_I(a)
                val bv = byteArrayToFloat_I(b)
                return av.compareTo(bv)
            } else if (typeA == ETripleComponentTypeExt.INTEGER) {
                val av = byteArrayToInteger_I(a)
                val bv = byteArrayToInteger_I(b)
                return av.compareTo(bv)
            } else if (typeA == ETripleComponentTypeExt.STRING_LANG || typeA == ETripleComponentTypeExt.STRING_TYPED || typeA == ETripleComponentTypeExt.IRI || typeA == ETripleComponentTypeExt.STRING) {
                return a.compareTo(b)
            }
        }
        throw Exception("can not compare $typeA $typeB")
    }
}
