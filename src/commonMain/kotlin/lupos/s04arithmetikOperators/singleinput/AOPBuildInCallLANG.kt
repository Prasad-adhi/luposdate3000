package lupos.s04arithmetikOperators.singleinput
import lupos.s00misc.Coverage
import lupos.s00misc.EOperatorID
import lupos.s03resultRepresentation.Value
import lupos.s03resultRepresentation.ValueBoolean
import lupos.s03resultRepresentation.ValueDateTime
import lupos.s03resultRepresentation.ValueDefinition
import lupos.s03resultRepresentation.ValueError
import lupos.s03resultRepresentation.ValueLanguageTaggedLiteral
import lupos.s03resultRepresentation.ValueNumeric
import lupos.s03resultRepresentation.ValueSimpleLiteral
import lupos.s03resultRepresentation.ValueStringBase
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04logicalOperators.iterator.ColumnIterator
import lupos.s04logicalOperators.iterator.IteratorBundle
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
class AOPBuildInCallLANG(query: Query, child: AOPBase) : AOPBase(query, EOperatorID.AOPBuildInCallLANGID, "AOPBuildInCallLANG", arrayOf(child)) {
    override fun toSparql() = "LANG(" + children[0].toSparql() + ")"
    override fun equals(other: Any?): Boolean {
Coverage.funStart(3100)
        if (other !is AOPBuildInCallLANG) {
Coverage.ifStart(3101)
            return false
        }
Coverage.statementStart(3102)
        return children[0] == other.children[0]
    }
    override fun evaluate(row: IteratorBundle): () -> ValueDefinition {
Coverage.funStart(3103)
        val childA = (children[0] as AOPBase).evaluate(row)
Coverage.statementStart(3104)
        return {
Coverage.statementStart(3105)
            var res: ValueDefinition = ValueError()
Coverage.statementStart(3106)
            val a = childA()
Coverage.statementStart(3107)
            if (a is ValueLanguageTaggedLiteral) {
Coverage.ifStart(3108)
                res = ValueSimpleLiteral(a.delimiter, a.language)
Coverage.statementStart(3109)
            } else if (a is ValueStringBase || a is ValueNumeric || a is ValueBoolean || a is ValueDateTime) {
Coverage.ifStart(3110)
                res = ValueSimpleLiteral("\"", "")
Coverage.statementStart(3111)
            }
Coverage.statementStart(3112)
/*return*/res
        }
Coverage.statementStart(3113)
    }
    override fun cloneOP() = AOPBuildInCallLANG(query, children[0].cloneOP() as AOPBase)
}
