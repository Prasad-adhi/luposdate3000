package lupos.s04logicalOperators.singleinput

import lupos.s00misc.EOperatorID
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.noinput.OPNothing


class LOPNOOP() : LOPBase() {
    override val operatorID = EOperatorID.LOPNOOPID
    override val classname = "LOPNOOP"
    override val children: Array<OPBase> = arrayOf(OPNothing())

    constructor(child: OPBase) : this() {
        children[0] = child
    }

    override fun equals(other: Any?): Boolean {
        if (other !is LOPNOOP)
            return false
        for (i in children.indices) {
            if (!children[i].equals(other.children[i]))
                return false
        }
        return true
    }
}

