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

package lupos.s04arithmetikOperators.noinput
import lupos.s00misc.EOperatorIDExt
import lupos.s00misc.XMLElement
import lupos.s03resultRepresentation.ValueBnode
import lupos.s03resultRepresentation.ValueDefinition
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.IQuery
import lupos.s04logicalOperators.iterator.IteratorBundle
import kotlin.jvm.JvmField
public class AOPConstant : AOPBase, IAOPConstant {
    @JvmField
    public val value: Int
    override fun getValue(): Int = value
    public constructor(query: IQuery, value2: ValueDefinition) : super(query, EOperatorIDExt.AOPConstantID, "AOPConstant", arrayOf()) {
        value = query.getDictionary().createValue(value2)
    }
    public constructor(query: IQuery, value2: Int) : super(query, EOperatorIDExt.AOPConstantID, "AOPConstant", arrayOf()) {
        value = value2
    }
    override /*suspend*/ fun toXMLElement(): XMLElement {
        val tmp = query.getDictionary().getValue(value)
        return if (tmp is ValueBnode) {
            XMLElement("ValueBnode").addAttribute("dictvalue", "" + value)
        } else {
            tmp.toXMLElement()
        }
    }
    override fun toSparql(): String = query.getDictionary().getValue(value).valueToString() ?: ""
    override fun equals(other: Any?): Boolean = other is AOPConstant && value == other.value
    override fun evaluate(row: IteratorBundle): () -> ValueDefinition {
        return {
            query.getDictionary().getValue(value)
        }
    }
    override fun evaluateID(row: IteratorBundle): () -> Int {
        return {
            value
        }
    }
    override fun cloneOP(): IOPBase = this
}
