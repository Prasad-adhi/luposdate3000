package lupos.s09physicalOperators.singleinput

import lupos.s00misc.CoroutinesHelper
import lupos.s00misc.EOperatorID
import lupos.s00misc.resultFlowConsume
import lupos.s00misc.resultFlowProduce
import lupos.s00misc.Trace
import lupos.s03resultRepresentation.ResultSet
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s03resultRepresentation.Value
import lupos.s03resultRepresentation.Variable
import lupos.s04arithmetikOperators.noinput.AOPVariable
import lupos.s04logicalOperators.noinput.OPNothing
import lupos.s04logicalOperators.OPBase
import lupos.s09physicalOperators.POPBase


class POPFilterExact(override val dictionary: ResultSetDictionary, val variable: AOPVariable, val value: String, child: OPBase) : POPBase() {
    override val operatorID = EOperatorID.POPFilterExactID
    override val classname = "POPFilterExact"
    override val children: Array<OPBase> = arrayOf(child)
    override val resultSet = children[0].resultSet
    override fun equals(other: Any?): Boolean {
        if (other !is POPFilterExact)
            return false
        if (dictionary !== other.dictionary)
            return false
        if (variable != other.variable)
            return false
        if (value != other.value)
            return false
        for (i in children.indices) {
            if (!children[i].equals(other.children[i]))
                return false
        }
        return true
    }

    override fun cloneOP() = POPFilterExact(dictionary, variable, value, children[0].cloneOP())


    override fun getRequiredVariableNames(): MutableList<String> {
        return mutableListOf(variable.name)
    }

    override fun evaluate() = Trace.trace<Unit>({ "POPFilterExact.evaluate" }, {
        val filterVariable: Variable
        val valueR: Value
        valueR = resultSet.createValue(value)
        filterVariable = resultSet.createVariable(variable.name)
        children[0].evaluate()
        CoroutinesHelper.run {
            try {
                for (nextRow in children[0].channel) {
                    resultFlowConsume({ this@POPFilterExact }, { children[0] }, { nextRow })
                    if (nextRow[filterVariable] == valueR)
                        channel.send(resultFlowProduce({ this@POPFilterExact }, { nextRow }))
                }
                channel.close()
                children[0].channel.close()
            } catch (e: Throwable) {
                channel.close(e)
                children[0].channel.close(e)
            }
        }
    })

    override fun toXMLElement() = super.toXMLElement().addAttribute("name", variable.name).addAttribute("value", value)
}
