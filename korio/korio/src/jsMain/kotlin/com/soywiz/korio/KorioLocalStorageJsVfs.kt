package com.soywiz.korio
import lupos.s04logicalOperators.ResultIterator

import com.soywiz.korio.file.*
import com.soywiz.korio.file.std.*
import org.w3c.dom.*
import kotlin.browser.*

val jsLocalStorageVfs by lazy {
	MapLikeStorageVfs(object : SimpleStorage {
		override suspend fun get(key: String): String? = localStorage[key]
		override suspend fun set(key: String, value: String) = run { localStorage[key] = value }
		override suspend fun remove(key: String) = localStorage.removeItem(key)
	})
}
