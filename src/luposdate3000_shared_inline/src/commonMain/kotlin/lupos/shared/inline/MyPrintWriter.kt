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
package lupos.shared.inline

import lupos.shared.DictionaryValueType
import lupos.shared.IMyOutputStream

internal expect class MyPrintWriter(hasBuffer: Boolean = true) : IMyOutputStream {
    fun clearBuffer()
    override fun toString(): String
    override fun println(x: String)
    override fun print(x: String)
    fun println(x: Boolean)
    override fun print(x: Boolean)
    fun println(x: Int)
    override fun print(x: Int)
    fun println(x: Double)
    override fun print(x: Double)
    override fun println()
    override fun close()
    override fun flush()
    override fun writeInt(value: Int): Unit
    override fun writeDictionaryValueType(value: DictionaryValueType): Unit
    override fun writeLong(value: Long): Unit
    override fun write(buf: ByteArray): Unit
    override fun write(buf: ByteArray, len: Int): Unit
}
