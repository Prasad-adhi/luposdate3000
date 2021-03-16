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
package lupos.s03resultRepresentation

import lupos.s00misc.ETripleComponentTypeExt
import lupos.s00misc.MyBigDecimal
import lupos.s00misc.MyBigInteger
import lupos.s00misc.SanityCheck
import kotlin.jvm.JvmField

public class ResultSetDictionary : IResultSetDictionary {
    @Suppress("NOTHING_TO_INLINE")
    internal inline fun isLocalBNode(value: Int) = (value and ResultSetDictionaryShared.mask3) == ResultSetDictionaryShared.flaggedValueLocalBnode

    @JvmField
    internal val localBnodeToInt = mutableMapOf<String, Int>()

    @JvmField
    internal var bNodeCounter = 5

    @JvmField
    internal val bnodeMapToGlobal = mutableMapOf<Int, Int>()

    @JvmField
    internal val iriToInt = mutableMapOf<String, Int>()

    @JvmField
    internal var iriToValue = Array(1) { ResultSetDictionaryShared.emptyString }

    @JvmField
    internal val langTaggedToInt = mutableMapOf<String, Int>()

    @JvmField
    internal var langTaggedToValue = Array(1) { ResultSetDictionaryShared.emptyString }

    @JvmField
    internal val typedToInt = mutableMapOf<String, Int>()

    @JvmField
    internal var typedToValue = Array(1) { ResultSetDictionaryShared.emptyString }

    @JvmField
    internal val doubleToInt = mutableMapOf<Double, Int>()

    @JvmField
    internal var doubleToValue = DoubleArray(1) { 0.0 }

    @JvmField
    internal val floatToInt = mutableMapOf<Double, Int>()

    @JvmField
    internal var floatToValue = DoubleArray(1) { 0.0 }

    @JvmField
    internal val decimalToInt = mutableMapOf<String, Int>()

    @JvmField
    internal var decimalToValue = Array(1) { ResultSetDictionaryShared.emptyString }

    @JvmField
    internal val intToInt = mutableMapOf<String, Int>()

