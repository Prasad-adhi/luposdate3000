package lupos.s04logicalOperators.singleinput.modifiers

import kotlin.jvm.JvmField
import lupos.s00misc.EOperatorID
import lupos.s00misc.ESortPriority
import lupos.s04logicalOperators.HistogramResult
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.IQuery
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.noinput.OPEmptyRow
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query

class LOPOffset(query: IQuery, @JvmField val offset: Int, child: IOPBase = OPEmptyRow(query)) : LOPBase(query, EOperatorID.LOPOffsetID, "LOPOffset", arrayOf(child), ESortPriority.SAME_AS_CHILD) {
    override suspend fun toXMLElement() = super.toXMLElement().addAttribute("offset", "" + offset)
    override fun equals(other: Any?) = other is LOPOffset && offset == other.offset && children[0] == other.children[0]
    override fun cloneOP(): IOPBase = LOPOffset(query, offset, children[0].cloneOP())
    suspend override fun calculateHistogram(): HistogramResult {
        var res = HistogramResult()
        var childHistogram = children[0].getHistogram()
        res.count = childHistogram.count - offset
        if (res.count < 0) {
            res.count = 0
            for ((k, v) in childHistogram.values) {
                res.values[k] = 0
            }
        } else {
            for ((k, v) in childHistogram.values) {
                if (v > childHistogram.count - offset) {
                    res.values[k] = childHistogram.count - offset
                } else {
                    res.values[k] = v
                }
            }
        }
        return res
    }
}
