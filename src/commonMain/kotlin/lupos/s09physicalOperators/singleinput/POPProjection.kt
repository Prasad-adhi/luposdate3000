package lupos.s09physicalOperators.singleinput

import kotlinx.coroutines.channels.Channel
import lupos.s00misc.CoroutinesHelper
import lupos.s00misc.EOperatorID
import lupos.s00misc.resultFlowConsume
import lupos.s00misc.resultFlowProduce
import lupos.s00misc.Trace
import lupos.s00misc.XMLElement
import lupos.s03resultRepresentation.*
import lupos.s03resultRepresentation.ResultSet
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s03resultRepresentation.Variable
import lupos.s04arithmetikOperators.noinput.AOPVariable
import lupos.s04logicalOperators.noinput.OPNothing
import lupos.s04logicalOperators.OPBase
import lupos.s09physicalOperators.POPBase


class POPProjection(override val dictionary: ResultSetDictionary, @JvmField val variables: MutableList<AOPVariable>, child: OPBase) : POPBase() {
    override val operatorID = EOperatorID.POPProjectionID
    override val classname = "POPProjection"
    override val resultSet = ResultSet(dictionary)
    override val children: Array<OPBase> = arrayOf(child)
    override fun equals(other: Any?): Boolean {
        if (other !is POPProjection)
            return false
        if (dictionary !== other.dictionary)
            return false
        if (!variables.equals(other.variables))
            return false
        for (i in children.indices) {
            if (!children[i].equals(other.children[i]))
                return false
        }
        return true
    }

    override fun toSparql(): String {
        var res = "{SELECT "
        for (c in variables)
            res += c.toSparql() + " "
        res += "{"
        res += children[0].toSparql()
        res += "}}"
        return res
    }

    override fun cloneOP() = POPProjection(dictionary, variables, children[0].cloneOP())

    override fun getProvidedVariableNames(): List<String> {
        return MutableList(variables.size) { variables[it].name }.distinct()
    }

    override fun getRequiredVariableNames(): List<String> {
        return MutableList(variables.size) { variables[it].name }.distinct()
    }

    override fun evaluate() = Trace.trace<Channel<ResultRow>>({ "POPProjection.evaluate" }, {
        val variablesOld = Array(variables.size) { children[0].resultSet.createVariable(variables[it].name) }
        val variablesNew = Array(variables.size) { resultSet.createVariable(variables[it].name) }
        val children0Channel = children[0].evaluate()
        val channel = Channel<ResultRow>(CoroutinesHelper.channelType)
        CoroutinesHelper.run {
            try {
                for (rsOld in children0Channel) {
                    resultFlowConsume({ this@POPProjection }, { children[0] }, { rsOld })
                    var rsNew = resultSet.createResultRow()
                    for (i in variablesNew.indices)
                        rsNew[variablesNew[i]] = rsOld[variablesOld[i]]
                    channel.send(resultFlowProduce({ this@POPProjection }, { rsNew }))
                }
                channel.close()
                children0Channel.close()
            } catch (e: Throwable) {
                channel.close(e)
                children0Channel.close(e)
            }
        }
        return channel
    })

    override fun toXMLElement(): XMLElement {
        val res = XMLElement("POPProjection")
        res.addAttribute("uuid", "" + uuid)
        val vars = XMLElement("variables")
        res.addContent(vars)
        for (v in variables)
            vars.addContent(XMLElement("variable").addAttribute("name", v.name))
        res.addContent(childrenToXML())
        return res
    }
}
