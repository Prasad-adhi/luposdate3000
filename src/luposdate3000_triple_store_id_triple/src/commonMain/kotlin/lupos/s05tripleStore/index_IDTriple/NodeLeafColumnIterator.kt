package lupos.s05tripleStore.index_IDTriple
import lupos.s00misc.MyReadWriteLock
import lupos.s00misc.SanityCheck
import lupos.s04logicalOperators.iterator.ColumnIterator
import kotlin.jvm.JvmField
internal abstract class NodeLeafColumnIterator(@JvmField public var node: ByteArray, @JvmField public var nodeid: Int, @JvmField public val lock: MyReadWriteLock) : ColumnIterator() {
    @JvmField
    var remaining = 0
    @JvmField
    var offset = NodeLeaf.START_OFFSET
    @JvmField
    var label = 3
    @JvmField
    var needsReset = true
    /*suspend*/ internal inline fun __init() {
        SanityCheck.println { "readLock(${lock.getUUID()}) x44" }
        lock.readLock()
        remaining = NodeShared.getTripleCount(node)
    }
    /*suspend*/ internal inline fun _close() {
        if (label == 3) {
/* "__init" was never called*/
            label = 0
            if (nodeid != NodeManager.nodeNullPointer) {
                SanityCheck.println { "Outside.refcount($nodeid)  x38" }
                NodeManager.releaseNode(nodeid)
            }
        } else if (label != 0) {
            label = 0
            if (nodeid != NodeManager.nodeNullPointer) {
                SanityCheck.println { "Outside.refcount($nodeid)  x38" }
                NodeManager.releaseNode(nodeid)
            }
            SanityCheck.println { "readUnlock(${lock.getUUID()}) x45" }
            lock.readUnlock()
        }
    }
    override /*suspend*/ fun close() {
        _close()
    }
    /*suspend*/ internal inline fun updateRemaining(crossinline setDone: () -> Unit = {}) {
        SanityCheck.check { remaining > 0 }
        remaining--
        if (remaining == 0) {
            needsReset = true
            offset = NodeLeaf.START_OFFSET
            SanityCheck.println { "Outside.refcount($nodeid)  x594" }
            NodeManager.releaseNode(nodeid)
            nodeid = NodeShared.getNextNode(node)
            if (nodeid != NodeManager.nodeNullPointer) {
                SanityCheck.println { "Outside.refcount($nodeid)  x505" }
                NodeManager.getNodeLeaf(nodeid) {
                    SanityCheck.check { node != it }
                    node = it
                }
                remaining = NodeShared.getTripleCount(node)
            } else {
                _close()
                setDone()
            }
        }
        SanityCheck.check { remaining > 0 || label == 0 }
    }
}
