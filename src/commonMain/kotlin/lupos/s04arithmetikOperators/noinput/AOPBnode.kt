package lupos.s04arithmetikOperators.noinput

import lupos.s00misc.EOperatorID
import lupos.s04logicalOperators.OPBase


class AOPBnode(var value: String) : AOPConstant() {
    override val operatorID = EOperatorID.AOPBnodeID
    override val classname = "AOPBnode"
    override val children: Array<OPBase> = arrayOf()

    override fun toXMLElement() = super.toXMLElement().addAttribute("value", "" + value)

    override fun valueToString() = "_:" + value
    override fun equals(other: Any?): Boolean {
        if (other !is AOPBnode)
            return false
        return value == other.value
    }

    override fun toDouble(): Double {
        throw Exception("cannot cast AOPBnode to Double")
    }

    override fun toInt(): Int {
        throw Exception("cannot cast AOPBnode to Int")
    }

    override fun toBoolean(): Boolean {
        throw Exception("cannot cast AOPBnode to Boolean")
    }
}
