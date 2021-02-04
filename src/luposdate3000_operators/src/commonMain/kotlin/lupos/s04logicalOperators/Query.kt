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
package lupos.s04logicalOperators
import lupos.s00misc.MyLock
import lupos.s00misc.ParallelJob
import lupos.s00misc.Partition
import lupos.s00misc.SanityCheck
import lupos.s00misc.XMLElement
import lupos.s03resultRepresentation.IResultSetDictionary
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s04logicalOperators.iterator.IteratorBundle
import lupos.s09physicalOperators.POPBase
import lupos.s09physicalOperators.partition.POPChangePartitionOrderedByIntId
import lupos.s09physicalOperators.partition.POPMergePartition
import lupos.s09physicalOperators.partition.POPMergePartitionCount
import lupos.s09physicalOperators.partition.POPMergePartitionOrderedByIntId
import lupos.s09physicalOperators.partition.POPSplitPartition
import lupos.s09physicalOperators.partition.POPSplitPartitionFromStore
import lupos.s09physicalOperators.partition.POPSplitPartitionPassThrough
import kotlin.jvm.JvmField
public class PartitionHelper public constructor() {
    @JvmField public var iterators: MutableMap<Partition, Array<IteratorBundle>>? = null
    internal var jobs: MutableMap<Partition, ParallelJob>? = null
    internal val lock = MyLock()
}
public class Query public constructor(@JvmField public val dictionary: ResultSetDictionary, @JvmField public var transactionID: Long) : IQuery {
    public constructor(dictionary: ResultSetDictionary) : this(dictionary, global_transactionID++)
    public constructor(transactionID: Long) : this(ResultSetDictionary(), transactionID)
    public constructor() : this(ResultSetDictionary(), global_transactionID++)
    @JvmField
    public var _workingDirectory: String = ""
    @JvmField
    public var filtersMovedUpFromOptionals: Boolean = false
    @JvmField
    public var commited: Boolean = false
    @JvmField
    public var dontCheckVariableExistence: Boolean = false
    @JvmField
    public var generatedNameCounter: Int = 0
    @JvmField
    public var generatedNameByBase: MutableMap<String, String> = mutableMapOf()
    @JvmField
    internal val partitions = mutableMapOf<Long, PartitionHelper>()
    @JvmField
    internal val partitionsLock = MyLock()
    @JvmField
    public val partitionOperators: MutableMap<Int, MutableSet<Long>> = mutableMapOf()
    @JvmField
    public val partitionOperatorCount: MutableMap<Int, Int> = mutableMapOf()
    @JvmField
    internal var root: IOPBase? = null

    override fun initialize() {
        initialize(root!!)
    }
    @JvmField public var allVariationsKey: MutableMap<String, Int> = mutableMapOf<String, Int>()
    override fun getDistributionKey(): Map<String, Int> = allVariationsKey
    internal var operatorgraphParts = mutableMapOf<String, XMLElement>()
    internal fun getAllVariations(node: IOPBase, allNames: Array<String>, allSize: IntArray, allIdx: IntArray, offset: Int) {
        if (offset == allNames.size) {
            for (i in 0 until allNames.size) {
                allVariationsKey[allNames[i]] = allIdx[i]
            }
            operatorgraphParts["${node.getUUID()}:$allVariationsKey"] = node.toXMLElementRoot(true)
            allVariationsKey.clear()
        } else {
            for (i in 0 until allSize[offset]) {
                allIdx[offset] = i
                getAllVariations(node, allNames, allSize, allIdx, offset + 1)
            }
        }
    }

    internal fun initialize_helper(node: IOPBase, currentPartitions: Map<String, Int>) {
        if ((node is POPBase) || (node is OPBaseCompound)) {
            val currentPartitionsCopy = mutableMapOf<String, Int>()
            currentPartitionsCopy.putAll(currentPartitions)
            when (node) {
                is POPMergePartition -> {
                    SanityCheck.check { currentPartitionsCopy[node.partitionVariable] == null }
                    currentPartitionsCopy[node.partitionVariable] = node.partitionCount
                }
                is POPMergePartitionCount -> {
                    SanityCheck.check { currentPartitionsCopy[node.partitionVariable] == null }
                    currentPartitionsCopy[node.partitionVariable] = node.partitionCount
                }
                is POPMergePartitionOrderedByIntId -> {
                    SanityCheck.check { currentPartitionsCopy[node.partitionVariable] == null }
                    currentPartitionsCopy[node.partitionVariable] = node.partitionCount
                }
                is POPChangePartitionOrderedByIntId -> {
                    SanityCheck.check { currentPartitionsCopy[node.partitionVariable] == node.partitionCountTo }
                    currentPartitionsCopy[node.partitionVariable] = node.partitionCountFrom
                }
                is POPSplitPartition -> {
                    SanityCheck.check { currentPartitionsCopy[node.partitionVariable] != null }
                    currentPartitionsCopy.remove(node.partitionVariable)
                }
                is POPSplitPartitionFromStore -> {
                    SanityCheck.check { currentPartitionsCopy[node.partitionVariable] == node.partitionCount }
                }
                is POPSplitPartitionPassThrough -> {
                    SanityCheck.check { currentPartitionsCopy[node.partitionVariable] == node.partitionCount }
                }
            }
            for (ci in 0 until (node as OPBase).childrenToVerifyCount()) {
                val c = node.getChildren()[ci]
                initialize_helper(c, currentPartitionsCopy)
            }
            when (node) {
                is POPMergePartition,
                is POPMergePartitionCount,
                is POPMergePartitionOrderedByIntId,
                is POPChangePartitionOrderedByIntId,
                is POPSplitPartition,
                is POPSplitPartitionFromStore,
                is POPSplitPartitionPassThrough
                -> {
                    val allNames = Array(currentPartitionsCopy.size) { "" }
                    val allSize = IntArray(currentPartitionsCopy.size)
                    var i = 0
                    for ((k, v) in currentPartitionsCopy) {
                        allNames[i] = k
                        allSize[i] = v
                        i++
                    }
                    getAllVariations(node, allNames, allSize, IntArray(currentPartitionsCopy.size), 0)
//                    println("subquery graph within partition $currentPartitionsCopy")
//                    println(toXMLElementRoot(true).toPrettyString())
                }
            }
        } else {
            throw Exception("this query is not executable ${node.getClassname()} ${(node as OPBase).uuid}")
        }
    }

