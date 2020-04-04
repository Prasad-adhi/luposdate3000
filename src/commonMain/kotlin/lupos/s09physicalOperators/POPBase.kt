package lupos.s09physicalOperators

import kotlin.jvm.JvmField
import lupos.s00misc.*
import lupos.s00misc.Coverage
import lupos.s00misc.EOperatorID
import lupos.s03resultRepresentation.*
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query

abstract class POPBase(query: Query,
                       var projectedVariables: List<String>,
                       operatorID: EOperatorID,
                       classname: String,
                       children: Array<OPBase>
) :
        OPBase(query, operatorID, classname, children) {
    open fun getProvidedVariableNamesInternal() = super.getProvidedVariableNames()
    override fun getProvidedVariableNames() = projectedVariables
    override fun toXMLElement(): XMLElement {
        val res = super.toXMLElement()
        val projectedXML = XMLElement("projectedVariables")
        res.addContent(projectedXML)
        for (variable in projectedVariables) {
            projectedXML.addContent(XMLElement("variable").addAttribute("name", variable))
        }
        return res
    }

    override open fun syntaxVerifyAllVariableExists(additionalProvided: List<String>, autocorrect: Boolean) {
        for (i in 0 until childrenToVerifyCount()) {
            children[i].syntaxVerifyAllVariableExists(additionalProvided, autocorrect)
        }
        val res = (additionalProvided + getProvidedVariableNamesInternal()).containsAll(getRequiredVariableNames())
        if (!res) {
            if (autocorrect) {
                syntaxVerifyAllVariableExistsAutocorrect()
            } else {
                throw Exception("${classNameToString(this)} undefined Variable ${toXMLElement().toPrettyString()} ${additionalProvided} ${getProvidedVariableNames()} ${getRequiredVariableNames()}")
            }
        }
    }
}
