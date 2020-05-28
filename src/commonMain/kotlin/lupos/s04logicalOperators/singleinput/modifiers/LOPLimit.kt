package lupos.s04logicalOperators.singleinput.modifiers

import kotlin.jvm.JvmField
import lupos.s00misc.Coverage
import lupos.s00misc.EOperatorID
import lupos.s00misc.ESortPriority
import lupos.s04logicalOperators.HistogramResult
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.noinput.OPEmptyRow
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query

class LOPLimit(query: Query, @JvmField val limit: Int, child: OPBase = OPEmptyRow(query)) : LOPBase(query, EOperatorID.LOPLimitID, "LOPLimit", arrayOf(child), ESortPriority.SAME_AS_CHILD) {
    override fun toXMLElement() = super.toXMLElement().addAttribute("limit", "" + limit)
    override fun equals(other: Any?): Boolean {
        if (other !is LOPLimit) {
            return false
        }
        if (limit != other.limit) {
            return false
        }
        for (i in children.indices) {
            if (children[i] != other.children[i]) {
                return false
            }
        }
        return true
    }

    override fun cloneOP() = LOPLimit(query, limit, children[0].cloneOP())
    override fun calculateHistogram(): HistogramResult {
        var res = HistogramResult()
        var childHistogram = children[0].getHistogram()
        res.count = childHistogram.count
        if (res.count > limit) {
            res.count = limit
            var scale = limit.toDouble() / res.count.toDouble()
            childHistogram.values.forEach { k, v ->
                res.values[k] = (v.toDouble() * scale).toInt()
            }
        } else {
            childHistogram.values.forEach { k, v ->
                res.values[k] = v
            }
        }
        return res
    }
}
