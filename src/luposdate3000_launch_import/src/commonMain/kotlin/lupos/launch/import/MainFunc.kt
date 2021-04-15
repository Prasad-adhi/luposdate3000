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
package lupos.launch.import

import lupos.fileformat.DictionaryIntermediate
import lupos.fileformat.DictionaryIntermediateReader
import lupos.fileformat.DictionaryIntermediateWriter
import lupos.fileformat.TriplesIntermediate
import lupos.fileformat.TriplesIntermediateReader
import lupos.fileformat.TriplesIntermediateWriter
import lupos.s00misc.ByteArrayWrapper
import lupos.s00misc.File
import lupos.s00misc.Parallel
import lupos.s00misc.SanityCheck
import lupos.s02buildSyntaxTree.nQuads.NQuads2Parser
import lupos.s02buildSyntaxTree.turtle.Turtle2Parser

internal fun helperCleanString(s: String): String {
    var res: String = s
    try {
        while (true) {
            val match = "\\\\u[0-9a-fA-f]{4}".toRegex().find(res) ?: break
            val replacement = match.value.substring(2, 6).toInt(16).toChar() + ""
            res = res.replace(match.value, replacement)
        }
    } catch (e: Throwable) {
        e.printStackTrace()
        println("error during clean :: $s")
    }
    return res
}

private inline fun cmp(tripleBuf: IntArray, order: IntArray, a: Int, b: Int): Int {
    var res = 0
    res = tripleBuf[a + order[0]] - tripleBuf[b + order[0]]
    if (res != 0) {
        return res
    }
    res = tripleBuf[a + order[1]] - tripleBuf[b + order[1]]
    if (res != 0) {
        return res
    }
    res = tripleBuf[a + order[2]] - tripleBuf[b + order[2]]
    return res
}

private inline fun cmp(a: IntArray, b: IntArray): Int {
    var res = 0
    res = a[0] - b[0]
    if (res != 0) {
        return res
    }
    res = a[1] - b[1]
    if (res != 0) {
        return res
    }
    res = a[2] - b[2]
    return res
}

private inline fun swap(tripleBuf: IntArray, a: Int, b: Int) {
    for (i in 0 until 3) {
        val t = tripleBuf[a + i]
        tripleBuf[a + i] = tripleBuf[b + i]
        tripleBuf[b + i] = t
    }
}

private fun quicksort(tripleBuf: IntArray, order: IntArray, l: Int, r: Int) {
    if (l < r) {
        var i = l
        var j = r - 3
        while (i < j) {
            while (i < r && cmp(tripleBuf, order, i, r) < 0) {
                i += 3
            }
            while (j > l && cmp(tripleBuf, order, j, r) >= 0) {
                j -= 3
            }
            if (i < j) {
                swap(tripleBuf, i, j)
            }
        }
        if (cmp(tripleBuf, order, i, r) > 0) {
            swap(tripleBuf, i, r)
        }
        quicksort(tripleBuf, order, l, i - 3)
        quicksort(tripleBuf, order, i + 3, r)
    }
}

