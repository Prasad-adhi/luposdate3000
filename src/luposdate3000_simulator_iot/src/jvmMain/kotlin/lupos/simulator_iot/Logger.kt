package lupos.simulator_iot

import kotlinx.datetime.Instant
import lupos.shared.inline.File
import lupos.simulator_core.ISimulationLifeCycle
import lupos.simulator_core.Simulation
import lupos.simulator_iot.config.Configuration
import lupos.simulator_iot.net.LinkManager
import lupos.simulator_iot.net.routing.RPL
import lupos.simulator_iot.sensor.ParkingSensor

internal object Logger : ISimulationLifeCycle {

    override lateinit var simulation: Simulation
    private lateinit var startUpTimeStamp: Instant
    private lateinit var shutDownTimeStamp: Instant
    private lateinit var realShutDownTimeStamp: Instant
    private val initStartTimeStamp: Instant = Time.stamp()

    override fun onStartUp() {
        startUpTimeStamp = Time.stamp()
        refreshDirectories()
        logStartUp()
    }

    override fun onSteadyState() {
    }

    override fun onShutDown() {
        shutDownTimeStamp = getSimulationTime()
        realShutDownTimeStamp = Time.stamp()
        logShutDown()
        resetCounters()
    }

    private fun refreshDirectories() {
        File(FilePaths.queryResult).deleteRecursively()
        File(FilePaths.queryResult).mkdirs()
        File(FilePaths.dbStates).deleteRecursively()
        File(FilePaths.dbStates).mkdirs()
    }

    private fun getSimulationTime(): Instant = Time.addMillis(startUpTimeStamp, simulation.getCurrentClock())

    internal fun getSimulationTimeString(): String = Time.toISOString(getSimulationTime())

    internal fun getInitDuration(): Double
        = Time.differenceInSeconds(initStartTimeStamp, startUpTimeStamp)

    internal fun getSimulationDuration(): Double
        = Time.differenceInSeconds(startUpTimeStamp, shutDownTimeStamp)

    internal fun getRealSimulationDuration(): Double
        = Time.differenceInSeconds(startUpTimeStamp, realShutDownTimeStamp)

    private fun logStartUp() {
        log("")
        log("")
        log("================================================")
        log("Simulation has started at ${Time.toISOString(startUpTimeStamp)}")
        log("")
        log("Number of devices: ${Configuration.devices.size}")
        log("Number of sensors: ${ParkingSensor.sensorCounter}")
        log("Number of links: ${LinkManager.linkCounter}")
    }

    private fun logShutDown() {
        log(getDODAGString())
        log("Total number of network packages: ${Device.packageCounter}")
        log("Number of received DIOs: ${RPL.dioCounter}, of which further sent: ${RPL.forwardedDioCounter}")
        log("Number of received DAOs: ${RPL.daoCounter}, of which further sent: ${RPL.forwardedDaoCounter}")
        log("Number of data packages: ${Device.observationPackageCounter}")
        log("Number of parking observations: ${ParkingSensor.totalSampleCounter}")
        log("")
        log("Simulation end time: ${Time.toISOString(shutDownTimeStamp)}")
        log("Difference to start time: ${getSimulationDuration()}s")
        log("Simulation completed")
        log("================================================")
        log("")
        log("")
    }

    private fun log(content: String) {
        println(content)
    }

    private fun resetCounters() {
        LinkManager.resetCounter()
        Device.resetCounter()
        RPL.resetCounter()
        ParkingSensor.resetCounter()
    }

    private fun getDODAGString(): String {
        val strBuilder = StringBuilder()
        strBuilder.appendLine("Constructed DODAG:")
        for (device in Configuration.devices) {
            strBuilder.appendLine(device.router)
        }
        return strBuilder.toString()
    }
}
