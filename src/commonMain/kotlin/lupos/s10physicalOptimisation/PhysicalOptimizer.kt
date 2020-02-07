package lupos.s10physicalOptimisation

import lupos.s00misc.classNameToString
import lupos.s02buildSyntaxTree.sparql1_1.ASTInteger
import lupos.s02buildSyntaxTree.sparql1_1.ASTIri
import lupos.s02buildSyntaxTree.sparql1_1.ASTLanguageTaggedLiteral
import lupos.s02buildSyntaxTree.sparql1_1.ASTSimpleLiteral
import lupos.s02buildSyntaxTree.sparql1_1.ASTTypedLiteral
import lupos.s03resultRepresentation.*
import lupos.s04logicalOperators.multiinput.LOPJoin
import lupos.s04logicalOperators.multiinput.LOPUnion
import lupos.s04logicalOperators.noinput.*
import lupos.s04logicalOperators.noinput.LOPExpression
import lupos.s04logicalOperators.noinput.LOPGraphOperation
import lupos.s04logicalOperators.noinput.LOPValues
import lupos.s04logicalOperators.noinput.LOPVariable
import lupos.s04logicalOperators.noinput.OPNothing
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.singleinput.*
import lupos.s04logicalOperators.singleinput.LOPBind
import lupos.s04logicalOperators.singleinput.LOPFilter
import lupos.s04logicalOperators.singleinput.LOPMakeBooleanResult
import lupos.s04logicalOperators.singleinput.LOPProjection
import lupos.s04logicalOperators.singleinput.LOPRename
import lupos.s04logicalOperators.singleinput.LOPSort
import lupos.s04logicalOperators.singleinput.LOPSubGroup
import lupos.s04logicalOperators.singleinput.modifiers.LOPDistinct
import lupos.s04logicalOperators.singleinput.modifiers.LOPLimit
import lupos.s04logicalOperators.singleinput.modifiers.LOPOffset
import lupos.s05tripleStore.globalStore
import lupos.s09physicalOperators.multiinput.POPJoinHashMap
import lupos.s09physicalOperators.multiinput.POPUnion
import lupos.s09physicalOperators.noinput.POPEmptyRow
import lupos.s09physicalOperators.noinput.POPExpression
import lupos.s09physicalOperators.noinput.POPGraphOperation
import lupos.s09physicalOperators.noinput.POPModifyData
import lupos.s09physicalOperators.noinput.POPValues
import lupos.s09physicalOperators.POPBase
import lupos.s09physicalOperators.singleinput.modifiers.POPDistinct
import lupos.s09physicalOperators.singleinput.modifiers.POPLimit
import lupos.s09physicalOperators.singleinput.modifiers.POPOffset
import lupos.s09physicalOperators.singleinput.POPBind
import lupos.s09physicalOperators.singleinput.POPBindUndefined
import lupos.s09physicalOperators.singleinput.POPFilter
import lupos.s09physicalOperators.singleinput.POPFilterExact
import lupos.s09physicalOperators.singleinput.POPGroup
import lupos.s09physicalOperators.singleinput.POPMakeBooleanResult
import lupos.s09physicalOperators.singleinput.POPModify
import lupos.s09physicalOperators.singleinput.POPProjection
import lupos.s09physicalOperators.singleinput.POPRename
import lupos.s09physicalOperators.singleinput.POPSort
import lupos.s10physicalOptimisation.OptimizerVisitorPOP


class PhysicalOptimizer(transactionID: Long, dictionary: ResultSetDictionary) : OptimizerVisitorPOP(transactionID, dictionary) {


    override fun visit(node: LOPGraphOperation): OPBase {
        val s = store
        if (s == null)
            return POPGraphOperation(dictionary, transactionID, node.silent, node.graphref1!!, node.graphref2, node.action, globalStore)
        else
            return POPGraphOperation(dictionary, transactionID, node.silent, node.graphref1!!, node.graphref2, node.action, s)
    }

    override fun visit(node: LOPModify): OPBase {
        val s = store
        if (s == null)
            return POPModify(dictionary, transactionID, node.iri, node.insert, node.delete, globalStore, optimize(node.children[0]))
        return POPModify(dictionary, transactionID, node.iri, node.insert, node.delete, s, optimize(node.children[0]))
    }

    override fun visit(node: LOPModifyData): OPBase {
        val s = store
        if (s == null)
            return POPModifyData(dictionary, transactionID, node.type, node.data, globalStore)
        return POPModifyData(dictionary, transactionID, node.type, node.data, s)
    }

    override fun visit(node: LOPProjection): OPBase {
        return POPProjection(dictionary, node.variables, optimize(node.children[0]))
    }

    override fun visit(node: LOPMakeBooleanResult): OPBase {
        return POPMakeBooleanResult(dictionary, optimize(node.children[0]))
    }

    override fun visit(node: LOPRename): OPBase {
        return POPRename(dictionary, node.nameTo, node.nameFrom, optimize(node.children[0]))
    }

    override fun visit(node: LOPValues): OPBase {
        return POPValues(dictionary, node)
    }

