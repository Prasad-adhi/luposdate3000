package lupos.simulator_iot.queryproc.pck

import lupos.simulator_iot.models.net.IPayload

internal class DBSequenceEndPackage(
    sourceAddress: Int,
    destinationAddress: Int,
    val numberOfPackages: Int,
) : IPayload, SequencedPackage(sourceAddress, destinationAddress) {

    override fun getSizeInBytes(): Int {
        // ignore this package size
        return 0
    }

    override fun toString(): String {
        return "DBSequenceEndPck(numberOfPacks $numberOfPackages, sequenceID $sequenceID)"
    }
}
