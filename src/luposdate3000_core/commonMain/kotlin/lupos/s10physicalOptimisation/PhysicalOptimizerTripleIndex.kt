package lupos.s10physicalOptimisation

import lupos.s00misc.EOptimizerID
import lupos.s00misc.ESortType
import lupos.s00misc.Partition
import lupos.s00misc.SanityCheck
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.AOPVariable
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.noinput.LOPTriple
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.singleinput.LOPProjection
import lupos.s08logicalOptimisation.OptimizerBase
import lupos.s09physicalOperators.POPBase
import lupos.s09physicalOperators.singleinput.POPProjection
import lupos.s15tripleStoreDistributed.distributedTripleStore

class PhysicalOptimizerTripleIndex(query: Query) : OptimizerBase(query, EOptimizerID.PhysicalOptimizerTripleIndexID) {
    override val classname = "PhysicalOptimizerTripleIndex"
    override suspend fun optimize(node: IOPBase, parent: IOPBase?, onChange: () -> Unit): IOPBase {
        var res = node
        if (node is LOPTriple) {
            val projectedVariables: List<String>
            if (parent is LOPProjection) {
                projectedVariables = parent.getProvidedVariableNames()
            } else if (parent is POPProjection) {
                projectedVariables = parent.getProvidedVariableNamesInternal()
            } else if (node is POPBase) {
                projectedVariables = node.getProvidedVariableNamesInternal()
            } else {
                projectedVariables = node.getProvidedVariableNames()
            }
            onChange()
            val store = distributedTripleStore.getNamedGraph(query, node.graph)
            val params = Array<AOPBase>(3) {
                var res2 = node.children[it] as AOPBase
                SanityCheck {
                    if (res2 is AOPVariable) {
                        SanityCheck.check { projectedVariables.contains(res2.name) || res2.name == "_" }
                    }
                }
/*return*/res2
            }
            SanityCheck {
                for (i in 0 until node.mySortPriority.size) {
                    SanityCheck.check { node.mySortPriority[i].sortType == ESortType.FAST }
                }
            }
            res = store!!.getIterator(params, LOPTriple.getIndex(node.children, node.mySortPriority.map { it.variableName }), Partition())
            SanityCheck.check { res.getProvidedVariableNames().containsAll(node.mySortPriority.map { it.variableName }) }
            res.mySortPriority = node.mySortPriority
            res.sortPriorities = node.sortPriorities
        }
        return res
    }
}
