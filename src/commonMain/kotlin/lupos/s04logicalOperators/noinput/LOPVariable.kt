package lupos.s04logicalOperators.noinput
import lupos.s04logicalOperators.OPBase

import lupos.s00misc.XMLElement
import lupos.s04logicalOperators.LOPBase


class LOPVariable(var name: String) : LOPBase() {
    override val children: Array<OPBase> = arrayOf()
    override fun getProvidedVariableNames(): List<String> {
        return mutableListOf<String>(name)
    }

    override fun getRequiredVariableNames(): List<String> {
        return mutableListOf<String>(name)
    }

    override fun toXMLElement(): XMLElement {
        return XMLElement("LOPVariable")
    }
}
