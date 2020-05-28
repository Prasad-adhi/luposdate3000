package lupos.s04arithmetikOperators.noinput
import com.benasher44.uuid.uuid4
import lupos.s00misc.Coverage
import lupos.s00misc.EOperatorID
import lupos.s03resultRepresentation.Value
import lupos.s03resultRepresentation.ValueDefinition
import lupos.s03resultRepresentation.ValueIri
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04logicalOperators.iterator.ColumnIterator
import lupos.s04logicalOperators.iterator.IteratorBundle
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
class AOPBuildInCallUUID(query: Query) : AOPBase(query, EOperatorID.AOPBuildInCallUUIDID, "AOPBuildInCallUUID", arrayOf()) {
    override fun toSparql() = "UUID()"
    override fun equals(other: Any?): Boolean {
Coverage.funStart(2672)
        if (other !is AOPBuildInCallUUID) {
Coverage.ifStart(2673)
            return false
        }
Coverage.statementStart(2674)
        return children[0] == other.children[0]
    }
    override fun evaluate(row: IteratorBundle): () -> ValueDefinition {
Coverage.funStart(2675)
        return {
Coverage.statementStart(2676)
            /*return*/ValueIri("urn:uuid:" + uuid4())
        }
Coverage.statementStart(2677)
    }
    override fun cloneOP() = this
}
