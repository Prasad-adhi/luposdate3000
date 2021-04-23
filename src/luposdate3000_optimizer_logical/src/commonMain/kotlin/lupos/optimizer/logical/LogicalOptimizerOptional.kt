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
package lupos.optimizer.logical

import lupos.operator_logical.IOPBase
import lupos.operator_logical.Query
import lupos.operator_logical.multiinput.LOPJoin
import lupos.operator_logical.noinput.OPEmptyRow
import lupos.operator_logical.singleinput.LOPOptional

public class LogicalOptimizerOptional(query: Query) : OptimizerBase(query, EOptimizerIDExt.LogicalOptimizerOptionalID, "LogicalOptimizerOptional") {
    override /*suspend*/ fun optimize(node: IOPBase, parent: IOPBase?, onChange: () -> Unit): IOPBase {
        var res: IOPBase = node
        if (node is LOPOptional) {
            res = LOPJoin(query, OPEmptyRow(query), node.getChildren()[0], true)
        }
        return res
    }
}
