package lupos.s00misc

import kotlin.jvm.JvmField

enum class EOptimizerID(@JvmField val optional: Boolean, @JvmField val repeatOnChange: Boolean) {
    LogicalOptimizerMinusAddSortID(true, true),
    LogicalOptimizerDistinctSplitID(true, true),
    LogicalOptimizerSortDownID(true, true),
    LogicalOptimizerReducedDownID(true, true),
    LogicalOptimizerID(false, true),
    LogicalOptimizerDetectMinusID(false, true),
    LogicalOptimizerDetectMinusStep2ID(false, true),
    LogicalOptimizerColumnSortOrderID(true, true),
    LogicalOptimizerRemoveProjectionID(true, true),
    LogicalOptimizerJoinOrderID(true, false),
    LogicalOptimizerUnionUpID(true, true),
    LogicalOptimizerExistsID(true, true),
    LogicalOptimizerArithmeticID(false, true),
    LogicalOptimizerFilterDownID(true, true),
    LogicalOptimizerFilterEQID(true, true),
    LogicalOptimizerFilterUpID(true, true),
    LogicalOptimizerBindUpID(true, true),
    LogicalOptimizerProjectionDownID(true, true),
    LogicalOptimizerProjectionUpID(true, true),
    LogicalOptimizerFilterIntoTripleID(true, true),
    LogicalOptimizerFilterOptionalID(true, true),
    LogicalOptimizerFilterOptionalStep2ID(true, true),
    LogicalOptimizerFilterSplitANDID(true, true),
    LogicalOptimizerFilterMergeANDID(true, true),
    LogicalOptimizerFilterSplitORID(true, true),
    LogicalOptimizerBindToFilterID(false, true),
    LogicalOptimizerRemoveNOOPID(false, true),
    LogicalOptimizerOptionalID(false, true),
    LogicalOptimizerRemovePrefixID(false, true),
    LogicalOptimizerRemoveBindVariableID(true, true),
    PhysicalOptimizerID(false, true),
    PhysicalOptimizerTripleIndexID(true, true),
    PhysicalOptimizerJoinTypeID(false, true),
    PhysicalOptimizerNaiveID(false, true),
    PhysicalOptimizerDebugID(false, true),
    PhysicalOptimizerPartition1ID(false, true),
    PhysicalOptimizerPartition2ID(false, true),
    PhysicalOptimizerPartition3ID(false, true),
    PhysicalOptimizerPartition4ID(false, true),
    KeyDistributionOptimizerID(false, true),
    LogicalOptimizerDistinctUpID(false, true),
    LogicalOptimizerStoreToValuesID(true, false),
}
