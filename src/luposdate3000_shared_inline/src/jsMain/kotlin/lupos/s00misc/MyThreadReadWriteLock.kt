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
package lupos.modulename
import lupos.s00misc.SanityCheck
internal actual class MyThreadReadWriteLock {
    internal companion object {
        var uuidCounter = 0L
    }
    val uuid = uuidCounter++
    @Suppress("NOTHING_TO_INLINE") internal actual inline fun getUUID() = uuid
    var lockedRead = 0
    var lockedWrite = false
    @Suppress("NOTHING_TO_INLINE") internal actual inline fun downgradeToReadLock() {
        SanityCheck {
            if (!lockedWrite) {
                throw Exception("something went wrong 1")
            }
            lockedRead = 1
            lockedWrite = false
        }
    }
    @Suppress("NOTHING_TO_INLINE") internal actual inline fun readLock() {
        SanityCheck {
            if (lockedWrite) {
                throw Exception("something went wrong 2")
            }
            lockedRead++
        }
    }
    @Suppress("NOTHING_TO_INLINE") internal actual inline fun readUnlock() {
        SanityCheck {
            if (lockedRead <= 0) {
                throw Exception("something went wrong 3")
            }
            lockedRead--
        }
    }
    @Suppress("NOTHING_TO_INLINE") internal actual inline fun writeLock() {
        SanityCheck {
            if (lockedRead > 0 || lockedWrite) {
                throw Exception("something went wrong 4 $lockedRead $lockedWrite")
            }
            lockedWrite = true
        }
    }
    @Suppress("NOTHING_TO_INLINE") internal actual inline fun tryWriteLock(): Boolean {
        SanityCheck {
            if (lockedRead > 0 || lockedWrite) {
                throw Exception("something went wrong 5 $lockedRead $lockedWrite")
            }
            lockedWrite = true
        }
        return true
    }
    @Suppress("NOTHING_TO_INLINE") internal actual inline fun writeUnlock() {
        SanityCheck {
            if (!lockedWrite) {
                throw Exception("something went wrong 6")
            }
            lockedWrite = false
        }
    }
    internal actual inline fun <T> withReadLock(crossinline action: () -> T): T {
        readLock()
        try {
            return action()
        } finally {
            readUnlock()
        }
    }
    internal actual inline fun <T> withWriteLock(crossinline action: () -> T): T {
        writeLock()
        try {
            return action()
        } finally {
            writeUnlock()
        }
    }
}
