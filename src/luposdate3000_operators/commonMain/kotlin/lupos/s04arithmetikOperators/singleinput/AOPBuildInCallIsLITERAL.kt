package lupos.s04arithmetikOperators.singleinput

import lupos.s00misc.EOperatorID
import lupos.s03resultRepresentation.*
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.IQuery
import lupos.s04logicalOperators.iterator.IteratorBundle

class AOPBuildInCallIsLITERAL(query: IQuery, child: AOPBase) : AOPBase(query, EOperatorID.AOPBuildInCallIsLITERALID, "AOPBuildInCallIsLITERAL", arrayOf(child)) {
    override fun toSparql(): String = "isLiteral(" + children[0].toSparql() + ")"
    override fun equals(other: Any?): Boolean = other is AOPBuildInCallIsLITERAL && children[0] == other.children[0]
    override fun evaluate(row: IteratorBundle): () -> ValueDefinition {
        val childA = (children[0] as AOPBase).evaluate(row)
        return {
            var res: ValueDefinition = ValueError()
            val a = childA()
            if (a !is ValueUndef && a !is ValueError) {
                res = ValueBoolean(a is ValueStringBase || a is ValueDouble || a is ValueBoolean || a is ValueInteger || a is ValueDecimal || a is ValueFloat || a is ValueDateTime)
            }
/*return*/res
        }
    }

    override fun enforcesBooleanOrError(): Boolean = true
    override fun cloneOP(): IOPBase = AOPBuildInCallIsLITERAL(query, children[0].cloneOP() as AOPBase)
}
