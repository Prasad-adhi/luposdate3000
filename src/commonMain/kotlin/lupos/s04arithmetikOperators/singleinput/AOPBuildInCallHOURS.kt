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

class AOPBuildInCallHOURS(query: Query, child: AOPBase) : AOPBase(query, EOperatorID.AOPBuildInCallHOURSID, "AOPBuildInCallHOURS", arrayOf(child)) {
    override fun toSparql() = "HOURS(" + children[0].toSparql() + ")"
    override fun equals(other: Any?): Boolean {
        if (other !is AOPBuildInCallHOURS)
            return false
        return children[0] == other.children[0]
    }

    override fun calculate(resultChunk:ResultVektorRaw) :ResultVektorRaw{
        val rVektor = ResultVektorRaw(resultChunk.availableRead())
        val aVektor = (children[0] as AOPBase).calculate(resultChunk)
        for (i in 0 until resultChunk.availableRead()) {
            val a = aVektor.data[i]
            if (a is ValueDateTime)
                rVektor.data[i] = ValueInteger(a.hours)
        }
        return rVektor
    }

    override fun cloneOP() = AOPBuildInCallHOURS(query, children[0].cloneOP() as AOPBase)
}
