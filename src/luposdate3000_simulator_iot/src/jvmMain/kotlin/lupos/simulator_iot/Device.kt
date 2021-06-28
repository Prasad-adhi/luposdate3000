package lupos.simulator_iot

import kotlinx.datetime.Instant
import lupos.simulator_core.Entity
import lupos.simulator_iot.config.Configuration
import lupos.simulator_iot.geo.GeoLocation
import lupos.simulator_iot.net.IPayload
import lupos.simulator_iot.net.LinkManager
import lupos.simulator_iot.net.NetworkPackage
import lupos.simulator_iot.net.routing.IRoutingProtocol
import lupos.simulator_iot.net.routing.RPL
import lupos.simulator_iot.sensor.ISensor
import lupos.simulator_iot.sensor.ParkingSample
import kotlin.math.roundToLong

internal class Device(
    internal var location: GeoLocation,
    internal val address: Int,
    internal var database: DatabaseAdapter?,
    internal var sensor: ISensor?,
    internal val performance: Double,
    internal val supportedLinkTypes: IntArray
) : Entity() {
    internal val router: IRoutingProtocol = RPL(this)
    internal val linkManager: LinkManager = LinkManager(this)
    internal var isStarNetworkChild: Boolean = false

    internal var processedSensorDataPackages: Long = 0
        private set

    private lateinit var deviceStart: Instant

    private fun getNetworkDelay(destinationAddress: Int, pck: NetworkPackage): Long {
        return if (destinationAddress == address) {
            getProcessingDelay()
        } else {
            val size = NetworkPackage.headerSize + pck.payload.getSizeInBytes()
            linkManager.getTransmissionDelay(destinationAddress, size) + getProcessingDelay()
        }
    }

    private fun getProcessingDelay(): Long {
        val now = Time.stamp()
        val microDif = Time.differenceInMicroSec(deviceStart, now)
        val scaled = microDif * 100 / performance
        val millis = scaled / 1000
        return millis.roundToLong()
    }

    override fun onStartUp() {
        deviceStart = Time.stamp()
        sensor?.startSampling()
        database?.startUp()
        router.startRouting()
    }

    override fun onSteadyState() {
    }

    override fun onEvent(source: Entity, data: Any) {
        val pck = data as NetworkPackage
        deviceStart = Time.stamp()
        packageCounter++
        if (pck.destinationAddress == address) {
            processPackage(pck)
        } else {
            forwardPackage(pck)
        }
    }

    private fun processPackage(pck: NetworkPackage) {
        when {
            router.isControlPackage(pck) -> {
                router.processControlPackage(pck)
            }
            pck.payload is ParkingSample -> {
                processedSensorDataPackages++
                requireNotNull(
                    database
                ) { "The device $address has no database configured to store the ParkingSample." }
                database!!.saveParkingSample(pck.payload)
            }
            database?.isDatabasePackage(pck.payload) == true -> {
                database?.processPackage(pck.payload)
            }
            pck.payload is QuerySender.QueryPackage -> {
                requireNotNull(
                    database
                ) { "The device $address has no database configured to process the query." }
                database!!.processQuery(pck.payload.query)
            }
            else -> throw Exception("Undefined NetworkPackage or wrong device address.")
        }
    }

    override fun onShutDown() {
        sensor?.stopSampling()
        database?.shutDown()
    }

    private fun forwardPackage(pck: NetworkPackage) {
        val nextHop = router.getNextHop(pck.destinationAddress)
        val delay = getNetworkDelay(nextHop, pck)
        scheduleEvent(Configuration.devices[nextHop], pck, delay)
    }

    internal fun sendUnRoutedPackage(destinationNeighbour: Int, data: IPayload) {
        val pck = NetworkPackage(address, destinationNeighbour, data)
        val delay = getNetworkDelay(destinationNeighbour, pck)
        scheduleEvent(Configuration.devices[destinationNeighbour], pck, delay)
    }

    internal fun sendRoutedPackage(src: Int, dest: Int, data: IPayload) {
        val pck = NetworkPackage(src, dest, data)
        forwardPackage(pck)
    }

    internal fun sendSensorSample(destinationAddress: Int, data: IPayload) {
        sendRoutedPackage(address, destinationAddress, data)
    }

    internal fun hasDatabase(): Boolean = database != null

    override fun equals(other: Any?): Boolean {
        if (other === this) {
            return true
        }

        if (other !is Device) {
            return false
        }

        return address == other.address
    }

    override fun hashCode(): Int {
        return address
    }

    internal companion object {
        internal var packageCounter: Int = 0
            private set

        internal var observationPackageCounter: Int = 0
            private set

        internal fun resetCounter() {
            packageCounter = 0
            observationPackageCounter = 0
        }
    }
}
