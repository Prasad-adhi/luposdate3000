package lupos.s08logicalOptimisation

import lupos.s00misc.EOptimizerID
import lupos.s04arithmetikOperators.noinput.AOPVariable
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.singleinput.LOPBind
import lupos.s04logicalOperators.singleinput.LOPProjection
import lupos.s08logicalOptimisation.OptimizerBase

class LogicalOptimizerRemoveBindVariable(query: Query) : OptimizerBase(query, EOptimizerID.LogicalOptimizerRemoveBindVariableID) {
    override val classname = "LogicalOptimizerRemoveBindVariable"
    override suspend fun optimize(node: IOPBase, parent: IOPBase?, onChange: () -> Unit): IOPBase {
        var res = node
        if (node is LOPProjection) {
            val child = node.children[0]
            if (child is LOPBind) {
                var exp = child.children[1]
                if (exp is AOPVariable) {
                    var provided = node.getProvidedVariableNames()
                    if (!provided.contains(exp.name)) {
                        node.replaceVariableWithAnother(child.children[0], exp.name, child.name.name, child, 0)
                        node.children[0] = child.children[0]
                        onChange()
                    }
                }
            }
        }
        return res
    }
}
