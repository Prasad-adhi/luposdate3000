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
package lupos.operator.arithmetik.singleinput

import lupos.operator.arithmetik.AOPAggregationBase
import lupos.operator.arithmetik.AOPBase
import lupos.operator.logical.IOPBase
import lupos.operator.logical.IQuery
import lupos.operator.logical.iterator.ColumnIteratorAggregate
import lupos.operator.logical.iterator.IteratorBundle
import lupos.s03resultRepresentation.ValueDefinition
import lupos.shared.EOperatorIDExt
import lupos.shared.XMLElement
import kotlin.jvm.JvmField

public class AOPAggregationSAMPLE public constructor(query: IQuery, @JvmField public val distinct: Boolean, childs: Array<AOPBase>) : AOPAggregationBase(query, EOperatorIDExt.AOPAggregationSAMPLEID, "AOPAggregationSAMPLE", Array<IOPBase>(childs.size) { childs[it] }) {
    override /*suspend*/ fun toXMLElement(partial: Boolean): XMLElement = super.toXMLElement(partial).addAttribute("distinct", "" + distinct)
    override fun toSparql(): String {
        if (distinct) {
            return "SAMPLE(DISTINCT " + children[0].toSparql() + ")"
        }
        return "SAMPLE(" + children[0].toSparql() + ")"
    }

    override fun equals(other: Any?): Boolean = other is AOPAggregationSAMPLE && distinct == other.distinct && children.contentEquals(other.children)
    override fun createIterator(row: IteratorBundle): ColumnIteratorAggregate {
        val res = ColumnIteratorAggregate()
        val child = (children[0] as AOPBase).evaluate(row)
        res.evaluate = {
            val value = child()
            res.value = value
            res.evaluate = {
            }
        }
        return res
    }

    override fun evaluate(row: IteratorBundle): () -> ValueDefinition {
        val tmp = row.columns["#$uuid"]!! as ColumnIteratorAggregate
        return {
            tmp.value
        }
    }

    override fun cloneOP(): IOPBase = AOPAggregationSAMPLE(query, distinct, Array<AOPBase>(children.size) { (children[it].cloneOP()) as AOPBase })
}
