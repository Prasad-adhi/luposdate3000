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
package lupos.vk

import lupos.ProguardTestAnnotation
import lupos.buffer_manager.BufferManager
import lupos.shared.ByteArrayWrapper
import lupos.shared.SanityCheck
import lupos.shared_inline.BufferManagerPage
import kotlin.jvm.JvmField
import kotlin.math.min

public class ValueKeyStore {
    public companion object {
        public const val ID_NULL: Int = -1
        internal const val PAGEID_NULL_PTR: Int = -1
        internal const val PAGE_TYPE_LEAF: Int = 0
        internal const val PAGE_TYPE_INNER: Int = 1
        internal const val RESERVED_SPACE: Int = 16
        internal const val MIN_BRANCHING: Int = 10
    }

    @JvmField
    internal val rootPageID: Int

    @JvmField
    internal val bufferManager: BufferManager

    @JvmField
    internal var firstInnerID = ValueKeyStore.PAGEID_NULL_PTR

    @JvmField
    internal var firstLeafID = ValueKeyStore.PAGEID_NULL_PTR

    public constructor(bufferManager: BufferManager, rootPageID: Int, initFromRootPage: Boolean) {
        this.bufferManager = bufferManager
        this.rootPageID = rootPageID
        val rootPage = bufferManager.getPage(lupos.SOURCE_FILE, rootPageID)
        if (initFromRootPage) {
            firstLeafID = BufferManagerPage.readInt4(rootPage, 0)
            firstInnerID = BufferManagerPage.readInt4(rootPage, 4)
        } else {
            val writer = ValueKeyStoreWriter(bufferManager, PAGE_TYPE_LEAF)
            writer.close()
            firstLeafID = writer.firstLeafID
            var parent = writer
            while (parent.parentLayer != null) {
                parent = parent.parentLayer!!
            }
            firstInnerID = parent.firstLeafID
            BufferManagerPage.writeInt4(rootPage, 0, firstLeafID)
            BufferManagerPage.writeInt4(rootPage, 4, firstInnerID)
        }
        bufferManager.releasePage(lupos.SOURCE_FILE, rootPageID)
    }

    @ProguardTestAnnotation
    public fun delete() {
        deleteContent(firstInnerID)
        bufferManager.getPage(lupos.SOURCE_FILE, rootPageID)
        bufferManager.deletePage(lupos.SOURCE_FILE, rootPageID)
    }

    public fun deleteContent(root: Int) {
        // println("deleteContentOf $root")
        var nextStage = root
        while (nextStage != ValueKeyStore.PAGEID_NULL_PTR) {
            var node = nextStage
            var first = true
            while (node != ValueKeyStore.PAGEID_NULL_PTR) {
                val page = bufferManager.getPage(lupos.SOURCE_FILE, node)
                val nextPageID = BufferManagerPage.readInt4(page, 4)
                if (first) {
                    first = false
                    if (BufferManagerPage.readInt4(page, 0) == PAGE_TYPE_INNER) {
                        nextStage = BufferManagerPage.readInt4(page, 12)
                    } else {
                        nextStage = ValueKeyStore.PAGEID_NULL_PTR
                    }
                }
                bufferManager.deletePage(lupos.SOURCE_FILE, node)
                node = nextPageID
            }
        }
    }

    @ProguardTestAnnotation
    public fun close() {
    }

    @ProguardTestAnnotation
    public fun getIterator(buffer: ByteArrayWrapper): ValueKeyStoreIteratorLeaf {
        return ValueKeyStoreIteratorLeaf(bufferManager, firstLeafID, buffer)
    }

    public fun hasValue(data: ByteArrayWrapper): Int {
        val buffer = ByteArrayWrapper()
        var iterator = ValueKeyStoreIteratorSearch(bufferManager, firstLeafID, buffer)
        return iterator.search(data)
    }

    public fun createValue(data: ByteArrayWrapper, value: () -> Int): Int {
        var res = ID_NULL
        val buffer = ByteArrayWrapper()
        val writer = ValueKeyStoreWriter(bufferManager, PAGE_TYPE_LEAF)
        val reader = ValueKeyStoreIteratorLeaf(bufferManager, firstLeafID, buffer)
        var isInserted = false
        while (!isInserted && reader.hasNext()) {
            val id = reader.next()
            if (data == buffer) {
                res = id
                isInserted = true
                SanityCheck.check { res != ValueKeyStore.ID_NULL }
            } else if (data < buffer) {
                res = value()
                isInserted = true
                SanityCheck.check { res != ValueKeyStore.ID_NULL }
                writer.write(res, data)
            }
            writer.write(id, buffer)
        }
        while (reader.hasNext()) {
            val id = reader.next()
            writer.write(id, buffer)
        }
        if (!isInserted) {
            res = value()
            SanityCheck.check { res != ValueKeyStore.ID_NULL }
            writer.write(res, data)
        }
        reader.close()
        writer.close()
        deleteContent(firstInnerID)
        firstLeafID = writer.firstLeafID
        var parent = writer
        while (parent.parentLayer != null) {
            parent = parent.parentLayer!!
        }
        firstInnerID = parent.firstLeafID
        val rootPage = bufferManager.getPage(lupos.SOURCE_FILE, rootPageID)
        BufferManagerPage.writeInt4(rootPage, 0, firstLeafID)
        BufferManagerPage.writeInt4(rootPage, 4, firstInnerID)
        bufferManager.releasePage(lupos.SOURCE_FILE, rootPageID)
        return res
    }

