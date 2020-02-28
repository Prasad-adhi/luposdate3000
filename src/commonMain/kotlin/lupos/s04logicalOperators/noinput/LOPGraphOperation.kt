package lupos.s04logicalOperators.noinput

import lupos.s00misc.EGraphOperationType
import lupos.s00misc.EOperatorID
import lupos.s02buildSyntaxTree.sparql1_1.ASTGraphRef
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.OPBase


class LOPGraphOperation(var action: EGraphOperationType = EGraphOperationType.CREATE, var silent: Boolean = false, var graphref1: ASTGraphRef? = null, var graphref2: ASTGraphRef? = null) : LOPBase() {
    override val operatorID = EOperatorID.LOPGraphOperationID
    override val classname = "LOPGraphOperation"
    override val children: Array<OPBase> = arrayOf()

    override fun equals(other: Any?): Boolean {
        if (other !is LOPGraphOperation)
            return false
        if (silent != other.silent)
            return false
        if (graphref1 != other.graphref1)
            return false
        if (graphref2 != other.graphref2)
            return false
        if (action != other.action)
            return false
        return true
    }

    override fun cloneOP() = LOPGraphOperation(action, silent, graphref1, graphref2)
}
