package lupos.s04arithmetikOperators.singleinput

import lupos.s00misc.EOperatorID
import lupos.s00misc.XMLElement
import lupos.s03resultRepresentation.*
import lupos.s04arithmetikOperators.*
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.OPBase


class AOPBuildInCallTZ(child: AOPBase) : AOPBase() {
    override val operatorID = EOperatorID.AOPBuildInCallTZID
    override val classname = "AOPBuildInCallTZ"
    override val children: Array<OPBase> = arrayOf(child)


    override fun equals(other: Any?): Boolean {
        if (other !is AOPBuildInCallTZ)
            return false
        return children[0] == other.children[0]
    }

    override fun calculate(resultSet: ResultSet, resultRow: ResultRow): AOPConstant {
        val a = (children[0] as AOPBase).calculate(resultSet, resultRow)
        if (a is AOPDateTime)
            return resultFlow({ this }, { resultRow }, { resultSet }, {
                AOPSimpleLiteral("\"", a.getTZ())
            })
        throw resultFlow({ this }, { resultRow }, { resultSet }, {
            Exception("AOPBuiltInCall TZ only works with dateTime input")
        })
    }
}
