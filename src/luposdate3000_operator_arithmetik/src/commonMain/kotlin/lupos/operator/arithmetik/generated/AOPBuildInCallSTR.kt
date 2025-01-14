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
package lupos.operator.arithmetik.generated

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.integer.BigInteger
import lupos.operator.arithmetik.AOPBase
import lupos.shared.DictionaryValueHelper
import lupos.shared.DictionaryValueType
import lupos.shared.EOperatorIDExt
import lupos.shared.ETripleComponentTypeExt
import lupos.shared.IQuery
import lupos.shared.dynamicArray.ByteArrayWrapper
import lupos.shared.inline.DictionaryHelper
import lupos.shared.operator.IOPBase
import lupos.shared.operator.iterator.IteratorBundle

public class AOPBuildInCallSTR public constructor(query: IQuery, child0: AOPBase) : AOPBase(query, EOperatorIDExt.AOPBuildInCallSTRID, "AOPBuildInCallSTR", arrayOf(child0)) {
    override fun toSparql(): String = "STR(${children[0].toSparql()})"
    override fun equals(other: Any?): Boolean = other is AOPBuildInCallSTR && children[0] == other.children[0]
    override fun cloneOP(): IOPBase = AOPBuildInCallSTR(query, children[0].cloneOP() as AOPBase)
    override fun evaluateID(row: IteratorBundle): () -> DictionaryValueType {
        val tmp_0: ByteArrayWrapper = ByteArrayWrapper()
        val tmp_2: ByteArrayWrapper = ByteArrayWrapper()
        val child0: () -> DictionaryValueType = (children[0] as AOPBase).evaluateID(row)
        return {
            val res: DictionaryValueType
            val childIn0: DictionaryValueType = child0()
            query.getDictionary().getValue(tmp_0, childIn0)
            when (DictionaryHelper.byteArrayToType(tmp_0)) {
                ETripleComponentTypeExt.BLANK_NODE, ETripleComponentTypeExt.ERROR, ETripleComponentTypeExt.UNDEF -> {
                    DictionaryHelper.errorToByteArray(tmp_2)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.BOOLEAN -> {
                    val tmp_4: Boolean = DictionaryHelper.byteArrayToBoolean(tmp_0)
                    val tmp_5: String = tmp_4.toString()
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_5)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.DATE_TIME -> {
                    val tmp_7_typed_content: String = DictionaryHelper.byteArrayToDateTimeAsTyped_Content(tmp_0)
                    val tmp_7_year: BigInteger = DictionaryHelper.byteArrayToDateTime_Year(tmp_0)
                    val tmp_7_month: BigInteger = DictionaryHelper.byteArrayToDateTime_Month(tmp_0)
                    val tmp_7_day: BigInteger = DictionaryHelper.byteArrayToDateTime_Day(tmp_0)
                    val tmp_7_hours: BigInteger = DictionaryHelper.byteArrayToDateTime_Hours(tmp_0)
                    val tmp_7_minutes: BigInteger = DictionaryHelper.byteArrayToDateTime_Minutes(tmp_0)
                    val tmp_7_seconds: BigDecimal = DictionaryHelper.byteArrayToDateTime_Seconds(tmp_0)
                    val tmp_7_tz: String = DictionaryHelper.byteArrayToDateTime_TZ(tmp_0)
                    val tmp_7_timezone: String = DictionaryHelper.byteArrayToDateTime_TimeZone(tmp_0)
                    val tmp_8: String = tmp_7_typed_content
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_8)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.DECIMAL -> {
                    val tmp_10: BigDecimal = DictionaryHelper.byteArrayToDecimal_I(tmp_0)
                    val tmp_11: String = tmp_10.toString()
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_11)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.DOUBLE -> {
                    val tmp_13: Double = DictionaryHelper.byteArrayToDouble_I(tmp_0)
                    val tmp_14: String = tmp_13.toString()
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_14)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.FLOAT -> {
                    val tmp_17: Double = DictionaryHelper.byteArrayToFloat_I(tmp_0)
                    val tmp_18: String = tmp_17.toString()
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_18)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.INTEGER -> {
                    val tmp_20: BigInteger = DictionaryHelper.byteArrayToInteger_I(tmp_0)
                    val tmp_21: String = tmp_20.toString()
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_21)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.IRI -> {
                    val tmp_23: String = DictionaryHelper.byteArrayToIri(tmp_0)
                    val tmp_24: String = tmp_23
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_24)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.STRING -> {
                    val tmp_26: String = DictionaryHelper.byteArrayToString(tmp_0)
                    val tmp_27: String = tmp_26
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_27)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.STRING_LANG -> {
                    val tmp_29_content: String = DictionaryHelper.byteArrayToLang_Content(tmp_0)
                    val tmp_29_lang: String = DictionaryHelper.byteArrayToLang_Lang(tmp_0)
                    val tmp_30: String = tmp_29_content
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_30)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.STRING_TYPED -> {
                    val tmp_32_content: String = DictionaryHelper.byteArrayToTyped_Content(tmp_0)
                    val tmp_32_type: String = DictionaryHelper.byteArrayToTyped_Type(tmp_0)
                    val tmp_33: String = tmp_32_content
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_33)
                    res = query.getDictionary().createValue(tmp_2)
                }
                else -> {
                    res = DictionaryValueHelper.errorValue
                }
            }
            res
        }
    }
}
