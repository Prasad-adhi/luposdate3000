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
package lupos.simulator_db
import lupos.simulator_db.luposdate3000.MySimulatorAbstractPackage
import lupos.simulator_db.luposdate3000.MySimulatorOperatorGraphPackage
public class ApplicationLayerMultipleChilds(private val parent: IUserApplicationLayer) : IUserApplicationLayer {
    private var childs = mutableListOf<IUserApplication>()
    init {
        parent.addChildApplication(this)
    }
    override fun startUp() {
        for (child in childs) {
            child.startUp()
        }
    }
    override fun shutDown() {
        for (child in childs) {
            child.shutDown()
        }
    }
    override fun getAllChildApplications(): Set<IUserApplication> {
        var res = mutableSetOf<IUserApplication>()
        for (child in childs) {
            res.add(child)
            val c = child
            if (c is IUserApplicationLayer) {
                res.addAll(c.getAllChildApplications())
            }
        }
        return res
    }
    override fun addChildApplication(child: IUserApplication) {
        childs.add(child)
    }
    override fun receive(pck: IPayload): IPayload? {
        for (child in childs) {
            val pp = child.receive(pck)
            if (pp == null) {
                return null
            }
        }
        return pck
    }
    override fun send(destinationAddress: Int, pck: IPayload) {
        when (pck) {
            is QueryPackage -> println("ApplicationLayerMultipleChilds.send($destinationAddress)QueryPackage")
            is MySimulatorAbstractPackage -> println("ApplicationLayerMultipleChilds.send($destinationAddress)MySimulatorAbstractPackage ${pck.path}")
            is MySimulatorOperatorGraphPackage -> println("ApplicationLayerMultipleChilds.send($destinationAddress)MySimulatorOperatorGraphPackage")
            else -> println("ApplicationLayerMultipleChilds.sendElse$pck")
        }

        parent.send(destinationAddress, pck)
    }
    override fun getNextDatabaseHops(destinationAddresses: IntArray): IntArray {
        return parent.getNextDatabaseHops(destinationAddresses)
    }
}
