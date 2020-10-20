package lupos.s04arithmetikOperators


import lupos.s00misc.EOperatorID
import lupos.s00misc.ESortPriority
import lupos.s00misc.EvaluationException
import lupos.s00misc.SanityCheck
import lupos.s03resultRepresentation.ResultSetDictionaryExt

import lupos.s03resultRepresentation.ValueDefinition
import lupos.s04logicalOperators.HistogramResult
import lupos.s04logicalOperators.iterator.IteratorBundle
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query

abstract class AOPBase(query: Query,
                       operatorID: EOperatorID,
                       classname: String,
                       children: Array<OPBase>) :
        OPBase(query, operatorID, classname, children, ESortPriority.PREVENT_ANY) ,IAOPBase{
    fun evaluateAsBoolean(row: IteratorBundle): () -> Boolean {
        if (enforcesBooleanOrError()) {
            val tmp = evaluateID(row)
            return {
                /*return*/tmp() == ResultSetDictionaryExt.booleanTrueValue
            }

        } else {
            val tmp = evaluate(row)
            return {
                var res: Boolean
                try {
                    val value = tmp()
                    res = value.toBoolean()
                } catch (e: EvaluationException) {
                    res = false
                } catch (e: Throwable) {
                    res = false
                    SanityCheck.println({ "TODO exception 48" })
                    e.printStackTrace()
                }
/*return*/res
            }
        }
    }

    open fun evaluate(row: IteratorBundle): () -> ValueDefinition {
        return {
            /*return*/query.dictionary.getValue(evaluateID(row)())
        }

    }

    open fun evaluateID(row: IteratorBundle): () -> Int {
        return {
            /*return*/query.dictionary.createValue(evaluate(row)())
        }

    }

    open fun enforcesBooleanOrError() = false
    override fun getPartitionCount(variable: String): Int = SanityCheck.checkUnreachable()
    suspend override fun calculateHistogram(): HistogramResult = SanityCheck.checkUnreachable()
}
