package lupos.s15tripleStoreDistributed
import lupos.s05tripleStore.IndexPattern
import lupos.s05tripleStore.ModifyType
import lupos.s05tripleStore.PersistentStoreLocal
import lupos.s05tripleStore.TripleStoreLocal
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s03resultRepresentation.Value

import lupos.s03resultRepresentation.ResultRow
import lupos.s03resultRepresentation.ResultSet
import lupos.s03resultRepresentation.ResultSetIterator
import lupos.s05tripleStore.POPTripleStoreIteratorBase


val globalStore = DistributedTripleStore()

class DistributedGraph(val store: DistributedTripleStore, val name: String, val create: Boolean = false) {

    fun modifyData(transactionID: Long, vals: Value, valp: Value, valo: Value, action: ModifyType) {
        store.localStore.getNamedGraph(name, create).modifyData(transactionID, vals, valp, valo, action)
    }

    fun clear() {
        store.localStore.getNamedGraph(name, create).clear()
    }

    fun addData(key: ResultRow, value: ResultRow, store: MutableMap<ResultRow, MutableSet<ResultRow>>) {
        require(false)
    }

    fun deleteData(key: ResultRow, value: ResultRow, store: MutableMap<ResultRow, MutableSet<ResultRow>>) {
        require(false)
    }

    fun abort(transactionID: Long) {
        store.localStore.getNamedGraph(name, create).abort(transactionID)
    }

    fun commit2(transactionID: Long) {
        store.localStore.getNamedGraph(name, create).commit2(transactionID)
    }

    fun commitModifyData(vals: Value, valp: Value, valo: Value, action: (ResultRow, ResultRow, MutableMap<ResultRow, MutableSet<ResultRow>>) -> Unit) {
        store.localStore.getNamedGraph(name, create).commitModifyData(vals, valp, valo, action)
    }

    fun addData(transactionID: Long, t: List<String?>) {
        store.localStore.getNamedGraph(name, create).addData(transactionID, t)
    }

    fun deleteData(transactionID: Long, t: List<String?>) {
        store.localStore.getNamedGraph(name, create).deleteData(transactionID, t)
    }

    fun addDataVar(transactionID: Long, t: List<Pair<String, Boolean>>) {
        store.localStore.getNamedGraph(name, create).addDataVar(transactionID, t)
    }

    fun deleteDataVar(transactionID: Long, t: List<Pair<String, Boolean>>) {
        store.localStore.getNamedGraph(name, create).deleteDataVar(transactionID, t)
    }

    fun addData(transactionID: Long, iterator: ResultSetIterator) {
        store.localStore.getNamedGraph(name, create).addData(transactionID, iterator)
    }

    fun getIterator(dictionary: ResultSetDictionary): POPTripleStoreIteratorBase {
        return store.localStore.getNamedGraph(name, create).getIterator(dictionary)
    }

    fun getIterator(dictionary: ResultSetDictionary, s: String, p: String, o: String): POPTripleStoreIteratorBase {
        return store.localStore.getNamedGraph(name, create).getIterator(dictionary, s, p, o)
    }

    fun getIterator(dictionary: ResultSetDictionary, index: IndexPattern): POPTripleStoreIteratorBase {
        return store.localStore.getNamedGraph(name, create).getIterator(dictionary, index)
    }
}

class DistributedTripleStore() {
    val localStore = PersistentStoreLocal()
    fun nextTransactionID(): Long {
        return localStore.nextTransactionID()
    }

    fun getGraphNames(): List<String> {
        return localStore.getGraphNames()
    }

    fun createGraph(name: String): DistributedGraph {
        localStore.createGraph(name)
        return DistributedGraph(this, name)
    }

    fun dropGraph(name: String) {
        localStore.dropGraph(name)
    }

    fun clearGraph(name: String) {
        localStore.clearGraph(name)
    }

    fun getNamedGraph(name: String, create: Boolean = false): DistributedGraph {
        return DistributedGraph(this, name, create)
    }

    fun getDefaultGraph(): DistributedGraph {
        return DistributedGraph(this, localStore.defaultGraphName)
    }

    fun commit(transactionID: Long) {
        localStore.commit(transactionID)
    }
}
