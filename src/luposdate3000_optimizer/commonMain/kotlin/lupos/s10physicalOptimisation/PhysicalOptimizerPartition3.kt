package lupos.s10physicalOptimisation

import lupos.s00misc.DontCareWhichException
import lupos.s00misc.EOptimizerID
import lupos.s00misc.Partition
import lupos.s00misc.SanityCheck
import lupos.s00misc.TripleStoreLocal
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.IAOPVariable
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s05tripleStore.TripleStoreFeature
import lupos.s05tripleStore.TripleStoreFeatureParamsPartition
import lupos.s08logicalOptimisation.OptimizerBase
import lupos.s09physicalOperators.multiinput.POPUnion
import lupos.s09physicalOperators.partition.POPChangePartitionOrderedByIntId
import lupos.s09physicalOperators.partition.POPMergePartition
import lupos.s09physicalOperators.partition.POPMergePartitionCount
import lupos.s09physicalOperators.partition.POPMergePartitionOrderedByIntId
import lupos.s09physicalOperators.partition.POPSplitPartition
import lupos.s09physicalOperators.partition.POPSplitPartitionFromStore
import lupos.s09physicalOperators.singleinput.modifiers.POPReduced
import lupos.s09physicalOperators.singleinput.POPBind
import lupos.s09physicalOperators.singleinput.POPFilter
import lupos.s09physicalOperators.singleinput.POPProjection
import lupos.s15tripleStoreDistributed.distributedTripleStore
import lupos.s15tripleStoreDistributed.TripleStoreIteratorGlobal

