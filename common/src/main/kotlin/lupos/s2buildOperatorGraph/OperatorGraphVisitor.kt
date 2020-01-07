package lupos.s2buildOperatorGraph

import lupos.s1buildSyntaxTree.sparql1_1.*
import lupos.s2buildOperatorGraph.data.LOPExpression
import lupos.s2buildOperatorGraph.data.LOPTriple
import lupos.s2buildOperatorGraph.data.LOPValues
import lupos.s2buildOperatorGraph.data.LOPVariable
import lupos.s2buildOperatorGraph.multiinput.LOPJoin
import lupos.s2buildOperatorGraph.multiinput.LOPMinus
import lupos.s2buildOperatorGraph.multiinput.LOPUnion
import lupos.s2buildOperatorGraph.singleinput.*
import lupos.s2buildOperatorGraph.singleinput.modifiers.*

class OperatorGraphVisitor : Visitor<OPBase> {
    override fun visit(node: ASTNode, childrenValues: List<OPBase>): OPBase = LOPNOOP()

    override fun visit(node: ASTSelectQuery, childrenValues: List<OPBase>): OPBase {
        val result = LOPNOOP()
        if (!node.selectAll()) {
            val projection = LOPProjection()
            result.getLatestChild().setChild(projection)
            for (sel in node.select) {
                when (sel) {
                    is ASTVar -> {
                        projection.variables.add(LOPVariable(sel.name))
                    }
                    is ASTAs -> {
                        val v = LOPVariable(sel.variable.name)
                        projection.variables.add(v)
                        result.getLatestChild().setChild(LOPBind(v, sel.expression.visit(this)))
                    }
                    else -> {
                        throw UnsupportedOperationException("UnsupportedOperationException Select-Parameter ${node::class.simpleName}")
                    }
                }
            }
        }
        if (node.existsLimit()) {
            result.getLatestChild().setChild(LOPLimit(node.limit))
        }
        if (node.existsOffset()) {
            result.getLatestChild().setChild(LOPOffset(node.offset))
        }
        if (node.distinct) {
            result.getLatestChild().setChild(LOPDistinct())
        }
        if (node.reduced) {
            result.getLatestChild().setChild(LOPReduced())
        }
        if (node.existsOrderBy()) {
            for (order in node.orderBy) {
                result.getLatestChild().setChild(order.visit(this) as LOPSort)
            }
        }
        if (node.existsHaving()) {
            for (h in node.having) {
                result.getLatestChild().setChild(LOPFilter(h.visit(this) as LOPExpression))
            }
        }
        if (node.existsGroupBy()) {
            for (b in node.groupBy) {
                when (b) {
                    is ASTVar -> {
                        result.getLatestChild().setChild(LOPGroup(b.visit(this) as LOPVariable))
                    }
                    is ASTAs -> {
                        val v = LOPVariable(b.variable.name)
                        result.getLatestChild().setChild(LOPGroup(v))
                        result.getLatestChild().setChild(LOPBind(v, b.expression.visit(this)))
                    }
                    else -> {
                        throw UnsupportedOperationException("UnsupportedOperationException Group-Parameter ${node::class.simpleName}")
                    }
                }
            }
        }
        if (node.where.isNotEmpty()) {
            result.getLatestChild().setChild(parseGroup(node.where))
        }
        if (node.existsDatasets()) {
            // var datasets: Array<ASTDatasetClause> = arrayOf<ASTDatasetClause>();
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
        return result.child
    }

    private fun parseGroup(nodes: Array<ASTNode>): OPBase {
        if (nodes.isEmpty()) {
            return LOPNOOP()
        }
        var result: OPBase? = null
        var tJoin: OPBase? = null
        var tMinus: LOPSingleInputBase? = null
        var tFilter: LOPSingleInputBase? = null
        for (n in nodes) {
            val tmp2 = n.visit(this)
            if (tmp2 is LOPFilter) {
                if (tFilter == null) {
                    tFilter = tmp2
                } else
                    tFilter.getLatestChild().setChild(tmp2)
            } else if (tmp2 is LOPMinus) {
                if (tMinus == null) {
                    tMinus = tmp2
                } else {
                    tMinus.getLatestChild().setChild(tmp2)
                }
            } else {
                val j = tJoin
                if (j == null) {
                    tJoin = tmp2
                } else {
                    tJoin = LOPJoin(j, tmp2, false)
                }
            }
        }
        if (tMinus != null) {
            result = tMinus
        }
        if (tFilter != null) {
            if (result == null)
                result = tFilter
            else
                (result as LOPSingleInputBase).getLatestChild().setChild(tFilter)
        }
        if (tJoin != null) {
            if (result == null)
                result = tJoin
            else
                (result as LOPSingleInputBase).getLatestChild().setChild(tJoin)
        }
        return result!!
    }

    override fun visit(node: ASTQuery, childrenValues: List<OPBase>): OPBase {
        if (childrenValues.isEmpty())
            return LOPNOOP() //empty query
        var query: OPBase = LOPNOOP()
        var prefix: LOPPrefix? = null
        var values: OPBase? = null
        for (q in childrenValues) {
            if (q is LOPPrefix) {
                if (prefix == null)
                    prefix = q
                else
                    prefix.getLatestChild().setChild(q)
            } else if (q is LOPValues) {
                if (values == null) {
                    values = q
                } else {
                    values = LOPJoin(values, q, false)
                }
            } else {
                query = q
            }
        }
        if (query is LOPNOOP)
            return LOPNOOP()
        if (values != null && prefix != null) {
            prefix.getLatestChild().setChild(joinValuesAndQuery(values, query))
            return prefix
        } else if (values != null) {
            return joinValuesAndQuery(values, query)
        } else if (prefix != null) {
            prefix.getLatestChild().setChild(query)
            return prefix
        }
        return LOPNOOP()
    }

    private fun joinValuesAndQuery(values: OPBase, query: OPBase): OPBase {
        if (query !is LOPProjection)
            return LOPJoin(values, query, false)
        var latestProjection = query
        var realQuery = query
        while (realQuery is LOPProjection) {
            latestProjection = realQuery
            realQuery = realQuery.child
        }
        (latestProjection as LOPProjection).setChild(LOPJoin(values, realQuery, false))
        return query
    }

    override fun visit(node: ASTUndef, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTSimpleLiteral, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTTypedLiteral, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTLanguageTaggedLiteral, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTBooleanLiteral, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTNumericLiteral, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTInteger, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTDouble, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTDecimal, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTFunctionCall, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTTriple, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.size == 3)
        return LOPTriple(childrenValues[0], childrenValues[1], childrenValues[2])
    }

