package lupos.s04arithmetikOperators.singleinput

import lupos.s00misc.Crypto
import lupos.s00misc.EOperatorID
import lupos.s03resultRepresentation.ValueDefinition
import lupos.s03resultRepresentation.ValueError
import lupos.s03resultRepresentation.ValueSimpleLiteral
import lupos.s03resultRepresentation.ValueStringBase
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.IQuery
import lupos.s04logicalOperators.iterator.IteratorBundle
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query

@UseExperimental(ExperimentalStdlibApi::class)
class AOPBuildInCallSHA1(query: IQuery, child: AOPBase) : AOPBase(query, EOperatorID.AOPBuildInCallSHA1ID, "AOPBuildInCallSHA1", arrayOf(child)) {
    override fun toSparql() = "SHA1(" + children[0].toSparql() + ")"
    override fun equals(other: Any?) = other is AOPBuildInCallSHA1 && children[0] == other.children[0]
    override fun evaluate(row: IteratorBundle): () -> ValueDefinition {
        val childA = (children[0] as AOPBase).evaluate(row)
        return {
            var res: ValueDefinition = ValueError()
            val a = childA()
            if (a is ValueStringBase) {
                res = ValueSimpleLiteral(a.delimiter, Crypto.sha1(a.content))
            }
/*return*/res
        }
    }

    override fun cloneOP(): IOPBase = AOPBuildInCallSHA1(query, children[0].cloneOP() as AOPBase)
}