    public fun createValues(hasNext: () -> Boolean, next: () -> ByteArrayWrapper, onNotFound: (ByteArrayWrapper) -> Int, onFound: (ByteArrayWrapper, Int) -> Unit) {
        if (hasNext()) {
            var data = next()
            val buffer = ByteArrayWrapper()
            val writer = ValueKeyStoreWriter(bufferManager, PAGE_TYPE_LEAF)
            val reader = ValueKeyStoreIteratorLeaf(bufferManager, firstLeafID, buffer)
            var isInserted = false
            while (!isInserted && reader.hasNext()) {
                val id = reader.next()
                if (data == buffer) {
                    onFound(buffer, id)
                    writer.write(id, buffer)
                    if (hasNext()) {
                        data = next()
                    } else {
                        isInserted = true
                    }
                } else if (data < buffer) {
                    val res = onNotFound(data)
                    writer.write(res, data)
                    isInserted = true
                    localloop@ while (hasNext()) {
                        data = next()
                        if (data < buffer) {
                            val res2 = onNotFound(data)
                            writer.write(res2, data)
                        } else {
                            isInserted = false
                            break@localloop
                        }
                    }
                    writer.write(id, buffer)
                } else {
                    writer.write(id, buffer)
                }
            }
            while (reader.hasNext()) {
                val id = reader.next()
                writer.write(id, buffer)
            }
            if (!isInserted) {
                val res = onNotFound(data)
                SanityCheck.check { res != ValueKeyStore.ID_NULL }
                writer.write(res, data)
                while (hasNext()) {
                    data = next()
                    val res2 = onNotFound(data)
                    SanityCheck.check { res2 != ValueKeyStore.ID_NULL }
                    writer.write(res2, data)
                }
            }
            reader.close()
            writer.close()
            deleteContent(firstInnerID)
            firstLeafID = writer.firstLeafID
            var parent = writer
            while (parent.parentLayer != null) {
                parent = parent.parentLayer!!
            }
            firstInnerID = parent.firstLeafID
            val rootPage = bufferManager.getPage(lupos.SOURCE_FILE, rootPageID)
            BufferManagerPage.writeInt4(rootPage, 0, firstLeafID)
            BufferManagerPage.writeInt4(rootPage, 4, firstInnerID)
            bufferManager.releasePage(lupos.SOURCE_FILE, rootPageID)
        }
    }
}

internal class ValueKeyStoreWriter {
    @JvmField
    internal var firstLeafID: Int = ValueKeyStore.PAGEID_NULL_PTR

    @JvmField
    internal var lastPageID: Int = ValueKeyStore.PAGEID_NULL_PTR

    @JvmField
    internal var pageid: Int = ValueKeyStore.PAGEID_NULL_PTR

    @JvmField
    internal var page: ByteArray

    @JvmField
    internal var offset = 12

    @JvmField
    internal val lastBuffer = ByteArrayWrapper()

    @JvmField
    internal val pageType: Int

    @JvmField
    internal val bufferManager: BufferManager

    @JvmField
    internal var parentLayer: ValueKeyStoreWriter? = null

    @JvmField
    internal var counter = 0

    @JvmField
    internal var lastChildPageID = ValueKeyStore.PAGEID_NULL_PTR

    internal constructor(bufferManager: BufferManager, pageType: Int) : this(bufferManager, pageType, ValueKeyStore.PAGEID_NULL_PTR)

    private fun writeHeader() {
        BufferManagerPage.writeInt4(page, 0, pageType)
        BufferManagerPage.writeInt4(page, 4, ValueKeyStore.PAGEID_NULL_PTR)
        if (pageType == ValueKeyStore.PAGE_TYPE_INNER) {
            offset = 16
            // println("add connection.toFirstChild $pageid -> $lastChildPageID")
            BufferManagerPage.writeInt4(page, 12, lastChildPageID)
        } else {
            // println("add connection.toFirstLeaf xxx -> $pageid")
            offset = 12
        }
        BufferManagerPage.writeInt4(page, 8, offset)
    }

