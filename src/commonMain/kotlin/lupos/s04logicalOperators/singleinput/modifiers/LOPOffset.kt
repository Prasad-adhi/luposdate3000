package lupos.s04logicalOperators.singleinput.modifiers

import lupos.s00misc.EOperatorID
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.noinput.OPNothing


class LOPOffset(val offset: Int) : LOPBase() {
    override val operatorID = EOperatorID.LOPOffsetID
    override val classname = "LOPOffset"
    override val children: Array<OPBase> = arrayOf(OPNothing())

    constructor(offset: Int, child: OPBase) : this(offset) {
        this.children[0] = child
    }


    override fun toXMLElement() = super.toXMLElement().addAttribute("offset", "" + offset)

    override fun equals(other: Any?): Boolean {
        if (other !is LOPOffset)
            return false
        if (offset != other.offset)
            return false
        for (i in children.indices) {
            if (!children[i].equals(other.children[i]))
                return false
        }
        return true
    }
}
