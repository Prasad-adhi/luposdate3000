package lupos.s04arithmetikOperators.multiinput

import kotlin.jvm.JvmField
import lupos.s00misc.EOperatorID
import lupos.s00misc.resultFlow
import lupos.s03resultRepresentation.*
import lupos.s03resultRepresentation.ResultRow
import lupos.s03resultRepresentation.ResultSet
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.ResultIterator


class AOPBuildInCallCONCAT(query: Query, child: AOPBase, childB: AOPBase) : AOPBase(query, EOperatorID.AOPBuildInCallCONCATID, "AOPBuildInCallCONCAT", arrayOf(child, childB)) {

    override fun toSparql() = "CONCAT(" + children[0].toSparql() + ", " + children[1].toSparql() + ")"

    override fun equals(other: Any?): Boolean {
        if (other !is AOPBuildInCallCONCAT)
            return false
        for (i in children.indices) {
            if (children[i] != other.children[i])
                return false
        }
        return true
    }

    override fun calculate(resultSet: ResultSet, resultRow: ResultRow): ValueDefinition {
        val a = (children[0] as AOPBase).calculate(resultSet, resultRow)
        val b = (children[1] as AOPBase).calculate(resultSet, resultRow)
        if (a is ValueLanguageTaggedLiteral && b is ValueLanguageTaggedLiteral && a.language == b.language)
            return resultFlow({ this }, { resultRow }, { resultSet }, {
                ValueLanguageTaggedLiteral(a.delimiter, a.content + b.content, a.language)
            })
        if (a is ValueTypedLiteral && b is ValueTypedLiteral && a.type_iri == "http://www.w3.org/2001/XMLSchema#string" && a.type_iri == b.type_iri)
            return resultFlow({ this }, { resultRow }, { resultSet }, {
                ValueTypedLiteral.create(a.delimiter, a.content + b.content, a.type_iri)
            })
        if (a is ValueStringBase && b is ValueStringBase)
            return resultFlow({ this }, { resultRow }, { resultSet }, {
                ValueSimpleLiteral(a.delimiter, a.content + b.content)
            })
        throw resultFlow({ this }, { resultRow }, { resultSet }, {
            Exception("AOPBuiltInCall CONCAT only works with compatible string input")
        })
    }

    override fun cloneOP() = AOPBuildInCallCONCAT(query, children[0].cloneOP() as AOPBase, children[1].cloneOP() as AOPBase)
}
