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

package lupos.s04logicalOperators.multiinput
import lupos.s00misc.EOperatorIDExt
import lupos.s00misc.ESortPriorityExt
import lupos.s00misc.XMLElement
import lupos.s04logicalOperators.HistogramResult
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.IQuery
import lupos.s04logicalOperators.LOPBase
import kotlin.jvm.JvmField
public class LOPJoin public constructor(query: IQuery, first: IOPBase, second: IOPBase, @JvmField public val optional: Boolean) : LOPBase(query, EOperatorIDExt.LOPJoinID, "LOPJoin", arrayOf(first, second), ESortPriorityExt.JOIN) {
    override /*suspend*/ fun toXMLElement(): XMLElement = super.toXMLElement().addAttribute("optional", "" + optional)
    override fun equals(other: Any?): Boolean = other is LOPJoin && optional == other.optional && children[0] == other.children[0] && children[1] == other.children[1]
    override fun cloneOP(): IOPBase = LOPJoin(query, children[0].cloneOP(), children[1].cloneOP(), optional)
    public companion object {
        public fun getColumns(columnsA: List<String>, columnsB: List<String>): Array<MutableList<String>> {
            /*result array indices 0:Join,1:AOnly,2:BOnly*/
            val res = Array(3) { mutableListOf<String>() }
            res[2].addAll(columnsB)
            for (name in columnsA) {
                if (res[2].contains(name)) {
                    res[0].add(name)
                    res[2].remove(name)
                } else {
                    res[1].add(name)
                }
            }
            return res
        }
        public fun mergeHistograms(a: HistogramResult, b: HistogramResult, optional: Boolean): HistogramResult {
            val res = HistogramResult()
            val columns = getColumns(a.values.keys.toList(), b.values.keys.toList())
            val c0 = a.count.toDouble()
            val c1 = b.count.toDouble()
            var estimatedResults = c0 * c1
            val tmpMap = mutableMapOf<String, Int>()
            for (v in columns[0]) {
                val av = a.values[v]!!.toDouble()
                val bv = b.values[v]!!.toDouble()
                when {
                    av == 0.0 -> {
                        estimatedResults = 0.0
                        tmpMap[v] = 0
                    }
                    bv == 0.0 -> {
                        estimatedResults = 0.0
                        tmpMap[v] = 0
                    }
                    av < bv -> {
                        // not all rows from b get a match
                        val diff = bv - av
                        estimatedResults *= (1 - diff / bv)
                        tmpMap[v] = av.toInt()
                    }
                    else -> {
                        // not all rows from a get a match
                        val diff = av - bv
                        estimatedResults *= (1 - diff / av)
                        tmpMap[v] = bv.toInt()
                    }
                }
            }
            if (estimatedResults < 0.0) {
                estimatedResults = 0.0
            }
            if (optional) {
                estimatedResults += c0
                if (estimatedResults > c0 * c1) {
                    estimatedResults = c0 * c1
                }
            }
            res.count = (estimatedResults + 0.9999).toInt()
            for (v in columns[1]) {
                tmpMap[v] = a.values[v]!!
            }
            for (v in columns[2]) {
                tmpMap[v] = b.values[v]!!
            }
            for ((k, v) in tmpMap) {
                if (v > res.count) {
                    res.values[k] = res.count
                } else {
                    res.values[k] = v
                }
            }
            return res
        }
    }
    override /*suspend*/ fun calculateHistogram(): HistogramResult {
        return mergeHistograms(children[0].getHistogram(), children[1].getHistogram(), optional)
    }
}
