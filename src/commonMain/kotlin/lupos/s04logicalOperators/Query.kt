package lupos.s04logicalOperators

import lupos.s00misc.*
import lupos.s00misc.Coverage
import lupos.s03resultRepresentation.*
import lupos.s15tripleStoreDistributed.DistributedTripleStore

class Query(val dictionary: ResultSetDictionary = ResultSetDictionary(), val transactionID: Long = global_transactionID.next()) {
    var commited = false

    companion object {
        private val global_transactionID = ThreadSafeUuid()
    }

    fun commit() {
        DistributedTripleStore.commit(this)
        commited = true
    }

    protected fun finalize() {
    }
}
