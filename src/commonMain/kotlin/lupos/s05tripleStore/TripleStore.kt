package lupos.s05tripleStore

import lupos.s00misc.*
import lupos.s00misc.GlobalLogger
import lupos.s00misc.ThreadSafeUuid
import lupos.s09physicalOperators.POPBase


abstract class POPTripleStoreIteratorBase() : POPBase() {
    var nameS = "#s" + uuid
    var nameP = "#p" + uuid
    var nameO = "#o" + uuid
    abstract fun setMNameS(n: String)
    abstract fun setMNameP(n: String)
    abstract fun setMNameO(n: String)
    abstract fun getGraphName(): String
}

class PersistentStoreLocal {
    val defaultGraphName = ""
    val stores = ThreadSafeMutableMap<String, TripleStoreLocal>()

    companion object {
        private val global_transactionID = ThreadSafeUuid()

    }

    fun nextTransactionID(): Long {
        return global_transactionID.next()
    }

    fun getGraphNames(includeDefault: Boolean = false): List<String> {
        val res = mutableListOf<String>()
        stores.forEachKey { t ->
            if (t != defaultGraphName || includeDefault)
                res.add(t)
        }
        return res
    }

    constructor() {
        if (stores[defaultGraphName] == null) {
            stores[defaultGraphName] = TripleStoreLocal(defaultGraphName)
        }
    }

    fun createGraph(name: String): TripleStoreLocal {
        val tmp = stores[name]
        if (tmp != null)
            throw Exception("PersistentStore.createGraph :: graph[$name] already exist")
        val tmp2 = TripleStoreLocal(name)
        stores[name] = tmp2
        return tmp2
    }

    fun dropGraph(name: String) {
        require(name != defaultGraphName)
        if (stores[name] == null)
            throw Exception("PersistentStore.dropGraph :: graph[$name] did not exist")
        stores.remove(name)
    }

    fun clearGraph(name: String) {
        getNamedGraph(name).clear()
    }

    fun getNamedGraph(name: String, create: Boolean = false): TripleStoreLocal {
        val tmp = stores[name]
        if (tmp != null || !create)
            return tmp!!
        return createGraph(name)
    }

    fun getDefaultGraph(): TripleStoreLocal {
        return getNamedGraph(defaultGraphName, true)
    }

    fun commit(transactionID: Long) {
        stores.forEachValue { v ->
            v.commit2(transactionID)
        }
    }
}

