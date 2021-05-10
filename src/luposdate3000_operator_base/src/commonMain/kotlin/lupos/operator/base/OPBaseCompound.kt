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
package lupos.operator.base

import lupos.shared.*
import lupos.shared.SanityCheck
import lupos.shared.operator.HistogramResult
import lupos.shared.operator.IOPBase
import kotlin.jvm.JvmField

public class OPBaseCompound public constructor(query: IQuery, children: Array<IOPBase>, @JvmField public val columnProjectionOrder: List<List<String>>) : OPBase(query, EOperatorIDExt.OPCompoundID, "OPBaseCompound", children, ESortPriorityExt.PREVENT_ANY) {
    override fun getPartitionCount(variable: String): Int = SanityCheck.checkUnreachable()
    override fun cloneOP(): IOPBase = OPBaseCompound(query, getChildren().map { it.cloneOP() }.toTypedArray(), columnProjectionOrder)
    override /*suspend*/ fun toXMLElement(partial: Boolean): XMLElement {
        val res = super.toXMLElement(partial)
        val x = XMLElement("columnProjectionOrders")
        res.addContent(x)
        for (cpos in columnProjectionOrder) {
            val y = XMLElement("columnProjectionOrder")
            x.addContent(y)
            for (cpo in cpos) {
                val z = XMLElement("columnProjectionOrderElement")
                y.addContent(z)
                z.addContent(cpo)
            }
        }
        return res
    }

    override /*suspend*/ fun calculateHistogram(): HistogramResult = SanityCheck.checkUnreachable()

    override fun equals(other: Any?): Boolean {
        if (other !is OPBaseCompound) {
            return false
        }
        if (getChildren().size != other.getChildren().size) {
            return false
        }
        for (i in getChildren().indices) {
            if (getChildren()[i] != other.getChildren()[i]) {
                return false
            }
        }
        if (columnProjectionOrder.size != other.columnProjectionOrder.size) {
            return false
        }
        for (i in columnProjectionOrder.indices) {
            if (columnProjectionOrder[i].size != other.columnProjectionOrder[i].size) {
                return false
            }
            for (j in columnProjectionOrder[i].indices) {
                if (columnProjectionOrder[i][j] != other.columnProjectionOrder[i][j]) {
                    return false
                }
            }
        }
        return true
    }

    override fun toSparqlQuery(): String = toSparql()
    override fun toSparql(): String {
        val res = StringBuilder()
        for (c in getChildren()) {
            res.append(c.toSparqlQuery() + "\n")
        }
        return res.toString()
    }
}
