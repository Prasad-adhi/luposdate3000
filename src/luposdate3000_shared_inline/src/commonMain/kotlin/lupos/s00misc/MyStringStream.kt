package lupos.s00misc
import kotlin.jvm.JvmField
internal class MyStringStream(str: String) : IMyInputStream {
    @JvmField val data = str.encodeToByteArray()
    @JvmField var off = 0
    override fun read(buf: ByteArray): Int {
        var len = off + buf.size
        var res = buf.size
        if (len> data.size) {
            len = data.size
            res = len - off
        }
        data.copyInto(buf, 0, off, len)
        off = len
        return res
    }
}
