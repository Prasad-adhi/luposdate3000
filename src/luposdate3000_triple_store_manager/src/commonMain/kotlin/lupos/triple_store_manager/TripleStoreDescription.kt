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
package lupos.triple_store_manager

import lupos.operator.arithmetik.noinput.AOPVariable
import lupos.shared.BugException
import lupos.shared.EIndexPattern
import lupos.shared.EIndexPatternExt
import lupos.shared.EIndexPatternHelper
import lupos.shared.EModifyType
import lupos.shared.IQuery
import lupos.shared.ITripleStoreDescription
import lupos.shared.ITripleStoreDescriptionModifyCache
import lupos.shared.LuposHostname
import lupos.shared.LuposStoreKey
import lupos.shared.Luposdate3000Instance
import lupos.shared.SanityCheck
import lupos.shared.operator.IAOPBase
import lupos.shared.operator.IOPBase
import lupos.shared.operator.noinput.IAOPConstant
import lupos.shared.operator.noinput.IAOPVariable
import kotlin.jvm.JvmField

public class TripleStoreDescription(
    @JvmField internal val indices: Array<TripleStoreIndexDescription>,
    @JvmField internal val instance: Luposdate3000Instance,
) : ITripleStoreDescription {
    public fun toMetaString(): String {
        var res = StringBuilder()
        for (idx in indices) {
            when (idx) {
                is TripleStoreIndexDescriptionPartitionedByID -> {
                    res.append("PartitionedByID;${EIndexPatternExt.names[idx.idx_set[0]]};${idx.partitionCount};${idx.partitionColumn}")
                    for (i in 0 until idx.partitionCount) {
                        res.append(";${idx.hostnames[i]};${idx.keys[i]}")
                    }
                    res.append("|")
                }
                is TripleStoreIndexDescriptionPartitionedByKey -> {
                    res.append("PartitionedByKey;${EIndexPatternExt.names[idx.idx_set[0]]};${idx.partitionCount}")
                    for (i in 0 until idx.partitionCount) {
                        res.append(";${idx.hostnames[i]};${idx.keys[i]}")
                    }
                    res.append("|")
                }
                is TripleStoreIndexDescriptionSimple -> {
                    res.append("Simple;${EIndexPatternExt.names[idx.idx_set[0]]};${idx.hostname};${idx.key}|")
                }
                else -> throw Exception("unexpected type")
            }
        }
        return res.toString()
    }

    public companion object {
        public operator fun invoke(metaString: String, instance: Luposdate3000Instance): TripleStoreDescription {
            var indices = mutableListOf<TripleStoreIndexDescription>()
            val metad = metaString.split("|")
            for (meta in metad) {
                val args = meta.split(";")
                if (args.size > 1) {
                    when (args[0]) {
                        "PartitionedByID" -> {
                            val idx = TripleStoreIndexDescriptionPartitionedByID(EIndexPatternExt.names.indexOf(args[1]), args[2].toInt(), args[3].toInt(), instance)
                            for (i in 0 until args[2].toInt()) {
                                idx.hostnames[i] = args[4 + i * 2]
                                idx.keys[i] = args[4 + i * 2 + 1]
                            }
                            indices.add(idx)
                        }
                        "PartitionedByKey" -> {
                            val idx = TripleStoreIndexDescriptionPartitionedByKey(EIndexPatternExt.names.indexOf(args[1]), args[2].toInt(), instance)
                            for (i in 0 until args[2].toInt()) {
                                idx.hostnames[i] = args[3 + i * 2]
                                idx.keys[i] = args[3 + i * 2 + 1]
                            }
                            indices.add(idx)
                        }
                        "Simple" -> {
                            val idx = TripleStoreIndexDescriptionSimple(EIndexPatternExt.names.indexOf(args[1]), instance)
                            idx.hostname = args[2]
                            idx.key = args[3]
                            indices.add(idx)
                        }
                        else -> throw Exception("unexpected type")
                    }
                }
            }
            return TripleStoreDescription(indices.toTypedArray(), instance)
        }
    }

    internal fun getAllLocations(): List<Pair<LuposHostname, LuposStoreKey>> {
        var res = mutableListOf<Pair<LuposHostname, LuposStoreKey>>()
        for (idx in indices) {
            res.addAll(idx.getAllLocations())
        }
        return res
    }

    public override fun modify_create_cache(type: EModifyType): ITripleStoreDescriptionModifyCache {
        return TripleStoreDescriptionModifyCache(this, type, instance)
    }

    public override fun modify_create_cache_sorted(type: EModifyType, sortedBy: EIndexPattern): ITripleStoreDescriptionModifyCache {
        return TripleStoreDescriptionModifyCache(this, type, sortedBy, instance)
    }

    public override fun getIterator(query: IQuery, params: Array<IAOPBase>, idx: EIndexPattern): IOPBase {
        for (index in indices) {
            if (index.hasPattern(idx)) {
                val projectedVariables = mutableListOf<String>()
                for (param in params) {
                    if (param is AOPVariable && param.name != "_") {
                        projectedVariables.add(param.name)
                    }
                }
                return POPTripleStoreIterator(query, projectedVariables, index, arrayOf<IOPBase>(params[0], params[1], params[2]))
            }
        }
        throw Exception("no valid index found")
    }

    public override fun getHistogram(query: IQuery, params: Array<IAOPBase>, idx: EIndexPattern): Pair<Int, Int> {
        var variableCount = 0
        val filter2 = mutableListOf<Int>()
        for (ii in 0 until 3) {
            val i = EIndexPatternHelper.tripleIndicees[idx][ii]
            val param = params[i]
            if (param is IAOPConstant) {
                SanityCheck.check { filter2.size == ii }
                filter2.add(query.getDictionary().valueToGlobal(param.getValue()))
            } else if (param is IAOPVariable) {
                if (param.getName() != "_") {
                    variableCount++
                }
            } else {
                SanityCheck.checkUnreachable()
            }
        }
        if (variableCount != 1) {
            throw BugException("TripleStoreFeature", "Filter can not be calculated using multipe variables at once. ${params.map { it.toSparql() }}")
        }
        val filter = IntArray(filter2.size) { filter2[it] }
        for (index in indices) {
            if (index.hasPattern(idx)) {
                var first = 0
                var second = 0
                for (store in index.getAllLocations()) {
                    if (store.first == ((query.getInstance().tripleStoreManager!!) as TripleStoreManagerImpl).localhost) {
                        val tmp = ((query.getInstance().tripleStoreManager!!) as TripleStoreManagerImpl).localStoresGet()[store.second]!!.getHistogram(query, filter)
                        first += tmp.first
                        second += tmp.second
                    } else {
                        val conn = instance.communicationHandler!!.openConnection(store.first, "/distributed/query/histogram", mapOf("tag" to store.second))
                        conn.second.writeInt(filter.size)
                        for (i in 0 until filter.size) {
                            conn.second.writeInt(filter[i])
                        }
                        first += conn.first.readInt()
                        second += conn.first.readInt()
                        conn.first.close()
                        conn.second.close()
                    }
                }
                return Pair(first, second)
            }
        }
        throw Exception("no valid index found")
    }
}
