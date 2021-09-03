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
package lupos.optimizer.distributed.query

import lupos.shared.IQuery
import lupos.shared.XMLElement

public class DistributedOptimizerAssignParent : DistributedOptimizerBase {
    override fun optimize(query: IQuery, key: Int, node: XMLElement, dependenciesTopDown: Set<Int>, dependenciesBottomUp: Set<Int>, keytoHostMapGet: (Int) -> String?, keytoHostMapSet: (Int, String) -> Unit, onChange: () -> Unit) {
        if (dependenciesBottomUp.isNotEmpty()) {
            val possibleHost = keytoHostMapGet(dependenciesBottomUp.first())
            if (possibleHost != null) {
                for (s in dependenciesBottomUp) {
                    if (possibleHost != keytoHostMapGet(s)) {
                        return
                    }
                }
                keytoHostMapSet(key, possibleHost)
                onChange()
            }
        }
    }
}
