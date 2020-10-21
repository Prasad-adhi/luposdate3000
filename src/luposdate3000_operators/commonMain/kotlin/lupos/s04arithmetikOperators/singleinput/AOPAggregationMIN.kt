package lupos.s04arithmetikOperators.singleinput

import kotlin.jvm.JvmField

import lupos.s00misc.EOperatorID
import lupos.s00misc.EvaluationException
import lupos.s00misc.SanityCheck
import lupos.s00misc.XMLElement


import lupos.s03resultRepresentation.ValueDefinition
import lupos.s03resultRepresentation.ValueError
import lupos.s03resultRepresentation.ValueUndef
import lupos.s04arithmetikOperators.AOPAggregationBase
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04logicalOperators.iterator.ColumnIterator
import lupos.s04logicalOperators.iterator.ColumnIteratorAggregate
import lupos.s04logicalOperators.iterator.IteratorBundle
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query

class AOPAggregationMIN(query: Query, @JvmField val distinct: Boolean, childs: Array<AOPBase>) : AOPAggregationBase(query, EOperatorID.AOPAggregationMINID, "AOPAggregationMIN", Array(childs.size) { childs[it] }) {
    override suspend fun toXMLElement() = super.toXMLElement().addAttribute("distinct", "" + distinct)
    override fun toSparql(): String {
        if (distinct) {
            return "MIN(DISTINCT " + children[0].toSparql() + ")"
        }
        return "MIN(" + children[0].toSparql() + ")"
    }

    override fun equals(other: Any?) = other is AOPAggregationMIN && distinct == other.distinct && children.contentEquals(other.children)
    override fun createIterator(row: IteratorBundle): ColumnIteratorAggregate {
        val res = ColumnIteratorAggregate()
        val child = (children[0] as AOPBase).evaluate(row)
        res.evaluate = {
            val value = child()
            try {
                if (res.value is ValueUndef || res.value.compareTo(value) > 0) {
                    res.value = value
                }
            } catch (e: EvaluationException) {
                res.value = ValueError()
                res.evaluate = res::_evaluate
            } catch (e: Throwable) {
                SanityCheck.println({ "TODO exception 38" })
                e.printStackTrace()
                res.value = ValueError()
                res.evaluate = res::_evaluate
            }
        }
        return res
    }

    override fun evaluate(row: IteratorBundle): () -> ValueDefinition {
        val tmp = row.columns["#" + uuid]!! as ColumnIteratorAggregate
        return {
            var res: ValueDefinition = tmp.value
            /*return*/res
        }

    }

    override fun cloneOP() :IOPBase= AOPAggregationMIN(query, distinct, Array(children.size) { (children[it].cloneOP()) as AOPBase })
}
