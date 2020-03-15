package lupos.s03resultRepresentation

import kotlin.jvm.JvmField
import lupos.s03resultRepresentation.ResultChunk
import lupos.s04arithmetikOperators.ResultVektorRaw
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.ResultIterator


class ResultSetDictionary() {
    @JvmField
    val undefValue: Value = ValueUndef()

    fun createValue(value: ValueDefinition): Value = value
    fun getValue(value: Value): ValueDefinition = value
}
