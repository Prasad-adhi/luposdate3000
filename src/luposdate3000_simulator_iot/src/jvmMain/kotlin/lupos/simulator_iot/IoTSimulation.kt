package lupos.simulator_iot

import lupos.simulator_core.Simulation
import lupos.simulator_iot.config.Configuration
import lupos.simulator_iot.log.Logger
import lupos.simulator_iot.log.LoggerCollection

public fun main() {

    //IoTSimulation().simulate("${FilePaths.jvmResource}\\Exception_2Sensors1Database.json")
    //IoTSimulation().simulate("${FilePaths.jvmResource}\\anotherException_2Sensor1Database.json")
    //IoTSimulation().simulate("${FilePaths.jvmResource}\\Exception_2DBwith1Sensor.json")
    //IoTSimulation().simulate("${FilePaths.jvmResource}\\star.json")
    IoTSimulation().measureStarPerformance()


}

public class IoTSimulation {



    public fun simulate(configFileName: String) {
        Configuration.parse(configFileName)
        val sim = Simulation(
            entities =  Configuration.getEntities(),
            callback = Logger
        )
        sim.startSimulation()
    }



    public fun measureStarPerformance() {
        val loggerCollection = LoggerCollection()
        val testWithDatabase = true
        var arr: IntArray = if(testWithDatabase)
            buildNodeSizesArray(130, 10) // OutOfMemoryError >1330 DBs with 2048 heap space
        else
            buildNodeSizesArray(100, 1000)

        Configuration.parse("${FilePaths.jvmResource}\\starPerformance.json")
        for (numberOfChilds in arr)
            runSimulationStarPerformance(numberOfChilds, loggerCollection)
    }

    private fun buildNodeSizesArray(arrSize: Int, delta: Int): IntArray {
        val arr = IntArray(arrSize) {0}
        for (i in arr.withIndex())
            arr[i.index] = i.index * delta
        return arr
    }

    private fun runSimulationStarPerformance(numberOfChilds: Int, collection: LoggerCollection) {
        Logger.reset()
        val configFileName = "${FilePaths.jvmResource}\\starPerformance.json"
        val jsonObjects = Configuration.readJsonFile(configFileName)
        jsonObjects.randomStarNetwork[0].number = numberOfChilds
        Configuration.parse(jsonObjects)
        val entities = Configuration.getEntities()
        val sim = Simulation(entities =  entities, callback = Logger)
        sim.startSimulation()
        collection.add(Logger, entities.size)
    }

}
