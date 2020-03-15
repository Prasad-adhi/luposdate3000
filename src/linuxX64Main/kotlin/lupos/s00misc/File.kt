package lupos.s00misc

import dirent.closedir
import dirent.opendir
import dirent.readdir
import kotlin.native.concurrent.*
import kotlinx.cinterop.*
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.memScoped
import lupos.s03resultRepresentation.ResultChunk
import lupos.s04arithmetikOperators.ResultVektorRaw
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.ResultIterator
import platform.posix.FILE
import stdio.fclose
import stdio.fgets
import stdio.fopen
import stdio.fread
import stdio.fwrite
import stdio.luposfprintf
import unistd.luposSTDINread


class File(val filename: String) {
    companion object {
        fun readStdInAsDynamicByteArray(): DynamicByteArray? {
            var res = ByteArray(0)
            memScoped {
                val bufferLength = 64 * 1024
                val buffer = allocArray<ByteVar>(bufferLength)
                while (true) {
                    val len = luposSTDINread(buffer, bufferLength.toULong())
                    if (len <= 0)
                        break
                    res += buffer.readBytes(len.toInt())
                }
            }
            if (res.size < 4)
                return null
            val result = DynamicByteArray(res)
            if (res.size < result.getInt(0))
                result.setInt(res.size, 0)//ensure there are enough available Bytes
            return result
        }
    }

    fun readAsString(): String {
        var result: String = ""
        val file = fopen(filename, "r")
        if (file == null)
            throw Exception("can not open file $filename")
        try {
            memScoped {
                val bufferLength = 64 * 1024
                val buffer = allocArray<ByteVar>(bufferLength)
                while (true) {
                    val nextLine = fgets(buffer, bufferLength, file)?.toKString()
                    if (nextLine == null || nextLine.isEmpty())
                        break
                    result += nextLine
                }
            }
        } finally {
            fclose(file)
        }
        return result
    }

    fun walk(action: (String) -> Unit) {
        val d = opendir(filename);
        if (d != null) {
            while (true) {
                val dir = readdir(d)
                if (dir == null)
                    break
                action(filename + "/" + dir.pointed.d_name!!.toKString())
            }
            closedir(d);
        }
    }

    fun readAsDynamicByteArray(): DynamicByteArray {
        var res = ByteArray(0)
        val file = fopen(filename, "r")
        if (file == null)
            throw Exception("can not open file $filename")
        try {
            memScoped {
                val bufferLength = 64 * 1024
                val buffer = allocArray<ByteVar>(bufferLength)
                while (true) {
                    val len = fread(buffer, 1L.toULong(), bufferLength.toULong(), file)
                    if (len == (0L).toULong())
                        break
                    res += buffer.readBytes(len.toInt())
                }
            }
        } finally {
            fclose(file)
        }
        return DynamicByteArray(res)
    }

    fun write(buffer: DynamicByteArray) {
        val file = fopen(filename, "w")
        if (file == null)
            throw Exception("can not open file $filename")
        try {
            var offset = 0
            val buf = buffer.finish()
            while (offset < buffer.pos) {
                val len = fwrite(buf.refTo(offset), 1L.toULong(), (buffer.pos - offset).toULong(), file)
                offset += len.toInt()
            }
        } finally {
            fclose(file)
        }
    }

    fun printWriter(action: (PrintWriter) -> Unit) {
        val p = PrintWriter(this)
        try {
            p.open()
            action(p)
        } finally {
            p.close()
        }
    }
}


class PrintWriter(val f: File) {
    var file: CValuesRef<FILE>? = null
    fun open() {
        file = fopen(f.filename, "w")
        if (f == null)
            throw Exception("can not open file ${f.filename}")
    }

    fun close() {
        fclose(file)
    }

    fun println(s: String) {
        luposfprintf(file, s + "\n")
    }
}