class PhysicalOptimizerPartition3(query: Query) : OptimizerBase(query, EOptimizerID.PhysicalOptimizerPartition3ID) {
    override val classname = "PhysicalOptimizerPartition3"
    override suspend fun optimize(node: IOPBase, parent: IOPBase?, onChange: () -> Unit): IOPBase {
        var res = node
        when (node) {
            is POPSplitPartitionFromStore -> {
                var storeNodeTmp = node.children[0]
                while (storeNodeTmp !is TripleStoreIteratorGlobal) {
//this is POPDebug or something similar with is not affecting the calculation - otherwise this node wont be POPSplitPartitionFromStore
                    storeNodeTmp = storeNodeTmp.getChildren()[0]
                }
                SanityCheck.check { storeNodeTmp is TripleStoreIteratorGlobal }
                val storeNode = storeNodeTmp as TripleStoreIteratorGlobal
                val idx = storeNode.idx
                var partitionColumn = 0
                for (ii in 0 until 3) {
                    val i = idx.tripleIndicees[ii]
                    val param = storeNode.children[i]
                    if (param is IAOPVariable) {
                        if (param.getName() == node.partitionVariable) {
                            break
                        } else {
                            partitionColumn++
                        }
                    } else {
                        partitionColumn++ //constants at the front do count
                    }
                }
                SanityCheck.check({ partitionColumn <= 2 && partitionColumn >= 1 }, { "$partitionColumn ${node.partitionVariable} $idx ${idx.tripleIndicees.map { it }} ${storeNode.children.map { "${(it as OPBase).classname} ${(it as? IAOPVariable)?.getName()}" }}" })
                var count = -1
                val partitions = distributedTripleStore.getLocalStore().getDefaultGraph(query).getEnabledPartitions()
                for (p in partitions) {
                    if (p.index.contains(idx) && p.column == partitionColumn) {
                        println("${p.partitionCount}")
                        if (count == -1 || (p.partitionCount >= count && p.partitionCount <= node.partitionCount)) {
                            count = p.partitionCount
                        }
                    }
                }
//SanityCheck failed :: -1 0 P_SO 1
                SanityCheck.check({ count != -1 }, { "$count $partitionColumn $idx ${node.partitionCount}" })
                if (count != node.partitionCount) {
                    var newID = query.getNextPartitionOperatorID()
                    query.removePartitionOperator(node.getUUID(), node.partitionID)
                    res = POPChangePartitionOrderedByIntId(query, node.projectedVariables, node.partitionVariable, count, node.partitionCount, newID, node.partitionID, node)
                    node.partitionID = newID
                    node.partitionCount = count
                    query.addPartitionOperator(node.getUUID(), node.partitionID)
                    query.addPartitionOperator(res.getUUID(), res.partitionIDTo)
                    query.addPartitionOperator(res.getUUID(), res.partitionIDFrom)
                    println("change ${res.getUUID()} ${node.getUUID()} X")
                    onChange()
                }
            }
            is POPBind -> {
                val c = node.children[0]
                if (c is POPMergePartition) {
                    res = POPMergePartition(query, node.projectedVariables, c.partitionVariable, c.partitionCount, c.partitionID, POPBind(query, node.projectedVariables, node.name, node.children[1] as AOPBase, c.children[0]))
                    query.removePartitionOperator(c.getUUID(), c.partitionID)
                    query.addPartitionOperator(res.getUUID(), c.partitionID)
                    query.partitionOperatorCount.clear()
                    println("change ${c.getUUID()} A")
                    onChange()
                } else if (c is POPMergePartitionOrderedByIntId) {
                    res = POPMergePartitionOrderedByIntId(query, node.projectedVariables, c.partitionVariable, c.partitionCount, c.partitionID, POPBind(query, node.projectedVariables, node.name, node.children[1] as AOPBase, c.children[0]))
                    query.removePartitionOperator(c.getUUID(), c.partitionID)
                    query.addPartitionOperator(res.getUUID(), c.partitionID)
                    query.partitionOperatorCount.clear()
                    println("change ${c.getUUID()} B")
                    onChange()
                } else if (c is POPMergePartitionCount) {
                    res = POPMergePartitionCount(query, node.projectedVariables, c.partitionVariable, c.partitionCount, c.partitionID, POPBind(query, node.projectedVariables, node.name, node.children[1] as AOPBase, c.children[0]))
                    query.removePartitionOperator(c.getUUID(), c.partitionID)
                    query.addPartitionOperator(res.getUUID(), c.partitionID)
                    query.partitionOperatorCount.clear()
                    println("change ${c.getUUID()} C")
                    onChange()
                }
            }
            is POPUnion -> {
                val c0 = node.children[0]
                val c1 = node.children[1]
                var modeC0 = 0
                var modeC1 = 0
                var columnNameC0 = ""
                var columnCountC0 = 0
                var columnIDC0 = 0
                var columnNameC1 = ""
                var columnCountC1 = 0
                var columnIDC1 = 0
                if (c0 is POPMergePartition) {
                    modeC0 = 1
                    columnNameC0 = c0.partitionVariable
                    columnCountC0 = c0.partitionCount
                    columnIDC0 = c0.partitionID
                } else if (c0 is POPMergePartitionOrderedByIntId) {
                    modeC0 = 2
                    columnNameC0 = c0.partitionVariable
                    columnCountC0 = c0.partitionCount
                    columnIDC0 = c0.partitionID
                } else if (c0 is POPMergePartitionCount) {
                    modeC0 = 3
                    columnNameC0 = c0.partitionVariable
                    columnCountC0 = c0.partitionCount
                    columnIDC0 = c0.partitionID
                }
                if (c1 is POPMergePartition) {
                    modeC1 = 1
                    columnNameC1 = c1.partitionVariable
                    columnCountC1 = c1.partitionCount
                    columnIDC1 = c1.partitionID
                } else if (c1 is POPMergePartitionOrderedByIntId) {
                    modeC1 = 2
                    columnNameC1 = c1.partitionVariable
                    columnCountC1 = c1.partitionCount
                    columnIDC1 = c1.partitionID
                } else if (c1 is POPMergePartitionCount) {
                    modeC1 = 3
                    columnNameC1 = c1.partitionVariable
                    columnCountC1 = c1.partitionCount
                    columnIDC1 = c1.partitionID
                }
                if (modeC0 == modeC1 && columnNameC0 == columnNameC1 && modeC0 > 0) {
                    if (columnCountC0 == columnCountC1) {
                        val columnID = query.mergePartitionOperator(columnIDC0, columnIDC1, c1.getChildren()[0])
                        if (modeC0 == 1) {
                            res = POPMergePartition(query, node.projectedVariables, columnNameC0, columnCountC0, columnID, POPUnion(query, node.projectedVariables, c0.getChildren()[0], c1.getChildren()[0]))
                            query.removePartitionOperator(c0.getUUID(), columnID)
                            query.removePartitionOperator(c1.getUUID(), columnID)
                            query.addPartitionOperator(res.getUUID(), columnID)
                            query.partitionOperatorCount.clear()
                            println("change ${c0.getUUID()} ${c1.getUUID()} D")
                            onChange()
                        } else if (modeC0 == 2) {
                            res = POPMergePartitionOrderedByIntId(query, node.projectedVariables, columnNameC0, columnCountC0, columnID, POPUnion(query, node.projectedVariables, c0.getChildren()[0], c1.getChildren()[0]))
                            query.removePartitionOperator(c0.getUUID(), columnID)
                            query.removePartitionOperator(c1.getUUID(), columnID)
                            query.addPartitionOperator(res.getUUID(), columnID)
                            query.partitionOperatorCount.clear()
                            println("change ${c0.getUUID()} ${c1.getUUID()} E")
                            onChange()
                        } else if (modeC0 == 3) {
                            res = POPMergePartitionCount(query, node.projectedVariables, columnNameC0, columnCountC0, columnID, POPUnion(query, node.projectedVariables, c0.getChildren()[0], c1.getChildren()[0]))
                            query.removePartitionOperator(c0.getUUID(), columnID)
                            query.removePartitionOperator(c1.getUUID(), columnID)
                            query.addPartitionOperator(res.getUUID(), columnID)
                            query.partitionOperatorCount.clear()
                            println("change ${c0.getUUID()} ${c1.getUUID()} F")
                            onChange()
                        } else {
                            throw Exception("not reachable - implementation error")
                        }
                    } else {
                        throw Exception("not implemented ... column counts are different")
                    }
                }
            }
            is POPProjection -> {
                val c = node.children[0]
                if (c is POPMergePartition) {
                    res = POPMergePartition(query, node.projectedVariables, c.partitionVariable, c.partitionCount, c.partitionID, POPProjection(query, node.projectedVariables, c.children[0]))
                    query.removePartitionOperator(c.getUUID(), c.partitionID)
                    query.addPartitionOperator(res.getUUID(), c.partitionID)
                    query.partitionOperatorCount.clear()
                    println("change ${c.getUUID()} G")
                    onChange()
                } else if (c is POPMergePartitionOrderedByIntId) {
                    res = POPMergePartitionOrderedByIntId(query, node.projectedVariables, c.partitionVariable, c.partitionCount, c.partitionID, POPProjection(query, node.projectedVariables, c.children[0]))
                    query.removePartitionOperator(c.getUUID(), c.partitionID)
                    query.addPartitionOperator(res.getUUID(), c.partitionID)
                    query.partitionOperatorCount.clear()
                    println("change ${c.getUUID()} H")
                    onChange()
                } else if (c is POPMergePartitionCount) {
                    res = POPMergePartitionCount(query, node.projectedVariables, c.partitionVariable, c.partitionCount, c.partitionID, POPProjection(query, node.projectedVariables, c.children[0]))
                    query.removePartitionOperator(c.getUUID(), c.partitionID)
                    query.addPartitionOperator(res.getUUID(), c.partitionID)
                    query.partitionOperatorCount.clear()
                    println("change ${c.getUUID()} I")
                    onChange()
                }
            }
            is POPReduced -> {
                val c = node.children[0]
                if (c is POPMergePartition) {
                    res = POPMergePartition(query, node.projectedVariables, c.partitionVariable, c.partitionCount, c.partitionID, POPReduced(query, node.projectedVariables, c.children[0]))
                    query.removePartitionOperator(c.getUUID(), c.partitionID)
                    query.addPartitionOperator(res.getUUID(), c.partitionID)
                    query.partitionOperatorCount.clear()
                    println("change ${c.getUUID()} J")
                    onChange()
                } else if (c is POPMergePartitionOrderedByIntId) {
                    res = POPMergePartitionOrderedByIntId(query, node.projectedVariables, c.partitionVariable, c.partitionCount, c.partitionID, POPReduced(query, node.projectedVariables, c.children[0]))
                    query.removePartitionOperator(c.getUUID(), c.partitionID)
                    query.addPartitionOperator(res.getUUID(), c.partitionID)
                    query.partitionOperatorCount.clear()
                    println("change ${c.getUUID()} K")
                    onChange()
                } else if (c is POPMergePartitionCount) {
                    res = POPMergePartitionCount(query, node.projectedVariables, c.partitionVariable, c.partitionCount, c.partitionID, POPReduced(query, node.projectedVariables, c.children[0]))
                    query.removePartitionOperator(c.getUUID(), c.partitionID)
                    query.addPartitionOperator(res.getUUID(), c.partitionID)
                    query.partitionOperatorCount.clear()
                    println("change ${c.getUUID()} L")
                    onChange()
                }
            }
            is POPFilter -> {
                val c = node.children[0]
                if (c is POPMergePartition) {
                    res = POPMergePartition(query, node.projectedVariables, c.partitionVariable, c.partitionCount, c.partitionID, POPFilter(query, node.projectedVariables, node.children[1] as AOPBase, c.children[0]))
                    query.removePartitionOperator(c.getUUID(), c.partitionID)
                    query.addPartitionOperator(res.getUUID(), c.partitionID)
                    query.partitionOperatorCount.clear()
                    println("change ${c.getUUID()} M")
                    onChange()
                } else if (c is POPMergePartitionOrderedByIntId) {
                    res = POPMergePartitionOrderedByIntId(query, node.projectedVariables, c.partitionVariable, c.partitionCount, c.partitionID, POPFilter(query, node.projectedVariables, node.children[1] as AOPBase, c.children[0]))
                    query.removePartitionOperator(c.getUUID(), c.partitionID)
                    query.addPartitionOperator(res.getUUID(), c.partitionID)
                    query.partitionOperatorCount.clear()
                    println("change ${c.getUUID()} N")
                    onChange()
                } else if (c is POPMergePartitionCount) {
                    res = POPMergePartitionCount(query, node.projectedVariables, c.partitionVariable, c.partitionCount, c.partitionID, POPFilter(query, node.projectedVariables, node.children[1] as AOPBase, c.children[0]))
                    query.removePartitionOperator(c.getUUID(), c.partitionID)
                    query.addPartitionOperator(res.getUUID(), c.partitionID)
                    query.partitionOperatorCount.clear()
                    println("change ${c.getUUID()} O")
                    onChange()
                }
            }
            is POPSplitPartition -> {
//splitting must always split all variables provided by its direct children - if there is a different children, adapt the variables
                val c = node.children[0]
                when (c) {
                    is POPMergePartition -> {
                        if (node.partitionVariable == c.partitionVariable) {
                            if (node.partitionCount == c.partitionCount) {
                                res = c.children[0]
                                query.removePartitionOperator(c.getUUID(), c.partitionID)
                                query.removePartitionOperator(node.getUUID(), node.partitionID)
                                query.mergePartitionOperator(node.partitionID, c.partitionID, res)
                                query.partitionOperatorCount.clear()
                                println("change ${node.getUUID()} ${c.getUUID()} P")
                                onChange()
                            } else if (node.partitionCount < c.partitionCount) {
                                query.removePartitionOperator(c.getUUID(), c.partitionID)
                                query.removePartitionOperator(node.getUUID(), node.partitionID)
                                res = POPChangePartitionOrderedByIntId(query, node.projectedVariables, node.partitionVariable, c.partitionCount, node.partitionCount, c.partitionID, node.partitionID, c.children[0])
                                query.addPartitionOperator(res.getUUID(), res.partitionIDTo)
                                query.addPartitionOperator(res.getUUID(), res.partitionIDFrom)
                                onChange()
                            }
                        }
                    }
                    is POPMergePartitionOrderedByIntId -> {
                        if (node.partitionVariable == c.partitionVariable) {
                            if (node.partitionCount == c.partitionCount) {
                                res = c.children[0]
                                query.removePartitionOperator(c.getUUID(), c.partitionID)
                                query.removePartitionOperator(node.getUUID(), node.partitionID)
                                query.mergePartitionOperator(node.partitionID, c.partitionID, res)
                                query.partitionOperatorCount.clear()
                                println("change ${node.getUUID()} ${c.getUUID()} Q")
                                onChange()
                            } else if (node.partitionCount < c.partitionCount) {
                                query.removePartitionOperator(c.getUUID(), c.partitionID)
                                query.removePartitionOperator(node.getUUID(), node.partitionID)
                                res = POPChangePartitionOrderedByIntId(query, node.projectedVariables, node.partitionVariable, c.partitionCount, node.partitionCount, c.partitionID, node.partitionID, c.children[0])
                                query.addPartitionOperator(res.getUUID(), res.partitionIDTo)
                                query.addPartitionOperator(res.getUUID(), res.partitionIDFrom)
                                onChange()
                            }
                        }
                    }
                    is POPMergePartitionCount -> {
                        if (node.partitionVariable == c.partitionVariable) {
                            if (node.partitionCount == c.partitionCount) {
                                res = c.children[0]
                                query.removePartitionOperator(c.getUUID(), c.partitionID)
                                query.removePartitionOperator(node.getUUID(), node.partitionID)
                                query.mergePartitionOperator(node.partitionID, c.partitionID, res)
                                query.partitionOperatorCount.clear()
                                println("change ${node.getUUID()} ${c.getUUID()} R")
                                onChange()
                            }
                        }
                    }
                    is POPReduced -> {
                        res = POPReduced(query, c.projectedVariables, POPSplitPartition(query, c.children[0].getProvidedVariableNames(), node.partitionVariable, node.partitionCount, node.partitionID, c.children[0]))
                        query.removePartitionOperator(node.getUUID(), node.partitionID)
                        query.addPartitionOperator(res.children[0].getUUID(), node.partitionID)
                        query.partitionOperatorCount.clear()
                        println("change ${node.getUUID()} S")
                        onChange()
                    }
                    is POPProjection -> {
                        res = POPProjection(query, c.projectedVariables, POPSplitPartition(query, c.children[0].getProvidedVariableNames(), node.partitionVariable, node.partitionCount, node.partitionID, c.children[0]))
                        query.removePartitionOperator(node.getUUID(), node.partitionID)
                        query.addPartitionOperator(res.children[0].getUUID(), node.partitionID)
                        query.partitionOperatorCount.clear()
                        println("change ${node.getUUID()} T")
                        onChange()
                    }
                    is POPFilter -> {
                        res = POPFilter(query, c.projectedVariables, c.children[1] as AOPBase, POPSplitPartition(query, c.children[0].getProvidedVariableNames(), node.partitionVariable, node.partitionCount, node.partitionID, c.children[0]))
                        query.removePartitionOperator(node.getUUID(), node.partitionID)
                        query.addPartitionOperator(res.children[0].getUUID(), node.partitionID)
                        query.partitionOperatorCount.clear()
                        println("change ${node.getUUID()} U")
                        onChange()
                    }
                    is TripleStoreIteratorGlobal -> {
                        if (TripleStoreLocal.providesFeature(TripleStoreFeature.PARTITION, null)) {
                            try {
                                val p = Partition(Partition(), node.partitionVariable, 0, node.partitionCount)
                                val params = TripleStoreFeatureParamsPartition(c.idx, Array(3) { c.children[it] as AOPBase }, p)
                                if (params.getColumn() > 0 && TripleStoreLocal.providesFeature(TripleStoreFeature.PARTITION, params)) {
                                    res = POPSplitPartitionFromStore(query, node.projectedVariables, node.partitionVariable, node.partitionCount, node.partitionID, c)
                                    c.partition.limit[node.partitionVariable] = node.partitionCount
                                    query.removePartitionOperator(node.getUUID(), node.partitionID)
                                    query.addPartitionOperator(res.getUUID(), node.partitionID)
                                    query.partitionOperatorCount.clear()
                                    println("change ${node.getUUID()} V")
                                    onChange()
                                }
                            } catch (e: DontCareWhichException) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
        }
        return res
    }
}
