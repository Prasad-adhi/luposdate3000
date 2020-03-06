package lupos.s04logicalOperators.singleinput

import lupos.s00misc.EOperatorID
import lupos.s00misc.XMLElement
import lupos.s04arithmetikOperators.noinput.AOPVariable
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.noinput.OPNothing
import lupos.s04logicalOperators.OPBase


class LOPSort(@JvmField val asc: Boolean, @JvmField var by: AOPVariable, child: OPBase = OPNothing()) : LOPBase() {
    override val operatorID = EOperatorID.LOPSortID
    override val classname = "LOPSort"
    override val children: Array<OPBase> = arrayOf(child)

    override fun toXMLElement(): XMLElement {
        val res = XMLElement("LOPSort")
        res.addAttribute("by", by.name)
        if (asc)
            res.addAttribute("order", "ASC")
        else
            res.addAttribute("order", "DESC")
        res.addContent(childrenToXML())
        return res
    }

    override fun equals(other: Any?): Boolean {
        if (other !is LOPSort)
            return false
        if (asc != other.asc)
            return false
        if (by != other.by)
            return false
        for (i in children.indices) {
            if (children[i] != other.children[i])
                return false
        }
        return true
    }

    override fun cloneOP() = LOPSort(asc, by, children[0].cloneOP())
}
