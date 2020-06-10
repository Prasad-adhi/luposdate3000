package lupos.s03resultRepresentation

import kotlin.jvm.JvmField
import lupos.s00misc.BufferManager
import lupos.s00misc.Coverage
import lupos.s00misc.File
import lupos.s00misc.MyListDouble
import lupos.s00misc.MyListInt
import lupos.s00misc.MyMapDoubleInt
import lupos.s00misc.MyMapIntInt
import lupos.s00misc.MyMapStringIntPatriciaTrie
import lupos.s00misc.MyMapStringIntPatriciaTrieDouble
import lupos.s00misc.SanityCheck
import lupos.s03resultRepresentation.nodeGlobalDictionary
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s03resultRepresentation.Value
import lupos.s03resultRepresentation.ValueBnode
import lupos.s03resultRepresentation.ValueBoolean
import lupos.s03resultRepresentation.ValueDateTime
import lupos.s03resultRepresentation.ValueDecimal
import lupos.s03resultRepresentation.ValueDefinition
import lupos.s03resultRepresentation.ValueDouble
import lupos.s03resultRepresentation.ValueError
import lupos.s03resultRepresentation.ValueFloat
import lupos.s03resultRepresentation.ValueInteger
import lupos.s03resultRepresentation.ValueIri
import lupos.s03resultRepresentation.ValueLanguageTaggedLiteral
import lupos.s03resultRepresentation.ValueSimpleLiteral
import lupos.s03resultRepresentation.ValueTypedLiteral
import lupos.s03resultRepresentation.ValueUndef

val nodeGlobalDictionary = ResultSetDictionary(true)

@UseExperimental(kotlin.ExperimentalUnsignedTypes::class)
class ResultSetDictionary(val global: Boolean = false) {
    companion object {
        /*to most bit leads to signed errors because toInt sadly performs a whole reencoding of the int and stores it completely different*/
        val mask1 = 0x40000000.toInt()/*first 2 bit*/
        val mask3 = 0x30000000.toInt()/*first 4 bit*/
        val mask6 = 0x3E000000.toInt()/*first 7 bit*/
        val filter3 = 0x0FFFFFFF.toInt()
        val filter6 = 0x01FFFFFF.toInt()
        val flaggedValueLocalBnode = 0x00000000.toInt()/*first 4 bit*/ /*required to be 0 by booleanTrueValue*/
        val flaggedValueLocalIri = 0x10000000.toInt()/*first 4 bit*/
        val flaggedValueLocalTyped = 0x20000000.toInt()/*first 4 bit*/
        val flaggedValueLocalInt = 0x30000000.toInt()/*first 7 bit*/
        val flaggedValueLocalDecimal = 0x34000000.toInt()/*first 7 bit*/
        val flaggedValueLocalDouble = 0x38000000.toInt()/*first 7 bit*/
        val flaggedValueLocalFloat = 0x3C000000.toInt()/*first 7 bit*/
        val flaggedValueLocalLangTagged = 0x3E000000.toInt()/*first 7 bit*/
        val flaggedValueGlobalBnode = 0x40000000.toInt()/*first 4 bit*/
        val flaggedValueGlobalIri = 0x50000000.toInt()/*first 4 bit*/
        val flaggedValueGlobalTyped = 0x60000000.toInt()/*first 4 bit*/
        val flaggedValueGlobalInt = 0x70000000.toInt()/*first 7 bit*/
        val flaggedValueGlobalDecimal = 0x74000000.toInt()/*first 7 bit*/
        val flaggedValueGlobalDouble = 0x78000000.toInt()/*first 7 bit*/
        val flaggedValueGlobalFloat = 0x7C000000.toInt()/*first 7 bit*/
        val flaggedValueGlobalLangTagged = 0x7E000000.toInt()/*first 7 bit*/
        @JvmField
        val booleanTrueValue = (flaggedValueLocalBnode or 0x00000000.toInt()) /*lowest 4 values*/ /*required to be 0 for_ truth table loopups*/
        @JvmField
        val booleanFalseValue = (flaggedValueLocalBnode or 0x00000001.toInt()) /*lowest 4 values*/ /*required to be 1 for_ truth table loopups*/
        @JvmField
        val errorValue = (flaggedValueLocalBnode or 0x00000002.toInt()) /*lowest 4 values*/ /*required to be 2 for_ truth table loopups*/
        @JvmField
        val undefValue = (flaggedValueLocalBnode or 0x00000003.toInt()) /*lowest 4 values*/
        @JvmField
        val booleanTrueValue2 = ValueBoolean(true)
        @JvmField
        val booleanFalseValue2 = ValueBoolean(false)
        @JvmField
        val errorValue2 = ValueError()
        @JvmField
        val undefValue2 = ValueUndef()

        fun isGlobalBNode(value: Value) = (value and mask3) == flaggedValueGlobalBnode
        fun debug() {
            SanityCheck {
                nodeGlobalDictionary.printContents()
                nodeGlobalDictionary.typedMap.safeToFile("log/dict_1")
                var tmp = ResultSetDictionary()
                tmp.typedMap.loadFromFile("log/dict_1")
                tmp.typedMap.safeToFile("log/dict_2")
                if (File("log/dict_1") != File("log/dict_2")) {
                    throw Exception("saveing and reloading the Dictionary failed")
                }
            }
        }
    }

