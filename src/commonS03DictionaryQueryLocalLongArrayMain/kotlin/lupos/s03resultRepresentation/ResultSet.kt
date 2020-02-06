package lupos.s03resultRepresentation

import lupos.s03resultRepresentation.*
import lupos.s03resultRepresentation.ResultSetIterator
import lupos.s03resultRepresentation.Value
import lupos.s03resultRepresentation.Variable


class ResultSet(val dictionary: ResultSetDictionary) {
    val variablesSTL = mutableMapOf<String, Long>()
    val variablesLTS = mutableListOf<String>()

    fun renameVariable(variableOld: String, variableNew: String): Long {
        val l = variablesSTL[variableOld]!!
        variablesSTL.remove(variableOld)
        variablesSTL[variableNew] = l
        variablesLTS[l.toInt()] = variableNew
        println("renameVariable $l $variableOld $variableNew")
        return l
    }

    fun createVariable(variable: String): Variable {
        val o = variablesSTL[variable]
        if (o != null)
            return o
        val l = 0L + variablesLTS.size
        variablesSTL[variable] = l
        variablesLTS.add(variable)
        println("createVariable $l $variable")
        return l
    }

    fun getVariable(variable: Variable): String {
        return variablesLTS[variable.toInt()]
    }

    fun getVariableNames(): Set<String> {
        return variablesLTS.toSet()
    }

    fun createValue(value: String?): Value {
        if (value == null)
            return dictionary.undefValue
        return dictionary.createValue(value)
    }

    fun createResultRow(): ResultRow {
        return ResultRow(variablesLTS.size, dictionary.undefValue)
    }

    fun getValue(value: Value): String? {
        return dictionary.getValue(value)
    }

    fun isUndefValue(r: ResultRow, v: Variable): Boolean {
        return r[v] == dictionary.undefValue
    }

    fun setUndefValue(r: ResultRow, v: Variable) {
        r[v] = dictionary.undefValue
    }
}
