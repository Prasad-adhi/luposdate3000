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
package lupos.triple_store_manager

import lupos.buffer_manager.BufferManagerExt
import lupos.operator.base.Query
import lupos.shared.DictionaryValueHelper
import lupos.shared.DictionaryValueTypeArray
import lupos.shared.EIndexPattern
import lupos.shared.EIndexPatternExt
import lupos.shared.EIndexPatternHelper
import lupos.shared.EModifyType
import lupos.shared.EModifyTypeExt
import lupos.shared.EPartitionModeExt
import lupos.shared.IBufferManager
import lupos.shared.IMyInputStream
import lupos.shared.IQuery
import lupos.shared.ITripleStoreDescriptionFactory
import lupos.shared.ITripleStoreIndexDescription
import lupos.shared.LuposGraphName
import lupos.shared.LuposHostname
import lupos.shared.LuposStoreKey
import lupos.shared.Luposdate3000Instance
import lupos.shared.SanityCheck
import lupos.shared.TripleStoreIndex
import lupos.shared.TripleStoreManager
import lupos.shared.XMLElement
import lupos.shared.inline.BufferManagerPage
import lupos.shared.inline.ByteArrayHelper
import lupos.shared.inline.File
import lupos.triple_store_id_triple.TripleStoreIndexIDTriple
import kotlin.jvm.JvmField
import kotlin.math.min

