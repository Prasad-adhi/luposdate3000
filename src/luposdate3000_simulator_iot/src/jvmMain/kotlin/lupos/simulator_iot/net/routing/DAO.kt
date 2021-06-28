package lupos.simulator_iot.net.routing

import lupos.simulator_iot.net.IPayload

internal class DAO(
    internal val isPath: Boolean,
    internal val destinations: IntArray,
    internal val hopHasDatabase: Boolean,
    internal val existingDatabaseHops: IntArray
) : IPayload {
    override fun getSizeInBytes(): Int {
        val ipv6InBytes = 16
        val destinationsSize = destinations.size * ipv6InBytes
        val existingDatabaseHopsSize = existingDatabaseHops.size * ipv6InBytes
        return destinationsSize + existingDatabaseHopsSize
    }

    override fun toString(): String {
        return "DAO(isPath=$isPath, destinations=${destinations.contentToString()}, hopHasDatabase=$hopHasDatabase, existingDatabaseHops=${existingDatabaseHops.contentToString()})"
    }


}
