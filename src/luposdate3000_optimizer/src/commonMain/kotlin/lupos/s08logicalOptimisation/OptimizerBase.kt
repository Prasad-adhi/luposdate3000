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

package lupos.s08logicalOptimisation
import lupos.s00misc.EOptimizerID
import lupos.s00misc.SanityCheck
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.Query
import kotlin.jvm.JvmField
public abstract class OptimizerBase internal constructor(@JvmField public val query: Query, @JvmField public val optimizerID: EOptimizerID) {
    public abstract val classname: String
    public abstract /*suspend*/ fun optimize(node: IOPBase, parent: IOPBase?, onChange: () -> Unit): IOPBase
    public /*suspend*/ fun optimizeInternal(node: IOPBase, parent: IOPBase?, onChange: () -> Unit): IOPBase {
        SanityCheck {
            if (parent != null) {
                var found = false
                for (c in parent.getChildren()) {
                    if (c === node) {
                        found = true
                        break
                    }
                }
                SanityCheck.check { found }
            }
        }
        for (i in node.getChildren().indices) {
            val tmp = optimizeInternal(node.getChildren()[i], node, onChange)
            node.updateChildren(i, tmp)
        }
        return optimize(node, parent, onChange)
    }
    public open /*suspend*/ fun optimizeCall(node: IOPBase): IOPBase {
        return optimizeCall(node, {})
    }
    public open /*suspend*/ fun optimizeCall(node: IOPBase, onChange: () -> Unit): IOPBase {
        if (query.filtersMovedUpFromOptionals) {
            node.syntaxVerifyAllVariableExists(listOf(), true)
        }
        val res = optimizeInternal(node, null, onChange)
        if (query.filtersMovedUpFromOptionals) {
            res.syntaxVerifyAllVariableExists(listOf(), false)
        }
        return res
    }
}
