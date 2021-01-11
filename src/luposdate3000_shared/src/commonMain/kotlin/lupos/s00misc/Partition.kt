package lupos.s00misc
import kotlin.jvm.JvmField
public class Partition {
    @JvmField
    public val data: MutableMap<String, Int>
    @JvmField
    public val limit: MutableMap<String, Int>
    public companion object {
        @JvmField
        public val estimatedPartitions1: MutableMap<String, MutableSet<Int>> = mutableMapOf()
        @JvmField
        public val estimatedPartitions2: MutableMap<String, MutableSet<Int>> = mutableMapOf()
        @JvmField
        public var estimatedPartitionsValid: Boolean = false
        @JvmField
        public var default_k: Int = 128
        @JvmField
        public val queue_size: Int = 1000
    }
    public constructor() {
        data = mutableMapOf()
        limit = mutableMapOf()
    }
    public constructor(parentPartition: Partition, variableName: String, partitionNumber: Int, partitionLimit: Int) {
        val t = mutableMapOf<String, Int>()
        for ((k, v) in parentPartition.data) {
            t[k] = v
        }
        t[variableName] = partitionNumber
        data = t
        val t2 = mutableMapOf<String, Int>()
        for ((k, v) in parentPartition.limit) {
            t2[k] = v
        }
        t2[variableName] = partitionLimit
        limit = t2
    }
    public constructor(parentPartition: Partition, variableName: String) {
        val t = mutableMapOf<String, Int>()
        for ((k, v) in parentPartition.data) {
            if (k != variableName) {
                t[k] = v
            }
        }
        data = t
        val t2 = mutableMapOf<String, Int>()
        for ((k, v) in parentPartition.limit) {
            if (k != variableName) {
                t2[k] = v
            }
        }
        limit = t2
    }
    override fun equals(other: Any?): Boolean = other is Partition && data == other.data && limit == other.limit
    override fun hashCode(): Int = data.hashCode()
    public fun toXMLElement(): XMLElement {
        val res = XMLElement("Partition") //
        for ((k, v) in limit) {
            res.addContent(XMLElement("Limit").addAttribute("name", k).addAttribute("value", "$v"))
        }
        return res
    }
}
