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
package lupos.optimizer.physical

import lupos.operator.base.Query
import lupos.operator.physical.partition.POPChangePartitionOrderedByIntId
import lupos.operator.physical.partition.POPMergePartition
import lupos.operator.physical.partition.POPMergePartitionCount
import lupos.operator.physical.partition.POPMergePartitionOrderedByIntId
import lupos.operator.physical.partition.POPSplitPartition
import lupos.operator.physical.partition.POPSplitPartitionFromStore
import lupos.operator.physical.partition.POPSplitPartitionFromStoreCount
import lupos.optimizer.logical.EOptimizerIDExt
import lupos.optimizer.logical.OptimizerBase
import lupos.shared.EPartitionModeExt
import lupos.shared.operator.IOPBase
import lupos.triple_store_manager.POPTripleStoreIterator

public class PhysicalOptimizerPartitionAssignsSamePartitionCountToAnyRelatedOperator(query: Query) : OptimizerBase(query, EOptimizerIDExt.PhysicalOptimizerPartitionAssignsSamePartitionCountToAnyRelatedOperatorID, "PhysicalOptimizerPartitionAssignsSamePartitionCountToAnyRelatedOperator") {
    // this optimizer makes sure, that every partitioning which belongs to the same section uses the same partition count
    override /*suspend*/ fun optimize(node: IOPBase, parent: IOPBase?, onChange: () -> Unit): IOPBase {
        if (query.getInstance().LUPOS_PARTITION_MODE == EPartitionModeExt.Thread || query.getInstance().LUPOS_PARTITION_MODE == EPartitionModeExt.Process) {
            when (node) {
                is POPSplitPartitionFromStore -> {
                    var storeNodeTmp = node.children[0]
                    while (storeNodeTmp !is POPTripleStoreIterator) {
// this is POPDebug or something similar with is not affecting the calculation - otherwise this node wont be POPSplitPartitionFromStore
                        storeNodeTmp = storeNodeTmp.getChildren()[0]
                    }
                    val storeNode = storeNodeTmp
                    val max_count = query.partitionOperatorCount[node.partitionID]
                    var new_count = storeNode.changeToIndexWithMaximumPartitions(max_count, node.partitionVariable)
                    if (new_count > 0) {
                        query.partitionOperatorCount[node.partitionID] = new_count
                        node.partitionCount = new_count
                        if (new_count != max_count) {
                            onChange()
                        }
                    }
                }
                is POPSplitPartitionFromStoreCount -> {
                    var storeNodeTmp = node.children[0]
                    while (storeNodeTmp !is POPTripleStoreIterator) {
// this is POPDebug or something similar with is not affecting the calculation - otherwise this node wont be POPSplitPartitionFromStoreCount
                        storeNodeTmp = storeNodeTmp.getChildren()[0]
                    }
                    val storeNode = storeNodeTmp
                    val max_count = query.partitionOperatorCount[node.partitionID]
                    val new_count = storeNode.changeToIndexWithMaximumPartitions(max_count, node.partitionVariable)
                    if (new_count > 0) {
                        query.partitionOperatorCount[node.partitionID] = new_count
                node.partitionCount = new_count
                        if (new_count != max_count) {
                            onChange()
                        }
                    }
                }
                is POPMergePartition -> {
                    val tmp = query.partitionOperatorCount[node.partitionID]
                    if (tmp != null && tmp != node.partitionCount) {
                        node.partitionCount = tmp
                        onChange()
                    }
                }
                is POPMergePartitionCount -> {
                    val tmp = query.partitionOperatorCount[node.partitionID]
                    if (tmp != null && tmp != node.partitionCount) {
                        node.partitionCount = tmp
                        onChange()
                    }
                }
                is POPMergePartitionOrderedByIntId -> {
                    val tmp = query.partitionOperatorCount[node.partitionID]
                    if (tmp != null && tmp != node.partitionCount2) {
                        node.partitionCount2 = tmp
                        onChange()
                    }
                }
                is POPSplitPartition -> {
                    val tmp = query.partitionOperatorCount[node.partitionID]
                    if (tmp != null && tmp != node.partitionCount) {
                        node.partitionCount = tmp
                        onChange()
                    }
                }
                is POPChangePartitionOrderedByIntId -> {
                    val tmp = query.partitionOperatorCount[node.partitionIDFrom]
                    if (tmp != null && tmp != node.partitionCountFrom) {
                        node.partitionCountFrom = tmp
                        onChange()
                    }
                    val tmp2 = query.partitionOperatorCount[node.partitionIDTo]
                    if (tmp2 != null && tmp2 != node.partitionCountTo) {
                        node.partitionCountTo = tmp2
                        onChange()
                    }
                }
            }
        }
        return node
    }
}
