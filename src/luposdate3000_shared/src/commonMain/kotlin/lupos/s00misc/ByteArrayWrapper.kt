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
package lupos.s00misc

public class ByteArrayWrapper : Comparable<ByteArrayWrapper> {
    private var buf = ByteArray(0)
    private var size = 0
    public fun setSize(c: Int) {
        if (c > buf.size) {
            size = c
            buf = ByteArray(c)
        }
    }

    public fun getSize(): Int = size
    public fun getBuf(): ByteArray = buf
    override fun compareTo(other: ByteArrayWrapper): Int {
        var res = size - other.size
        var i = 0
        while (i < size && res == 0) {
            res = buf[i] - other.buf[i]
            i++
        }
        return res
    }

    override fun equals(other: ByteArrayWrapper): Boolean {
        return compareTo(other) == 0
    }

    override fun hashCode(): Int {
        var res = size
        for (i in 0 until size) {
            res = (res shl 1) + buf[i]
        }
        return res
    }
}
