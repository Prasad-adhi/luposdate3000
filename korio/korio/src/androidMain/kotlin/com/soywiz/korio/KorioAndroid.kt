package com.soywiz.korio
import lupos.s04arithmetikOperators.ResultVektorRaw
import lupos.s03resultRepresentation.ResultChunk
import lupos.s04logicalOperators.ResultIterator

import android.content.*
import com.soywiz.korio.async.*
import kotlinx.coroutines.*
import com.soywiz.korio.android.withAndroidContext

fun Korio(context: Context, entry: suspend CoroutineScope.() -> Unit) = asyncEntryPoint { withAndroidContext(context) { entry(CoroutineScope(coroutineContext)) } }
