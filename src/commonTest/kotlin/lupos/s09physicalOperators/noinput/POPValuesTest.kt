package lupos.s09physicalOperators.noinput
import lupos.s00misc.*
import lupos.s00misc.Coverage
import lupos.s03resultRepresentation.MyListValue
import lupos.s03resultRepresentation.nodeGlobalDictionary
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s03resultRepresentation.Value
import lupos.s03resultRepresentation.ValueBnode
import lupos.s03resultRepresentation.ValueBoolean
import lupos.s03resultRepresentation.ValueComparatorASC
import lupos.s03resultRepresentation.ValueComparatorDESC
import lupos.s03resultRepresentation.ValueComparatorFast
import lupos.s03resultRepresentation.ValueDateTime
import lupos.s03resultRepresentation.ValueDecimal
import lupos.s03resultRepresentation.ValueDefinition
import lupos.s03resultRepresentation.ValueDouble
import lupos.s03resultRepresentation.ValueError
import lupos.s03resultRepresentation.ValueInteger
import lupos.s03resultRepresentation.ValueIri
import lupos.s03resultRepresentation.ValueLanguageTaggedLiteral
import lupos.s03resultRepresentation.ValueNumeric
import lupos.s03resultRepresentation.ValueSimpleLiteral
import lupos.s03resultRepresentation.ValueStringBase
import lupos.s03resultRepresentation.ValueTypedLiteral
import lupos.s03resultRepresentation.ValueUndef
import lupos.s04logicalOperators.*
import lupos.s04logicalOperators.iterator.*


object POPValuesTest {
    val MAX_VARIABLES = 4
    val MAX_COUNT = 10
    val MAX_VALUE = 10
    val verbose = false
    fun log(value: Any?) {
        if (verbose) {
            println(value)
        }
    }

    suspend fun invoke(random: TestRandom) {
        try {
            while (true) {
                val query = Query()
                val variableSize = random.nextInt(MAX_VARIABLES - 1) + 1
                val count = random.nextInt(MAX_COUNT)
                val allVariables = mutableListOf<String>()
                for (i in 0 until MAX_VARIABLES) {
                    allVariables.add("v$i")
                }
                val data = mutableMapOf<String, MyListValue>()
                val variables = List(variableSize) {
                    val idx = random.nextInt(allVariables.size)
                    val tmp = allVariables[idx]
                    allVariables.removeAt(idx)
                    data[tmp] = MyListValue()
/*return*/tmp
                }
                for (i in 0 until count) {
                    for (variable in variables) {
                        data[variable]!!.add(random.nextInt(MAX_VALUE))
                    }
                }
                println(variables)
                println(data)
                val iterator = POPValues(query, variables, data).evaluate()
                for (variable in variables) {
                    for (i in 0 until count) {
                        println("loop $count $i")
                        val value = iterator.columns[variable]!!.next()
                        require(value == data[variable]!![i])
                    }
                    println(data[variable]!!)
                    require(iterator.columns[variable]!!.next() == null)
                }
            }
        } catch (e: NoMoreRandomException) {
        }
    }
}
