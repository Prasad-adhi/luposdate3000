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

package lupos.simulator_iot.unit
import lupos.simulator_iot.SimulationRun
import lupos.simulator_iot.models.routing.RPL
import lupos.simulator_iot.utils.TimeUtils
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RoutingSimulationTest {

    companion object {
        private const val prefix = "src/jvmTest/resources/routingSimulationTest"
    }

    @Test
    fun runSimulationWithoutEntities() {
        val simRun = SimulationRun()
        val config = simRun.parseConfig("$prefix/runSimulationWithoutEntities.json")
        simRun.startSimulation(config)

        assertEquals(0, simRun.sim.clock)
    }

    @Test
    fun starNetworkIsASimpleDODAG() {
        val simRun = SimulationRun()
        val config = simRun.parseConfig("$prefix/starNetworkIsASimpleDODAG.json")

        val starNet = config.randStarNetworks["garageA"]!!
        val parent = starNet.root
        val parentRouter = parent.router as RPL

        val child1 = starNet.children[0]
        val child1Router = child1.router as RPL
        val child2 = starNet.children[1]
        val child2Router = child2.router as RPL
        assertFalse(child1Router.hasParent())
        assertFalse(child2Router.hasParent())

        simRun.simMaxClock = TimeUtils.toNanoSec(200)
        simRun.startSimulation(config)

        assertTrue(child1Router.hasParent())
        assertTrue(child2Router.hasParent())
        assertFalse(parentRouter.hasParent())
        assertEquals(RPL.ROOT_RANK, parentRouter.rank)
        assertTrue(child1Router.rank >= RPL.ROOT_RANK)
        assertTrue(child2Router.rank >= RPL.ROOT_RANK)
        assertEquals(parent.address, child1Router.preferredParent.address)
        assertEquals(parent.address, child2Router.preferredParent.address)
        assertEquals(parentRouter.rank, child1Router.preferredParent.rank)
        assertEquals(parentRouter.rank, child2Router.preferredParent.rank)
    }

    @Test
    fun meshToDODAG() {
        val simRun = SimulationRun()
        val config = simRun.parseConfig("$prefix/meshToDODAG.json")
        val root = config.getRootDevice()
        val rootRouter = root.router as RPL
        simRun.startSimulation(config)

        assertEquals(config.getNumberOfDevices() - 1, rootRouter.routingTable.getDestinations().size)
    }

    @Test
    fun upwardRouteForwarding() {
        // Send data from the leaf F to the root A
        val simRun = SimulationRun()
        val config = simRun.parseConfig("$prefix/upwardRouteForwarding.json")
        simRun.simMaxClock = TimeUtils.toNanoSec(300)
        simRun.startSimulation(config)

        config.getDeviceByName("A")
    }

    @Test
    fun downwardRouteForwarding() {
        // Send data from the root A to the leaf F
        val simRun = SimulationRun()
        val config = simRun.parseConfig("$prefix/downwardRouteForwarding.json")
        simRun.simMaxClock = TimeUtils.toNanoSec(200)
        simRun.startSimulation(config)

        config.getDeviceByName("F")
    }

    @Test
    fun upAndDownwardRouteForwarding() {
        // Send data from the leaf F to the leaf D
        val simRun = SimulationRun()
        val config = simRun.parseConfig("$prefix/upAndDownwardRouteForwarding.json")
        simRun.simMaxClock = TimeUtils.toNanoSec(800)
        simRun.startSimulation(config)

        config.getDeviceByName("D")
    }

    @Test
    fun sensorFromStarSendOverMesh() {
        // Send data from one Sensor over Mesh to fixed node
        val simRun = SimulationRun()
        val config = simRun.parseConfig("$prefix/sensorFromStarSendOverMesh.json")

        config.getDeviceByName("Fog")
        val starRoot = config.randStarNetworks.getValue("1")
        val child = starRoot.children[0]
        assertTrue(child.linkManager.hasLink(starRoot.root))

        simRun.startSimulation(config)
    }

    @Test
    fun sensorsFromStarSendOverFixedLinks() {
        val simRun = SimulationRun()
        val config = simRun.parseConfig("$prefix/sensorsFromStarSendOverFixedLinks.json")

        config.getDeviceByName("DODAG ROOT")

        simRun.startSimulation(config)
    }
}
