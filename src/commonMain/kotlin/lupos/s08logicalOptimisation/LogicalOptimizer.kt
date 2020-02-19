package lupos.s08logicalOptimisation

import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.singleinput.LOPNOOP
import lupos.s04logicalOperators.singleinput.modifiers.LOPPrefix
import lupos.s08logicalOptimisation.OptimizerBase


class LogicalOptimizer(transactionID: Long, dictionary: ResultSetDictionary) : OptimizerBase(transactionID, dictionary) {
val optimizer=arrayOf(LogicalOptimizerRemovePrefix(transactionID,dictionary),LogicalOptimizerRemoveNOOP(transactionID,dictionary))
    override fun optimize(node: OPBase, parent: OPBase?): OPBase {
	var tmp=node
	for(o in optimizer)
		tmp=o.optimize(tmp,parent)
	return tmp
    }

}
