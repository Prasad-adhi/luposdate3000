import com.javadocmd.simplelatlng.LatLng
import com.javadocmd.simplelatlng.LatLngTool
import com.javadocmd.simplelatlng.util.LengthUnit
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.lang.IllegalArgumentException

object Configuration {

    var devices: MutableMap<String, Device> = HashMap()
        private set

    var jsonObjects: JsonObjects = JsonObjects()
        private set

    var entities: MutableList<Entity> = ArrayList()
        private set

    var graph = Graph<String, ConnectionParameter>(arrayListOf())
        private set

    private var connections: MutableList<Connection> = ArrayList()

    private class Connection(
        val src: String,
        val dest: String,
        val params: ConnectionParameter)

    fun parse(fileName: String) {
        resetVariables()
        readJsonFile(fileName)
        createFixedDevices()
        createFixedConnections()
        createRandomNetworks()
        createGraph()
    }


    private fun resetVariables() {
        devices = HashMap()
        entities = ArrayList()
        jsonObjects = JsonObjects()
        connections = ArrayList()
        graph = Graph(arrayListOf())
    }


    private fun readJsonFile(fileName: String) {
        val fileStr = readFileDirectlyAsText(fileName)
        jsonObjects = Json.decodeFromString(fileStr)
    }

    private fun readFileDirectlyAsText(fileName: String)
        = javaClass.classLoader!!.getResource(fileName)!!.readText()



    private fun put(key: String, device: Device) {
        require(!devices.contains(key))
        devices[key] = device
    }



    private fun createRandomNetworks() {
        for (network in jsonObjects.randomNetwork) {
            createRandomNetwork(network)
        }
    }

    private fun createRandomNetwork(network: RandomNetwork){
        val dataSink = devices[network.dataSink]!!
        val deviceType = findDeviceType(network.type)
        val protocol = findProtocol(network.protocolType)
        for (i in 1..network.number) {
            val deviceName = network.networkPrefix + i.toString()
            val location = RandomGenerator.getLatLngInRadius(dataSink.location, protocol.rangeInMeters)
            val createdDevice = createDevice(deviceType, location, deviceName)

            put(createdDevice.name, createdDevice)
            createdDevice.networkPrefix = network.networkPrefix
            createSensors(network, createdDevice)
        }
    }

    private fun createSensors(network: RandomNetwork, device: Device) {
        val numberOfSensors = network.sensorsPerDevice.number
        val sensorDeviceType = findDeviceType(network.sensorsPerDevice.type)
        val protocol = findProtocol(network.protocolType)
        for(i in 1..numberOfSensors) {
            val sensorDeviceName = device.name + "Sensor" + i.toString()
            val location = RandomGenerator.getLatLngInRadius(device.location, protocol.rangeInMeters)
            val createdDevice = createDevice(sensorDeviceType, location, sensorDeviceName)
            put(createdDevice.name, createdDevice)
            createdDevice.networkPrefix = device.name
            createConnection(createdDevice, device, protocol)
        }


    }


    private fun createFixedDevices() {
        for (fixedDevice in jsonObjects.fixedDevices) {
            val createdDevice = createFixedLocatedDevice(fixedDevice)
            put(createdDevice.name, createdDevice)
        }
    }

    private fun createFixedConnections() {
        for (fixedCon in jsonObjects.fixedConnection) {
            val protocol = findProtocol(fixedCon.protocolType)
            val a = devices[fixedCon.endpointA]!!
            val b = devices[fixedCon.endpointB]!!
            createConnection(a, b, protocol)
        }

    }

    private fun createConnection(src: Device, dest: Device, p: ProtocolType) {
        val distance = src.getDistanceInMeters(dest);
        val param = ConnectionParameter(
            p.dataRateInKbps, p.rangeInMeters.toDouble(),
            p.name, distance
        )
        val con = Connection(src.name, dest.name, param)
        connections.add(con)
    }



    private fun createFixedLocatedDevice(fixedDevices: FixedDevices): Device {
        val deviceType = findDeviceType(fixedDevices.deviceType)
        val location = LatLng(fixedDevices.latitude, fixedDevices.longitude)
        return createDevice(deviceType, location, fixedDevices.name)
    }

    private fun createDevice(deviceType: DeviceType, location: LatLng, name: String): Device {
        val powerSupply = PowerSupply(deviceType.powerCapacity)
        val application = createAppEntity(deviceType)
        val protocols = createProtocols(deviceType)
        val device = Device(powerSupply, location, name, application, null, protocols)
        val parkingSensor = createParkingSensor(deviceType, device)
        device.sensor = parkingSensor
        entities.add(device.networkCard)
        return device
    }

    private fun createProtocols(deviceType: DeviceType): Set<ProtocolType> {
        val result = mutableSetOf<ProtocolType>()
        for (name in deviceType.supportedProtocolType) {
            val protocolType = findProtocol(name)
            result.add(protocolType)
        }
        return result
    }



    private fun findDeviceType(typeName: String): DeviceType {
        val deviceType = jsonObjects.deviceType.find { typeName == it.name }
        requireNotNull(deviceType, { "device type name $typeName does not exist" })
        return deviceType
    }

    private fun findProtocol(name: String): ProtocolType {
        val element = jsonObjects.protocolType.find { name == it.name }
        requireNotNull(element, { "protocol $name does not exist" })
        return element
    }

    private fun createAppEntity(deviceType: DeviceType) : Entity? {
        var app: Entity? = null
        if (deviceType.application) {
            app = DatabaseApp()
            entities.add(app)
        }

        return app
    }

    private fun createParkingSensor(deviceType: DeviceType, device: Device): ParkingSensor? {
        var sensor: ParkingSensor? = null
        if(deviceType.parkingSensor) {
            val name = device.name + "_ParkingSensor"
            sensor = ParkingSensor(name, device)
            entities.add(sensor)
        }
        return sensor
    }


    private fun createGraph() {
        val keyList = ArrayList(devices.keys)
        graph = Graph(keyList)
        for (con in connections) {
            graph.addUndirectedEdge(con.src, con.dest, con.params)
        }
    }





}
