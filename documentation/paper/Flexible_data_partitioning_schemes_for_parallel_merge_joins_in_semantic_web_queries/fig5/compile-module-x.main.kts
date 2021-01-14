#!/usr/bin/env kotlin
@file:Import("src/luposdate3000_scripting/generate-buildfile-inline.kt")
@file:Import("src/luposdate3000_scripting/generate-buildfile-suspend.kt")
@file:Import("src/luposdate3000_scripting/generate-buildfile-module.kt")
@file:Import("src/luposdate3000_shared/src/commonMain/kotlin/lupos/s00misc/EOperatingSystem.kt")
@file:Import("src/luposdate3000_shared_inline/src/commonMain/kotlin/lupos/s00misc/_Platform.kt")
@file:Import("src/luposdate3000_shared_inline/src/jvmMain/kotlin/lupos/s00misc/_Platform.kt")
@file:CompilerOptions("-Xmulti-platform")
createBuildFileForModule("Luposdate3000_Endpoint", ReleaseMode.Enable, SuspendMode.Disable, InlineMode.Enable, DryMode.Disable, FastMode.JVM, IntellijMode.Disable, arrayOf("--QueryResultToStream=lupos.s11outputResult.QueryResultToEmptyStream"))
