package lupos.s10physicalOptimisation

import kotlin.jvm.JvmField
import lupos.s00misc.EOptimizerID
import lupos.s03resultRepresentation.ResultChunk
import lupos.s04arithmetikOperators.ResultVektorRaw
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.ResultIterator
import lupos.s08logicalOptimisation.OptimizerCompoundBase


class PhysicalOptimizer(query: Query) : OptimizerCompoundBase(query, EOptimizerID.PhysicalOptimizerID) {
    override val classname = "PhysicalOptimizer"
    override val childrenOptimizers = arrayOf(//
            PhysicalOptimizerTripleIndex(query),//
            PhysicalOptimizerNaive(query))
}