    internal constructor(bufferManager: BufferManager, pageType: Int, childPageID: Int) {
        this.bufferManager = bufferManager
        this.pageType = pageType
        pageid = bufferManager.allocPage(lupos.SOURCE_FILE)
        page = bufferManager.getPage(lupos.SOURCE_FILE, pageid)
        firstLeafID = pageid
        lastChildPageID = childPageID
        writeHeader()
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun write(id: Int, buffer: ByteArrayWrapper) {
        write(ValueKeyStore.PAGEID_NULL_PTR, id, buffer)
    }

    internal fun write(childPageID: Int, id: Int, buffer: ByteArrayWrapper) {
        write(childPageID, id, buffer) {
            if (parentLayer == null) {
                parentLayer = ValueKeyStoreWriter(bufferManager, ValueKeyStore.PAGE_TYPE_INNER, firstLeafID)
            }
            parentLayer!!.write(pageid, id, buffer)
        }
        lastChildPageID = childPageID
    }

    internal inline fun write(childPageID: Int, id: Int, buffer: ByteArrayWrapper, onNextEntryPoint: () -> Unit) {
        SanityCheck.check { offset <= BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - ValueKeyStore.RESERVED_SPACE }
        SanityCheck.check { id != ValueKeyStore.ID_NULL }
        counter++
        var common = buffer.commonBytes(lastBuffer)
        buffer.copyInto(lastBuffer)
        BufferManagerPage.writeInt4(page, offset, id)
        BufferManagerPage.writeInt4(page, offset + 4, buffer.getSize())
        BufferManagerPage.writeInt4(page, offset + 8, common)
        offset += 12
        if (pageType == ValueKeyStore.PAGE_TYPE_INNER) {
            BufferManagerPage.writeInt4(page, offset, childPageID)
            // println("add connection.toChild $pageid -> $childPageID")
            offset += 4
        }
        lastPageID = pageid
        val len = buffer.getSize()
        var bufferOffset = common
        var writeEntryPoint = false
        while (bufferOffset < len) {
            val l1 = min(BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - offset, len - bufferOffset)
            if (l1 > 0) {
                BufferManagerPage.copyFrom(page, buffer.getBuf(), offset, bufferOffset, bufferOffset + l1)
                bufferOffset += l1
                offset += l1
            }
            if (offset > BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - ValueKeyStore.RESERVED_SPACE) {
                var nextpageid = bufferManager.allocPage(lupos.SOURCE_FILE)
                BufferManagerPage.writeInt4(page, 4, nextpageid)
                // println("add connection.toNext $pageid -> $nextpageid")
                bufferManager.releasePage(lupos.SOURCE_FILE, pageid)
                pageid = nextpageid
                page = bufferManager.getPage(lupos.SOURCE_FILE, nextpageid)
                writeHeader()
                writeEntryPoint = true
            }
        }
        if (writeEntryPoint) {
            BufferManagerPage.writeInt4(page, 8, offset)
            lastBuffer.setSize(0)
            if (counter > ValueKeyStore.MIN_BRANCHING) {
                counter = 0
                onNextEntryPoint()
            }
        }
    }

    internal fun close() {
        if (offset <= BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - ValueKeyStore.RESERVED_SPACE) {
            BufferManagerPage.writeInt4(page, offset, ValueKeyStore.ID_NULL)
        }
        bufferManager.releasePage(lupos.SOURCE_FILE, pageid)
        parentLayer?.close()
    }
}

public class ValueKeyStoreIteratorLeaf internal constructor(@JvmField internal val bufferManager: BufferManager, startPageID: Int, @JvmField internal val buffer: ByteArrayWrapper) {
    @JvmField
    internal var pageid = startPageID

    @JvmField
    internal var page = bufferManager.getPage(lupos.SOURCE_FILE, pageid)

    @JvmField
    internal var nextPageID = BufferManagerPage.readInt4(page, 4)

    @JvmField
    internal var offset = BufferManagerPage.readInt4(page, 8)
    public fun hasNext(): Boolean {
        if (pageid == ValueKeyStore.PAGEID_NULL_PTR) {
            return false
        } else {
            SanityCheck.check { BufferManagerPage.readInt4(page, 0) == ValueKeyStore.PAGE_TYPE_LEAF }
            return BufferManagerPage.readInt4(page, offset) != ValueKeyStore.ID_NULL
        }
    }

