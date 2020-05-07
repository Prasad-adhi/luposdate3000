package lupos.s05tripleStore.index_IDTriple

import lupos.s00misc.Coverage

class NodeLeafIterator(var node: NodeLeaf) : TripleIterator() {
    var remaining = node.getTripleCount()
    var offset = 8
    var counter = IntArray(3)
    override fun hasNext() = remaining > 0
    override fun next(component: Int): Int {
        println("read H1 at $offset")
        var header = node.read1(offset)
        var headerA = header and 0b11000000
        if (headerA == 0b0000000) {
            counter[0] = ((header and 0b00110000) shr 4) + 1
            counter[1] = ((header and 0b00001100) shr 2) + 1
            counter[2] = ((header and 0b00000011)) + 1
        } else if (headerA == 0b01000000) {
            counter[0] = 0
            counter[1] = ((header and 0b00001100) shr 2) + 1
            counter[2] = ((header and 0b00000011)) + 1
        } else {
            require(headerA == 0b10000000)
            counter[0] = 0
            counter[1] = 0
            counter[2] = ((header and 0b00000011)) + 1
        }
        offset += 1
        for (i in 0 until 3) {
            when (counter[i]) {
                1 -> {
                    println("read d[$i]1 at $offset")
                    value[i] = value[i] xor node.read1(offset)
                }
                2 -> {
                    println("read d[$i]2 at $offset")
                    value[i] = value[i] xor node.read2(offset)
                }
                3 -> {
                    println("read d[$i]3 at $offset")
                    value[i] = value[i] xor node.read3(offset)
                }
                4 -> {
                    println("read d[$i]4 at $offset")
                    value[i] = value[i] xor node.read4(offset)
                }
            }
            offset += counter[i]
        }
        remaining--
        loop@ while (remaining == 0) {
            var nextNodeIdx = node.getNextNode()
            if (nextNodeIdx != NodeManager.NodeNullPointer) {
                val tmp = NodeManager.getNode(nextNodeIdx) as NodeLeaf
                require(node != tmp)
                node = tmp
                remaining = node.getTripleCount()
                value[0] = 0
                value[1] = 0
                value[2] = 0
                offset = 8
            } else {
                break@loop
            }
        }
        return value[component]
    }
}
