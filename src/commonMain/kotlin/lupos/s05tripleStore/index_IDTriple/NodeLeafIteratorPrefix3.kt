package lupos.s05tripleStore.index_IDTriple
import lupos.s00misc.Coverage


class NodeLeafIteratorPrefix3(node: NodeLeaf, prefix: IntArray) : NodeLeafIteratorPrefix(node, prefix) {
    override fun checkTooSmall(): Boolean {
        return (valueNext[0] < prefix[0]) || (valueNext[0] == prefix[0] && valueNext[1] < prefix[1]) || (valueNext[0] == prefix[0] && valueNext[1] == prefix[1] && valueNext[2] < prefix[2])
    }

    override fun checkNotTooLarge(): Boolean {
        return (valueNext[0] < prefix[0]) || (valueNext[0] == prefix[0] && valueNext[1] < prefix[1]) || (valueNext[0] == prefix[0] && valueNext[1] == prefix[1] && valueNext[2] <= prefix[2])
    }
}
