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
package lupos.code_gen_test_03
import lupos.operator.base.Query
import lupos.shared.MemoryTable
import lupos.shared.inline.File
import lupos.simulator_core.Simulation
import lupos.simulator_db.luposdate3000.Application_Luposdate3000
import lupos.simulator_db.luposdate3000.Package_Luposdate3000_TestingCompareGraphPackage
import lupos.simulator_db.luposdate3000.Package_Luposdate3000_TestingImportPackage
import lupos.simulator_iot.SimulationRun
import kotlin.test.Test
import kotlin.test.fail

public class bind07BINDfixeddataforOWLDL {
    internal val inputData = arrayOf(
        File("src/jvmTest/resources/bind07BINDfixeddataforOWLDL.input").readAsString(),
    )
    internal val inputDataFile = arrayOf(
        "src/jvmTest/resources/bind07BINDfixeddataforOWLDL.input",
    )
    internal val inputGraph = arrayOf(
        "",
    )
    internal val inputType = arrayOf(
        ".ttl",
    )
    internal val targetData = File("src/jvmTest/resources/bind07BINDfixeddataforOWLDL.output").readAsString()
    internal val targetType = ".srx"
    internal val query = "PREFIX : <http://example.org/>  \n" +
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
        "SELECT ?s ?p ?o ?z \n" +
        "{ \n" +
        "  ?s ?p ?o . \n" +
        "  ?p a owl:DatatypeProperty .  \n" +
        "  { BIND(?o+1 AS ?z) } UNION { BIND(?o+2 AS ?z) } \n" +
        "} \n" +
        ""

    @Test
    public fun `bind07  BIND fixed data for OWL DL - in simulator - PartitionByID_O_AllCollations - Centralized - false - Thread`() {
        simulatorHelper(
            "../luposdate3000_simulator_iot/src/jvmTest/resources/autoIntegrationTest/test2.json",
            mutableMapOf(
                "predefinedPartitionScheme" to "PartitionByID_O_AllCollations",
                "mergeLocalOperatorgraphs" to true,
                "queryDistributionMode" to "Centralized",
                "useDictionaryInlineEncoding" to false,
                "REPLACE_STORE_WITH_VALUES" to false,
                "LUPOS_PARTITION_MODE" to "Thread",
            )
        )
    }

    @Test
    public fun `bind07  BIND fixed data for OWL DL - in simulator - PartitionByID_S_AllCollations - Centralized - true - Thread`() {
        simulatorHelper(
            "../luposdate3000_simulator_iot/src/jvmTest/resources/autoIntegrationTest/test2.json",
            mutableMapOf(
                "predefinedPartitionScheme" to "PartitionByID_S_AllCollations",
                "mergeLocalOperatorgraphs" to true,
                "queryDistributionMode" to "Centralized",
                "useDictionaryInlineEncoding" to true,
                "REPLACE_STORE_WITH_VALUES" to false,
                "LUPOS_PARTITION_MODE" to "Thread",
            )
        )
    }
    public fun simulatorHelper(fileName: String, cfg: MutableMap<String, Any>) {
        val simRun = SimulationRun()
        val config = simRun.parseConfig(fileName, false, { it.getOrEmptyObject("deviceType").getOrEmptyObject("LUPOSDATE_DEVICE").getOrEmptyObject("applications").getOrEmptyObject("Luposdate3000").putAll(cfg) })
        simRun.sim = Simulation(config.getEntities())
        simRun.sim.maxClock = if (simRun.simMaxClock == simRun.notInitializedClock) simRun.sim.maxClock else simRun.simMaxClock
        simRun.sim.steadyClock = if (simRun.simSteadyClock == simRun.notInitializedClock) simRun.sim.steadyClock else simRun.simSteadyClock
        simRun.sim.startUp()
        val instance = (config.devices.filter { it.userApplication != null }.map { it.userApplication!!.getAllChildApplications() }.flatten().filter { it is Application_Luposdate3000 }.first()as Application_Luposdate3000).instance
        val pkg0 = Package_Luposdate3000_TestingImportPackage(inputDataFile[0], inputGraph[0], inputType[0])
        var verifyExecuted1 = 0
        val pkg1 = Package_Luposdate3000_TestingCompareGraphPackage(null, MemoryTable.parseFromAny(inputData[0], inputType[0], Query(instance))!!, { verifyExecuted1++ }, inputGraph[0], instance)
        pkg0.setOnFinish(pkg1)
        var verifyExecuted2 = 0
        val pkg2 = Package_Luposdate3000_TestingCompareGraphPackage(query, MemoryTable.parseFromAny(targetData, targetType, Query(instance))!!, { verifyExecuted2++ }, "", instance)
        pkg1.setOnFinish(pkg2)
        config.addQuerySender(10, 1, 1, pkg0)
        simRun.sim.run()
        simRun.sim.shutDown()
        if (verifyExecuted1 == 0) {
            fail("pck1 not verified")
        }
        if (verifyExecuted2 == 0) {
            fail("pck2 not verified")
        }
    }
}
