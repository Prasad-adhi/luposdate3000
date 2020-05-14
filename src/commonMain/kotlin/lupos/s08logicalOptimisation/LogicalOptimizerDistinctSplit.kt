package lupos.s08logicalOptimisation
import lupos.s04arithmetikOperators.noinput.AOPVariable
import lupos.s00misc.Coverage
import lupos.s00misc.EOptimizerID
import lupos.s00misc.ESortType
import lupos.s00misc.SortHelper
import lupos.s00misc.ExecuteOptimizer
import lupos.s04logicalOperators.singleinput.modifiers.*
import lupos.s04logicalOperators.singleinput.*
import lupos.s04logicalOperators.multiinput.*
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s08logicalOptimisation.OptimizerBase

class LogicalOptimizerDistinctSplit(query: Query) : OptimizerBase(query, EOptimizerID.LogicalOptimizerDistinctSplitID) {
    override val classname = "LogicalOptimizerDistinctSplit"

    override fun optimize(node: OPBase, parent: OPBase?, onChange: () -> Unit) = ExecuteOptimizer.invoke({ this }, { node }, {
        var res: OPBase = node
        if (node is LOPDistinct) {
            val child = node.children[0]
            val provided = child.getProvidedVariableNames().toMutableList()
            if (provided.size == 0) {
                //no variables -> no sort required
                res = LOPReduced(query, child)
                onChange()
            } else {
                if (child.mySortPriority.size == provided.size) {
                    res = LOPReduced(query, LOPSortAny(query, child.mySortPriority, child))
                    onChange()
                } else {
                    if (child is LOPJoin) {
                        val columns = LOPJoin.getColumns(child.children[0].getProvidedVariableNames(), child.children[1].getProvidedVariableNames())
                        val variables = mutableListOf<String>()
                        variables.addAll(columns[0])
                        variables.addAll(columns[1])
                        variables.addAll(columns[2])
                        res = LOPReduced(query, LOPSortAny(query, variables.map { SortHelper(it, ESortType.FAST) }, child))
                        onChange()
                    } else {
                        res = LOPReduced(query, LOPSortAny(query, provided.map { SortHelper(it, ESortType.FAST) }, child))
                        onChange()
                    }
                }
            }
        } else if (node is LOPSortAny) {
            val variables = node.possibleSortOrder.map { it.variableName }
            val child = node.children[0]
            if (child is LOPJoin) {
                if (child.children[0].getProvidedVariableNames().containsAll(variables) && child.children[1].getProvidedVariableNames().containsAll(variables)) {
                    child.children[0] = LOPSortAny(query, node.possibleSortOrder, child.children[0])
                    child.children[1] = LOPSortAny(query, node.possibleSortOrder, child.children[1])
                    res = child
                    onChange()
                }
            } else if (child is LOPFilter) {
                child.children[0] = LOPSortAny(query, node.possibleSortOrder, child.children[0])
                res = child
                onChange()
            } else if (child is LOPBind && child.children[1] is AOPVariable) {
                var varNameOut = child.name.name
                var varNameIn = (child.children[1] as AOPVariable).name
                var tmp = mutableListOf<SortHelper>()
                for (c in node.possibleSortOrder) {
                    if (c.variableName == varNameOut) {
                        tmp.add(SortHelper(varNameIn, c.sortType))
                    } else {
                        tmp.add(c)
                    }
                }
child.children[0] = LOPSortAny(query, tmp, child.children[0])
                res = child
                onChange()
            }
        }
/*return*/res
    })
}
