package lupos.s04logicalOperators.noinput

import lupos.s00misc.Coverage
import lupos.s00misc.EOperatorID
import lupos.s00misc.XMLElement
import lupos.s00misc.ESortPriority
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query

class OPNothing(query: Query,val myProvidedVariableNames:List<String>) : LOPBase(query, EOperatorID.OPNothingID, "OPNothing", arrayOf(), ESortPriority.PREVENT_ANY) {
override fun getProvidedVariableNames()=myProvidedVariableNames
override fun toXMLElement(): XMLElement{
var res=super.toXMLElement()
for(v in myProvidedVariableNames){
res.addContent(XMLElement("v").addContent(v))
}
return res
}
    override fun equals(other: Any?): Boolean {
        if (other !is OPNothing) {
            return false
        }
        return true
    }

    override fun cloneOP() = this
}
