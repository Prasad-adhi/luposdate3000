package lupos.s05tripleStore.index_SingleList

import lupos.s00misc.BenchmarkUtils
import lupos.s00misc.Coverage
import lupos.s00misc.EBenchmark
import lupos.s03resultRepresentation.MyListValue
import lupos.s03resultRepresentation.Value
import lupos.s04logicalOperators.iterator.ColumnIterator
import lupos.s04logicalOperators.iterator.ColumnIteratorNext

class ColumnIteratorStore2a(val values: MyListValue, start: Int) : ColumnIterator() {
    var counterSecondary: Int
    var counterTerniary: Int
    var index = start + 3
    var value = values[index - 2]

    init {
        counterSecondary = values[index - 3] - 1
        counterTerniary = values[index - 1] - 1
        next = object : ColumnIteratorNext("ColumnIteratorStore2a.next") {
            override fun invoke(): Value? {
                //BenchmarkUtils.start(EBenchmark.STORE_NEXT2a)
                var res: Value? = value
                index++
                if (counterTerniary == 0) {
                    if (counterSecondary == 0) {
                        close()
                    } else {
                        counterSecondary--
                        value = values[index]
                        counterTerniary = values[index + 1] - 1
                        index += 2
                    }
                } else {
                    counterTerniary--
                }
//BenchmarkUtils.elapsedSeconds(EBenchmark.STORE_NEXT2a)
                return res
            }
        }
    }
}
