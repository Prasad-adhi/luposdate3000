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

public class AOPFunctionCallString public constructor(query: IQuery, child0: AOPBase) : AOPBase(query, EOperatorIDExt.AOPFunctionCallStringID, "AOPFunctionCallString", arrayOf(child0)) {
    override fun toSparql(): String = "<http://www.w3.org/2001/XMLSchema#string>(${children[0].toSparql()})"
    override fun equals(other: Any?): Boolean = other is AOPFunctionCallString && children[0] == other.children[0]
    override fun cloneOP(): IOPBase = AOPFunctionCallString(query, children[0].cloneOP() as AOPBase)
    override fun evaluateID(row: IteratorBundle): () -> DictionaryValueType {
        val tmp_0: ByteArrayWrapper = ByteArrayWrapper()
        val tmp_2: ByteArrayWrapper = ByteArrayWrapper()
        val child0: () -> DictionaryValueType = (children[0] as AOPBase).evaluateID(row)
        return {
            var res: DictionaryValueType = 0
            val childIn0: DictionaryValueType = child0()
            query.getDictionary().getValue(tmp_0, childIn0)
            when (DictionaryHelper.byteArrayToType(tmp_0)) {
                ETripleComponentTypeExt.BLANK_NODE, ETripleComponentTypeExt.ERROR, ETripleComponentTypeExt.IRI, ETripleComponentTypeExt.UNDEF -> {
                    DictionaryHelper.errorToByteArray(tmp_2)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.BOOLEAN -> {
                    val tmp_4: Boolean = DictionaryHelper.byteArrayToBoolean(tmp_0)
                    val tmp_5_content: String = tmp_4.toString()
                    val tmp_5_type: String = "http://www.w3.org/2001/XMLSchema#string"
                    DictionaryHelper.typedToByteArray(tmp_2, tmp_5_content, tmp_5_type)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.DATE_TIME -> {
                    val action = {
                        val tmp_7_typed_content: String = DictionaryHelper.byteArrayToDateTimeAsTyped_Content(tmp_0)
                        val tmp_8_content: String = tmp_7_typed_content
                        val action2 = {
                            val tmp_8_type: String = "http://www.w3.org/2001/XMLSchema#string"
                            DictionaryHelper.typedToByteArray(tmp_2, tmp_8_content, tmp_8_type)
                        }
                        action2()
                        query.getDictionary().createValue(tmp_2)
                    }
                    res = action()
                }
                ETripleComponentTypeExt.DECIMAL -> {
                    val tmp_10: BigDecimal = DictionaryHelper.byteArrayToDecimal_I(tmp_0)
                    val tmp_11_content: String = tmp_10.toString()
                    val tmp_11_type: String = "http://www.w3.org/2001/XMLSchema#string"
                    DictionaryHelper.typedToByteArray(tmp_2, tmp_11_content, tmp_11_type)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.DOUBLE -> {
                    val tmp_13: Double = DictionaryHelper.byteArrayToDouble_I(tmp_0)
                    val tmp_14_content: String = tmp_13.toString()
                    val tmp_14_type: String = "http://www.w3.org/2001/XMLSchema#string"
                    DictionaryHelper.typedToByteArray(tmp_2, tmp_14_content, tmp_14_type)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.FLOAT -> {
                    val tmp_17: Double = DictionaryHelper.byteArrayToFloat_I(tmp_0)
                    val tmp_18_content: String = tmp_17.toString()
                    val tmp_18_type: String = "http://www.w3.org/2001/XMLSchema#string"
                    DictionaryHelper.typedToByteArray(tmp_2, tmp_18_content, tmp_18_type)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.INTEGER -> {
                    val tmp_20: BigInteger = DictionaryHelper.byteArrayToInteger_I(tmp_0)
                    val tmp_21_content: String = tmp_20.toString()
                    val tmp_21_type: String = "http://www.w3.org/2001/XMLSchema#string"
                    DictionaryHelper.typedToByteArray(tmp_2, tmp_21_content, tmp_21_type)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.STRING -> {
                    val action = {
                        val tmp_24: String = DictionaryHelper.byteArrayToString(tmp_0)
                        val tmp_25_content: String = tmp_24
                        val tmp_25_type: String = "http://www.w3.org/2001/XMLSchema#string"
                        DictionaryHelper.typedToByteArray(tmp_2, tmp_25_content, tmp_25_type)
                        res = query.getDictionary().createValue(tmp_2)
                    }
                    action()
                }
                ETripleComponentTypeExt.STRING_LANG -> {
                    val action = {
                        val tmp_27_content: String = DictionaryHelper.byteArrayToLang_Content(tmp_0)
                        val tmp_27_lang: String = DictionaryHelper.byteArrayToLang_Lang(tmp_0)
                        val tmp_28_content: String = tmp_27_content
                        val tmp_28_type: String = "http://www.w3.org/2001/XMLSchema#string"
                        DictionaryHelper.typedToByteArray(tmp_2, tmp_28_content, tmp_28_type)
                        res = query.getDictionary().createValue(tmp_2)
                    }
                    action()
                }
                ETripleComponentTypeExt.STRING_TYPED -> {
                    val action = {
                        val tmp_30_content: String = DictionaryHelper.byteArrayToTyped_Content(tmp_0)
                        val tmp_30_type: String = DictionaryHelper.byteArrayToTyped_Type(tmp_0)
                        val tmp_31_content: String = tmp_30_content
                        val tmp_31_type: String = "http://www.w3.org/2001/XMLSchema#string"
                        DictionaryHelper.typedToByteArray(tmp_2, tmp_31_content, tmp_31_type)
                        res = query.getDictionary().createValue(tmp_2)
                    }
                    action()
                }
                else -> {
                    res = DictionaryValueHelper.errorValue
                }
            }
            res
        }
    }
}
