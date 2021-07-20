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
package lupos.operator.physical.singleinput

import lupos.operator.arithmetik.AOPBase
import lupos.operator.physical.POPBase
import lupos.shared.DictionaryValueHelper
import lupos.shared.DictionaryValueType
import lupos.shared.EOperatorIDExt
import lupos.shared.ESortPriorityExt
import lupos.shared.IQuery
import lupos.shared.Partition
import lupos.shared.SanityCheck
import lupos.shared.inline.ColumnIteratorQueueExt
import lupos.shared.operator.IOPBase
import lupos.shared.operator.iterator.ColumnIterator
import lupos.shared.operator.iterator.ColumnIteratorQueue
import lupos.shared.operator.iterator.IteratorBundle

public class POPFilter public constructor(query: IQuery, projectedVariables: List<String>, filter: AOPBase, child: IOPBase) : POPBase(query, projectedVariables, EOperatorIDExt.POPFilterID, "POPFilter", arrayOf(child, filter), ESortPriorityExt.SAME_AS_CHILD) {
    override fun getPartitionCount(variable: String): Int = children[0].getPartitionCount(variable)
    override fun toSparql(): String {
        val sparql = children[0].toSparql()
        if (sparql.startsWith("{SELECT ")) {
            return "{SELECT * {" + sparql + ". FILTER (" + children[1].toSparql() + ")}}"
        }
        return "{SELECT * {" + sparql + " FILTER (" + children[1].toSparql() + ")}}"
    }

    override fun equals(other: Any?): Boolean = other is POPFilter && children[0] == other.children[0] && children[1] == other.children[1]
    override fun childrenToVerifyCount(): Int = 1
    override fun cloneOP(): IOPBase = POPFilter(query, projectedVariables, children[1].cloneOP() as AOPBase, children[0].cloneOP())
    override fun getProvidedVariableNamesInternal(): List<String> = children[0].getProvidedVariableNames()
    override fun getRequiredVariableNames(): List<String> = children[1].getRequiredVariableNamesRecoursive()
    override /*suspend*/ fun evaluate(parent: Partition): IteratorBundle {
        // TODO not-equal shortcut during evaluation based on integer-ids
        val variables = children[0].getProvidedVariableNames()
        val variablesOut = getProvidedVariableNames()
        val outMap = mutableMapOf<String, ColumnIterator>()
        val localMap = mutableMapOf<String, ColumnIterator>()
        var expression: () -> Boolean = { true }
        val child = children[0].evaluate(parent)
        val res: IteratorBundle
        val columnsIn = Array(variables.size) { child.columns[variables[it]] }
        val columnsOut = mutableListOf<ColumnIteratorQueue>()
        val columnsLocal = mutableListOf<ColumnIteratorQueue>()
        for (i in variables.indices) {
            columnsLocal.add(object : ColumnIteratorQueue() {
                override /*suspend*/ fun close() {
                    __close()
                }

                @Suppress("NOTHING_TO_INLINE")
                /*suspend*/ inline fun __close() {
                    if (label != 0) {
                        ColumnIteratorQueueExt._close(this)
                        for (v in child.columns.values) {
                            v.close()
                        }
                    }
                }

                override /*suspend*/ fun next(): DictionaryValueType {
                    return ColumnIteratorQueueExt.nextHelper(
                        this,
                        {
                            var done = false
                            while (!done) {
                                for (variableIndex2 in variables.indices) {
                                    columnsLocal[variableIndex2].tmp = columnsIn[variableIndex2]!!.next()
                                    // point each iterator to the current value
                                    if (columnsLocal[variableIndex2].tmp == DictionaryValueHelper.nullValue) {
                                        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_operator_physical/src/commonMain/kotlin/lupos/operator/physical/singleinput/POPFilter.kt:86"/*SOURCE_FILE_END*/ }, { variableIndex2 == 0 })
                                        for (v in child.columns.values) {
                                            v.close()
                                        }
                                        for (variableIndex3 in 0 until variables.size) {
                                            ColumnIteratorQueueExt.closeOnEmptyQueue(columnsLocal[variableIndex3])
                                        }
                                        done = true
                                        break
                                    }
                                }
                                if (!done) {
                                    // evaluate
                                    if (expression()) {
                                        // accept/deny row in each iterator
                                        for (variableIndex2 in variablesOut.indices) {
                                            columnsOut[variableIndex2].queue.add(columnsOut[variableIndex2].tmp)
                                        }
                                        done = true
                                    }
                                }
                            }
                        },
                        { __close() }
                    )
                }
            })
        }
        for (variableIndex in variables.indices) {
            if (projectedVariables.contains(variables[variableIndex])) {
                outMap[variables[variableIndex]] = columnsLocal[variableIndex]
            }
            localMap[variables[variableIndex]] = columnsLocal[variableIndex]
        }
        val resLocal: IteratorBundle = if (variables.isNotEmpty()) {
            IteratorBundle(localMap)
        } else {
            IteratorBundle(0)
        }
        for (element in variablesOut) {
            columnsOut.add(resLocal.columns[element]!! as ColumnIteratorQueue)
        }
        expression = (children[1] as AOPBase).evaluateAsBoolean(resLocal)
        if (variablesOut.isEmpty()) {
            if (variables.isEmpty()) {
                res = if (expression()) {
                    child
                } else {
                    object : IteratorBundle(0) {
                        override /*suspend*/ fun hasNext2(): Boolean {
                            return false
                        }
                    }
                }
            } else {
                res = object : IteratorBundle(0) {
                    override /*suspend*/ fun hasNext2Close() {
                        for (v in child.columns.values) {
                            v.close()
                        }
                    }

                    override /*suspend*/ fun hasNext2(): Boolean {
                        var res2 = false
                        var done = false
                        while (!done) {
                            for (variableIndex2 in variables.indices) {
                                columnsLocal[variableIndex2].tmp = columnsIn[variableIndex2]!!.next()
                                // point each iterator to the current value
                                if (columnsLocal[variableIndex2].tmp == DictionaryValueHelper.nullValue) {
                                    for (v in child.columns.values) {
                                        v.close()
                                    }
                                    SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_operator_physical/src/commonMain/kotlin/lupos/operator/physical/singleinput/POPFilter.kt:159"/*SOURCE_FILE_END*/ }, { variableIndex2 == 0 })
                                    for (variableIndex3 in 0 until variables.size) {
                                        ColumnIteratorQueueExt.closeOnEmptyQueue(columnsLocal[variableIndex3])
                                    }
                                    done = true
                                    break
                                }
                            }
                            if (!done) {
                                // evaluate
                                if (expression()) {
                                    // accept/deny row in each iterator
                                    res2 = true
                                }
                            }
                        }
                        return res2
                    }
                }
            }
        } else {
            res = IteratorBundle(outMap)
        }
        return res
    }
}