    override fun initialize(newroot: IOPBase) {
        println("initializing Query ------------ start")
        println(newroot.toXMLElementRoot(false).toPrettyString())
        root = newroot
        transactionID = global_transactionID++
        commited = false
        partitions.clear()
        operatorgraphParts.clear()
        initialize_helper(newroot, mutableMapOf())
        for ((k, v) in operatorgraphParts) {
            println("subgraph $k")
            println(v.toPrettyString())
        }
        println("initializing Query ------------ done")
    }
    public fun getNextPartitionOperatorID(): Int {
        var res = 0
        while (partitionOperators[res] != null) {
            res++
        }
        return res
    }
    public fun addPartitionOperator(uuid: Long, id: Int) {
        val tmp = partitionOperators[id]
        if (tmp == null) {
            partitionOperators[id] = mutableSetOf(uuid)
        } else {
            SanityCheck.check { !tmp.contains(uuid) }
            tmp.add(uuid)
        }
    }
    public fun removePartitionOperator(uuid: Long, id: Int) {
        val tmp = partitionOperators[id]
        if (tmp != null) {
            SanityCheck.check { tmp.contains(uuid) }
            tmp.remove(uuid)
            if (tmp.size == 0) {
                partitionOperators.remove(id)
            }
        }
    }
    private fun changeID(root: IOPBase, list: Set<Long>, idFrom: Int, idTo: Int) {
        if (list.contains(root.getUUID())) {
            when (root) {
                is POPMergePartitionCount -> root.partitionID = idTo
                is POPMergePartition -> root.partitionID = idTo
                is POPMergePartitionOrderedByIntId -> root.partitionID = idTo
                is POPSplitPartitionFromStore -> root.partitionID = idTo
                is POPSplitPartition -> root.partitionID = idTo
                is POPChangePartitionOrderedByIntId -> {
                    if (root.partitionIDFrom == idFrom) {
                        root.partitionIDFrom = idTo
                    } else {
                        SanityCheck.check { root.partitionIDTo == idFrom }
                        root.partitionIDTo = idTo
                    }
                }
                else -> {
                    SanityCheck.checkUnreachable()
                }
            }
        }
        for (c in root.getChildren()) {
            changeID(c, list, idFrom, idTo)
        }
    }
    public fun mergePartitionOperator(id1: Int, id2: Int, root: IOPBase): Int {
        partitionOperators[id1]!!.addAll(partitionOperators[id2]!!)
        changeID(root, partitionOperators[id2]!!, id2, id1)
        partitionOperators.remove(id2)
        return id1
    }
    override fun setWorkingDirectory(value: String) {
        _workingDirectory = if (value.endsWith("/")) {
            value
        } else {
            "$value/"
        }
    }
    override fun getTransactionID(): Long = transactionID
    override fun getWorkingDirectory(): String = _workingDirectory
    override fun getDictionary(): IResultSetDictionary = dictionary
    override fun checkVariableExistence(): Boolean = !dontCheckVariableExistence
    override fun setCommited() {
        commited = true
    }
    public fun getUniqueVariableName(): String = "#+${generatedNameCounter++}"
    public fun isGeneratedVariableName(name: String): Boolean = name.startsWith('#')
    public /*suspend*/ fun getPartitionHelper(uuid: Long): PartitionHelper {
        var res: PartitionHelper? = null
        partitionsLock.withLock {
            res = partitions[uuid]
            if (res == null) {
                res = PartitionHelper()
                partitions[uuid] = res!!
            }
        }
        return res!!
    }
    public fun getUniqueVariableName(name: String): String {
        val tmp = generatedNameByBase[name]
        return if (tmp != null) {
            tmp
        } else {
            val tmp2 = getUniqueVariableName()
            generatedNameByBase[name] = tmp2
            tmp2
        }
    }
    internal companion object {
        @JvmField
        internal var global_transactionID = 0L
    }
}
