import config.LinkType
import geo.GeoLocation
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LinkManagerTest {

    @Test
    fun cannotLinkWithItself() {
        val linkTypeX = LinkType("X", 50, 7 )
        LinkManager.sortedLinkTypes = arrayOf(linkTypeX)
        val device: Device = Stubs.createEmptyDevice(0, intArrayOf(0))
        device.linkManager.setLinkIfPossible(device)
        Assertions.assertNull(device.linkManager.getLink(device))
        Assertions.assertFalse(device.linkManager.hasLink(device))
    }



    @Test
    fun `two devices with different linkTypes cannot link`() {
        val deviceOne: Device = Stubs.createEmptyDevice(1)
        val linkType = LinkType("X")
        LinkManager.sortedLinkTypes = arrayOf(linkType)
        val deviceTwo: Device = Stubs.createEmptyDevice(2, intArrayOf(0))
        Assertions.assertNull(deviceOne.linkManager.getBestLink(deviceTwo))
        Assertions.assertNull(deviceTwo.linkManager.getBestLink(deviceOne))
    }

    @Test
    fun `two devices are too far away to link`() {
        val linkTypeX = LinkType("X", 50, 7 )
        LinkManager.sortedLinkTypes = arrayOf(linkTypeX)

        val deviceOne: Device = Stubs.createEmptyDevice(1, intArrayOf(0))
        val deviceTwo: Device = Stubs.createEmptyDevice(2, intArrayOf(0))
        val distance = 51
        deviceTwo.location = GeoLocation.createNorthernLocation(deviceOne.location, distance)
        Assertions.assertNull(deviceOne.linkManager.getBestLink(deviceTwo))
        Assertions.assertNull(deviceTwo.linkManager.getBestLink(deviceOne))
    }

    @Test
    fun `two devices link with most suitable linkType`() {
        val linkTypeX = LinkType("X", 50, 7 )
        val linkTypeY = LinkType("Y", 50, 8 )
        val linkTypeZ = LinkType("Z", 48, 9 )
        LinkManager.sortedLinkTypes = arrayOf(linkTypeX, linkTypeY, linkTypeZ)
        val deviceOne: Device = Stubs.createEmptyDevice(1, intArrayOf(0, 1, 2))
        val deviceTwo: Device = Stubs.createEmptyDevice(2, intArrayOf(0, 1, 2))
        deviceTwo.location = GeoLocation.createNorthernLocation(deviceOne.location, 49)

        val actualLink1 = deviceOne.linkManager.getBestLink(deviceTwo)
        val actualLink2 = deviceTwo.linkManager.getBestLink(deviceOne)
        Assertions.assertNotNull(actualLink1)
        Assertions.assertNotNull(actualLink2)
        Assertions.assertEquals(actualLink1, actualLink2)
        Assertions.assertEquals(1, actualLink1!!.linkTypeIndex)
        Assertions.assertEquals("Y", LinkManager.getLinkTypeByIndex(actualLink1.linkTypeIndex).name)
    }

    @Test
    fun `get sorted LinkType Indices`() {
        val linkTypeW = LinkType("W", 51, 13 )
        val linkTypeX = LinkType("X", 50, 7 )
        val linkTypeY = LinkType("Y", 50, 8 )
        val linkTypeZ = LinkType("Z", 48, 9 )
        LinkManager.sortedLinkTypes = arrayOf(linkTypeW, linkTypeX, linkTypeY, linkTypeZ)

        val expected1 = intArrayOf(1, 2)
        val actual1 = LinkManager.getSortedLinkTypeIndices(arrayListOf(linkTypeY, linkTypeZ))
        val expected2 = intArrayOf(0, 3)
        val actual2 = LinkManager.getSortedLinkTypeIndices(arrayListOf(linkTypeW, linkTypeX))

        Assertions.assertTrue(expected1.contentEquals(actual1))
        Assertions.assertTrue(expected2.contentEquals(actual2))
    }

}