package lupos.s04arithmetikOperators.multiinput

import lupos.s02buildSyntaxTree.sparql1_1.*
import lupos.s03resultRepresentation.*
import lupos.s04arithmetikOperators.*
import lupos.s04arithmetikOperators.multiinput.*
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04arithmetikOperators.singleinput.*
import lupos.s04logicalOperators.noinput.*
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.singleinput.*
import lupos.s04logicalOperators.singleinput.modifiers.LOPPrefix
import lupos.s08logicalOptimisation.OptimizerBase
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


class AOPSubtractionTest {

    fun toConstant(d: Double, i: Int): AOPConstant {
        when (i) {
            0 -> return AOPInteger(d.toInt())
            1 -> return AOPDecimal(d)
            else -> return AOPDouble(d)
        }
    }

    fun max(a: Int, b: Int): Int {
        return if (a > b)
            a
        else
            b
    }

    @TestFactory
    fun testCalculate2(): List<DynamicTest> {
        val res = mutableListOf<DynamicTest>()
        listOf(
                arrayOf<Double>(0.0, 1.0),
                arrayOf<Double>(0.0, -1.0),
                arrayOf<Double>(2.0, 3.0),
                arrayOf<Double>(2.0, -4.0),
                arrayOf<Double>(1.0, 1.0),
                arrayOf<Double>(-1.0, -1.0)
        ).forEach { input ->
            for (i in 0 until 2) {
                for (a in 0 until 3) {
                    val x = toConstant(input[i], a)
                    for (b in 0 until 3) {
                        val y = toConstant(input[1 - i], b)
                        val expected = toConstant(input[i] - input[1 - i], max(a, b))
                        val list = mutableListOf<String>()
                        list.add("" + x.valueToString())
                        list.add("" + y.valueToString())
                        val s = "calculate(${list} to ${expected.valueToString()})"
                        res.add(DynamicTest.dynamicTest(s) {
                            val resultSet = ResultSet(ResultSetDictionary())
                            val output = AOPSubtraction(x, y).calculate(resultSet, resultSet.createResultRow())
                            assertTrue(expected.equals(output))
                            assertTrue(output.equals(output))
                        })
                    }
                }
            }
        }
        return res
    }

    fun testInvalidInput() {
        TODO("not implemented")
    }
}
