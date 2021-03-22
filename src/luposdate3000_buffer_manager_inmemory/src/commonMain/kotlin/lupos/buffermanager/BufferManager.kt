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

import lupos.ProguardTestAnnotation
import lupos.s00misc.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES
import lupos.s00misc.BUFFER_MANAGER_USE_FREE_LIST
import lupos.s00misc.MyReadWriteLock
import lupos.s00misc.SanityCheck
import kotlin.jvm.JvmField

public class BufferManager internal constructor(@JvmField public val name: String) {
    @ProguardTestAnnotation
    public constructor() : this("")

    /*
     * each type safe page-manager safes to its own store
     * using another layer of indirection,
     * to be able to share this code across different type-safe managers as
     * - triple store
     * - dictionary (currently not implemented)
     * - temporary result rows (currently not implemented)
     * additionally this should make it more easy to exchange this with on disk storage
     */
    private var allPages = Array(100) { ByteArray(BUFFER_MANAGER_PAGE_SIZE_IN_BYTES.toInt()) }

    private var allPagesRefcounters = IntArray(100)

    private var counter = 0

    private val lock = MyReadWriteLock()

    private val freeList = mutableListOf<Int>()
    /*suspend*/ private fun clearAssumeLocks() {
        counter = 0
        SanityCheck {
            for (i in 0 until counter) {
                SanityCheck.check({ allPagesRefcounters[i] == 0 }, { "Failed requirement allPagesRefcounters[$i] = ${allPagesRefcounters[i]} == 0" })
            }
        }
        allPages = Array(100) { ByteArray(BUFFER_MANAGER_PAGE_SIZE_IN_BYTES.toInt()) }
        allPagesRefcounters = IntArray(100)
        if (BUFFER_MANAGER_USE_FREE_LIST) {
            freeList.clear()
        }
    }

    @ProguardTestAnnotation
    public fun getNumberOfAllocatedPages(): Int = counter - freeList.size

    @ProguardTestAnnotation
    public fun getNumberOfReferencedPages(): Int {
        val res = allPagesRefcounters.sum()
        SanityCheck {
            val tmp = mutableMapOf<Int, Int>()
            for (i in 0 until counter) {
                if (allPagesRefcounters[i] != 0) {
                    tmp[i] = allPagesRefcounters[i]
                }
            }
            println("getNumberOfReferencedPages = $tmp")
        }
        return res
    }

    public fun flushPage(pageid: Int) {}
    public fun releasePage(pageid: Int) {
        SanityCheck.check({ allPagesRefcounters[pageid] > 0 }, { "Failed requirement allPagesRefcounters[$pageid] = ${allPagesRefcounters[pageid]} > 0" })
        allPagesRefcounters[pageid]--
    }

    public fun getPage(pageid: Int): ByteArray {
        // no locking required, assuming an assignment to 'allPages' is atomic
        SanityCheck {
            SanityCheck.check({ pageid < counter }, { "$pageid < $counter" })
            SanityCheck.check({ pageid >= 0 }, { "$pageid >= 0" })
            if (BUFFER_MANAGER_USE_FREE_LIST) {
                SanityCheck.check { !freeList.contains(pageid) }
            }
        }
        allPagesRefcounters[pageid]++
        return allPages[pageid]
    }

    public /*suspend*/ fun createPage(action: (ByteArray, Int) -> Unit): Unit = lock.withWriteLock {
        val pageid: Int
        if (freeList.size > 0 && BUFFER_MANAGER_USE_FREE_LIST) {
            pageid = freeList.removeAt(0)
        } else {
            if (counter == allPages.size) {
                var size = counter * 2
                if (size < 100) {
                    size = 100
                } else if (counter > 1000) {
                    size = counter + 1000
                }
                val tmp = Array(size) {
                    val res: ByteArray = if (it < counter) {
                        allPages[it]
                    } else {
                        ByteArray(BUFFER_MANAGER_PAGE_SIZE_IN_BYTES.toInt())
                    }
                    res
                }
                val tmp2 = IntArray(size)
                allPagesRefcounters.copyInto(tmp2)
                allPages = tmp
                allPagesRefcounters = tmp2
            }
            pageid = counter++
        }
        allPagesRefcounters[pageid]++
        action(allPages[pageid], pageid)
    }

    public /*suspend*/ fun deletePage(pageid: Int): Unit = lock.withWriteLock {
        SanityCheck {
            if (BUFFER_MANAGER_USE_FREE_LIST) {
                SanityCheck.check { !freeList.contains(pageid) }
            }
        }
        SanityCheck.check({ allPagesRefcounters[pageid] == 1 }, { "Failed requirement allPagesRefcounters[$pageid] = ${allPagesRefcounters[pageid]} == 1" })
        allPagesRefcounters[pageid] = 0
        if (BUFFER_MANAGER_USE_FREE_LIST) {
            freeList.add(pageid)
            if (freeList.size == counter) {
                clearAssumeLocks()
            }
        } else {
            if (counter == 0) {
                clearAssumeLocks()
            }
        }
    }

    @ProguardTestAnnotation
    public fun close() {
        SanityCheck {
            val allErrors = mutableMapOf<Int, Int>()
            for (i in 0 until counter) {
                if (allPagesRefcounters[i] != 0) {
                    allErrors[i] = allPagesRefcounters[i]
                }
            }
            SanityCheck.check({ allErrors.size == 0 }, { "$allErrors" })
        }
    }
}
