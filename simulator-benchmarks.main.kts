#!/usr/bin/env kotlin
/*
 * This }le is part of the Luposdate3000 distribution (https://github.com/luposdate3000/luposdate3000).
 * Copyright (c) 2020-2021, Institute of Information Systems (Benjamin Warnke and contributors of LUPOSDATE3000), University of Luebeck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or }TNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.File
import java.lang.ProcessBuilder.Redirect
File("simulator_output").deleteRecursively()
File("simulator_output").mkdirs()

inline fun execute(args: List<String>): List<String> {
println(args.joinToString(" "))
    val p = ProcessBuilder(args)
.redirectError(Redirect.INHERIT)
    val it = p.start()
    val res = it.getInputStream().bufferedReader().use { it.readText() }.split("\n")
    it.waitFor()
    if (it.exitValue() != 0) {
        println("exit-code:: " + it.exitValue())
    }
    return res
}


val baseCmd = execute(listOf("./launcher.main.kts", "--run", "--mainClass=Launch_Simulator_Config", "--dryMode=Enable")).filter { it.contains("exec") }.first().replace("exec :: ", "").split(" ")

var first = true
val BASE_PATH = "src/luposdate3000_simulator_iot/src/jvmMain/resources"
val json_evaluation = "${BASE_PATH}/evaluation.json"
val json_luposdate3000 = "${BASE_PATH}/luposdate3000.json"

val campusList = listOf(
    "campusNoSamples.json",
    "campus.json",
)
val routingList = listOf(
    "routing_RPL_Fast.json",
    "routing_AllShortestPath.json",
)
val queryList = listOf(
    "Q0.json",
    "Q1.json",
    "Q2.json",
    "Q3.json",
    "Q4.json",
    "Q5.json",
    "Q6.json",
    "Q7.json",
    "Q8.json",
)
val databaseTopologyList = listOf(
    "distributed.json",
    "distributedWithQueryHops.json",
    "central.json",
)
val dataDistributionList = listOf(
    "luposdate3000_by_key.json",
"luposdate3000_by_id_S_all_collations.json",
)
val multicastList = listOf(
    "luposdate3000MulticastDisabled.json",
    "luposdate3000MulticastEnabled.json",
)
val queryDistributionList = listOf(
    "luposdate3000_distribution_routing.json",
)
val networkTopologyList=listOf(
"scenarioParkingFull.json" ,
 "scenarioParkingRandom.json",
  "scenarioParkingRing.json",
  "scenarioParkingUniform.json",
)
val headerLine = mutableListOf<String>()
val contentLines = mutableListOf<MutableList<Double>>()
val attributeLines = mutableListOf<MutableList<String>>()
val specializedCmdHeaders = listOf("campus","networkTopology", "databaseTopology", "query", "dataDistribution", "evaluation", "luposdate3000", "queryDistribution", "multicast", "routing")
headerLine.addAll(specializedCmdHeaders)

loop@        for (query in queryList) {
            val json_query = "${BASE_PATH}/$query"
for (campus in campusList) {
    val json_campus = "${BASE_PATH}/$campus"
    for (routing in routingList) {
        val json_routing = "${BASE_PATH}/$routing"
            for (databaseTopology in databaseTopologyList) {
                val json_database_topology = "${BASE_PATH}/$databaseTopology"
                for (dataDistribution in dataDistributionList) {
                    val json_dataDistribution = "${BASE_PATH}/$dataDistribution"
                    for (multicast in multicastList) {
                        val json_multicast = "${BASE_PATH}/$multicast"
                        for (queryDistribution in queryDistributionList) {
                            val json_queryDistribution = "${BASE_PATH}/$queryDistribution"
for(networkTopology in networkTopologyList){
val json_networkTopology="${BASE_PATH}/$networkTopology"
                            if (databaseTopology == "central.json" && query != "Q0.json") {
                                //centralized has only traffic during initialization, afterwards all zero
                                continue
                            }
                            if (campus == "campusNoSamples.json" && query != "Q0.json") {
                                //there is no data at all, any query get all zero data
                                continue
                            }
                            if (campus == "campusNoSamples.json" && multicast=="luposdate3000MulticastEnabled.json") {
 //no difference because there are no samples at all
                                continue
                            }
                            if (multicast == "luposdate3000MulticastEnabled.json" && query != "Q0.json") {
                                //multicast is only relevant for insert, everything else is the same
                                continue
                            }
                            val specializedCmd = listOf(json_campus,json_networkTopology, json_database_topology, json_query, json_dataDistribution, json_evaluation, json_luposdate3000, json_queryDistribution, json_multicast, json_routing)
                            val cmd = baseCmd + specializedCmd
                            val measurementFile = execute(cmd).filter { it.contains("outputdirectory=") }.first().replace("outputdirectory=", "") + "/measurement.csv"
                            var firstLine = listOf<String>()
                            var contentLine = mutableListOf<Double>()
val attributeLine=specializedCmd.map { it.substring(it.lastIndexOf("/") + 1, it.length - 5) }.toMutableList()
                            attributeLines.add(attributeLine)
                            File(measurementFile).forEachLine {
                                if (firstLine.isEmpty()) {
                                    firstLine = it.split(",")
                                } else if (contentLine.isEmpty()) {
                                    val data = it.split(",")
                                    for (i in 0 until firstLine.size) {
                                        var idx = headerLine.indexOf(firstLine[i])
                                        if (idx < 0) {
                                            idx = headerLine.size
                                            headerLine.add(firstLine[i])
                                        }
idx-=specializedCmdHeaders.size
                                        while (contentLine.size <= idx) {
                                            contentLine.add(0.0)
                                        }
                                        contentLine[idx] = data[i].toDouble()
                                    }
                                }
                            }
                            contentLines.add(contentLine)
                        }
                    }
                }
            }
        }
    }
}
}
println()
println(headerLine.joinToString())
for (i in 0 until contentLines.size) {
    while (contentLines[i].size < headerLine.size-specializedCmdHeaders.size) {
        contentLines[i].add(0.0)
    }
    println(attributeLines[i].joinToString() + "," + contentLines[i].joinToString())
}