    override fun visit(node: LOPLimit): OPBase {
        return POPLimit(dictionary, node.limit, optimize(node.children[0]))
    }

    override fun visit(node: LOPDistinct): OPBase {
        return POPDistinct(dictionary, optimize(node.children[0]))
    }

    override fun visit(node: LOPOffset): OPBase {
        return POPOffset(dictionary, node.offset, optimize(node.children[0]))
    }

    override fun visit(node: LOPGroup): OPBase {
        if (node.bindings != null)
            return POPGroup(dictionary, node.by, optimize(node.bindings!!) as POPBind, optimize(node.children[0]))
        return POPGroup(dictionary, node.by, null, optimize(node.children[0]))
    }

    override fun visit(node: LOPUnion): OPBase {
        return POPUnion(dictionary, optimize(node.children[0]), optimize(node.children[1]))
    }

    override fun visit(node: LOPExpression): OPBase {
        return POPExpression(dictionary, node.child)
    }

    override fun visit(node: LOPSort): OPBase {
        if (node.by is LOPVariable)
            return POPSort(dictionary, node.by as LOPVariable, node.asc, optimize(node.children[0]))
        else if (node.by is LOPExpression) {
            val v = LOPVariable("#" + node.uuid)
            return POPSort(dictionary, v, node.asc, POPBind(dictionary, v, optimize(node.by) as POPExpression, optimize(node.children[0])))
        } else
            throw UnsupportedOperationException("${classNameToString(this)} ${classNameToString(node)}, ${classNameToString(node.by)}")
    }

    override fun visit(node: LOPSubGroup): OPBase {
        return optimize(node.children[0])
    }

    override fun visit(node: LOPFilter): OPBase {
        return POPFilter(dictionary, optimize(node.filter) as POPExpression, optimize(node.children[0]))
    }

    override fun visit(node: LOPBind): OPBase {
        val variable = optimize(node.name) as LOPVariable
        val child = optimize(node.children[0])
        when (node.expression) {
            is LOPVariable ->
                if (child.getResultSet().getVariableNames().contains(variable.name))
                    return POPRename(dictionary, variable, node.expression, child)
                else
                    return POPBindUndefined(dictionary, variable, child)
            else ->
                return POPBind(dictionary, variable, optimize(node.expression) as POPExpression, child)
        }
    }

    override fun visit(node: LOPJoin): OPBase {
//        return POPJoinNestedLoop(dictionary,optimize(node.children[0]), optimize(node.children[1]), node.optional)
        return POPJoinHashMap(dictionary, optimize(node.children[0]), optimize(node.children[1]), node.optional)
    }

inline    fun optimizeTriple(param: OPBase, name: String, child: POPBase, node: LOPTriple): POPBase {
        when (param) {
            is LOPVariable -> {
                if (param.name != name)
                    return POPRename(dictionary, param, LOPVariable(name), child)
            }
            is LOPExpression -> {
                when (param.child) {
                    is ASTInteger -> return POPFilterExact(dictionary, LOPVariable(name), "\"" + param.child.value + "\"^^<http://www.w3.org/2001/XMLSchema#integer>", child)
                    is ASTIri -> return POPFilterExact(dictionary, LOPVariable(name), "<" + param.child.iri + ">", child)
                    is ASTLanguageTaggedLiteral -> return POPFilterExact(dictionary, LOPVariable(name), param.child.delimiter + param.child.content + param.child.delimiter + "@" + param.child.language, child)
                    is ASTTypedLiteral -> return POPFilterExact(dictionary, LOPVariable(name), param.child.delimiter + param.child.content + param.child.delimiter + "^^<" + param.child.type_iri + ">", child)
                    is ASTSimpleLiteral -> return POPFilterExact(dictionary, LOPVariable(name), param.child.delimiter + param.child.content + param.child.delimiter, child)
                    else -> throw UnsupportedOperationException("${classNameToString(this)} ${classNameToString(node)}, ${classNameToString(param.child)}")
                }
            }
        }
        return child
    }

    override fun visit(node: LOPTriple): OPBase {
        val variables = mutableListOf<LOPVariable>()
        if (node.s is LOPVariable)
            variables.add(node.s)
        if (node.p is LOPVariable)
            variables.add(node.p)
        if (node.o is LOPVariable)
            variables.add(node.o)
        var result2 = if (store == null)
            globalStore.getNamedGraph(node.graph).getIterator(dictionary)
        else
            store!!.getNamedGraph(node.graph).getIterator(dictionary)
        var sname = result2.nameS
        var pname = result2.nameP
        var oname = result2.nameO
        var result: POPBase = result2
        result = optimizeTriple(node.s, sname, result, node)
        result = optimizeTriple(node.p, pname, result, node)
        result = optimizeTriple(node.o, oname, result, node)
        if (variables.size < 3) {
            result = POPProjection(dictionary, variables, result)
        }
        return result
    }

    override fun visit(node: OPNothing): OPBase {
        return POPEmptyRow(dictionary)
    }
}
