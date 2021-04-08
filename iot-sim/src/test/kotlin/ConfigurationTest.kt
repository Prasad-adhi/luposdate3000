import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ConfigurationTest {


    @ParameterizedTest
    @ValueSource(strings = ["config/configEmptyFile.json"])
    fun `parse empty config file`(fileName: String) {
        Configuration.parse(fileName)
        Assertions.assertTrue(Configuration.devices.isEmpty())
        Assertions.assertTrue(Configuration.entities.isEmpty())
    }

    @ParameterizedTest
    @ValueSource(strings = ["config/configOneSimpleDevice.json"])
    fun `one simple device`(fileName: String) {
        Configuration.parse(fileName)
        val devices = Configuration.devices
        val deviceName = Configuration.jsonObjects.fixedDevices[0].name
        val lat = Configuration.jsonObjects.fixedDevices[0].latitude
        val lon = Configuration.jsonObjects.fixedDevices[0].longitude
        val location = GeoLocation(lat, lon)

        Assertions.assertEquals(Configuration.jsonObjects.fixedDevices.size, devices.size)
        Assertions.assertEquals(deviceName, devices[deviceName]!!.name)
        Assertions.assertEquals(location, devices[deviceName]!!.location)
        Assertions.assertNull(devices[deviceName]!!.application)
        Assertions.assertNull(devices[deviceName]!!.sensor)
        Assertions.assertTrue(devices[deviceName]!!.powerSupply.isInfinite)
        Assertions.assertEquals(1, Configuration.entities.size)
    }

    @ParameterizedTest
    @ValueSource(strings = ["config/configOneComplexDevice.json"])
    fun `one application device with sensors`(fileName: String) {
        Configuration.parse(fileName)
        val devices = Configuration.devices
        val deviceName = Configuration.jsonObjects.fixedDevices[0].name
        val numSensors = 1
        Assertions.assertTrue(devices[deviceName]!!.application is DatabaseApp)
        Assertions.assertNotNull(devices[deviceName]!!.sensor)
        Assertions.assertEquals(70.0, devices[deviceName]!!.powerSupply.actualCapacity)
        Assertions.assertFalse(devices[deviceName]!!.powerSupply.isInfinite)
        Assertions.assertEquals(2 + numSensors, Configuration.entities.size)
    }

    @ParameterizedTest
    @ValueSource(strings = ["config/configOneComplexDevice.json"])
    fun `sensors know their device`(fileName: String) {
        Configuration.parse(fileName)
        val devices = Configuration.devices
        val deviceName = "Tower1"
        val device = devices[deviceName]!!
        Assertions.assertEquals(device, device.sensor!!.device)
    }

    @ParameterizedTest
    @ValueSource(strings = ["config/configOneComplexDevice.json"])
    fun `sensors get correct values`(fileName: String) {
        Configuration.parse(fileName)
        val devices = Configuration.devices
        val deviceName = "Tower1"
        val device = devices[deviceName]!!
        Assertions.assertEquals(device, device.sensor!!.dataSink)
    }

    @ParameterizedTest
    @ValueSource(strings = ["config/configOneFixedConnection.json"])
    fun `two devices have a connection`(fileName: String) {
        Configuration.parse(fileName)
        val deviceAName = Configuration.jsonObjects.fixedLinks[0].endpointA
        val deviceBName = Configuration.jsonObjects.fixedLinks[0].endpointB
        val deviceA = Configuration.devices[deviceAName]!!
        val deviceB = Configuration.devices[deviceBName]!!

        Assertions.assertNotNull(deviceA.getAvailableLink(deviceB))
        Assertions.assertNotNull(deviceB.getAvailableLink(deviceA))
    }

    @ParameterizedTest
    @ValueSource(strings = ["config/configOneRandomNetwork.json"])
    fun `count number of devices in random network`(fileName: String) {
        Configuration.parse(fileName)
        val devices = Configuration.devices
        val network = Configuration.jsonObjects.randomStarNetwork[0]
        val starNet = Configuration.randStarNetworks[network.networkPrefix]!!
        Assertions.assertEquals(1 + network.number, devices.size)
        Assertions.assertEquals(network.number, starNet.childs.size)
    }

    @ParameterizedTest
    @ValueSource(strings = ["config/configOneRandomNetwork.json"])
    fun `in star network every child is linked to root and vice versa`(fileName: String) {
        Configuration.parse(fileName)
        val networkPrefix = Configuration.jsonObjects.randomStarNetwork[0].networkPrefix
        val starNet = Configuration.randStarNetworks[networkPrefix]!!
        for(child in starNet.childs) {
            Assertions.assertTrue(child.hasAvailAbleLink(starNet.parent))
            Assertions.assertTrue(starNet.parent.hasAvailAbleLink(child))
        }
    }


    @ParameterizedTest
    @ValueSource(strings = ["config/configOneFixedConnection.json"])
    fun `check link between two fixed devices`(fileName: String) {
        Configuration.parse(fileName)
        val device1Address = Configuration.jsonObjects.fixedDevices[0].name
        val device2Address = Configuration.jsonObjects.fixedDevices[1].name
        val device1 = Configuration.devices[device1Address]!!
        val device2 = Configuration.devices[device2Address]!!
        val link1 = device1.getAvailableLink(device2)
        val link2 = device2.getAvailableLink(device1)

        Assertions.assertTrue(device1.hasAvailAbleLink(device2))
        Assertions.assertTrue(device2.hasAvailAbleLink(device1))
        Assertions.assertEquals(1, device1.numOfAvailAbleLinks())
        Assertions.assertEquals(1, device2.numOfAvailAbleLinks())
        Assertions.assertEquals(link1!!.distanceInMeters, link2!!.distanceInMeters)
    }


}
