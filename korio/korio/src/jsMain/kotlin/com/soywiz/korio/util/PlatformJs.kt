package com.soywiz.korio.util
import lupos.s04arithmetikOperators.ResultVektorRaw
import lupos.s03resultRepresentation.ResultChunk
import lupos.s04logicalOperators.ResultIterator

import com.soywiz.korio.*

internal actual val rawOsName: String by lazy {
	when {
		isNodeJs -> process.platform.unsafeCast<String>()
		else -> navigator.platform.unsafeCast<String>()
	}
}

internal actual val rawPlatformName: String = when {
	isWeb -> "web.js"
	isNodeJs -> "node.js"
	isWorker -> "worker.js"
	isShell -> "shell.js"
	else -> "js"
}
