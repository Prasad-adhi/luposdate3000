package lupos.s09physicalOperators.singleinput

import kotlin.jvm.JvmField
import kotlinx.coroutines.channels.Channel
import lupos.s00misc.CoroutinesHelper
import lupos.s00misc.EOperatorID
import lupos.s00misc.resultFlowConsume
import lupos.s00misc.resultFlowProduce
import lupos.s00misc.Trace
import lupos.s03resultRepresentation.*
import lupos.s03resultRepresentation.ResultRow
import lupos.s03resultRepresentation.ResultSet
import lupos.s03resultRepresentation.Variable
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04logicalOperators.noinput.*
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s09physicalOperators.POPBase


class POPMakeBooleanResult(query: Query, child: OPBase) : POPBase(query, EOperatorID.POPMakeBooleanResultID, "POPMakeBooleanResult", ResultSet(query.dictionary), arrayOf(child)) {
    override fun equals(other: Any?): Boolean {
        if (other !is POPMakeBooleanResult)
            return false
        for (i in children.indices) {
            if (!children[i].equals(other.children[i]))
                return false
        }
        return true
    }

    override fun toSparqlQuery() = "ASK{" + children[0].toSparql() + "}"

    override fun cloneOP() = POPMakeBooleanResult(query, children[0].cloneOP())

    override fun getProvidedVariableNames() = mutableListOf("?boolean")

    override fun getRequiredVariableNames() = listOf<String>()

    override fun evaluate() = Trace.trace<Channel<ResultRow>>({ "POPMakeBooleanResult.evaluate" }, {
        val variableNew = resultSet.createVariable("?boolean")
        val children0Channel = children[0].evaluate()
        val channel = Channel<ResultRow>(CoroutinesHelper.channelType)
        CoroutinesHelper.run {
            try {
                var first = true
                for (c in children0Channel) {
                    resultFlowConsume({ this@POPMakeBooleanResult }, { children[0] }, { c })
                    first = false
                    children0Channel.close()
                    break
                }
                var rsNew = resultSet.createResultRow()
                rsNew[variableNew] = resultSet.createValue(AOPBoolean(query, !first).valueToString())
                channel.send(resultFlowProduce({ this@POPMakeBooleanResult }, { rsNew }))
                channel.close()
            } catch (e: Throwable) {
                channel.close(e)
            }
        }
        return channel
    })

}
