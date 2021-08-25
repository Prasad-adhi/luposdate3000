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
package lupos.operator.physical.partition

import lupos.shared.DictionaryValueHelper
import lupos.shared.DictionaryValueTypeArray
import lupos.shared.EOperatorIDExt
import lupos.shared.ESortPriorityExt
import lupos.shared.IMyInputStream
import lupos.shared.IMyOutputStream
import lupos.shared.IQuery
import lupos.shared.Partition
import lupos.shared.SanityCheck
import lupos.shared.XMLElement
import lupos.shared.operator.IOPBase
import lupos.shared.operator.iterator.IteratorBundle
import lupos.shared.operator.iterator.RowIterator
import kotlin.jvm.JvmField

// http://blog.pronghorn.tech/optimizing-suspending-functions-in-kotlin/
public class POPDistributedReceiveSingle public constructor(
    query: IQuery,
    projectedVariables: List<String>,
    @JvmField public var partitionID: Int,
    child: IOPBase,
    private val input: IMyInputStream,
    private val output: IMyOutputStream? = null,
    private val keys: String,
) : APOPDistributed(
    query,
    projectedVariables,
    EOperatorIDExt.POPDistributedReceiveSingleID,
    "POPDistributedReceiveSingle",
    arrayOf(child),
    ESortPriorityExt.PREVENT_ANY,
) {
    public companion object {
        public operator fun invoke(
            query: IQuery,
            projectedVariables: List<String>,
            partitionID: Int,
            child: IOPBase,
            hosts: Pair<String, String>,
        ): POPDistributedReceiveSingle {
            val handler = query.getInstance().communicationHandler!!
            val conn = handler.openConnection(hosts.second, "/distributed/query/execute", mapOf("key" to hosts.first, "dictionaryURL" to query.getDictionaryUrl()!!), query.getTransactionID().toInt())
            return POPDistributedReceiveSingle(query, projectedVariables, partitionID, child, conn.first, conn.second, hosts.first)
        }
    }
    init {
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_operator_physical/src/commonMain/kotlin/lupos/operator/physical/partition/POPDistributedReceiveSingle.kt:64"/*SOURCE_FILE_END*/ }, { projectedVariables.isNotEmpty() })
    }

    override fun getPartitionCount(variable: String): Int = 1
    override /*suspend*/ fun toXMLElementRoot(partial: Boolean): XMLElement = toXMLElementHelper2(partial, true)
    override /*suspend*/ fun toXMLElement(partial: Boolean): XMLElement = toXMLElementHelper2(partial, false)
    override fun cloneOP(): IOPBase = POPDistributedReceiveSingle(query, projectedVariables, partitionID, children[0].cloneOP(), input, output, keys)
    override fun equals(other: Any?): Boolean = other is POPDistributedReceiveSingle && children[0] == other.children[0]

    private fun toXMLElementHelper2(partial: Boolean, isRoot: Boolean): XMLElement {
        val res = if (partial) {
            XMLElement(classname).addContent(childrenToXML(partial))
        } else {
            super.toXMLElementHelper(partial, partial && !isRoot)
        }
        res.addAttribute("uuid", "$uuid")
        res.addContent(XMLElement("partitionDistributionKey").addAttribute("key", mergeKey(keys, query.getDistributionKey())))
        res.addAttribute("providedVariables", getProvidedVariableNames().toString())
        res.addAttribute("partitionID", "" + partitionID)
        val projectedXML = XMLElement("projectedVariables")
        res.addContent(projectedXML)
        for (variable in projectedVariables) {
            projectedXML.addContent(XMLElement("variable").addAttribute("name", variable))
        }
        return res
    }

    override /*suspend*/ fun evaluate(parent: Partition): IteratorBundle {
        val variables = mutableListOf<String>()
        variables.addAll(projectedVariables)
        val mapping = IntArray(variables.size)
        val iterator = RowIterator()
        iterator.columns = variables.toTypedArray()
        iterator.buf = DictionaryValueTypeArray(variables.size)
        val cnt = input.readInt()
        SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_operator_physical/src/commonMain/kotlin/lupos/operator/physical/partition/POPDistributedReceiveSingle.kt:100"/*SOURCE_FILE_END*/ }, { cnt == variables.size }, { "$cnt vs ${variables.size}" })
        for (i in 0 until variables.size) {
            val len = input.readInt()
            val buf = ByteArray(len)
            input.read(buf, len)
            val name = buf.decodeToString()
            val j = variables.indexOf(name)
            SanityCheck.check({ /*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_operator_physical/src/commonMain/kotlin/lupos/operator/physical/partition/POPDistributedReceiveSingle.kt:107"/*SOURCE_FILE_END*/ }, { j >= 0 && j < variables.size }, { "$j ${variables.size} $variables $name" })
            mapping[i] = j
        }
        var closed = false
        iterator.next = {
            var res = -1
            if (!closed) {
                for (i in 0 until variables.size) {
                    iterator.buf[mapping[i]] = input.readDictionaryValueType()
                }
                if (iterator.buf[0] == DictionaryValueHelper.nullValue) {
                    input.close()
                    output?.close()
                    closed = true
                } else {
                    res = 0
                }
            }
            res
        }
        iterator.close = {
            input.close()
            output?.close()
        }
        return IteratorBundle(iterator)
    }
}
