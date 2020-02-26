package lupos.s04arithmetikOperators.singleinput

import lupos.s00misc.EOperatorID
import lupos.s03resultRepresentation.ResultRow
import lupos.s03resultRepresentation.ResultSet
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.AOPConstant
import lupos.s04arithmetikOperators.noinput.AOPDateTime
import lupos.s04arithmetikOperators.noinput.AOPInteger
import lupos.s04arithmetikOperators.resultFlow
import lupos.s04logicalOperators.OPBase


class AOPBuildInCallMONTH(child: AOPBase) : AOPBase() {
    override val operatorID = EOperatorID.AOPBuildInCallMONTHID
    override val classname = "AOPBuildInCallMONTH"
    override val children: Array<OPBase> = arrayOf(child)


    override fun equals(other: Any?): Boolean {
        if (other !is AOPBuildInCallMONTH)
            return false
        return children[0].equals(other.children[0])
    }

    override fun calculate(resultSet: ResultSet, resultRow: ResultRow): AOPConstant {
        val a = (children[0] as AOPBase).calculate(resultSet, resultRow)
        if (a is AOPDateTime)
            return resultFlow({ this }, { resultRow }, { resultSet }, {
                AOPInteger(a.month)
            })
        throw resultFlow({ this }, { resultRow }, { resultSet }, {
            Exception("AOPBuiltInCall MONTH only works with dateTime input")
        })
    }
}
