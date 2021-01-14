@file:Import("../src/luposdate3000_shared/src/commonMain/kotlin/lupos/s00misc/EOperatingSystem.kt")
@file:Import("../src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/s00misc/_Platform.kt")
@file:Import("../src/luposdate3000_shared_inline/src/jvmMain/kotlin/lupos/s00misc/_Platform.kt")
@file:CompilerOptions("-Xmulti-platform")
import java.io.BufferedOutputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
enum class ETripleComponentType {
    IRI,
    BLANK_NODE,
    STRING,
    INTEGER,
    DECIMAL,
    DOUBLE,
    BOOLEAN,
    STRING_TYPED,
    STRING_LANG,
}
// generateTriples("${Platform.getBenchmarkHome()}/luposdate-testdata/bench_4", 2097152, 16,1)
// 128 1 1 32768
// generateTriples("${Platform.getBenchmarkHome()}/luposdate-testdata/bench_4_a", 32768, 128,1,1)
fun generateTriples(folderName: String, count: Int, trash_block: Int, join_block: Int, join_count: Int): Int {
    val byteBuf = ByteArray(1)
    File(folderName).mkdirs()
    var outN3: PrintWriter = File(folderName + "/intermediate.n3").printWriter()
    var outIntermediateDictionary = DataOutputStream(BufferedOutputStream(FileOutputStream(folderName + "/intermediate.n3.dictionary")))
    var outIntermediateTriples = DataOutputStream(BufferedOutputStream(FileOutputStream(folderName + "/intermediate.n3.triples")))
    var outIntermediateDictionaryStat = File(folderName + "/intermediate.n3.stat")
    var dictCounterBnode = 0
    var dictCounterIri = 0
    var outIntermediateTriplesStatCounter = 0
    var idMapping = mutableMapOf<String, Int>()
    var idMappingCounter = 0
    fun writeToDict(s: String): Int {
        if (idMapping[s] != null) {
            return idMapping[s]!!
        }
        val tmp: ByteArray
        if (s.startsWith("_:")) {
            dictCounterBnode++
            byteBuf[0] = ETripleComponentType.BLANK_NODE.ordinal.toByte()
            tmp = s.encodeToByteArray()
        } else {
            dictCounterIri++
            byteBuf[0] = ETripleComponentType.IRI.ordinal.toByte()
            tmp = s.substring(1, s.length - 1).encodeToByteArray()
        }
        outIntermediateDictionary.writeInt(tmp.size)
        outIntermediateDictionary.write(byteBuf)
        outIntermediateDictionary.write(tmp)
        idMapping[s] = idMappingCounter++
        return idMapping[s]!!
    }
    fun appendTriple(s: String, p: String, o: String) {
        val si = writeToDict(s)
        val pi = writeToDict(p)
        val oi = writeToDict(o)
        outN3.println("$s $p $o .")
        outIntermediateTriples.writeInt(si)
        outIntermediateTriples.writeInt(pi)
        outIntermediateTriples.writeInt(oi)
        outIntermediateTriplesStatCounter++
    }
    for (i in 0 until count) {
        for (c in 0 until join_count + 1) {
            val cc = 'a' + c
            for (j in 0 until join_block) {
                appendTriple("_:$i", "<$cc>", "_:$j")
            }
        }
        for (c in 0 until join_count + 1) {
            val cc = 'a' + c
            for (j in 0 until trash_block) {
                appendTriple("_:${cc}_${i}_$j", "<$cc>", "_:$j")
            }
        }
    }
    outIntermediateTriples.close()
    File("$folderName/intermediate.n3.partitions").printWriter().use { out ->
        out.println("PSO,-1,1")
        for (i in listOf(2, 4, 8, 16)) {
            out.println("PSO,1,$i")
        }
    }
    outIntermediateDictionaryStat.printWriter().use { out ->
        out.println("total=${dictCounterBnode + dictCounterIri}")
        for (t in ETripleComponentType.values()) {
            if (t == ETripleComponentType.BLANK_NODE) {
                out.println("$t=$dictCounterBnode")
            } else if (t == ETripleComponentType.IRI) {
                out.println("$t=$dictCounterIri")
            } else {
                out.println("$t=0")
            }
        }
    }
    outN3.close()
    outIntermediateDictionary.close()
    outIntermediateTriples.close()
    return outIntermediateTriplesStatCounter
}
