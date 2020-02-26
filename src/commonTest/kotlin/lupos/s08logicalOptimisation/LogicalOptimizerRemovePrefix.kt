package lupos.s08logicalOptimisation

import lupos.s00misc.EOperatorID
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04logicalOperators.noinput.*
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.singleinput.*
import lupos.s04logicalOperators.singleinput.modifiers.LOPPrefix
import lupos.s08logicalOptimisation.OptimizerBase
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


class LogicalOptimizerRemovePrefixTest {
    fun helper(input: OPBase, target: OPBase, transactionID: Long, dictionary: ResultSetDictionary) {
        val output = LogicalOptimizerRemovePrefix(transactionID, dictionary).optimizeCall(input)
        assertTrue(target.equals(output))
    }

    @Test
    fun test1() {
        helper(
                LOPPrefix("a", "b"),
                OPNothing(),
                0,
                ResultSetDictionary()
        )
    }

    @Test
    fun test2() {
        helper(
                LOPPrefix("a", "b", LOPPrefix("c", "d")),
                OPNothing(),
                0,
                ResultSetDictionary()
        )
    }

    @Test
    fun test3() {
        helper(
                LOPProjection(mutableListOf(AOPVariable("a")), LOPPrefix("c", "d")),
                LOPProjection(mutableListOf(AOPVariable("a")), OPNothing()),
                0,
                ResultSetDictionary()
        )
    }
}
