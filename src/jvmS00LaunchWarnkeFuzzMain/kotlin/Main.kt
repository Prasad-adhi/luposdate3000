import java.net.ConnectException
import java.time.Duration
import java.time.Instant
import lupos.s00misc.CoroutinesHelper
import lupos.s00misc.Coverage
import lupos.s00misc.DynamicByteArray
import lupos.s00misc.File
import lupos.s03resultRepresentation.Value
import lupos.s03resultRepresentation.ValueDefinition
import lupos.s03resultRepresentation.ValueSimpleLiteral
import lupos.s04logicalOperators.Query
import lupos.s14endpoint.Endpoint
import lupos.s14endpoint.endpointServer
import lupos.s14endpoint.EndpointServerImpl
import lupos.s16network.ServerCommunicationSend

fun main(args: Array<String>) = CoroutinesHelper.runBlock {
    endpointServer = EndpointServerImpl("localhost")
    ServerCommunicationSend.start(null)
    mapOf(/*return*/
/*return*/testDictionaryVarName to "DictionaryVarName.txt",
            /*return*/testDictionaryValue to "DictionaryValue.txt"
    ).forEach { (k, v) ->
        File("resources/$v").forEachLine {
            k.add(it)
        }
    }
    val query = Query()
    testDictionaryValue.forEach {
        try {
            val tmp = ValueDefinition(it)
            if (testDictionaryValueTyped[ValueToID(tmp)] == null) {
                testDictionaryValueTyped[ValueToID(tmp)] = ThreadSafeMutableList()
            }
            testDictionaryValueTyped[ValueToID(tmp)]!!.add(it!!)
        } catch (e: Throwable) {
            testDictionaryValueTyped[ValueEnum.ValueSimpleLiteral]!!.add("\"" + it!! + "\"")
        }
    }
    var testcase = TestCase.ETripleStore
    var datasize = 16
    if (args.size > 0) {
        JenaRequest.db = args[0]
        JenaRequest.dbwascreated = true
        if (args.size > 1) {
            datasize = args[1].toInt()
            if (args.size > 2) {
                try {
                    val x = args[2].toInt()
                    testcase = TestCase.values()[x]
                } catch (e: Throwable) {
                    testcase = TestCase.valueOf(args[2])
                }
            }
        }
    }
    val workdir = "javafuzz/${JenaRequest.db}"
    var timepoint = Instant.now()
    val randomFile = File("/dev/urandom")
    val fis = FileInputStream(randomFile)
    val fileChannel = fis.getChannel()
    var currentSize = 0
    var testnumber = 0
    var counter = datasize
    var errors = 0
    while (true) {
        testnumber++
        counter--
        if (counter == 0) {
            if (datasize < 1000) {
                datasize += 2
            } else {
                datasize = (datasize * 1.01).toInt() + 1
            }
            counter = datasize
        }
        val bytebuffer = ByteBuffer.allocate(datasize)
        currentSize = fileChannel.read(bytebuffer)
        val data = bytebuffer.array()
        val input = DynamicByteArray(data, currentSize)
        try {
            for (testcase in TestCase.values()) {
                runBlocking {
                    withTimeout(1000, {
                        val position = input.pos
                        testcase.action(TestRandom(input!!))
                        input.pos = position
                    })
                }
            }
            val timepointNext2 = Instant.now()
            val elapsed2 = Duration.between(timepoint, timepointNext2)
            timepoint = timepointNext2
            if (testnumber % 1000 == 0) {
                println("test ${JenaRequest.db} ${currentSize} $testnumber ${elapsed2.toMillis()} milliseconds")
            }
        } catch (e: ConnectException) {
            e.printStackTrace()
        } catch (e: Throwable) {
            e.printStackTrace()
            lupos.s00misc.File("mnt/crash-${data.hashCode()}").write(input)
        }
    }
}
