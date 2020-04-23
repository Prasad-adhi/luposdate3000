package lupos.s04logicalOperators.iterator

import lupos.s00misc.*
import lupos.s03resultRepresentation.*
import lupos.s05tripleStore.*

class ColumnIteratorStore2a(val values: MyListValue, start: Int) : ColumnIterator() {
    var counterSecondary: Int
    var counterTerniary: Int
    var index = start + 3
    var value = values[index - 2]

    init {
        counterSecondary = values[index - 3] - 1
        counterTerniary = values[index - 1] - 1
        next = {
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
/*return*/res
        }
    }
}
