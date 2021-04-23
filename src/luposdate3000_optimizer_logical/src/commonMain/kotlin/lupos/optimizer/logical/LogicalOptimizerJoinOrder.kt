/*
 * This file is part of the Luposdate3000 distribution (https://github.com/luposdate3000/luposdate3000).
 * Copyright (c) 2020-2021, Institute of Information Systems (Benjamin Warnke and contributors of LUPOSDATE3000), University of Luebeck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package lupos.optimizer.logical

import lupos.operator.arithmetik.noinput.AOPVariable
import lupos.operator.logical.Query
import lupos.operator.logical.multiinput.LOPJoin
import lupos.operator.logical.noinput.OPEmptyRow
import lupos.operator.logical.noinput.OPNothing
import lupos.operator.logical.singleinput.LOPProjection
import lupos.shared.EmptyResultException
import lupos.shared.SanityCheck
import lupos.shared.operator.IOPBase

public class LogicalOptimizerJoinOrder(query: Query) : OptimizerBase(query, EOptimizerIDExt.LogicalOptimizerJoinOrderID, "LogicalOptimizerJoinOrder") {
    private fun findAllJoinsInChildren(node: LOPJoin): List<IOPBase> {
        val res = mutableListOf<IOPBase>()
        for (c in node.getChildren()) {
            if (c is LOPJoin && !c.optional) {
                res.addAll(findAllJoinsInChildren(c))
            } else if (c is LOPProjection) {
                var d = c.getChildren()[0]
                while (d is LOPProjection) {
                    d = d.getChildren()[0]
                }
                if (d is LOPJoin && !d.optional) {
                    res.addAll(findAllJoinsInChildren(d))
                } else {
                    res.add(d)
                }
            } else if (c is OPNothing) {
                // there can not be any result, if_ one of the children does not have any output.
                throw EmptyResultException()
            } else if (c is OPEmptyRow) {
                // skip those unnecessary joins, without any observeable effekt
            } else {
                res.add(c)
            }
        }
        return res
    }

    private fun clusterizeChildren(nodes: List<IOPBase>): List<MutableList<IOPBase>> {
        // put children with same variables into groups, such that those definetly can use Merge-Join as much as possible
        val res = mutableListOf<MutableList<IOPBase>>()
        val variables = mutableListOf<List<String>>()
        loop@ for (node in nodes) {
            val v = node.getProvidedVariableNames()
            if (res.size > 0) {
                for (i in 0 until variables.size) {
                    if (variables[i].size == v.size && variables[i].containsAll(v)) {
                        res[i].add(node)
                        continue@loop
                    }
                }
            }
            res.add(mutableListOf(node))
            variables.add(v)
        }
        return res
    }

    /*suspend*/ private fun applyOptimisation(nodes: List<IOPBase>, root: LOPJoin): IOPBase {
        when {
            nodes.size > 2 -> {
                var result = LogicalOptimizerJoinOrderStore(nodes, root)
                if (result != null) {
                    return result
                }
                result = LogicalOptimizerJoinOrderCostBasedOnHistogram(nodes, root)
                if (result != null) {
                    return result
                }
                result = LogicalOptimizerJoinOrderCostBasedOnVariable(nodes, root)
                if (result != null) {
                    return result
                }
                SanityCheck.checkUnreachable()
            }
            nodes.size == 2 -> {
                val res = LOPJoin(root.query, nodes[0], nodes[1], false)
                res.onlyExistenceRequired = root.onlyExistenceRequired
                return res
            }
            else -> {
                SanityCheck.check { nodes.size == 1 }
                return nodes[0]
            }
        }
/*Coverage Unreachable*/
    }

    override /*suspend*/ fun optimize(node: IOPBase, parent: IOPBase?, onChange: () -> Unit): IOPBase {
        var res: IOPBase = node
        if (node is LOPJoin && !node.optional && (parent !is LOPJoin || parent.optional)) {
            val originalProvided = node.getProvidedVariableNames()
            try {
                val allChilds2 = findAllJoinsInChildren(node)
                if (allChilds2.size > 2) {
                    var result: IOPBase? = null
                    if (result == null && node.onlyExistenceRequired) {
                        // dont not prefer merge join for_ ask-queries, as this makes it harder later, to avoid any materialisation
                        result = LogicalOptimizerJoinOrderStore(allChilds2, node)
                    }
                    if (result == null) {
                        val allChilds3 = clusterizeChildren(allChilds2)
                        val allChilds4 = mutableListOf<IOPBase>()
                        for (child in allChilds3) {
                            allChilds4.add(applyOptimisation(child, node))
                        }
                        result = applyOptimisation(allChilds4, node)
                    }
                    if (result != res) {
                        onChange()
                        if (!originalProvided.containsAll(result.getProvidedVariableNames())) {
                            result = LOPProjection(query, originalProvided.map { AOPVariable(query, it) }.toMutableList(), result)
                        }
                        res = result
                    }
                }
            } catch (e: EmptyResultException) {
                e.printStackTrace()
                res = OPNothing(query, originalProvided)
            }
        }
        return res
    }
}
