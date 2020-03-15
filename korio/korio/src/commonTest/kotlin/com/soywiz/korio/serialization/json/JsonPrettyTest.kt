package com.soywiz.korio.serialization.json
import lupos.s04arithmetikOperators.ResultVektorRaw
import lupos.s03resultRepresentation.ResultChunk
import lupos.s04logicalOperators.ResultIterator

import com.soywiz.korio.dynamic.mapper.*
import com.soywiz.korio.dynamic.serialization.*
import kotlin.test.*

class JsonPrettyTest {
	val mapper = ObjectMapper()

	data class Demo(val a: Int, val b: String)

	data class DemoList(val demos: ArrayList<Demo>)

	data class DemoSet(val demos: Set<Demo>)

	init {
		// AUTOGENERATED!
		mapper.registerUntype<Demo> { linkedMapOf("a" to it.a.gen(), "b" to it.b.gen()) }
	}


	@Test
	fun encode1() {
		assertEquals("1", Json.stringifyTyped(1, mapper, pretty = true))
		//assertEquals("null", Json.encodePretty(null, mapper))
		assertEquals("true", Json.stringifyTyped(true, mapper, pretty = true))
		assertEquals("false", Json.stringifyTyped(false, mapper, pretty = true))
		assertEquals("{\n}", Json.stringifyTyped(mapOf<String, Any?>(), mapper, pretty = true))
		assertEquals("[\n]", Json.stringifyTyped(listOf<Any?>(), mapper, pretty = true))
		assertEquals("\"a\"", Json.stringifyTyped("a", mapper, pretty = true))
	}

	@Test
	fun encode2() {
		assertEquals(
			"""
			|[
			|	1,
			|	2,
			|	3
			|]
		""".trimMargin(), Json.stringifyTyped(listOf(1, 2, 3), mapper, pretty = true)
		)

		assertEquals(
			"""
			|{
			|	"a": 1,
			|	"b": 2
			|}
		""".trimMargin(), Json.stringifyTyped(linkedMapOf("a" to 1, "b" to 2), mapper, pretty = true)
		)
	}

	@Test
	fun encodeTyped() {
		assertEquals(
			"""
			|{
			|	"a": 1,
			|	"b": "test"
			|}
			""".trimMargin(), Json.stringifyTyped(Demo(1, "test"), mapper, pretty = true)
		)
	}

	@Test
	fun encodeMix() {
		assertEquals(
			"""
				|{
				|	"a": [
				|		1,
				|		2,
				|		3,
				|		4
				|	],
				|	"b": [
				|		5,
				|		6
				|	],
				|	"c": {
				|		"a": true,
				|		"b": null,
				|		"c": "hello"
				|	}
				|}
			""".trimMargin(),
			Json.stringifyTyped(
				linkedMapOf(
					"a" to listOf(1, 2, 3, 4),
					"b" to listOf(5, 6),
					"c" to linkedMapOf(
						"a" to true,
						"b" to null,
						"c" to "hello"
					)
				), mapper, pretty = true
			)
		)
	}
}