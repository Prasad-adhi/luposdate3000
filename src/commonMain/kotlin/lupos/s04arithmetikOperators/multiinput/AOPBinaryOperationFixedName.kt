package lupos.s04arithmetikOperators.multiinput

import lupos.s00misc.EOperatorID
import lupos.s04arithmetikOperators.AOPBase


abstract class AOPBinaryOperationFixedName() : AOPBase() {
    override val operatorID = EOperatorID.AOPBinaryOperationFixedNameID
    override val classname = "AOPBinaryOperationFixedName"
}
