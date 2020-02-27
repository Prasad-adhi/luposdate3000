package lupos.s04arithmetikOperators.noinput
import lupos.s00misc.EOperatorID
import lupos.s04logicalOperators.OPBase


class AOPBoolean(var value: Boolean) : AOPConstant() {
    override val operatorID = EOperatorID.AOPBooleanID
    override val classname = "AOPBoolean"
    override val children: Array<OPBase> = arrayOf()


    override fun toXMLElement() = super.toXMLElement().addAttribute("value", "" + value)

    override fun valueToString() = "\"" + value + "\"^^<http://www.w3.org/2001/XMLSchema#boolean>"
    override fun equals(other: Any?): Boolean {
        if (other !is AOPBoolean)
            return false
        return value == other.value
    }

    override fun toDouble(): Double {
        throw Exception("cannot cast AOPBoolean to Double")
    }

    override fun toInt(): Int {
        throw Exception("cannot cast AOPBoolean to Int")
    }

    override fun toBoolean(): Boolean = value
}
