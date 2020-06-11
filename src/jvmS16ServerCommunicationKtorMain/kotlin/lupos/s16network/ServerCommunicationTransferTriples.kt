package lupos.s16network

import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import java.net.InetSocketAddress
import kotlin.jvm.JvmField
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lupos.s00misc.ByteArrayBuilder
import lupos.s00misc.ByteArrayRead
import lupos.s00misc.Coverage
import lupos.s00misc.EGraphOperationType
import lupos.s00misc.EIndexPattern
import lupos.s00misc.ELoggerType
import lupos.s00misc.EModifyType
import lupos.s00misc.EOperatorID
import lupos.s00misc.ESortPriority
import lupos.s00misc.GlobalLogger
import lupos.s00misc.SanityCheck
import lupos.s00misc.XMLElement
import lupos.s03resultRepresentation.MyListValue
import lupos.s03resultRepresentation.ResultSetDictionary
import lupos.s03resultRepresentation.Value
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.AOPConstant
import lupos.s04arithmetikOperators.noinput.AOPVariable
import lupos.s04logicalOperators.iterator.ColumnIterator
import lupos.s04logicalOperators.iterator.ColumnIteratorMultiValue
import lupos.s04logicalOperators.iterator.IteratorBundle
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s05tripleStore.PersistentStoreLocal
import lupos.s05tripleStore.TripleStoreBulkImport
import lupos.s05tripleStore.TripleStoreLocalBase
import lupos.s09physicalOperators.POPBase
import lupos.s15tripleStoreDistributed.*

object ServerCommunicationTransferTriples {
    fun receiveTriples(packet: ByteArrayRead, dict: ResultSetDictionary, remoteName: String, bulk: TripleStoreBulkImport) {
/*always assume SPO*/
        val columns = packet.readInt()
        require(columns == 3)
        while (packet.remaining() > 0) {
            var si = packet.readInt()
            var pi = packet.readInt()
            var oi = packet.readInt()
            bulk.insert(si, pi, oi)
        }
    }

    fun receiveTriples(packet: ByteArrayRead, dict: ResultSetDictionary, expectedColumns: Int, outputAsSingle: Boolean, remoteName: String): Array<MutableList<Value>> {
/*always assume SPO even _if some of the components are allowed to be missing*/
        val columns = packet.readInt()
        require(columns == expectedColumns)
        require(columns > 0)
        var res: Array<MutableList<Value>>
        if (outputAsSingle) {
            res = Array(1) { mutableListOf<Value>() }
        } else {
            res = Array(columns) { mutableListOf<Value>() }
        }
        while (packet.remaining() > 0) {
            for (i in 0 until columns) {
                val v = packet.readInt()
                if (outputAsSingle) {
                    res[0].add(v)
                } else {
                    res[i].add(v)
                }
            }
        }
        return res
    }

    suspend fun sendTriples(bundle: IteratorBundle, dict: ResultSetDictionary, params: Array<AOPBase>, onFullPacket: suspend (ByteArrayBuilder) -> Unit) {
/*always assume SPO*/
        var iterators = mutableListOf<ColumnIterator>()
        for (p in params) {
            if (p is AOPVariable && p.name != "_") {
                iterators.add(bundle.columns[p.name]!!)
            }
        }
        sendTriples(iterators.toTypedArray(), dict, onFullPacket)
    }

    suspend fun sendTriples(iterators: Array<ColumnIterator>, dict: ResultSetDictionary, onFullPacket: suspend (ByteArrayBuilder) -> Unit) {
        /*always assume SPO*/
        var builder = ByteArrayBuilder()
        loop@ while (true) {
            builder.writeInt(ServerCommunicationHeader.RESPONSE_TRIPLES.ordinal)
            builder.writeInt(iterators.size)
            var counter = 0
            while (builder.size < NETWORK_PACKET_SIZE || counter < NETWORK_PACKET_MIN_TRIPLES) {
                counter++
                for (i in 0 until iterators.size) {
                    val it = iterators[i]
                    val v = it.next()
                    if (v == null) {
                        require(i == 0)
                        break@loop
                    } else {
                        builder.writeInt(v)
                    }
                }
            }
            onFullPacket(builder)
            builder.reset()
        }
        onFullPacket(builder)
    }
}
