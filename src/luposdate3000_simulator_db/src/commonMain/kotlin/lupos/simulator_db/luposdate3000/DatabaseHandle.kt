/*
 * This file is part of the Luposdate3000 distribution (https://github.com/luposdate3000/luposdate3000).
 * Copyright (c) 2020-2021, Institute of Information Systems (Benjamin Warnke and contributors of LUPOSDATE3000), University of Luebeck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

// onFinish:IDatabasePackage?,expectedResult:MemoryTable?

package lupos.simulator_db.luposdate3000
import lupos.endpoint.LuposdateEndpoint
import lupos.endpoint_launcher.RestEndpoint
import lupos.operator.base.OPBaseCompound
import lupos.operator.base.Query
import lupos.operator.factory.XMLElementToOPBase
import lupos.operator.factory.XMLElementToOPBaseMap
import lupos.operator.logical.noinput.OPNothing
import lupos.operator.physical.partition.POPDistributedReceiveSingle
import lupos.operator.physical.partition.POPDistributedSendSingle
import lupos.result_format.EQueryResultToStreamExt
import lupos.result_format.QueryResultToXMLStream
import lupos.shared.EPartitionModeExt
import lupos.shared.IMyInputStream
import lupos.shared.Luposdate3000Instance
import lupos.shared.MemoryTable
import lupos.shared.MyInputStreamFromByteArray
import lupos.shared.SanityCheck
import lupos.shared.XMLElement
import lupos.shared.dictionary.EDictionaryTypeExt
import lupos.shared.dynamicArray.ByteArrayWrapper
import lupos.shared.inline.MyPrintWriter
import lupos.shared.operator.IOPBase
import lupos.simulator_db.DatabaseState
import lupos.simulator_db.IDatabase
import lupos.simulator_db.IDatabasePackage
import lupos.simulator_db.IRouter
import lupos.simulator_db.QueryPackage
import lupos.simulator_db.QueryResponsePackage

public class DatabaseHandle : IDatabase {
    private var ownAdress: Int = 0
    private var instance = Luposdate3000Instance()
    private val myPendingWork = mutableListOf<MySimulatorPendingWork>()
    private val myPendingWorkData = mutableMapOf<String, ByteArrayWrapper>()
    private var router: IRouter? = null

    override fun start(initialState: DatabaseState) {
        println("DatabaseHandle.start ${initialState.allAddresses.map{it}} .. ${initialState.ownAddress}")
        if (initialState.allAddresses.size == 0) {
            throw Exception("invalid input")
        }
        ownAdress = initialState.ownAddress
        router = initialState.sender
        instance.LUPOS_PROCESS_URLS = initialState.allAddresses.map { it.toString() }.toTypedArray()
        instance.LUPOS_PROCESS_ID = initialState.allAddresses.indexOf(initialState.ownAddress)
        instance.LUPOS_HOME = initialState.absolutePathToDataDirectory
        instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Process
        instance.LUPOS_DICTIONARY_MODE = EDictionaryTypeExt.KV
        instance.LUPOS_BUFFER_SIZE = 8192
        instance.communicationHandler = MySimulatorCommunicationHandler(instance, initialState.sender)
        instance = LuposdateEndpoint.initializeB(instance)
        instance.distributedOptimizerQueryFactory = {
            MySimulatorDistributedOptimizer(initialState.sender)
        }
    }

    override fun activate() {
/*
        if (!instance.initialized) {
            instance = LuposdateEndpoint.initializeB(instance)
        }
*/
    }

    override fun deactivate() {
/*
        if ((!BufferManagerExt.isInMemoryOnly) && (instance.LUPOS_DICTIONARY_MODE != EDictionaryTypeExt.InMemory)) {
            // do not disable inmemory databases, because they would loose all data
            LuposdateEndpoint.close(instance)
        }
*/
    }

    override fun end() {
        LuposdateEndpoint.close(instance)
    }

    private fun receive(pck: MySimulatorTestingImportPackage) {
        if (listOf(".n3", ".ttl", ".nt").contains(pck.type)) {
            LuposdateEndpoint.importTurtleString(instance, pck.data, pck.graph)
        } else {
            TODO()
        }
// TODO wait for all ack - or assume ordered messages
        val onFinish = pck.onFinish
        if (onFinish != null) {
            receive(onFinish)
        }
    }
    private fun receive(pck: MySimulatorTestingCompareGraphPackage) {
        receive(QueryPackage(ownAdress, pck.query.encodeToByteArray()), pck.onFinish, pck.expectedResult)
    }
    private fun receive(pck: MySimulatorTestingExecute) {
        receive(QueryPackage(ownAdress, pck.query.encodeToByteArray()), pck.onFinish, null)
    }

    private fun receive(pck: QueryPackage, onFinish: IDatabasePackage?, expectedResult: MemoryTable?) {
        val queryString = pck.query.decodeToString()
        val op = LuposdateEndpoint.evaluateSparqlToOperatorgraphA(instance, queryString)
        val q = op.getQuery()
        q.initialize(op)

        val parts = q.getOperatorgraphParts()
        if (parts.size == 1) {
// TODO wait for all ack - or assume ordered messages
            if (expectedResult != null) {
                val buf = MyPrintWriter(false)
                val result = (LuposdateEndpoint.evaluateOperatorgraphToResultA(instance, q.getRoot(), buf, EQueryResultToStreamExt.MEMORY_TABLE) as List<MemoryTable>).first()
                val buf_err = MyPrintWriter()
                if (!result.equalsVerbose(expectedResult, true, true, buf_err)) {
                    throw Exception(buf_err.toString())
                }
                if (onFinish != null) {
                    receive(onFinish)
                } else {
                    router!!.send(pck.sourceAddress, QueryResponsePackage("success".encodeToByteArray()))
                }
            } else {
                val out = MyPrintWriter(true)
                QueryResultToXMLStream(q.getRoot(), out)
                val res = out.toString().encodeToByteArray()
                if (onFinish != null) {
                    receive(onFinish)
                } else {
                    router!!.send(pck.sourceAddress, QueryResponsePackage(res))
                }
            }
        } else {
            val destinations = mutableMapOf("" to pck.sourceAddress)
            receive(MySimulatorOperatorGraphPackage(parts, destinations, q.getOperatorgraphPartsToHostMap(), q.getDependenciesMapTopDown(), onFinish, expectedResult))
        }
    }

    private fun receive(pck: MySimulatorAbstractPackage) {
        when (pck.path) {
            "/distributed/query/dictionary/register",
            "/distributed/query/dictionary/remove" -> {
                // dont use dictionaries right now
            }
            "/distributed/graph/create" -> RestEndpoint.distributed_graph_create(pck.params, instance)
            "/distributed/graph/modify" -> RestEndpoint.distributed_graph_modify(pck.params, instance, MyInputStreamFromByteArray(pck.data!!))
            "simulator-intermediate-result" -> {
                SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_simulator_db/src/commonMain/kotlin/lupos/simulator_db/luposdate3000/DatabaseHandle.kt:162"/*SOURCE_FILE_END*/ }, { myPendingWorkData[pck.params["key"]!!] == null })
                myPendingWorkData[pck.params["key"]!!] = pck.data!!
                doWork()
            }
            else -> TODO("${pck.path} ${pck.params}")
        }
    }

    private fun receive(pck: MySimulatorOperatorGraphPackage) {
        val allHosts = pck.operatorGraphPartsToHostMap.values.toSet().toTypedArray()
        val allHostAdresses = IntArray(allHosts.size) { allHosts[it].toInt() }
//        val nextHops = router!!.getNextDatabaseHops(allHostAdresses)  //TODO
        val nextHops = allHostAdresses
        val packages = mutableMapOf<Int, MySimulatorOperatorGraphPackage>()
        for (i in nextHops.toSet()) {
            packages[i] = MySimulatorOperatorGraphPackage(mutableMapOf(), mutableMapOf(), mutableMapOf(), mutableMapOf(), pck.onFinish, pck.expectedResult)
        }
        packages[ownAdress] = MySimulatorOperatorGraphPackage(mutableMapOf(), mutableMapOf(), mutableMapOf(), mutableMapOf(), pck.onFinish, pck.expectedResult)
        val packageMap = mutableMapOf<String, Int>()
        for ((k, v) in pck.operatorGraphPartsToHostMap) {
            packageMap[k] = nextHops[allHostAdresses.indexOf(v.toInt())]
        }
        var changed = true
        while (changed) {
            changed = false
            loop@ for ((k, v) in pck.dependenciesMapTopDown) {
                if (!packageMap.contains(k)) {
                    SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_simulator_db/src/commonMain/kotlin/lupos/simulator_db/luposdate3000/DatabaseHandle.kt:189"/*SOURCE_FILE_END*/ }, { v.size >= 1 })
                    var dest = -1
                    for (key in v) {
                        val d = packageMap[key]
                        if (d != null) {
                            if (dest == -1) {
                                dest = d
                            } else {
                                if (dest != d) {
                                    packageMap[k] = ownAdress // alles mit unterschiedlichen next hops selber berechnen
                                    for (i in pck.dependenciesMapTopDown[k]!!) {
                                        pck.destinations[i] = ownAdress
                                    }
                                    changed = true
                                    continue@loop
                                }
                            }
                        } else {
                            continue@loop
                        }
                    }
                    for (i in pck.dependenciesMapTopDown[k]!!) {
                        pck.destinations[i] = dest
                    }
                    packageMap[k] = dest // alles mit gemeinsamen next Hop zusammen weitersenden
                    changed = true
                }
            }
        }
        for ((k, v) in packageMap) {
            val targetInt = v
            val p = packages[targetInt]!!
            p.operatorGraph[k] = pck.operatorGraph[k]!!
            val h = pck.operatorGraphPartsToHostMap[k]
            if (h != null) {
                p.operatorGraphPartsToHostMap[k] = h
            }
            val deps = pck.dependenciesMapTopDown[k]
            if (deps != null) {
                p.dependenciesMapTopDown[k] = deps
            } else {
                p.dependenciesMapTopDown[k] = mutableSetOf()
            }
            val d = pck.destinations[k]
            if (d != null) {
                p.destinations[k] = d
            } else {
                p.destinations[k] = ownAdress
            }
        }
        for ((k, v) in packages) {
            if (k != ownAdress) {
                router!!.send(k, v)
            }
        }
        val p = packages[ownAdress!!]!!
        for ((k, v) in p.operatorGraph) {
            myPendingWork.add(
                MySimulatorPendingWork(
                    p.operatorGraph[k]!!,
                    p.destinations[k]!!,
                    p.dependenciesMapTopDown[k]!!,
                    k,
                    pck.onFinish,
                    pck.expectedResult,
                )
            )
        }
        doWork()
    }

    private fun localXMLElementToOPBase(query: Query, node: XMLElement): IOPBase {
        val operatorMap = mutableMapOf<String, XMLElementToOPBaseMap>()
        operatorMap.putAll(XMLElementToOPBase.operatorMap)
        operatorMap["POPDistributedReceiveSingle"] = { query, node, mapping, recursionFunc ->
            val id = node.attributes["partitionID"]!!.toInt()
            val keys = mutableSetOf<String>()
            for (c in node.childs) {
                if (c.tag == "partitionDistributionReceiveKey") {
                    keys.add(c.attributes["key"]!!)
                }
            }
            SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_simulator_db/src/commonMain/kotlin/lupos/simulator_db/luposdate3000/DatabaseHandle.kt:271"/*SOURCE_FILE_END*/ }, { keys.size == 1 })
            val key = keys.first()!!
            SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_simulator_db/src/commonMain/kotlin/lupos/simulator_db/luposdate3000/DatabaseHandle.kt:273"/*SOURCE_FILE_END*/ }, { myPendingWorkData.contains(key) })
            val input = MyInputStreamFromByteArray(myPendingWorkData[key]!!)
            myPendingWorkData.remove(key)
            val res = POPDistributedReceiveSingle(
                query,
                XMLElementToOPBase.createProjectedVariables(node),
                node.attributes["partitionVariable"]!!,
                node.attributes["partitionCount"]!!.toInt(),
                id,
                OPNothing(query, XMLElementToOPBase.createProjectedVariables(node)),
                input
            )
            query.addPartitionOperator(res.uuid, id)
            res
        }
        operatorMap["POPDistributedReceiveMulti"] = { query, node, mapping, recursionFunc ->
            val id = node.attributes["partitionID"]!!.toInt()
            val keys = mutableSetOf<String>()
            for (c in node.childs) {
                if (c.tag == "partitionDistributionReceiveKey") {
                    keys.add(c.attributes["key"]!!)
                }
            }
            val inputs = keys.map { key ->
                val input = MyInputStreamFromByteArray(myPendingWorkData[key]!!)
                myPendingWorkData.remove(key)
                input as IMyInputStream
            }.toTypedArray()
            val res = MySimulatorPOPDistributedReceiveMulti(
                query,
                XMLElementToOPBase.createProjectedVariables(node),
                node.attributes["partitionVariable"]!!,
                node.attributes["partitionCount"]!!.toInt(),
                id,
                OPNothing(query, XMLElementToOPBase.createProjectedVariables(node)),
                inputs
            )
            query.addPartitionOperator(res.uuid, id)
            res
        }
        return XMLElementToOPBase(query, node, mutableMapOf(), operatorMap) as IOPBase
    }

    private fun doWork() {
        var changed = true
        while (changed) {
            changed = false
            for (w in myPendingWork) {
                if (myPendingWorkData.keys.containsAll(w.dependencies)) {
                    myPendingWork.remove(w)
                    changed = true
                    val query = Query(instance)
                    val node = localXMLElementToOPBase(query, w.operatorGraph)
                    when (node) {
                        is POPDistributedSendSingle -> {
                            val out = MySimulatorOutputStreamToPackage(w.destination, "simulator-intermediate-result", mapOf("key" to w.key), router!!)
                            node.evaluate(out)
                            out.close()
                        }
                        is OPBaseCompound -> {
                            if (w.expectedResult != null) {
                                val buf = MyPrintWriter(false)
                                val result = (LuposdateEndpoint.evaluateOperatorgraphToResultA(instance, node, buf, EQueryResultToStreamExt.MEMORY_TABLE) as List<MemoryTable>).first()
                                val buf_err = MyPrintWriter()
                                if (!result.equalsVerbose(w.expectedResult, true, true, buf_err)) {
                                    throw Exception(buf_err.toString())
                                }
                                if (w.onFinish != null) {
                                    receive(w.onFinish)
                                } else {
                                    router!!.send(w.destination, QueryResponsePackage("success".encodeToByteArray()))
                                }
                            } else {
                                val buf = MyPrintWriter(true)
                                QueryResultToXMLStream(node, buf, false)
                                if (w.onFinish != null) {
                                    receive(w.onFinish)
                                } else {
                                    router!!.send(w.destination, QueryResponsePackage(buf.toString().encodeToByteArray()))
                                }
                            }
                        }
                        else -> TODO(node.toString())
                    }

                    changed = true
                    break
                }
            }
        }
    }

    override fun receive(pck: IDatabasePackage) {
        when (pck) {
            is MySimulatorTestingImportPackage -> receive(pck)
            is MySimulatorTestingCompareGraphPackage -> receive(pck)
            is MySimulatorTestingExecute -> receive(pck)
            is QueryPackage -> receive(pck, null, null)
            is MySimulatorAbstractPackage -> receive(pck)
            is MySimulatorOperatorGraphPackage -> receive(pck)
            else -> TODO("$pck")
        }
    }
}
