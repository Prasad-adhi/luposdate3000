package lupos

import kotlin.jvm.JvmField
import kotlin.time.DurationUnit
import kotlin.time.TimeSource.Monotonic
import kotlinx.coroutines.runBlocking
import lupos.s00misc.CoroutinesHelper
import lupos.s00misc.Coverage
import lupos.s00misc.EIndexPattern
import lupos.s00misc.ELoggerType
import lupos.s00misc.EModifyType
import lupos.s00misc.File
import lupos.s00misc.GlobalLogger
import lupos.s00misc.JenaBugException
import lupos.s00misc.JenaWrapper
import lupos.s00misc.Luposdate3000Exception
import lupos.s00misc.MAX_TRIPLES_DURING_TEST
import lupos.s00misc.MyMapStringIntPatriciaTrie
import lupos.s00misc.NotImplementedException
import lupos.s00misc.OperatorGraphToLatex
import lupos.s00misc.parseFromXml
import lupos.s00misc.Partition
import lupos.s00misc.SanityCheck
import lupos.s00misc.UnknownManifestException
import lupos.s00misc.XMLElement
import lupos.s02buildSyntaxTree.LexerCharIterator
import lupos.s02buildSyntaxTree.LookAheadTokenIterator
import lupos.s02buildSyntaxTree.ParseError
import lupos.s02buildSyntaxTree.rdf.BlankNode
import lupos.s02buildSyntaxTree.rdf.Dictionary
import lupos.s02buildSyntaxTree.rdf.ID_Triple
import lupos.s02buildSyntaxTree.rdf.IRI
import lupos.s02buildSyntaxTree.rdf.SimpleLiteral
import lupos.s02buildSyntaxTree.sparql1_1.parseSPARQL
import lupos.s02buildSyntaxTree.sparql1_1.SPARQLParser
import lupos.s02buildSyntaxTree.sparql1_1.TokenIteratorSPARQLParser
import lupos.s02buildSyntaxTree.turtle.TurtleParserWithDictionary
import lupos.s03resultRepresentation.nodeGlobalDictionary
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s03resultRepresentation.Value
import lupos.s04arithmetikOperators.noinput.AOPVariable
import lupos.s04logicalOperators.iterator.ColumnIterator
import lupos.s04logicalOperators.Query
import lupos.s05tripleStore.index_IDTriple.NodeManager
import lupos.s06buildOperatorGraph.OperatorGraphVisitor
import lupos.s08logicalOptimisation.LogicalOptimizer
import lupos.s09physicalOperators.noinput.POPValuesImportXML
import lupos.s10physicalOptimisation.PhysicalOptimizer
import lupos.s11outputResult.QueryResultToXMLElement
import lupos.s13keyDistributionOptimizer.KeyDistributionOptimizer
import lupos.s14endpoint.convertToOPBase
import lupos.s15tripleStoreDistributed.DistributedTripleStore
import lupos.s16network.HttpEndpoint
import lupos.s16network.ServerCommunicationSend

class SparqlTestSuite() {
    companion object {
        val filterList = mutableListOf<String>()
    }

    @JvmField
    val errorBoundForDecimalsDigits = 6
    fun testMain() {
        repeat(1) {
            GlobalLogger.log(ELoggerType.RELEASE, { "Starting tests..." })
            val (nr_t, nr_e) = parseManifestFile("resources/sparql11-test-suite/", "manifest-all.ttl")
            GlobalLogger.log(ELoggerType.RELEASE, { "Number of tests: " + nr_t })
            GlobalLogger.log(ELoggerType.RELEASE, { "Number of errors: " + nr_e })
            var prefixes = listOf("resources/myqueries/", "resources/bsbm/", "resources/btc/", "resources/sp2b/")
            for (prefix in prefixes) {
                var lastinput: String? = null
                File(prefix + "config.csv").forEachLine {
                    val line = it.split(",")
                    if (line.size > 3) {
                        val triplesCount = line[0]
                        val queryFile = prefix + line[1]
                        var inputFile = prefix + line[2]
                        val outputFile = prefix + line[3]
                        if (!File(outputFile).exists()) {
                            try {
                                JenaWrapper.loadFromFile("/src/luposdate3000/" + inputFile)
                                val jenaResult = JenaWrapper.execQuery(File(queryFile).readAsString())
                                val jenaXML = XMLElement.parseFromXml(jenaResult)!!
                                runBlocking {
                                    File(outputFile).printWriter {
                                        it.println(jenaXML.toPrettyString())
                                    }
                                }
                            } catch (e: Throwable) {
                                SanityCheck.println({ "TODO exception 39" })
                                e.printStackTrace()
                            } finally {
                                JenaWrapper.dropAll()
                            }
                        }
                        if (lastinput == inputFile) {
                            inputFile = "#keep-data#"
                        } else {
                            lastinput = inputFile
                        }
                        runBlocking {
                            ServerCommunicationSend.distributedLogMessage("  Test: " + queryFile + "-" + triplesCount)
                            parseSPARQLAndEvaluate(false, queryFile, true, queryFile, inputFile, outputFile, null, mutableListOf<MutableMap<String, String>>(), mutableListOf<MutableMap<String, String>>())
                        }
                    }
                }
            }
        }
        ResultSetDictionary.debug()
    }

