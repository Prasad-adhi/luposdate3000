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

package lupos.s05tripleStore

import lupos.s00misc.EIndexPattern
import lupos.s00misc.EIndexPatternExt
import lupos.s00misc.SanityCheck
import kotlin.jvm.JvmField

public class TripleStoreIndexDescriptionPartitionedByKey(
    idx: EIndexPattern,
    @JvmField internal val partitionCount: Int,
) : TripleStoreIndexDescription() {
    internal val hostnames = Array<LuposHostname>(partitionCount) { "" }
    internal val keys = Array<LuposStoreKey>(partitionCount) { "" }

    init {
        idx_set = when (idx) {
            EIndexPatternExt.SPO, EIndexPatternExt.SOP, EIndexPatternExt.PSO, EIndexPatternExt.POS, EIndexPatternExt.OPS, EIndexPatternExt.OPS -> intArrayOf(EIndexPatternExt.SPO, EIndexPatternExt.SOP, EIndexPatternExt.PSO, EIndexPatternExt.POS, EIndexPatternExt.OPS, EIndexPatternExt.OPS)
            EIndexPatternExt.S_PO, EIndexPatternExt.S_OP -> intArrayOf(EIndexPatternExt.S_PO, EIndexPatternExt.S_OP)
            EIndexPatternExt.P_SO, EIndexPatternExt.P_OS -> intArrayOf(EIndexPatternExt.P_SO, EIndexPatternExt.P_OS)
            EIndexPatternExt.O_SP, EIndexPatternExt.O_PS -> intArrayOf(EIndexPatternExt.O_SP, EIndexPatternExt.O_PS)
            EIndexPatternExt.SP_O, EIndexPatternExt.PS_O -> intArrayOf(EIndexPatternExt.SP_O, EIndexPatternExt.PS_O)
            EIndexPatternExt.SO_P, EIndexPatternExt.OS_P -> intArrayOf(EIndexPatternExt.SO_P, EIndexPatternExt.OS_P)
            EIndexPatternExt.PO_S, EIndexPatternExt.OP_S -> intArrayOf(EIndexPatternExt.PO_S, EIndexPatternExt.OP_S)
            else -> SanityCheck.checkUnreachable()
        }
    }

    internal override fun assignHosts() {
        for (i in 0 until partitionCount) {
            val tmp = (tripleStoreManager as TripleStoreManagerImpl).getNextHostAndKey()
            hostnames[i] = tmp.first
            keys[i] = tmp.second
        }
    }

    internal override fun releaseHosts() {
        for (i in 0 until partitionCount) {
            (tripleStoreManager as TripleStoreManagerImpl).releaseHostAndKey(hostnames[i], keys[i])
        }
    }

    public override fun getPartitionCount(): Int {
        return 1
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
