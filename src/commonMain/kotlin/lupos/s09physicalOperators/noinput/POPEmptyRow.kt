package lupos.s09physicalOperators.noinput

import kotlinx.coroutines.channels.Channel
import lupos.s00misc.CoroutinesHelper
import lupos.s00misc.EOperatorID
import lupos.s00misc.resultFlowProduce
import lupos.s00misc.Trace
import lupos.s03resultRepresentation.*
import lupos.s03resultRepresentation.ResultRow
import lupos.s03resultRepresentation.ResultSet
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s04logicalOperators.OPBase
import lupos.s09physicalOperators.POPBase


class POPEmptyRow(override val dictionary: ResultSetDictionary) : POPBase() {
    override val operatorID = EOperatorID.POPEmptyRowID
    override val classname = "POPEmptyRow"
    override val resultSet = ResultSet(dictionary)
    override val children: Array<OPBase> = arrayOf()
    var first = true
    override fun cloneOP() = POPEmptyRow(dictionary)

override fun toSparql()="{}"

    override fun equals(other: Any?): Boolean {
        if (other !is POPEmptyRow)
            return false
        if (dictionary !== other.dictionary)
            return false
        for (i in children.indices) {
            if (!children[i].equals(other.children[i]))
                return false
        }
        return true
    }

    override fun evaluate() = Trace.trace<Channel<ResultRow>>({ "POPEmptyRow.evaluate" }, {
        val channel = Channel<ResultRow>(CoroutinesHelper.channelType)
        CoroutinesHelper.run {
            try {
                channel.send(resultFlowProduce({ this@POPEmptyRow }, { resultSet.createResultRow() }))
                channel.close()
            } catch (e: Throwable) {
                channel.close(e)
            }
        }
        return channel
    })

}