    private fun listMembers(data: SevenIndices, start: Long, f: (Long) -> Unit) {
        val rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#"
        val nil = rdf + "nil"
        val first = rdf + "first"
        val rest = rdf + "rest"
        val nil_iri = Dictionary.IRI(nil)
        val first_iri = Dictionary.IRI(first)
        val rest_iri = Dictionary.IRI(rest)
        fun recursiveListMembers(current: Long) {
            data.sp(current, first_iri).forEach { f(it) }
            data.sp(current, rest_iri).forEach {
                if (it != nil_iri) {
                    listMembers(data, it, f)
                }
            }
        }
        recursiveListMembers(start)
    }

    private fun readTurtleData(filename: String, consume_triple: (Long, Long, Long) -> Unit) {
        val ltit = LookAheadTokenIterator(lupos.s02buildSyntaxTree.turtle.TurtleScanner(LexerCharIterator(File(filename).readAsString())), 3)
        try {
            TurtleParserWithDictionary(consume_triple, ltit).turtleDoc()
        } catch (e: ParseError) {
            GlobalLogger.stacktrace(ELoggerType.DEBUG, e)
            GlobalLogger.log(ELoggerType.DEBUG, { "Error in the following line:" })
            GlobalLogger.log(ELoggerType.DEBUG, { e.lineNumber })
        }
    }

    private fun createSevenIndices(filename: String): SevenIndices {
        val data = SevenIndices()
        readTurtleData(filename, data::add)
        data.distinct()
        return data
    }