public class TripleStoreManagerImpl public constructor(
    @JvmField
    internal var hostnames: Array<LuposHostname>, @JvmField
    internal var localhost: LuposHostname, instance2: Luposdate3000Instance
) : TripleStoreManager() {

    @JvmField
    internal var instance: Luposdate3000Instance = instance2

    @JvmField
    internal var bufferManager: IBufferManager

    @JvmField
    internal val localStores_ = mutableMapOf<LuposStoreKey, TripleStoreIndex>()

    @JvmField
    internal val metadata_ = mutableMapOf<LuposGraphName, TripleStoreDescription>()
    private lateinit var defaultTripleStoreLayout: TripleStoreDescriptionFactory

    @JvmField
    internal var rootPageID: Int = -1

    @JvmField
    internal val globalManagerRootFileName = "triple_store_manager.page"

    @JvmField
    internal val keysOnHostname_: Array<MutableSet<LuposStoreKey>>

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun localStoresGet(): MutableMap<LuposStoreKey, TripleStoreIndex> {
        return localStores_
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun metadataGet() = metadata_

    @Suppress("NOTHING_TO_INLINE")
    private inline fun toByteArray(): ByteArray {
        var size = 8
        for (k in localStores_.keys) {
            val buf = k.encodeToByteArray()
            size += 8 + buf.size
        }
        for ((name, description) in metadata_) {
            val buf = name.encodeToByteArray()
            size += 8 + buf.size
            for (index in description.indices) {
                val buf2 = index.toByteArray()
                size += 4 + buf2.size
            }
        }
        val buffer = ByteArray(size)
        var off = 0
        ByteArrayHelper.writeInt4(buffer, off, localStores_.size)
        off += 4
        for ((key, index) in localStores_) {
            val buf = key.encodeToByteArray()
            ByteArrayHelper.writeInt4(buffer, off, index.getRootPageID())
            off += 4
            ByteArrayHelper.writeInt4(buffer, off, buf.size)
            off += 4
            buf.copyInto(buffer, off)
            off += buf.size
        }
        ByteArrayHelper.writeInt4(buffer, off, metadata_.size)
        off += 4
        for ((name, description) in metadata_) {
            val buf = name.encodeToByteArray()
            ByteArrayHelper.writeInt4(buffer, off, buf.size)
            off += 4
            buf.copyInto(buffer, off)
            off += buf.size
            ByteArrayHelper.writeInt4(buffer, off, description.indices.size)
            off += 4
            for (index in description.indices) {
                val buf2 = index.toByteArray()
                ByteArrayHelper.writeInt4(buffer, off, buf2.size)
                off += 4
                buf2.copyInto(buffer, off)
                off += buf2.size
            }
        }
        return buffer
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun initFromByteArray(buffer: ByteArray) {
        var off = 0
        val l1 = ByteArrayHelper.readInt4(buffer, off)
        off += 4
        for (i in 0 until l1) {
            val pageid = ByteArrayHelper.readInt4(buffer, off)
            off += 4
            val l2 = ByteArrayHelper.readInt4(buffer, off)
            off += 4
            val buf = ByteArray(l2)
            buffer.copyInto(buf, 0, off, off + l2)
            off += l2
            val store = TripleStoreIndexIDTriple(pageid, true, instance)
            val key = buf.decodeToString()
            localStores_[key] = store
        }
        val l3 = ByteArrayHelper.readInt4(buffer, off)
        off += 4
        for (i in 0 until l3) {
            val l4 = ByteArrayHelper.readInt4(buffer, off)
            off += 4
            val buf = ByteArray(l4)
            buffer.copyInto(buf, 0, off, off + l4)
            off += l4
            val name = buf.decodeToString()
            val l5 = ByteArrayHelper.readInt4(buffer, off)
            off += 4
            val description = TripleStoreDescriptionFactory(instance)
            for (j in 0 until l5) {
                val l6 = ByteArrayHelper.readInt4(buffer, off)
                off += 4
                val buf2 = ByteArray(l6)
                buffer.copyInto(buf2, 0, off, off + l6)
                off += l6
                description.addIndex { it.initFromByteArray(buf2) }
            }
            metadata_[name] = description.build(instance)
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun keysOnHostnameAdd(hostidx: Int, key: LuposStoreKey) {
        keysOnHostname_[hostidx].add(key)
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun localStoresAdd(key: LuposStoreKey, tripleStore: TripleStoreIndex) {
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:182"/*SOURCE_FILE_END*/ }, { localStores_[key] == null })
        localStores_[key] = tripleStore
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun localStoresRemove(key: LuposStoreKey) {
        val tripleStore = localStores_[key]!!
        tripleStore.delete()
        localStores_.remove(key)
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun metadataAdd(name: LuposGraphName, tripleStore: TripleStoreDescription) {
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:195"/*SOURCE_FILE_END*/ }, { metadata_[name] == null })
        metadata_[name] = tripleStore
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun metadataRemove(name: LuposGraphName) {
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:201"/*SOURCE_FILE_END*/ }, { metadata_[name] != null || name == DEFAULT_GRAPH_NAME }, { "$name :: ${metadata_.keys}" })
        metadata_.remove(name)
    }

    init {
        this.bufferManager = instance.bufferManager!!
        keysOnHostname_ = Array(hostnames.size) { mutableSetOf() }
        println("allocation TripleStoreManagerImpl on $localhost")
    }

    public override fun initialize() {
        val file = File(instance.BUFFER_HOME + globalManagerRootFileName)
        var pageid = -1
        if (BufferManagerExt.allowInitFromDisk && instance.allowInitFromDisk && file.exists()) {
            file.withInputStream {
                pageid = it.readInt()
            }
            initialize(bufferManager, pageid, true)
        } else {
            pageid = bufferManager.allocPage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:223"/*SOURCE_FILE_END*/)
            if (BufferManagerExt.allowInitFromDisk) {
                file.withOutputStream {
                    it.writeInt(pageid)
                }
            }
            initialize(bufferManager, pageid, false)
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun initFromPageID() {
        var pageid = rootPageID
        var page = bufferManager.getPage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:236"/*SOURCE_FILE_END*/, pageid)
        var nextid = BufferManagerPage.readInt4(page, 0)
        val size = BufferManagerPage.readInt4(page, 4)
        val buffer = ByteArray(size)
        var off = 0
        val len = min(size - off, BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - 8)
        page.copyInto(buffer, off, 8, 8 + len)
        off += len
        bufferManager.releasePage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:244"/*SOURCE_FILE_END*/, pageid)
        while (off < size) {
            pageid = nextid
            page = bufferManager.getPage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:247"/*SOURCE_FILE_END*/, pageid)
            nextid = BufferManagerPage.readInt4(page, 0)
            val len2 = min(size - off, BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - 4)
            page.copyInto(buffer, off, 4, 4 + len2)
            off += len2
            bufferManager.releasePage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:252"/*SOURCE_FILE_END*/, pageid)
        }
        initFromByteArray(buffer)
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun deleteAllPagesExceptRootID() {
        var pageid = rootPageID
        var page = bufferManager.getPage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:260"/*SOURCE_FILE_END*/, pageid)
        var nextid = BufferManagerPage.readInt4(page, 0)
        val size = BufferManagerPage.readInt4(page, 4)
        var off = 0
        val len = min(size - off, BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - 8)
        off += len
        bufferManager.releasePage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:266"/*SOURCE_FILE_END*/, pageid)
        while (off < size) {
            pageid = nextid
            page = bufferManager.getPage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:269"/*SOURCE_FILE_END*/, pageid)
            nextid = BufferManagerPage.readInt4(page, 0)
            val len2 = min(size - off, BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - 4)
            off += len2
            bufferManager.deletePage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:273"/*SOURCE_FILE_END*/, pageid)
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun writeToPageID() {
        val buffer = toByteArray()
        var pageid = rootPageID
        var page = bufferManager.getPage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:281"/*SOURCE_FILE_END*/, pageid)
        val size = buffer.size
        BufferManagerPage.writeInt4(page, 4, size)
        var off = 0
        val len = min(size - off, BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - 8)
        BufferManagerPage.copyFrom(page, buffer, 8, off, off + len)
        off += len
        while (off < size) {
            val pageid2 = bufferManager.allocPage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:289"/*SOURCE_FILE_END*/)
            BufferManagerPage.writeInt4(page, 0, pageid2)
            bufferManager.releasePage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:291"/*SOURCE_FILE_END*/, pageid)
            pageid = pageid2
            page = bufferManager.getPage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:293"/*SOURCE_FILE_END*/, pageid2)
            val len2 = min(size - off, BufferManagerPage.BUFFER_MANAGER_PAGE_SIZE_IN_BYTES - 4)
            BufferManagerPage.copyFrom(page, buffer, 4, off, off + len2)
            off += len2
        }
        bufferManager.releasePage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:298"/*SOURCE_FILE_END*/, pageid)
    }

    public fun initialize(bufferManager: IBufferManager, rootPageID: Int, initFromRootPage: Boolean) {
        this.bufferManager = bufferManager
        this.rootPageID = rootPageID
        resetDefaultTripleStoreLayout()
        if (initFromRootPage) {
            initFromPageID()
        } else {
            writeToPageID()
        }
    }

    override fun close() {
        for (v in localStores_.values) {
            v.close()
        }
        deleteAllPagesExceptRootID()
        writeToPageID()
    }

    override fun delete() {
        for (v in localStores_.values) {
            v.delete()
        }
        deleteAllPagesExceptRootID()
        bufferManager.getPage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:325"/*SOURCE_FILE_END*/, rootPageID)
        bufferManager.deletePage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:326"/*SOURCE_FILE_END*/, rootPageID)
    }

    public override fun getLocalhost(): LuposHostname = localhost
    public override fun debugAllLocalStoreContent() {
        File("${localhost.replace(":", "_")}.metadata").withOutputStream { out ->
            for ((k, v) in metadata_) {
                out.println("graphname : '$k'")
                val meta = v.toMetaString().split("|")
                for (s in meta) {
                    out.println("    $s")
                }
            }
            out.flush()
        }
        for ((k, v) in localStores_) {
            File("${localhost.replace(":", "_")}_$k.store").withOutputStream { out ->
                val query = Query(instance)
                val iter = v.getIterator(query, DictionaryValueTypeArray(0), listOf("s", "p", "o"))
                val rowiter = iter.rows
                var off = rowiter.next()
                while (off > -1) {
                    var s = ""
                    for (i in 0 until 3) {
                        s += "0x${rowiter.buf[off + i].toString(16)}"
                        if (i < 2) {
                            s += " "
                        }
                    }
                    out.println(s)
                    off = rowiter.next()
                }
                out.flush()
            }
        }
    }

    public override fun resetDefaultTripleStoreLayout() {
        if (instance.LUPOS_PARTITION_MODE == EPartitionModeExt.None) {
            defaultTripleStoreLayout = TripleStoreDescriptionFactory(instance) //
                .addIndex { it.simple(EIndexPatternExt.SPO) } //
                .addIndex { it.simple(EIndexPatternExt.SOP) } //
                .addIndex { it.simple(EIndexPatternExt.PSO) } //
                .addIndex { it.simple(EIndexPatternExt.POS) } //
                .addIndex { it.simple(EIndexPatternExt.OSP) } //
                .addIndex { it.simple(EIndexPatternExt.OPS) } //
        } else {
            var partitionCount = 4
            while (partitionCount <instance.LUPOS_PROCESS_URLS.size) {
                partitionCount *= 2
            }
            when (0) {
                0 -> { // use partitions as default
                    defaultTripleStoreLayout = TripleStoreDescriptionFactory(instance) //
                        .addIndex { it.partitionedByID(idx = EIndexPatternExt.SPO, partitionCount = partitionCount, partitionColumn = 1) } //
                        .addIndex { it.partitionedByID(idx = EIndexPatternExt.SPO, partitionCount = partitionCount, partitionColumn = 2) } //
                        .addIndex { it.partitionedByID(idx = EIndexPatternExt.SOP, partitionCount = partitionCount, partitionColumn = 1) } //
                        .addIndex { it.partitionedByID(idx = EIndexPatternExt.SOP, partitionCount = partitionCount, partitionColumn = 2) } //
                        .addIndex { it.partitionedByID(idx = EIndexPatternExt.PSO, partitionCount = partitionCount, partitionColumn = 1) } //
                        .addIndex { it.partitionedByID(idx = EIndexPatternExt.PSO, partitionCount = partitionCount, partitionColumn = 2) } //
                        .addIndex { it.partitionedByID(idx = EIndexPatternExt.POS, partitionCount = partitionCount, partitionColumn = 1) } //
                        .addIndex { it.partitionedByID(idx = EIndexPatternExt.POS, partitionCount = partitionCount, partitionColumn = 2) } //
                        .addIndex { it.partitionedByID(idx = EIndexPatternExt.OSP, partitionCount = partitionCount, partitionColumn = 1) } //
                        .addIndex { it.partitionedByID(idx = EIndexPatternExt.OSP, partitionCount = partitionCount, partitionColumn = 2) } //
                        .addIndex { it.partitionedByID(idx = EIndexPatternExt.OPS, partitionCount = partitionCount, partitionColumn = 1) } //
                        .addIndex { it.partitionedByID(idx = EIndexPatternExt.OPS, partitionCount = partitionCount, partitionColumn = 2) } //
                }
                1 -> { // use hashing as default
                    defaultTripleStoreLayout = TripleStoreDescriptionFactory(instance) //
                        .addIndex { it.partitionedByKey(idx = EIndexPatternExt.SPO, partitionCount = partitionCount) } //
                        .addIndex { it.partitionedByKey(idx = EIndexPatternExt.S_PO, partitionCount = partitionCount) } //
                        .addIndex { it.partitionedByKey(idx = EIndexPatternExt.P_SO, partitionCount = partitionCount) } //
                        .addIndex { it.partitionedByKey(idx = EIndexPatternExt.O_SP, partitionCount = partitionCount) } //
                        .addIndex { it.partitionedByKey(idx = EIndexPatternExt.SP_O, partitionCount = partitionCount) } //
                        .addIndex { it.partitionedByKey(idx = EIndexPatternExt.SO_P, partitionCount = partitionCount) } //
                        .addIndex { it.partitionedByKey(idx = EIndexPatternExt.PO_S, partitionCount = partitionCount) } //
                }
            }
        }
    }

    public override fun updateDefaultTripleStoreLayout(action: (ITripleStoreDescriptionFactory) -> Unit) {
        val factory = TripleStoreDescriptionFactory(instance)
        action(factory)
        defaultTripleStoreLayout = factory
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun getNextHostAndKey(): Pair<LuposHostname, LuposStoreKey> {
        var hostidx = 0
        for (i in 1 until hostnames.size) {
            if (keysOnHostname_[i].size < keysOnHostname_[hostidx].size) {
                hostidx = i
            }
        }
        var key = 0
        while (keysOnHostname_[hostidx].contains("$key")) {
            key++
        }
        keysOnHostnameAdd(hostidx, "$key")
        return Pair(hostnames[hostidx], "$key")
    }

    public override fun createGraph(query: IQuery, graphName: LuposGraphName) {
        createGraph(query, graphName) { it.apply(defaultTripleStoreLayout) }
    }

    public override fun remoteHistogram(tag: String, filter: DictionaryValueTypeArray): Pair<Int, Int> {
        return localStoresGet()[tag]!!.getHistogram(Query(instance), filter)
    }

    public override fun remoteModify(query: IQuery, key: String, mode: EModifyType, idx: EIndexPattern, stream: IMyInputStream) {
        val store = localStores_[key]!!
        val buf = DictionaryValueTypeArray(instance.LUPOS_BUFFER_SIZE / 4)
        val limit = buf.size - 3
        var done = false
        while (!done) {
            var i = 0
            while (i <limit) {
                val a = stream.readDictionaryValueType()
                if (a == DictionaryValueHelper.nullValue) {
                    done = true
                    break
                }
                val b = stream.readDictionaryValueType()
                val c = stream.readDictionaryValueType()
                SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:452"/*SOURCE_FILE_END*/ }, { !query.getDictionary().isLocalValue(a) })
                SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:453"/*SOURCE_FILE_END*/ }, { !query.getDictionary().isLocalValue(b) })
                SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:454"/*SOURCE_FILE_END*/ }, { !query.getDictionary().isLocalValue(c) })
                buf[i++] = a
                buf[i++] = b
                buf[i++] = c
            }
            if (mode == EModifyTypeExt.INSERT) {
                store.insertAsBulk(buf, EIndexPatternHelper.tripleIndicees[idx], i)
            } else {
                store.removeAsBulk(buf, EIndexPatternHelper.tripleIndicees[idx], i)
            }
        }
    }

    public override fun remoteModifySorted(query: IQuery, key: String, mode: EModifyType, idx: EIndexPattern, stream: IMyInputStream) {
        val store = localStores_[key]!!
        val buf = DictionaryValueTypeArray(instance.LUPOS_BUFFER_SIZE / 4)
        val limit = buf.size - 3
        var done = false
        while (!done) {
            var i = 0
            while (i <limit) {
                val a = stream.readDictionaryValueType()
                if (a == DictionaryValueHelper.nullValue) {
                    done = true
                    break
                }
                buf[i++] = a
                buf[i++] = stream.readDictionaryValueType()
                buf[i++] = stream.readDictionaryValueType()
            }
            if (mode == EModifyTypeExt.INSERT) {
                store.insertAsBulkSorted(buf, EIndexPatternHelper.tripleIndicees[idx], i)
            } else {
                store.removeAsBulkSorted(buf, EIndexPatternHelper.tripleIndicees[idx], i)
            }
        }
    }

    public override fun remoteCreateGraph(query: IQuery, graphName: LuposGraphName, origin: Boolean, meta: String?) {
        if (origin) {
            createGraph(query, graphName)
        } else {
            val graph = TripleStoreDescription(meta!!, instance)
            metadataAdd(graphName, graph)
            createGraphShared(graph)
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun createGraphShared(graph: TripleStoreDescription) {
        for (index in graph.indices) {
            for ((first, second) in index.getAllLocations()) {
                if (first == localhost) {
                    val page = bufferManager.allocPage(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_triple_store_manager/src/commonMain/kotlin/lupos/triple_store_manager/TripleStoreManagerImpl.kt:507"/*SOURCE_FILE_END*/)
                    val tripleStore = TripleStoreIndexIDTriple(page, false, instance)
                    tripleStore.debugSortOrder = EIndexPatternHelper.tripleIndicees[index.idx_set[0]]
                    localStoresAdd(second, tripleStore)
                }
            }
        }
    }

    public fun createGraph(query: IQuery, graphName: LuposGraphName, action: (ITripleStoreDescriptionFactory) -> Unit) {
        if (metadata_[graphName] != null) {
            throw Exception("graph already exist")
        }
        val factory = TripleStoreDescriptionFactory(instance)
        action(factory)
        val graph = factory.build(instance)
        metadataAdd(graphName, graph)
        for (index in graph.indices) {
            index.assignHosts()
        }
        createGraphShared(graph)
        val metadataStr = graph.toMetaString()
        for (hostname in hostnames) {
            if (hostname != localhost) {
                query.getInstance().communicationHandler!!.sendData(
                    hostname,
                    "/distributed/graph/create",
                    mapOf(
                        "name" to graphName,
                        "origin" to "false",
                        "metadata" to metadataStr,
                    ),
                    query.getTransactionID().toInt()
                )
            }
        }
    }

    public override fun resetGraph(query: IQuery, graphName: LuposGraphName) {
        dropGraph(query, graphName)
        createGraph(query, graphName) { it.apply(defaultTripleStoreLayout) }
    }

    public override fun clearGraph(query: IQuery, graphName: LuposGraphName) {
        remoteClearGraph(query, graphName, true)
    }

    public override fun remoteClearGraph(query: IQuery, graphName: LuposGraphName, origin: Boolean) {
        if (graphName == DEFAULT_GRAPH_NAME && metadata_[graphName] == null) {
            createGraph(query, graphName)
        } else {
            val graph = metadata_[graphName]
            if (graph != null) {
                for (index in graph.indices) {
                    for ((first, second) in index.getAllLocations()) {
                        if (first == localhost) {
                            localStores_[second]!!.clear()
                        } else {
                            if (origin) {
                                query.getInstance().communicationHandler!!.sendData(
                                    first,
                                    "/distributed/graph/clear",
                                    mapOf(
                                        "origin" to "false",
                                        "name" to graphName
                                    ),
                                    query.getTransactionID().toInt()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    public override fun dropGraph(query: IQuery, graphName: LuposGraphName) {
        remoteDropGraph(query, graphName, true)
    }

    public override fun remoteDropGraph(query: IQuery, graphName: LuposGraphName, origin: Boolean) {
        val graph = metadata_[graphName]
        if (graph != null) {
            for (index in graph.indices) {
                for ((first, second) in index.getAllLocations()) {
                    if (first == localhost) {
                        localStoresRemove(second)
                    } else {
                        if (origin) {
                            query.getInstance().communicationHandler!!.sendData(
                                first,
                                "/distributed/graph/drop",
                                mapOf(
                                    "origin" to "false",
                                    "name" to graphName
                                ),
                                query.getTransactionID().toInt()
                            )
                        }
                    }
                }
            }
        }
        metadataRemove(graphName)
    }

    public override fun getGraphNames(): List<LuposGraphName> {
        return getGraphNames(false)
    }

    public override fun getGraphNames(includeDefault: Boolean): List<LuposGraphName> {
        val res = mutableListOf<LuposGraphName>()
        if (includeDefault) {
            res.add(DEFAULT_GRAPH_NAME)
        }
        metadata_.keys.forEach {
            if (it != DEFAULT_GRAPH_NAME) {
                res.add(it)
            }
        }
        return res
    }

    public override fun getDefaultGraph(): TripleStoreDescription {
        return getGraph(DEFAULT_GRAPH_NAME)
    }

    public override fun getIndexFromXML(node: XMLElement): ITripleStoreIndexDescription {
        val node2 = node["TripleStoreIndexDescription"]!!
        val graph = metadata_[node2.attributes["graphName"]]!!
        val idx = EIndexPatternExt.names.indexOf(node2.attributes["pattern"]!!)
        for (index in graph.indices) {
            if (index.hasPattern(idx)) {
                when (index) {
                    is TripleStoreIndexDescriptionPartitionedByID -> {
                        if (node2.attributes["type"] == "TripleStoreIndexDescriptionPartitionedByID") {
                            if (index.partitionCount == node2.attributes["partitionCount"]!!.toInt()) {
                                if (index.partitionColumn == node2.attributes["partitionColumn"]!!.toInt()) {
                                    return index
                                }
                            }
                        }
                    }
                    is TripleStoreIndexDescriptionPartitionedByKey -> {
                        if (node2.attributes["type"] == "TripleStoreIndexDescriptionPartitionedByKey") {
                            if (index.partitionCount == node2.attributes["partitionCount"]!!.toInt()) {
                                return index
                            }
                        }
                    }
                    is TripleStoreIndexDescriptionSimple -> {
                        if (node2.attributes["type"] == "TripleStoreIndexDescriptionSimple") {
                            return index
                        }
                    }
                    else -> throw Exception("unexpected type")
                }
            }
        }
        throw Exception("desired index not found")
    }

    public override fun getGraph(graphName: LuposGraphName): TripleStoreDescription {
        if (graphName == DEFAULT_GRAPH_NAME && metadata_[graphName] == null) {
            val query = Query(instance)
            createGraph(query, graphName)
        }
        val res = metadata_[graphName]
        return res!!
    }

    public override fun remoteCommit(query: IQuery, origin: Boolean) {
        for (graph in metadata_.values) {
            for (index in graph.indices) {
                for ((first, second) in index.getAllLocations()) {
                    if (first == localhost) {
                        localStores_[second]!!.flush()
                    }
                }
            }
        }
        if (origin) {
            for (hostname in hostnames) {
                if (hostname != localhost) {
                    query.getInstance().communicationHandler!!.sendData(
                        hostname,
                        "/distributed/graph/commit",
                        mapOf(
                            "origin" to "false",
                        ),
                        query.getTransactionID().toInt()
                    )
                }
            }
        }
    }

    public override fun commit(query: IQuery) {
        remoteCommit(query, true)
    }
}