    override fun visit(node: ASTOptional, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.size == 1)
        return LOPOptional(childrenValues.first())
    }

    override fun visit(node: ASTSet, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTOr, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTAnd, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTEQ, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTNEQ, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTLEQ, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTGEQ, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTLT, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTGT, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTIn, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTNotIn, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTAddition, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTSubtraction, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTMultiplication, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTDivision, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTNot, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTBase, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.isEmpty())
        return LOPPrefix("", node.iri)
    }

    override fun visit(node: ASTPrefix, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.isEmpty())
        return LOPPrefix(node.name, node.iri)
    }

    override fun visit(node: ASTAskQuery, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.isEmpty())
        return LOPMakeBooleanResult()
    }

    override fun visit(node: ASTAs, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.isEmpty())
        return LOPBind(node.variable.visit(this), node.expression.visit(this))
    }

    override fun visit(node: ASTBuiltInCall, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTAggregation, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTMinusGroup, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.isNotEmpty())
        return LOPMinus(LOPNOOP(), parseGroup(node.children))
    }

    override fun visit(node: ASTUnion, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.size == 2)
        return LOPUnion(childrenValues[0], childrenValues[1])
    }

    override fun visit(node: ASTFilter, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.size == 1)
        return LOPFilter(childrenValues.first() as LOPExpression)
    }

    override fun visit(node: ASTOrderCondition, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.size == 1)
        return LOPSort(node.asc, childrenValues.first())
    }

    override fun visit(node: ASTVar, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.isEmpty())
        return LOPVariable(node.name)
    }

    override fun visit(node: ASTLiteral, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.isEmpty())
        return LOPExpression(node)
    }

    override fun visit(node: ASTIri, childrenValues: List<OPBase>): OPBase {
        require(childrenValues.isEmpty())
        return LOPExpression(node)
    }

    override fun visit(node: ASTGroup, childrenValues: List<OPBase>): OPBase {
        return parseGroup(node.children)
    }

    override fun visit(node: ASTValues, childrenValues: List<OPBase>): OPBase {
        if (node.variables.isEmpty())
            return LOPNOOP()
        val variables = mutableListOf<LOPVariable>()
        val values = mutableListOf<LOPExpression>()
        for (v in node.variables) {
            variables.add(v.visit(this) as LOPVariable)
        }
        for (v in node.children) {
            values.add(LOPExpression(v))
        }
        return LOPValues(variables, values)
    }

    override fun visit(node: ASTValue, childrenValues: List<OPBase>): OPBase {
        return LOPExpression(node)
    }

    override fun visit(node: ASTAdd, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTMove, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTCopy, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTGraph, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTDefaultGraph, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTNamedGraph, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTGraphRef, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTIriGraphRef, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTNamedIriGraphRef, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTDefaultGraphRef, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTNamedGraphRef, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTAllGraphRef, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTGrapOperation, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTUpdateGrapOperation, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTClear, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTLoad, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTDrop, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTCreate, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Graph ${node::class.simpleName}")
    }

    override fun visit(node: ASTModify, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Update ${node::class.simpleName}")
    }

    override fun visit(node: ASTDeleteData, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Update ${node::class.simpleName}")
    }

    override fun visit(node: ASTDeleteWhere, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Update ${node::class.simpleName}")
    }

    override fun visit(node: ASTInsertData, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Update ${node::class.simpleName}")
    }

    override fun visit(node: ASTModifyWithWhere, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Update ${node::class.simpleName}")
    }

    override fun visit(node: ASTPathAlternatives, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Path ${node::class.simpleName}")
    }

    override fun visit(node: ASTPathSequence, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Path ${node::class.simpleName}")
    }

    override fun visit(node: ASTPathInverse, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Path ${node::class.simpleName}")
    }

    override fun visit(node: ASTPathArbitraryOccurrences, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Path ${node::class.simpleName}")
    }

    override fun visit(node: ASTPathOptionalOccurrence, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Path ${node::class.simpleName}")
    }

    override fun visit(node: ASTPathArbitraryOccurrencesNotZero, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Path ${node::class.simpleName}")
    }

    override fun visit(node: ASTPathNegatedPropertySet, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Path ${node::class.simpleName}")
    }

    override fun visit(node: ASTGroupConcat, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Group ${node::class.simpleName}")
    }

    override fun visit(node: ASTBlankNode, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Blank Node ${node::class.simpleName}")
    }

    override fun visit(node: ASTSubSelectQuery, childrenValues: List<OPBase>): OPBase {
        if (node.existsValues()) {
            throw UnsupportedOperationException("UnsupportedOperationException Values ${node::class.simpleName}")
        }
        return visit(node as ASTSelectQuery, childrenValues)
    }

    override fun visit(node: ASTConstructQuery, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Query Type ${node::class.simpleName}")
    }

    override fun visit(node: ASTDescribeQuery, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Query Type ${node::class.simpleName}")
    }

    override fun visit(node: ASTDatasetClause, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Query Type ${node::class.simpleName}")
    }

    override fun visit(node: ASTService, childrenValues: List<OPBase>): OPBase {
        throw UnsupportedOperationException("UnsupportedOperationException Service ${node::class.simpleName}")
    }

    override fun visit(node: ASTQueryBaseClass, childrenValues: List<OPBase>): OPBase {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visit(node: ASTRDFTerm, childrenValues: List<OPBase>): OPBase {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visit(node: ASTPlus, childrenValues: List<OPBase>): OPBase {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visit(node: ASTMinus, childrenValues: List<OPBase>): OPBase {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
