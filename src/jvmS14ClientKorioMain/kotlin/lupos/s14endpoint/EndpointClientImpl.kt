package lupos.s14endpoint

import com.soywiz.korio.net.http.*
import com.soywiz.korio.net.http.createHttpClient
import com.soywiz.korio.net.http.Http
import com.soywiz.korio.net.URL
import com.soywiz.korio.stream.*
import kotlin.concurrent.thread
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

import lupos.s03resultRepresentation.ResultRepresenationNetwork

import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04arithmetikOperators.ResultVektorRaw
import lupos.s04logicalOperators.OPBase
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.ResultIterator
import lupos.s09physicalOperators.noinput.*
import lupos.s09physicalOperators.noinput.POPEmptyRow
import lupos.s09physicalOperators.POPBase
import lupos.s14endpoint.*
import lupos.s14endpoint.Endpoint
import lupos.SparqlTestSuite

@UseExperimental(ExperimentalStdlibApi::class)
object EndpointClientImpl {
    @JvmField
    val client = createHttpClient()

    fun encodeParam(key: String, value: Any) = URL.encodeComponent(key) + "=" + URL.encodeComponent("" + value)
    suspend fun requestGetBytes(url: String): ByteArray = Trace.trace({ "EndpointClientImpl.requestGetBytes" }, {
        SanityCheck.check({ !url.startsWith("http://${endpointServer!!.fullname}") })
        var i = 0
        var res: HttpClient.Response
        while (true) {
            i++
            try {
                res = client.request(Http.Method.GET, url)
                break
            } catch (e: Throwable) {
                if (i > 100)
                    throw e
                delay(10)
            }
        }
        return res.readAllBytes()
    })

    suspend fun requestPostBytes(url: String, data: DynamicByteArray): ByteArray = Trace.trace({ "EndpointClientImpl.requestPostBytes" }, {
        SanityCheck.check({ !url.startsWith("http://${endpointServer!!.fullname}") })
        var i = 0
        var res: HttpClient.Response
        while (true) {
            i++
            try {
                res = client.request(Http.Method.POST, url, Http.Headers(), AsyncStream(MyDynamicByteArray(data)))
                break
            } catch (e: Throwable) {
                if (i > 100)
                    throw e
                delay(10)
            }
        }
        return res.readAllBytes()
    })

    suspend fun requestGetString(url: String): String = Trace.trace({ "EndpointClientImpl.requestGetString" }, {
        SanityCheck.check({ !url.startsWith("http://${endpointServer!!.fullname}") })
        var i = 0
        var res: HttpClient.Response
        while (true) {
            i++
            try {
                res = client.request(Http.Method.GET, url)
                break
            } catch (e: Throwable) {
                if (i > 100)
                    throw e
                delay(10)
            }
        }
        return res.readAllString()
    })

    suspend fun requestPostString(url: String, data: DynamicByteArray): String = Trace.trace({ "EndpointClientImpl.requestPostString" }, {
        SanityCheck.check({ !url.startsWith("http://${endpointServer!!.fullname}") })
        var i = 0
        var res: HttpClient.Response
        while (true) {
            i++
            try {
                res = client.request(Http.Method.POST, url, Http.Headers(), AsyncStream(MyDynamicByteArray(data)))
                break
            } catch (e: Throwable) {
                if (i > 100)
                    throw e
                delay(10)
            }
        }
        return res.readAllString()
    })

    suspend fun requestPostString(url: String, data: String): String = Trace.trace({ "EndpointClientImpl.requestPostString2" }, {
        SanityCheck.check({ !url.startsWith("http://${endpointServer!!.fullname}") })
        var res: HttpClient.Response
        var i = 0
        while (true) {
            i++
            try {
                val a = AsyncStream(MyDynamicByteArray(data.encodeToByteArray()))
                res = client.request(Http.Method.POST, url, Http.Headers(), a)
                break
            } catch (e: Throwable) {
                if (i > 100)
                    throw e
                delay(10)
            }
        }
        return res.readAllString()
    })
}

class MyDynamicByteArray : AsyncStreamBase {
    @JvmField
    val data: DynamicByteArray

    constructor(data: DynamicByteArray) {
        this.data = data
        data.finish()
    }

    constructor(param: ByteArray) {
        data = DynamicByteArray(param)
        data.pos = param.size
    }

    override suspend fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        if (position > data.pos) {
            return 0
        }
        if (position + len > data.pos) {
            data.data.copyInto(buffer, offset, position.toInt(), data.pos)
            return data.pos - position.toInt()
        }
        data.data.copyInto(buffer, offset, position.toInt(), position.toInt() + len)
        return len
    }

    override suspend fun getLength(): Long {
        return data.pos.toLong()
    }
}
