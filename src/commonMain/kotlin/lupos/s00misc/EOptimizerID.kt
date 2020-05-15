package lupos.s00misc

import kotlin.jvm.JvmField
import lupos.s00misc.Coverage

enum class EOptimizerID(@JvmField val optional: Boolean, @JvmField val repeatOnChange: Boolean) {
    LogicalOptimizerDistinctSplitID(true, true),
    LogicalOptimizerID(false, true),
    LogicalOptimizerDetectMinusID(false, true),
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
    KeyDistributionOptimizerID(false, true),
    LogicalOptimizerDistinctUpID(false, true),
    LogicalOptimizerStoreToValuesID(true, false),
}
