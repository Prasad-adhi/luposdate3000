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
package lupos.s16network
import lupos.s00misc.EnpointRecievedInvalidPath
import lupos.s00misc.File
import lupos.s00misc.ICommunicationHandler
import lupos.s00misc.IMyInputStream
import lupos.s00misc.IMyOutputStream
import lupos.s00misc.JenaWrapper
import lupos.s00misc.MyInputStream
import lupos.s00misc.MyOutputStream
import lupos.s00misc.MyPrintWriter
import lupos.s00misc.MyStringStream
import lupos.s00misc.Parallel
import lupos.s00misc.Partition
import lupos.s00misc.XMLElement
import lupos.s00misc.xmlParser.XMLParser
import lupos.s03resultRepresentation.ResultSetDictionaryExt
import lupos.s03resultRepresentation.nodeGlobalDictionary
import lupos.s04logicalOperators.Query
import lupos.s09physicalOperators.POPBase
import lupos.s11outputResult.EQueryResultToStreamExt
import lupos.s14endpoint.convertToOPBase
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.URLDecoder
import java.net.URLEncoder
internal class MyPrintWriterExtension(out: OutputStream) : MyPrintWriter(out) {
    private var counter = 0
    override fun print(x: String) {
        val len = x.length
        counter += len
        if (counter > 8192) {
            flush()
            counter = len
        }
        super.print(x)
    }
}
public class CommunicationHandler : ICommunicationHandler {
    public override fun sendData(targetHost: String, path: String, params: Map<String, String>) {
        val target = targetHost.split(":")
        val targetName = target[0]
        val targetPort = if (target.size> 1) {
            target[1].toInt()
        } else {
            80
        }
        val client = Socket(targetName, targetPort)
        val connectionOutPrinter = MyPrintWriterExtension(client.getOutputStream())
        connectionOutPrinter.println("POST $path")
        connectionOutPrinter.println()
        var first = true
        for ((k, v)in params) {
            if (!first) {
                connectionOutPrinter.print("&")
            }
            first = false
            connectionOutPrinter.print("$k=${URLEncoder.encode(v)}")
        }
        connectionOutPrinter.flush()
        connectionOutPrinter.close()
    }
    public override fun openConnection(targetHost: String, path: String, params: Map<String, String>, action: (input: IMyInputStream, output: IMyOutputStream) -> Unit) {
        val target = targetHost.split(":")
        val targetName = target[0]
        val targetPort = if (target.size> 1) {
            target[1].toInt()
        } else {
            80
        }
        val client = Socket(targetName, targetPort)
        val input = client.getInputStream()
        val output = client.getOutputStream()
        var p = "POST $path"
        var first = true
        for ((k, v)in params) {
            if (first) {
                p += "?"
            } else {
                p += "&"
            }
            first = false
            p += "$k=${URLEncoder.encode(v)}"
        }
        val buf = "$p\n\n".encodeToByteArray()
        output.write(buf, 0, buf.size)
        action(MyInputStream(input), MyOutputStream(output))
        output.flush()
        output.close()
        input.close()
    }
}
@OptIn(ExperimentalStdlibApi::class)
public actual object HttpEndpointLauncher {
    private fun printHeaderSuccess(stream: MyPrintWriter) {
        stream.println("HTTP/1.1 200 OK")
        stream.println("Content-Type: text/plain")
        stream.println()
    }
    internal fun extractParamsFromString(str: String, params: MutableMap<String, String>) {
        for (p in str.split('&')) {
            val t = p.split('=')
            if (t.size> 1) {
                params[t[0]] = URLDecoder.decode(t[1])
            }
        }
    }
    internal fun inputElement(name: String, value: String): String = "<input type=\"text\" name=\"$name\" value=\"$value\"/>"
    internal class PathMappingHelper(val addPostParams: Boolean/*parse the post-body as additional parameters for the query*/, val params: Map<Pair<String/*name*/, String/*default-value*/>, (String, String)->String/*html-string of element*/>, val action: () -> Unit/*action to perform, when this is the called url*/)
    public actual /*suspend*/ fun start() {
        val hosturl = Partition.myProcessUrls[Partition.myProcessId].split(":")
        val hostname = hosturl[0]
        val port = if (hosturl.size> 1) {
            hosturl[1].toInt()
        } else {
            80
        }
        try {
            var queryMappings = mutableMapOf<String, XMLElement>()
            val server = ServerSocket()
            server.bind(InetSocketAddress("0.0.0.0", port)) // maybe use "::" for ipv6
            println("launched server socket on '0.0.0.0':'$port' - waiting for connections now")
            while (true) {
                val connection = server.accept()
                Thread {
                    Parallel.runBlocking {
//                        var timertotal = DateHelperRelative.markNow()
//                        var timer = timertotal
                        var connectionInBase = connection.getInputStream()
                        var connectionOutBase = connection.getOutputStream()
                        var connectionIn: BufferedReader? = null
                        var connectionOutPrinter: MyPrintWriter? = null
                        var connectionOutMy: MyOutputStream? = null
                        try {
                            connectionIn = BufferedReader(InputStreamReader(connectionInBase))
                            var line = connectionIn.readLine()
                            var path = ""
                            var isPost = false
                            val params = mutableMapOf<String, String>()
                            while (line != null && line.isNotEmpty()) {
                                if (line.startsWith("POST")) {
                                    isPost = true
                                    path = line.substring(5)
                                }
                                if (line.startsWith("GET")) {
                                    path = line.substring(4)
                                }
                                line = connectionIn.readLine()
                            }
                            var idx = path.indexOf(' ')
                            if (idx > 0) {
                                path = path.substring(0, idx)
                            }
                            idx = path.indexOf('?')
                            if (idx > 0) {
                                extractParamsFromString(path.substring(idx + 1), params)
                                path = path.substring(0, idx)
                            }
                            val paths = mutableMapOf<String, PathMappingHelper>()
                            paths[ "/sparql/jenaquery" ] = PathMappingHelper(true, mapOf(Pair("query", "SELECT * WHERE {?s <p> ?o . ?s ?p <o>}") to ::inputElement)) {
                                val connectionOutPrinter2 = MyPrintWriterExtension(connectionOutBase)
                                connectionOutPrinter = connectionOutPrinter2
                                printHeaderSuccess(connectionOutPrinter2)
                                connectionOutPrinter2.print(JenaWrapper.execQuery(params["query"]!!))
                                /*Coverage Unreachable*/
                            }
                            paths[ "/sparql/jenaload" ] = PathMappingHelper(true, mapOf(Pair("file", "/mnt/luposdate-testdata/sp2b/1024/complete.n3") to ::inputElement)) {
                                JenaWrapper.loadFromFile(params["file"]!!)
                                val connectionOutPrinter2 = MyPrintWriterExtension(connectionOutBase)
                                connectionOutPrinter = connectionOutPrinter2
                                printHeaderSuccess(connectionOutPrinter2)
                                connectionOutPrinter2.print("success")
                            }
                            paths[ "/sparql/query" ] = PathMappingHelper(
                                true,
                                mapOf(
                                    Pair("query", "SELECT * WHERE {?s <p> ?o . ?s ?p <o>}") to ::inputElement,
                                    Pair("evaluator", "") to { it, value ->
                                        var res: String = "<select name=\"$it\">"
                                        for (evaluator in EQueryResultToStreamExt.names) {
                                            res += "<option>$evaluator</option>"
                                        }
                                        res + "</select>"
                                    }
                                )
                            ) {
                                val e = params["evaluator"]
                                val evaluator = if (e == null) {
                                    EQueryResultToStreamExt.DEFAULT_STREAM
                                } else {
                                    val e2 = EQueryResultToStreamExt.names.indexOf(e)
                                    if (e2 >= 0) {
                                        e2
                                    } else {
                                        EQueryResultToStreamExt.DEFAULT_STREAM
                                    }
                                }
                                val node = LuposdateEndpoint.evaluateSparqlToOperatorgraphB(params["query"]!!, false)
                                node.getQuery().setCommunicationHandler(CommunicationHandler())
                                val connectionOutPrinter2 = MyPrintWriterExtension(connectionOutBase)
                                connectionOutPrinter = connectionOutPrinter2
                                LuposdateEndpoint.evaluateOperatorgraphToResultA(node, connectionOutPrinter2, evaluator)
                                /*Coverage Unreachable*/
                            }
                            paths["/sparql/operator" ] = PathMappingHelper(true, mapOf(Pair("query", "") to ::inputElement)) {
                                val connectionOutPrinter2 = MyPrintWriterExtension(connectionOutBase)
                                connectionOutPrinter = connectionOutPrinter2
                                printHeaderSuccess(connectionOutPrinter2)
                                connectionOutPrinter2.print(LuposdateEndpoint.evaluateOperatorgraphxmlToResultB(params["query"]!!, true))
                                /*Coverage Unreachable*/
                            }
                            paths["/import/turtle" ] = PathMappingHelper(true, mapOf(Pair("file", "/mnt/luposdate-testdata/sp2b/1024/complete.n3") to ::inputElement)) {
                                val dict = mutableMapOf<String, Int>()
                                val dictfile = params["bnodeList"]
                                if (dictfile != null) {
                                    File(dictfile).forEachLine {
                                        dict[it] = nodeGlobalDictionary.createNewBNode()
                                    }
                                }
                                val connectionOutPrinter2 = MyPrintWriterExtension(connectionOutBase)
                                connectionOutPrinter = connectionOutPrinter2
                                printHeaderSuccess(connectionOutPrinter2)
                                connectionOutPrinter2.print(LuposdateEndpoint.importTurtleFiles(params["file"]!!, dict))
                                /*Coverage Unreachable*/
                            }
                            paths["/import/estimatedPartitions" ] = PathMappingHelper(true, mapOf(Pair("file", "/mnt/luposdate-testdata/sp2b/1024/complete.n3.partitions") to ::inputElement)) {
                                LuposdateEndpoint.setEstimatedPartitionsFromFile(params["file"]!!)
                                val connectionOutPrinter2 = MyPrintWriterExtension(connectionOutBase)
                                connectionOutPrinter = connectionOutPrinter2
                                printHeaderSuccess(connectionOutPrinter2)
                                connectionOutPrinter2.println("Partitining-Scheme :: ")
                                connectionOutPrinter2.println("${Partition.estimatedPartitions0}")
                                connectionOutPrinter2.println("${Partition.estimatedPartitions1}")
                                connectionOutPrinter2.println("${Partition.estimatedPartitions2}")
                                connectionOutPrinter2.println("${Partition.estimatedPartitionsValid}")
                            }
                            paths["/import/intermediate" ] = PathMappingHelper(true, mapOf(Pair("file", "/mnt/luposdate-testdata/sp2b/1024/complete.n3") to ::inputElement)) {
                                val connectionOutPrinter2 = MyPrintWriterExtension(connectionOutBase)
                                connectionOutPrinter = connectionOutPrinter2
                                printHeaderSuccess(connectionOutPrinter2)
                                connectionOutPrinter2.print(LuposdateEndpoint.importIntermediateFiles(params["file"]!!))
                                /*Coverage Unreachable*/
                            }
                            paths["/import/xml" ] = PathMappingHelper(true, mapOf(Pair("xml", "") to ::inputElement)) {
                                val connectionOutPrinter2 = MyPrintWriterExtension(connectionOutBase)
                                connectionOutPrinter = connectionOutPrinter2
                                printHeaderSuccess(connectionOutPrinter2)
                                connectionOutPrinter2.print(LuposdateEndpoint.importXmlData(params["xml"]!!))
                                /*Coverage Unreachable*/
                            }
                            paths["/distributed/query/register"] = PathMappingHelper(true, mapOf()) {
                                println("query :: ${params["query"]}")
                                val xml = XMLParser(MyStringStream(params["query"]!!))
                                val keys = mutableListOf<String>()
                                for (c in xml.childs) {
                                    if (c.tag == "partitionDistributionProvideKey") {
                                        keys.add(c.attributes["key"]!!)
                                    }
                                }
                                println("keys :: $keys")
                                if (keys.size == 1) {
                                    queryMappings[keys[0]] = xml
                                } else {
                                    throw Exception("the number ${keys.size} is not implemented as number of keys for a distributed query.")
                                }
                            }
                            paths["/distributed/query/execute"] = PathMappingHelper(false, mapOf()) {
                                var queryXML = queryMappings[params["key"]!!]
                                var dictionaryURL = params["dictionaryURL"]!!
                                val connectionOutBuffered2 = MyOutputStream(connectionOutBase)
                                connectionOutBuffered = connectionOutBuffered2
                                if (queryXML == null) {
                                    throw Exception("this query was not registered before")
                                } else {
                                    val remoteDictionary = RemoteDictionaryClient(dictionaryURL)
                                    val query = Query(remoteDictionary)
                                    val node = XMLElement.convertToOPBase(query, queryXML) as POPBase
                                    var variables = Array(node.projectedVariables.size) { "" }
                                    var i = 0
                                    connectionOutBuffered2.writeInt(variables.size)
                                    for (v in node.projectedVariables) {
                                        variables[i++] = v
                                        val buf = v.encodeToByteArray()
                                        connectionOutBuffered2.writeInt(buf.size)
                                        connectionOutBuffered2.write(buf)
                                    }
                                    var p = Partition()
                                    val bundle = node.evaluate(p)
                                    val columns = Array(variables.size) { bundle.columns[variables[it]]!! }
                                    var buf = ResultSetDictionaryExt.nullValue + 1
                                    while (buf != ResultSetDictionaryExt.nullValue) {
                                        for (i in 0 until variables.size) {
                                            buf = columns[i].next()
                                            connectionOutBuffered2.writeInt(buf)
                                        }
                                    }
                                }
                                queryMappings.remove(params["key"]!!)
                            }
                            paths["/distributed/query/list"] = PathMappingHelper(true, mapOf()) {
                                val connectionOutPrinter2 = MyPrintWriterExtension(connectionOutBase)
                                connectionOutPrinter = connectionOutPrinter2
                                printHeaderSuccess(connectionOutPrinter2)
                                for ((k, v) in queryMappings) {
                                    connectionOutPrinter2.println("<p> $k :: $v </p>")
                                }
                            }
                            paths["/index.html" ] = PathMappingHelper(true, mapOf()) {
                                val connectionOutPrinter2 = MyPrintWriterExtension(connectionOutBase)
                                connectionOutPrinter = connectionOutPrinter2
                                connectionOutPrinter2.println("HTTP/1.1 200 OK")
                                connectionOutPrinter2.println("Content-Type: text/html; charset=UTF-8")
                                connectionOutPrinter2.println()
                                connectionOutPrinter2.println("<!DOCTYPE html>")
                                connectionOutPrinter2.println("<html lang=\"en\">")
                                connectionOutPrinter2.println("   <head>")
                                connectionOutPrinter2.println("   <title>Luposdate3000</title>")
                                connectionOutPrinter2.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>")
                                connectionOutPrinter2.println("<script>")
                                connectionOutPrinter2.println("   $(document).ready(function() {")
                                for ((k, v) in paths) {
                                    if (k.length> 1) {
                                        val formId = k.replace("/", "_")
                                        connectionOutPrinter2.println("       $('#$formId').on(\"submit\", function(event) {")
                                        connectionOutPrinter2.println("           var formData = {")
                                        for ((p, q) in v.params) {
                                            connectionOutPrinter2.println("               '${p.first}': $('#$formId [name=${p.first}]').val(),")
                                        }
                                        connectionOutPrinter2.println("           };")
                                        connectionOutPrinter2.println("           $.ajax({")
                                        connectionOutPrinter2.println("                   type: 'POST',")
                                        connectionOutPrinter2.println("                   url: '${k.substring(1)}',")
                                        connectionOutPrinter2.println("                   data: formData")
                                        connectionOutPrinter2.println("               })")
                                        connectionOutPrinter2.println("               .done(function(data) {")
                                        connectionOutPrinter2.println("                   $('#responseDiv').text(data);")
                                        connectionOutPrinter2.println("               });")
                                        connectionOutPrinter2.println("           event.preventDefault();")
                                        connectionOutPrinter2.println("       });")
                                    }
                                }
                                connectionOutPrinter2.println("   });")
                                connectionOutPrinter2.println("</script>")
                                connectionOutPrinter2.println("   </head>")
                                connectionOutPrinter2.println("   <body>")
                                for ((k, v) in paths) {
                                    if (k.length> 1) {
                                        val formId = k.replace("/", "_")
                                        connectionOutPrinter2.println("<form id=\"$formId\" >")
                                        for ((p, q) in v.params) {
                                            connectionOutPrinter2.println("${q(p.first,p.second)}")
                                        }
                                        connectionOutPrinter2.println("<input type=\"submit\" value=\"$k\" />")
                                        connectionOutPrinter2.println("</form>")
                                    }
                                }
                                connectionOutPrinter2.println("   <div id=\"responseDiv\"></div>")
                                connectionOutPrinter2.println("   </body>")
                                connectionOutPrinter2.println("</html>")
                            }
                            paths[""] = paths["/index.html"]!!
                            paths["/"] = paths["/index.html"]!!
                            val actionHelper = paths[path]
                            if (actionHelper == null) {
                                throw EnpointRecievedInvalidPath(path)
                            } else {
                                if (actionHelper.addPostParams && isPost) {
                                    val content = StringBuilder()
                                    while (connectionIn.ready()) {
                                        content.append(connectionIn.read().toChar())
                                    }
                                    extractParamsFromString(content.toString(), params)
                                }
                                actionHelper.action()
                            }
                        } catch (e: Throwable) {
                            e.printStackTrace()
                            val connectionOutPrinter2 = connectionOutPrinter
                            if (connectionOutPrinter2 != null) {
                                connectionOutPrinter2.println("HTTP/1.1 500 Internal Server Error")
                                connectionOutPrinter2.println()
                            }
                        } finally {
                            connectionOutBuffered?.flush()
                            connectionOutBuffered?.close()
                            connectionOutPrinter?.flush()
                            connectionOutPrinter?.close()
                            connectionOutBase.close()
                            connectionIn?.close()
                            connectionInBase.close()
                            connection?.close()
                        }
                    }
                }.start()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}
