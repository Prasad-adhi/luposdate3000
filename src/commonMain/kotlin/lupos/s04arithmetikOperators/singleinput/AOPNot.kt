package lupos.s04arithmetikOperators.noinput

import lupos.s00misc.EOperatorID
import lupos.s00misc.XMLElement
import lupos.s03resultRepresentation.*
import lupos.s04arithmetikOperators.*
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.OPBase


class AOPNot(var child: AOPBase) : AOPBase() {
    override val operatorID = EOperatorID.AOPNotID
    override val classname = "AOPNot"
    override val children: Array<OPBase> = arrayOf(child)


    override fun equals(other: Any?): Boolean {
        if (other !is AOPNot)
            return false
        for (i in children.indices) {
            if (children[i] != other.children[i])
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
