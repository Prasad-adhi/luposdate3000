package lupos.s03resultRepresentation

import lupos.s03resultRepresentation.Value
import lupos.s03resultRepresentation.Variable


class ResultRow : Comparable<ResultRow> {
    val values: Array<Value>

    constructor(columns: Int, undefValue: Value) {
        values = Array<Value>(columns, { undefValue })
    }

    operator inline fun set(name: Variable, value: Value) {
        values[name.toInt()] = value
    }

    operator inline fun get(name: Variable): Value {
        return values[name.toInt()]!!
    }

    override fun toString(): String {
        return values.toString()
    }

    override operator inline fun compareTo(other: ResultRow): Int {
        var res = 0
        val s = values.size - other.values.size
        if (s != 0) {
            res = s
            return res
        }
        for (k in values.indices) {
            if (other.values[k] == null) {
                res = 1
                return res
            }
            val c = values[k]!!.compareTo(other.values[k]!!)
            if (c != 0) {
                res = c
                return res
            }
        }
        return res
    }

    override inline fun equals(other: Any?): Boolean {
        if (other == null || !(other is ResultRow))
            return false
        return compareTo(other) == 0
    }

    override inline fun hashCode(): Int {
        var res = values.size.hashCode()
        for (v in values)
            res += v.hashCode()
        return res
    }

}
