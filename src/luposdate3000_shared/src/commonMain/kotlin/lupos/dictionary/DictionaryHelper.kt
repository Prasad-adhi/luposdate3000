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
package lupos.dictionary

import lupos.s00misc.ByteArrayHelper
import lupos.s00misc.ByteArrayWrapper
import lupos.s00misc.ETripleComponentType
import lupos.s00misc.ETripleComponentTypeExt
import lupos.s03resultRepresentation.ValueBnode
import lupos.s03resultRepresentation.ValueBoolean
import lupos.s03resultRepresentation.ValueDateTime
import lupos.s03resultRepresentation.ValueDecimal
import lupos.s03resultRepresentation.ValueDefinition
import lupos.s03resultRepresentation.ValueDouble
import lupos.s03resultRepresentation.ValueError
import lupos.s03resultRepresentation.ValueFloat
import lupos.s03resultRepresentation.ValueInteger
import lupos.s03resultRepresentation.ValueIri
import lupos.s03resultRepresentation.ValueLanguageTaggedLiteral
import lupos.s03resultRepresentation.ValueSimpleLiteral
import lupos.s03resultRepresentation.ValueTypedLiteral
import lupos.s03resultRepresentation.ValueUndef

public object DictionaryHelper {

    public fun valueToByteArray(buffer: ByteArrayWrapper, value: String?) {
        valueToByteArray(buffer, ValueDefinition(value))
    }

