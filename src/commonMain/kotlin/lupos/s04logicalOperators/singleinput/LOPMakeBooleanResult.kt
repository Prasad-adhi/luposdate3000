package lupos.s04logicalOperators.singleinput
import lupos.s04logicalOperators.noinput.OPNothing
import lupos.s04logicalOperators.LOPBase

import lupos.s00misc.XMLElement
import lupos.s04logicalOperators.OPBase


class LOPMakeBooleanResult() : LOPBase() {
    override val children: Array<OPBase> = arrayOf(OPNothing())

    constructor(child: OPBase) : this() {
        children[0] = child
    }

    override fun getProvidedVariableNames(): List<String> {
        return listOf("?boolean")
    }

    override fun getRequiredVariableNames(): List<String> {
        return listOf<String>()
    }

    override fun toXMLElement(): XMLElement {
        val res = XMLElement("LOPMakeBooleanResult")
        res.addContent(childrenToXML())
        return res
    }
}
