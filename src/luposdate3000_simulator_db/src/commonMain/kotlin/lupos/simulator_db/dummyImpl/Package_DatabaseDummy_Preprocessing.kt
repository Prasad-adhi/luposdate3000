/*
 * This file is part of the Luposdate3000 distribution (https://github.com/luposdate3000/luposdate3000).
 * Copyright (c) 2020-2021, Institute of Information Systems (Benjamin Warnke and contributors of LUPOSDATE3000), University of Luebeck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package lupos.simulator_db.dummyImpl
import lupos.shared.UUID_Counter
import lupos.simulator_db.IPackage_Database

public class Package_DatabaseDummy_Preprocessing(
    public val destinationAddresses: IntArray, // Richtung triple store
    public val operatorGraphParts: ByteArray,
    public val senderAddress: Int, // dies MUSS ein DB-node sein ... von wo kommt das paket
    public val queryID: Int, // die ist immer gleich für alles was zu einem "Package_Application_DatabaseDummy_Query" gehört
) : IPackage_Database {
    public val pckID: Long = UUID_Counter.getNextUUID()
    override fun getPackageID(): Long = pckID
    override fun getSizeInBytes(): Int {
        @Suppress("UnnecessaryVariable")
        val dummySize = 20
        return dummySize
    }

    override fun getContentLogString(): String {
        return "Package_DatabaseDummy_Preprocessing(dests=${destinationAddresses.contentToString()}, operatorGraphParts=${operatorGraphParts.contentToString()}, senderAddress=$senderAddress, queryID=$queryID)"
    }
}