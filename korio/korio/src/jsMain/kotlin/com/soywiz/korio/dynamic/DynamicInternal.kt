package com.soywiz.korio.dynamic
import lupos.s04arithmetikOperators.ResultVektorRaw
import lupos.s03resultRepresentation.ResultChunk
import lupos.s04logicalOperators.ResultIterator

internal actual object DynamicInternal {
	actual val global: Any? = js("(typeof global !== 'undefined') ? global : window")

	actual fun get(instance: Any?, key: String): Any? {
		return (instance.asDynamic())[key]
	}

	actual fun set(instance: Any?, key: String, value: Any?) {
		(instance.asDynamic())[key] = value
	}

	actual fun invoke(instance: Any?, key: String, args: Array<out Any?>): Any? {
		return (instance.asDynamic())[key].apply(instance, args)
	}
}
