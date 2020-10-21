package lupos.s09physicalOperators.multiinput

import lupos.s00misc.EOperatorID
import lupos.s00misc.ESortPriority
import lupos.s00misc.Partition
import lupos.s00misc.SanityCheck
import lupos.s04logicalOperators.iterator.ColumnIterator
import lupos.s04logicalOperators.iterator.ColumnIteratorMultiIterator
import lupos.s04logicalOperators.iterator.IteratorBundle
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s09physicalOperators.POPBase

class POPUnion(query: Query, projectedVariables: List<String>, childA: OPBase, childB: OPBase) : POPBase(query, projectedVariables, EOperatorID.POPUnionID, "POPUnion", arrayOf(childA, childB), ESortPriority.UNION) {
    override fun getPartitionCount(variable: String): Int {
        if (children[0].getProvidedVariableNames().contains(variable)) {
            if (children[1].getProvidedVariableNames().contains(variable)) {
                SanityCheck.check { children[0].getPartitionCount(variable) == children[1].getPartitionCount(variable) }
                return children[0].getPartitionCount(variable)
            } else {
                return children[0].getPartitionCount(variable)
            }
        } else {
            if (children[1].getProvidedVariableNames().contains(variable)) {
                return children[1].getPartitionCount(variable)
            } else {
                throw Exception("unknown variable $variable")
            }
        }
    }

    override fun cloneOP() :IOPBase= POPUnion(query, projectedVariables, children[0].cloneOP(), children[1].cloneOP())
    override fun toSparql() = "{" + children[0].toSparql() + "} UNION {" + children[1].toSparql() + "}"
    override fun equals(other: Any?): Boolean = other is POPUnion && children[0] == other.children[0] && children[1] == other.children[1]
    override suspend fun evaluate(parent: Partition): IteratorBundle {
        val variables = getProvidedVariableNames()
        SanityCheck {
            for (v in children[0].getProvidedVariableNames()) {
                getPartitionCount(v)
            }
            for (v in children[1].getProvidedVariableNames()) {
                getPartitionCount(v)
            }
        }
        SanityCheck.check { children[0].getProvidedVariableNames().containsAll(variables) }
        SanityCheck.check { children[1].getProvidedVariableNames().containsAll(variables) }
        val outMap = mutableMapOf<String, ColumnIterator>()
        val childA = children[0].evaluate(parent)
        val childB = children[1].evaluate(parent)
        if (variables.size > 0) {
            for (variable in variables) {
                outMap[variable] = ColumnIteratorMultiIterator(listOf(childA.columns[variable]!!, childB.columns[variable]!!))
            }
            return IteratorBundle(outMap)
        } else {
            SanityCheck.check { childA.hasCountMode() && childB.hasCountMode() }
            var res = object : IteratorBundle(0) {
                override suspend fun hasNext2(): Boolean {
                    return childA.hasNext2() || childB.hasNext2()
                }

                suspend override fun hasNext2Close() {
                    childA.hasNext2Close()
                    childB.hasNext2Close()
                }
            }
            return res
        }
    }
}
