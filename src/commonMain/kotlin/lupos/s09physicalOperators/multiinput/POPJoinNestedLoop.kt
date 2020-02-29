package lupos.s09physicalOperators.multiinput

import kotlinx.coroutines.channels.Channel
import lupos.s00misc.CoroutinesHelper
import lupos.s00misc.EOperatorID
import lupos.s00misc.resultFlowConsume
import lupos.s00misc.resultFlowProduce
import lupos.s00misc.Trace
import lupos.s00misc.XMLElement
import lupos.s03resultRepresentation.*
import lupos.s03resultRepresentation.ResultRow
import lupos.s03resultRepresentation.ResultSet
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s03resultRepresentation.Variable
import lupos.s04logicalOperators.noinput.OPNothing
import lupos.s04logicalOperators.OPBase
import lupos.s09physicalOperators.POPBase
import lupos.s09physicalOperators.singleinput.POPTemporaryStore


class POPJoinNestedLoop : POPBase {
    override val operatorID = EOperatorID.POPJoinNestedLoopID
    override val classname = "POPJoinNestedLoop"
    override val dictionary: ResultSetDictionary
    override val resultSet: ResultSet
    override val children: Array<OPBase> = arrayOf(OPNothing(), OPNothing())
    val optional: Boolean
    val joinVariables = mutableListOf<String>()
    private val variablesOldA = mutableListOf<Pair<Variable, Variable>>()//not joined
    private val variablesOldB = mutableListOf<Pair<Variable, Variable>>()//not joined
    private val variablesOldJ = mutableListOf<Pair<Pair<Variable, Variable>, Variable>>()//joined
    private var resultRowA: ResultRow? = null
    private var hadMatchForA = false

    override fun equals(other: Any?): Boolean {
        if (other !is POPJoinNestedLoop)
            return false
        if (dictionary !== other.dictionary)
            return false
        if (optional != other.optional)
            return false
        for (i in children.indices) {
            if (!children[i].equals(other.children[i]))
                return false
        }
        return true
    }

    constructor(dictionary: ResultSetDictionary, childA: OPBase, childB: OPBase, optional: Boolean) : super() {
        this.dictionary = dictionary
        resultSet = ResultSet(dictionary)
        this.children[0] = childA
        this.children[1] = POPTemporaryStore(dictionary, childB)
        this.optional = optional
        require(children[0].resultSet.dictionary == dictionary || (!(this.children[0] is POPBase)))
        require(children[1].resultSet.dictionary == dictionary || (!(this.children[0] is POPBase)))
        val variablesAt = children[0].getProvidedVariableNames()
        val variablesBt = children[1].getProvidedVariableNames()
        val variablesA = MutableList(variablesAt.size) { variablesAt[it] }
        val variablesB = MutableList(variablesBt.size) { variablesBt[it] }
        variablesA.forEach {
            if (variablesB.contains(it))
                joinVariables.add(it)
        }
        val variablesA2 = variablesA.minus(joinVariables)
        val variablesB2 = variablesB.minus(joinVariables)
        for (name in variablesA2)
            variablesOldA.add(Pair(children[0].resultSet.createVariable(name), resultSet.createVariable(name)))
        for (name in variablesB2)
            variablesOldB.add(Pair(children[1].resultSet.createVariable(name), resultSet.createVariable(name)))
        for (name in joinVariables)
            variablesOldJ.add(Pair(Pair(children[0].resultSet.createVariable(name), children[1].resultSet.createVariable(name)), resultSet.createVariable(name)))
    }

    override fun evaluate() = Trace.trace<Channel<ResultRow>>({ "POPJoinNestedLoop.evaluate" }, {
        val channels = children.map { it.evaluate() }
        val channel = Channel<ResultRow>(CoroutinesHelper.channelType)
        CoroutinesHelper.run {
            try {
                for (resultRowA in channels[0]) {
                    resultFlowConsume({ this@POPJoinNestedLoop }, { children[0] }, { resultRowA })
                    for (resultRowB in channels[1]) {
                        resultFlowConsume({ this@POPJoinNestedLoop }, { children[1] }, { resultRowB })
                        var joinVariableOk = true
                        var rsNew = resultSet.createResultRow()
                        for (p in variablesOldA) {
                            // TODO reuse resultSet
                            rsNew[p.second] = resultRowA!![p.first]
                        }
                        for (p in variablesOldB) {
                            // TODO reuse resultSet
                            rsNew[p.second] = resultRowB[p.first]
                        }
                        for (p in variablesOldJ) {
                            // TODO reuse resultSet
                            val a = children[0].resultSet.getValue(resultRowA!![p.first.first])
                            val b = children[1].resultSet.getValue(resultRowB[p.first.second])
                            if (a != b && (!children[0].resultSet.isUndefValue(resultRowA!!, p.first.first)) && (!children[1].resultSet.isUndefValue(resultRowB, p.first.second))) {
                                joinVariableOk = false
                                break
                            }
                            if (children[0].resultSet.isUndefValue(resultRowA!!, p.first.first))
                                rsNew[p.second] = resultSet.createValue(b)
                            else
                                rsNew[p.second] = resultSet.createValue(a)
                        }
                        if (!joinVariableOk)
                            continue
                        hadMatchForA = true
                        channel.send(resultFlowProduce({ this@POPJoinNestedLoop }, { rsNew }))
                    }
                    (children[1] as POPTemporaryStore).reset()
                }
                channel.close()
                for (c in channels)
                    c.close()
            } catch (e: Throwable) {
                channel.close(e)
                for (c in channels)
                    c.close(e)
            }
        }
        return channel
    })

    override fun toXMLElement() = super.toXMLElement().addAttribute("optional", "" + optional)
    override fun cloneOP() = POPJoinNestedLoop(dictionary, children[0].cloneOP(), children[1].cloneOP(), optional)
}
