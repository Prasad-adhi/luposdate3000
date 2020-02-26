package lupos.s04arithmetikOperators.noinput

import lupos.s00misc.EOperatorID
import lupos.s03resultRepresentation.ResultRow
import lupos.s03resultRepresentation.ResultSet
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.resultFlow
import lupos.s04logicalOperators.OPBase


class AOPNot(var child: AOPBase) : AOPBase() {
    override val operatorID = EOperatorID.AOPNotID
    override val classname = "AOPNot"
    override val children: Array<OPBase> = arrayOf(child)


    override fun equals(other: Any?): Boolean {
        if (other !is AOPNot)
            return false
        for (i in children.indices) {
            if (!children[i].equals(other.children[i]))
                return false
        }
        return true
    }

    override fun calculate(resultSet: ResultSet, resultRow: ResultRow): AOPConstant {
        val a = (children[0] as AOPBase).calculate(resultSet, resultRow)
        if (a is AOPBoolean)
            return resultFlow({ this }, { resultRow }, { resultSet }, {
                AOPBoolean(!a.value)
            })
        throw resultFlow({ this }, { resultRow }, { resultSet }, {
            Exception("AOPNot only works with boolean input")
        })
    }
}