    private fun parseManifestFile(prefix: String, filename: String): Pair<Int, Int> {
        var numberOfErrors = 0
        var numberOfTests = 0
        GlobalLogger.log(ELoggerType.DEBUG, { "Reading file " + filename + "..." })
        val data = createSevenIndices(prefix + filename)
        val newprefix = prefix + filename.substringBeforeLast("/") + "/"
        val manifestEntries = data.po(Dictionary.IRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), Dictionary.IRI("http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#Manifest"))
        manifestEntries.forEach {
            // Are other manifest files included?
            val included = data.sp(it, Dictionary.IRI("http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#include"))
            included.forEach {
                // follow list of included manifest files:
                listMembers(data, it) {
                    val includedfile = Dictionary[it]
                    if (includedfile != null) {
                        includedfile as IRI
                        val (nr_t, nr_e) = parseManifestFile(prefix, includedfile.iri)
                        numberOfTests += nr_t
                        numberOfErrors += nr_e
                    }
                }
            }
            // Now look for_ the tests:
            val tests = data.sp(it, Dictionary.IRI("http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#entries"))
            tests.forEach {
                // follow list of entries:
                listMembers(data, it) {
                    // for_ printing out the name:
                    val name = data.sp(it, Dictionary.IRI("http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#name"))
                    name.forEach {
                        ServerCommunicationSend.distributedLogMessage("  Test: " + (Dictionary[it] as SimpleLiteral).content)
                    }
                    numberOfTests++
                    if (!testOneEntry(data, it, newprefix)) {
                        numberOfErrors++
                    }
                }
            }
        }
        return Pair(numberOfTests, numberOfErrors)
    }

    private fun readFileOrNull(name: String?): String? {
        if (name == null) {
            return null
        }
        return File(name).readAsString()
    }

    private fun testOneEntry(data: SevenIndices, node: Long, prefix: String): Boolean {
        var testType: String? = null
        var comment: String? = null
        var features = mutableListOf<String>()
        var description: String? = null
        var names = mutableListOf<String>()
        var expectedResult = true
        var queryFile: String? = null
        var inputDataFile: String? = null
        var resultFile: String? = null
        var services = mutableListOf<MutableMap<String, String>>()
        var inputDataGraph = mutableListOf<MutableMap<String, String>>()
        var outputDataGraph = mutableListOf<MutableMap<String, String>>()
        data.s(node).forEach {
            val iri = (Dictionary[it.first] as IRI).iri
            when (iri) {
                "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#result" -> {
                    when {
                        Dictionary[it.second] is IRI -> {
                            SanityCheck.check({ resultFile == null })
                            resultFile = prefix + (Dictionary[it.second] as IRI).iri
                        }
                        Dictionary[it.second] is BlankNode -> {
                            data.s(it.second).forEach {
                                val iri2 = (Dictionary[it.first] as IRI).iri
                                when (iri2) {
                                    "http://www.w3.org/2009/sparql/tests/test-update#data" -> {
                                        val graph = mutableMapOf<String, String>()
                                        graph["name"] = ""
                                        graph["filename"] = prefix + (Dictionary[it.second] as IRI).iri
                                        outputDataGraph.add(graph)
                                    }
                                    "http://www.w3.org/2009/sparql/tests/test-update#graphData" -> {
                                        val graph = mutableMapOf<String, String>()
                                        data.s(it.second).forEach {
                                            val iri3 = (Dictionary[it.first] as IRI).iri
                                            when (iri3) {
                                                "http://www.w3.org/2009/sparql/tests/test-update#graph" -> {
                                                    graph["filename"] = prefix + (Dictionary[it.second] as IRI).iri
                                                }
                                                "http://www.w3.org/2000/01/rdf-schema#label" -> {
                                                    graph["name"] = (Dictionary[it.second] as SimpleLiteral).content
                                                }
                                                else -> {
                                                    throw UnknownManifestException("SparqlTestSuite", (Dictionary[it.first] as IRI).iri + " # " + Dictionary[it.second])
                                                }
                                            }
                                        }
                                        outputDataGraph.add(graph)
                                    }
                                    "http://www.w3.org/2009/sparql/tests/test-update#result" -> {
                                        GlobalLogger.log(ELoggerType.DEBUG, { "unknown-manifest::http://www.w3.org/2009/sparql/tests/test-update#result : " + (Dictionary[it.second] as IRI).iri })
                                    }
                                    else -> {
                                        throw UnknownManifestException("SparqlTestSuite", (Dictionary[it.first] as IRI).iri + " # " + Dictionary[it.second])
                                    }
                                }
                            }
                        }
                        else -> {
                            throw UnknownManifestException("SparqlTestSuite", (Dictionary[it.first] as IRI).iri + " # " + Dictionary[it.second])
                        }
                    }
                }
                "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#action" -> {
                    when {
                        Dictionary[it.second] is IRI -> {
                            queryFile = prefix + (Dictionary[it.second] as IRI).iri
                        }
                        Dictionary[it.second] is BlankNode -> {
                            data.s(it.second).forEach {
                                val iri2 = (Dictionary[it.first] as IRI).iri
                                when (iri2) {
                                    "http://www.w3.org/2001/sw/DataAccess/tests/test-query#data" -> {
                                        SanityCheck.check({ inputDataFile == null })
                                        inputDataFile = prefix + (Dictionary[it.second] as IRI).iri
                                    }
                                    "http://www.w3.org/2001/sw/DataAccess/tests/test-query#query" -> {
                                        SanityCheck.check({ queryFile == null })
                                        queryFile = prefix + (Dictionary[it.second] as IRI).iri
                                    }
                                    "http://www.w3.org/ns/sparql-service-description#entailmentRegime" -> {
                                        GlobalLogger.log(ELoggerType.DEBUG, { "unknown-manifest::http://www.w3.org/ns/sparql-service-description#entailmentRegime " + Dictionary[it.second] })
                                    }
                                    "http://www.w3.org/ns/sparql-service-description#EntailmentProfile" -> {
                                        GlobalLogger.log(ELoggerType.DEBUG, { "unknown-manifest::http://www.w3.org/ns/sparql-service-description#EntailmentProfile " + Dictionary[it.second] })
                                    }
                                    "http://www.w3.org/2001/sw/DataAccess/tests/test-query#graphData" -> {
                                        val graph = mutableMapOf<String, String>()
                                        graph["name"] = (Dictionary[it.second] as IRI).iri
                                        graph["filename"] = prefix + (Dictionary[it.second] as IRI).iri
                                        inputDataGraph.add(graph)
                                    }
                                    "http://www.w3.org/2001/sw/DataAccess/tests/test-query#serviceData" -> {
                                        val service = mutableMapOf<String, String>()
                                        data.s(it.second).forEach {
                                            val iri3 = (Dictionary[it.first] as IRI).iri
                                            when (iri3) {
                                                "http://www.w3.org/2001/sw/DataAccess/tests/test-query#endpoint" -> {
                                                    service["name"] = (Dictionary[it.second] as IRI).iri
                                                }
                                                "http://www.w3.org/2001/sw/DataAccess/tests/test-query#data" -> {
                                                    service["filename"] = prefix + (Dictionary[it.second] as IRI).iri
                                                }
                                                else -> {
                                                    throw UnknownManifestException("SparqlTestSuite", (Dictionary[it.first] as IRI).iri + " # " + Dictionary[it.second])
                                                }
                                            }
                                        }
                                        if (service["filename"] != null) {
                                            services.add(service)
                                        }
                                    }
                                    "http://www.w3.org/2009/sparql/tests/test-update#request" -> {
                                        SanityCheck.check({ queryFile == null })
                                        queryFile = prefix + (Dictionary[it.second] as IRI).iri
                                    }
                                    "http://www.w3.org/2009/sparql/tests/test-update#data" -> {
                                        SanityCheck.check({ inputDataFile == null })
                                        inputDataFile = prefix + (Dictionary[it.second] as IRI).iri
                                    }
                                    "http://www.w3.org/2009/sparql/tests/test-update#graphData" -> {
                                        val graph = mutableMapOf<String, String>()
                                        data.s(it.second).forEach {
                                            val iri3 = (Dictionary[it.first] as IRI).iri
                                            when (iri3) {
                                                "http://www.w3.org/2009/sparql/tests/test-update#graph" -> {
                                                    graph["filename"] = prefix + (Dictionary[it.second] as IRI).iri
                                                }
                                                "http://www.w3.org/2000/01/rdf-schema#label" -> {
                                                    graph["name"] = (Dictionary[it.second] as SimpleLiteral).content
                                                }
                                                else -> {
                                                    throw UnknownManifestException("SparqlTestSuite", (Dictionary[it.first] as IRI).iri + " # " + Dictionary[it.second])
                                                }
                                            }
                                        }
                                        inputDataGraph.add(graph)
                                    }
                                    else -> {
                                        throw UnknownManifestException("SparqlTestSuite", (Dictionary[it.first] as IRI).iri + " # " + Dictionary[it.second])
                                    }
                                }
                            }
                        }
                        else -> {
                            throw UnknownManifestException("SparqlTestSuite", (Dictionary[it.first] as IRI).iri + " # " + Dictionary[it.second])
                        }
                    }
                }
                "http://www.w3.org/1999/02/22-rdf-syntax-ns#type" -> {
                    SanityCheck.check({ testType == null })
                    testType = (Dictionary[it.second] as IRI).iri
                    when ((Dictionary[it.second] as IRI).iri) {
                        "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#CSVResultFormatTest" -> {
                        }
                        "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#NegativeUpdateSyntaxTest11" -> {
                            expectedResult = false
                        }
                        "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#PositiveSyntaxTest11" -> {
                        }
                        "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#PositiveUpdateSyntaxTest11" -> {
                        }
                        "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#ProtocolTest" -> {
                        }
                        "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#QueryEvaluationTest" -> {
                        }
                        "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#ServiceDescriptionTest" -> {
                        }
                        "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#UpdateEvaluationTest" -> {
                        }
                        "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#NegativeSyntaxTest11" -> {
                            expectedResult = false
                        }
                        else -> {
                            throw UnknownManifestException("SparqlTestSuite", (Dictionary[it.first] as IRI).iri + " # " + (Dictionary[it.second] as IRI).iri)
                        }
                    }
                }
                "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#name" -> {
                    names.add((Dictionary[it.second] as SimpleLiteral).content)
                }
                "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#feature" -> {
                    features.add((Dictionary[it.second] as IRI).iri)
                }
                "http://www.w3.org/2000/01/rdf-schema#comment" -> {
                    SanityCheck.check({ comment == null })
                    comment = (Dictionary[it.second] as SimpleLiteral).content
                }
                "http://www.w3.org/2001/sw/DataAccess/tests/test-dawg#approval" -> {
                    GlobalLogger.log(ELoggerType.DEBUG, { "unknown-manifest::http://www.w3.org/2001/sw/DataAccess/tests/test-dawg#approval " + Dictionary[it.second] })
                }
                "http://www.w3.org/2001/sw/DataAccess/tests/test-dawg#approvedBy" -> {
                    GlobalLogger.log(ELoggerType.DEBUG, { "unknown-manifest::http://www.w3.org/2001/sw/DataAccess/tests/test-dawg#approvedBy " + Dictionary[it.second] })
                }
                "http://www.w3.org/2000/01/rdf-schema#seeAlso" -> {
                    GlobalLogger.log(ELoggerType.DEBUG, { "unknown-manifest::http://www.w3.org/2000/01/rdf-schema#seeAlso " + (Dictionary[it.second] as IRI).iri })
                }
                "http://www.w3.org/2001/sw/DataAccess/tests/test-query#queryForm" -> {
                    GlobalLogger.log(ELoggerType.DEBUG, { "unknown-manifest::http://www.w3.org/2001/sw/DataAccess/tests/test-query#queryForm " + (Dictionary[it.second] as IRI).iri })
                }
                "http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#description" -> {
                    SanityCheck.check({ description == null })
                    description = (Dictionary[it.second] as SimpleLiteral).content
                }
                else -> {
                    throw UnknownManifestException("SparqlTestSuite", (Dictionary[it.first] as IRI).iri + " # " + Dictionary[it.second])
                }
            }
        }
        GlobalLogger.log(ELoggerType.TEST_DETAIL, { "testType : $testType" })
        GlobalLogger.log(ELoggerType.TEST_DETAIL, { "names : $names" })
        GlobalLogger.log(ELoggerType.TEST_DETAIL, { "comment : $comment" })
        GlobalLogger.log(ELoggerType.TEST_DETAIL, { "description : $description" })
        GlobalLogger.log(ELoggerType.TEST_DETAIL, { "features : $features" })
        GlobalLogger.log(ELoggerType.TEST_DETAIL, { "inputDataGraph : $inputDataGraph" })
        GlobalLogger.log(ELoggerType.TEST_DETAIL, { "outputDataGraph : $outputDataGraph" })
        GlobalLogger.log(ELoggerType.TEST_DETAIL, { "expectedResult : $expectedResult" })
        GlobalLogger.log(ELoggerType.TEST_DETAIL, { "queryFile : $queryFile" })
        GlobalLogger.log(ELoggerType.TEST_DETAIL, { "inputDataFile : $inputDataFile" })
        GlobalLogger.log(ELoggerType.TEST_DETAIL, { "resultFile : $resultFile" })
        GlobalLogger.log(ELoggerType.TEST_DETAIL, { "services : $services" })
        if (queryFile == null) {
            return true
        }
        var success = false
        runBlocking {
            lastTripleCount = 0//dont apply during w3c-tests
            success = parseSPARQLAndEvaluate(true, names.first(), expectedResult, queryFile!!, inputDataFile, resultFile, services, inputDataGraph, outputDataGraph)
        }
        return success == expectedResult
    }

    @JvmField
    var i = 0

    @JvmField
    var lastTripleCount = 0

    @UseExperimental(ExperimentalStdlibApi::class, kotlin.time.ExperimentalTime::class)
    fun parseSPARQLAndEvaluate(executeJena: Boolean, testName: String, expectedResult: Boolean, queryFile: String, inputDataFileName: String?, resultDataFileName: String?, services: List<Map<String, String>>?, inputDataGraph: MutableList<MutableMap<String, String>>, outputDataGraph: MutableList<MutableMap<String, String>>): Boolean {
        if (!testName.contains("resources")) {
            return true
        }
        if (filterList.size > 0 && !filterList.contains(testName)) {
            SanityCheck.println({ "'$testName' not in WhiteList of Unit-Tests" })
            return true
        } else {
            SanityCheck.println({ "'$testName' is in WhiteList of Unit-Tests" })
        }
        File("log/storetest").mkdirs()
        var ignoreJena = !executeJena
        var timer = Monotonic.markNow()
        var shouldHaveSkipped = false
        try {
            val toParse = readFileOrNull(queryFile)!!
            if (toParse.contains("service", true)) {
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(Service)" })
                return false
            }
            val resultData = readFileOrNull(resultDataFileName)
            if (inputDataFileName != "#keep-data#") {
                val query2 = Query()
                query2.workingDirectory = queryFile.substring(0, queryFile.lastIndexOf("/"))
                ServerCommunicationSend.graphClearAll(query2)
                query2.commit()
                nodeGlobalDictionary.clear()
                JenaWrapper.dropAll()
                val inputData = readFileOrNull(inputDataFileName)
                if (inputData != null && inputDataFileName != null) {
                    lastTripleCount = inputData.split("\n").size
                    if (MAX_TRIPLES_DURING_TEST > 0 && lastTripleCount > MAX_TRIPLES_DURING_TEST) {
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success(Skipped)" })
                        shouldHaveSkipped = true
                        return true
                    }
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "InputData Graph[] Original" })
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { inputData })
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Input Data Graph[]" })
                    var xmlQueryInput = XMLElement.parseFromAny(inputData, inputDataFileName)!!
                    if (inputDataFileName.endsWith(".ttl") || inputDataFileName.endsWith(".n3")) {
                        var xmlGraphBulk: XMLElement? = null
                        val query = Query()
                        query.workingDirectory = queryFile.substring(0, queryFile.lastIndexOf("/"))
                        HttpEndpoint.import_turtle_files(inputDataFileName, MyMapStringIntPatriciaTrie())
                        val bulkSelect = DistributedTripleStore.getDefaultGraph(query).getIterator(arrayOf(AOPVariable(query, "s"), AOPVariable(query, "p"), AOPVariable(query, "o")), EIndexPattern.SPO)
                        xmlGraphBulk = QueryResultToXMLElement.toXML(bulkSelect)
                        if (xmlGraphBulk == null || !xmlGraphBulk!!.myEqualsUnclean(xmlQueryInput, true, true, true)) {
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "test xmlQueryInput :: " + xmlQueryInput.toPrettyString() })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "test xmlGraphBulk :: " + xmlGraphBulk?.toPrettyString() })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(BulkImport)" })
                            return false
                        }
                    } else {
                        val query = Query()
                        query.workingDirectory = queryFile.substring(0, queryFile.lastIndexOf("/"))
                        val tmp = POPValuesImportXML(query, listOf("s", "p", "o"), xmlQueryInput).evaluate(Partition())
                        DistributedTripleStore.getDefaultGraph(query).modify(arrayOf(tmp.columns["s"]!!, tmp.columns["p"]!!, tmp.columns["o"]!!), EModifyType.INSERT)
                        query.commit()
                    }
                    File("log/storetest").mkdirs()
                    DistributedTripleStore.localStore.safeToFolder()
                    DistributedTripleStore.localStore.loadFromFolder()
                    var xmlGraphLoad: XMLElement? = null
                    val query = Query()
                    query.workingDirectory = queryFile.substring(0, queryFile.lastIndexOf("/"))
                    val loadSelect = DistributedTripleStore.getDefaultGraph(query).getIterator(arrayOf(AOPVariable(query, "s"), AOPVariable(query, "p"), AOPVariable(query, "o")), EIndexPattern.SPO)
                    xmlGraphLoad = QueryResultToXMLElement.toXML(loadSelect)
                    if (xmlGraphLoad == null || !xmlGraphLoad!!.myEqualsUnclean(xmlQueryInput, true, true, true)) {
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "test xmlQueryInput :: " + xmlQueryInput.toPrettyString() })
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "test xmlGraphLoad :: " + xmlGraphLoad?.toPrettyString() })
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(LoadImport)" })
                        return false
                    }
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "test InputData Graph[] ::" + xmlQueryInput.toPrettyString() })
                    try {
                        if (!ignoreJena) {
                            JenaWrapper.loadFromFile("/src/luposdate3000/" + inputDataFileName)
                        }
                    } catch (e: JenaBugException) {
                        SanityCheck.println({ e.message })
                        ignoreJena = true
                    } catch (e: Throwable) {
                        SanityCheck.println({ "TODO exception 41" })
                        e.printStackTrace()
                        ignoreJena = true
                    }
                }
                inputDataGraph.forEach {
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "InputData Graph[${it["name"]}] Original" })
                    val inputData2 = readFileOrNull(it["filename"])
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { inputData2 })
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Input Data Graph[${it["name"]}]" })
                    var xmlQueryInput = XMLElement.parseFromAny(inputData2!!, it["filename"]!!)!!
                    val query = Query()
                    query.workingDirectory = queryFile.substring(0, queryFile.lastIndexOf("/"))
                    val tmp = POPValuesImportXML(query, listOf("s", "p", "o"), xmlQueryInput).evaluate(Partition())
                    DistributedTripleStore.getNamedGraph(query, it["name"]!!).modify(arrayOf(tmp.columns["s"]!!, tmp.columns["p"]!!, tmp.columns["o"]!!), EModifyType.INSERT)
                    query.commit()
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "test Input Graph[${it["name"]!!}] :: " + xmlQueryInput.toPrettyString() })
                    try {
                        if (!ignoreJena) {
                            JenaWrapper.loadFromFile("/src/luposdate3000/" + it["filename"]!!, it["name"]!!)
                        }
                    } catch (e: JenaBugException) {
                        SanityCheck.println({ e.message })
                        ignoreJena = true
                    } catch (e: Throwable) {
                        SanityCheck.println({ "TODO exception 42" })
                        e.printStackTrace()
                        ignoreJena = true
                    }
                }
                if (services != null) {
                    for (s in services) {
                        val n = s["name"]!!
                        val fn = s["filename"]!!
                        val fc = readFileOrNull(fn)!!
//                        ServerCommunicationSend.insertOnNamedNode(n, XMLElement.parseFromAny(fc, fn)!!)
                    }
                }
            } else {
                if (MAX_TRIPLES_DURING_TEST > 0 && lastTripleCount > MAX_TRIPLES_DURING_TEST) {
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success(Skipped)" })
                    shouldHaveSkipped = true
                    return true
                }
            }
            val testName2 = "[^a-zA-Z0-9]".toRegex().replace(testName, "-")
            val query = Query()
            query.workingDirectory = queryFile.substring(0, queryFile.lastIndexOf("/"))
            var res: Boolean
            GlobalLogger.log(ELoggerType.TEST_DETAIL, { "----------String Query" })
            GlobalLogger.log(ELoggerType.TEST_RESULT, { toParse })
            GlobalLogger.log(ELoggerType.TEST_DETAIL, { "----------Abstract Syntax Tree" })
            val lcit = LexerCharIterator(toParse)
            val tit = TokenIteratorSPARQLParser(lcit)
            val ltit = LookAheadTokenIterator(tit, 3)
            val parser = SPARQLParser(ltit)
            val ast_node = parser.expr()
            GlobalLogger.log(ELoggerType.TEST_DETAIL, { ast_node })
            GlobalLogger.log(ELoggerType.TEST_DETAIL, { "----------Logical Operator Graph" })
            val lop_node = ast_node.visit(OperatorGraphVisitor(query))
            File("log/${testName2}-Logical-Operator-Graph.tex").printWriter {
                it.println(OperatorGraphToLatex(lop_node.toXMLElement().toString(), testName2))
            }
            SanityCheck.check({ lop_node == lop_node.cloneOP() }, { lop_node.toString() + " - " + lop_node.cloneOP().toString() })
            GlobalLogger.log(ELoggerType.TEST_DETAIL, { lop_node.toXMLElement().toPrettyString() })
            GlobalLogger.log(ELoggerType.TEST_DETAIL, { "----------Logical Operator Graph optimized" })
            val lop_node2 = LogicalOptimizer(query).optimizeCall(lop_node)
            SanityCheck.check { lop_node2 == lop_node2.cloneOP() }
            File("log/${testName2}-Logical-Operator-Graph-Optimized.tex").printWriter {
                it.println(OperatorGraphToLatex(lop_node2.toXMLElement().toString(), testName2))
            }
            GlobalLogger.log(ELoggerType.TEST_DETAIL, { lop_node2.toXMLElement().toPrettyString() })
            GlobalLogger.log(ELoggerType.TEST_DETAIL, { "----------Physical Operator Graph" })
            val pop_optimizer = PhysicalOptimizer(query)
            val pop_node = pop_optimizer.optimizeCall(lop_node2)
            SanityCheck.check({ pop_node == pop_node.cloneOP() }, { pop_node.toString() + " - " + pop_node.cloneOP().toString() })
            SanityCheck { pop_node.toSparqlQuery() }
            File("log/${testName2}-Physical-Operator-Graph.tex").printWriter {
                it.println(OperatorGraphToLatex(pop_node.toXMLElement().toString(), testName2))
            }
            GlobalLogger.log(ELoggerType.TEST_DETAIL, { pop_node.toXMLElement().toPrettyString() })
            GlobalLogger.log(ELoggerType.TEST_DETAIL, { "----------Distributed Operator Graph" })
            val pop_distributed_node = KeyDistributionOptimizer(query).optimizeCall(pop_node)
            SanityCheck.check { pop_distributed_node == pop_distributed_node.cloneOP() }
            SanityCheck { pop_distributed_node.toSparqlQuery() }
            File("log/${testName2}-Distributed-Operator-Graph.tex").printWriter {
                it.println(OperatorGraphToLatex(pop_distributed_node.toXMLElement().toString(), testName2))
            }
            GlobalLogger.log(ELoggerType.TEST_DETAIL, { pop_distributed_node })
            var xmlQueryResult: XMLElement? = null
            if (!outputDataGraph.isEmpty() || (resultData != null && resultDataFileName != null)) {
                GlobalLogger.log(ELoggerType.TEST_DETAIL, { "----------Query Result" })
                xmlQueryResult = QueryResultToXMLElement.toXML(pop_distributed_node)
                GlobalLogger.log(ELoggerType.TEST_DETAIL, { "test xmlQueryResult :: " + xmlQueryResult.toPrettyString() })
                query.commit()
            }
            var verifiedOutput = false
            outputDataGraph.forEach {
                val outputData = readFileOrNull(it["filename"])
                var xmlGraphTarget = XMLElement.parseFromAny(outputData!!, it["filename"]!!)!!
                val tmp = DistributedTripleStore.getNamedGraph(query, it["name"]!!).getIterator(arrayOf(AOPVariable(query, "s"), AOPVariable(query, "p"), AOPVariable(query, "o")), EIndexPattern.SPO)
                var xmlGraphActual = QueryResultToXMLElement.toXML(tmp)
                if (!xmlGraphTarget.myEqualsUnclean(xmlGraphActual, true, true, true)) {
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "OutputData Graph[${it["name"]}] Original" })
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { outputData })
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Verify Output Data Graph[${it["name"]}] ... target,actual" })
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "test xmlGraphTarget :: " + xmlGraphTarget.toPrettyString() })
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "test xmlGraphActual :: " + xmlGraphActual.toPrettyString() })
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(PersistentStore Graph)" })
                    return false
                } else {
                    GlobalLogger.log(ELoggerType.TEST_DETAIL, { "OutputData Graph[${it["name"]}] Original" })
                    GlobalLogger.log(ELoggerType.TEST_DETAIL, { outputData })
                    GlobalLogger.log(ELoggerType.TEST_DETAIL, { "----------Verify Output Data Graph[${it["name"]}] ... target,actual" })
                    GlobalLogger.log(ELoggerType.TEST_DETAIL, { "test xmlGraphTarget :: " + xmlGraphTarget.toPrettyString() })
                    GlobalLogger.log(ELoggerType.TEST_DETAIL, { "test xmlGraphActual :: " + xmlGraphActual.toPrettyString() })
                }
                verifiedOutput = true
            }
            NodeManager.debug()
            if (resultData != null && resultDataFileName != null) {
                GlobalLogger.log(ELoggerType.TEST_DETAIL, { "----------Target Result" })
                var xmlQueryTarget = XMLElement.parseFromAny(resultData, resultDataFileName)!!
                GlobalLogger.log(ELoggerType.TEST_DETAIL, { "test xmlQueryTarget :: " + xmlQueryTarget.toPrettyString() })
                GlobalLogger.log(ELoggerType.TEST_DETAIL, { resultData })
                if (!ignoreJena) {
                    try {
                        val jenaResult = JenaWrapper.execQuery(toParse)
                        val jenaXML = XMLElement.parseFromXml(jenaResult)
//println("test xmlJena >>>>>"+jenaResult+"<<<<<")
                        if (jenaXML != null && !jenaXML.myEqualsUnclean(xmlQueryResult, true, true, true)) {
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Verify Output Jena jena,actual" })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "test jenaOriginal :: " + jenaResult })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "test xmlJena :: " + jenaXML.toPrettyString() })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "test xmlActual :: " + xmlQueryResult!!.toPrettyString() })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "test xmlTarget :: " + xmlQueryTarget.toPrettyString() })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(Jena)" })
                            return false
                        }
                    } catch (e: JenaBugException) {
                        SanityCheck.println({ e.message })
                        ignoreJena = true
                    } catch (e: Throwable) {
                        SanityCheck.println({ "TODO exception 43" })
                        e.printStackTrace()
                        ignoreJena = true
                    }
                }
                res = xmlQueryResult!!.myEquals(xmlQueryTarget)
                if (res) {
                    val xmlPOP = pop_distributed_node.toXMLElement()
                    val query4 = Query()
                    query4.workingDirectory = queryFile.substring(0, queryFile.lastIndexOf("/"))
                    val popNodeRecovered = XMLElement.convertToOPBase(query4, xmlPOP)
                    GlobalLogger.log(ELoggerType.TEST_DETAIL, { xmlPOP.toPrettyString() })
                    GlobalLogger.log(ELoggerType.TEST_DETAIL, { popNodeRecovered.toXMLElement().toPrettyString() })
                    val xmlQueryResultRecovered = QueryResultToXMLElement.toXML(popNodeRecovered)
                    query4.commit()
                    GlobalLogger.log(ELoggerType.TEST_DETAIL, { "test xmlQueryResultRecovered :: " + xmlQueryResultRecovered.toPrettyString() })
                    if (xmlQueryResultRecovered.myEquals(xmlQueryResult)) {
                        if (expectedResult) {
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success" })
                        } else {
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(expectFalse)" })
                        }
                    } else {
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(RecoverFromXMLOperatorGraph)" })
                        res = false
                    }
                } else {
                    val containsOrderBy = toParse.contains("ORDER", true)
                    val correctIfIgnoreOrderBy = xmlQueryResult.myEqualsUnclean(xmlQueryTarget, false, false, true)
                    val correctIfIgnoreString = xmlQueryResult.myEqualsUnclean(xmlQueryTarget, true, false, true)
                    val correctIfIgnoreNumber = xmlQueryResult.myEqualsUnclean(xmlQueryTarget, true, true, true)
                    val correctIfIgnoreAllExceptOrder = xmlQueryResult.myEqualsUnclean(xmlQueryTarget, true, true, false)
                    if (correctIfIgnoreNumber) {
                        if (expectedResult) {
                            if (containsOrderBy) {
                                if (correctIfIgnoreAllExceptOrder) {
                                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success" })
                                } else {
                                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                                    GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success(Unordered)" })
                                }
                            } else if (correctIfIgnoreOrderBy) {
                                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success" })
                            } else if (correctIfIgnoreString) {
                                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success(String)" })
                            } else if (correctIfIgnoreNumber) {
                                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success(Number & String)" })
                            } else {
                                SanityCheck.checkUnreachable()
                            }
                        } else {
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(expectFalse,Simplified)" })
                        }
                    } else {
                        if (expectedResult) {
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "test xmlQueryTarget :: " + xmlQueryTarget.toPrettyString() })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "test xmlQueryResult :: " + xmlQueryResult.toPrettyString() })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(Incorrect)" })
                        } else {
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success(ExpectFalse)" })
                        }
                    }
                }
                return res
            } else {
                if (verifiedOutput) {
                    if (expectedResult) {
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success(Graph)" })
                    } else {
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(ExpectFalse,Graph)" })
                    }
                } else {
                    if (expectedResult) {
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success(Syntax)" })
                    } else {
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                        GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(ExpectFalse,Syntax)" })
                    }
                }
                return expectedResult
            }