    fun isLocalBNode(value: Value) = (value and mask3) == flaggedValueLocalBnode
    val localBnodeMap = MyMapStringIntPatriciaTrie()
    var bNodeCounter = 4
    val bnodeMapToGlobal = MyMapIntInt() // never used by the global dictionary instance
    val iriMap = MyMapStringIntPatriciaTrieDouble()
    val langTaggedMap = MyMapStringIntPatriciaTrieDouble()
    val typedMap = MyMapStringIntPatriciaTrieDouble()
    val doubleMap = MyMapDoubleInt()
    val doubleList = MyListDouble()
    val floatMap = MyMapDoubleInt()
    val floatList = MyListDouble()
    val decimalMap = MyMapDoubleInt()
    val decimalList = MyListDouble()
    val intMap = MyMapIntInt()
    val intList = MyListInt()
    fun clear() {
        localBnodeMap.clear()
        bNodeCounter = 4
        bnodeMapToGlobal.clear()
        iriMap.clear()
        langTaggedMap.clear()
        typedMap.clear()
        doubleMap.clear()
        doubleList.clear()
        floatMap.clear()
        floatList.clear()
        decimalMap.clear()
        decimalList.clear()
        intMap.clear()
        intList.clear()
    }

    fun toBooleanOrError(value: Value): Value {
        var res: Value = errorValue
        if (value < undefValue && value >= 0) {
            res = value
        } else {
            try {
                if (getValue(value).toBoolean()) {
                    res = booleanTrueValue
                } else {
                    res = booleanFalseValue
                }
            } catch (e: Throwable) {
            }
        }
//SanityCheck.check({(res and filter6) < 10000},{"${res} ${res and filter6} ${res.toString(16)} ${(res and filter6).toString(16)}"})
        return res
    }

    fun createNewBNode(value: String = ""): Value {
        var res: Value
        if (global) {
            res = (flaggedValueGlobalBnode or (bNodeCounter++).toInt())
        } else {
            res = localBnodeMap.getOrCreate(value, { (flaggedValueLocalBnode or (bNodeCounter++).toInt()) })
        }
//SanityCheck.check({(res and filter6) < 10000},{"${res} ${res and filter6} ${res.toString(16)} ${(res and filter6).toString(16)}"})
        return res
    }

    fun createIri(iri: String): Value {
        var res: Value
        if (global) {
            res = flaggedValueGlobalIri or iriMap.getOrCreate(iri)
        } else {
            var tmp = nodeGlobalDictionary.iriMap[iri]
            if (tmp != null) {
                res = tmp or flaggedValueGlobalIri
            } else {
                res = flaggedValueLocalIri or iriMap.getOrCreate(iri)
            }
        }
//SanityCheck.check({(res and filter6) < 10000},{"${res} ${res and filter6} ${res.toString(16)} ${(res and filter6).toString(16)}"})
        return res
    }

    fun createLangTagged(content: String, lang: String): Value {
        var res: Value
        val key = lang + "@" + content
        if (global) {
            res = flaggedValueGlobalLangTagged or langTaggedMap.getOrCreate(key)
        } else {
            var tmp = nodeGlobalDictionary.langTaggedMap[key]
            if (tmp != null) {
                res = tmp or flaggedValueGlobalLangTagged
            } else {
                res = flaggedValueLocalLangTagged or langTaggedMap.getOrCreate(key)
            }
        }
//SanityCheck.check({(res and filter6) < 10000},{"${res} ${res and filter6} ${res.toString(16)} ${(res and filter6).toString(16)}"})
        return res
    }

