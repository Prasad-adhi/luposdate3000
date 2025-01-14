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
package lupos.operator.arithmetik.noinput

import lupos.operator.arithmetik.AOPBase
import lupos.shared.DictionaryValueType
import lupos.shared.EOperatorIDExt
import lupos.shared.ETripleComponentTypeExt
import lupos.shared.IQuery
import lupos.shared.PartitionHelper
import lupos.shared.XMLElement
import lupos.shared.dynamicArray.ByteArrayWrapper
import lupos.shared.inline.DictionaryHelper
import lupos.shared.operator.IOPBase
import lupos.shared.operator.iterator.IteratorBundle
import lupos.shared.operator.noinput.IAOPConstant
import kotlin.jvm.JvmField

public class AOPConstant : AOPBase, IAOPConstant {
    @JvmField
    public val value: DictionaryValueType
    override fun getValue(): DictionaryValueType = value

    public constructor(query: IQuery, buffer: ByteArrayWrapper) : super(query, EOperatorIDExt.AOPConstantID, "AOPConstant", arrayOf()) {
        value = query.getDictionary().createValue(buffer)
    }

    public constructor(query: IQuery, value2: DictionaryValueType) : super(query, EOperatorIDExt.AOPConstantID, "AOPConstant", arrayOf()) {
        value = value2
    }

    override /*suspend*/ fun toXMLElement(partial: Boolean, partition: PartitionHelper): XMLElement {
        val buffer = ByteArrayWrapper()
        query.getDictionary().getValue(buffer, value)
        val res = if (DictionaryHelper.byteArrayToType(buffer) == ETripleComponentTypeExt.BLANK_NODE) {
            XMLElement("ValueBnode")
        } else {
            DictionaryHelper.byteArrayToXMLElement(buffer)
        }
        res.addAttribute("dictvalue", "" + value)
        return res
    }

    override fun toSparql(): String {
        val buffer = ByteArrayWrapper()
        query.getDictionary().getValue(buffer, value)
        val res = DictionaryHelper.byteArrayToSparql(buffer)
        return res
    }

    override fun equals(other: Any?): Boolean = other is AOPConstant && value == other.value

    override fun evaluateID(row: IteratorBundle): () -> DictionaryValueType {
        return {
            value
        }
    }

    override fun cloneOP(): IOPBase = AOPConstant(query, value)
}