/*Coverage Unreachable*/
        } catch (e: ParseError) {
            if (expectedResult) {
                e.printStackTrace()
                GlobalLogger.log(ELoggerType.DEBUG, { e })
                GlobalLogger.log(ELoggerType.DEBUG, { "Error in the following line:" })
                GlobalLogger.log(ELoggerType.DEBUG, { e.lineNumber })
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(ParseError)" })
            } else {
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success(ExpectFalse,ParseError)" })
            }
            return false
        } catch (e: NotImplementedException) {
            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
            GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(NotImplemented)" })
            GlobalLogger.stacktrace(ELoggerType.TEST_RESULT, e)
            return false
        } catch (e: Luposdate3000Exception) {
            SanityCheck.println({ "lastStatement :: ${Coverage.CoverageMapGenerated[Coverage.lastcounter]}" })
            if (expectedResult) {
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(${e.classname})" })
                GlobalLogger.stacktrace(ELoggerType.TEST_RESULT, e)
            } else {
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success(ExpectFalse,${e.classname})" })
            }
            return false
        } catch (e: Throwable) {
            SanityCheck.println({ "TODO exception 44" })
            e.printStackTrace()
            SanityCheck.println({ "lastStatement :: ${Coverage.CoverageMapGenerated[Coverage.lastcounter]}" })
            if (expectedResult) {
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Failed(Throwable)" })
                GlobalLogger.stacktrace(ELoggerType.TEST_RESULT, e)
            } else {
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Time(${timer.elapsedNow().toDouble(DurationUnit.SECONDS)})" })
                GlobalLogger.log(ELoggerType.TEST_RESULT, { "----------Success(ExpectFalse,Throwable)" })
                GlobalLogger.stacktrace(ELoggerType.TEST_RESULT, e)
            }
            return false
        }
    }
}

