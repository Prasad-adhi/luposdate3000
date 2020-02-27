package lupos.s04arithmetikOperators.singleinput
import lupos.s00misc.EOperatorID
import lupos.s03resultRepresentation.ResultRow
import lupos.s03resultRepresentation.ResultSet
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.AOPConstant
import lupos.s04logicalOperators.OPBase


class AOPFunctionCall(var iri: String, var distinct: Boolean, args: List<OPBase>) : AOPBase() {
    override val operatorID = EOperatorID.AOPFunctionCallID
    override val classname = "AOPFunctionCall"
    override val children: Array<OPBase> = Array(args.size) { args[it] }

    override fun toXMLElement() = super.toXMLElement().addAttribute("iri", iri).addAttribute("distinct", "" + distinct)

    override fun equals(other: Any?): Boolean {
        if (other !is AOPFunctionCall)
            return false
        if (iri != other.iri)
            return false
        if (distinct != other.distinct)
            return false
        for (i in children.indices) {
            if (children[i] != other.children[i])
                return false
        }
        return true
    }

    override fun calculate(resultSet: ResultSet, resultRow: ResultRow): AOPConstant {
        TODO("not implemented")
    }
}
