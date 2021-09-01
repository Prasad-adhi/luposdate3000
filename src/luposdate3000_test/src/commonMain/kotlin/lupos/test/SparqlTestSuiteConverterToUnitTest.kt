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
package lupos.test
import lupos.shared.EPartitionModeExt
import lupos.shared.EPredefinedPartitionSchemesExt
import lupos.shared.EQueryDistributionModeExt
import lupos.shared.IMyOutputStream
import lupos.shared.TripleStoreManager
import lupos.shared.inline.File
import lupos.shared.inline.MyPrintWriter
import kotlin.jvm.JvmField

public class SparqlTestSuiteConverterToUnitTest(resource_folder: String) : SparqlTestSuite() {
    private val withSimulator = true
    private val onlyFirstTest = false // to reduce the number of tests, which are failing and can not be abortet by timeout
    private val minifyMode = false
/*
in minify mode all passing tests will be removed, such that the next execution will skip them.
without minify mode only the passing tests will be added
*/

    @JvmField
    internal var counter = 0

    @JvmField
    internal var lastFile: String = ""

    internal val folderCount = 20
    internal var folderCurrent = 0

    internal fun folderPathCoponent(idx: Int) = "code_gen_test_${idx.toString().padStart(2,'0')}"
    internal fun outputFolderRoot(idx: Int) = "src/luposdate3000_${folderPathCoponent(idx)}/"
    internal fun outputFolderSrcJvm(idx: Int) = "${outputFolderRoot(idx)}src/jvmMain/kotlin/lupos/${folderPathCoponent(idx)}/"
    internal fun outputFolderTestJvm(idx: Int) = "${outputFolderRoot(idx)}src/jvmTest/kotlin/lupos/${folderPathCoponent(idx)}/"
    internal fun outputFolderTestResourcesJvm(idx: Int) = "${outputFolderRoot(idx)}src/jvmTest/resources/"

    internal val duplicationDetector = mutableMapOf<String, Int>()

    @JvmField
    internal val allTests = mutableListOf<String>()

    internal val listOfFailed = mutableSetOf<String>()
    internal val listOfTimeout = mutableSetOf<String>()
    internal val listOfPassed = mutableSetOf<String>()
    internal val listOfRemoved = mutableSetOf<String>()
    internal fun isIgnored(testName: String): Boolean {
        if (minifyMode) {
            return listOfRemoved.contains(testName)
        } else {
            return !listOfPassed.contains(testName)
        }
    }
    internal fun shouldAddFunction(testName: String): Boolean {
//        if (minifyMode) {
        return !isIgnored(testName)
//        } else {
//            return true
//        }
    }
    init {
        prefixDirectory = "$resource_folder/"
        File("resources/tests/failed").forEachLine {
            listOfFailed.add(it)
        }
        File("resources/tests/passed").forEachLine {
            listOfPassed.add(it)
        }
        File("resources/tests/timeout").forEachLine {
            listOfTimeout.add(it)
        }
        if (minifyMode) {
            listOfRemoved.addAll(listOfFailed)
            listOfRemoved.addAll(listOfTimeout)
            listOfRemoved.addAll(listOfPassed)
        }
        for (idx in 0 until folderCount) {
            File(outputFolderRoot(idx)).deleteRecursively()
            File(outputFolderRoot(idx)).mkdirs()
            File(outputFolderSrcJvm(idx)).mkdirs()
            File(outputFolderTestJvm(idx)).mkdirs()
            File(outputFolderTestResourcesJvm(idx)).mkdirs()
            File("${outputFolderRoot(idx)}/module_config").withOutputStream { out ->
                out.println("disableJS=true")
            }
        }
    }

    public fun finish() {
    }