    fun createTyped(content: String, type: String): Value {
        var res: Value
        when (type) {
            "http://www.w3.org/2001/XMLSchema#integer" -> {
                res = createInteger(content.toInt())
            }
            "http://www.w3.org/2001/XMLSchema#decimal" -> {
                res = createDecimal(content.toDouble())
            }
            "http://www.w3.org/2001/XMLSchema#double" -> {
                res = createDouble(content.toDouble())
            }
            "http://www.w3.org/2001/XMLSchema#float" -> {
                res = createFloat(content.toDouble())
            }
            "http://www.w3.org/2001/XMLSchema#boolean" -> {
                if (content == "true") {
                    res = booleanTrueValue
                } else {
                    res = booleanFalseValue
                }
            }
            else -> {
                val key = type + ">" + content
                if (global) {
                    res = flaggedValueGlobalTyped or typedMap.getOrCreate(key)
                } else {
                    var tmp = nodeGlobalDictionary.typedMap[key]
                    if (tmp != null) {
                        res = tmp or flaggedValueGlobalTyped
                    } else {
                        res = flaggedValueLocalTyped or typedMap.getOrCreate(key)
                    }
                }
            }
        }
//SanityCheck.check({(res and filter6) < 10000},{"${res} ${res and filter6} ${res.toString(16)} ${(res and filter6).toString(16)}"})
        return res
    }

    fun createDouble(value: Double): Value {
        var res: Value
        if (global) {
            res = doubleMap.getOrCreate(value, {
                val idx = doubleList.size
                doubleList.add(value)
                /*return*/ (flaggedValueGlobalDouble or idx.toInt())
            })
        } else {
            val tmp = nodeGlobalDictionary.doubleMap[value]
            if (tmp != null) {
                res = tmp or flaggedValueGlobalDouble
            } else {
                res = doubleMap.getOrCreate(value, {
                    val idx = doubleList.size
                    doubleList.add(value)
                    /*return*/ (flaggedValueLocalDouble or idx.toInt())
                })
            }
        }
//SanityCheck.check({(res and filter6) < 10000},{"${res} ${res and filter6} ${res.toString(16)} ${(res and filter6).toString(16)}"})
        return res
    }

    fun createFloat(value: Double): Value {
        var res: Value
        if (global) {
            res = floatMap.getOrCreate(value, {
                val idx = floatList.size
                floatList.add(value)
                /*return*/ (flaggedValueGlobalFloat or idx.toInt())
            })
        } else {
            val tmp = nodeGlobalDictionary.floatMap[value]
            if (tmp != null) {
                res = tmp or flaggedValueGlobalFloat
            } else {
                res = floatMap.getOrCreate(value, {
                    val idx = floatList.size
                    floatList.add(value)
                    /*return*/ (flaggedValueLocalFloat or idx.toInt())
                })
            }
        }
//SanityCheck.check({(res and filter6) < 10000},{"${res} ${res and filter6} ${res.toString(16)} ${(res and filter6).toString(16)}"})
        return res
    }

    fun createDecimal(value: Double): Value {
        var res: Value
        if (global) {
            res = decimalMap.getOrCreate(value, {
                val idx = decimalList.size
                decimalList.add(value)
                /*return*/ (flaggedValueGlobalDecimal or idx.toInt())
            })
        } else {
            val tmp = nodeGlobalDictionary.decimalMap[value]
            if (tmp != null) {
                res = tmp or flaggedValueGlobalDecimal
            } else {
                res = decimalMap.getOrCreate(value, {
                    val idx = decimalList.size
                    decimalList.add(value)
                    /*return*/ (flaggedValueLocalDecimal or idx.toInt())
                })
            }
        }
//SanityCheck.check({(res and filter6) < 10000},{"${res} ${res and filter6} ${res.toString(16)} ${(res and filter6).toString(16)}"})
        return res
    }

    fun createInteger(value: Int): Value {
        var res: Value
        if (global) {
            res = intMap.getOrCreate(value, {
                val idx = intList.size
                intList.add(value)
                /*return*/(flaggedValueGlobalInt or idx.toInt())
            })
        } else {
            val tmp = nodeGlobalDictionary.intMap[value]
            if (tmp != null) {
                res = tmp or flaggedValueGlobalInt
            } else {
                res = intMap.getOrCreate(value, {
                    val idx = intList.size
                    intList.add(value)
                    /*return*/(flaggedValueLocalInt or idx.toInt())
                })
            }
        }
//SanityCheck.check({(res and filter6) < 10000},{"${res} ${res and filter6} ${res.toString(16)} ${(res and filter6).toString(16)}"})
        return res
    }

