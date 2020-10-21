package lupos.s08logicalOptimisation
import lupos.s04logicalOperators.IOPBase
import lupos.s00misc.EOptimizerID
import lupos.s00misc.SanityCheck
import lupos.s04arithmetikOperators.noinput.AOPVariable
import lupos.s04arithmetikOperators.singleinput.AOPBuildInCallBOUND
import lupos.s04arithmetikOperators.singleinput.AOPBuildInCallNotExists
import lupos.s04arithmetikOperators.singleinput.AOPNot
import lupos.s04logicalOperators.multiinput.LOPJoin
import lupos.s04logicalOperators.multiinput.LOPMinus
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.singleinput.LOPFilter
import lupos.s04logicalOperators.singleinput.LOPSubGroup

class LogicalOptimizerDetectMinus(query: Query) : OptimizerBase(query, EOptimizerID.LogicalOptimizerDetectMinusID) {
    override val classname = "LogicalOptimizerDetectMinus"
    override suspend fun optimize(node: IOPBase, parent: IOPBase?, onChange: () -> Unit): IOPBase {
        var res: OPBase = node
        if (node is LOPFilter) {
            val node1 = node.children[1]
            if (node1 is AOPNot) {
                val node10 = node1.children[0]
                if (node10 is AOPBuildInCallBOUND) {
                    //there exists a filter, such that the variable is NOT bound.
                    //now search for_ an optional join, where this variable is bound only in the optional part
                    val variableName = (node10.children[0] as AOPVariable).name
                    searchForOptionalJoin(node, variableName, { p, i ->
                        val a = p.children[i].children[0]
                        val b = p.children[i].children[1]
                        var c = b
                        while (c is LOPSubGroup) {
                            c = c.children[0]
                        }
                        if (c is LOPFilter && !c.getProvidedVariableNames().containsAll(c.children[1].getRequiredVariableNamesRecoursive())) {
                            //only use minus if there is another filter which requires variables from the other operand
                            val tmpFakeVariables = b.getProvidedVariableNames().toMutableList()
                            tmpFakeVariables.removeAll(a.getProvidedVariableNames())
                            if (b.getProvidedVariableNames().containsAll(a.getProvidedVariableNames())) {
                                p.children[i] = LOPMinus(query, a, b, tmpFakeVariables)
                            } else {
                                c = b
                                while (c.children[0] is LOPSubGroup || c.children[0] is LOPFilter) {
                                    c = c.children[0]
                                }
                                SanityCheck.check { c is LOPSubGroup || c is LOPFilter }
                                c.children[0] = LOPJoin(query, a.cloneOP(), c.children[0], false)//put a below all the filters - to prevent these filters from missing variables
                                p.children[i] = LOPMinus(query, a, b, tmpFakeVariables)//put all the variables into the subtracting child too - to be able to process the filters
                            }
                            res = node.children[0] // remove the !bound part
                            onChange()
                        }
                    })
                }
            } else if (node1 is AOPBuildInCallNotExists) {
                val a = node.children[0]
                val b = node1.children[0]
                if (b.getProvidedVariableNames().containsAll(a.getProvidedVariableNames())) {
                    res = LOPMinus(query, a, b, listOf())
                } else {
                    res = LOPMinus(query, a, LOPJoin(query, a.cloneOP(), b, false), listOf())
                }
                onChange()
            }
        }
        return res
    }

    fun searchForOptionalJoin(node: OPBase, variableName: String, action: (OPBase, Int) -> Unit) {
        for (c in 0 until node.children.size) {
            val child = node.children[c]
            if (child is LOPJoin && child.optional && !child.children[0].getProvidedVariableNames().contains(variableName) && child.children[1].getProvidedVariableNames().contains(variableName)) {
                action(node, c)
            } else {
                searchForOptionalJoin(child, variableName, action)
            }
        }
    }
}
