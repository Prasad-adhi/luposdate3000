package lupos.s04logicalOperators.singleinput

import lupos.s00misc.XMLElement
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.noinput.LOPVariable
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.singleinput.LOPBind
import lupos.s04logicalOperators.singleinput.LOPFilter
import lupos.s04logicalOperators.singleinput.LOPGroup
import lupos.s04logicalOperators.singleinput.LOPMakeBooleanResult
import lupos.s04logicalOperators.singleinput.LOPModify
import lupos.s04logicalOperators.singleinput.LOPNOOP
import lupos.s04logicalOperators.singleinput.LOPOptional
import lupos.s04logicalOperators.singleinput.LOPProjection
import lupos.s04logicalOperators.singleinput.LOPRename
import lupos.s04logicalOperators.singleinput.LOPServiceIRI
import lupos.s04logicalOperators.singleinput.LOPSingleInputBase


class LOPServiceVAR(val name: String, val silent: Boolean, var constraint: OPBase) : LOPSingleInputBase() {
    constructor(name: String, silent: Boolean, constraint: OPBase, child: OPBase) : this(name, silent, constraint) {
        this.child = child
    }

    override fun getProvidedVariableNames(): List<String> {
        return constraint.getProvidedVariableNames() + child.getProvidedVariableNames()
    }

    override fun getRequiredVariableNames(): List<String> {
        return constraint.getRequiredVariableNames() + child.getRequiredVariableNames()
    }

    override fun toXMLElement(): XMLElement {
        val res = XMLElement("LOPService")
        res.addAttribute("name", name)
        res.addAttribute("silent", "" + silent)
        res.addContent(XMLElement("constraint").addContent(constraint.toXMLElement()))
        res.addContent(XMLElement("child").addContent(child.toXMLElement()))
        return res
    }
}
