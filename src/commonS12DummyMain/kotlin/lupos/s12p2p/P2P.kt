package lupos.s12p2p

import kotlin.jvm.JvmField
import kotlinx.coroutines.delay
import lupos.s00misc.*
import lupos.s00misc.CoroutinesHelper
import lupos.s00misc.EGraphOperationType
import lupos.s00misc.ELoggerType
import lupos.s00misc.GlobalLogger
import lupos.s00misc.parseFromXml
import lupos.s00misc.Trace
import lupos.s00misc.XMLElement
import lupos.s02buildSyntaxTree.rdf.Dictionary
import lupos.s03resultRepresentation.*
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04logicalOperators.iterator.*
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s09physicalOperators.noinput.*
import lupos.s09physicalOperators.noinput.POPEmptyRow
import lupos.s09physicalOperators.POPBase
import lupos.s14endpoint.*
import lupos.s14endpoint.Endpoint
import lupos.SparqlTestSuite

object P2P {
    /*
    all functions prefixed with "exec" are executed from within this database instance
    all functions prefixed with "process" are answering querys from some remote node
    */
    @JvmField
    val knownClients = mutableListOf<String>()
    @JvmField
    val knownClientsLock = CoroutinesHelper.createLock()

    fun execCommit(query: Query) {
        Endpoint.process_local_commit(query)
        TODO("stream commit to all nodes in transaction")
    }

    suspend fun execTripleModify(query: Query, node: String, graphName: String, data: Array<ColumnIterator>, idx: EIndexPattern, type: EModifyType) {
        if (node == endpointServer!!.fullname) {
            Endpoint.process_local_triple_modify(query, graphName, data, idx, type)
        } else {
//TODO("stream modify to specific node")
        }
    }

    fun execGraphClearAll(query: Query) {
        Endpoint.process_local_graph_clear_all(query)
//TODO("stream clear all to all nodes")
    }

    fun execGraphOperation(query: Query, graphName: String, type: EGraphOperationType) {
        Endpoint.process_local_graph_operation(query, graphName, type)
//TODO("stream create/clear/delete of graph to all nodes")
    }

    fun execTripleGet(query: Query, node: String, graphName: String, params: Array<AOPBase>, idx: EIndexPattern): ColumnIteratorRow {
        if (node == endpointServer!!.fullname)
            return Endpoint.process_local_triple_get(query, graphName, params, idx)
        else {
            TODO("request triple stream from node network")
        }
    }

    fun process_peers_list(): ByteArray {
        TODO("provide list of all known nodes")
    }

    fun process_peers_join_internal(hostname: String?): String {
        TODO("add the hostname to known clients, redistribute data, no further broadcast")
    }

    suspend fun process_peers_join(hostname: String?): String {
        TODO("add the new host the network, and broadcast the new member to all known nodes")
    }

    suspend fun start(bootstrap: String?) {
        knownClients.add(endpointServer!!.fullname)
//TODO("broadcast that this node wants to join, obtain a list of all known clients, and perform the data redistribution")
    }
}
