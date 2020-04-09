package lupos.s04logicalOperators.singleinput

import kotlin.jvm.JvmField
import lupos.s00misc.Coverage
import lupos.s00misc.EOperatorID
import lupos.s00misc.ESortPriority
import lupos.s04logicalOperators.LOPBase
import lupos.s04logicalOperators.noinput.OPNothing
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query

class LOPMakeBooleanResult(query: Query, child: OPBase) : LOPBase(query, EOperatorID.LOPMakeBooleanResultID, "LOPMakeBooleanResult", arrayOf(child), ESortPriority.PREVENT_ANY) {
    override fun getProvidedVariableNames(): List<String> {
        return mutableListOf("?boolean")
    }

    override fun equals(other: Any?): Boolean {
        if (other !is LOPMakeBooleanResult) {
            return false
        }
        for (i in children.indices) {
            if (children[i] != other.children[i]) {
                return false
            }
        }
        return true
    }

    override fun cloneOP() = LOPMakeBooleanResult(query, children[0].cloneOP())
}