    @JvmField
    internal var intToValue = Array(1) { ResultSetDictionaryShared.emptyString }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun prepareBulk(total: Int, typed: IntArray) {
        for (t in 0 until ETripleComponentTypeExt.values_size) {
            when (t) {
                ETripleComponentTypeExt.IRI -> {
                    val tmp = Array(iriToValue.size + typed[t]) { ResultSetDictionaryShared.emptyString }
                    for (i in iriToValue.indices) {
                        tmp[i] = iriToValue[i]
                    }
                    iriToValue = tmp
                }
                ETripleComponentTypeExt.BLANK_NODE -> {
                }
                ETripleComponentTypeExt.STRING -> {
                    val tmp = Array(typedToValue.size + typed[t]) { ResultSetDictionaryShared.emptyString }
                    for (i in typedToValue.indices) {
                        tmp[i] = typedToValue[i]
                    }
                    typedToValue = tmp
                }
                ETripleComponentTypeExt.INTEGER -> {
                    val tmp = Array(intToValue.size + typed[t]) { ResultSetDictionaryShared.emptyString }
                    for (i in intToValue.indices) {
                        tmp[i] = intToValue[i]
                    }
                    intToValue = tmp
                }
                ETripleComponentTypeExt.DECIMAL -> {
                    val tmp = Array(decimalToValue.size + typed[t]) { ResultSetDictionaryShared.emptyString }
                    for (i in decimalToValue.indices) {
                        tmp[i] = decimalToValue[i]
                    }
                    decimalToValue = tmp
                }
                ETripleComponentTypeExt.DOUBLE -> {
                    val tmp = DoubleArray(doubleToValue.size + typed[t]) { 0.0 }
                    for (i in doubleToValue.indices) {
                        tmp[i] = doubleToValue[i]
                    }
                    doubleToValue = tmp
                }
                ETripleComponentTypeExt.BOOLEAN -> {
                }
                ETripleComponentTypeExt.STRING_TYPED -> {
                    val tmp = Array(typedToValue.size + typed[t]) { ResultSetDictionaryShared.emptyString }
                    for (i in typedToValue.indices) {
                        tmp[i] = typedToValue[i]
                    }
                    typedToValue = tmp
                }
                ETripleComponentTypeExt.STRING_LANG -> {
                    val tmp = Array(langTaggedToValue.size + typed[t]) { ResultSetDictionaryShared.emptyString }
                    for (i in langTaggedToValue.indices) {
                        tmp[i] = langTaggedToValue[i]
                    }
                    langTaggedToValue = tmp
                }
                else -> {
                    throw Exception("unexpected type")
                }
            }
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun clear() {
        localBnodeToInt.clear()
        bNodeCounter = 5
        bnodeMapToGlobal.clear()
        iriToInt.clear()
        langTaggedToInt.clear()
        typedToInt.clear()
        doubleToInt.clear()
        floatToInt.clear()
        decimalToInt.clear()
        intToInt.clear()
        iriToValue = Array(1) { ResultSetDictionaryShared.emptyString }
        langTaggedToValue = Array(1) { ResultSetDictionaryShared.emptyString }
        typedToValue = Array(1) { ResultSetDictionaryShared.emptyString }
        doubleToValue = DoubleArray(1) { 0.0 }
        floatToValue = DoubleArray(1) { 0.0 }
        decimalToValue = Array(1) { ResultSetDictionaryShared.emptyString }
        intToValue = Array(1) { ResultSetDictionaryShared.emptyString }
    }

    override fun toBooleanOrError(value: Int): Int {
        var res: Int = ResultSetDictionaryExt.errorValue
        if (value < ResultSetDictionaryExt.undefValue && value >= 0) {
            res = value
        } else {
            try {
                res = if (getValue(value).toBoolean()) {
                    ResultSetDictionaryExt.booleanTrueValue
                } else {
                    ResultSetDictionaryExt.booleanFalseValue
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        return res
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun createNewBNode(value: String = ResultSetDictionaryShared.emptyString): Int {
        val res: Int
        val tmp = localBnodeToInt[value]
        if (tmp == null) {
            res = ResultSetDictionaryShared.flaggedValueLocalBnode or (bNodeCounter++)
            localBnodeToInt[value] = res
        } else {
            res = tmp
        }
        return res
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun createIri(iri: String): Int {
        var res: Int
        val tmp3 = iriToInt[iri]
        if (tmp3 == null) {
            res = iriToInt.size
            iriToInt[iri] = res
            if (iriToValue.size <= res) {
                val tmp2 = Array(iriToValue.size * 2) { ResultSetDictionaryShared.emptyString }
                for (i in iriToValue.indices) {
                    tmp2[i] = iriToValue[i]
                }
                iriToValue = tmp2
            }
            iriToValue[res] = iri
            res = res or ResultSetDictionaryShared.flaggedValueLocalIri
        } else {
            res = tmp3 or ResultSetDictionaryShared.flaggedValueLocalIri
        }
        return res
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun createLangTagged(content: String, lang: String): Int {
        var res: Int
        val key = "$lang@$content"
        val tmp3 = langTaggedToInt[key]
        if (tmp3 == null) {
            res = langTaggedToInt.size
            langTaggedToInt[key] = res
            if (langTaggedToValue.size <= res) {
                val tmp2 = Array(langTaggedToValue.size * 2) { ResultSetDictionaryShared.emptyString }
                for (i in langTaggedToValue.indices) {
                    tmp2[i] = langTaggedToValue[i]
                }
                langTaggedToValue = tmp2
            }
            langTaggedToValue[res] = key
            res = res or ResultSetDictionaryShared.flaggedValueLocalLangTagged
        } else {
            res = tmp3 or ResultSetDictionaryShared.flaggedValueLocalLangTagged
        }
        return res
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun createTyped(content: String, type: String): Int {
        var res: Int
        when (type) {
            "http://www.w3.org/2001/XMLSchema#integer" -> {
                res = createInteger(MyBigInteger(content))
            }
            "http://www.w3.org/2001/XMLSchema#decimal" -> {
                res = createDecimal(MyBigDecimal(content))
            }
            "http://www.w3.org/2001/XMLSchema#double" -> {
                res = createDouble(content.toDouble())
            }
            "http://www.w3.org/2001/XMLSchema#float" -> {
                res = createFloat(content.toDouble())
            }
            "http://www.w3.org/2001/XMLSchema#boolean" -> {
                res = if (content == "true") {
                    ResultSetDictionaryExt.booleanTrueValue
                } else {
                    ResultSetDictionaryExt.booleanFalseValue
                }
            }
            else -> {
                val key = "$type>$content"
                val tmp3 = typedToInt[key]
                if (tmp3 == null) {
                    res = typedToInt.size
                    typedToInt[key] = res
                    if (typedToValue.size <= res) {
                        val tmp2 = Array(typedToValue.size * 2) { ResultSetDictionaryShared.emptyString }
                        for (i in typedToValue.indices) {
                            tmp2[i] = typedToValue[i]
                        }
                        typedToValue = tmp2
                    }
                    typedToValue[res] = key
                    res = res or ResultSetDictionaryShared.flaggedValueLocalTyped
                } else {
                    res = tmp3 or ResultSetDictionaryShared.flaggedValueLocalTyped
                }
            }
        }
        return res
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun createDouble(value: Double): Int {
        var res: Int
        val tmp3 = doubleToInt[value]
        if (tmp3 == null) {
            res = doubleToInt.size
            doubleToInt[value] = res
            if (doubleToValue.size <= res) {
                val tmp2 = DoubleArray(doubleToValue.size * 2) { 0.0 }
                for (i in doubleToValue.indices) {
                    tmp2[i] = doubleToValue[i]
                }
                doubleToValue = tmp2
            }
            doubleToValue[res] = value
            res = res or ResultSetDictionaryShared.flaggedValueLocalDouble
        } else {
            res = tmp3 or ResultSetDictionaryShared.flaggedValueLocalDouble
        }
        return res
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun createFloat(value: Double): Int {
        var res: Int
        val tmp3 = floatToInt[value]
        if (tmp3 == null) {
            res = floatToInt.size
            floatToInt[value] = res
            if (floatToValue.size <= res) {
                val tmp2 = DoubleArray(floatToValue.size * 2) { 0.0 }
                for (i in floatToValue.indices) {
                    tmp2[i] = floatToValue[i]
                }
                floatToValue = tmp2
            }
            floatToValue[res] = value
            res = res or ResultSetDictionaryShared.flaggedValueLocalFloat
        } else {
            res = tmp3 or ResultSetDictionaryShared.flaggedValueLocalFloat
        }
        return res
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun createDecimal(value2: MyBigDecimal): Int {
        val value = value2.toString()
        var res: Int
        val tmp3 = decimalToInt[value]
        if (tmp3 == null) {
            res = decimalToInt.size
            decimalToInt[value] = res
            if (decimalToValue.size <= res) {
                val tmp2 = Array(decimalToValue.size * 2) { ResultSetDictionaryShared.emptyString }
                for (i in decimalToValue.indices) {
                    tmp2[i] = decimalToValue[i]
                }
                decimalToValue = tmp2
            }
            decimalToValue[res] = value
            res = res or ResultSetDictionaryShared.flaggedValueLocalDecimal
        } else {
            res = tmp3 or ResultSetDictionaryShared.flaggedValueLocalDecimal
        }
        return res
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun createInteger(value2: MyBigInteger): Int {
        val value = value2.toString()
        var res: Int
        val tmp3 = intToInt[value]
        if (tmp3 == null) {
            res = intToInt.size
            intToInt[value] = res
            if (intToValue.size <= res) {
                val tmp2 = Array(intToValue.size * 2) { ResultSetDictionaryShared.emptyString }
                for (i in intToValue.indices) {
                    tmp2[i] = intToValue[i]
                }
                intToValue = tmp2
            }
            intToValue[res] = value
            res = res or ResultSetDictionaryShared.flaggedValueLocalInt
        } else {
            res = tmp3 or ResultSetDictionaryShared.flaggedValueLocalInt
        }
        return res
    }

    override fun createValue(value: String?): Int {
        val res = createValue(ValueDefinition(value))
        return res
    }

    override fun createValue(value: ValueDefinition): Int {
        val tmp = nodeGlobalDictionary.hasValue(value)
        if (tmp != null) {
            return tmp
        }
        val res: Int
        when (value) {
            is ValueUndef -> {
                res = ResultSetDictionaryExt.undefValue
            }
            is ValueError -> {
                res = ResultSetDictionaryExt.errorValue
            }
            is ValueBnode -> {
                res = createNewBNode(value.value)
            }
            is ValueBoolean -> {
                res = if (value.value) {
                    ResultSetDictionaryExt.booleanTrueValue
                } else {
                    ResultSetDictionaryExt.booleanFalseValue
                }
            }
            is ValueLanguageTaggedLiteral -> {
                res = createLangTagged(value.content, value.language)
            }
            is ValueSimpleLiteral -> {
                res = createTyped(value.content, ResultSetDictionaryShared.emptyString)
            }
            is ValueTypedLiteral -> {
                res = createTyped(value.content, value.type_iri)
            }
            is ValueDecimal -> {
                res = createDecimal(value.value)
            }
            is ValueDouble -> {
                res = createDouble(value.value)
            }
            is ValueFloat -> {
                res = createFloat(value.value)
            }
            is ValueInteger -> {
                res = createInteger(value.value)
            }
            is ValueIri -> {
                res = createIri(value.iri)
            }
            is ValueDateTime -> {
                val tmp = value.valueToString()
                res = createTyped(tmp.substring(1, tmp.length - 1 - "^^<http://www.w3.org/2001/XMLSchema#dateTime>".length), "http://www.w3.org/2001/XMLSchema#dateTime")
            }
        }
        SanityCheck {
            val tmp2 = getValue(res)
            SanityCheck.check({ (value is ValueBnode && tmp2 is ValueBnode) || (value is ValueError && tmp2 is ValueError) || tmp2 == value || (value is ValueSimpleLiteral && tmp2 is ValueTypedLiteral && tmp2.type_iri == "http://www.w3.org/2001/XMLSchema#string" && tmp2.content == value.content) }, { "$value (${value.toSparql()}) -> ${res.toString(16)} -> $tmp2 (${tmp2.toSparql()})" })
        }
        return res
    }

    override fun getValue(value: Int): ValueDefinition {
        val res: ValueDefinition
        if ((value and ResultSetDictionaryShared.mask1) == ResultSetDictionaryShared.mask1) {
            return nodeGlobalDictionary.getValue(value)
        }
        when (value and ResultSetDictionaryShared.mask3) {
            ResultSetDictionaryShared.flaggedValueLocalIri -> {
                res = ValueIri(iriToValue[value and ResultSetDictionaryShared.filter3])
            }
            ResultSetDictionaryShared.flaggedValueLocalBnode -> {
                when (value) {
                    0 -> {
                        res = ResultSetDictionaryExt.booleanTrueValue2
                    }
                    1 -> {
                        res = ResultSetDictionaryExt.booleanFalseValue2
                    }
                    2 -> {
                        res = ResultSetDictionaryExt.errorValue2
                    }
                    3 -> {
                        res = ResultSetDictionaryExt.undefValue2
                    }
                    else -> {
                        res = ValueBnode(ResultSetDictionaryShared.emptyString + value)
                    }
                }
            }
            ResultSetDictionaryShared.flaggedValueLocalTyped -> {
                val tmp = typedToValue[value and ResultSetDictionaryShared.filter3]
                val idx = tmp.indexOf(">")
                val type = tmp.substring(0, idx)
                val content = tmp.substring(idx + 1, tmp.length)
                res = if (idx == 0) {
                    ValueSimpleLiteral("\"", content)
                } else {
                    ValueTypedLiteral("\"", content, type)
                }
            }
            else -> {
                val bit5 = value and ResultSetDictionaryShared.mask6
                res = when (bit5) {
                    ResultSetDictionaryShared.flaggedValueLocalInt -> {
                        ValueInteger(MyBigInteger(intToValue[value and ResultSetDictionaryShared.filter6]))
                    }
                    ResultSetDictionaryShared.flaggedValueLocalDecimal -> {
                        ValueDecimal(MyBigDecimal(decimalToValue[value and ResultSetDictionaryShared.filter6]))
                    }
                    ResultSetDictionaryShared.flaggedValueLocalDouble -> {
                        ValueDouble(doubleToValue[value and ResultSetDictionaryShared.filter6])
                    }
                    ResultSetDictionaryShared.flaggedValueLocalFloat -> {
                        ValueFloat(floatToValue[value and ResultSetDictionaryShared.filter6])
                    }
                    else -> {
                        val tmp = langTaggedToValue[value and ResultSetDictionaryShared.filter6]
                        val idx = tmp.indexOf("@")
                        val lang = tmp.substring(0, idx)
                        val content = tmp.substring(idx + 1, tmp.length)
                        ValueLanguageTaggedLiteral("\"", content, lang)
                    }
                }
            }
        }
        return res
    }

    override fun getValue(
        value: Int,
        onBNode: (value: Int) -> Unit,
        onBoolean: (value: Boolean) -> Unit,
        onLanguageTaggedLiteral: (content: String, lang: String) -> Unit,
        onSimpleLiteral: (content: String) -> Unit,
        onTypedLiteral: (content: String, type: String) -> Unit,
        onDecimal: (value: String) -> Unit,
        onFloat: (value: Double) -> Unit,
        onDouble: (value: Double) -> Unit,
        onInteger: (value: String) -> Unit,
        onIri: (value: String) -> Unit,
        onError: () -> Unit,
        onUndefined: () -> Unit
    ) {
        if ((value and ResultSetDictionaryShared.mask1) == ResultSetDictionaryShared.mask1) {
            nodeGlobalDictionary.getValue(value, onBNode, onBoolean, onLanguageTaggedLiteral, onSimpleLiteral, onTypedLiteral, onDecimal, onFloat, onDouble, onInteger, onIri, onError, onUndefined)
        } else {
            when (value and ResultSetDictionaryShared.mask3) {
                ResultSetDictionaryShared.flaggedValueLocalIri -> {
                    onIri(iriToValue[value and ResultSetDictionaryShared.filter3])
                }
                ResultSetDictionaryShared.flaggedValueLocalBnode -> {
                    when (value) {
                        0 -> {
                            onBoolean(true)
                        }
                        1 -> {
                            onBoolean(false)
                        }
                        2 -> {
                            onError()
                        }
                        3 -> {
                            onUndefined()
                        }
                        else -> {
                            onBNode(value)
                        }
                    }
                }
                ResultSetDictionaryShared.flaggedValueLocalTyped -> {
                    val tmp = typedToValue[value and ResultSetDictionaryShared.filter3]
                    val idx = tmp.indexOf(">")
                    if (idx == 0) {
                        onSimpleLiteral(tmp.substring(idx + 2, tmp.length - 1))
                    } else {
                        onTypedLiteral(tmp.substring(idx + 2, tmp.length - 1), tmp.substring(1, idx - 1))
                    }
                }
                else -> {
                    when (value and ResultSetDictionaryShared.mask6) {
                        ResultSetDictionaryShared.flaggedValueLocalInt -> {
                            onInteger(intToValue[value and ResultSetDictionaryShared.filter6])
                        }
                        ResultSetDictionaryShared.flaggedValueLocalDecimal -> {
                            onDecimal(decimalToValue[value and ResultSetDictionaryShared.filter6])
                        }
                        ResultSetDictionaryShared.flaggedValueLocalDouble -> {
                            onDouble(doubleToValue[value and ResultSetDictionaryShared.filter6])
                        }
                        ResultSetDictionaryShared.flaggedValueLocalFloat -> {
                            onFloat(floatToValue[value and ResultSetDictionaryShared.filter6])
                        }
                        else -> {
                            val tmp = langTaggedToValue[value and ResultSetDictionaryShared.filter6]
                            val idx = tmp.indexOf("@")
                            onLanguageTaggedLiteral(tmp.substring(idx + 2, tmp.length - 1), tmp.substring(0, idx))
                        }
                    }
                }
            }
        }
    }

    override fun valueToGlobal(value: Int): Int {
        val res: Int
        if ((value and ResultSetDictionaryShared.mask1) == ResultSetDictionaryShared.mask1) {
            res = value
        } else {
            if (isLocalBNode(value)) {
                val tmp = bnodeMapToGlobal[value]
                if (tmp == null) {
                    res = nodeGlobalDictionary.createNewBNode()
                    bnodeMapToGlobal[value] = res
                } else {
                    res = tmp
                }
            } else {
                res = nodeGlobalDictionary.createValue(getValue(value))
            }
        }
        return res
    }
}
