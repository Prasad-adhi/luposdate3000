package lupos.s00misc

import kotlinx.coroutines.*
import lupos.s00misc.File

object Coverage {
    var verbose = false
    val counters = Array(CoverageMapGenerated.keys.size) { 0L }
    fun funStart(counter: Int) {
        counters[counter]++
        if (verbose)
            println("funStart $counter")
    }

    fun forLoopStart(counter: Int) {
        counters[counter]++
        if (verbose)
            println("forLoopStart $counter")
    }

    fun forEachLoopStart(counter: Int) {
        counters[counter]++
        if (verbose)
            println("forEachLoopStart $counter")
    }

    fun whileLoopStart(counter: Int) {
        counters[counter]++
        if (verbose)
            println("whileLoopStart $counter")
    }

    fun whenCaseStart(counter: Int) {
        counters[counter]++
        if (verbose)
            println("whenCaseStart $counter")
    }

    fun ifStart(counter: Int) {
        counters[counter]++
        if (verbose)
            println("ifStart $counter")
    }

    fun statementStart(counter: Int) {
        counters[counter]++
    }

    override fun toString(): String {
        val res = StringBuilder()
        for (k in 0 until counters.size) {
            val v = CoverageMapGenerated[k]
            if (v != "")
                res.append("${counters[k]},$v\n")
        }
        return res.toString()
    }

    fun printToFile() {
        val s = toString()
        var h = s.hashCode()
        if (h < 0)
            h = -h
        File("mnt/coverage${h}.cov").printWriter { out ->
            out.println(s)
        }
    }

    init {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() = runBlocking {
                printToFile()
            }
        })
    }
}
