package lupos.s09physicalOperators.noinput

import kotlin.jvm.JvmField
import kotlinx.coroutines.channels.Channel
import lupos.s00misc.CoroutinesHelper
import lupos.s00misc.EOperatorID
import lupos.s00misc.resultFlowProduce
import lupos.s00misc.Trace
import lupos.s03resultRepresentation.*
import lupos.s03resultRepresentation.ResultRow
import lupos.s03resultRepresentation.ResultSet
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.ResultIterator
import lupos.s09physicalOperators.POPBase


class POPEmptyRow(query: Query) : POPBase(query, EOperatorID.POPEmptyRowID, "POPEmptyRow", ResultSet(query.dictionary), arrayOf()) {
    override fun cloneOP() = POPEmptyRow(query)
    override fun toSparql() = "{}"
    override fun equals(other: Any?) = other is POPEmptyRow
    override fun evaluate(): ResultIterator {
        val res = ResultIterator()
        res.next = {
            res.close()
            resultFlowProduce({ this@POPEmptyRow }, { resultSet.createResultRow() })
        }
        return res
    }
}
