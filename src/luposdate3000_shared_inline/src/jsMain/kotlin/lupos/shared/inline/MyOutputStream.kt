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
import lupos.shared.IMyOutputStream
import lupos.shared.NotImplementedException

internal actual class MyOutputStream : IMyOutputStream {
    internal actual constructor() {}

    public actual override fun writeInt(value: Int): Unit = throw NotImplementedException("MyOutputStream", "xyz not implemented")
    public actual override fun close(): Unit = throw NotImplementedException("MyOutputStream", "xyz not implemented")
    public actual override fun flush(): Unit = throw NotImplementedException("MyOutputStream", "xyz not implemented")
    public actual override fun write(buf: ByteArray): Unit = write(buf, buf.size)
    public actual override fun write(buf: ByteArray, len: Int): Unit = throw NotImplementedException("MyOutputStream", "xyz not implemented")
    public actual override fun println(x: String): Unit = throw NotImplementedException("MyOutputStream", "xyz not implemented")
    public actual override fun print(x: String): Unit = throw NotImplementedException("MyOutputStream", "xyz not implemented")
    public actual override fun print(x: Boolean): Unit = throw NotImplementedException("MyOutputStream", "xyz not implemented")
    public actual override fun print(x: Int): Unit = throw NotImplementedException("MyOutputStream", "xyz not implemented")
    public actual override fun print(x: Double): Unit = throw NotImplementedException("MyOutputStream", "xyz not implemented")
    public actual override fun println(): Unit = throw NotImplementedException("MyOutputStream", "xyz not implemented")
}
