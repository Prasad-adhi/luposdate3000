package com.soywiz.korio.vfs
import lupos.s04arithmetikOperators.ResultVektorRaw
import lupos.s03resultRepresentation.ResultChunk
import lupos.s04logicalOperators.ResultIterator

import com.soywiz.korio.async.*
import com.soywiz.korio.file.*
import com.soywiz.korio.file.std.*
import com.soywiz.korio.lang.*
import com.soywiz.korio.stream.*
import kotlinx.coroutines.channels.*
import kotlin.test.*

class CopyToTreeTest {
	@Test
	fun name() = suspendTest {
		val mem = MemoryVfs(
			linkedMapOf(
				"root.txt" to "hello".toByteArray().openAsync(),
				"hello/world.txt" to "hello".toByteArray().openAsync()
			)
		)
		val out = MemoryVfs()
		mem.copyToTree(out)
		assertEquals(
			"[/root.txt, /hello, /hello/world.txt]",
			out.listRecursive().map { it.fullName }.toList().toString()
		)
	}
}