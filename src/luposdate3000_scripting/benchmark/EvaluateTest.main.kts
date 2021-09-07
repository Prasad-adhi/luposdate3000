#!/usr/bin/env kotlin
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
@file:Import("DatabaseHandleVirtuoso.kt")
@file:Import("Config.kt")
@file:Import("DatabaseHandleBlazegraph.kt")
@file:Import("DatabaseHandleJena.kt")
@file:Import("DatabaseHandle.kt")
@file:Import("DatabaseHandleLuposdate3000Thread.kt")
@file:Import("DatabaseHandleLuposdate3000.kt")
@file:Import("DatabaseHandleLuposdate3000NoPartition.kt")
@file:Import("DatabaseHandleLuposdateMemory.kt")
@file:Import("DatabaseHandleLuposdateRDF3X.kt")
@file:Import("../../luposdate3000_shared/src/commonMain/kotlin/lupos/shared/EOperatingSystem.kt")
@file:Import("../../luposdate3000_shared/src/commonMain/kotlin/lupos/shared/EOperatingSystemExt.kt")
@file:Import("../../luposdate3000_shared/src/commonMain/kotlin/lupos/shared/ETripleComponentType.kt")
@file:Import("../../luposdate3000_shared/src/commonMain/kotlin/lupos/shared/ETripleComponentTypeExt.kt")
@file:Import("../../luposdate3000_shared_inline/src/commonMain/kotlin/lupos/shared/inline/Platform.kt")
@file:Import("../../luposdate3000_shared_inline/src/jvmMain/kotlin/lupos/shared/inline/Platform.kt")
@file:Import("../../luposdate3000_shared/src/jvmMain/kotlin/lupos/shared/DateHelperRelative.kt")
@file:Import("../../luposdate3000_shared/src/commonMain/kotlin/lupos/shared/DateHelperRelative.kt")
@file:CompilerOptions("-Xmulti-platform")

import lupos.benchmark.*
import lupos.shared.DateHelperRelative
import java.io.File

/*
 * pretty print xml :: 
 * apt install libxml2-utils
 * cat FILE_IN | sed 's/<?xml version="1.0"?>//g' | sed 's/<sparql [^>]*>/<sparql>/g' | xmllint --format - > FILE_OUT
 * for f in $(find /data/benchmark-results -type f -name "*xml") ; do cat $f | sed 's/<?xml version="1.0"?>//g' | sed 's/<sparql [^>]*>/<sparql>/g' | xmllint --format -  > $f.pretty.xml; done
 */

// configure databases
val allDatabases = mutableListOf(
    DatabaseHandleBlazegraph("/data/benchmark/"),//out of memory during load
    DatabaseHandleLuposdateMemory(port = 8080),//out of memory during load
    DatabaseHandleLuposdateRDF3X(workDir = "/data/benchmark/", port = 8080),
    DatabaseHandleVirtuoso(workDir = "/data/benchmark/"),
//    DatabaseHandleJena(port = 8080),
//    DatabaseHandleLuposdate3000NoPartition(workDir = "/data/benchmark/", port = 8080).setBufferManager("Inmemory"),
//    DatabaseHandleLuposdate3000NoPartition(workDir = "/data/benchmark/", port = 8080).setBufferManager("Persistent_Cached"),
)

// evaluating all tasks
val outputFolder = "/tmp/luposdate3000_output/"
File(outputFolder).mkdirs()
File("$outputFolder/log.txt").printWriter().use { logger ->
val datasetName="simulator_parking"
        println("use $datasetName")
        val datasetFile = "/src/luposdate3000/resources/myqueries/simulator_parking_input.ttl"
        for (databaseIdx in 0 until allDatabases.size) {
            val database = allDatabases[databaseIdx]
            println("use ${database.getName()}")
            try {
                var abortSignal = false
                val startTime = DateHelperRelative.markNow()
                database.launch(
                    datasetFile,
                    {
                        abortSignal = true
                    },
                    {
                        try {
                            if (!abortSignal) {
                                val importTime = DateHelperRelative.elapsedSeconds(startTime)
                                logger.println("import,$datasetName,${database.getName()},_,$importTime")
                                logger.flush()
                                for (queryID in 1 until 9){ 
val queryname="Q$queryID"
val query=File("/src/luposdate3000/resources/myqueries/simulator_parking_query$queryID.sparql").readText()
                                    println("use $queryname")
                                        val startTime2 = DateHelperRelative.markNow()
                                        val response = database.runQuery(query)
                                        val querytime = DateHelperRelative.elapsedSeconds(startTime2)
                                        logger.println("evaluate,$datasetName,${database.getName()},$queryname,$querytime")
                                        logger.flush()
                                        File("$outputFolder$datasetName/$queryname").mkdirs()
                                        File("$outputFolder$datasetName/$queryname/${database.getName()}.xml").printWriter().use { out ->
                                            out.println(response)
                                        }
                                    }
                            }
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        }
                    }
                )
            } catch (e: Throwable) {
                e.printStackTrace()
                println("errored import ${database.getName()} $datasetName")
            }
        }
    }