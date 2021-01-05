package lupos.s03resultRepresentation
import lupos.s00misc.File
import lupos.s00misc.MyMapIntInt
import lupos.s01io.BufferManager
import kotlin.jvm.JvmField
import kotlin.jvm.JvmName
val nodeGlobalDictionary = ResultSetDictionary(true)
class ResultSetDictionary(val global: Boolean = false) {
    companion object {
        const val booleanTrueValue = 0 // required by truth-tables
        @JvmField
        val booleanTrueValue2 = ValueBoolean(true)
        const val booleanFalseValue = 1 // required by truth-tables
        @JvmField
        val booleanFalseValue2 = ValueBoolean(false)
        const val errorValue = 2 // required by truth-tables
        @JvmField
        val errorValue2 = ValueError()
        const val undefValue = 3
        @JvmField
        val undefValue2 = ValueUndef()
        fun debug() {
        }
        fun isGlobalBNode(value: Int): Boolean {
            if (value >= 0) {
                return false
            }
            return nodeGlobalDictionary.getValue(value) is ValueBnode
        }
    }
    fun isLocalBNode(value: Int): Boolean {
        if (value < 0) {
            return false
        }
        return getValue(value) is ValueBnode
    }
    val bnodeMapToGlobal = MyMapIntInt()
    var mapSTL = mutableMapOf<ValueDefinition, Int>(errorValue2 to errorValue, booleanTrueValue2 to booleanTrueValue, booleanFalseValue2 to booleanFalseValue, undefValue2 to undefValue)
    var mapLTS = mutableMapOf<Int, ValueDefinition>(errorValue to errorValue2, booleanTrueValue to booleanTrueValue2, booleanFalseValue to booleanFalseValue2, undefValue to undefValue2)
    var bNodeCounter = 0
    fun clear() {
        mapSTL = mutableMapOf<ValueDefinition, Int>(errorValue2 to errorValue, booleanTrueValue2 to booleanTrueValue, booleanFalseValue2 to booleanFalseValue, undefValue2 to undefValue)
        mapLTS = mutableMapOf<Int, ValueDefinition>(errorValue to errorValue2, booleanTrueValue to booleanTrueValue2, booleanFalseValue to booleanFalseValue2, undefValue to undefValue2)
        bNodeCounter = 0
        bnodeMapToGlobal.clear()
    }
    @JvmName("toBooleanOrError") internal inline fun toBooleanOrError(value: Int): Int {
        var res: Int = errorValue
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
                SanityCheck.println({ "TODO exception 40" })
                e.printStackTrace()
            }
        }
        return res
    }
    @JvmName("createNewBNode") internal inline fun createNewBNode(): Int {
        return createValue(ValueBnode("" + bNodeCounter++))
    }
    @JvmName("createIri") internal inline fun createIri(iri: String): Int {
        return createValue("<" + iri + ">")
    }
    @JvmName("createValue") internal inline fun createValue(value: String?): Int {
        return createValue(ValueDefinition(value))
    }
    @JvmName("createTyped") internal inline fun createTyped(content: String, type: String): Int {
        return createValue(ValueDefinition("\"$content\"^^<$type>"))
    }
    @JvmName("createDouble") internal inline fun createDouble(value: Double): Int {
        return createValue(ValueDouble(value))
    }
    @JvmName("createFloat") internal inline fun createFloat(value: Double): Int {
        return createValue(ValueFloat(value))
    }
    @JvmName("createDecimal") internal inline fun createDecimal(value: Double): Int {
        return createValue(ValueDecimal(value))
    }
    @JvmName("createInteger") internal inline fun createInteger(value: Int): Int {
        return createValue(ValueInteger(value))
    }
    @JvmName("checkValue") internal inline fun checkValue(value: ValueDefinition): Int {
        var res: Int
        if (value is ValueUndef) {
            res = undefValue
        } else if (value is ValueError) {
            res = errorValue
        } else {
            if (global) {
                res = mapSTL[value]
            } else {
                res = nodeGlobalDictionary.mapSTL[value]
                if (res == null) {
                    res = mapSTL[value]
                    if (res != null) {
                        res = -res
                    }
                }
            }
        }
        return res
    }
    @JvmName("createValue") internal inline fun createValue(value: ValueDefinition): Int {
        var res = checkValue(value)
        if (res == null) {
            val l = mapLTS.size
            mapSTL[value] = l
            mapLTS[l] = value
            if (global) {
                res = l
            } else {
                res = -l
            }
        }
        return res
    }
    @JvmName("getValue") internal inline fun getValue(value: Int): ValueDefinition {
        if (value < 0) {
            return mapLTS[-value]!!
        } else {
            return nodeGlobalDictionary.mapLTS[value]!!
        }
/*Coverage Unreachable*/
    }
    @JvmName("valueToGlobal") internal inline fun valueToGlobal(value: Int): Int {
        if (value >= 0) {
            return value
        } else {
            if (isLocalBNode(value)) {
                return bnodeMapToGlobal.getOrCreate(value, { nodeGlobalDictionary.createNewBNode() })
            }
            return nodeGlobalDictionary.createValue(getValue(value))
        }
/*Coverage Unreachable*/
    }
    fun safeToFolder() {
        File(BufferManager.bufferPrefix + "dictionary.txt").printWriter { out ->
            var idx = 0
            for ((k, line) in mapLTS) {
                if (idx > 3) {
                    out.println(line.valueToString())
                }
                idx++
            }
        }
    }
    fun loadFromFolder() {
        File(BufferManager.bufferPrefix + "dictionary.txt").forEachLine {
            createValue(ValueDefinition(it))
        }
    }
}
