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
import com.ionspin.kotlin.bignum.integer.Sign
import com.ionspin.kotlin.bignum.integer.toBigInteger
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
    internal inline fun headerSize(): Int = 1

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun headerEncode(buffer: ByteArrayWrapper, type: ETripleComponentType, flag: Int) {
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:51"/*SOURCE_FILE_END*/ }, { (type and ETripleComponentTypeExt.values_mask) == type }, { "DictionaryHelper.headerEncode type is bad ${type.toString(16)} ... ${ETripleComponentTypeExt.values_mask.toString(16)} " })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:52"/*SOURCE_FILE_END*/ }, { (flag and ETripleComponentTypeExt.values_mask_inversed) == flag }, { "DictionaryHelper.headerEncode flag is bad" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:53"/*SOURCE_FILE_END*/ }, { (type or flag) <= 0xff }, { "DictionaryHelper.headerEncode can not be encoded in 1 byte" })
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
        ByteArrayWrapperExt.setSize(buffer, headerSize())
        headerEncode(buffer, ETripleComponentTypeExt.ERROR, 0)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun undefToByteArray(buffer: ByteArrayWrapper) {
        ByteArrayWrapperExt.setSize(buffer, headerSize())
        headerEncode(buffer, ETripleComponentTypeExt.UNDEF, 0)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun dateTimeToByteArray(buffer: ByteArrayWrapper, str: String) {
        val year: String
        val month: Int
        val day: Int
        val hours: Int
        val minutes: Int
        val seconds: String
        val timezoneHours: Int
        val timezoneMinutes: Int
        val hasTimeZone: Boolean
        var idx = 0
        var idx2 = str.indexOf('-', 1)
        if (idx2 < idx) {
            idx2 = str.length - 1
        }
        if (idx2 > idx) {
            year = str.substring(idx, idx2)
            idx = idx2
            idx2 = str.indexOf('-', idx + 1)
            if (idx2 < idx) {
                idx2 = str.length - 1
            }
            if (idx2 > idx) {
                month = str.substring(idx + 1, idx2).toInt()
                idx = idx2
                idx2 = str.indexOf('T', idx + 1)
                if (idx2 < idx) {
                    idx2 = str.length - 1
                }
                if (idx2 > idx) {
                    day = str.substring(idx + 1, idx2).toInt()
                    idx = idx2
                    idx2 = str.indexOf(':', idx + 1)
                    if (idx2 < idx) {
                        idx2 = str.length - 1
                    }
                    if (idx2 > idx) {
                        hours = str.substring(idx + 1, idx2).toInt()
                        idx = idx2
                        idx2 = str.indexOf(':', idx + 1)
                        if (idx2 < idx) {
                            idx2 = str.length - 1
                        }
                        if (idx2 > idx) {
                            minutes = str.substring(idx + 1, idx2).toInt()
                            idx = idx2
                            val idxa = str.indexOf('Z', idx + 1)
                            val idxb = str.indexOf('+', idx + 1)
                            val idxc = str.indexOf('-', idx + 1)
                            if (idxa > idx) {
                                seconds = str.substring(idx + 1, idxa)
                                timezoneHours = 0
                                timezoneMinutes = 0
                                hasTimeZone = true
                            } else if (idxb > idx) {
                                seconds = str.substring(idx + 1, idxb)
                                idx = idxb
                                idx2 = str.indexOf(':', idx + 1)
                                if (idx2 > idx) {
                                    timezoneHours = str.substring(idx, idx2).toInt()
                                    timezoneMinutes = str.substring(idx2 + 1, str.length).toInt()
                                    hasTimeZone = true
                                } else {
                                    timezoneHours = -99
                                    timezoneMinutes = -99
                                    hasTimeZone = false
                                }
                            } else if (idxc > idx) {
                                seconds = str.substring(idx + 1, idxc)
                                idx = idxc
                                idx2 = str.indexOf(':', idx + 1)
                                if (idx2 > idx) {
                                    timezoneHours = str.substring(idx, idx2).toInt()
                                    timezoneMinutes = str.substring(idx2 + 1, str.length).toInt()
                                    hasTimeZone = true
                                } else {
                                    timezoneHours = -99
                                    timezoneMinutes = -99
                                    hasTimeZone = false
                                }
                            } else {
                                seconds = str.substring(idx + 1, str.length)
                                timezoneHours = -99
                                timezoneMinutes = -99
                                hasTimeZone = false
                            }
                        } else {
                            minutes = 0
                            seconds = "0.0"
                            timezoneHours = -99
                            timezoneMinutes = -99
                            hasTimeZone = false
                        }
                    } else {
                        hours = 0
                        minutes = 0
                        seconds = "0.0"
                        timezoneHours = -99
                        timezoneMinutes = -99
                        hasTimeZone = false
                    }
                } else {
                    day = 0
                    hours = 0
                    minutes = 0
                    seconds = "0.0"
                    timezoneHours = -99
                    timezoneMinutes = -99
                    hasTimeZone = false
                }
            } else {
                month = 0
                day = 0
                hours = 0
                minutes = 0
                seconds = "0.0"
                timezoneHours = -99
                timezoneMinutes = -99
                hasTimeZone = false
            }
        } else {
            year = "0"
            month = 0
            day = 0
            hours = 0
            minutes = 0
            seconds = "0.0"
            timezoneHours = -99
            timezoneMinutes = -99
            hasTimeZone = false
        }
        var done = false
        if (timezoneHours == -99 && timezoneMinutes == -99) {
            var shortEncoding = true
            var secondsBeforeDot = 0L
            var secondsAfterDot = 0L
            var digitsAfterDot = 0
            var si = 0
            while (si < seconds.length) {
                val s = seconds[si]
                        si++
                when (s) {
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                        secondsBeforeDot = secondsBeforeDot * 10 + (s - '0')
                        if (secondsBeforeDot >= 60) {
                            shortEncoding = false
                            break
                        }
                    }
                    '.' -> {
                        while (si < seconds.length) {
                            val s = seconds[si]
si++
                            when (s) {
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                                    digitsAfterDot++
                                    secondsAfterDot = secondsAfterDot * 10 + (s - '0')
                                    if (secondsAfterDot >= 1000) {
                                        shortEncoding = false
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (shortEncoding) {
                var componentMilliseconds = secondsBeforeDot * 1000L
                when (digitsAfterDot) {
                    0 -> {
                    }
                    1 -> {
                        componentMilliseconds = componentMilliseconds + digitsAfterDot * 100
                    }
                    2 -> {
                        componentMilliseconds = componentMilliseconds + digitsAfterDot * 10
                    }
                    3 -> {
                        componentMilliseconds = componentMilliseconds + digitsAfterDot
                    }
                    else -> {
                        shortEncoding = false
                    }
                }
                if (shortEncoding) {
                    componentMilliseconds += (minutes * 1000L * 60L)
                    componentMilliseconds += (hours * 1000L * 60L * 60L)
                    SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:270"/*SOURCE_FILE_END*/ }, { componentMilliseconds >= 0 })
                    SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:271"/*SOURCE_FILE_END*/ }, { componentMilliseconds < (1L shl 27) })
                    if (day < 32 && month <16) {
                        val componentDay = day.toLong() shl 27
                        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:274"/*SOURCE_FILE_END*/ }, { componentDay >= (1L shl 27) })
                        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:275"/*SOURCE_FILE_END*/ }, { componentDay < (1L shl 32) })
                        val componentMonth = month.toLong() shl 32
                        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:277"/*SOURCE_FILE_END*/ }, { componentMonth >= (1L shl 32) })
                        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:278"/*SOURCE_FILE_END*/ }, { componentMonth < (1L shl 36) })
                        var componentYearSign = 1L shl 36
                        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:280"/*SOURCE_FILE_END*/ }, { componentYearSign >= (1L shl 36) })
                        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:281"/*SOURCE_FILE_END*/ }, { componentYearSign < (1L shl 37) })
                        var componentYear = 0L
                        var si = 0
                        while (si < year.length) {
                            val s = year[si]
si++
                            when (s) {
                                '-' -> {
                                    componentYearSign = 0L
                                }
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                                    componentYear = componentYear * 10 + (s - '0')
                                }
                            }
                        }
                        if (componentYear < (1L shl 19)) {
                            componentYear = componentYear shl 37
                            SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:298"/*SOURCE_FILE_END*/ }, { componentYear >= (1L shl 37) })
                            SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:299"/*SOURCE_FILE_END*/ }, { componentYear < (1L shl 56) })
                            val componentAll = componentMilliseconds or componentDay or componentMonth or componentYearSign or componentYear
                            ByteArrayWrapperExt.setSize(buffer, headerSize() + 7)
                            headerEncode(buffer, ETripleComponentTypeExt.DATE_TIME, 0x80)
                            ByteArrayHelper.writeLong7(ByteArrayWrapperExt.getBuf(buffer), headerSize(), componentAll)
                            done = true
                        }
                    }
                }
            }
        }

/*
 * 56 bits = 7 bytes total
 *
 * 27 bits milliseconds on day   (max 86400000)
 *  1 bit year-sign
 *  5 bits day in month
 *  4 bits month in year
 * 19 seconds year
 * no timezone
 */
        if (!done) {
            dateTimeToByteArray(buffer, BigInteger.parseString(year, 10), month, day, hours, minutes, BigDecimal.parseString(seconds, 10), timezoneHours, timezoneMinutes, hasTimeZone)
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun dateTimeToByteArray(buffer: ByteArrayWrapper) {
        val dateNow = DateHelper()
        dateTimeToByteArray(buffer, BigInteger.parseString(dateNow.year().toString(), 10), dateNow.month(), dateNow.day(), dateNow.hours(), dateNow.minutes(), BigDecimal.parseString(dateNow.seconds().toString(), 10), -99, -99, false)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun dateTimeToByteArray(buffer: ByteArrayWrapper, year: BigInteger, month: Int, day: Int, hours: Int, minutes: Int, seconds: BigDecimal, timezoneHours: Int, timezoneMinutes: Int, hasTimeZone: Boolean) {
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:334"/*SOURCE_FILE_END*/ }, { month >= 0 }, { "dateTimeToByteArray.month : $month" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:335"/*SOURCE_FILE_END*/ }, { month <= 99 }, { "dateTimeToByteArray.month : $month" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:336"/*SOURCE_FILE_END*/ }, { day >= 0 }, { "dateTimeToByteArray.day : $day" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:337"/*SOURCE_FILE_END*/ }, { day <= 99 }, { "dateTimeToByteArray.day : $day" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:338"/*SOURCE_FILE_END*/ }, { hours >= 0 }, { "dateTimeToByteArray.hours : $hours" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:339"/*SOURCE_FILE_END*/ }, { hours <= 24 }, { "dateTimeToByteArray.hours : $hours" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:340"/*SOURCE_FILE_END*/ }, { minutes >= 0 }, { "dateTimeToByteArray.minutes : $minutes" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:341"/*SOURCE_FILE_END*/ }, { minutes <= 99 }, { "dateTimeToByteArray.minutes : $minutes" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:342"/*SOURCE_FILE_END*/ }, { !hasTimeZone || timezoneHours >= -24 }, { "dateTimeToByteArray.timezoneHours : $timezoneHours" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:343"/*SOURCE_FILE_END*/ }, { !hasTimeZone || timezoneHours <= 24 }, { "dateTimeToByteArray.timezoneHours : $timezoneHours" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:344"/*SOURCE_FILE_END*/ }, { !hasTimeZone || timezoneMinutes >= 0 }, { "dateTimeToByteArray.timezoneMinutes : $timezoneMinutes" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:345"/*SOURCE_FILE_END*/ }, { !hasTimeZone || timezoneMinutes <= 99 }, { "dateTimeToByteArray.timezoneMinutes : $timezoneMinutes" })
        val buf1 = year.toByteArray()
        val buf2 = seconds.significand.toByteArray()
        val l1 = buf1.size
        val l2 = buf2.size
        ByteArrayWrapperExt.setSize(buffer, headerSize() + 38 + l1 + l2)
        var off = 0
        headerEncode(buffer, ETripleComponentTypeExt.DATE_TIME, 0)
        off += headerSize()
        ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), off, l1)
        off += 4
        ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), off, month)
        off += 4
        ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), off, day)
        off += 4
        ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), off, hours)
        off += 4
        ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), off, minutes)
        off += 4
        if (hasTimeZone) {
            ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), off, timezoneHours)
            off += 4
            ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), off, timezoneMinutes)
            off += 4
        } else {
            ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), off, -99)
            off += 4
            ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), off, -99)
            off += 4
        }
        ByteArrayHelper.writeLong8(ByteArrayWrapperExt.getBuf(buffer), off, seconds.exponent)
        off += 8
        ByteArrayWrapperExt.getBuf(buffer)[off] = year.signum().toByte()
        off++
        ByteArrayWrapperExt.getBuf(buffer)[off] = seconds.signum().toByte()
        off++
        buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), off)
        off += l1
        buf2.copyInto(ByteArrayWrapperExt.getBuf(buffer), off)
        off += l2
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:385"/*SOURCE_FILE_END*/ }, { off == ByteArrayWrapperExt.getSize(buffer) })
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDateTime_Year(buffer: ByteArrayWrapper): BigInteger {
        if (headerDecodeFlag(buffer) == 0x80) {
            val componentAll = ByteArrayHelper.readLong7(ByteArrayWrapperExt.getBuf(buffer), headerSize())
            var year = ((componentAll shr 37) and 0x7FFFF)
            if ((componentAll and (1L shl 36)) == (1L shl 36)) {
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
            off += 8
            val yearSignum = when (ByteArrayWrapperExt.getBuf(buffer)[off]) {
                (-1).toByte() -> Sign.NEGATIVE
                1.toByte() -> Sign.POSITIVE
                else -> Sign.ZERO
            }
            off++
            off++
            val buf1 = ByteArray(l1)
            ByteArrayWrapperExt.getBuf(buffer).copyInto(buf1, 0, off, off + l1)
            off += l1
            val l2 = ByteArrayWrapperExt.getSize(buffer) - l1 - headerSize() - 38
            off += l2
            SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:421"/*SOURCE_FILE_END*/ }, { off == ByteArrayWrapperExt.getSize(buffer) })
            val year = BigInteger.fromByteArray(buf1, yearSignum)
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
            val secondsExponent = ByteArrayHelper.readLong8(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 8
            off++
            val secondsSignum = when (ByteArrayWrapperExt.getBuf(buffer)[off]) {
                (-1).toByte() -> Sign.NEGATIVE
                1.toByte() -> Sign.POSITIVE
                else -> Sign.ZERO
            }
            off++
            off += l1
            val l2 = ByteArrayWrapperExt.getSize(buffer) - l1 - headerSize() - 38
            val buf2 = ByteArray(l2)
            ByteArrayWrapperExt.getBuf(buffer).copyInto(buf2, 0, off, off + l2)
            buf2.copyInto(ByteArrayWrapperExt.getBuf(buffer), off)
            off += l2
            SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:523"/*SOURCE_FILE_END*/ }, { off == ByteArrayWrapperExt.getSize(buffer) })
            val seconds = BigDecimal.fromBigIntegerWithExponent(BigInteger.fromByteArray(buf2, secondsSignum), secondsExponent)
            return seconds
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDateTimeAsTyped_Content(buffer: ByteArrayWrapper): String {
        if (headerDecodeFlag(buffer) == 0x80) {
            val componentAll = ByteArrayHelper.readLong7(ByteArrayWrapperExt.getBuf(buffer), headerSize())
            val componentMilliseconds = (componentAll and 0x7FFFFFF)
            val milliseconds = componentMilliseconds % (60 * 1000)
            val minutes = (componentMilliseconds / (1000 * 60)) % 60
            val hours = componentMilliseconds / (1000 * 60 * 60)
            val day = ((componentAll shr 27) and 0x1F)
            val month = ((componentAll shr 32) and 0xF)
            var year = ((componentAll shr 37) and 0x7FFFF)
            if ((componentAll and (1L shl 36)) == (1L shl 36)) {
                year = -year
            }
            val secondsString = if ((milliseconds % 1000) != 0L) {
                (milliseconds / 1000).toString().padStart(2, '0') + "." + (milliseconds % 1000).toString().padStart(1, '0')
            } else {
                (milliseconds / 1000).toString().padStart(2, '0')
            }
            return "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}T${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:$secondsString"
        } else {
            var off = 0
            off += headerSize()
            val l1 = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 4
            val month = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 4
            val day = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 4
            val hours = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 4
            val minutes = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 4
            val timezoneHours = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 4
            val timezoneMinutes = ByteArrayHelper.readInt4(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 4
            val secondsExponent = ByteArrayHelper.readLong8(ByteArrayWrapperExt.getBuf(buffer), off)
            off += 8
            val yearSignum = when (ByteArrayWrapperExt.getBuf(buffer)[off]) {
                (-1).toByte() -> Sign.NEGATIVE
                1.toByte() -> Sign.POSITIVE
                else -> Sign.ZERO
            }
            off++
            val secondsSignum = when (ByteArrayWrapperExt.getBuf(buffer)[off]) {
                (-1).toByte() -> Sign.NEGATIVE
                1.toByte() -> Sign.POSITIVE
                else -> Sign.ZERO
            }
            off++
            val buf1 = ByteArray(l1)
            ByteArrayWrapperExt.getBuf(buffer).copyInto(buf1, 0, off, off + l1)
            off += l1
            val l2 = ByteArrayWrapperExt.getSize(buffer) - l1 - headerSize() - 38
            val buf2 = ByteArray(l2)
            ByteArrayWrapperExt.getBuf(buffer).copyInto(buf2, 0, off, off + l2)
            buf2.copyInto(ByteArrayWrapperExt.getBuf(buffer), off)
            off += l2
            SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:588"/*SOURCE_FILE_END*/ }, { off == ByteArrayWrapperExt.getSize(buffer) })
            val year = BigInteger.fromByteArray(buf1, yearSignum)
            val seconds = BigDecimal.fromBigIntegerWithExponent(BigInteger.fromByteArray(buf2, secondsSignum), secondsExponent)
            val secondsString2 = seconds.toStringExpanded().split(".")
            var secondsString = secondsString2[0].padStart(2, '0')
            if (secondsString2.size > 1) {
                var tmp = secondsString2[1]
                while (tmp.endsWith('0')) {
                    tmp = tmp.substring(0, tmp.length - 1)
                }
                if (tmp.length > 0) {
                    secondsString += "." + tmp
                }
            }
            return if (timezoneHours == -99 && timezoneMinutes == -99) {
                "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}T${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:$secondsString"
            } else if (timezoneHours == 0 && timezoneMinutes == 0) {
                "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}T${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${secondsString}Z"
            } else {
                var timezoneHoursLocal = timezoneHours.toString()
                if (timezoneHoursLocal[0] == '-' || timezoneHoursLocal[0] == '+') {
                    timezoneHoursLocal = "" + timezoneHoursLocal[0] + timezoneHoursLocal.substring(1).padStart(2, '0')
                } else {
                    timezoneHoursLocal = "+" + timezoneHoursLocal.padStart(2, '0')
                }
                "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}T${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${secondsString}$timezoneHoursLocal:${timezoneMinutes.toString().padStart(2, '0')}"
            }
        }
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
        ByteArrayWrapperExt.setSize(buffer, headerSize())
        if (value) {
            headerEncode(buffer, ETripleComponentTypeExt.BOOLEAN, 0x80)
        } else {
            headerEncode(buffer, ETripleComponentTypeExt.BOOLEAN, 0x00)
        }
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:676"/*SOURCE_FILE_END*/ }, { byteArrayToBoolean(buffer) == value })
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToBoolean(buffer: ByteArrayWrapper): Boolean {
        val flag = headerDecodeFlag(buffer)
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:682"/*SOURCE_FILE_END*/ }, { flag == 0x0 || flag == 0x80 }, { "0x${flag.toString(16)}" })
        return flag == 0x80
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun integerToByteArray(buffer: ByteArrayWrapper, value: String) {
        integerToByteArray(buffer, BigInteger.parseString(value, 10))
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun integerToByteArray(buffer: ByteArrayWrapper, value: BigInteger) {
        val buf1 = value.toByteArray()
        ByteArrayWrapperExt.setSize(buffer, headerSize() + buf1.size)
        if (value.signum() < 0) {
            headerEncode(buffer, ETripleComponentTypeExt.INTEGER, 0x80)
        } else if (value.signum() > 0) {
            headerEncode(buffer, ETripleComponentTypeExt.INTEGER, 0x40)
        } else {
            headerEncode(buffer, ETripleComponentTypeExt.INTEGER, 0)
        }
        buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToInteger_S(buffer: ByteArrayWrapper): String {
        return byteArrayToInteger_I(buffer).toString()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToInteger_I(buffer: ByteArrayWrapper): BigInteger {
        val l1 = ByteArrayWrapperExt.getSize(buffer) - headerSize()
        val buf = ByteArray(l1)
        ByteArrayWrapperExt.getBuf(buffer).copyInto(buf, 0, headerSize(), headerSize() + l1)
        val sign = when (headerDecodeFlag(buffer)) {
            0x80 -> Sign.NEGATIVE
            0x40 -> Sign.POSITIVE
            else -> Sign.ZERO
        }
        return BigInteger.fromByteArray(buf, sign)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun decimalToByteArray(buffer: ByteArrayWrapper, value: String) {
        decimalToByteArray(buffer, BigDecimal.parseString(value, 10))
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun decimalToByteArray(buffer: ByteArrayWrapper, value: BigDecimal) {
        val buf1 = value.significand.toByteArray()
        ByteArrayWrapperExt.setSize(buffer, headerSize() + 8 + buf1.size)
        if (value.signum() < 0) {
            headerEncode(buffer, ETripleComponentTypeExt.DECIMAL, 0x80)
        } else if (value.signum() > 0) {
            headerEncode(buffer, ETripleComponentTypeExt.DECIMAL, 0x40)
        } else {
            headerEncode(buffer, ETripleComponentTypeExt.DECIMAL, 0)
        }
        ByteArrayHelper.writeLong8(ByteArrayWrapperExt.getBuf(buffer), headerSize(), value.exponent)
        buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize() + 8)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDecimal_I(buffer: ByteArrayWrapper): BigDecimal {
        val l1 = ByteArrayWrapperExt.getSize(buffer) - headerSize() - 8
        val buf = ByteArray(l1)
        ByteArrayWrapperExt.getBuf(buffer).copyInto(buf, 0, headerSize() + 8, headerSize() + 8 + l1)
        val exponent = ByteArrayHelper.readLong8(ByteArrayWrapperExt.getBuf(buffer), headerSize())
        val sign = when (headerDecodeFlag(buffer)) {
            0x80 -> Sign.NEGATIVE
            0x40 -> Sign.POSITIVE
            else -> Sign.ZERO
        }
        return BigDecimal.fromBigIntegerWithExponent(BigInteger.fromByteArray(buf, sign), exponent)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun byteArrayToDecimal_S(buffer: ByteArrayWrapper): String {
        val tmp = byteArrayToDecimal_I(buffer).toStringExpanded()
        if (tmp.contains('.')) {
            return tmp
        }
        return tmp + ".0"
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun doubleToByteArray(buffer: ByteArrayWrapper, value: Double) {
        ByteArrayWrapperExt.setSize(buffer, headerSize() + 8)
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
        ByteArrayWrapperExt.setSize(buffer, headerSize() + 8)
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
        ByteArrayWrapperExt.setSize(buffer, headerSize() + 4 + buf1.size + buf2.size)
        headerEncode(buffer, ETripleComponentTypeExt.STRING_LANG, 0)
        ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), headerSize() + buf1.size + buf2.size, buf1.size)
        buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize())
        buf2.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize() + buf1.size)
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:819"/*SOURCE_FILE_END*/ }, { content == byteArrayToLang_Content(buffer) }, { "$content vs ${byteArrayToLang_Content(buffer)}" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:820"/*SOURCE_FILE_END*/ }, { lang == byteArrayToLang_Lang(buffer) }, { "$lang vs ${byteArrayToLang_Lang(buffer)}" })
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
        try {
            when (type) {
                "http://www.w3.org/2001/XMLSchema#integer" -> integerToByteArray(buffer, content)
                "http://www.w3.org/2001/XMLSchema#decimal" -> decimalToByteArray(buffer, content)
                "http://www.w3.org/2001/XMLSchema#double" -> doubleToByteArray(buffer, content.toDouble())
                "http://www.w3.org/2001/XMLSchema#float" -> floatToByteArray(buffer, content.toDouble())
                "http://www.w3.org/2001/XMLSchema#boolean" -> booleanToByteArray(buffer, content.lowercase() == "true")
                "http://www.w3.org/2001/XMLSchema#dateTime" -> dateTimeToByteArray(buffer, content)
                else -> {
                    val buf1 = type.encodeToByteArray()
                    val buf2 = content.encodeToByteArray()
                    ByteArrayWrapperExt.setSize(buffer, headerSize() + 4 + buf1.size + buf2.size)
                    headerEncode(buffer, ETripleComponentTypeExt.STRING_TYPED, 0)
                    ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), headerSize() + buf1.size + buf2.size, buf1.size)
                    buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize())
                    buf2.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize() + buf1.size)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            stringToByteArray(buffer, content)
        }
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
        ByteArrayWrapperExt.setSize(buffer, headerSize() + 4 + buf1.size)
        headerEncode(buffer, ETripleComponentTypeExt.BLANK_NODE, 0)
        ByteArrayHelper.writeInt4(ByteArrayWrapperExt.getBuf(buffer), headerSize(), buf1.size)
        buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize() + 4)
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun bnodeToByteArray(buffer: ByteArrayWrapper, value: DictionaryValueType) {
        ByteArrayWrapperExt.setSize(buffer, headerSize() + DictionaryValueHelper.getSize())
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
        ByteArrayWrapperExt.setSize(buffer, headerSize() + buf1.size)
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
        ByteArrayWrapperExt.setSize(buffer, headerSize() + buf1.size)
        headerEncode(buffer, ETripleComponentTypeExt.STRING, 0)
        buf1.copyInto(ByteArrayWrapperExt.getBuf(buffer), headerSize())
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun sparqlToByteArray(buffer: ByteArrayWrapper, value: String?) {
        if (value == null || value.isEmpty() || value.lowercase() == "undef") {
            undefToByteArray(buffer)
            return
        }
        if (value.lowercase() == "error") {
            errorToByteArray(buffer)
            return
        }
        if (value.lowercase() == "true") {
            booleanToByteArray(buffer, true)
            return
        }
        if (value.lowercase() == "false") {
            booleanToByteArray(buffer, false)
            return
        }
        if (value.startsWith("_:")) {
            bnodeToByteArray(buffer, value.substring(2, value.length))
            return
        }
        if (value.startsWith("<") && value.endsWith(">")) {
            iriToByteArray(buffer, value.substring(1, value.length - 1))
            return
        }
        if (!value.contains('.')) {
            try {
                val i = BigInteger.parseString(value, 10)
                integerToByteArray(buffer, i)
                return
            } catch (e: Exception) {
                // e.printStackTrace() this is handled correctly
            }
        }
        if (!value.contains("e") && !value.contains("E")) {
            try {
                val d = BigDecimal.parseString(value, 10)
                decimalToByteArray(buffer, d)
                return
            } catch (e: Exception) {
                // e.printStackTrace() this is handled correctly
            }
        }
        try {
            val d = value.toDouble()
            doubleToByteArray(buffer, d)
            return
        } catch (e: Exception) {
            // e.printStackTrace() this is handled correctly
        }
        if (!value.endsWith("" + value[0])) {
            val typeIdx = value.lastIndexOf("" + value[0] + "^^<")
            val langIdx = value.lastIndexOf("" + value[0] + "@")
            if (value.endsWith(">") && typeIdx > 0) {
                typedToByteArray(buffer, removeQuotesFromString(value.substring(0, typeIdx + 1)), value.substring(typeIdx + 4, value.length - 1))
                return
            } else {
                SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:1022"/*SOURCE_FILE_END*/ }, { langIdx > 0 }, { "$langIdx :: $value" })
                langToByteArray(buffer, removeQuotesFromString(value.substring(0, langIdx + 1)), value.substring(langIdx + 2, value.length))
                return
            }
        }
        stringToByteArray(buffer, removeQuotesFromString(value))
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
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:1058"/*SOURCE_FILE_END*/ }, { res >= 0 }, { "$res" })
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/DictionaryHelper.kt:1059"/*SOURCE_FILE_END*/ }, { res < ETripleComponentTypeExt.values_size }, { "$res" })
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
