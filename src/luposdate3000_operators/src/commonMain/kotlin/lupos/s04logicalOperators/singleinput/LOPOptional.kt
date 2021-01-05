package lupos.s04logicalOperators.singleinput
import lupos.s00misc.EOperatorID
import lupos.s00misc.ESortPriority
import lupos.s04logicalOperators.HistogramResult
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.IQuery
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.noinput.OPEmptyRow
public class LOPOptional(query: IQuery, child: IOPBase = OPEmptyRow(query)) : LOPBase(query, EOperatorID.LOPOptionalID, "LOPOptional", arrayOf(child), ESortPriority.SAME_AS_CHILD) {
    override fun equals(other: Any?): Boolean = other is LOPOptional && children[0] == other.children[0]
    override fun cloneOP(): IOPBase = LOPOptional(query, children[0].cloneOP())
    override /*suspend*/ fun calculateHistogram(): HistogramResult = children[0].getHistogram()
}
