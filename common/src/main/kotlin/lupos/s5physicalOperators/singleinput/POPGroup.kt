package lupos.s5physicalOperators.singleinput

import lupos.s2buildOperatorGraph.data.LOPVariable
import lupos.s2buildOperatorGraph.data.LOPExpression
import lupos.s2buildOperatorGraph.singleinput.LOPBind
import lupos.s2buildOperatorGraph.OPBase
import lupos.s4resultRepresentation.ResultRow
import lupos.s4resultRepresentation.ResultSet
import lupos.s4resultRepresentation.Variable
import lupos.s4resultRepresentation.Value
import lupos.s5physicalOperators.singleinput.POPSingleInputBaseNullableIterator
import lupos.s5physicalOperators.POPBaseNullableIterator
import lupos.s5physicalOperators.POPBase
import lupos.s5physicalOperators.POPExpression

class POPGroup : POPSingleInputBaseNullableIterator {
    private var data: MutableList<ResultRow>? = null
    private val resultSetOld: ResultSet
    private val resultSetNew = ResultSet()
    private var iterator: Iterator<ResultRow>? = null
    var by: List<LOPVariable>
    var bindings = mutableListOf<Pair<Variable, POPExpression>>()

    constructor(by: List<LOPVariable>, bindings: POPBind?, child: POPBase) : super(child) {
        this.by = by
        resultSetOld = child.getResultSet()
        var tmpBind: POPBase? = bindings
        while (tmpBind != null && tmpBind is POPBind) {
            this.bindings.add(Pair(resultSetNew.createVariable((tmpBind.name as LOPVariable).name), tmpBind.expression as POPExpression))
            resultSetNew.createVariable((tmpBind.name as LOPVariable).name)
            tmpBind = tmpBind.child
        }
        this.bindings = this.bindings.asReversed()
        for (v in by) {
            resultSetNew.createVariable(v.name)
        }
    }

    override fun getResultSet(): ResultSet {
        return resultSetNew
    }

    override fun nnext(): ResultRow? {
        if (data == null) {
            val tmpMutableMap = mutableMapOf<String, MutableList<ResultRow>>()
            val variables = mutableListOf<Pair<Variable, Variable>>()
            for (v in by) {
                variables.add(Pair(resultSetNew.createVariable(v.name), resultSetOld.createVariable(v.name)))
            }
            while (child.hasNext()) {
                val rsOld = child.next()
                var key: String = "|"
                for (variable in variables)
                    key = key + resultSetOld.getValue(rsOld[variable.second]) + "|"
                var tmp = tmpMutableMap[key]
                if (tmp == null) {
                    tmp = mutableListOf<ResultRow>()
                    tmpMutableMap[key] = tmp
                }
                tmp!!.add(rsOld)
            }
            data = mutableListOf<ResultRow>()
            for (k in tmpMutableMap.keys) {
                val rsOld = tmpMutableMap[k]!!.first()!!
                val rsNew = resultSetNew.createResultRow()
                for (variable in variables) {
                    rsNew[variable.first] = resultSetNew.createValue(resultSetOld.getValue(rsOld[variable.second]))
                }
                for (b in bindings) {
                    rsNew[b.first] = resultSetNew.createValue(b.second.evaluate(resultSetOld, tmpMutableMap[k]!!))
                }
                data!!.add(rsNew)
            }
            reset()
        }
        if (iterator == null || !iterator!!.hasNext())
            return null
        return iterator!!.next()
    }

    fun reset() {
        iterator = data!!.listIterator()
    }

    override fun toString(indentation: String): String {
        return "${indentation}${this::class.simpleName}\n${indentation}\tchild:\n${child.toString("${indentation}\t\t")}"
    }
}
