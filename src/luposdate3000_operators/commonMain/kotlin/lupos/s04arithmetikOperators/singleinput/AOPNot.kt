package lupos.s04arithmetikOperators.singleinput

import kotlin.jvm.JvmField
import lupos.s00misc.EOperatorID
import lupos.s03resultRepresentation.ResultSetDictionaryExt
import lupos.s03resultRepresentation.ValueBoolean
import lupos.s03resultRepresentation.ValueDefinition
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.IQuery
import lupos.s04logicalOperators.iterator.IteratorBundle

class AOPNot(query: IQuery, @JvmField var child: AOPBase) : AOPBase(query, EOperatorID.AOPNotID, "AOPNot", arrayOf(child)) {
    override fun toSparql() = "!(" + children[0].toSparql() + ")"
    override fun equals(other: Any?) = other is AOPNot && children[0] == other.children[0]
    override fun evaluate(row: IteratorBundle): () -> ValueDefinition {
        if ((children[0] as AOPBase).enforcesBooleanOrError()) {
            val childA = (children[0] as AOPBase).evaluateID(row)
            return {
                val a = childA()
                val res: ValueDefinition = if (a == ResultSetDictionaryExt.errorValue) {
                    ResultSetDictionaryExt.errorValue2
                } else {
                    ValueBoolean(a == ResultSetDictionaryExt.booleanFalseValue)
                }
                /*return*/res
            }
        } else {
            val childA = (children[0] as AOPBase).evaluate(row)
            return {
                val a = childA()
                var res: ValueDefinition = ResultSetDictionaryExt.errorValue2
                try {
                    res = ValueBoolean(!a.toBoolean())
                } catch (e: Throwable) {
                }
                /*return*/res
            }
        }
    }

    override fun enforcesBooleanOrError() = true
    override fun cloneOP(): IOPBase = AOPNot(query, children[0].cloneOP() as AOPBase)
}
