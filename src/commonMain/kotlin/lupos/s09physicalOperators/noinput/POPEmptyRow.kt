package lupos.s09physicalOperators.noinput

import lupos.s00misc.Coverage
import lupos.s00misc.EOperatorID
import lupos.s00misc.ESortPriority
import lupos.s00misc.Partition
import lupos.s04logicalOperators.iterator.IteratorBundle
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s09physicalOperators.POPBase

class POPEmptyRow(query: Query, projectedVariables: List<String>) : POPBase(query, projectedVariables, EOperatorID.POPEmptyRowID, "POPEmptyRow", arrayOf(), ESortPriority.PREVENT_ANY) {
    override fun cloneOP() = POPEmptyRow(query, projectedVariables)
    override fun toSparql() = "{}"
    override fun equals(other: Any?) = other is POPEmptyRow
    override fun evaluate(parent: Partition): IteratorBundle {
        return IteratorBundle(1)
    }
}
