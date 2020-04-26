package lupos.s05tripleStore

import lupos.s00misc.*
import lupos.s03resultRepresentation.*

class TripleStoreBulkImport {
    @JvmField
    val dictionaryBNode = MyMapStringIntPatriciaTrie()
    @JvmField
    val dataSPO = MyMapLong<MySetInt>()
    @JvmField
    val dataSOP = MyMapLong<MySetInt>()
    @JvmField
    val dataPOS = MyMapLong<MySetInt>()
    @JvmField
    val dataOSP = MyMapLong<MySetInt>()
    @JvmField
    val dataPSO = MyMapLong<MySetInt>()
    @JvmField
    val dataOPS = MyMapLong<MySetInt>()

    fun insert(si: Value, pi: Value, oi: Value) {
        dataSPO.getOrCreate((si.toLong() shl 32) + pi, { MySetInt() }).add(oi, {}, {})
        dataSOP.getOrCreate((si.toLong() shl 32) + oi, { MySetInt() }).add(pi, {}, {})
        dataPOS.getOrCreate((pi.toLong() shl 32) + oi, { MySetInt() }).add(si, {}, {})
        dataPSO.getOrCreate((pi.toLong() shl 32) + si, { MySetInt() }).add(oi, {}, {})
        dataOPS.getOrCreate((oi.toLong() shl 32) + pi, { MySetInt() }).add(si, {}, {})
        dataOSP.getOrCreate((oi.toLong() shl 32) + si, { MySetInt() }).add(pi, {}, {})
    }
}
