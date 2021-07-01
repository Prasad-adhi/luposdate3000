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
package lupos.simulator_db.luposdate3000

import lupos.shared.dynamicArray.ByteArrayWrapper
import lupos.simulator_db.IDatabasePackage

internal class MySimulatorAbstractPackage(
    val path: String,
    val params: Map<String, String>,
    val data: ByteArrayWrapper = ByteArrayWrapper()
) : IDatabasePackage {

    override fun getPackageSizeInBytes(): Int {
        return path.encodeToByteArray().size + getParamsSizeInBytes() + data.buf.size
    }

    override fun getContentLogString(): String {
        return "AbstractPck(path '$path', params $params, data $data)"
    }

    private fun getParamsSizeInBytes(): Int {
        var size = 0
        for ((key, value) in params)
            size += key.encodeToByteArray().size + value.encodeToByteArray().size
        return size
    }
}
