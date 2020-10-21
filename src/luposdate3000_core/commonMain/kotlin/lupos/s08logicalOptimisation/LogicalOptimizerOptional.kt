package lupos.s08logicalOptimisation

import lupos.s00misc.EOptimizerID
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.multiinput.LOPJoin
import lupos.s04logicalOperators.noinput.OPEmptyRow
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.singleinput.LOPOptional

class LogicalOptimizerOptional(query: Query) : OptimizerBase(query, EOptimizerID.LogicalOptimizerOptionalID) {
    override val classname = "LogicalOptimizerOptional"
    override suspend fun optimize(node: IOPBase, parent: IOPBase?, onChange: () -> Unit): IOPBase {
        var res: OPBase = node
        if (node is LOPOptional) {
            res = LOPJoin(query, OPEmptyRow(query), node.children[0], true)
        }
        return res
    }
}