    public fun valueToByteArray(buffer: ByteArrayWrapper, value: ValueDefinition) {
        when (value) {
            is ValueUndef -> {
                buffer.setSize(4)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.UNDEF)
            }
            is ValueError -> {
                buffer.setSize(4)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.ERROR)
            }
            is ValueBnode -> {
                val buf1 = value.value.encodeToByteArray()
                buffer.setSize(8 + buf1.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.BLANK_NODE)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 4, buf1.size)
                buf1.copyInto(buffer.getBuf(), 8)
            }
            is ValueBoolean -> {
                buffer.setSize(5)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.BOOLEAN)
                if (value.value) {
                    buffer.getBuf()[4] = 1
                } else {
                    buffer.getBuf()[4] = 0
                }
            }
            is ValueIri -> {
                val buf1 = value.iri.encodeToByteArray()
                buffer.setSize(8 + buf1.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.IRI)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 4, buf1.size)
                buf1.copyInto(buffer.getBuf(), 8)
            }
            is ValueLanguageTaggedLiteral -> {
                val buf1 = value.content.encodeToByteArray()
                val buf2 = value.language.encodeToByteArray()
                buffer.setSize(12 + buf1.size + buf2.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.STRING_LANG)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 4, buf1.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 8, buf2.size)
                buf1.copyInto(buffer.getBuf(), 12)
                buf2.copyInto(buffer.getBuf(), 12 + buf1.size)
            }
            is ValueSimpleLiteral -> {
                val buf1 = value.content.encodeToByteArray()
                buffer.setSize(8 + buf1.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.STRING)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 4, buf1.size)
                buf1.copyInto(buffer.getBuf(), 8)
            }
            is ValueTypedLiteral -> {
                val buf1 = value.content.encodeToByteArray()
                val buf2 = value.type_iri.encodeToByteArray()
                buffer.setSize(12 + buf1.size + buf2.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.STRING_TYPED)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 4, buf1.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 8, buf2.size)
                buf1.copyInto(buffer.getBuf(), 12)
                buf2.copyInto(buffer.getBuf(), 12 + buf1.size)
            }
            is ValueDecimal -> {
                val buf1 = value.value.toString().encodeToByteArray()
                val buf2 = "http://www.w3.org/2001/XMLSchema#decimal".encodeToByteArray()
                buffer.setSize(12 + buf1.size + buf2.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.STRING_TYPED)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 4, buf1.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 8, buf2.size)
                buf1.copyInto(buffer.getBuf(), 12)
                buf2.copyInto(buffer.getBuf(), 12 + buf1.size)
            }
            is ValueDouble -> {
                val buf1 = value.value.toString().encodeToByteArray()
                val buf2 = "http://www.w3.org/2001/XMLSchema#double".encodeToByteArray()
                buffer.setSize(12 + buf1.size + buf2.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.STRING_TYPED)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 4, buf1.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 8, buf2.size)
                buf1.copyInto(buffer.getBuf(), 12)
                buf2.copyInto(buffer.getBuf(), 12 + buf1.size)
            }
            is ValueFloat -> {
                val buf1 = value.value.toString().encodeToByteArray()
                val buf2 = "http://www.w3.org/2001/XMLSchema#float".encodeToByteArray()
                buffer.setSize(12 + buf1.size + buf2.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.STRING_TYPED)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 4, buf1.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 8, buf2.size)
                buf1.copyInto(buffer.getBuf(), 12)
                buf2.copyInto(buffer.getBuf(), 12 + buf1.size)
            }
            is ValueInteger -> {
                val buf1 = value.value.toString().encodeToByteArray()
                val buf2 = "http://www.w3.org/2001/XMLSchema#integer".encodeToByteArray()
                buffer.setSize(12 + buf1.size + buf2.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.STRING_TYPED)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 4, buf1.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 8, buf2.size)
                buf1.copyInto(buffer.getBuf(), 12)
                buf2.copyInto(buffer.getBuf(), 12 + buf1.size)
            }
            is ValueDateTime -> {
                val s = value.valueToString()
                val buf1 = s.substring(1, s.length - "http://www.w3.org/2001/XMLSchema#dateTime".length - 5).encodeToByteArray()
                val buf2 = "http://www.w3.org/2001/XMLSchema#dateTime".encodeToByteArray()
                buffer.setSize(12 + buf1.size + buf2.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 0, ETripleComponentTypeExt.STRING_TYPED)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 4, buf1.size)
                ByteArrayHelper.writeInt4(buffer.getBuf(), 8, buf2.size)
                buf1.copyInto(buffer.getBuf(), 12)
                buf2.copyInto(buffer.getBuf(), 12 + buf1.size)
            }
        }
    }

    public fun byteArrayToType(buffer: ByteArrayWrapper): ETripleComponentType = ByteArrayHelper.readInt4(buffer.getBuf(), 0)
    public fun byteArrayToValueDefinition(buffer: ByteArrayWrapper): ValueDefinition {
        val type = byteArrayToType(buffer)
        return when (type) {
            ETripleComponentTypeExt.UNDEF -> DictionaryExt.undefValue2
            ETripleComponentTypeExt.ERROR -> DictionaryExt.errorValue2
            ETripleComponentTypeExt.BLANK_NODE -> {
                val l1 = ByteArrayHelper.readInt4(buffer.getBuf(), 4)
                val buf1 = ByteArray(l1)
                buffer.getBuf().copyInto(buf1, 0, 8, 8 + l1)
                ValueBnode(buf1.decodeToString())
            }
            ETripleComponentTypeExt.BOOLEAN -> {
                if (buffer.getBuf()[4] != 0.toByte()) {
                    DictionaryExt.booleanTrueValue2
                } else {
                    DictionaryExt.booleanFalseValue2
                }
            }
            ETripleComponentTypeExt.IRI -> {
                val l1 = ByteArrayHelper.readInt4(buffer.getBuf(), 4)
                val buf1 = ByteArray(l1)
                buffer.getBuf().copyInto(buf1, 0, 8, 8 + l1)
                ValueIri(buf1.decodeToString())
            }
            ETripleComponentTypeExt.STRING -> {
                val l1 = ByteArrayHelper.readInt4(buffer.getBuf(), 4)
                val buf1 = ByteArray(l1)
                buffer.getBuf().copyInto(buf1, 0, 8, 8 + l1)
                ValueSimpleLiteral("\"", buf1.decodeToString())
            }
            ETripleComponentTypeExt.STRING_LANG -> {
                val l1 = ByteArrayHelper.readInt4(buffer.getBuf(), 4)
                val l2 = ByteArrayHelper.readInt4(buffer.getBuf(), 8)
                val buf1 = ByteArray(l1)
                val buf2 = ByteArray(l2)
                buffer.getBuf().copyInto(buf1, 0, 12, 12 + l1)
                buffer.getBuf().copyInto(buf2, 0, 12 + l1, 12 + l1 + l2)
                ValueLanguageTaggedLiteral("\"", buf1.decodeToString(), buf2.decodeToString())
            }
            ETripleComponentTypeExt.STRING_TYPED -> {
                val l1 = ByteArrayHelper.readInt4(buffer.getBuf(), 4)
                val l2 = ByteArrayHelper.readInt4(buffer.getBuf(), 8)
                val buf1 = ByteArray(l1)
                val buf2 = ByteArray(l2)
                buffer.getBuf().copyInto(buf1, 0, 12, 12 + l1)
                buffer.getBuf().copyInto(buf2, 0, 12 + l1, 12 + l1 + l2)
                ValueTypedLiteral("\"", buf1.decodeToString(), buf2.decodeToString())
            }
            else -> throw Exception("unreachable $type")
        }
    }

    public fun byteArrayToCallback(
        value: Int,
        buffer: ByteArrayWrapper,
        onBNode: (value: Int) -> Unit,
        onBoolean: (value: Boolean) -> Unit,
        onLanguageTaggedLiteral: (content: String, lang: String) -> Unit,
        onSimpleLiteral: (content: String) -> Unit,
        onTypedLiteral: (content: String, type: String) -> Unit,
        onDecimal: (value: String) -> Unit,
        onFloat: (value: Double) -> Unit,
        onDouble: (value: Double) -> Unit,
        onInteger: (value: String) -> Unit,
        onIri: (value: String) -> Unit,
        onError: () -> Unit,
        onUndefined: () -> Unit
    ) {
        val type = ByteArrayHelper.readInt4(buffer.getBuf(), 0)
        when (type) {
            ETripleComponentTypeExt.UNDEF -> onUndefined()
            ETripleComponentTypeExt.ERROR -> onError()
            ETripleComponentTypeExt.BLANK_NODE -> onBNode(value)
            ETripleComponentTypeExt.BOOLEAN -> onBoolean(buffer.getBuf()[4] != 0.toByte())
            ETripleComponentTypeExt.IRI -> {
                val l1 = ByteArrayHelper.readInt4(buffer.getBuf(), 4)
                val buf1 = ByteArray(l1)
                buffer.getBuf().copyInto(buf1, 0, 8, 8 + l1)
                onIri(buf1.decodeToString())
            }
            ETripleComponentTypeExt.STRING -> {
                val l1 = ByteArrayHelper.readInt4(buffer.getBuf(), 4)
                val buf1 = ByteArray(l1)
                buffer.getBuf().copyInto(buf1, 0, 8, 8 + l1)
                onSimpleLiteral(buf1.decodeToString())
            }
            ETripleComponentTypeExt.STRING_LANG -> {
                val l1 = ByteArrayHelper.readInt4(buffer.getBuf(), 4)
                val l2 = ByteArrayHelper.readInt4(buffer.getBuf(), 8)
                val buf1 = ByteArray(l1)
                val buf2 = ByteArray(l2)
                buffer.getBuf().copyInto(buf1, 0, 12, 12 + l1)
                buffer.getBuf().copyInto(buf2, 0, 12 + l1, 12 + l1 + l2)
                onLanguageTaggedLiteral(buf1.decodeToString(), buf2.decodeToString())
            }
            ETripleComponentTypeExt.STRING_TYPED -> {
                val l1 = ByteArrayHelper.readInt4(buffer.getBuf(), 4)
                val l2 = ByteArrayHelper.readInt4(buffer.getBuf(), 8)
                val buf1 = ByteArray(l1)
                val buf2 = ByteArray(l2)
                buffer.getBuf().copyInto(buf1, 0, 12, 12 + l1)
                buffer.getBuf().copyInto(buf2, 0, 12 + l1, 12 + l1 + l2)
                onTypedLiteral(buf1.decodeToString(), buf2.decodeToString())
            }
        }
    }
}
