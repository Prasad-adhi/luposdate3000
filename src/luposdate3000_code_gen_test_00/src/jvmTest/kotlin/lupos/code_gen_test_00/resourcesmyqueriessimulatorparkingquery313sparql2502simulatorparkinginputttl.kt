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
package lupos.code_gen_test_00

import lupos.operator.base.Query
import lupos.shared.MemoryTable
import lupos.shared.inline.File
import lupos.simulator_db.luposdate3000.Application_Luposdate3000
import lupos.simulator_db.luposdate3000.Package_Luposdate3000_TestingCompareGraphPackage
import lupos.simulator_db.luposdate3000.Package_Luposdate3000_TestingImportPackage
import simora.simulator_iot.SimulationRun
import simora.simulator_iot.config.addQuerySender
import kotlin.test.Test
import kotlin.test.fail

public class resourcesmyqueriessimulatorparkingquery313sparql2502simulatorparkinginputttl {
    internal val inputData = arrayOf(
        File("src/jvmTest/resources/resourcesmyqueriessimulatorparkingquery313sparql2502simulatorparkinginputttl.input").readAsString(),
    )
    internal val inputDataFile = arrayOf(
        "src/jvmTest/resources/resourcesmyqueriessimulatorparkingquery313sparql2502simulatorparkinginputttl.input",
    )
    internal val inputGraph = arrayOf(
        "",
    )
    internal val inputType = arrayOf(
        ".ttl",
    )
    internal val targetData = File("src/jvmTest/resources/resourcesmyqueriessimulatorparkingquery313sparql2502simulatorparkinginputttl.output").readAsString()
    internal val targetType = ".srx"
    internal val query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        "PREFIX parking: <https://github.com/luposdate3000/parking#> \n" +
        "select * \n" +
        "where { \n" +
        "?b  parking:area 1 . \n" +
        "} \n" +
        ""

    @Test
    public fun `resourcesmyqueriessimulatorparkingquery313sparql2502 simulatorparkinginputttl - in simulator - Simple - Centralized - true - None - RPL`() {
        simulatorHelper(
            "../luposdate3000_simulator_db/src/jvmTest/resources/autoIntegrationTest/test2.json",
            mutableMapOf(
                "predefinedPartitionScheme" to "Simple",
                "mergeLocalOperatorgraphs" to true,
                "queryDistributionMode" to "Centralized",
                "useDictionaryInlineEncoding" to true,
                "REPLACE_STORE_WITH_VALUES" to false,
                "LUPOS_PARTITION_MODE" to "None",
            ),
            "RPL",
        )
    }

    @Test
    public fun `resourcesmyqueriessimulatorparkingquery313sparql2502 simulatorparkinginputttl - in simulator - PartitionByIDTwiceAllCollations - Routing - true - Process - RPL`() {
        simulatorHelper(
            "../luposdate3000_simulator_db/src/jvmTest/resources/autoIntegrationTest/test1.json",
            mutableMapOf(
                "predefinedPartitionScheme" to "PartitionByIDTwiceAllCollations",
                "mergeLocalOperatorgraphs" to true,
                "queryDistributionMode" to "Routing",
                "useDictionaryInlineEncoding" to true,
                "REPLACE_STORE_WITH_VALUES" to false,
                "LUPOS_PARTITION_MODE" to "Process",
            ),
            "RPL",
        )
    }

    @Test
    public fun `resourcesmyqueriessimulatorparkingquery313sparql2502 simulatorparkinginputttl - in simulator - PartitionByID_O_AllCollations - Routing - true - Process - AllShortestPath`() {
        simulatorHelper(
            "../luposdate3000_simulator_db/src/jvmTest/resources/autoIntegrationTest/test1.json",
            mutableMapOf(
                "predefinedPartitionScheme" to "PartitionByID_O_AllCollations",
                "mergeLocalOperatorgraphs" to true,
                "queryDistributionMode" to "Routing",
                "useDictionaryInlineEncoding" to true,
                "REPLACE_STORE_WITH_VALUES" to false,
                "LUPOS_PARTITION_MODE" to "Process",
            ),
            "AllShortestPath",
        )
    }

    public fun simulatorHelper(fileName: String, database_cfg: MutableMap<String, Any>, routingProtocol: String) {
        val simRun = SimulationRun()
        val config = simRun.parseConfig(
            fileName, false,
            {
                it.getOrEmptyObject("deviceType").getOrEmptyObject("LUPOSDATE_DEVICE").getOrEmptyObject("applications").getOrEmptyObject("lupos.simulator_db.luposdate3000.ApplicationFactory_Luposdate3000").putAll(database_cfg)
                it.getOrEmptyObject("routing").putAll(mapOf("protocol" to routingProtocol))
            }
        )

        simRun.entities = config.getEntities()
        simRun.maxClock = if (simRun.maxClock == simRun.notInitializedClock) simRun.maxClock else simRun.maxClock
        simRun.steadyClock = if (simRun.steadyClock == simRun.notInitializedClock) simRun.steadyClock else simRun.steadyClock
        simRun.startUp()
        val instance = (config.devices.map { it.getAllChildApplications() }.flatten().filter { it is Application_Luposdate3000 }.first() as Application_Luposdate3000).instance
        val pkg0 = Package_Luposdate3000_TestingImportPackage(inputDataFile[0], inputGraph[0], inputType[0])
        var verifyExecuted1 = 0
        val pkg1 = Package_Luposdate3000_TestingCompareGraphPackage(null, MemoryTable.parseFromAny(inputData[0], inputType[0], Query(instance))!!, { verifyExecuted1++ }, inputGraph[0], instance)
        pkg0.setOnFinish(pkg1)
        var verifyExecuted2 = 0
        val pkg2 = Package_Luposdate3000_TestingCompareGraphPackage(query, MemoryTable.parseFromAny(targetData, targetType, Query(instance))!!, { verifyExecuted2++ }, "", instance)
        pkg1.setOnFinish(pkg2)
        config.addQuerySender(10, 1, 1, pkg0)
        simRun.run()
        simRun.shutDown()
        if (verifyExecuted1 == 0) {
            fail("pck1 not verified")
        }
        if (verifyExecuted2 == 0) {
            fail("pck2 not verified")
        }
    }
}
