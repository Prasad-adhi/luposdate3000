package lupos.s04arithmetikOperators.singleinput
import lupos.s04logicalOperators.Query

import kotlin.jvm.JvmField
import lupos.s00misc.EOperatorID
import lupos.s00misc.resultFlow
import lupos.s03resultRepresentation.ResultRow
import lupos.s03resultRepresentation.ResultSet
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04arithmetikOperators.noinput.AOPBoolean
import lupos.s04arithmetikOperators.noinput.AOPConstant
import lupos.s04arithmetikOperators.noinput.AOPDateTime
import lupos.s04arithmetikOperators.noinput.AOPDecimal
import lupos.s04arithmetikOperators.noinput.AOPDouble
import lupos.s04arithmetikOperators.noinput.AOPInteger
import lupos.s04arithmetikOperators.noinput.AOPTypedLiteral
import lupos.s04logicalOperators.OPBase


class AOPBuildInCallDATATYPE(query:Query,child: AOPBase) : AOPBase(query,EOperatorID.AOPBuildInCallDATATYPEID,"AOPBuildInCallDATATYPE",arrayOf(child)) {

    override fun toSparql() = "DATATYPE(" + children[0].toSparql() + ")"

    override fun equals(other: Any?): Boolean {
        if (other !is AOPBuildInCallDATATYPE)
            return false
        return children[0] == other.children[0]
    }

    override fun calculate(resultSet: ResultSet, resultRow: ResultRow): AOPConstant {
        val a = (children[0] as AOPBase).calculate(resultSet, resultRow)
        when (a) {
            is AOPSimpleLiteral -> return resultFlow({ this }, { resultRow }, { resultSet }, {
                AOPIri(query,"http://www.w3.org/2001/XMLSchema#string")
            })
            is AOPLanguageTaggedLiteral -> return resultFlow({ this }, { resultRow }, { resultSet }, {
                AOPIri(query,"http://www.w3.org/1999/02/22-rdf-syntax-ns#langString")
            })
            is AOPTypedLiteral -> return resultFlow({ this }, { resultRow }, { resultSet }, {
                AOPIri(query,a.type_iri)
            })
            is AOPBoolean -> return resultFlow({ this }, { resultRow }, { resultSet }, {
                AOPIri(query,"http://www.w3.org/2001/XMLSchema#boolean")
            })
            is AOPDateTime -> return resultFlow({ this }, { resultRow }, { resultSet }, {
                AOPIri(query,"http://www.w3.org/2001/XMLSchema#dateTime")
            })
            is AOPDecimal -> return resultFlow({ this }, { resultRow }, { resultSet }, {
                AOPIri(query,"http://www.w3.org/2001/XMLSchema#decimal")
            })
            is AOPDouble -> return resultFlow({ this }, { resultRow }, { resultSet }, {
                AOPIri(query,"http://www.w3.org/2001/XMLSchema#double")
            })
            is AOPInteger -> return resultFlow({ this }, { resultRow }, { resultSet }, {
                AOPIri(query,"http://www.w3.org/2001/XMLSchema#integer")
            })
        }
        throw resultFlow({ this }, { resultRow }, { resultSet }, {
            Exception("AOPBuiltInCall DATATYPE only works with typed string input")
        })
    }

    override fun cloneOP() = AOPBuildInCallDATATYPE(query,children[0].cloneOP() as AOPBase)
}
