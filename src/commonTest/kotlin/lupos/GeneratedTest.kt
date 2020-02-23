package lupos

import lupos.s02buildSyntaxTree.sparql1_1.*
import lupos.s03resultRepresentation.*
import lupos.s04arithmetikOperators.*
import lupos.s04arithmetikOperators.multiinput.*
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04arithmetikOperators.singleinput.*
import lupos.s04logicalOperators.*
import lupos.s04logicalOperators.noinput.*
import lupos.s04logicalOperators.singleinput.*
import lupos.s04logicalOperators.singleinput.modifiers.*
import lupos.s08logicalOptimisation.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


class AOPVariableTest {
    fun setAggregationMode(node: OPBase, mode: Boolean, count: Int) {
        for (n in node.children)
            setAggregationMode(n, mode, count)
        if (node is AOPAggregation) {
            node.count = count
            node.collectMode = mode
            if (node.collectMode)
                node.a = null
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg01_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf(AOPVariable("O"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(5)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg02_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf(AOPVariable("O"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(3)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf(AOPVariable("O"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(2)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg03_rq() = listOf(
            /*{
                MicroTest0(
                        AOPGT(AOPInteger(3), AOPInteger(2)),
                        AOPBoolean(true)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf(AOPVariable("O"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(3)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf(AOPVariable("O"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(3)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPGT(AOPInteger(2), AOPInteger(2)),
                        AOPBoolean(false)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf(AOPVariable("O"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(2)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf(AOPVariable("O"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(2)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg03.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg04_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf()),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(5)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg04.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg05_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf()),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(3)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf()),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(2)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg05.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg06_rq() = listOf(
            /*{
                MicroTest0(
                        AOPGT(AOPInteger(5), AOPInteger(0)),
                        AOPBoolean(true)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf()),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(5)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf()),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(5)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg06.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg07_rq() = listOf(
            /*{
                MicroTest0(
                        AOPGT(AOPInteger(3), AOPInteger(2)),
                        AOPBoolean(true)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf()),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(3)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf()),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p1>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o3>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(3)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPGT(AOPInteger(2), AOPInteger(2)),
                        AOPBoolean(false)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf()),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(2)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("S")
                resultSet.createVariable("P")
                resultSet.createVariable("O")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf()),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o1>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://www.example.org/p2>")
                                resultRow[resultSet.createVariable("O")] = resultSet.createValue("<http://www.example.org/o2>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(2)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg07.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg08b_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("O1")
                resultSet.createVariable("#2")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTest1(
                        AOPAddition(AOPVariable("O2"), AOPVariable("O1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(0)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("O1")
                resultSet.createVariable("#2")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTest1(
                        AOPAddition(AOPVariable("O2"), AOPVariable("O1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(1)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("O1")
                resultSet.createVariable("#2")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTest1(
                        AOPAddition(AOPVariable("O2"), AOPVariable("O1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("O1")
                resultSet.createVariable("#2")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTest1(
                        AOPAddition(AOPVariable("O2"), AOPVariable("O1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(1)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("O1")
                resultSet.createVariable("#2")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTest1(
                        AOPAddition(AOPVariable("O2"), AOPVariable("O1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("O1")
                resultSet.createVariable("#2")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTest1(
                        AOPAddition(AOPVariable("O2"), AOPVariable("O1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("O1")
                resultSet.createVariable("#2")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTest1(
                        AOPAddition(AOPVariable("O2"), AOPVariable("O1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("O1")
                resultSet.createVariable("#2")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTest1(
                        AOPAddition(AOPVariable("O2"), AOPVariable("O1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("O1")
                resultSet.createVariable("#2")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTest1(
                        AOPAddition(AOPVariable("O2"), AOPVariable("O1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(4)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("O12")
                resultSet.createVariable("#1")
                resultSet.createVariable("O1")
                resultSet.createVariable("#3")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf(AOPVariable("O1"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("O12")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                                resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(1)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("O12")
                resultSet.createVariable("#1")
                resultSet.createVariable("O1")
                resultSet.createVariable("#3")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf(AOPVariable("O1"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("O12")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                                resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("O12")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                                resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(2)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("O12")
                resultSet.createVariable("#1")
                resultSet.createVariable("O1")
                resultSet.createVariable("#3")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf(AOPVariable("O1"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("O12")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                                resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("O12")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                                resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("O12")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                                resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(3)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("O12")
                resultSet.createVariable("#1")
                resultSet.createVariable("O1")
                resultSet.createVariable("#3")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf(AOPVariable("O1"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("O12")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                                resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("O12")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                                resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(2)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("O12")
                resultSet.createVariable("#1")
                resultSet.createVariable("O1")
                resultSet.createVariable("#3")
                resultSet.createVariable("O2")
                resultSet.createVariable("S")
                MicroTestN(
                        AOPAggregation(Aggregation.COUNT,false,arrayOf(AOPVariable("O1"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("O12")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("O1")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                                resultRow[resultSet.createVariable("O2")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow[resultSet.createVariable("S")] = resultSet.createValue("<http://www.example.org/s>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(1)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg08b.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg_sum_01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.SUM, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                    resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                    resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDecimal(11.100000000000001)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg-sum-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg_sum_02_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.SUM, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDecimal(6.7)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.SUM, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"100.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"30000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDouble(32100.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.SUM, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPInteger(6)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.SUM, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDecimal(3.2)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.SUM, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"0.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"0.2\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDouble(0.4)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg-sum-02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg_avg_01_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDecimal(2.22)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg-avg-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg_avg_02_rq() = listOf(
            /*{
                MicroTest0(
                        AOPLEQ(AOPDecimal(0.6666666666666666), AOPDecimal(2.0)),
                        AOPBoolean(true)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPLEQ(AOPDecimal(2.1333333333333333), AOPDecimal(2.0)),
                        AOPBoolean(true)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPLEQ(AOPDecimal(4.466666666666667), AOPDecimal(2.0)),
                        AOPBoolean(false)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDecimal(4.466666666666667)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDecimal(2.2333333333333334)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPLEQ(AOPDouble(66.66666666666667), AOPDecimal(2.0)),
                        AOPBoolean(false)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPLEQ(AOPDouble(1400.0), AOPDecimal(2.0)),
                        AOPBoolean(false)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPLEQ(AOPDouble(21400.0), AOPDecimal(2.0)),
                        AOPBoolean(false)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"100.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"100.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"30000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"30000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDouble(21400.0)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"100.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"30000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDouble(10700.0)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPLEQ(AOPDecimal(2.0), AOPDecimal(2.0)),
                        AOPBoolean(true)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPLEQ(AOPDecimal(4.0), AOPDecimal(2.0)),
                        AOPBoolean(false)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDecimal(4.0)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDecimal(2.0)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPLEQ(AOPDecimal(2.2), AOPDecimal(2.0)),
                        AOPBoolean(true)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPLEQ(AOPDecimal(3.2), AOPDecimal(2.0)),
                        AOPBoolean(false)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDecimal(3.2)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDecimal(1.6)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPLEQ(AOPDecimal(0.2), AOPDecimal(2.0)),
                        AOPBoolean(true)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPLEQ(AOPDouble(0.4), AOPDecimal(2.0)),
                        AOPBoolean(true)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"0.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"0.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"0.2\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"0.2\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDouble(0.4)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"0.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"0.2\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDouble(0.2)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg-avg-02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg_min_01_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.MIN,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDecimal(1.0)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg-min-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg_min_02_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.MIN, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDecimal(1.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.MIN, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"100.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"30000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDouble(100.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.MIN, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPInteger(1)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.MIN, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPInteger(1)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.MIN, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"0.2\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDouble(0.2)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg-min-02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg_max_01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.MAX, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"100.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"30000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"0.2\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDouble(30000.0)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg-max-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg_max_02_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.MAX, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDecimal(3.5)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.MAX, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"100.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/doubles>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"30000.0\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDouble(30000.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.MAX, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/ints>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.MAX, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/int>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDecimal(2.2)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.MAX, false, arrayOf(AOPVariable("o"))),
                        listOf(
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/dec>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                    resultRow
                                }(),
                                {
                                    val resultRow = resultSet.createResultRow()
                                    resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                    resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://www.example.org/double>")
                                    resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"0.2\"^^<http://www.w3.org/2001/XMLSchema#double>")
                                    resultRow
                                }()
                        ),
                        resultSet,
                        AOPDecimal(2.2)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg-max-02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg_sample_01_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("o")
                MicroTestN(
                        AOPAggregation(Aggregation.SAMPLE,false,arrayOf(AOPVariable("o"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/decimals>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed1>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://www.example.org/mixed2>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDecimal(2.2)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("sample")
                MicroTest1(
                        AOPEQ(AOPVariable("sample"), AOPDecimal(3.5)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("sample")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("sample")
                MicroTest1(
                        AOPEQ(AOPVariable("sample"), AOPDecimal(2.2)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("sample")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("sample")
                MicroTest1(
                        AOPEQ(AOPVariable("sample"), AOPDecimal(1.0)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("sample")] = resultSet.createValue("\"2.2\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPOr(AOPBoolean(true), AOPBoolean(false)),
                        AOPBoolean(true)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPOr(AOPBoolean(false), AOPBoolean(true)),
                        AOPBoolean(true)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg-sample-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_aggregates_agg_err_01_rq() = listOf(
            /*{
                MicroTest0(
                        AOPAddition(AOPInteger(1), AOPInteger(1)),
                        AOPInteger(2)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPDivision(AOPInteger(2), AOPInteger(2)),
                        AOPInteger(1)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPAddition(AOPInteger(2), AOPInteger(1)),
                        AOPInteger(3)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPDivision(AOPInteger(2), AOPInteger(3)),
                        AOPInteger(0)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPAddition(AOPInteger(3), AOPInteger(1)),
                        AOPInteger(4)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPDivision(AOPInteger(2), AOPInteger(4)),
                        AOPInteger(0)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPAddition(AOPInteger(4), AOPInteger(1)),
                        AOPInteger(5)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPDivision(AOPInteger(2), AOPInteger(5)),
                        AOPInteger(0)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("g")
                resultSet.createVariable("#1")
                resultSet.createVariable("p")
                MicroTestN(
                        AOPAggregation(Aggregation.MAX,false,arrayOf(AOPVariable("p"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(4)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("g")
                resultSet.createVariable("#1")
                resultSet.createVariable("p")
                MicroTestN(
                        AOPAggregation(Aggregation.MIN,false,arrayOf(AOPVariable("p"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(1)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("g")
                resultSet.createVariable("#1")
                resultSet.createVariable("p")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("p"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#x>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDecimal(2.5)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPAddition(AOPDecimal(1.0), AOPDecimal(1.0)),
                        AOPDecimal(2.0)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPDivision(AOPInteger(2), AOPDecimal(2.0)),
                        AOPDecimal(1.0)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPAddition(AOPDecimal(2.0), AOPDecimal(1.0)),
                        AOPDecimal(3.0)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPDivision(AOPInteger(2), AOPDecimal(3.0)),
                        AOPDecimal(0.6666666666666666)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPAddition(AOPDecimal(3.0), AOPDecimal(1.0)),
                        AOPDecimal(4.0)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPDivision(AOPInteger(2), AOPDecimal(4.0)),
                        AOPDecimal(0.5)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPAddition(AOPInteger(4), AOPDecimal(1.0)),
                        AOPDecimal(5.0)
                )
            }()*/
            /*{
                MicroTest0(
                        AOPDivision(AOPInteger(2), AOPDecimal(5.0)),
                        AOPDecimal(0.4)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("g")
                resultSet.createVariable("#1")
                resultSet.createVariable("p")
                MicroTestN(
                        AOPAggregation(Aggregation.MAX,false,arrayOf(AOPVariable("p"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(4)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("g")
                resultSet.createVariable("#1")
                resultSet.createVariable("p")
                MicroTestN(
                        AOPAggregation(Aggregation.MIN,false,arrayOf(AOPVariable("p"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDecimal(1.0)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("g")
                resultSet.createVariable("#1")
                resultSet.createVariable("p")
                MicroTestN(
                        AOPAggregation(Aggregation.AVG,false,arrayOf(AOPVariable("p"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#y>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                                resultRow
                            }(),
                            {
                                val resultRow = resultSet.createResultRow()
                                resultRow[resultSet.createVariable("g")] = resultSet.createValue("<http://example.com/data/#z>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                                resultRow[resultSet.createVariable("p")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPDecimal(2.5)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/aggregates/agg-err-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_bind_bind01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(11)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(12)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(13)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(14)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/bind/bind01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_bind_bind02_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(11)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(12)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(100), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"11\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(101)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(13)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(100), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"12\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(102)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(14)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(100), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"13\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(103)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(100), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"14\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(104)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/bind/bind02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_bind_bind03_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(4)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(5)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/bind/bind03.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_bind_bind05_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(4)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(5)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"5\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/bind/bind05.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_bind_bind06_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(11)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(12)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(13)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(14)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/bind/bind06.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_bind_bind08_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(4)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(5)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"5\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/bind/bind08.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_bind_bind10_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("#2")
                resultSet.createVariable("v")
                MicroTest1(
                        AOPEQ(AOPVariable("v"), AOPVariable("z")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("z"))
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("v")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("#2")
                resultSet.createVariable("v")
                MicroTest1(
                        AOPEQ(AOPVariable("v"), AOPVariable("z")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("z"))
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("v")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("#2")
                resultSet.createVariable("v")
                MicroTest1(
                        AOPEQ(AOPVariable("v"), AOPVariable("z")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("z"))
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("v")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("#2")
                resultSet.createVariable("v")
                MicroTest1(
                        AOPEQ(AOPVariable("v"), AOPVariable("z")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("z"))
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("v")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/bind/bind10.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_bind_bind11_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("#2")
                resultSet.createVariable("v")
                MicroTest1(
                        AOPEQ(AOPVariable("v"), AOPVariable("z")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("v")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("#2")
                resultSet.createVariable("v")
                MicroTest1(
                        AOPEQ(AOPVariable("v"), AOPVariable("z")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("v")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("#2")
                resultSet.createVariable("v")
                MicroTest1(
                        AOPEQ(AOPVariable("v"), AOPVariable("z")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("v")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("#2")
                resultSet.createVariable("v")
                MicroTest1(
                        AOPEQ(AOPVariable("v"), AOPVariable("z")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("v")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/bind/bind11.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_entailment_bind01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(11)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(12)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(13)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(14)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/entailment/bind01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_entailment_bind02_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(11)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(12)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(13)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(100), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"11\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(101)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(14)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(100), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"12\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(102)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(100), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"13\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(103)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(100), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"14\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(104)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/entailment/bind02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_entailment_bind03_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(4)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(5)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/entailment/bind03.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_entailment_bind05_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(4)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(5)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("o")
                resultSet.createVariable("#3")
                resultSet.createVariable("#4")
                resultSet.createVariable("p")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("o")
                resultSet.createVariable("#3")
                resultSet.createVariable("#4")
                resultSet.createVariable("p")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("o")
                resultSet.createVariable("#3")
                resultSet.createVariable("#4")
                resultSet.createVariable("p")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("o")
                resultSet.createVariable("#3")
                resultSet.createVariable("#4")
                resultSet.createVariable("p")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"5\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/entailment/bind05.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_entailment_bind06_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(11)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(12)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(13)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(10), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(14)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/entailment/bind06.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_entailment_bind08_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(4)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPAddition(AOPInteger(1), AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(5)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("o")
                resultSet.createVariable("#3")
                resultSet.createVariable("#4")
                resultSet.createVariable("p")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("o")
                resultSet.createVariable("#3")
                resultSet.createVariable("#4")
                resultSet.createVariable("p")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("o")
                resultSet.createVariable("#3")
                resultSet.createVariable("#4")
                resultSet.createVariable("p")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("z")
                resultSet.createVariable("s")
                resultSet.createVariable("o")
                resultSet.createVariable("#3")
                resultSet.createVariable("#4")
                resultSet.createVariable("p")
                MicroTest1(
                        AOPEQ(AOPVariable("z"), AOPInteger(3)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"5\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/p>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/entailment/bind08.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_strdt01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallLANGMATCHES(AOPSimpleLiteral("\"", ""), AOPSimpleLiteral("\"", "en")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "en")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallLANGMATCHES(AOPSimpleLiteral("\"", "en"), AOPSimpleLiteral("\"", "en")),
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("str"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/strdt01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_strdt02_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            /*{
                MicroTest0(
                        AOPBuildInCallLANGMATCHES(AOPSimpleLiteral("\"", ""), AOPSimpleLiteral("\"", "en")),
                        AOPBoolean(false)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "en")
                )
            }()*/
            /*{
                MicroTest0(
                        AOPBuildInCallLANGMATCHES(AOPSimpleLiteral("\"", "en"), AOPSimpleLiteral("\"", "en")),
                        AOPBoolean(true)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "bar", "en")
                )
            }()*/
            /*{
                MicroTest0(
                        AOPBuildInCallSTRDT(AOPLanguageTaggedLiteral("\"", "bar", "en"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/strdt02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_strdt03_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n5>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "foo", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "BAZ", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "食べ物", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "100%", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRDT only works with simple string input and iri datatype")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/strdt03.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_strlang01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallLANGMATCHES(AOPSimpleLiteral("\"", ""), AOPSimpleLiteral("\"", "en")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "en")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallLANGMATCHES(AOPSimpleLiteral("\"", "en"), AOPSimpleLiteral("\"", "en")),
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("str"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/strlang01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_strlang02_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            /*{
                MicroTest0(
                        AOPBuildInCallLANGMATCHES(AOPSimpleLiteral("\"", ""), AOPSimpleLiteral("\"", "en")),
                        AOPBoolean(false)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "en")
                )
            }()*/
            /*{
                MicroTest0(
                        AOPBuildInCallLANGMATCHES(AOPSimpleLiteral("\"", "en"), AOPSimpleLiteral("\"", "en")),
                        AOPBoolean(true)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "bar", "en")
                )
            }()*/
            /*{
                MicroTest0(
                        AOPBuildInCallSTRLANG(AOPLanguageTaggedLiteral("\"", "bar", "en"), AOPSimpleLiteral("\"", "en-US")),
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/strlang02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_strlang03_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n5>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "foo", "en-us")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "BAZ", "en-us")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "食べ物", "en-us")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "100%", "en-us")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRLANG only works with simple string input")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/strlang03.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_isnumeric01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n5>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallIsNUMERIC(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/isnumeric01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_abs01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallABS(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(1)
                )
            }(),
            {
                MicroTest0(
                        AOPGEQ(AOPInteger(1), AOPInteger(2)),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallABS(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(1.6)
                )
            }(),
            {
                MicroTest0(
                        AOPGEQ(AOPDecimal(1.6), AOPInteger(2)),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallABS(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(1.1)
                )
            }(),
            {
                MicroTest0(
                        AOPGEQ(AOPDecimal(1.1), AOPInteger(2)),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallABS(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }(),
            {
                MicroTest0(
                        AOPGEQ(AOPInteger(2), AOPInteger(2)),
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallABS(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(2.5)
                )
            }(),
            {
                MicroTest0(
                        AOPGEQ(AOPDecimal(2.5), AOPInteger(2)),
                        AOPBoolean(true)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/abs01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_ceil01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallCEIL(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(-1)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallCEIL(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(-1.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallCEIL(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(2.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallCEIL(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(-2)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallCEIL(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(3.0)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/ceil01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_floor01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallFLOOR(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(-2.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallFLOOR(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(1.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallFLOOR(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(2.0)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/floor01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_round01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallROUND(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(-1)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallROUND(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(-2.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallROUND(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(1.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallROUND(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(-2)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("num")
                MicroTest1(
                        AOPBuildInCallROUND(AOPVariable("num")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("num")] = resultSet.createValue("\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(3.0)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/round01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_concat01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("#3")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "abcDEF", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/concat01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_concat02_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"123\"")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"123\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "123123")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"123\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "日本語123", "ja")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"english\"@en")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"123\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "english123", "en")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"français\"@fr")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"123\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "français123", "fr")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"123\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "abc123")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"123\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "def123")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"123\"")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"123\"")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "123日本語", "ja")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "日本語日本語", "ja")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"english\"@en")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "english日本語")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"français\"@fr")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "français日本語")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "abc日本語")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "def日本語")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"123\"")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"english\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "123english", "en")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"english\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "日本語english")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"english\"@en")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"english\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "englishenglish", "en")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"français\"@fr")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"english\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "françaisenglish")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"english\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "abcenglish")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"english\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "defenglish")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"english\"@en")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"123\"")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"français\"@fr")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "123français", "fr")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"français\"@fr")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "日本語français")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"english\"@en")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"français\"@fr")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "englishfrançais")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"français\"@fr")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"français\"@fr")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "françaisfrançais", "fr")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"français\"@fr")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "abcfrançais")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"français\"@fr")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "deffrançais")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"français\"@fr")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"123\"")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "123abc")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "日本語abc")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"english\"@en")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "englishabc")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"français\"@fr")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "françaisabc")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "abcabc", "http://www.w3.org/2001/XMLSchema#string")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "defabc", "http://www.w3.org/2001/XMLSchema#string")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"123\"")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "123def")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "日本語def")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"english\"@en")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "englishdef")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"français\"@fr")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "françaisdef")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "abcdef", "http://www.w3.org/2001/XMLSchema#string")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "defdef", "http://www.w3.org/2001/XMLSchema#string")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"123\"")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"english\"@en")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"français\"@fr")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s1")
                resultSet.createVariable("#1")
                resultSet.createVariable("str1")
                resultSet.createVariable("s2")
                resultSet.createVariable("#4")
                resultSet.createVariable("str2")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str1")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("str2")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall CONCAT only works with compatible string input")
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/concat02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_length01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRLEN(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRLEN(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRLEN(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRLEN(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRLEN(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(4)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRLEN(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRLEN(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/length01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_ucase01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallUCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "FOO")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallUCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "BAR", "en")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallUCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "BAZ")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallUCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "食べ物")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallUCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "100%")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallUCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "ABC", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallUCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "DEF", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/ucase01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_lcase01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "foo")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "bar", "en")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "baz")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "食べ物")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "100%")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "abc", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallLCASE(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "def", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/lcase01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_contains01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallCONTAINS(AOPVariable("str"), AOPSimpleLiteral("\"", "a")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallCONTAINS(AOPVariable("str"), AOPSimpleLiteral("\"", "a")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallCONTAINS(AOPVariable("str"), AOPSimpleLiteral("\"", "a")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallCONTAINS(AOPVariable("str"), AOPSimpleLiteral("\"", "a")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallCONTAINS(AOPVariable("str"), AOPSimpleLiteral("\"", "a")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallCONTAINS(AOPVariable("str"), AOPSimpleLiteral("\"", "a")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallCONTAINS(AOPVariable("str"), AOPSimpleLiteral("\"", "a")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/contains01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_starts01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "2010-06-21T11:28:01Z")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "2010-06-21T11:28:01Z"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "2010-12-21T15:38:02-08:00")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "2010-12-21T15:38:02-08:00"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "2008-06-20T23:59:00Z")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "2008-06-20T23:59:00Z"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "2011-02-01T01:02:03")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "2011-02-01T01:02:03"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "-1")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "-1"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "-1.6")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "-1.6"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "1.1")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "1.1"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "-2")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "-2"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n5>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "2.5")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "2.5"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "foo")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "foo"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPLanguageTaggedLiteral("\"", "bar", "en")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPLanguageTaggedLiteral("\"", "bar", "en"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "BAZ")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "BAZ"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "食べ物")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "食べ物"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "100%")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPSimpleLiteral("\"", "100%"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "abc", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPTypedLiteral("\"", "abc", "http://www.w3.org/2001/XMLSchema#string"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("str")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "DEF", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallSTRSTARTS(AOPTypedLiteral("\"", "DEF", "http://www.w3.org/2001/XMLSchema#string"), AOPSimpleLiteral("\"", "1")),
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/starts01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_ends01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRENDS only works with string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRENDS only works with string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRENDS only works with string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/date>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRENDS only works with string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRENDS only works with string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRENDS only works with string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRENDS only works with string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRENDS only works with string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/n5>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/num>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STRENDS only works with string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("str")
                MicroTest1(
                        AOPBuildInCallSTRENDS(AOPVariable("str"), AOPSimpleLiteral("\"", "bc")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("str")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/ends01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_plus_1_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPAddition(AOPVariable("y"), AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("_:b")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x2>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPAddition only works with numeric input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPAddition(AOPVariable("y"), AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://example/a>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x3>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPAddition only works with numeric input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPAddition(AOPVariable("y"), AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x4>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPAddition(AOPVariable("y"), AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x5>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(3.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPAddition(AOPVariable("y"), AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1\"")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x6>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPAddition only works with numeric input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPAddition(AOPVariable("y"), AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x7>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPAddition only works with numeric input")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/plus-1.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_plus_2_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("y")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"a\"")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x1>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "1")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"a\"")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x1>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "a")
                )
            }(),
            {
                MicroTest0(
                        AOPAddition(AOPSimpleLiteral("\"", "1"), AOPSimpleLiteral("\"", "a")),
                        Exception("AOPAddition only works with numeric input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("y")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("_:b")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x2>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "1")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("_:b")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x2>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall STR only works with string input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("y")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://example/a>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x3>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "1")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("y")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x4>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "2")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x4>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "1")
                )
            }(),
            {
                MicroTest0(
                        AOPAddition(AOPSimpleLiteral("\"", "2"), AOPSimpleLiteral("\"", "1")),
                        Exception("AOPAddition only works with numeric input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("y")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x5>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "2")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x5>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "1.0")
                )
            }(),
            {
                MicroTest0(
                        AOPAddition(AOPSimpleLiteral("\"", "2"), AOPSimpleLiteral("\"", "1.0")),
                        Exception("AOPAddition only works with numeric input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("y")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1\"")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x6>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "2")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1\"")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x6>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "1")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("y")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x7>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "2")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x7>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "1", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                MicroTest0(
                        AOPAddition(AOPSimpleLiteral("\"", "2"), AOPTypedLiteral("\"", "1", "http://www.w3.org/2001/XMLSchema#string")),
                        Exception("AOPAddition only works with numeric input")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("y")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x8>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "2")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("y")
                resultSet.createVariable("s")
                MicroTest1(
                        AOPBuildInCallSTR(AOPVariable("x")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/x8>")
                            resultRow
                        }(),
                        resultSet,
                        AOPTypedLiteral("\"", "1", "http://www.w3.org/2001/XMLSchema#string")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/plus-2.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_md5_01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("#1")
                resultSet.createVariable("l")
                MicroTest1(
                        AOPBuildInCallMD5(AOPVariable("l")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("l")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "acbd18db4cc2f85cedef654fccc4a4d8")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/md5-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_md5_02_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("#1")
                resultSet.createVariable("l")
                MicroTest1(
                        AOPBuildInCallMD5(AOPVariable("l")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("l")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "e7ada485d13b1decf628c9211bc3a97b")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/md5-02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_sha1_01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("#1")
                resultSet.createVariable("l")
                MicroTest1(
                        AOPBuildInCallSHA1(AOPVariable("l")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("l")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/sha1-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_sha1_02_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("#1")
                resultSet.createVariable("l")
                MicroTest1(
                        AOPBuildInCallSHA1(AOPVariable("l")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("l")] = resultSet.createValue("\"食\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "d46696735b6a09ff407bfc1a9407e008840db9c9")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/sha1-02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_sha256_01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("#1")
                resultSet.createVariable("l")
                MicroTest1(
                        AOPBuildInCallSHA256(AOPVariable("l")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("l")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/sha256-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_sha256_02_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("#1")
                resultSet.createVariable("l")
                MicroTest1(
                        AOPBuildInCallSHA256(AOPVariable("l")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("l")] = resultSet.createValue("\"食\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "0fbe868d1df356ca9df7ebff346da3a56280e059a7ea81186ef885b140d254ee")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/sha256-02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_minutes_01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallMINUTES(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(28)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallMINUTES(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(38)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallMINUTES(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(59)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallMINUTES(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/minutes-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_seconds_01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallSECONDS(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(1.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallSECONDS(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(2.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallSECONDS(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(0.0)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallSECONDS(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPDecimal(3.0)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/seconds-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_hours_01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallHOURS(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(11)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallHOURS(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(15)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallHOURS(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(23)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallHOURS(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(1)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/hours-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_month_01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallMONTH(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(6)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallMONTH(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(12)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallMONTH(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(6)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallMONTH(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/month-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_year_01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallYEAR(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2010)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallYEAR(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2010)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallYEAR(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2008)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallYEAR(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2011)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/year-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_day_01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallDAY(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(21)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallDAY(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(21)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallDAY(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(20)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallDAY(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(1)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/day-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_timezone_01_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallTIMEZONE(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "\"PT0S\"^^<http://www.w3.org/2001/XMLSchema#dayTimeDuration>")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallTIMEZONE(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "\"-PT8H\"^^<http://www.w3.org/2001/XMLSchema#dayTimeDuration>")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallTIMEZONE(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "\"PT0S\"^^<http://www.w3.org/2001/XMLSchema#dayTimeDuration>")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallTIMEZONE(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/timezone-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_tz_01_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallTZ(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "\"Z\"")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallTZ(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "\"-08:00\"")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallTZ(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "\"Z\"")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("#1")
                resultSet.createVariable("date")
                MicroTest1(
                        AOPBuildInCallTZ(AOPVariable("date")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/d4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("date")] = resultSet.createValue("\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "\"\"")
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/tz-01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_bnode01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                MicroTest0(
                        AOPOr(AOPBoolean(false), AOPBoolean(true)),
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(
                        AOPOr(AOPBoolean(false), AOPBoolean(false)),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPBuildInCallBNODE1(AOPVariable("s2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBnode("27392\"foo\"")
                )
            }()*/
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("b2")
                resultSet.createVariable("a")
                resultSet.createVariable("#2")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#5")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPBuildInCallBNODE1(AOPVariable("s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("b2")] = resultSet.createValue("_:27392\"foo\"")
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#5"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBnode("27386\"foo\"")
                )
            }()*/
            {
                MicroTest0(
                        AOPOr(AOPBoolean(true), AOPBoolean(false)),
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPBuildInCallBNODE1(AOPVariable("s2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBnode("27392\"foo\"")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("b2")
                resultSet.createVariable("a")
                resultSet.createVariable("#2")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#5")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPBuildInCallBNODE1(AOPVariable("s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("b2")] = resultSet.createValue("_:27392\"foo\"")
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#5"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBnode("27386\"BAZ\"")
                )
            }()*/
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"foo\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"bar\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPBuildInCallBNODE1(AOPVariable("s2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBnode("27392\"BAZ\"")
                )
            }()*/
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("b2")
                resultSet.createVariable("a")
                resultSet.createVariable("#2")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#5")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPBuildInCallBNODE1(AOPVariable("s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("b2")] = resultSet.createValue("_:27392\"BAZ\"")
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#5"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBnode("27386\"foo\"")
                )
            }()*/
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPBuildInCallBNODE1(AOPVariable("s2")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBnode("27392\"BAZ\"")
                )
            }()*/
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("b2")
                resultSet.createVariable("a")
                resultSet.createVariable("#2")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#5")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPBuildInCallBNODE1(AOPVariable("s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("b2")] = resultSet.createValue("_:27392\"BAZ\"")
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#5"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBnode("27386\"BAZ\"")
                )
            }()*/
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("a"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"BAZ\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"食べ物\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"100%\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s1>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"foo\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s2>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"bar\"@en")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s3>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"BAZ\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s4>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"食べ物\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s5>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"100%\"")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s6>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s3")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("a")
                resultSet.createVariable("#1")
                resultSet.createVariable("s1")
                resultSet.createVariable("b")
                resultSet.createVariable("#4")
                resultSet.createVariable("s2")
                MicroTest1(
                        AOPEQ(AOPVariable("b"), AOPIri("http://example.org/s1")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("a")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("s1")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow[resultSet.createVariable("b")] = resultSet.createValue("<http://example.org/s7>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#4"))
                            resultRow[resultSet.createVariable("s2")] = resultSet.createValue("\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/bnode01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_bnode02_rq() = listOf(
            /*{
                MicroTest0(
                        AOPBuildInCallBNODE0(),
                        AOPBnode("3120131226")
                )
            }()*/
            /*{
                MicroTest0(
                        AOPBuildInCallBNODE0(),
                        AOPBnode("3119631228")
                )
            }()*/
            /*{
                MicroTest0(
                        AOPBuildInCallBNODE0(),
                        AOPBnode("3123231250")
                )
            }()*/
            /*{
                MicroTest0(
                        AOPBuildInCallBNODE0(),
                        AOPBnode("3123931252")
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/bnode02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_now01_rq() = listOf(
            /*{
                MicroTest0(
                        AOPBuildInCallNOW(),
                        AOPDateTime("\"2020-02-23T16:48:37Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("n")
                MicroTest1(
                        AOPBuildInCallDATATYPE(AOPVariable("n")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("n")] = resultSet.createValue("\"2020-02-23T16:48:37Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")
                            resultRow
                        }(),
                        resultSet,
                        AOPIri("http://www.w3.org/2001/XMLSchema#dateTime")
                )
            }()*/
            /*{
                MicroTest0(
                        AOPEQ(AOPIri("http://www.w3.org/2001/XMLSchema#dateTime"), AOPIri("http://www.w3.org/2001/XMLSchema#dateTime")),
                        AOPBoolean(true)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/now01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_iri01_rq() = listOf(
            {
                MicroTest0(
                        AOPBuildInCallIRI(AOPSimpleLiteral("\"", "iri"), "http://example.org/"),
                        AOPIri("http://example.org/iri")
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallURI(AOPSimpleLiteral("\"", "uri"), "http://example.org/"),
                        AOPIri("http://example.org/uri")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/iri01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_if01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s1>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"123\"")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                MicroTest0(
                        AOPEQ(AOPSimpleLiteral("\"", ""), AOPSimpleLiteral("\"", "ja")),
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallIF(AOPBoolean(false), AOPBoolean(true), AOPBoolean(false)),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s2>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"日本語\"@ja")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "ja")
                )
            }(),
            {
                MicroTest0(
                        AOPEQ(AOPSimpleLiteral("\"", "ja"), AOPSimpleLiteral("\"", "ja")),
                        AOPBoolean(true)
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallIF(AOPBoolean(true), AOPBoolean(true), AOPBoolean(false)),
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s3>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"english\"@en")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "en")
                )
            }(),
            {
                MicroTest0(
                        AOPEQ(AOPSimpleLiteral("\"", "en"), AOPSimpleLiteral("\"", "ja")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s4>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"français\"@fr")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "fr")
                )
            }(),
            {
                MicroTest0(
                        AOPEQ(AOPSimpleLiteral("\"", "fr"), AOPSimpleLiteral("\"", "ja")),
                        AOPBoolean(false)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s5>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s6>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"def\"^^<http://www.w3.org/2001/XMLSchema#string>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("s")
                resultSet.createVariable("p")
                resultSet.createVariable("o")
                MicroTest1(
                        AOPBuildInCallLANG(AOPVariable("o")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example.org/s7>")
                            resultRow[resultSet.createVariable("p")] = resultSet.createValue("<http://example.org/str>")
                            resultRow[resultSet.createVariable("o")] = resultSet.createValue("\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/if01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_functions_if02_rq() = listOf(
            {
                MicroTest0(
                        AOPDivision(AOPInteger(0), AOPInteger(1)),
                        AOPInteger(0)
                )
            }(),
            {
                MicroTest0(
                        AOPBuildInCallIF(AOPInteger(0), AOPBoolean(false), AOPBoolean(true)),
                        Exception("AOPBuiltInCall IF only works with boolean condition")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/functions/if02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_grouping_group03_rq() = listOf(
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("v")
                resultSet.createVariable("#2")
                resultSet.createVariable("w")
                resultSet.createVariable("s")
                MicroTestN(
                        AOPAggregation(Aggregation.SAMPLE,false,arrayOf(AOPVariable("v"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                                resultRow[resultSet.createVariable("v")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                                resultRow[resultSet.createVariable("w")] = resultSet.createValue("\"9\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/s1>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(1)
                )
            }()*/
            /*{
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("v")
                resultSet.createVariable("#2")
                resultSet.createVariable("w")
                resultSet.createVariable("s")
                MicroTestN(
                        AOPAggregation(Aggregation.SAMPLE,false,arrayOf(AOPVariable("v"))),
                        listOf(
                            {
                                val resultRow = resultSet.createResultRow()
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                                resultRow[resultSet.createVariable("v")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                                resultSet.setUndefValue(resultRow, resultSet.createVariable("w"))
                                resultRow[resultSet.createVariable("s")] = resultSet.createValue("<http://example/s2>")
                                resultRow
                            }()
                        ),
                        resultSet,
                        AOPInteger(2)
                )
            }()*/
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/grouping/group03.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_project_expression_projexp01_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("y")
                resultSet.createVariable("#2")
                resultSet.createVariable("z")
                resultSet.createVariable("x")
                MicroTest1(
                        AOPEQ(AOPVariable("y"), AOPVariable("z")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://www.example.org/instance#a>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(true)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("y")
                resultSet.createVariable("#2")
                resultSet.createVariable("z")
                resultSet.createVariable("x")
                MicroTest1(
                        AOPEQ(AOPVariable("y"), AOPVariable("z")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://www.example.org/instance#a>")
                            resultRow
                        }(),
                        resultSet,
                        AOPBoolean(false)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/project-expression/projexp01.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_project_expression_projexp02_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("y")
                resultSet.createVariable("#2")
                resultSet.createVariable("z")
                resultSet.createVariable("x")
                MicroTest1(
                        AOPAddition(AOPVariable("z"), AOPVariable("y")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://www.example.org/instance#a>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/project-expression/projexp02.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_project_expression_projexp03_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("y")
                resultSet.createVariable("#2")
                resultSet.createVariable("z")
                resultSet.createVariable("x")
                MicroTest1(
                        AOPAddition(AOPVariable("z"), AOPVariable("y")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://www.example.org/instance#a>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(3)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("sum")
                resultSet.createVariable("#1")
                resultSet.createVariable("y")
                resultSet.createVariable("#3")
                resultSet.createVariable("z")
                resultSet.createVariable("x")
                MicroTest1(
                        AOPMultiplication(AOPVariable("sum"), AOPInteger(2)),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("sum")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#3"))
                            resultRow[resultSet.createVariable("z")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://www.example.org/instance#a>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(6)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/project-expression/projexp03.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_project_expression_projexp04_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("x")
                resultSet.createVariable("#1")
                resultSet.createVariable("y")
                MicroTest1(
                        AOPAddition(AOPVariable("y"), AOPVariable("y")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://www.example.org/instance#a>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(2)
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("x")
                resultSet.createVariable("#1")
                resultSet.createVariable("y")
                MicroTest1(
                        AOPAddition(AOPVariable("y"), AOPVariable("y")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://www.example.org/instance#a>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPInteger(4)
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/project-expression/projexp04.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_project_expression_projexp05_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("x")
                resultSet.createVariable("#1")
                resultSet.createVariable("l")
                MicroTest1(
                        AOPBuildInCallDATATYPE(AOPVariable("l")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://www.example.org/instance#a>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("l")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        AOPIri("http://www.w3.org/2001/XMLSchema#integer")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("x")
                resultSet.createVariable("#1")
                resultSet.createVariable("l")
                MicroTest1(
                        AOPBuildInCallDATATYPE(AOPVariable("l")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://www.example.org/instance#a>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#1"))
                            resultRow[resultSet.createVariable("l")] = resultSet.createValue("<http://www.example.org/schema#a>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall DATATYPE only works with typed string input")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/project-expression/projexp05.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_project_expression_projexp06_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("m")
                resultSet.createVariable("x")
                resultSet.createVariable("#2")
                resultSet.createVariable("l")
                MicroTest1(
                        AOPBuildInCallDATATYPE(AOPVariable("m")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("m"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://www.example.org/instance#a>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("l")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall DATATYPE only works with typed string input")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/project-expression/projexp06.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_project_expression_projexp07_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("y")
                resultSet.createVariable("#2")
                resultSet.createVariable("l")
                resultSet.createVariable("x")
                MicroTest1(
                        AOPBuildInCallDATATYPE(AOPVariable("l")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("l")] = resultSet.createValue("\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://www.example.org/instance#a>")
                            resultRow
                        }(),
                        resultSet,
                        AOPIri("http://www.w3.org/2001/XMLSchema#integer")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("y")
                resultSet.createVariable("#2")
                resultSet.createVariable("l")
                resultSet.createVariable("x")
                MicroTest1(
                        AOPBuildInCallDATATYPE(AOPVariable("l")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("y")] = resultSet.createValue("\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("l"))
                            resultRow[resultSet.createVariable("x")] = resultSet.createValue("<http://www.example.org/instance#b>")
                            resultRow
                        }(),
                        resultSet,
                        Exception("AOPBuiltInCall DATATYPE only works with typed string input")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/project-expression/projexp07.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

    @TestFactory
    fun testresources_sparql11_test_suite_subquery_sq12_rq() = listOf(
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("F")
                resultSet.createVariable("#2")
                resultSet.createVariable("L")
                resultSet.createVariable("P")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPVariable("F"), AOPSimpleLiteral("\"", " ")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("F")] = resultSet.createValue("\"John\"")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("L")] = resultSet.createValue("\"Doe\"")
                            resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://p1>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "John ")
                )
            }(),
            {
                val resultSet = ResultSet(ResultSetDictionary())
                resultSet.createVariable("#0")
                resultSet.createVariable("F")
                resultSet.createVariable("#2")
                resultSet.createVariable("L")
                resultSet.createVariable("P")
                MicroTest1(
                        AOPBuildInCallCONCAT(AOPSimpleLiteral("\"", "John "), AOPVariable("L")),
                        {
                            val resultRow = resultSet.createResultRow()
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#0"))
                            resultRow[resultSet.createVariable("F")] = resultSet.createValue("\"John\"")
                            resultSet.setUndefValue(resultRow, resultSet.createVariable("#2"))
                            resultRow[resultSet.createVariable("L")] = resultSet.createValue("\"Doe\"")
                            resultRow[resultSet.createVariable("P")] = resultSet.createValue("<http://p1>")
                            resultRow
                        }(),
                        resultSet,
                        AOPSimpleLiteral("\"", "John Doe")
                )
            }(),
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("test->resources/sparql11-test-suite/subquery/sq12.rq<-$index") {
            try {
                val output: AOPConstant
                if (data is MicroTest1) {
                    output = data.input.calculate(data.resultSet, data.resultRow)
                } else if (data is MicroTestN) {
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, true, data.resultRows.count())
                    for (resultRow in data.resultRows)
                        data.input.calculate(data.resultSet, resultRow)
                    if (data.input is AOPBase)
                        setAggregationMode(data.input, false, data.resultRows.count())
                    output = data.input.calculate(data.resultSet, data.resultSet.createResultRow())
                } else {
                    val resultSet = ResultSet(ResultSetDictionary())
                    output = data.input.calculate(resultSet, resultSet.createResultRow())
                }
                assertTrue(data.expected is AOPConstant)
                if (!data.expected.equals(output)) {
                    if (data is MicroTest1)
                        println(data.resultRow)
                    println(output.valueToString())
                    println((data.expected as AOPConstant).valueToString())
                }
                assertTrue(data.expected.equals(output))
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }

}
