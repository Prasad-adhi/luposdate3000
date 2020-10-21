package lupos.s04arithmetikOperators.multiinput


import lupos.s00misc.EOperatorID
import lupos.s00misc.SparqlFeatureNotImplementedException

import lupos.s03resultRepresentation.ValueDefinition
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04logicalOperators.iterator.IteratorBundle
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query

class AOPSet(query: Query, childs: List<AOPBase>) : AOPBase(query, EOperatorID.AOPSetID, "AOPSet", Array(childs.size) { childs[it] }) {
    override fun toSparql(): String {
        var res = ""
        res += "("
        if (children.size > 0) {
            res += children[0].toSparql()
        }
        for (i in 1 until children.size) {
            res += "," + children[i].toSparql()
        }
        res += ")"
        return res
    }

    override fun equals(other: Any?) = other is AOPSet && children.contentEquals(other.children)
    override fun evaluate(row: IteratorBundle): () -> ValueDefinition {
        throw SparqlFeatureNotImplementedException("AOPSet")

    }

    override fun cloneOP() :IOPBase= AOPSet(query, List(children.size) { children[it].cloneOP() as AOPBase })
}
