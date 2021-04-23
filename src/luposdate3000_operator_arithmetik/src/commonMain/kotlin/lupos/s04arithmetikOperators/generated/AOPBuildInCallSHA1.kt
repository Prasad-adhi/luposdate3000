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
package lupos.operator_arithmetik.generated

import lupos.dictionary.DictionaryExt
import lupos.operator_arithmetik.AOPBase
import lupos.operator_logical.IOPBase
import lupos.operator_logical.IQuery
import lupos.operator_logical.iterator.IteratorBundle
import lupos.s00misc.ByteArrayWrapper
import lupos.s00misc.Crypto
import lupos.s00misc.EOperatorIDExt
import lupos.s00misc.ETripleComponentType
import lupos.s00misc.ETripleComponentTypeExt
import lupos.shared_inline.DictionaryHelper

public class AOPBuildInCallSHA1 public constructor(query: IQuery, child0: AOPBase, ) : AOPBase(query, EOperatorIDExt.AOPBuildInCallSHA1ID, "AOPBuildInCallSHA1", arrayOf(child0, )) {
    override fun toSparql(): String = "SHA1(${children[0].toSparql()})"
    override fun equals(other: Any?): Boolean = other is AOPBuildInCallSHA1 && children[0] == other.children[0]
    override fun cloneOP(): IOPBase = AOPBuildInCallSHA1(query, children[0].cloneOP() as AOPBase)
    override fun evaluateID(row: IteratorBundle): () -> Int {
        val tmp_0: ByteArrayWrapper = ByteArrayWrapper()
        val tmp_2: ByteArrayWrapper = ByteArrayWrapper()
        val child0: () -> Int = (children[0] as AOPBase).evaluateID(row)
        return {
            var res: Int
            val childIn0: Int = child0()
            query.getDictionary().getValue(tmp_0, childIn0)
            val tmp_1: ETripleComponentType = DictionaryHelper.byteArrayToType(tmp_0)
            when (tmp_1) {
                ETripleComponentTypeExt.BLANK_NODE -> {
                    DictionaryHelper.errorToByteArray(tmp_2)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.BOOLEAN -> {
                    DictionaryHelper.errorToByteArray(tmp_2)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.DATE_TIME -> {
                    DictionaryHelper.errorToByteArray(tmp_2)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.DECIMAL -> {
                    DictionaryHelper.errorToByteArray(tmp_2)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.DOUBLE -> {
                    DictionaryHelper.errorToByteArray(tmp_2)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.ERROR -> {
                    DictionaryHelper.errorToByteArray(tmp_2)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.FLOAT -> {
                    DictionaryHelper.errorToByteArray(tmp_2)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.INTEGER -> {
                    DictionaryHelper.errorToByteArray(tmp_2)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.IRI -> {
                    DictionaryHelper.errorToByteArray(tmp_2)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.STRING -> {
                    val tmp_12: String = DictionaryHelper.byteArrayToString(tmp_0)
                    val tmp_13: String = Crypto.sha1(tmp_12)
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_13)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.STRING_LANG -> {
                    val tmp_15_content: String = DictionaryHelper.byteArrayToLang_Content(tmp_0)
                    val tmp_15_lang: String = DictionaryHelper.byteArrayToLang_Lang(tmp_0)
                    val tmp_16: String = Crypto.sha1(tmp_15_content)
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_16)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.STRING_TYPED -> {
                    val tmp_18_content: String = DictionaryHelper.byteArrayToTyped_Content(tmp_0)
                    val tmp_18_type: String = DictionaryHelper.byteArrayToTyped_Type(tmp_0)
                    val tmp_19: String = Crypto.sha1(tmp_18_content)
                    DictionaryHelper.stringToByteArray(tmp_2, tmp_19)
                    res = query.getDictionary().createValue(tmp_2)
                }
                ETripleComponentTypeExt.UNDEF -> {
                    DictionaryHelper.errorToByteArray(tmp_2)
                    res = query.getDictionary().createValue(tmp_2)
                }
                else -> {
                    res = DictionaryExt.errorValue
                }
            }
            res
        }
    }
}
