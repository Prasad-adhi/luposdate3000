package lupos.s04arithmetikOperators.multiinput

import kotlin.jvm.JvmField
import lupos.s00misc.EOperatorID
import lupos.s00misc.resultFlow
import lupos.s03resultRepresentation.*
import lupos.s03resultRepresentation.ResultChunk
import lupos.s03resultRepresentation.ResultRow
import lupos.s03resultRepresentation.ResultSet
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04arithmetikOperators.ResultVektorRaw
import lupos.s04logicalOperators.*
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.ResultIterator


class AOPAddition(query: Query, childA: AOPBase, childB: AOPBase) : AOPBinaryOperationFixedName(query, EOperatorID.AOPAdditionID, "AOPAddition", arrayOf(childA, childB)) {


    override fun toSparql() = "(" + children[0].toSparql() + " + " + children[1].toSparql() + ")"

    override fun equals(other: Any?): Boolean {
        if (other !is AOPAddition)
            return false
        for (i in children.indices) {
            if (children[i] != other.children[i])
                return false
        }
        return true
    }

    override fun calculate(resultSet: ResultSet, resultChunk: ResultChunk): ResultVektorRaw {
        val rVektor = ResultVektorRaw()
        val aVektor = (children[0] as AOPBase).calculate(resultSet, resultChunk)
        val bVektor = (children[1] as AOPBase).calculate(resultSet, resultChunk)
        for (i in resultChunk.pos until resultChunk.size) {
            val a = aVektor.data[i]
            val b = bVektor.data[i]
            try {
                if (a is ValueDouble || b is ValueDouble)
                    rVektor.data[i] = ValueDouble(a.toDouble() + b.toDouble())
                else if (a is ValueDecimal || b is ValueDecimal)
                    rVektor.data[i] = ValueDecimal(a.toDouble() + b.toDouble())
                else if (a is ValueInteger || b is ValueInteger)
                    rVektor.data[i] = ValueInteger(a.toInt() + b.toInt())
            } catch (e: Throwable) {
            }
        }
        println("AOPAdditiona = ${aVektor.toString(resultChunk.pos, resultChunk.size)}")
        println("AOPAdditionb = ${bVektor.toString(resultChunk.pos, resultChunk.size)}")
        println("AOPAdditionr = ${rVektor.toString(resultChunk.pos, resultChunk.size)}")
        return resultFlow({ this }, { resultChunk }, { resultSet }, { rVektor })
    }

    override fun cloneOP() = AOPAddition(query, children[0].cloneOP() as AOPBase, children[1].cloneOP() as AOPBase)
}
