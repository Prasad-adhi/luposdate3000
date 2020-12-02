import lupos.s00misc.*
import lupos.s03resultRepresentation.*
import lupos.s04arithmetikOperators.*
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04logicalOperators.*
import lupos.s05tripleStore.*
import lupos.s09physicalOperators.*
import lupos.s09physicalOperators.multiinput.*
import lupos.s09physicalOperators.partition.*
import lupos.s15tripleStoreDistributed.*
import lupos.s16network.*

@OptIn(ExperimentalStdlibApi::class, kotlin.time.ExperimentalTime::class)
fun mainFunc(args: Array<String>): Unit = Parallel.runBlocking {
    LuposdateEndpoint.initialize()
    val partitionOptions = listOf(1, 2, 4, 8, 16)
    val enableDisable = listOf(0, 1)
    val datasourceFiles = args[0]
    val minimumTime = args[1].toDouble()
    val numberOfTriples = args[2].toLong()
    val timer = DateHelperRelative.markNow()
    LuposdateEndpoint.importIntermediateFiles(datasourceFiles)
    val time = DateHelperRelative.elapsedSeconds(timer)
    println("$datasourceFiles/persistence-import.sparql,$numberOfTriples,0,1,${numberOfTriples * 1000.0},${1.0 / time}")
    for (x in partitionOptions) {
        for (y in partitionOptions) {
            for (z in partitionOptions) {
                for (a in partitionOptions) {
                    for (b in partitionOptions) {
                        for (zPt in enableDisable) {
                            if (a != x && a != y) {
                                continue
                            }
                            if (b != a && b != z && b != 1) {
                                continue
                            }
                            if (zPt == 1 && b == 1) {
                                continue
                            }
                            if (zPt == 1 && z != 1) {
                                continue
                            }
                            if (zPt == 1 && a != b) {
                                continue
                            }
                            val query = Query()
                            var partitionID = 1
                            val xP = Partition()
                            var aPartitionID = partitionID++
                            var bPartitionID = partitionID++
                            var xPartitionID = if (x != a) partitionID++ else aPartitionID
                            xP.limit["a"] = x
                            var opX: IOPBase = TripleStoreIteratorGlobal(query, listOf("a"), "", arrayOf(AOPVariable(query, "a"), AOPConstant(query, ValueIri("bornIn")), AOPConstant(query, ValueIri("2009"))), EIndexPattern.POS, xP)
                            if (x > 1) {
                                opX = POPSplitPartitionFromStore(query, listOf("a"), "a", x, xPartitionID, opX)
                            }
                            val yP = Partition()
                            var yPartitionID = if (y != a) partitionID++ else aPartitionID
                            yP.limit["a"] = y
                            var opY: IOPBase = TripleStoreIteratorGlobal(query, listOf("a", "b"), "", arrayOf(AOPVariable(query, "b"), AOPConstant(query, ValueIri("writtenBy")), AOPVariable(query, "a")), EIndexPattern.PSO, yP)
                            if (y > 1) {
                                opY = POPSplitPartitionFromStore(query, listOf("a", "b"), "b", y, yPartitionID, opY)
                            }
                            val zP = Partition()
                            var zPartitionID = if (z != b) partitionID++ else bPartitionID
                            zP.limit["b"] = z
                            var opZ: IOPBase = TripleStoreIteratorGlobal(query, listOf("b", "t"), "", arrayOf(AOPVariable(query, "b"), AOPConstant(query, ValueIri("title")), AOPVariable(query, "t")), EIndexPattern.PSO, zP)
                            if (zPt == 1) {
                                opZ = POPSplitPartitionPassThrough(query, listOf("b", "t"), "a", z, zPartitionID, opZ)
                            } else {
                                if (z > 1) {
                                    opZ = POPSplitPartitionFromStore(query, listOf("b", "t"), "b", z, zPartitionID, opZ)
                                }
                            }
                            if (x != a) {
                                if (x > 1) {
                                    if (a > 1) {
                                        opX = POPMergePartitionOrderedByIntId(query, listOf("a"), "a", x, xPartitionID, opX)
                                        opX.mySortPriority = mutableListOf(SortHelper("a", ESortType.FAST))
                                        opX = POPSplitPartition(query, listOf("a"), "a", a, aPartitionID, opX)
                                    } else {
                                        opX = POPMergePartitionOrderedByIntId(query, listOf("a"), "a", x, xPartitionID, opX)
                                        opX.mySortPriority = mutableListOf(SortHelper("a", ESortType.FAST))
                                    }
                                } else {
                                    if (a > 1) {
                                        opX = POPSplitPartition(query, listOf("a"), "a", a, aPartitionID, opX)
                                    }
                                }
                            }
                            if (y != a) {
                                if (y > 1) {
                                    if (a > 1) {
                                        opY = POPMergePartitionOrderedByIntId(query, listOf("a", "b"), "a", y, yPartitionID, opY)
                                        opY.mySortPriority = mutableListOf(SortHelper("a", ESortType.FAST))
                                        opY = POPSplitPartition(query, listOf("a", "b"), "a", a, aPartitionID, opY)
                                    } else {
                                        opY = POPMergePartitionOrderedByIntId(query, listOf("a", "b"), "a", y, yPartitionID, opY)
                                        opY.mySortPriority = mutableListOf(SortHelper("a", ESortType.FAST))
                                    }
                                } else {
                                    if (a > 1) {
                                        opY = POPSplitPartition(query, listOf("a", "b"), "a", a, aPartitionID, opY)
                                    }
                                }
                            }
                            if (zPt != 1) {
                                if (z != b) {
                                    if (z > 1) {
                                        if (b > 1) {
                                            opZ = POPMergePartitionOrderedByIntId(query, listOf("b", "t"), "b", z, zPartitionID, opZ)
                                            opZ.mySortPriority = mutableListOf(SortHelper("b", ESortType.FAST))
                                            opZ = POPSplitPartition(query, listOf("b", "t"), "b", b, bPartitionID, opZ)
                                        } else {
                                            opZ = POPMergePartitionOrderedByIntId(query, listOf("b", "t"), "b", z, zPartitionID, opZ)
                                            opZ.mySortPriority = mutableListOf(SortHelper("b", ESortType.FAST))
                                        }
                                    } else {
                                        if (b > 1) {
                                            opZ = POPSplitPartition(query, listOf("b", "t"), "b", b, bPartitionID, opZ)
                                        }
                                    }
                                }
                            }
                            var opA: IOPBase = POPJoinMerge(query, listOf("b"), opX, opY, false)
                            if (zPt != 1) {
                                if (a > 1) {
                                    opA = POPMergePartition(query, listOf("b"), "a", a, aPartitionID, opA)
                                }
                                if (b > 1) {
                                    opA = POPSplitPartition(query, listOf("b"), "b", b, bPartitionID, opA)
                                }
                            }
                            var opB: IOPBase = POPJoinHashMap(query, listOf("t"), opZ, opA, false)
                            if (zPt == 1) {
                                if (a > 1) {
                                    opB = POPMergePartition(query, listOf("t"), "a", a, aPartitionID, opB)
                                }
                            } else {
                                if (b > 1) {
                                    opB = POPMergePartition(query, listOf("t"), "b", b, bPartitionID, opB)
                                }
                            }
                            val node = opB
                            println(node.toXMLElement().toPrettyString())
                            val writer = MyPrintWriter(false)
                            LuposdateEndpoint.evaluateOperatorgraphToResult(node, writer)
                            val timerFirst = DateHelperRelative.markNow()
                            LuposdateEndpoint.evaluateOperatorgraphToResult(node, writer)
                            val timeFirst = DateHelperRelative.elapsedSeconds(timerFirst)
                            val groupSize = 2000
// val groupSize =1 + (1.0 / timeFirst).toInt()
//println("groupSize $groupSize")
                            val timer = DateHelperRelative.markNow()
                            var time: Double
                            var counter = 0
                            while (true) {
                                counter += groupSize
                                for (i in 0 until groupSize) {
                                    LuposdateEndpoint.evaluateOperatorgraphToResult(node, writer)
                                }
                                time = DateHelperRelative.elapsedSeconds(timer)
                                if (time > minimumTime) {
                                    break
                                }
                            }
                            println("${x}_${y}_${z}__${a}_${b}__${zPt},$numberOfTriples,0,$counter,${time * 1000.0},${counter / time},NoOptimizer")
                        }
                    }
                }
            }
        }
    }
}