    fun createValue(value: String?): Value {
        val res = createValue(ValueDefinition(value))
//SanityCheck.check({(res and filter6) < 10000},{"${res} ${res and filter6} ${res.toString(16)} ${(res and filter6).toString(16)}"})
        return res
    }

    fun createValue(value: ValueDefinition): Value {
        var res: Value
        when (value) {
            is ValueUndef -> {
                res = undefValue
            }
            is ValueError -> {
                res = errorValue
            }
            is ValueBnode -> {
                res = createNewBNode(value.value)
            }
            is ValueBoolean -> {
                if (value.value) {
                    res = booleanTrueValue
                } else {
                    res = booleanFalseValue
                }
            }
            is ValueLanguageTaggedLiteral -> {
                res = createLangTagged(value.content, value.language)
            }
            is ValueSimpleLiteral -> {
                res = createTyped(value.content, "")
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
            SanityCheck.check({ (value is ValueBnode && tmp2 is ValueBnode) || (value is ValueError && tmp2 is ValueError) || tmp2 == value || (value is ValueSimpleLiteral && tmp2 is ValueTypedLiteral && tmp2.type_iri == "http://www.w3.org/2001/XMLSchema#string" && tmp2.content == value.content) }, { "$value (${value.toSparql()}) -> ${res.toString(16)} -> ${tmp2} (${tmp2.toSparql()})" })
        }
//SanityCheck.check({(res and filter6) < 10000},{"${res} ${res and filter6} ${res.toString(16)} ${(res and filter6).toString(16)}"})
        return res
    }

    fun getValue(value: Value): ValueDefinition {
//SanityCheck.check({(value and filter6) < 10000},{"${value} ${value and filter6} ${value.toString(16)} ${(value and filter6).toString(16)}"})
        var res: ValueDefinition
        val dict: ResultSetDictionary
        if ((value and mask1) == mask1) {
            dict = nodeGlobalDictionary
        } else {
            dict = this
        }
        var bit3 = value and mask3
        if (bit3 == flaggedValueLocalIri) {
            res = ValueIri(dict.iriMap[value and filter3])
        } else if (bit3 == flaggedValueLocalBnode) {
            when (value) {
                0 -> {
                    res = booleanTrueValue2
                }
                1 -> {
                    res = booleanFalseValue2
                }
                2 -> {
                    res = errorValue2
                }
                3 -> {
                    res = undefValue2
                }
                else -> {
                    res = ValueBnode("" + value)
                }
            }
        } else if (bit3 == flaggedValueLocalTyped) {
            val tmp = dict.typedMap[value and filter3]
            var idx = tmp.indexOf(">")
            var type = tmp.substring(0, idx)
            var content = tmp.substring(idx + 1, tmp.length)
            if (idx == 0) {
                res = ValueSimpleLiteral("\"", content)
            } else {
                res = ValueTypedLiteral("\"", content, type)
            }
        } else {
            var bit5 = value and mask6
            if (bit5 == flaggedValueLocalInt) {
                res = ValueInteger(dict.intList[value and filter6])
            } else if (bit5 == flaggedValueLocalDecimal) {
                res = ValueDecimal(dict.decimalList[value and filter6])
            } else if (bit5 == flaggedValueLocalDouble) {
                res = ValueDouble(dict.doubleList[value and filter6])
            } else if (bit5 == flaggedValueLocalFloat) {
                res = ValueFloat(dict.floatList[value and filter6])
            } else {
                val tmp = dict.langTaggedMap[value and filter6]
                var idx = tmp.indexOf("@")
                var lang = tmp.substring(0, idx)
                var content = tmp.substring(idx + 1, tmp.length)
                res = ValueLanguageTaggedLiteral("\"", content, lang)
            }
        }
        return res
    }

    fun printContents() {
        SanityCheck {
            var base: Int
            if (global) {
                base = mask1
            } else {
                base = 0
            }
            for (i in 0 until iriMap.size) {
                println("debug dict - iri :: ${i + base + flaggedValueLocalIri} -> ${iriMap[i]}")
            }
            for (i in 0 until typedMap.size) {
                println("debug dict - typed :: ${i + base + flaggedValueLocalTyped} -> ${typedMap[i]}")
            }
            for (i in 0 until intList.size) {
                println("debug dict - int :: ${i + base + flaggedValueLocalInt} -> ${intList[i]}")
            }
            for (i in 0 until decimalList.size) {
                println("debug dict - decimal :: ${i + base + flaggedValueLocalDecimal} -> ${decimalList[i]}")
            }
            for (i in 0 until doubleList.size) {
                println("debug dict - double :: ${i + base + flaggedValueLocalDouble} -> ${doubleList[i]}")
            }
            for (i in 0 until floatList.size) {
                println("debug dict - float :: ${i + base + flaggedValueLocalFloat} -> ${floatList[i]}")
            }
            for (i in 0 until langTaggedMap.size) {
                println("debug dict - langTagged :: ${i + base + flaggedValueLocalLangTagged} -> ${langTaggedMap[i]}")
            }
        }
    }

    fun valueToGlobal(value: Value): Value {
        var res: Value
        if ((value and mask1) == mask1) {
            res = value
        } else {
            if (isLocalBNode(value)) {
                res = bnodeMapToGlobal.getOrCreate(value, { nodeGlobalDictionary.createNewBNode() })
            } else {
                res = nodeGlobalDictionary.createValue(getValue(value))
            }
        }
//SanityCheck.check({(res and filter6) < 10000},{"${res} ${res and filter6} ${res.toString(16)} ${(res and filter6).toString(16)}"})
        return res
    }

    fun safeToFolder() {
        //println("Dictionary safe to folder '${BufferManager.bufferPrefix + "dictionary/"}'")
        File(BufferManager.bufferPrefix + "dictionary/").mkdirs()
        File(BufferManager.bufferPrefix + "dictionary/bnode.count").dataOutputStream { out ->
            out.writeInt(bNodeCounter)
        }
        iriMap.safeToFile(BufferManager.bufferPrefix + "dictionary/iri.map")
        langTaggedMap.safeToFile(BufferManager.bufferPrefix + "dictionary/langTagged.map")
        typedMap.safeToFile(BufferManager.bufferPrefix + "dictionary/typed.map")
        doubleMap.safeToFile(BufferManager.bufferPrefix + "dictionary/double.map")
        floatMap.safeToFile(BufferManager.bufferPrefix + "dictionary/float.map")
        decimalMap.safeToFile(BufferManager.bufferPrefix + "dictionary/decimal.map")
        intMap.safeToFile(BufferManager.bufferPrefix + "dictionary/int.map")
    }

    fun loadFromFolder() {
        //println("Dictionary loading from folder '${BufferManager.bufferPrefix + "dictionary/"}'")
        File(BufferManager.bufferPrefix + "dictionary/bnode.count").dataInputStream { fis ->
            bNodeCounter = fis.readInt()
        }
        iriMap.loadFromFile(BufferManager.bufferPrefix + "dictionary/iri.map")
        langTaggedMap.loadFromFile(BufferManager.bufferPrefix + "dictionary/langTagged.map")
        typedMap.loadFromFile(BufferManager.bufferPrefix + "dictionary/typed.map")
        doubleMap.loadFromFile(BufferManager.bufferPrefix + "dictionary/double.map")
        floatMap.loadFromFile(BufferManager.bufferPrefix + "dictionary/float.map")
        decimalMap.loadFromFile(BufferManager.bufferPrefix + "dictionary/decimal.map")
        intMap.loadFromFile(BufferManager.bufferPrefix + "dictionary/int.map")
        doubleList.clear()
        doubleMap.forEach { k, v2 ->
            val v = v2 and filter6
            while (v > doubleList.size) {
                doubleList.add(k)
            }
            doubleList[v] = k
        }
        floatList.clear()
        floatMap.forEach { k, v2 ->
            val v = v2 and filter6
            while (v > floatList.size) {
                floatList.add(k)
            }
            floatList[v] = k
        }
        decimalList.clear()
        decimalMap.forEach { k, v2 ->
            val v = v2 and filter6
            while (v > decimalList.size) {
                decimalList.add(k)
            }
            decimalList[v] = k
        }
        intList.clear()
        intMap.forEach { k, v2 ->
            val v = v2 and filter6
            while (v > intList.size) {
                intList.add(k)
            }
            intList[v] = k
        }
    }
}
