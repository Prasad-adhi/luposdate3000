package lupos.s04logicalOperators.singleinput.modifiers

import kotlin.jvm.JvmField
import lupos.s00misc.EOperatorID
import lupos.s03resultRepresentation.ResultChunk
import lupos.s04arithmetikOperators.ResultVektorRaw
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.noinput.OPNothing
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.ResultIterator


class LOPOffset(query: Query, @JvmField val offset: Int, child: OPBase = OPNothing(query)) : LOPBase(query, EOperatorID.LOPOffsetID, "LOPOffset", arrayOf(child)) {

    override fun toXMLElement() = super.toXMLElement().addAttribute("offset", "" + offset)

    override fun equals(other: Any?): Boolean {
        if (other !is LOPOffset)
            return false
        if (offset != other.offset)
            return false
        for (i in children.indices) {
            if (children[i] != other.children[i])
                return false
        }
        return true
    }

    override fun cloneOP() = LOPOffset(query, offset, children[0].cloneOP())
}