    private fun cleanFileContent(s: String): String {
        return s
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\" +\n        \"")
            .replace("\r", "\" +\n        \"")
            .replace("\t", " ")
            .replace(" +\n        \"\"", "")
            .replace("\" +\n", " \" +\n")
            .replace("\" +\n", "\\n\" +\n")
    }
    internal class GraphHelper(val graph: String, val type: String, val filename: String, val filenameoriginal: String)
    override fun parseSPARQLAndEvaluate( //
        executeJena: Boolean,
        testName: String, //
        expectedResult: Boolean, //
        queryFile: String, //
        inputDataFileName: String?, //
        resultDataFileName: String?, //
        services: List<Map<String, String>>?, //
        inputDataGraph: MutableList<MutableMap<String, String>>, //
        outputDataGraph: MutableList<MutableMap<String, String>> //
    ): Boolean {
        var testCaseName2 = testName.filter { it.isLetterOrDigit() || it == ' ' }
        var testCaseName = testCaseName2.filter { it.isLetterOrDigit() }
        val duplicate = duplicationDetector[testCaseName]
        if (duplicate != null) {
            testCaseName = testCaseName + "luposDuplicate$duplicate"
            testCaseName2 = testCaseName2 + " luposDuplicate$duplicate"
            duplicationDetector[testCaseName] = duplicate + 1
        } else {
            duplicationDetector[testCaseName] = 1
        }

        if (services != null && services.isNotEmpty()) {
            return false
        }
        var inputFile = inputDataFileName
        if (inputFile == "#keep-data#") {
            inputFile = lastFile
        }
        if (inputFile != null) {
            lastFile = inputFile
        }
        var mode = BinaryTestCaseOutputModeExt.SELECT_QUERY_RESULT
        if (resultDataFileName == null) {
            mode = BinaryTestCaseOutputModeExt.MODIFY_RESULT
        }
        val inputGraphs = mutableMapOf<String, GraphHelper>() // filename -> graphname, filetype
        if (inputFile != null) {
            inputGraphs["$testCaseName.input"] = GraphHelper(
                TripleStoreManager.DEFAULT_GRAPH_NAME,
                inputFile.substring(inputFile.lastIndexOf(".")),
                "${outputFolderTestResourcesJvm(folderCurrent)}/$testCaseName.input",
                inputFile,
            )
        }
        for (g in inputDataGraph) {
            val name = g["name"]!!
            val file = g["filename"]!!
            val outfile = "$testCaseName.input" + inputGraphs.size
            inputGraphs[outfile] = GraphHelper(
                name,
                file.substring(file.lastIndexOf(".")),
                "${outputFolderTestResourcesJvm(folderCurrent)}/$outfile",
                file,
            )
        }
        val outputGraphs = mutableMapOf<String, GraphHelper>() // filename -> graphname, filetype
        for (g in outputDataGraph) {
            val name = g["name"]!!
            val file = g["filename"]!!
            val outfile = "$testCaseName.output" + outputGraphs.size
            outputGraphs[outfile] = GraphHelper(
                name,
                file.substring(file.lastIndexOf(".")),
                "${outputFolderTestResourcesJvm(folderCurrent)}/$outfile",
                file,
            )
        }
        var targetType = "NONE"
        if (resultDataFileName != null) {
            targetType = resultDataFileName.substring(resultDataFileName.lastIndexOf("."))
        }
        counter++
        allTests.add(testCaseName)
        val queryResultIsOrdered = false
        val fileBufferPrefix = MyPrintWriter(true)
        val fileBufferTests = mutableMapOf<String/*classname*/, MyPrintWriter>()
        val fileBufferPostfix = MyPrintWriter(true)
        val distributedTest = StringBuilder()
        val distributedTestAtEnd = StringBuilder()
        var distributedTestCtr = 0
        var distributedTestAppendFlag = true
        fun appendDistributedTest(s: String, verify: Boolean) {
            if (distributedTestAppendFlag) {
                if (verify) {
                    distributedTest.appendLine("        var verifyExecuted$distributedTestCtr = 0")
                    distributedTestAtEnd.appendLine("        if (verifyExecuted$distributedTestCtr==0) {")
                    distributedTestAtEnd.appendLine("            fail(\"pck$distributedTestCtr not verified\")")
                    distributedTestAtEnd.appendLine("        }")
                }
                distributedTest.appendLine("        val pkg$distributedTestCtr = $s")
                distributedTestCtr++
                if (distributedTestCtr> 1) {
                    distributedTest.appendLine("        pkg${distributedTestCtr - 2}.onFinish = pkg${distributedTestCtr - 1}")
                }
            }
        }
        var localCounter = 0
        fun myExpectedData(counter: Int, data: String, type: String, out: IMyOutputStream) {
            out.println("        val expected$counter = MemoryTable.parseFromAny($data, $type, Query(instance))!!")
        }
        fun myActualDataOperatorGraph(counter: Int, graph: String, out: IMyOutputStream) {
            out.println("        val query$counter = Query(instance)")
            out.println("        val graph$counter = instance.tripleStoreManager!!.getGraph($graph)")
            out.println("        val operator$counter = graph$counter.getIterator(query$counter, arrayOf(AOPVariable(query$counter, \"s\"), AOPVariable(query$counter, \"p\"), AOPVariable(query$counter, \"o\")), EIndexPatternExt.SPO)")
        }
        fun myActualDataEvaluate(counter: Int, out: IMyOutputStream) {
            out.println("        val actual$counter = (LuposdateEndpoint.evaluateOperatorgraphToResultA(instance, operator$counter, buf, EQueryResultToStreamExt.MEMORY_TABLE) as List<MemoryTable>).first()")
        }
        fun myCompareData(counter: Int, out: IMyOutputStream) {
            out.println("        val buf_err$counter = MyPrintWriter()")
            out.println("        if (!expected$counter.equalsVerbose(actual$counter, ${!queryResultIsOrdered}, true, buf_err$counter)) {")
            val s = "expected$counter.toString() + \" .. \" + actual$counter.toString() + \" .. \" + buf_err$counter.toString() + \" .. \" + operator$counter"
            out.println("            fail($s)")
            out.println("        }")
        }
        fun myVerifyGraph(counter: Int, data: String, type: String, graph: String, query: String?, isDefaultGraph: Boolean, out: IMyOutputStream) {
            if (query == null) {
                myActualDataOperatorGraph(counter, graph, out)
            }
            myActualDataEvaluate(counter, out)
            myExpectedData(counter, data, type, out)
            myCompareData(counter, out)
            val q: String = query
                ?: if (isDefaultGraph) {
                    "\"SELECT ?s ?p ?o WHERE { ?s ?p ?o . }\""
                } else {
                    "\"SELECT ?s ?p ?o WHERE { GRAPH <\${$graph}> { ?s ?p ?o . }}\""
                }
            appendDistributedTest("MySimulatorTestingCompareGraphPackage($q,MemoryTable.parseFromAny($data, $type, Query(instance))!!, {verifyExecuted$distributedTestCtr++})", true)
        }
        fileBufferPrefix.println("/*")
        fileBufferPrefix.println(" * This file is part of the Luposdate3000 distribution (https://github.com/luposdate3000/luposdate3000).")
        fileBufferPrefix.println(" * Copyright (c) 2020-2021, Institute of Information Systems (Benjamin Warnke and contributors of LUPOSDATE3000), University of Luebeck")
        fileBufferPrefix.println(" *")
        fileBufferPrefix.println(" * This program is free software: you can redistribute it and/or modify")
        fileBufferPrefix.println(" * it under the terms of the GNU General Public License as published by")
        fileBufferPrefix.println(" * the Free Software Foundation, version 3.")
        fileBufferPrefix.println(" *")
        fileBufferPrefix.println(" * This program is distributed in the hope that it will be useful, but")
        fileBufferPrefix.println(" * WITHOUT ANY WARRANTY; without even the implied warranty of")
        fileBufferPrefix.println(" * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU")
        fileBufferPrefix.println(" * General Public License for more details.")
        fileBufferPrefix.println(" *")
        fileBufferPrefix.println(" * You should have received a copy of the GNU General Public License")
        fileBufferPrefix.println(" * along with this program. If not, see <http://www.gnu.org/licenses/>.")
        fileBufferPrefix.println(" */")
        fileBufferPrefix.println("package lupos.${folderPathCoponent(folderCurrent)}")
        fileBufferPrefix.println("import lupos.endpoint.LuposdateEndpoint")
        fileBufferPrefix.println("import lupos.operator.arithmetik.noinput.AOPVariable")
        fileBufferPrefix.println("import lupos.operator.base.Query")
        fileBufferPrefix.println("import lupos.parser.JsonParser")
        fileBufferPrefix.println("import lupos.parser.JsonParserObject")
        fileBufferPrefix.println("import lupos.result_format.EQueryResultToStreamExt")
        fileBufferPrefix.println("import lupos.shared.EIndexPatternExt")
        fileBufferPrefix.println("import lupos.shared.EQueryDistributionModeExt")
        fileBufferPrefix.println("import lupos.shared.Luposdate3000Config")
        fileBufferPrefix.println("import lupos.shared.Luposdate3000Instance")
        fileBufferPrefix.println("import lupos.shared.EPartitionModeExt")
        fileBufferPrefix.println("import lupos.shared.MemoryTable")
        fileBufferPrefix.println("import lupos.shared.EPredefinedPartitionSchemesExt")
        fileBufferPrefix.println("import lupos." + "shared.inline.File")
        fileBufferPrefix.println("import lupos." + "shared.inline.MyPrintWriter")
        fileBufferPrefix.println("import lupos.simulator_core.Simulation")
        fileBufferPrefix.println("import lupos.simulator_db.luposdate3000.MySimulatorTestingCompareGraphPackage")
        fileBufferPrefix.println("import lupos.simulator_db.luposdate3000.MySimulatorTestingImportPackage")
        fileBufferPrefix.println("import lupos.simulator_db.luposdate3000.MySimulatorTestingExecute")
        fileBufferPrefix.println("import lupos.simulator_db.luposdate3000.DatabaseHandle")
        fileBufferPrefix.println("import lupos.simulator_iot.log.Logger")
        fileBufferPrefix.println("import lupos.simulator_iot.SimulationRun")
        fileBufferPrefix.println("")
        fileBufferPrefix.println("import kotlin.test.Ignore")
        fileBufferPrefix.println("import kotlin.test.Test")
        fileBufferPrefix.println("import kotlin.test.fail")
        fileBufferPrefix.println("")
        val inputGraphIsDefaultGraph = mutableListOf<Boolean>()
        val outputGraphIsDefaultGraph = mutableListOf<Boolean>()
        fileBufferPrefix.println("public class $testCaseName {")
        if (inputGraphs.isNotEmpty()) {
            fileBufferPrefix.println("    internal val inputData = arrayOf(")
            for ((k, v) in inputGraphs) {
                fileBufferPrefix.println("        File(\"src/jvmTest/resources/$k\").readAsString(),")
            }
            fileBufferPrefix.println("    )")
            fileBufferPrefix.println("    internal val inputGraph = arrayOf(")
            for ((k, v) in inputGraphs) {
                fileBufferPrefix.println("        \"${v.graph}\",")
                inputGraphIsDefaultGraph.add(v.graph == "")
            }
            fileBufferPrefix.println("    )")
            fileBufferPrefix.println("    internal val inputType = arrayOf(")
            for ((k, v) in inputGraphs) {
                fileBufferPrefix.println("        \"${v.type}\",")
            }
            fileBufferPrefix.println("    )")
        }
        if (outputGraphs.isNotEmpty()) {
            fileBufferPrefix.println("    internal val outputData = arrayOf(")
            for ((k, v) in outputGraphs) {
                fileBufferPrefix.println("        File(\"src/jvmTest/resources/$k\").readAsString(),")
            }
            fileBufferPrefix.println("    )")
            fileBufferPrefix.println("    internal val outputGraph = arrayOf(")
            for ((k, v) in outputGraphs) {
                outputGraphIsDefaultGraph.add(v.graph == "")
                fileBufferPrefix.println("        \"${v.graph}\",")
            }
            fileBufferPrefix.println("    )")
            fileBufferPrefix.println("    internal val outputType = arrayOf(")
            for ((k, v) in outputGraphs) {
                fileBufferPrefix.println("        \"${v.type}\",")
            }
            fileBufferPrefix.println("    )")
        }
        if (mode == BinaryTestCaseOutputModeExt.SELECT_QUERY_RESULT) {
            fileBufferPrefix.println("    internal val targetData = File(\"src/jvmTest/resources/$testCaseName.output\").readAsString()")
            fileBufferPrefix.println("    internal val targetType = \"$targetType\"")
        }
        fileBufferPrefix.println("    internal val query = \"${cleanFileContent(File(queryFile).readAsString())}\"")
        fileBufferPrefix.println("")
        var first = true
        outerloop@ for (LUPOS_PARTITION_MODE in EPartitionModeExt.names) {
            for (predefinedPartitionScheme in EPredefinedPartitionSchemesExt.names) {
                for (useDictionaryInlineEncoding in listOf("true", "false")) {
                    if (LUPOS_PARTITION_MODE == EPartitionModeExt.names[EPartitionModeExt.Process]) {
                        continue
                    }
                    if (onlyFirstTest) {
                        if (first) {
                            first = false
                        } else {
                            break@outerloop
                        }
                    }
                    val finalTestName = "$testCaseName2 - $LUPOS_PARTITION_MODE - $predefinedPartitionScheme - $useDictionaryInlineEncoding"
                    if (shouldAddFunction(finalTestName)) {
                        val fileBufferTest = MyPrintWriter(true)
                        fileBufferTests[finalTestName] = fileBufferTest
                        if (isIgnored(finalTestName)) {
                            fileBufferTest.println("    @Ignore")
                        }
                        fileBufferTest.println("    @Test(timeout = 2000)")
                        fileBufferTest.println("    public fun `$finalTestName`() {")
                        fileBufferTest.println("      var instance = Luposdate3000Instance()")
                        fileBufferTest.println("      try{")
                        fileBufferTest.println("        instance.LUPOS_BUFFER_SIZE = 128")
                        fileBufferTest.println("        instance.LUPOS_PARTITION_MODE=EPartitionModeExt.$LUPOS_PARTITION_MODE")
                        fileBufferTest.println("        instance.predefinedPartitionScheme=EPredefinedPartitionSchemesExt.$predefinedPartitionScheme")
                        fileBufferTest.println("        instance.useDictionaryInlineEncoding=$useDictionaryInlineEncoding")
                        fileBufferTest.println("        instance = LuposdateEndpoint.initializeB(instance)")
                        fileBufferTest.println("        val buf = MyPrintWriter(false)")
                        for (i in 0 until inputGraphs.size) {
                            appendDistributedTest("MySimulatorTestingImportPackage(inputData[$i], inputGraph[$i], inputType[$i])", false)
                            fileBufferTest.println("        if (listOf(\".n3\", \".ttl\", \".nt\").contains(inputType[$i])) {")
                            fileBufferTest.println("            LuposdateEndpoint.importTurtleString(instance, inputData[$i], inputGraph[$i])")
                            fileBufferTest.println("        } else {")
                            fileBufferTest.println("            TODO()")
                            fileBufferTest.println("        }")
                        }
                        for (i in 0 until inputGraphs.size) {
                            val c = localCounter++
                            myVerifyGraph(c, "inputData[$i]", "inputType[$i]", "inputGraph[$i]", null, inputGraphIsDefaultGraph[i], fileBufferTest)
                        }
                        val counter = localCounter++
                        val evaluateIt = outputGraphs.isNotEmpty() || mode == BinaryTestCaseOutputModeExt.SELECT_QUERY_RESULT
                        if (evaluateIt || expectedResult) {
                            fileBufferTest.println("        val operator$counter = LuposdateEndpoint.evaluateSparqlToOperatorgraphA(instance, query)")
                            if (mode == BinaryTestCaseOutputModeExt.SELECT_QUERY_RESULT) {
                                myVerifyGraph(counter, "targetData", "targetType", "", "query", false, fileBufferTest)
                            } else {
                                if (evaluateIt) {
                                    appendDistributedTest("MySimulatorTestingExecute(query)", false)
                                    fileBufferTest.println("        LuposdateEndpoint.evaluateOperatorgraphToResultA(instance, operator$counter, buf, EQueryResultToStreamExt.EMPTY_STREAM)")
                                }
                            }
                        } else {
                            fileBufferTest.println("        var flag = false")
                            fileBufferTest.println("        try {")
                            fileBufferTest.println("            LuposdateEndpoint.evaluateSparqlToOperatorgraphA(instance, query)")
                            fileBufferTest.println("        } catch (e: Throwable) {")
                            fileBufferTest.println("            flag = true")
                            fileBufferTest.println("        }")
                            fileBufferTest.println("        if (!flag) {")
                            fileBufferTest.println("            fail(\"expected failure\")")
                            fileBufferTest.println("        }")
                        }
                        for (i in 0 until outputGraphs.size) {
                            val c = localCounter++
                            myVerifyGraph(c, "outputData[$i]", "outputType[$i]", "outputGraph[$i]", null, outputGraphIsDefaultGraph[i], fileBufferTest)
                        }
                        fileBufferTest.println("      }finally{")
                        fileBufferTest.println("        LuposdateEndpoint.close(instance)") // for inmemory db this results in complete wipe of ALL data
                        fileBufferTest.println("      }")
                        fileBufferTest.println("    }")
                        distributedTestAppendFlag = false
                    }
                }
            }
        }
        val str = distributedTest.toString()
        if (str.isNotEmpty()) {
            for (LUPOS_PARTITION_MODE in EPartitionModeExt.names) {
                for (predefinedPartitionScheme in EPredefinedPartitionSchemesExt.names) {
                    for (queryDistributionMode in EQueryDistributionModeExt.names) {
                        for (useDictionaryInlineEncoding in listOf("true", "false")) {
                            if (LUPOS_PARTITION_MODE != EPartitionModeExt.names[EPartitionModeExt.Process] && queryDistributionMode == EQueryDistributionModeExt.names[EQueryDistributionModeExt.Routing]) {
                                continue
                            }
                            if (LUPOS_PARTITION_MODE == EPartitionModeExt.names[EPartitionModeExt.Process] && predefinedPartitionScheme == EPredefinedPartitionSchemesExt.names[EPredefinedPartitionSchemesExt.Simple]) {
                                continue
                            }
                            val finalTestName = "$testCaseName2 - in simulator - $predefinedPartitionScheme - $queryDistributionMode - $useDictionaryInlineEncoding - $LUPOS_PARTITION_MODE"
                            if (shouldAddFunction(finalTestName)) {
                                val fileBufferTest = MyPrintWriter(true)
                                fileBufferTests[finalTestName] = fileBufferTest
                                if (isIgnored(finalTestName) || !withSimulator) {
                                    fileBufferTest.println("    @Ignore")
                                }
                                fileBufferTest.println("    @Test(timeout = 2000)")
                                fileBufferTest.println("    public fun `$finalTestName`() {")
                                fileBufferTest.println("        simulatorHelper(")
                                if (LUPOS_PARTITION_MODE == EPartitionModeExt.names[EPartitionModeExt.Process]) {
                                    fileBufferTest.println("            \"../luposdate3000_simulator_iot/src/jvmTest/resources/autoIntegrationTest/test1.json\",")
                                } else {
                                    fileBufferTest.println("            \"../luposdate3000_simulator_iot/src/jvmTest/resources/autoIntegrationTest/test2.json\",")
                                }
                                fileBufferTest.println("            mutableMapOf(")
                                fileBufferTest.println("                \"predefinedPartitionScheme\" to \"$predefinedPartitionScheme\",")
                                fileBufferTest.println("                \"mergeLocalOperatorgraphs\" to true,")
                                fileBufferTest.println("                \"queryDistributionMode\" to \"$queryDistributionMode\",")
                                fileBufferTest.println("                \"useDictionaryInlineEncoding\" to $useDictionaryInlineEncoding,")
                                fileBufferTest.println("                \"REPLACE_STORE_WITH_VALUES\" to false,") // this does not work in simulator
                                fileBufferTest.println("                \"LUPOS_PARTITION_MODE\" to \"$LUPOS_PARTITION_MODE\",")
                                fileBufferTest.println("            )")
                                fileBufferTest.println("        )")
                                fileBufferTest.println("    }")
                                fileBufferTest.println("    public fun simulatorHelper(fileName:String,cfg:MutableMap<String,Any>) {")
                                fileBufferTest.println("        val simRun = SimulationRun()")
                                fileBufferTest.println("        val config=simRun.parseConfig(fileName,false)")
                                fileBufferTest.println("        config.jsonObjects.database.putAll(cfg)")
                                fileBufferTest.println("        simRun.sim = Simulation(config.getEntities())")
                                fileBufferTest.println("        simRun.sim.maxClock = if (simRun.simMaxClock == simRun.notInitializedClock) simRun.sim.maxClock else simRun.simMaxClock")
                                fileBufferTest.println("        simRun.sim.steadyClock = if (simRun.simSteadyClock == simRun.notInitializedClock) simRun.sim.steadyClock else simRun.simSteadyClock")
                                fileBufferTest.println("        simRun.sim.startUp()")
                                fileBufferTest.println("        val instance = (config.devices.filter {it.userApplication!=null}.map{it.userApplication!!.getAllChildApplications()}.flatten().filter{it is DatabaseHandle}.first()as DatabaseHandle).instance")
                                fileBufferTest.print(str)
                                fileBufferTest.println("        config.querySenders[0].queryPck = pkg0")
                                fileBufferTest.println("        simRun.sim.run()")
                                fileBufferTest.println("        simRun.sim.shutDown()")
                                fileBufferTest.print(distributedTestAtEnd.toString())
                                fileBufferTest.println("    }")
                            }
                        }
                    }
                }
            }
        }
        fileBufferPostfix.println("}")
        val prefix = fileBufferPrefix.toString()
        val postfix = fileBufferPostfix.toString()
        for ((testname, fileBufferTest) in fileBufferTests) {
            val finalClassName = "${testname.takeLast(150)}".filter { it.isLetterOrDigit() }
            File("${outputFolderTestJvm(folderCurrent)}/$finalClassName.kt").withOutputStream { out ->
                val content = fileBufferTest.toString()
                out.print(prefix.replace("class $testCaseName", "class $finalClassName"))
                out.print(content)
                out.print(postfix)
            }
        }
        if (fileBufferTests.size> 0) {
            for ((x, g) in inputGraphs) {
                File(g.filename).withOutputStream { out ->
                    File(g.filenameoriginal).forEachLine {
                        out.println(it)
                    }
                }
            }
            for ((x, g) in outputGraphs) {
                File(g.filename).withOutputStream { out ->
                    File(g.filenameoriginal).forEachLine {
                        out.println(it)
                    }
                }
            }
            var targetType = "NONE"
            if (resultDataFileName != null) {
                File("${outputFolderTestResourcesJvm(folderCurrent)}/$testCaseName.output").withOutputStream { out ->
                    File(resultDataFileName).forEachLine {
                        out.println(it)
                    }
                }
            }
            folderCurrent = (folderCurrent + 1) % folderCount
        }
        return true
    }
}
