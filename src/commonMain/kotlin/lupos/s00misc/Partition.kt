package lupos.s00misc

class Partition {
    val data: Map<String, Int>

    companion object {
        var k = 12
        val queue_size = 1000
        inline fun hashFunction(v: Int): Int {
            if (v < 0) {
                return (-v) % k
            } else {
                return v % k
            }
        }
    }

    constructor() {
        data = mapOf<String, Int>()
    }

    constructor(parentPartition: Partition, variableName: String, partitionNumber: Int) {
        val t = mutableMapOf<String, Int>()
        for ((k, v) in parentPartition.data) {
            t[k] = v
        }
        t[variableName] = partitionNumber
        data = t.toMap()
    }

    constructor(parentPartition: Partition, variableName: String) {
        val t = mutableMapOf<String, Int>()
        for ((k, v) in parentPartition.data) {
            if (k != variableName) {
                t[k] = v
            }
        }
        data = t.toMap()
    }

    override fun equals(other: Any?) = other is Partition && data == other.data
    override fun hashCode() = data.hashCode()
}
