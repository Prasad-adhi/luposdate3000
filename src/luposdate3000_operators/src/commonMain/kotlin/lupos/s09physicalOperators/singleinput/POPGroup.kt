package lupos.s09physicalOperators.singleinput
import lupos.s00misc.EOperatorID
import lupos.s00misc.ESortPriority
import lupos.s00misc.GroupByColumnMissing
import lupos.s00misc.GroupByDuplicateColumnException
import lupos.s00misc.MyBigInteger
import lupos.s00misc.Partition
import lupos.s00misc.SanityCheck
import lupos.s00misc.SortHelper
import lupos.s00misc.VariableNotDefinedSyntaxException
import lupos.s00misc.XMLElement
import lupos.s03resultRepresentation.ResultSetDictionaryExt
import lupos.s03resultRepresentation.ValueInteger
import lupos.s04arithmetikOperators.AOPAggregationBase
import lupos.s04arithmetikOperators.AOPBase
import lupos.s04arithmetikOperators.noinput.AOPVariable
import lupos.s04arithmetikOperators.singleinput.AOPAggregationCOUNT
import lupos.s04logicalOperators.IOPBase
import lupos.s04logicalOperators.IQuery
import lupos.s04logicalOperators.iterator.ColumnIterator
import lupos.s04logicalOperators.iterator.ColumnIteratorAggregate
import lupos.s04logicalOperators.iterator.ColumnIteratorMultiValue
import lupos.s04logicalOperators.iterator.ColumnIteratorQueue
import lupos.s04logicalOperators.iterator.ColumnIteratorQueueEmpty
import lupos.s04logicalOperators.iterator.ColumnIteratorQueueExt
import lupos.s04logicalOperators.iterator.ColumnIteratorRepeatValue
import lupos.s04logicalOperators.iterator.IteratorBundle
import lupos.s04logicalOperators.noinput.OPEmptyRow
import lupos.s09physicalOperators.POPBase
import kotlin.jvm.JvmField
// TODO refactor such that the optimizer may choose which strategy to use
class POPGroup : POPBase {
    override fun getPossibleSortPriorities(): List<List<SortHelper>> {
        /*possibilities for_ next operator*/
        val res = mutableListOf<List<SortHelper>>()
        val provided = by.map { it.name }
        for (x in children[0].getPossibleSortPriorities()) {
            val tmp = mutableListOf<SortHelper>()
            for (v in x) {
                if (provided.contains(v.variableName)) {
                    tmp.add(v)
                } else {
                    break
                }
            }
            addToPrefixFreeList(tmp, res)
        }
        return res
    }
    override fun getPartitionCount(variable: String): Int {
        SanityCheck.check { children[0].getPartitionCount(variable) == 1 }
        return 1
    }
    @JvmField
    var by: List<AOPVariable>
    @JvmField
    var bindings: MutableList<Pair<String, AOPBase>> = mutableListOf()
    override fun toSparql(): String {
        var res = children[0].toSparql()
        res += " GROUP BY "
        for (b in by) {
            res += b.toSparql() + " "
        }
        for ((k, v) in bindings) {
            res += "(" + v.toSparql() + " AS " + AOPVariable(query, k).toSparql() + ")"
        }
        return res
    }
    override fun cloneOP(): POPGroup {
        return if (bindings.size > 0) {
            var tmpBindings = POPBind(query, listOf(), AOPVariable(query, bindings[0].first), bindings[0].second, OPEmptyRow(query))
            for (bp in 1 until bindings.size) {
                tmpBindings = POPBind(query, listOf(), AOPVariable(query, bindings[bp].first), bindings[bp].second, tmpBindings)
            }
            POPGroup(query, projectedVariables, by, tmpBindings, children[0].cloneOP())
        } else {
            POPGroup(query, projectedVariables, by, null, children[0].cloneOP())
        }
    }
    constructor(query: IQuery, projectedVariables: List<String>, by: List<AOPVariable>, bindings: POPBind?, child: IOPBase) : super(query, projectedVariables, EOperatorID.POPGroupID, "POPGroup", arrayOf(child), ESortPriority.GROUP) {
        this.by = by
        var tmpBind: IOPBase? = bindings
        while (tmpBind != null && tmpBind is POPBind) {
            this.bindings.add(Pair(tmpBind.name.name, tmpBind.children[1] as AOPBase))
            tmpBind = tmpBind.children[0]
        }
        this.bindings = this.bindings.asReversed()
    }
    constructor(query: IQuery, projectedVariables: List<String>, by: List<AOPVariable>, bindings: List<Pair<String, AOPBase>>, child: IOPBase) : super(query, projectedVariables, EOperatorID.POPGroupID, "POPGroup", arrayOf(child), ESortPriority.GROUP) {
        this.by = by
        this.bindings = bindings.toMutableList()
    }
    override fun equals(other: Any?): Boolean = other is POPGroup && by == other.by && children[0] == other.children[0] && bindings == other.bindings
    override fun getProvidedVariableNamesInternal(): List<String> = (MutableList(by.size) { by[it].name } + MutableList(bindings.size) { bindings[it].first }).distinct()
    override fun getRequiredVariableNames(): List<String> {
        val res = MutableList(by.size) { by[it].name }
        for (b in bindings) {
            res.addAll(b.second.getRequiredVariableNamesRecoursive())
        }
        return res.distinct()
    }
    override fun syntaxVerifyAllVariableExists(additionalProvided: List<String>, autocorrect: Boolean) {
        children[0].syntaxVerifyAllVariableExists(additionalProvided, autocorrect)
        SanityCheck.check { additionalProvided.isEmpty() }
        val localProvide = additionalProvided + children[0].getProvidedVariableNames()
        val localRequire = mutableListOf<String>()
        for (v in by) {
            localRequire.add(v.name)
        }
        for (b in bindings) {
            localRequire += b.second.getRequiredVariableNamesRecoursive()
        }
        if (!localProvide.containsAll(localRequire)) {
            if (autocorrect) {
                for (name in localRequire) {
                    var found = false
                    for (prov in localProvide) {
                        if (prov == name) {
                            found = true
                            break
                        }
                    }
                    if (!found) {
                        for (b in by) {
                            if (b.name == name) {
                                throw GroupByColumnMissing(name)
                            }
                        }
                        for (b in bindings.indices) {
                            bindings[b] = Pair(bindings[b].first, replaceVariableWithUndef(bindings[b].second, name, true) as AOPBase)
                        }
                    }
                }
            } else {
                val tmp = localRequire.toMutableSet()
                tmp.removeAll(localProvide)
                if (tmp.size == 1) {
                    throw VariableNotDefinedSyntaxException(classname, tmp.first())
                } else {
                    throw VariableNotDefinedSyntaxException(classname, tmp.toString())
                }
            }
        }
    }
    private fun getAggregations(node: IOPBase): MutableList<AOPAggregationBase> {
        val res = mutableListOf<AOPAggregationBase>()
        for (n in node.getChildren()) {
            res.addAll(getAggregations(n))
        }
        if (node is AOPAggregationBase) {
            res.add(node)
        }
        return res
    }
    internal class MapKey(@JvmField val data: IntArray) {
        override fun hashCode(): Int {
            var res = 0
            for (element in data) {
                res += element.hashCode()
            }
            return res
        }
        override fun equals(other: Any?) = other is MapKey && data.contentEquals(other.data)
    }
    internal class MapRow(val iterators: IteratorBundle, val aggregates: Array<ColumnIteratorAggregate>, val columns: Array<ColumnIteratorQueue>)
    override /*suspend*/ fun evaluate(parent: Partition): IteratorBundle {
        val localVariables = children[0].getProvidedVariableNames()
        val outMap = mutableMapOf<String, ColumnIterator>()
        val child = children[0].evaluate(parent)
        val aggregations = mutableListOf<AOPAggregationBase>()
        for (b in bindings) {
            aggregations.addAll(getAggregations(b.second))
        }
        val keyColumnNames = Array(by.size) { by[it].name }
        if (keyColumnNames.size != keyColumnNames.distinct().size) {
            throw GroupByDuplicateColumnException()
        }
        val keyColumns: Array<ColumnIterator> = Array(keyColumnNames.size) { child.columns[keyColumnNames[it]]!! }
        val valueColumnNames = mutableListOf<String>()
        for (name in localVariables) {
            if (!keyColumnNames.contains(name)) {
                valueColumnNames.add(name)
            }
        }
        val valueColumns = Array(valueColumnNames.size) { child.columns[valueColumnNames[it]]!! }
        println("evaluate POPGroup ${valueColumnNames.map { it }} ${keyColumnNames.map { it }}")
        if (keyColumnNames.isEmpty()) {
            println("case 'keyColumnNames.isEmpty()'")
            SanityCheck.println { "group mode a" }
            val localMap = mutableMapOf<String, ColumnIterator>()
            val localColumns = Array<ColumnIteratorQueue>(valueColumnNames.size) { ColumnIteratorQueueEmpty() }
            for (columnIndex in 0 until valueColumnNames.size) {
                localMap[valueColumnNames[columnIndex]] = localColumns[columnIndex]
            }
            val row = IteratorBundle(localMap)
            val localAggregations = Array(aggregations.size) {
                val tmp = aggregations[it].createIterator(row)
                localMap["#" + aggregations[it].uuid] = tmp
                tmp
            }
            val localRow = MapRow(row, localAggregations, localColumns)
            if (valueColumnNames.size == 0) {
                for (i in 0 until child.count()) {
                    for (aggregate in localRow.aggregates) {
                        aggregate.evaluate()
                    }
                }
            } else {
                loop2@ while (true) {
                    for (columnIndex in 0 until valueColumnNames.size) {
                        val value = valueColumns[columnIndex].next()
                        if (value == ResultSetDictionaryExt.nullValue) {
                            SanityCheck.check { columnIndex == 0 }
                            for (closeIndex in 0 until valueColumnNames.size) {
                                valueColumns[closeIndex].close()
                            }
                            break@loop2
                        }
                        localRow.columns[columnIndex].tmp = value
                    }
                    for (aggregate in localRow.aggregates) {
                        aggregate.evaluate()
                    }
                }
            }
            for (columnIndex in 0 until bindings.size) {
                val value = query.getDictionary().createValue(bindings[columnIndex].second.evaluate(localRow.iterators)())
                if (projectedVariables.contains(bindings[columnIndex].first)) {
                    outMap[bindings[columnIndex].first] = ColumnIteratorRepeatValue(1, value)
                }
            }
        } else {
            val tmpSortPriority = children[0].getMySortPriority().map { it.variableName }
            var canUseSortedInput = true
            if ((!localVariables.containsAll(keyColumnNames.toMutableList())) || (tmpSortPriority.size < keyColumnNames.size)) {
                println("not sorted because a ${!localVariables.containsAll(keyColumnNames.toMutableList())} ${tmpSortPriority.size < keyColumnNames.size}")
                canUseSortedInput = false
            } else {
                for (element in keyColumnNames) {
                    if (!tmpSortPriority.contains(element)) {
                        println("not sorted because b")
                        canUseSortedInput = false
                        break
                    }
                }
            }
            if (canUseSortedInput) {
                println("case 'canUseSortedInput'")
                SanityCheck.println { "group mode b" }
                var currentKey = IntArray(keyColumnNames.size) { ResultSetDictionaryExt.undefValue }
                var nextKey: IntArray? = null
                // first row ->
                var emptyResult = false
                for (columnIndex in keyColumnNames.indices) {
                    val value = keyColumns[columnIndex].next()
                    if (value == ResultSetDictionaryExt.nullValue) {
                        for (element in keyColumns) {
                            element.close()
                        }
                        for (element in valueColumns) {
                            element.close()
                        }
                        SanityCheck.check { columnIndex == 0 }
                        emptyResult = true
                        break
                    }
                    currentKey[columnIndex] = value
                }
                if (emptyResult) {
                    // there is no first row
                    for (v in keyColumnNames) {
                        if (projectedVariables.contains(v)) {
                            outMap[v] = ColumnIteratorRepeatValue(1, ResultSetDictionaryExt.undefValue)
                        }
                    }
                    for ((first) in bindings) {
                        if (projectedVariables.contains(first)) {
                            outMap[first] = ColumnIteratorRepeatValue(1, ResultSetDictionaryExt.undefValue)
                        }
                    }
                } else {
                    val localMap = mutableMapOf<String, ColumnIterator>()
                    var localRowColumns = Array(valueColumnNames.size) { ColumnIteratorQueueEmpty() }
                    for (columnIndex in keyColumnNames.indices) {
                        val tmp = ColumnIteratorQueueEmpty()
                        tmp.tmp = currentKey[columnIndex]
                        localMap[keyColumnNames[columnIndex]] = tmp
                    }
                    for (columnIndex in 0 until valueColumnNames.size) {
                        localMap[valueColumnNames[columnIndex]] = localRowColumns[columnIndex]
                    }
                    var localRowIterators = IteratorBundle(localMap)
                    var localRowAggregates = Array(aggregations.size) {
                        val tmp = aggregations[it].createIterator(localRowIterators)
                        localMap["#" + aggregations[it].uuid] = tmp
                        tmp
                    }
                    for (columnIndex in 0 until valueColumnNames.size) {
                        localRowColumns[columnIndex].tmp = valueColumns[columnIndex].next()
                    }
                    for (aggregate in localRowAggregates) {
                        aggregate.evaluate()
                    }
                    // first row <-
                    val output = mutableListOf<ColumnIteratorQueue>()
                    for (outIndex in 0 until keyColumnNames.size + bindings.size) {
                        val iterator = object : ColumnIteratorQueue() {
                            override /*suspend*/ fun close() {
                                __close()
                            }
                            /*suspend*/ inline fun __close() {
                                if (label != 0) {
                                    ColumnIteratorQueueExt._close(this)
                                    for (element in keyColumns) {
                                        element.close()
                                    }
                                    for (element in valueColumns) {
                                        element.close()
                                    }
                                }
                            }
                            override /*suspend*/ fun next(): Int {
                                return ColumnIteratorQueueExt.nextHelper(
                                    this,
                                    {
                                        loop@ while (true) {
                                            var changedKey = false
                                            if (nextKey != null) {
                                                currentKey = nextKey!!
                                                nextKey = null
                                            }
                                            for (columnIndex in keyColumnNames.indices) {
                                                val value = keyColumns[columnIndex].next()
                                                if (value == ResultSetDictionaryExt.nullValue) {
                                                    for (element in keyColumns) {
                                                        element.close()
                                                    }
                                                    for (element in valueColumns) {
                                                        element.close()
                                                    }
                                                    SanityCheck.check { columnIndex == 0 }
                                                    for (columnIndex2 in keyColumnNames.indices) {
                                                        if (projectedVariables.contains(keyColumnNames[columnIndex2])) {
                                                            output[columnIndex2].queue.add(currentKey[columnIndex2])
                                                        }
                                                    }
                                                    for (columnIndex2 in 0 until bindings.size) {
                                                        if (projectedVariables.contains(bindings[columnIndex2].first)) {
                                                            output[columnIndex2 + keyColumnNames.size].queue.add(query.getDictionary().createValue(bindings[columnIndex2].second.evaluate(localRowIterators)()))
                                                        }
                                                    }
                                                    for (outIndex2 in 0 until output.size) {
                                                        ColumnIteratorQueueExt.closeOnEmptyQueue(output[outIndex2])
                                                    }
                                                    break@loop
                                                }
                                                if (nextKey != null) {
                                                    nextKey!![columnIndex] = value
                                                } else if (currentKey[columnIndex] != value) {
                                                    nextKey = IntArray(keyColumnNames.size) { currentKey[it] }
                                                    nextKey!![columnIndex] = value
                                                    changedKey = true
                                                }
                                            }
                                            if (changedKey) {
                                                for (columnIndex in keyColumnNames.indices) {
                                                    if (projectedVariables.contains(keyColumnNames[columnIndex])) {
                                                        output[columnIndex].queue.add(currentKey[columnIndex])
                                                    }
                                                }
                                                for (columnIndex in 0 until bindings.size) {
                                                    if (projectedVariables.contains(bindings[columnIndex].first)) {
                                                        output[columnIndex + keyColumnNames.size].queue.add(query.getDictionary().createValue(bindings[columnIndex].second.evaluate(localRowIterators)()))
                                                    }
                                                }
                                                localMap.clear()
                                                localRowColumns = Array(valueColumnNames.size) { ColumnIteratorQueueEmpty() }
                                                for (columnIndex in keyColumnNames.indices) {
                                                    val tmp = ColumnIteratorQueueEmpty()
                                                    tmp.tmp = currentKey[columnIndex]
                                                    localMap[keyColumnNames[columnIndex]] = tmp
                                                }
                                                for (columnIndex in 0 until valueColumnNames.size) {
                                                    localMap[valueColumnNames[columnIndex]] = localRowColumns[columnIndex]
                                                }
                                                localRowIterators = IteratorBundle(localMap)
                                                localRowAggregates = Array(aggregations.size) {
                                                    val tmp = aggregations[it].createIterator(localRowIterators)
                                                    localMap["#" + aggregations[it].uuid] = tmp
                                                    tmp
                                                }
                                            }
                                            for (columnIndex in 0 until valueColumnNames.size) {
                                                localRowColumns[columnIndex].tmp = valueColumns[columnIndex].next()
                                            }
                                            for (aggregate in localRowAggregates) {
                                                aggregate.evaluate()
                                            }
                                            if (changedKey) {
                                                break@loop
                                            }
                                        }
                                    },
                                    { __close() }
                                )
                            }
                        }
                        output.add(iterator)
                    }
                    for (columnIndex in keyColumnNames.indices) {
                        if (projectedVariables.contains(keyColumnNames[columnIndex])) {
                            outMap[keyColumnNames[columnIndex]] = output[columnIndex]
                        }
                    }
                    for (columnIndex in 0 until bindings.size) {
                        if (projectedVariables.contains(bindings[columnIndex].first)) {
                            outMap[bindings[columnIndex].first] = output[columnIndex + keyColumnNames.size]
                        }
                    }
                }
            } else {
                if (bindings.size == 1 && bindings.toList().first().second is AOPAggregationCOUNT &&
// simplicity ->
                    keyColumnNames.size == 1 && valueColumnNames.size == 0
// <- simplicity
                ) {
                    println("case 'count only' ${valueColumnNames.map { it }} ${keyColumnNames.map { it }}")
                    var iterator = keyColumns[0]
                    var map = mutableMapOf<Int, Int>()
                    while (true) {
                        var value = iterator.next()
                        if (value == ResultSetDictionaryExt.nullValue) {
                            iterator.close()
                            break
                        }
                        val v = map[value]
                        if (v == null) {
                            map[value] = 1
                        } else {
                            map[value] = v + 1
                        }
                    }
                    val arrK = IntArray(map.size)
                    val arrV = IntArray(map.size)
                    var i = 0
                    val dict = query.getDictionary()
                    for ((k, v) in map) {
                        arrK[i] = k
                        arrV[i] = dict.createValue(ValueInteger(MyBigInteger(v)))
                        i++
                    }
                    outMap[keyColumnNames[0]] = ColumnIteratorMultiValue(arrK, arrK.size)
                    outMap[bindings.toList().first().first] = ColumnIteratorMultiValue(arrV, arrV.size)
                } else {
                    println("case 'generic'")
                    SanityCheck.println { "group mode c" }
                    val map = mutableMapOf<MapKey, MapRow>()
                    loop@ while (true) {
                        val currentKey = IntArray(keyColumnNames.size) { ResultSetDictionaryExt.undefValue }
                        for (columnIndex in keyColumnNames.indices) {
                            val value = keyColumns[columnIndex].next()
                            if (value == ResultSetDictionaryExt.nullValue) {
                                for (element in keyColumns) {
                                    element.close()
                                }
                                for (element in valueColumns) {
                                    element.close()
                                }
                                SanityCheck.check { columnIndex == 0 }
                                break@loop
                            }
                            currentKey[columnIndex] = value
                        }
                        val key = MapKey(currentKey)
                        var localRow = map[key]
                        if (localRow == null) {
                            val localMap = mutableMapOf<String, ColumnIterator>()
                            val localColumns = Array<ColumnIteratorQueue>(valueColumnNames.size) { ColumnIteratorQueueEmpty() }
                            for (columnIndex in keyColumnNames.indices) {
                                val tmp = ColumnIteratorQueueEmpty()
                                tmp.tmp = currentKey[columnIndex]
                                localMap[keyColumnNames[columnIndex]] = tmp
                            }
                            for (columnIndex in 0 until valueColumnNames.size) {
                                localMap[valueColumnNames[columnIndex]] = localColumns[columnIndex]
                            }
                            val row = IteratorBundle(localMap)
                            val localAggregations = Array(aggregations.size) {
                                val tmp = aggregations[it].createIterator(row)
                                localMap["#" + aggregations[it].uuid] = tmp
                                tmp
                            }
                            localRow = MapRow(row, localAggregations, localColumns)
                            map[key] = localRow
                        }
                        for (columnIndex in 0 until valueColumnNames.size) {
                            localRow.columns[columnIndex].tmp = valueColumns[columnIndex].next()
                        }
                        for (aggregate in localRow.aggregates) {
                            aggregate.evaluate()
                        }
                    }
                    if (map.isEmpty()) {
                        for (v in keyColumnNames) {
                            outMap[v] = ColumnIteratorRepeatValue(1, ResultSetDictionaryExt.undefValue)
                        }
                        for ((first) in bindings) {
                            outMap[first] = ColumnIteratorRepeatValue(1, ResultSetDictionaryExt.undefValue)
                        }
                    } else {
                        val outKeys = Array(keyColumnNames.size) { mutableListOf<Int>() }
                        val outValues = Array(bindings.size) { mutableListOf<Int>() }
                        for ((k, v) in map) {
                            for (columnIndex in keyColumnNames.indices) {
                                outKeys[columnIndex].add(k.data[columnIndex])
                            }
                            for (columnIndex in 0 until bindings.size) {
                                outValues[columnIndex].add(query.getDictionary().createValue(bindings[columnIndex].second.evaluate(v.iterators)()))
                            }
                        }
                        for (columnIndex in keyColumnNames.indices) {
                            outMap[keyColumnNames[columnIndex]] = ColumnIteratorMultiValue(outKeys[columnIndex])
                        }
                        for (columnIndex in 0 until bindings.size) {
                            outMap[bindings[columnIndex].first] = ColumnIteratorMultiValue(outValues[columnIndex])
                        }
                    }
                }
            }
        }
        return IteratorBundle(outMap)
    }
    override /*suspend*/ fun toXMLElement(): XMLElement {
        val res = super.toXMLElement()
        val byxml = XMLElement("by")
        res.addContent(byxml)
        for (b in by) {
            byxml.addContent(XMLElement("variable").addAttribute("name", b.name))
        }
        val xmlbindings = XMLElement("bindings")
        res.addContent(xmlbindings)
        for ((first, second) in bindings) {
            xmlbindings.addContent(XMLElement("binding").addAttribute("name", first).addContent(second.toXMLElement()))
        }
        return res
    }
}
