package lupos.s08logicalOptimisation

import kotlin.jvm.JvmField
import lupos.s00misc.Coverage
import lupos.s00misc.EOptimizerID
import lupos.s00misc.ExecuteOptimizer
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.multiinput.*
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04logicalOperators.multiinput.*
import lupos.s04logicalOperators.noinput.*
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.singleinput.*
import lupos.s04logicalOperators.singleinput.modifiers.*
import lupos.s08logicalOptimisation.OptimizerBase

class LogicalOptimizerProjectionDown(query: Query) : OptimizerBase(query, EOptimizerID.LogicalOptimizerProjectionDownID) {
    override val classname = "LogicalOptimizerProjectionDown"
    override fun optimize(node: OPBase, parent: OPBase?, onChange: () -> Unit) = ExecuteOptimizer.invoke({ this }, { node }, {
        var res: OPBase = node
        if (node is LOPMakeBooleanResult) {
            val child = node.children[0]
            if (child !is LOPProjection && child.getProvidedVariableNames().size > 0) {
                node.children[0] = LOPProjection(query, mutableListOf<AOPVariable>(), node.children[0])
                println("a :: ${node.children[0].uuid} ${node.uuid}")
                onChange()
            }
        }
        if (node is LOPProjection) {
            val variables = node.variables.distinct().map { it.name }.toMutableList()
            val child = node.children[0]
            when (child) {
                is LOPUnion -> {
                    child.children[0] = LOPProjection(query, node.variables.map { AOPVariable(query, it.name) }.toMutableList(), child.children[0])
                    child.children[1] = LOPProjection(query, node.variables.map { AOPVariable(query, it.name) }.toMutableList(), child.children[1])
                    println("b :: ${child.children[0].uuid} ${node.uuid} ${node.variables.map { it.name }} ${(child.children[0] as LOPProjection).variables.map { it.name }}")
                    println("c :: ${child.children[1].uuid} ${node.uuid} ${node.variables.map { it.name }} ${(child.children[1] as LOPProjection).variables.map { it.name }}")
                    res = child
                    onChange()
                }
                is LOPProjection -> {
                    val variables2 = mutableListOf<String>()
                    for (variable in variables) {
                        if (child.variables.distinct().map { it.name }.contains(variable)) {
                            variables2.add(variable)
                        }
                    }
                    res = LOPProjection(query, variables2.distinct().map { AOPVariable(query, it) }.toMutableList(), child.children[0])
                    println("d :: ${res.uuid} ${node.uuid} ${child.uuid} ${node.variables.map { it.name }} ${child.variables.map { it.name }} $variables2")
                    onChange()
                }
                is LOPLimit, is LOPOffset, is LOPSubGroup -> {
                    child.children[0] = LOPProjection(query, node.variables.map { AOPVariable(query, it.name) }.toMutableList(), child.children[0])
                    println("e :: ${child.children[0].uuid} ${node.uuid} ${node.variables.map { it.name }} ${(child.children[0] as LOPProjection).variables.map { it.name }}")
                    res = child
                    onChange()
                }
                is LOPFilter -> {
                    if (child.children[0] !is LOPTriple) {
                        if (variables.containsAll(child.getRequiredVariableNames())) {
                            child.children[0] = LOPProjection(query, node.variables.map { AOPVariable(query, it.name) }.toMutableList(), child.children[0])
                            println("f :: ${child.children[0].uuid} ${node.uuid} ${node.variables.map { it.name }} ${(child.children[0] as LOPProjection).variables.map { it.name }}")
                            res = child
                            onChange()
                        } else {
                            variables.addAll(child.getRequiredVariableNames())
                            if (!variables.containsAll(child.children[0].getProvidedVariableNames())) {
                                child.children[0] = LOPProjection(query, variables.distinct().map { AOPVariable(query, it) }.toMutableList(), child.children[0])
                                println("g :: ${child.children[0].uuid} ${node.uuid} ${node.variables.map { it.name }} $variables")
                                res = child
                                onChange()
                            }
                        }
                    }
                }
                is LOPSort -> {
                    if (variables.containsAll(child.getRequiredVariableNames())) {
                        child.children[0] = LOPProjection(query, node.variables.map { AOPVariable(query, it.name) }.toMutableList(), child.children[0])
                        println("h :: ${child.children[0].uuid} ${node.uuid} ${node.variables.map { it.name }} ${(child.children[0] as LOPProjection).variables.map { it.name }}")
                        res = child
                        onChange()
                    } else {
                        variables.addAll(child.getRequiredVariableNames())
                        if (!variables.containsAll(child.getProvidedVariableNames())) {
                            child.children[0] = LOPProjection(query, variables.distinct().map { AOPVariable(query, it) }.toMutableList(), child.children[0])
                            println("i :: ${child.children[0].uuid} ${node.uuid} ${node.variables.map { it.name }} $variables")
                            res = child
                            onChange()
                        }
                    }
                }
                is LOPBind -> {
                    if (variables.contains(child.name.name)) {
                        if (child.children[0] !is LOPProjection) {
                            variables.remove(child.name.name)
                            variables.addAll(child.getRequiredVariableNames())
                            if (!variables.containsAll(child.children[0].getProvidedVariableNames())) {
                                child.children[0] = LOPProjection(query, variables.distinct().map { AOPVariable(query, it) }.toMutableList(), child.children[0])
                                println("k :: ${child.children[0].uuid} ${node.uuid} ${node.variables.map { it.name }} $variables")
                                onChange()
                            }
                        }
                    } else {
                        /*bind of unused variable -> no sideeffects -> useless*/
                        node.children[0] = child.children[0]
                        onChange()
                    }
                }
                is LOPJoin -> {
                    val childA = child.children[0]
                    val childB = child.children[1]
                    val variablesA = childA.getProvidedVariableNames()
                    val variablesB = childB.getProvidedVariableNames()
                    val variablesJ = mutableListOf<String>()
                    for (variable in variablesA) {
                        if (variablesB.contains(variable)) {
                            variablesJ.add(variable)
                        }
                    }
                    variables.addAll(variablesJ)
                    if (!variables.containsAll(variablesA)) {
                        val variables2 = mutableListOf<String>()
                        for (variable in variables.distinct()) {
                            if (variablesA.contains(variable)) {
                                variables2.add(variable)
                            }
                        }
                        child.children[0] = LOPProjection(query, variables2.map { AOPVariable(query, it) }.toMutableList(), childA)
                        println("l :: ${child.children[0].uuid} ${node.uuid} ${node.variables.map { it.name }} $variables2")
                        onChange()
                    }
                    if (!variables.containsAll(variablesB)) {
                        val variables2 = mutableListOf<String>()
                        for (variable in variables.distinct()) {
                            if (variablesB.contains(variable)) {
                                variables2.add(variable)
                            }
                        }
                        child.children[1] = LOPProjection(query, variables2.map { AOPVariable(query, it) }.toMutableList(), childB)
                        println("m :: ${child.children[1].uuid} ${node.uuid} ${node.variables.map { it.name }} ${variables2}")
                        onChange()
                    }
                }
                else -> {
                }
            }
        }
/*return*/res
    })
}
