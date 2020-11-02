package com.soywiz.korio.compression

import com.soywiz.kmem.*
import com.soywiz.korio.async.*
import com.soywiz.korio.compression.deflate.*
import com.soywiz.korio.util.encoding.*
import kotlin.test.*

class CompressionTest {
    @Test
    fun name() = suspendTest {
        val data =
            "H4sIAAAAAAAAA+3SsREAEBSD4WcFm2ACTID9dxGFxgDcub/4mjQpEmdmDuYPKwsSJT3qz1KkXu7fWZMu4/IGr78AAAAAAD+a6ywcnAAQAAA=".fromBase64()
        val res = data.uncompress(GZIP)
        val res2 = res.readIntArrayLE(0, 4096 / 4)
        assertEquals(
            "" +
                "1111111111111111111111111111111111111111111111111111111111111111111818181814950511111111111111111111111111818181816566671111111" +
                "1111111111111111118181811818283111111111111111111111111118181111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111",
            res2.toList().joinToString("")
        )
    }
}
