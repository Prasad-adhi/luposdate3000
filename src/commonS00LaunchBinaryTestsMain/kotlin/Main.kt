import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.Duration
import java.time.Instant
import lupos.*
import lupos.s00misc.*
import lupos.s00misc.executeBinaryTest
import lupos.s03resultRepresentation.*
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04logicalOperators.*
import lupos.s12p2p.P2P
import lupos.s14endpoint.*


fun main(args: Array<String>) = CoroutinesHelper.runBlock {
    endpointServer = EndpointServerImpl("localhost")
    P2P.start(null)
    mapOf(
            testDictionaryVarName to "DictionaryVarName.txt",
            testDictionaryValue to "DictionaryValue.txt"
    ).forEach { (k, v) ->
        File("resources/$v").forEachLine {
            k.add(it)
        }
    }
    val query = Query()
    testDictionaryValue.forEach {
        try {
            val tmp = ValueDefinition.create(it)
            if (testDictionaryValueTyped[ValueToID(tmp)] == null)
                testDictionaryValueTyped[ValueToID(tmp)] = ThreadSafeMutableList<String?>()
            testDictionaryValueTyped[ValueToID(tmp)]!!.add(it!!)
        } catch (e: Throwable) {
            testDictionaryValueTyped[ValueEnum.ValueSimpleLiteral]!!.add("\"" + it!! + "\"")
        }
        ;
    }
    val input = lupos.s00misc.File.readStdInAsDynamicByteArray()
    if (input != null) {
val pos=input.pos
	for (testcase in TestCase.values()){
input.pos=pos
	        testcase.action(input!!)
	}
    } else if (args.isEmpty()) {
        executeBinaryTests("/opt/tmpfs")
    } else {
        executeBinaryTest(args[0], true)
    }
}
