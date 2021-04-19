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
package lupos.buffermanager

import lupos.modulename.Platform
import lupos.s00misc.MyReadWriteLock
import kotlin.jvm.JvmField

public object BufferManagerExt {
    @JvmField
    public val isInMemoryOnly: Boolean = true

    @JvmField
    public var allowInitFromDisk: Boolean = false

    @JvmField
    public var bufferPrefix: String = Platform.getEnv("LUPOS_HOME", "/tmp/luposdate3000/")!!

    @JvmField
    internal val managerList = mutableMapOf<String, BufferManager>()

    @JvmField
    internal val managerListLock = MyReadWriteLock()

    public fun getBuffermanager(name: String): BufferManager {
        var res: BufferManager? = null
        managerListLock.withWriteLock {
            res = managerList[name]
            if (res == null) {
                res = BufferManager(name)
                managerList[name] = res!!
            }
        }
        return res!!
    }

    public fun close() {
        managerListLock.withWriteLock {
            for (v in managerList.values) {
                v.close()
            }
        }
    }
}
