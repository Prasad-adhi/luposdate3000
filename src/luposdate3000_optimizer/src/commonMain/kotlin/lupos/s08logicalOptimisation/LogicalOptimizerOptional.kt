package lupos.s08logicalOptimisation
import lupos.s00misc.EOptimizerIDExt
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.multiinput.LOPJoin
import lupos.s04logicalOperators.noinput.OPEmptyRow
import lupos.s04logicalOperators.singleinput.LOPOptional
public class LogicalOptimizerOptional(query: Query) : OptimizerBase(query, EOptimizerIDExt.LogicalOptimizerOptionalID) {
    override val classname: String = "LogicalOptimizerOptional"
    override /*suspend*/ fun optimize(node: IOPBase, parent: IOPBase?, onChange: () -> Unit): IOPBase {
        var res: IOPBase = node
        if (node is LOPOptional) {
            res = LOPJoin(query, OPEmptyRow(query), node.getChildren()[0], true)
        }
        return res
    }
}