@OptIn(ExperimentalStdlibApi::class, kotlin.time.ExperimentalTime::class)
internal fun mainFunc(inputFileName: String): Unit = Parallel.runBlocking {
    val quadMode = inputFileName.endsWith(".n4")
    val statFileEnding = ".stat"
    val tripleFileEnding = if (quadMode) {
        "quads"
    } else {
        "triples"
    }
    val dictSizeLimit = 1024L * 1024L * 1024L
//    val dictSizeLimit = 1024L * 16L
    var dictSizeEstimated = 0L
    var chunc = 0
// create chunced dictionaries
    var outTriples = TriplesIntermediateWriter("$inputFileName.0")
    val dict = mutableMapOf<ByteArrayWrapper, Int>()
    var dictCounter = 0L
    var cnt = 0L
    var dicttotalcnt = 0L
    fun cmp(a: String, b: String): Int {
        val alen = a.length
        val blen = b.length
        val len = if (alen < blen) {
            alen
        } else {
            blen
        }
        for (i in 0 until len) {
            if (a[i] != b[i]) {
                return a[i] - b[i]
            }
        }
        return alen - blen
    }

    val iter = File(inputFileName).openInputStream()
    if (inputFileName.endsWith(".n3") || inputFileName.endsWith(".ttl") || inputFileName.endsWith(".nt")) {
        val row = IntArray(3)
        val x = object : Turtle2Parser(iter) {
            override fun onTriple() {
                for (i in 0 until 3) {
                    val tripleCleaned = triple[i]
                    val v = dict[tripleCleaned]
                    if (v != null) {
                        row[i] = v.toInt()
                    } else {
                        val v2 = dictCounter++
                        row[i] = v2.toInt()
                        val buf = ByteArrayWrapper()
                        tripleCleaned.copyInto(buf)
                        dict[buf] = v2.toInt()
                        dictSizeEstimated += tripleCleaned.getSize() * 2
                        dicttotalcnt++
                    }
                }
                outTriples.write(row[0], row[1], row[2])
                cnt++
                if (cnt % 10000L == 0L) {
                    println("$cnt :: $dictCounter :: $dictSizeEstimated(Bytes)")
                }
                if (dictSizeEstimated > dictSizeLimit) {
                    DictionaryIntermediateWriter("$inputFileName.$chunc").write(dict)
                    dictSizeEstimated = 0
                    chunc++
                }
            }
        }
        x.parse()
    } else if (inputFileName.endsWith(".n4")) {
        val row = IntArray(3)
        val x = object : NQuads2Parser(iter) {
            override fun onQuad() {
                for (i in 0 until 3) {
                    val quadCleaned = quad[i]
                    val v = dict[quadCleaned]
                    if (v != null) {
                        row[i] = v.toInt()
                    } else {
                        val v2 = dictCounter++
                        row[i] = v2.toInt()
                        val buf = ByteArrayWrapper()
                        quadCleaned.copyInto(buf)
                        dict[buf] = v2.toInt()
                        dictSizeEstimated += quadCleaned.getSize() * 2
                        dicttotalcnt++
                    }
                }
                outTriples.write(row[0], row[1], row[2])
                cnt++
                if (cnt % 10000L == 0L) {
                    println("$cnt :: $dictCounter :: $dictSizeEstimated(Bytes)")
                }
                if (dictSizeEstimated > dictSizeLimit) {
                    DictionaryIntermediateWriter("$inputFileName.$chunc").write(dict)
                    dictSizeEstimated = 0
                    chunc++
                }
            }
        }
        x.parse()
    } else {
        throw Exception("unknown filetype $inputFileName")
    }
    DictionaryIntermediateWriter("$inputFileName.$chunc").write(dict)
    chunc++
    outTriples.close()
    iter.close()
// merge dictionaries
    val outDictionary = DictionaryIntermediateWriter(inputFileName)
    val mapping = IntArray(dictCounter.toInt())

    val dictionaries = Array(chunc) { DictionaryIntermediateReader("$inputFileName.$it") }
    val dictionariesHeadBuffer = Array(chunc) { ByteArrayWrapper() }
    val dictionariesHead = Array(chunc) { dictionaries[it].next(dictionariesHeadBuffer[it]) }

    var buffer = ByteArrayWrapper()
    var current: ByteArrayWrapper? = null
    var currentValue = 0

    var changed = true
    loop@ while (changed) {
        changed = false
        for (i in 0 until chunc) {
            val d = dictionariesHead[i]
            if (d != null) {
                if (current == null || d.data <= current) {
                    d.data.copyInto(buffer)
                    current = buffer
                }
            }
        }
        if (current != null) {
            changed = true
            outDictionary.writeAssumeOrdered(currentValue, current)
            for (i in 0 until chunc) {
                val d = dictionariesHead[i]
                if (d != null) {
                    if (current == d.data) {
                        mapping[d.id] = currentValue
                        dictionariesHead[i] = dictionaries[i].next(dictionariesHeadBuffer[i])
                    }
                }
            }
            currentValue++
            current = null
        }
    }
    for (d in dictionaries) {
        d.close()
    }
    outDictionary.close()
    for (i in 0 until chunc) {
        DictionaryIntermediate.delete("$inputFileName.$i")
    }
    val inTriples = TriplesIntermediateReader("$inputFileName.0")
    val tripleBuf = IntArray(268435456)
    val limit = (tripleBuf.size / 3) * 3
    var offset = 0
    var tripleBlock = 0
    val orders = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(0, 2, 1),
        intArrayOf(1, 0, 2),
        intArrayOf(1, 2, 0),
        intArrayOf(2, 0, 1),
        intArrayOf(2, 1, 0),
    )
    val orderNames = arrayOf("spo", "sop", "pso", "pos", "osp", "ops")
    fun sortBlockMain() {
        for (o in 0 until 6) {
            val order = orders[o]
            quicksort(tripleBuf, order, 0, offset)
            val outTriples = TriplesIntermediateWriter("$inputFileName.${orderNames[o]}.$tripleBlock")
            var i = 0
            while (i < offset) {
                outTriples.write(tripleBuf[i + order[0]], tripleBuf[i + order[1]], tripleBuf[i + order[2]])
                i += 3
            }
            outTriples.close()
        }
        tripleBlock++
        offset = 0
    }
    inTriples.readAll { it ->
        tripleBuf[offset++] = mapping[it[0]]
        tripleBuf[offset++] = mapping[it[1]]
        tripleBuf[offset++] = mapping[it[2]]
        if (offset >= limit) {
            sortBlockMain()
        }
    }
    sortBlockMain()
    inTriples.close()
    TriplesIntermediate.delete("$inputFileName.0")
    for (o in 0 until 6) {
        val outTriples = TriplesIntermediateWriter("$inputFileName.${orderNames[o]}")
        val tripleInputs = Array(tripleBlock) { TriplesIntermediateReader("$inputFileName.${orderNames[o]}.$it") }
        val tripleInputHeads = Array(tripleBlock) { tripleInputs[it].next() }
        val smallest = IntArray(3)
        var valid = false
        for (i in 0 until tripleBlock) {
            val head = tripleInputHeads[i]
            if (head != null) {
                if (!valid || cmp(head, smallest) < 0) {
                    smallest[0] = head[0]
                    smallest[1] = head[1]
                    smallest[2] = head[2]
                    valid = true
                }
            }
        }
        if (valid) {
            valid = false
            outTriples.write(smallest[0], smallest[1], smallest[2])
            for (i in 0 until tripleBlock) {
                val head = tripleInputHeads[i]
                if (head != null) {
                    if (cmp(head, smallest) == 0) {
                        tripleInputHeads[i] = tripleInputs[i].next()
                    }
                }
            }
        }
        outTriples.close()
        for (i in 0 until tripleBlock) {
            TriplesIntermediate.delete("$inputFileName.${orderNames[o]}.$i")
        }
    }
    File("$inputFileName.$statFileEnding").withOutputStream {
        it.println("triples=$cnt")
        it.println("dictionary-entries=$currentValue")
    }
    if (false) {
// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        val outputTriplesFile = File("$inputFileName.$tripleFileEnding")
        val outputPartitionsFile = File("$inputFileName.partitions")
        println("partition-stats :: ")
        val lowerBoundToAnalyse = 256L
        val partitionSizes = intArrayOf(2, 4, 8, 16)
        val tripleBuf = LongArray(3)
        val counters = Array(3) { LongArray(currentValue.toInt()) }
        val maxCounter = LongArray(3)
        outputTriplesFile.withInputStream { fis ->
            for (c in 0 until cnt) {
                for (i in 0 until 3) {
                    val tmp = fis.readInt()
                    counters[i][tmp]++
                    if (counters[i][tmp] > maxCounter[i]) {
                        maxCounter[i] = counters[i][tmp]
                    }
                }
            }
        }
        val estimatedPartitionSizes = arrayOf(mutableMapOf<Int, Array<LongArray>>(), mutableMapOf<Int, Array<LongArray>>(), mutableMapOf<Int, Array<LongArray>>(), mutableMapOf<Int, Array<LongArray>>(), mutableMapOf<Int, Array<LongArray>>(), mutableMapOf<Int, Array<LongArray>>())
        val minimumOccurences = LongArray(3) {
            val tmp = maxCounter[it] / 2L
            if (lowerBoundToAnalyse > tmp) {
                lowerBoundToAnalyse
            } else {
                tmp
            }
        }
        outputTriplesFile.withInputStream { fis ->
            for (c in 0 until cnt) {
                for (i in 0 until 3) {
                    tripleBuf[i] = fis.readInt().toLong()
                }
                for (i in 0 until 3) {
                    val constantPart = tripleBuf[i]
                    if (counters[i][constantPart.toInt()] > minimumOccurences[i]) {
                        for (j2 in 0 until 2) {
                            val j = (i + j2 + 1) % 3
                            val partitionPart = tripleBuf[j]
                            val x: MutableMap<Int, Array<LongArray>> = estimatedPartitionSizes[i + j2 * 3]
                            var y: Array<LongArray>? = x[constantPart]
                            if (y == null) {
                                y = Array(partitionSizes.size) { LongArray(partitionSizes[it]) }
                                x[constantPart.toInt()] = y
                            }
                            for (k in partitionSizes.indices) {
                                y[k.toInt()][partitionPart.toInt() % partitionSizes[k.toInt()]]++
                            }
                        }
                    }
                }
            }
        }
        val configurations1 = mutableMapOf<String, MutableSet<Int>>()
        val configurations2 = mutableMapOf<String, MutableSet<Int>>()
        for (i in 0 until 3) {
            for (j2 in 0 until 2) {
                val x = estimatedPartitionSizes[i + j2 * 3]
                var lastMax = -1L
                var maxPartition = partitionSizes[0]
                for (ki in partitionSizes.indices) {
                    val k = partitionSizes[ki]
                    var min = -1L
                    var max = 0L
                    for (xv in x.values) {
                        for (xx in xv[ki]) {
                            if (xx > max) {
                                max = xx
                            }
                            if (xx < min || min == -1L) {
                                min = xx
                            }
                        }
                    }
                    if (max < lowerBoundToAnalyse) {
                        break
                    } else if (lastMax == -1L) {
                        lastMax = max
                    } else if (max > lastMax * 0.55) {
                        break
                    }
                    maxPartition = k
                }
                val idxName: String
                val idxNameSecondary: String
                when (i + j2 * 3) {
                    0 -> {
                        idxName = "SPO"
                        idxNameSecondary = "SOP"
                    }
                    1 -> {
                        idxName = "POS"
                        idxNameSecondary = "PSO"
                    }
                    2 -> {
                        idxName = "OSP"
                        idxNameSecondary = "OPS"
                    }
                    3 -> {
                        idxName = "SOP"
                        idxNameSecondary = "SPO"
                    }
                    4 -> {
                        idxName = "PSO"
                        idxNameSecondary = "POS"
                    }
                    5 -> {
                        idxName = "OPS"
                        idxNameSecondary = "OSP"
                    }
                    else -> SanityCheck.checkUnreachable()
                }
                if (maxPartition > 1) {
                    if (configurations1[idxName] == null) {
                        configurations1[idxName] = mutableSetOf(maxPartition)
                    } else {
                        configurations1[idxName]!!.add(maxPartition)
                    }
                    if (configurations2[idxNameSecondary] == null) {
                        configurations2[idxNameSecondary] = mutableSetOf(maxPartition)
                    } else {
                        configurations2[idxNameSecondary]!!.add(maxPartition)
                    }
                }
            }
        }
        val indicees = arrayOf("SPO", "SOP", "PSO", "POS", "OSP", "OPS")
        outputPartitionsFile.withOutputStream { out ->
            for (i in indicees) {
                val t1 = configurations1[i]
                val t2 = configurations2[i]
                if (t1 == null && t2 == null) {
                    out.println("$i,-1,1")
// add smalles possible partitions to the other collation orders due to currently incomplete optimizer
                    out.println("$i,1,${partitionSizes[0]}")
                    out.println("$i,2,${partitionSizes[0]}")
                } else {
                    if (t1 == null) {
                        out.println("$i,1,${partitionSizes[0]}")
                    } else {
                        for (j in t1) {
                            out.println("$i,1,$j")
                        }
                    }
                    if (t2 == null) {
                        out.println("$i,2,${partitionSizes[0]}")
                    } else {
                        for (j in t2) {
                            out.println("$i,2,$j")
                        }
                    }
                }
            }
        }
    }
}
