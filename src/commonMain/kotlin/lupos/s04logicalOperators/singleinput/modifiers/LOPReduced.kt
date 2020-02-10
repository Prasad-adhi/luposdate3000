package lupos.s04logicalOperators.singleinput.modifiers
import lupos.s04logicalOperators.noinput.OPNothing
import lupos.s04logicalOperators.LOPBase

import lupos.s00misc.XMLElement
import lupos.s04logicalOperators.OPBase


class LOPReduced() : LOPBase() {
    override val children: Array<OPBase> = arrayOf(OPNothing())

    constructor(child: OPBase) : this() {
        this.children[0] = child
    }

    override fun getProvidedVariableNames(): List<String> {
        return children[0].getProvidedVariableNames()
    }

    override fun getRequiredVariableNames(): List<String> {
        return children[0].getRequiredVariableNames()
    }

    override fun toXMLElement(): XMLElement {
        val res = XMLElement("LOPReduced")
        res.addContent(childrenToXML())
        return res
    }
}
