package lupos.s04arithmetikOperators.multiinput

import lupos.s00misc.*
import lupos.s03resultRepresentation.*
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.IQuery
import lupos.s04logicalOperators.iterator.IteratorBundle

class AOPDivision(query: IQuery, childA: AOPBase, childB: AOPBase) : AOPBinaryOperationFixedName(query, EOperatorID.AOPDivisionID, "AOPDivision", arrayOf(childA, childB)) {
    override fun toSparql(): String = "(" + children[0].toSparql() + " / " + children[1].toSparql() + ")"
    override fun equals(other: Any?): Boolean = other is AOPDivision && children[0] == other.children[0] && children[1] == other.children[1]
    override fun evaluate(row: IteratorBundle): () -> ValueDefinition {
        val childA = (children[0] as AOPBase).evaluate(row)
        val childB = (children[1] as AOPBase).evaluate(row)
        return {
            var res: ValueDefinition = ValueError()
            val a = childA()
            val b = childB()
            try {
                if (a is ValueDouble || b is ValueDouble) {
                    if (b.toDouble() != 0.0) {
                        res = ValueDouble(a.toDouble() / b.toDouble())
                    }
                }
                if (a is ValueFloat || b is ValueFloat) {
                    if (b.toDouble() != 0.0) {
                        res = ValueFloat(a.toDouble() / b.toDouble())
                    }
                }
                if (a is ValueDecimal || b is ValueDecimal) {
                    if (b.toDecimal() != MyBigDecimal("0.0")) {
                        res = ValueDecimal(a.toDecimal() / b.toDecimal())
                    }
                }
                if (a is ValueInteger || b is ValueInteger) {
                    if (b.toInt() != MyBigInteger("0")) {
                        res = ValueDecimal(a.toDecimal() / b.toDecimal())
                    }
                }
            } catch (e: EvaluationException) {
            } catch (e: Throwable) {
                SanityCheck.println { "TODO exception 23" }
                e.printStackTrace()
            }
/*return*/res
        }
    }

    override fun cloneOP(): IOPBase = AOPDivision(query, children[0].cloneOP() as AOPBase, children[1].cloneOP() as AOPBase)
}
