package lupos.s10physicalOptimisation

import lupos.s00misc.Coverage
import lupos.s00misc.EOptimizerID
import lupos.s04logicalOperators.Query
import lupos.s08logicalOptimisation.OptimizerCompoundBase
import lupos.s08logicalOptimisation.OptimizerBase

class PhysicalOptimizer(query: Query) : OptimizerCompoundBase(query, EOptimizerID.PhysicalOptimizerID) {
    override val classname = "PhysicalOptimizer"
    override val childrenOptimizers = arrayOf(//
            arrayOf<OptimizerBase>(
                    PhysicalOptimizerJoinType(query),//
),
arrayOf<OptimizerBase>(
                    PhysicalOptimizerTripleIndex(query),//
),
arrayOf<OptimizerBase>(
                    PhysicalOptimizerNaive(query),//
),
arrayOf<OptimizerBase>(
                    PhysicalOptimizerDebug(query)
            )
    )
}
