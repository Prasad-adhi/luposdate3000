package lupos.s04logicalOperators.singleinput.modifiers
import lupos.s00misc.EOperatorID
import lupos.s00misc.ESortPriority
import lupos.s00misc.SortHelper
import lupos.s04logicalOperators.HistogramResult
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.IQuery
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.noinput.OPEmptyRow
public class LOPSortAny(query: IQuery, val possibleSortOrder: List<SortHelper>, child: IOPBase = OPEmptyRow(query)) : LOPBase(query, EOperatorID.LOPSortAnyID, "LOPSortAny", arrayOf(child), ESortPriority.SORT) {
    override fun equals(other: Any?): Boolean = other is LOPSortAny && possibleSortOrder == other.possibleSortOrder && children[0] == other.children[0]
    override fun cloneOP(): IOPBase = LOPSortAny(query, possibleSortOrder, children[0].cloneOP())
    override /*suspend*/ fun calculateHistogram(): HistogramResult {
        return children[0].getHistogram()
    }
}
