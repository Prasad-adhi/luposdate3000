package lupos.s05tripleStore.index_IDTriple

import kotlin.jvm.JvmField
import lupos.s00misc.File
import lupos.s00misc.SanityCheck
import lupos.s01io.BufferManager

object NodeManager {
    const val nodeTypeLeaf = 1
    const val nodeTypeInner = 2
    const val nodeNullPointer = -1

    @JvmField
    val bufferManager = BufferManager("id_triples")
    fun debug() {
    }

    suspend fun safeToFolder() {
        SanityCheck.println { "debug NodeManager saving to folder '${BufferManager.bufferPrefix + "nodemanager/"}'" }
        File(BufferManager.bufferPrefix + "nodemanager/").mkdirs()
        debug()
        bufferManager.safeToFolder()
    }

    suspend    /*inline*/ fun loadFromFolder() {
        SanityCheck.println({ "debug NodeManager loading from folder '${BufferManager.bufferPrefix + "nodemanager/"}'" })
        bufferManager.loadFromFolder()
    }

    inline fun referenceNode(nodeid: Int) {
        bufferManager.referencePage(nodeid)
    }

    inline fun releaseNode(nodeid: Int) {
        bufferManager.releasePage(nodeid)
    }

    inline fun getNodeLeaf(nodeid: Int, crossinline actionLeaf: (ByteArray) -> Unit) {
        SanityCheck.println({ "debug NodeManager getNode ${nodeid.toString(16)}" })
        val node = bufferManager.getPage(nodeid)
        actionLeaf(node)
    }

    inline fun getNodeInner(nodeid: Int, crossinline actionInner: (ByteArray) -> Unit) {
        SanityCheck.println({ "debug NodeManager getNode ${nodeid.toString(16)}" })
        val node = bufferManager.getPage(nodeid)
        actionInner(node)
    }

    inline fun getNodeAny(nodeid: Int, crossinline actionLeaf: (ByteArray) -> Unit, crossinline actionInner: (ByteArray) -> Unit) {
        SanityCheck.println({ "debug NodeManager getNode ${nodeid.toString(16)}" })
        val node = bufferManager.getPage(nodeid)
        when (NodeShared.getNodeType(node)) {
            nodeTypeInner -> {
                actionInner(node)
            }
            nodeTypeLeaf -> {
                actionLeaf(node)
            }
            else -> {
                SanityCheck.checkUnreachable()
            }
        }
    }

    suspend inline fun getNodeAnySuspended(nodeid: Int, crossinline actionLeaf: suspend (ByteArray) -> Unit, crossinline actionInner: suspend (ByteArray) -> Unit) {
        SanityCheck.println({ "debug NodeManager getNode ${nodeid.toString(16)}" })
        val node = bufferManager.getPage(nodeid)
        when (NodeShared.getNodeType(node)) {
            nodeTypeInner -> {
                actionInner(node)
            }
            nodeTypeLeaf -> {
                actionLeaf(node)
            }
            else -> {
                SanityCheck.checkUnreachable()
            }
        }
    }

    /*inline*/ suspend fun allocateNodeLeaf(/*crossinline*/ action: suspend (ByteArray, Int) -> Unit) {
        SanityCheck.println({ "NodeManager.allocateNodeLeaf A" })
        var node: ByteArray? = null
        var nodeid = -1
        bufferManager.createPage { p, i ->
            node = p
            nodeid = i
        }
        SanityCheck.println({ "debug NodeManager allocateNodeLeafB ${nodeid.toString(16)}" })
        NodeShared.setNodeType(node!!, nodeTypeLeaf)
        NodeShared.setNextNode(node!!, nodeNullPointer)
        NodeShared.setTripleCount(node!!, 0)
        action(node!!, nodeid)
        SanityCheck.println({ "NodeManager.allocateNodeLeaf B" })
    }

