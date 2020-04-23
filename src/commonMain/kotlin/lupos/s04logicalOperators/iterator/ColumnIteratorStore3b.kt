package lupos.s04logicalOperators.iterator

import lupos.s00misc.*
import lupos.s03resultRepresentation.*
import lupos.s05tripleStore.*

class ColumnIteratorStore3b(val values: MyListValue) : ColumnIterator() {
    var counterPrimary: Int
    var counterSecondary: Int
    var counterTerniary: Int
    var index = 5
    var value = values[3]

    init {
        if (values.size > 4) {
            counterPrimary = values[0] - 1
            counterSecondary = values[2] - 1
            counterTerniary = values[4] - 1
            next = {
                var res: Value? = value
                index++
                if (counterTerniary == 0) {
                    if (counterSecondary == 0) {
                        if (counterPrimary == 0) {
                            close()
                        } else {
                            counterPrimary--
                            counterSecondary = values[index + 1] - 1
                            value = values[index + 2]
                            counterTerniary = values[index + 3] - 1
                            index += 4
                        }
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
        } else {
            counterPrimary = 0
            counterSecondary = 0
            counterTerniary = 0
        }
    }
}
