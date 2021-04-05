package integration

import Configuration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import Simulation

class SimulationConfigurationTest {


    @ParameterizedTest
    @ValueSource(strings = ["sim/EmptyFile.json"])
    fun `run simulation without entities`(fileName: String) {
        Configuration.parse(fileName)
        Simulation.initialize(Configuration.entities)
        val endClock = Simulation.runSimulation()
        Assertions.assertEquals(0, endClock)
    }

    @ParameterizedTest
    @ValueSource(strings = ["sim/OneDeviceWithParkingSensor.json"])
    fun `message to own device do not delay`(fileName: String) {
        Configuration.parse(fileName)
        val maxClock: Long = ParkingSensor.dataRateInSeconds.toLong() * 2
        Simulation.initialize(Configuration.entities, maxClock)
        val endClock = Simulation.runSimulation()
        Assertions.assertEquals(maxClock, endClock)
    }

//    @ParameterizedTest
//    @ValueSource(strings = ["sim/OneRandomNetwork.json"])
//    fun `message to other connected device do delay`(fileName: String) {
//        Configuration.parse(fileName)
//        val randomNetwork = Configuration.jsonObjects.randomNetwork[0]
//        val sendingDeviceAddress = randomNetwork.name + "1"
//        val sendingDevice = Configuration.devices[sendingDeviceAddress]!!
//        val receivingDeviceAddress = randomNetwork.dataSink
//        val receivingDevice = Configuration.devices[receivingDeviceAddress]!!
//        val delay = sendingDevice.networkCard.getNetworkDelay(receivingDevice)
//
//        val maxClock: Long = ParkingSensor.dataRateInSeconds.toLong() + delay
//        Simulation.initialize(Configuration.entities, maxClock)
//        val endClock = Simulation.runSimulation()
//        Assertions.assertEquals(maxClock, endClock)
//    }

}
