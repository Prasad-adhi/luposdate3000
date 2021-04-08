
import kotlinx.serialization.*
import kotlinx.serialization.json.*

object Configuration {

    var devices: MutableMap<String, Device> = HashMap()
        private set

    var jsonObjects: JsonObjects = JsonObjects()
        private set

    var entities: MutableList<Entity> = ArrayList()
        private set

    var randNetAddresses: MutableMap<String, MutableList<String>> = HashMap()
        private set


    fun parse(fileName: String) {
        resetVariables()
        readJsonFile(fileName)
        createFixedDevices()
        createFixedConnections()
        createRandomNetworks()
        createLinks()
    }


    private fun resetVariables() {
        devices = HashMap()
        randNetAddresses = HashMap()
        entities = ArrayList()
        jsonObjects = JsonObjects()
    }


    private fun readJsonFile(fileName: String) {
        val fileStr = readFileDirectlyAsText(fileName)
        jsonObjects = Json.decodeFromString(fileStr)
    }

    private fun readFileDirectlyAsText(fileName: String)
        = javaClass.classLoader!!.getResource(fileName)!!.readText()



    private fun addDevice(key: String, value: Device) {
        require(!devices.contains(key))
        devices[key] = value
    }

    private fun addNetAddresses(key: String, value: MutableList<String>) {
        require(!randNetAddresses.contains(key))
        randNetAddresses[key] = value
    }

    private fun createRandomNetworks() {
        for (network in jsonObjects.randomStarNetwork) {
            createRandomNetwork(network)
        }
    }

    private fun createRandomNetwork(network: RandomNetwork){
        val dataSink = devices[network.dataSink]!!
        dataSink.isWSNGateway = true
        val addressList = arrayListOf<String>()
        val deviceType = findDeviceType(network.type)
        val protocol = findProtocol(network.linkType)
        for (i in 1..network.number) {
            val deviceName = network.networkPrefix + i.toString()
            addressList.add(deviceName)
            val location = GeoLocation.getRandomLocationInRadius(dataSink.location, protocol.rangeInMeters)
            val createdDevice = createDevice(deviceType, location, deviceName)

            addDevice(createdDevice.name, createdDevice)
            createdDevice.networkPrefix = network.networkPrefix
            createSensors(network, createdDevice)
        }
        addNetAddresses(network.networkPrefix, addressList)
    }

    private fun createSensors(network: RandomNetwork, device: Device) {
        val numberOfSensors = network.sensorsPerDevice.number
        val sensorDeviceType = findDeviceType(network.sensorsPerDevice.type)
        val protocol = findProtocol(network.linkType)
        for(i in 1..numberOfSensors) {
            val sensorDeviceName = device.name + "Sensor" + i.toString()
            val location = GeoLocation.getRandomLocationInRadius(device.location, protocol.rangeInMeters)
            val createdDevice = createDevice(sensorDeviceType, location, sensorDeviceName)
            addDevice(createdDevice.name, createdDevice)
            createdDevice.networkPrefix = device.name
            //createConnection(createdDevice, device, protocol)
        }


    }


    private fun createFixedDevices() {
        for (fixedDevice in jsonObjects.fixedDevices) {
            val createdDevice = createFixedLocatedDevice(fixedDevice)
            addDevice(createdDevice.name, createdDevice)
        }
    }

    private fun createFixedConnections() {
        for (fixedLink in jsonObjects.fixedLinks) {
            val a = devices[fixedLink.endpointA]!!
            val b = devices[fixedLink.endpointB]!!
            a.addAvailableLink(b)
            b.addAvailableLink(a)
        }

    }



    private fun createFixedLocatedDevice(fixedDevices: FixedDevices): Device {
        val deviceType = findDeviceType(fixedDevices.deviceType)
        val location = GeoLocation(fixedDevices.latitude, fixedDevices.longitude)
        return createDevice(deviceType, location, fixedDevices.name)
    }

    private fun createDevice(deviceType: DeviceType, location: GeoLocation, name: String): Device {
        val powerSupply = PowerSupply(deviceType.powerCapacity)
        val application = createAppEntity(deviceType)
        val protocols = createProtocols(deviceType)
        val device = Device(powerSupply, location, name, application, null, protocols)
        val parkingSensor = createParkingSensor(deviceType, device)
        device.sensor = parkingSensor
        entities.add(device)
        return device
    }

    private fun createProtocols(deviceType: DeviceType): Set<LinkType> {
        val result = mutableSetOf<LinkType>()
        for (name in deviceType.supportedLinkTypes) {
            val linkType = findProtocol(name)
            result.add(linkType)
        }
        return result
    }



    private fun findDeviceType(typeName: String): DeviceType {
        val deviceType = jsonObjects.deviceType.find { typeName == it.name }
        requireNotNull(deviceType, { "device type name $typeName does not exist" })
        return deviceType
    }

    private fun findProtocol(name: String): LinkType {
        val element = jsonObjects.linkType.find { name == it.name }
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



    private fun createLinks() {
        val allDevices = ArrayList(devices.values)
        for(src in allDevices) {
            for(dest in allDevices) {
                src.addAvailableLink(dest)
            }
        }
    }



}
