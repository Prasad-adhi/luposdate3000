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

import lupos.operator.arithmetik.noinput.AOPConstant
import lupos.shared.EIndexPattern
import lupos.shared.EIndexPatternExt
import lupos.shared.EIndexPatternHelper
import lupos.shared.IQuery
import lupos.shared.LuposHostname
import lupos.shared.LuposStoreKey
import lupos.shared.Luposdate3000Instance
import lupos.shared.Partition
import lupos.shared.SanityCheck
import lupos.shared.XMLElement
import lupos.shared.inline.ByteArrayHelper
import lupos.shared.operator.IOPBase
import kotlin.jvm.JvmField

public class TripleStoreIndexDescriptionPartitionedByKey(
    idx: EIndexPattern,
    @JvmField internal val partitionCount: Int,
    instance: Luposdate3000Instance,
) : TripleStoreIndexDescription(instance) {
    internal val hostnames = Array<LuposHostname>(partitionCount) { "" }
    internal val keys = Array<LuposStoreKey>(partitionCount) { "" }
    internal val key_size: Int

    @JvmField
    internal var byteArray: ByteArray? = null
    override fun toByteArray(): ByteArray {
        if (byteArray != null) {
            return byteArray!!
        }
        var size = 12
        for (i in 0 until partitionCount) {
            var buf1 = hostnames[i].encodeToByteArray()
            var buf2 = keys[i].encodeToByteArray()
            size += 8 + buf1.size + buf2.size
        }
        val byteArray2 = ByteArray(size)
        byteArray = byteArray2
        var off = 0
        ByteArrayHelper.writeInt4(byteArray2, off, ETripleStoreIndexDescriptionPartitionedTypeExt.PartitionedByKey)
        off += 4
        ByteArrayHelper.writeInt4(byteArray2, off, idx_set.first())
        off += 4
        ByteArrayHelper.writeInt4(byteArray2, off, partitionCount)
        off += 4
        for (i in 0 until partitionCount) {
            var buf1 = hostnames[i].encodeToByteArray()
            ByteArrayHelper.writeInt4(byteArray2, off, buf1.size)
            off += 4
            buf1.copyInto(byteArray2, off)
            off += buf1.size
            var buf2 = keys[i].encodeToByteArray()
            ByteArrayHelper.writeInt4(byteArray2, off, buf2.size)
            off += 4
            buf2.copyInto(byteArray2, off)
            off += buf2.size
        }
        return byteArray2
    }

    internal override fun findPartitionFor(query: IQuery, triple: IntArray): Int {
        val hash: Int
        when (key_size) {
            1 -> {
                SanityCheck.check { triple[EIndexPatternHelper.tripleIndicees[idx_set[0]][0]] >= 0 }
                hash = triple[EIndexPatternHelper.tripleIndicees[idx_set[0]][0]]
            }
            2 -> {
                SanityCheck.check { triple[EIndexPatternHelper.tripleIndicees[idx_set[0]][0]] >= 0 }
                SanityCheck.check { triple[EIndexPatternHelper.tripleIndicees[idx_set[0]][1]] >= 0 }
                hash = triple[EIndexPatternHelper.tripleIndicees[idx_set[0]][0]] + triple[EIndexPatternHelper.tripleIndicees[idx_set[0]][1]]
            }
            3 -> {
                SanityCheck.check { triple[EIndexPatternHelper.tripleIndicees[idx_set[0]][0]] >= 0 }
                SanityCheck.check { triple[EIndexPatternHelper.tripleIndicees[idx_set[0]][1]] >= 0 }
                SanityCheck.check { triple[EIndexPatternHelper.tripleIndicees[idx_set[0]][2]] >= 0 }
                hash = triple[EIndexPatternHelper.tripleIndicees[idx_set[0]][0]] + triple[EIndexPatternHelper.tripleIndicees[idx_set[0]][1]] + triple[EIndexPatternHelper.tripleIndicees[idx_set[0]][2]]
            }
            else -> throw Exception("unreachable")
        }
        if (hash < 0) {
            return -hash % partitionCount
        } else {
            return hash % partitionCount
        }
    }

    public override fun getStore(query: IQuery, params: Array<IOPBase>, partition: Partition): Pair<LuposHostname, LuposStoreKey> {
        SanityCheck.check { partition.limit.size == 0 }
        SanityCheck.check { partition.data.size == 0 }
        val triple = IntArray(3) { -1 }
        var counter = 0
        for (i in 0 until 3) {
            val param = params[i]
            if (param is AOPConstant) {
                triple[i] = param.value
                counter++
            }
        }
        SanityCheck.check { counter == key_size }
        val partitionToUse = findPartitionFor(query, triple)
        return Pair(hostnames[partitionToUse], keys[partitionToUse])
    }

    init {
        when (idx) {
            EIndexPatternExt.SPO, EIndexPatternExt.SOP, EIndexPatternExt.PSO, EIndexPatternExt.POS, EIndexPatternExt.OPS, EIndexPatternExt.OSP -> {
                idx_set = intArrayOf(EIndexPatternExt.SPO, EIndexPatternExt.SOP, EIndexPatternExt.PSO, EIndexPatternExt.POS, EIndexPatternExt.OPS, EIndexPatternExt.OPS)
                key_size = 3
            }
            EIndexPatternExt.S_PO, EIndexPatternExt.S_OP -> {
                idx_set = intArrayOf(EIndexPatternExt.S_PO, EIndexPatternExt.S_OP)
                key_size = 1
            }
            EIndexPatternExt.P_SO, EIndexPatternExt.P_OS -> {
                idx_set = intArrayOf(EIndexPatternExt.P_SO, EIndexPatternExt.P_OS)
                key_size = 1
            }
            EIndexPatternExt.O_SP, EIndexPatternExt.O_PS -> {
                idx_set = intArrayOf(EIndexPatternExt.O_SP, EIndexPatternExt.O_PS)
                key_size = 1
            }
            EIndexPatternExt.SP_O, EIndexPatternExt.PS_O -> {
                idx_set = intArrayOf(EIndexPatternExt.SP_O, EIndexPatternExt.PS_O)
                key_size = 2
            }
            EIndexPatternExt.SO_P, EIndexPatternExt.OS_P -> {
                idx_set = intArrayOf(EIndexPatternExt.SO_P, EIndexPatternExt.OS_P)
                key_size = 2
            }
            EIndexPatternExt.PO_S, EIndexPatternExt.OP_S -> {
                idx_set = intArrayOf(EIndexPatternExt.PO_S, EIndexPatternExt.OP_S)
                key_size = 2
            }
            else -> SanityCheck.checkUnreachable()
        }
    }

    internal override fun assignHosts() {
        for (i in 0 until partitionCount) {
            val tmp = ((instance.tripleStoreManager!!) as TripleStoreManagerImpl).getNextHostAndKey()
            SanityCheck.check { hostnames[i] == "" }
            SanityCheck.check { keys[i] == "" }
            hostnames[i] = tmp.first
            keys[i] = tmp.second
        }
    }

    public override fun getPartitionCount(): Int {
        return 1
    }

    public override fun getDistributionCount(): Int {
        return partitionCount
    }

    internal override fun getAllLocations(): List<Pair<LuposHostname, LuposStoreKey>> {
        var res = mutableListOf<Pair<LuposHostname, LuposStoreKey>>()
        for (i in 0 until partitionCount) {
            res.add(Pair(hostnames[i], keys[i]))
        }
        return res
    }

    public override fun toXMLElement(): XMLElement {
        val res = super.toXMLElement()
        res.addAttribute("type", "TripleStoreIndexDescriptionPartitionedByKey")
        res.addAttribute("partitionCount", "$partitionCount")
        return res
    }
}