    public fun next(): Int {
        SanityCheck.check { BufferManagerPage.readInt4(page, 0) == ValueKeyStore.PAGE_TYPE_LEAF }
        val id = BufferManagerPage.readInt4(page, offset)
        val len = BufferManagerPage.readInt4(page, offset + 4)
        var bufferOffset = BufferManagerPage.readInt4(page, offset + 8)
        offset += 12
        buffer.setSizeCopy(len)
        while (bufferOffset < len) {
            val l1 = min(BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - offset, len - bufferOffset)
            if (l1 > 0) {
                page.copyInto(buffer.getBuf(), bufferOffset, offset, offset + l1)
                bufferOffset += l1
                offset += l1
            }
            if (offset > BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - ValueKeyStore.RESERVED_SPACE) {
                bufferManager.releasePage(lupos.SOURCE_FILE, pageid)
                pageid = nextPageID
                if (pageid == ValueKeyStore.PAGEID_NULL_PTR) {
                    SanityCheck.check { bufferOffset == len }
                } else {
                    page = bufferManager.getPage(lupos.SOURCE_FILE, pageid)
                    SanityCheck.check { BufferManagerPage.readInt4(page, 0) == ValueKeyStore.PAGE_TYPE_LEAF }
                    nextPageID = BufferManagerPage.readInt4(page, 4)
                    offset = 12
                }
            }
        }
        return id
    }

    public fun close() {
        if (pageid != ValueKeyStore.PAGEID_NULL_PTR) {
            SanityCheck.check { BufferManagerPage.readInt4(page, 0) == ValueKeyStore.PAGE_TYPE_LEAF }
            bufferManager.releasePage(lupos.SOURCE_FILE, pageid)
        }
    }
}

internal class ValueKeyStoreIteratorSearch internal constructor(@JvmField internal val bufferManager: BufferManager, startPageID: Int, @JvmField internal val buffer: ByteArrayWrapper) {
    @JvmField
    internal var pageid = startPageID

    internal inline fun search(target: ByteArrayWrapper): Int {
        // println("searching start")
        while (true) {
            // println("searching at $pageid")
            var page = bufferManager.getPage(lupos.SOURCE_FILE, pageid)
            var nextPageID = BufferManagerPage.readInt4(page, 4)
            var offset = BufferManagerPage.readInt4(page, 8)
            var pageType = BufferManagerPage.readInt4(page, 0)
            var childPageID = BufferManagerPage.readInt4(page, 12) // only valid if "pageType == ValueKeyStore.PAGE_TYPE_INNER"
            var lastID = ValueKeyStore.ID_NULL
            localloop@ while (pageid != ValueKeyStore.PAGEID_NULL_PTR && BufferManagerPage.readInt4(page, offset) != ValueKeyStore.ID_NULL) {
                var localChildPageID = childPageID
                lastID = BufferManagerPage.readInt4(page, offset)
                val len = BufferManagerPage.readInt4(page, offset + 4)
                var bufferOffset = BufferManagerPage.readInt4(page, offset + 8)
                offset += 12
                if (pageType == ValueKeyStore.PAGE_TYPE_INNER) {
                    localChildPageID = BufferManagerPage.readInt4(page, offset)
                    offset += 4
                }
                buffer.setSizeCopy(len)
                while (bufferOffset < len) {
                    val l1 = min(BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - offset, len - bufferOffset)
                    if (l1 > 0) {
                        page.copyInto(buffer.getBuf(), bufferOffset, offset, offset + l1)
                        bufferOffset += l1
                        offset += l1
                    }
                    if (offset > BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - ValueKeyStore.RESERVED_SPACE) {
                        bufferManager.releasePage(lupos.SOURCE_FILE, pageid)
                        // println("searching move to next $nextPageID")
                        pageid = nextPageID
                        if (pageid == ValueKeyStore.PAGEID_NULL_PTR) {
                            SanityCheck.check { bufferOffset == len }
                        } else {
                            page = bufferManager.getPage(lupos.SOURCE_FILE, pageid)
                            SanityCheck.check { BufferManagerPage.readInt4(page, 0) == ValueKeyStore.PAGE_TYPE_LEAF }
                            nextPageID = BufferManagerPage.readInt4(page, 4)
                            offset = 12
                        }
                    }
                }
                if (target <= buffer) {
                    break@localloop
                } else {
                    childPageID = localChildPageID
                }
            }
            if (pageid != ValueKeyStore.PAGEID_NULL_PTR) {
                bufferManager.releasePage(lupos.SOURCE_FILE, pageid)
            }
            if (pageType == ValueKeyStore.PAGE_TYPE_INNER) {
                // println("searching move to child $childPageID")
                pageid = childPageID
            } else if (target == buffer) {
                return lastID
            } else {
                return ValueKeyStore.ID_NULL
            }
        }
    }
}