class SevenIndices {
    private val s = mutableMapOf<Long, Array<Pair<Long, Long>>>()
    private val p = mutableMapOf<Long, Array<Pair<Long, Long>>>()
    private val o = mutableMapOf<Long, Array<Pair<Long, Long>>>()
    private val sp = mutableMapOf<Pair<Long, Long>, LongArray>()
    private val so = mutableMapOf<Pair<Long, Long>, LongArray>()
    private val po = mutableMapOf<Pair<Long, Long>, LongArray>()

    @JvmField
    val spo = mutableSetOf<ID_Triple>()
    fun s(key: Long): Array<Pair<Long, Long>> = this.s[key] ?: arrayOf()
    fun p(key: Long): Array<Pair<Long, Long>> = this.p[key] ?: arrayOf()
    fun o(key: Long): Array<Pair<Long, Long>> = this.o[key] ?: arrayOf()
    fun sp(key1: Long, key2: Long): LongArray = this.sp[Pair(key1, key2)] ?: longArrayOf()
    fun so(key1: Long, key2: Long): LongArray = this.so[Pair(key1, key2)] ?: longArrayOf()
    fun po(key1: Long, key2: Long): LongArray = this.po[Pair(key1, key2)] ?: longArrayOf()
    fun spo(key1: Long, key2: Long, key3: Long): Boolean = this.spo(ID_Triple(key1, key2, key3))
    fun spo(key: ID_Triple): Boolean = this.spo.contains(key)
    fun distinct() {
        distinctOneKeyMap(this.s)
        distinctOneKeyMap(this.p)
        distinctOneKeyMap(this.o)
        distinctTwoKeysMap(this.sp)
        distinctTwoKeysMap(this.so)
        distinctTwoKeysMap(this.po)
        // duplicates are already eliminated in this.spo!
    }

