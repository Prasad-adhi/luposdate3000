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

package lupos.fileformat

import lupos.s00misc.ByteArrayWrapper

public class DictionaryIntermediateRow(public val id: Int, public val data: ByteArrayWrapper) : Comparable<DictionaryIntermediateRow> {
    public override operator fun compareTo(other: DictionaryIntermediateRow): Int {
        var res = data.getSize() - other.data.getSize()
        var i = 0
        while (i < data.getSize() && res == 0) {
            res = data.getBuf()[i] - other.data.getBuf()[i]
            i++
        }
        return res
    }
}
