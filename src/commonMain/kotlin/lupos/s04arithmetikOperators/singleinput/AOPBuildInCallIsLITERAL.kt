package lupos.s04arithmetikOperators.singleinput

import kotlin.jvm.JvmField
import lupos.s00misc.EOperatorID

import lupos.s03resultRepresentation.*



import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04arithmetikOperators.ResultVektorRaw
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.ResultIterator

class AOPBuildInCallIsLITERAL(query: Query, child: AOPBase) : AOPBase(query, EOperatorID.AOPBuildInCallIsLITERALID, "AOPBuildInCallIsLITERAL", arrayOf(child)) {
    override fun toSparql() = "isLiteral(" + children[0].toSparql() + ")"
    override fun equals(other: Any?): Boolean {
        if (other !is AOPBuildInCallIsLITERAL)
            return false
        return children[0] == other.children[0]
    }

    override fun calculate(resultChunk:ResultVektorRaw) :ResultVektorRaw{
        val rVektor = ResultVektorRaw(resultChunk.availableRead())
        val aVektor = (children[0] as AOPBase).calculate(resultChunk)
        for (i in 0 until resultChunk.availableRead()) {
            val a = aVektor.data[i]
            if (a !is ValueUndef && a !is ValueError)
                rVektor.data[i] = ValueBoolean(a is ValueStringBase || a is ValueDouble || a is ValueBoolean || a is ValueInteger || a is ValueDecimal || a is ValueDateTime)
        }
        return rVektor
    }

    override fun cloneOP() = AOPBuildInCallIsLITERAL(query, children[0].cloneOP() as AOPBase)
}
