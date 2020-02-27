package lupos.s08logicalOptimisation

import lupos.s00misc.EOptimizerID
import lupos.s00misc.ExecuteOptimizer
import lupos.s03resultRepresentation.ResultSet
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.AOPUndef
import lupos.s04arithmetikOperators.noinput.AOPValue
import lupos.s04logicalOperators.OPBase
import lupos.s08logicalOptimisation.OptimizerBase


class LogicalOptimizerArithmetic(transactionID: Long, dictionary: ResultSetDictionary) : OptimizerBase(transactionID, dictionary, EOptimizerID.LogicalOptimizerArithmeticID) {
    override val classname = "LogicalOptimizerArithmetic"
    override fun optimize(node: OPBase, parent: OPBase?, onChange: () -> Unit) = ExecuteOptimizer.invoke({ this }, { node }, {
        var res = node
        if (node is AOPBase && node !is AOPValue && node.children.size > 0 && node.getRequiredVariableNamesRecoursive().size == 0) {
            try {
                val resultSet = ResultSet(ResultSetDictionary())
                val resultRow = resultSet.createResultRow()
                res = node.calculate(resultSet, resultRow)
            } catch (e: Throwable) {
                res = AOPUndef()
            }
            onChange()
        }
        res
    })
}
