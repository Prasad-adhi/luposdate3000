package lupos.s04arithmetikOperators.singleinput

import kotlin.jvm.JvmField
import lupos.s00misc.EOperatorID
import lupos.s03resultRepresentation.*
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04logicalOperators.iterator.*
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query

class AOPBuildInCallLANG(query: Query, child: AOPBase) : AOPBase(query, EOperatorID.AOPBuildInCallLANGID, "AOPBuildInCallLANG", arrayOf(child)) {
    override fun toSparql() = "LANG(" + children[0].toSparql() + ")"
    override fun equals(other: Any?): Boolean {
        if (other !is AOPBuildInCallLANG)
            return false
        return children[0] == other.children[0]
    }

    override fun evaluate(row: ColumnIteratorRow): () -> ValueDefinition {
        val childA = (children[0] as AOPBase).evaluate(row)
        return {
            var res: ValueDefinition = ValueError()
            val a = childA()
            if (a is ValueLanguageTaggedLiteral)
                res = ValueSimpleLiteral(a.delimiter, a.language)
            else if (a is ValueStringBase || a is ValueNumeric || a is ValueBoolean || a is ValueDateTime)
                res = ValueSimpleLiteral("\"", "")
            res
        }
    }

    override fun cloneOP() = AOPBuildInCallLANG(query, children[0].cloneOP() as AOPBase)
}
