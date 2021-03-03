import com.javadocmd.simplelatlng.LatLng

object Stubs {

    fun createEmptyDevice(): Device {
        val loc = LatLng(0.0, 0.0)
        val name = ""
        val app = DatabaseApp()
        val sensors = ArrayList<Sensor>()
        val powerSupply = PowerSupply(-1.0)
        return Device(powerSupply, loc, name, app, sensors)
    }
}