    fun add(triple_s: Long, triple_p: Long, triple_o: Long) {
        addToOneKeyMap(this.s, triple_s, triple_p, triple_o)
        addToOneKeyMap(this.p, triple_p, triple_s, triple_o)
        addToOneKeyMap(this.o, triple_o, triple_s, triple_p)
        addToTwoKeysMap(this.sp, triple_s, triple_p, triple_o)
        addToTwoKeysMap(this.so, triple_s, triple_o, triple_p)
        addToTwoKeysMap(this.po, triple_p, triple_o, triple_s)
        this.spo += ID_Triple(triple_s, triple_p, triple_o)
    }

    private fun addToOneKeyMap(onekeymap: MutableMap<Long, Array<Pair<Long, Long>>>, key: Long, value1: Long, value2: Long) {
        val values = onekeymap[key]
        val value = Pair(value1, value2)
        if (values == null) {
            onekeymap[key] = arrayOf(value)
        } else {
            onekeymap[key] = values + value
        }
    }

    private fun addToTwoKeysMap(twokeysmap: MutableMap<Pair<Long, Long>, LongArray>, key1: Long, key2: Long, value: Long) {
        val key = Pair(key1, key2)
        val values = twokeysmap[key]
        if (values == null) {
            twokeysmap[key] = longArrayOf(value)
        } else {
            twokeysmap[key] = values + value
        }
    }

    private fun distinctOneKeyMap(onekeymap: MutableMap<Long, Array<Pair<Long, Long>>>) {
        for (entry in onekeymap) {
            entry.setValue(entry.value.toMutableSet().toTypedArray())
        }
    }

    private fun distinctTwoKeysMap(twokeysmap: MutableMap<Pair<Long, Long>, LongArray>) {
        for (entry in twokeysmap) {
            entry.setValue(entry.value.toMutableSet().toLongArray())
        }
    }
}
