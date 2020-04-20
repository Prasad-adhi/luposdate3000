package lupos.s08logicalOptimisation

import kotlin.jvm.JvmField
import lupos.s00misc.Coverage
import lupos.s00misc.EOptimizerID
import lupos.s04logicalOperators.Query

class LogicalOptimizer(query: Query) : OptimizerCompoundBase(query, EOptimizerID.LogicalOptimizerID) {
    override val classname = "LogicalOptimizer"
    override val childrenOptimizers = arrayOf(//
            arrayOf<OptimizerBase>(
//assign prefix to all operators which require those
                    LogicalOptimizerRemovePrefix(query)//
            ),
            arrayOf<OptimizerBase>(
//split all filters containing AND as main operator
                    LogicalOptimizerFilterSplitAND(query)//
            ),
            arrayOf<OptimizerBase>(
//remove all filters testing for equality by renaming one of the variables
                    LogicalOptimizerFilterEQ(query)//
            ),
            arrayOf<OptimizerBase>(
//solve all arithmetic equations with only constants
                    LogicalOptimizerArithmetic(query)//
            ),
            arrayOf<OptimizerBase>(
                    LogicalOptimizerRemoveProjection(query),//
                    LogicalOptimizerRemoveNOOP(query),//
                    LogicalOptimizerDistinctUp(query),//
                    LogicalOptimizerOptional(query),//
                    LogicalOptimizerBindToFilter(query)//
            ),
            arrayOf<OptimizerBase>(
                    LogicalOptimizerUnionUp(query),//
                    LogicalOptimizerFilterUp(query)//
            ),
            arrayOf<OptimizerBase>(
//join order must stant alone otherwise there are lots of recalulations
                    LogicalOptimizerJoinOrder(query)//
            ),
            arrayOf<OptimizerBase>(
                    LogicalOptimizerFilterDown(query)//
            ),
            arrayOf<OptimizerBase>(
//merge consecutive filters into a single AND connected one
                    LogicalOptimizerFilterMergeAND(query)//
            ),
            arrayOf<OptimizerBase>(
//try to remove any unnecessary projection operator, never query unused columns
                    LogicalOptimizerProjectionDown(query),//
                    LogicalOptimizerRemoveProjection(query),//
                    LogicalOptimizerFilterIntoTriple(query)//
            ),
            arrayOf<OptimizerBase>(
//calculate the sort order of the columns, as a prerequisite for physical optimisation
                    LogicalOptimizerColumnSortOrder(query)//
            )
    )
}