    /*inline*/ suspend fun allocateNodeInner(/*crossinline*/ action: suspend (ByteArray, Int) -> Unit) {
        SanityCheck.println({ "NodeManager.allocateNodeInner A" })
        var node: ByteArray? = null
        var nodeid = -1
        bufferManager.createPage { p, i ->
            node = p
            nodeid = i
        }
        SanityCheck.println({ "debug NodeManager allocateNodeInnerB ${nodeid.toString(16)}" })
        NodeShared.setNodeType(node!!, nodeTypeInner)
        NodeShared.setNextNode(node!!, nodeNullPointer)
        NodeShared.setTripleCount(node!!, 0)
        action(node!!, nodeid)
        SanityCheck.println({ "NodeManager.allocateNodeInner B" })
    }

    /*inline*/ suspend fun freeNode(nodeid: Int) {
        SanityCheck.println({ "NodeManager.freeNode A" })
        SanityCheck.println({ "debug NodeManager freeNode ${nodeid.toString(16)}" })
        bufferManager.deletePage(nodeid)
        SanityCheck.println({ "NodeManager.freeNode B" })
    }

    /*inline*/    suspend fun freeNodeAndAllRelated(nodeid: Int) {
        SanityCheck.println({ "Outside.refcount($nodeid) ${NodeManager.bufferManager.allPagesRefcounters[nodeid]} x70" })
        releaseNode(nodeid)
        freeNodeAndAllRelatedInternal(nodeid)
    }

    suspend fun freeNodeAndAllRelatedInternal(nodeid: Int) {
        SanityCheck.println({ "NodeManager.freeNodeAndAllRelatedInternal A" })
        SanityCheck.println({ "debug NodeManager freeNodeAndAllRelatedInternal ${nodeid.toString(16)}" })
        if (nodeid != nodeNullPointer) {
            var node: ByteArray? = null
            SanityCheck.println({ "Outside.refcount($nodeid) ${NodeManager.bufferManager.allPagesRefcounters[nodeid]} x16" })
            getNodeAny(nodeid, {
            }, { n ->
                node = n
            })
            if (node != null) {
                NodeInner.forEachChild(node!!, {
                    freeNodeAndAllRelatedInternal(it)
                })
            }
            freeNode(nodeid)
        }
        SanityCheck.println({ "NodeManager.freeNodeAndAllRelatedInternal B" })
    }

    suspend inline fun freeAllLeaves(nodeid: Int) {
        SanityCheck.println({ "NodeManager.freeAllLeaves A" })
        SanityCheck.println({ "debug NodeManager freeAllLeaves ${nodeid.toString(16)}" })
        var pageid = nodeid
        while (pageid != nodeNullPointer) {
            var id = pageid
            SanityCheck.println({ "Outside.refcount($pageid) ${NodeManager.bufferManager.allPagesRefcounters[pageid]} x01" })
            getNodeLeaf(pageid, { node ->
                val tmp = NodeShared.getNextNode(node)
                pageid = tmp
            })
            freeNode(id)
        }
        SanityCheck.println({ "NodeManager.freeAllLeaves B" })
    }

    suspend fun freeAllInnerNodes(nodeid: Int) {
        SanityCheck.println({ "NodeManager.freeAllInnerNodes A" })
        SanityCheck.println({ "debug NodeManager freeAllInnerNodes ${nodeid.toString(16)}" })
        if (nodeid != nodeNullPointer) {
            var node: ByteArray? = null
            SanityCheck.println({ "Outside.refcount($nodeid) ${NodeManager.bufferManager.allPagesRefcounters[nodeid]} x17" })
            getNodeAny(nodeid, {
            }, { n ->
                node = n
            })
            if (node != null) {
                NodeInner.forEachChild(node!!, {
                    freeAllInnerNodes(it)
                })
                freeNode(nodeid)
            }
        }
        SanityCheck.println({ "NodeManager.freeAllInnerNodes B" })
    }
}
