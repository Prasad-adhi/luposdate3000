/*
 * This file is part of the Luposdate3000 distribution (https://github.com/luposdate3000/luposdate3000).
 * Copyright (c) 2020-2021, Institute of Information Systems (Benjamin Warnke and contributors of LUPOSDATE3000), University of Luebeck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package launcher
import lupos.shared.EOperatingSystemExt
import lupos.shared_inline.Platform
import java.io.File
import java.io.PrintWriter
import java.lang.ProcessBuilder.Redirect
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.nio.file.StandardCopyOption.REPLACE_EXISTING

val copySelevtively = false

val validPlatforms = listOf("iosArm32", "iosArm64", "linuxX64", "macosX64", "mingwX64")
private fun printDependencies(dependencies: Set<String>, buildForIDE: Boolean, out: PrintWriter) {
    for (d in dependencies) {
        if (d.startsWith("luposdate3000")) {
            if (buildForIDE) {
                var t = d.substring("luposdate3000:".length, d.lastIndexOf(":")).toLowerCase()
                if (t.contains("#")) {
                    t = t.substring(0, t.indexOf("#"))
                }
                out.println("                implementation(project(\":src:${t}\"))")
            } else {
                out.println("                compileOnly(\"${d.replace("#", "")}\")")
            }
        } else if (d.startsWith("npm(")) {
            out.println("                implementation($d)")
        } else {
            out.println("                implementation(\"$d\")")
        }
    }
}

private fun copyFileWithReplacement(src: File, dest: File, replacement: Map<String, String>, sharedInlineReferences: MutableSet<String>, dry: Boolean = false) {
    val out = if (dry) {
        null
    } else {
        dest.printWriter()
    }
    val effectiveReplacement = mutableMapOf<String, String>()
    effectiveReplacement.putAll(replacement)
    if (src.toString().contains("jsMain")) {
        effectiveReplacement.remove(" public ")
    }
    var line = 0
    src.forEachLine { it ->
        var s = it
        for ((k, v) in effectiveReplacement) {
            if (s.startsWith("import lupos.shared_inline.")) {
                sharedInlineReferences.add(s.substring("import lupos.shared_inline.".length))
            } else if (s.startsWith("// require lupos.shared_inline.")) {
                sharedInlineReferences.add(s.substring("// require lupos.shared_inline.".length))
            }
            s = s.replace(k, v)
            if (k.startsWith(" ")) {
                if (s.startsWith(k.substring(1))) {
                    s = v + s.substring(k.length - 1)
                }
            }
        }
        s = s.replace("${'$'}lupos.SOURCE_FILE", "${src.getAbsolutePath().replace("\\", "/")}:$line")
        s = s.replace("${'$'}{lupos.SOURCE_FILE}", "${src.getAbsolutePath().replace("\\", "/")}:$line")
        s = s.replace("lupos.SOURCE_FILE", "\"${src.getAbsolutePath().replace("\\", "/")}:$line\"")
        out?.println(s)
        line++
    }

    out?.close()
}

private fun copyFilesWithReplacement(src: String, dest: String, replacement: Map<String, String>, pathSeparator: String, sharedInlineReferences: MutableSet<String>, dry: Boolean = false) {
    for (it in File(src).walk()) {
        val tmp = it.toString()
        val t = tmp.substring(src.length)
        if (File(tmp).isFile()) {
            copyFileWithReplacement(File(src + pathSeparator + t), File(dest + pathSeparator + t), replacement, sharedInlineReferences, dry)
        } else {
            if (!dry) {
                File(dest + pathSeparator + t).mkdirs()
            }
        }
    }
}

class CreateModuleArgs() {
    var compilerVersion: String = ""
    var enabledFunc: () -> Boolean = { true }
    var enabledRunFunc: () -> Boolean = { true }
    var moduleName: String = ""
    var moduleFolder: String = ""
    var modulePrefix: String = ""
    var platform: String = "linuxX64"
    var releaseMode: ReleaseMode = ReleaseMode.Disable
    var suspendMode: SuspendMode = SuspendMode.Disable
    var inlineMode: InlineMode = InlineMode.Disable
    var dryMode: DryMode = DryMode.Disable
    var target: TargetMode2 = TargetMode2.JVM
    var ideaBuildfile: IntellijMode = IntellijMode.Disable
    var codegenKAPT: Boolean = false
    var codegenKSP: Boolean = false
    var args: MutableMap<String, String> = mutableMapOf()
    var dependenciesCommon: MutableSet<String> = mutableSetOf<String>()
    var dependenciesJvm: MutableSet<String> = mutableSetOf<String>()
    var dependenciesJvmRecoursive: MutableSet<String> = mutableSetOf<String>()
    var dependenciesJs: MutableSet<String> = mutableSetOf<String>()
    var dependenciesNative: MutableSet<String> = mutableSetOf<String>()

    init {
        if (Platform.getOperatingSystem() == EOperatingSystemExt.Windows) {
            platform = "mingw64"
        } else {
            platform = "linuxX64"
        }
    }

    fun clone(): CreateModuleArgs {
        var res = CreateModuleArgs()
        res.dependenciesCommon.addAll(dependenciesCommon)
        res.dependenciesJvm.addAll(dependenciesJvm)
        res.dependenciesJvmRecoursive.addAll(dependenciesJvmRecoursive)
        res.dependenciesJs.addAll(dependenciesJs)
        res.dependenciesNative.addAll(dependenciesNative)
        res.compilerVersion = compilerVersion
        res.enabledFunc = enabledFunc
        res.enabledRunFunc = enabledRunFunc
        res.moduleName = moduleName
        res.moduleFolder = moduleFolder
        res.modulePrefix = modulePrefix
        res.platform = platform
        res.releaseMode = releaseMode
        res.suspendMode = suspendMode
        res.inlineMode = inlineMode
        res.dryMode = dryMode
        res.target = target
        res.ideaBuildfile = ideaBuildfile
        res.codegenKAPT = codegenKAPT
        res.codegenKSP = codegenKSP
        res.args = args
        return res
    }

    fun ssetCompilerVersion(compilerVersion: String): CreateModuleArgs {
        val res = clone()
        res.compilerVersion = compilerVersion
        return res
    }

    fun ssetEnabledFunc(enabledFunc: () -> Boolean): CreateModuleArgs {
        val res = clone()
        res.enabledFunc = enabledFunc
        return res
    }

    fun ssetEnabledRunFunc(enabledRunFunc: () -> Boolean): CreateModuleArgs {
        val res = clone()
        res.enabledRunFunc = enabledRunFunc
        return res
    }

    fun ssetArgs(args: MutableMap<String, String>): CreateModuleArgs {
        val res = clone()
        res.args = mutableMapOf()
        res.args.putAll(this.args)
        res.args.putAll(args)
        return res
    }

    fun ssetArgs2(args: MutableMap<String, MutableMap<String, String>>): CreateModuleArgs {
        var arg = args["Luposdate3000_Shared_Inline"]
        if (arg != null) {
            val res = clone()
            res.args = mutableMapOf()
            res.args.putAll(this.args)
            res.args.putAll(arg)
        }
        arg = args[moduleName]
        if (arg != null) {
            val res = clone()
            res.args = mutableMapOf()
            res.args.putAll(this.args)
            res.args.putAll(arg)
            args.remove(moduleName)
            return res
        } else {
            return this
        }
    }

    fun ssetModuleName(moduleName: String): CreateModuleArgs {
        val res = clone()
        val onWindows = System.getProperty("os.name").contains("Windows")
        val pathSeparator: String
        if (onWindows) {
            pathSeparator = "\\\\"
        } else {
            pathSeparator = "/"
        }
        res.moduleName = moduleName
        res.moduleFolder = "src${pathSeparator}${moduleName.toLowerCase()}"
        return res
    }

    fun ssetModulePrefix(modulePrefix: String): CreateModuleArgs {
        val res = clone()
        res.modulePrefix = modulePrefix
        return res
    }

    fun ssetReleaseMode(releaseMode: ReleaseMode): CreateModuleArgs {
        val res = clone()
        res.releaseMode = releaseMode
        return res
    }

    fun ssetSuspendMode(suspendMode: SuspendMode): CreateModuleArgs {
        val res = clone()
        res.suspendMode = suspendMode
        return res
    }

    fun ssetInlineMode(inlineMode: InlineMode): CreateModuleArgs {
        val res = clone()
        res.inlineMode = inlineMode
        return res
    }

    fun ssetDryMode(dryMode: DryMode): CreateModuleArgs {
        val res = clone()
        res.dryMode = dryMode
        return res
    }

    fun ssetTarget(target: TargetMode2): CreateModuleArgs {
        val res = clone()
        res.target = target
        return res
    }

    fun ssetIdeaBuildfile(ideaBuildfile: IntellijMode): CreateModuleArgs {
        val res = clone()
        res.ideaBuildfile = ideaBuildfile
        return res
    }

    fun ssetCodegenKSP(codegenKSP: Boolean): CreateModuleArgs {
        val res = clone()
        res.codegenKSP = codegenKSP
        return res
    }

    fun ssetCodegenKAPT(codegenKAPT: Boolean): CreateModuleArgs {
        val res = clone()
        res.codegenKAPT = codegenKAPT
        return res
    }

    fun getPossibleOptions(): List<String> {
        val res = mutableListOf<String>()
        if (File(File(moduleFolder), "configOptions").exists()) {
            File(File(moduleFolder), "configOptions").forEachLine {
                val opt = it.split(",")
                if (opt.size == 4) {
                    res.add(opt[0])
                }
            }
        }
        return res
    }
}

public fun createBuildFileForModule(moduleArgs: CreateModuleArgs) {
    try {
        val buildLibrary = moduleArgs.modulePrefix != "Luposdate3000_Main"
var enableJVM=targetModeCompatible(moduleArgs.target,TargetMode2.JVM)
var enableJS=targetModeCompatible(moduleArgs.target,TargetMode2.JS)
var enableNative=targetModeCompatible(moduleArgs.target,TargetMode2.Native)
        val replacementsDefault = mutableMapOf<String, String>()
        if (buildLibrary) {
            replacementsDefault[" public "] = " @lupos.ProguardKeepAnnotation public "
        }
        val renameSharedInline = !moduleArgs.codegenKSP && !moduleArgs.codegenKAPT
        if (renameSharedInline) {
            replacementsDefault["lupos.shared_inline"] = "lupos.${moduleArgs.moduleName}"
        }
        val sharedInlineReferences = mutableSetOf<String>()
        if (moduleArgs.dryMode == DryMode.Enable || moduleArgs.ideaBuildfile == IntellijMode.Enable) {
            moduleArgs.dryMode = DryMode.Enable
        } else {
            moduleArgs.dryMode = DryMode.Disable
        }
        var appendix = ""
        if (moduleArgs.suspendMode == SuspendMode.Enable) {
            appendix += "_Coroutines"
        } else {
            appendix += "_Threads"
        }
        if (moduleArgs.inlineMode == InlineMode.Enable) {
            appendix += "_Inline"
        } else {
            appendix += "_NoInline"
        }
        if (moduleArgs.releaseMode == ReleaseMode.Enable) {
            appendix += "_Release"
        } else {
            appendix += "_Debug"
        }
        val onWindows = System.getProperty("os.name").contains("Windows")
        val onLinux = !onWindows // TODO this is not correct ...
        val pathSeparator: String
        val pathSeparatorEscaped: String
        if (onWindows) {
            pathSeparator = "\\"
            pathSeparatorEscaped = "\\\\"
        } else {
            pathSeparator = "/"
            pathSeparatorEscaped = "/"
        }
        if (File("${moduleArgs.moduleFolder}/disableTarget").exists()) {
            File("${moduleArgs.moduleFolder}/disableTarget").forEachLine {
                when (it) {
                    "jvm" -> enableJVM = false
                    "js" -> {
                        enableJS = false
                    }
                    moduleArgs.platform, "native" -> enableNative = false
                }
            }
        }
        if (!(enableJVM || enableJS || enableNative)) {
            return
        }
        println("generating buildfile for ${moduleArgs.moduleName}")
        if (!buildLibrary && moduleArgs.codegenKSP) {
            if (moduleArgs.compilerVersion.contains("SNAPSHOT")) {
                return
            }
        }
        var shortFolder = ".$pathSeparator${moduleArgs.moduleName}"
        shortFolder = shortFolder.substring(shortFolder.lastIndexOf(pathSeparator) + 1)
        File("src.generated").deleteRecursively()
        val buildFolder = "build-cache${pathSeparator}build_$shortFolder$appendix"
        println("buildFolder :: $buildFolder")
        val srcFolder = "build-cache${pathSeparator}src_$shortFolder$appendix"
        if (moduleArgs.ideaBuildfile == IntellijMode.Disable) {
            File("src.generated").mkdirs()
        }
        val p = Paths.get("${moduleArgs.moduleFolder}${pathSeparator}src")
        println("basepath=$p")
        try {
            Files.walk(p, 1).forEach { it ->
                val tmp = it.toString()
                if (tmp.length > p.toString().length) {
                    val idx = tmp.lastIndexOf(pathSeparator)
                    val f: String
                    if (idx >= 0) {
                        f = tmp.substring(idx + 1)
                    } else {
                        f = tmp
                    }
                    if (f.startsWith("common")) {
                        copyFilesWithReplacement(tmp, "src.generated$pathSeparator" + f.replace("common.*Main", "commonMain"), replacementsDefault, pathSeparator, sharedInlineReferences, moduleArgs.ideaBuildfile == IntellijMode.Enable)
                    } else if (f.startsWith("jvm")) {
                        if (enableJVM) {
                            copyFilesWithReplacement(tmp, "src.generated$pathSeparator" + f.replace("jvm.*Main", "jvmMain"), replacementsDefault, pathSeparator, sharedInlineReferences, moduleArgs.ideaBuildfile == IntellijMode.Enable)
                        }
                    } else if (f.startsWith("js")) {
                        if (enableJS) {
                            copyFilesWithReplacement(tmp, "src.generated$pathSeparator" + f.replace("js.*Main", "jsMain"), replacementsDefault, pathSeparator, sharedInlineReferences, moduleArgs.ideaBuildfile == IntellijMode.Enable)
                        }
                    } else if (f.startsWith("native")) {
                        if (enableNative) {
                            copyFilesWithReplacement(tmp, "src.generated$pathSeparator" + f.replace("native.*Main", "nativeMain"), replacementsDefault, pathSeparator, sharedInlineReferences, moduleArgs.ideaBuildfile == IntellijMode.Enable)
                        }
                    } else if (f.startsWith(moduleArgs.platform)) {
                        if (enableNative) {
                            copyFilesWithReplacement(tmp, "src.generated$pathSeparator" + f.replace("${moduleArgs.platform}.*Main", "${moduleArgs.platform}Main"), replacementsDefault, pathSeparator, sharedInlineReferences, moduleArgs.ideaBuildfile == IntellijMode.Enable)
                        }
                    }
                }
            }
        } catch (e: java.nio.file.NoSuchFileException) {
        }
        if (moduleArgs.ideaBuildfile == IntellijMode.Disable) {
            File("src.generated${pathSeparator}commonMain${pathSeparator}kotlin${pathSeparator}lupos").mkdirs()
            File("src.generated${pathSeparator}settings.gradle.kts").printWriter().use { out ->
                out.println("pluginManagement {")
                out.println("    resolutionStrategy {")
                out.println("        eachPlugin {")
                out.println("            when (requested.id.id) {")
                out.println("                \"kotlin-ksp\",")
                out.println("                \"org.jetbrains.kotlin.kotlin-ksp\",")
                out.println("                \"org.jetbrains.kotlin.ksp\" -> useModule(\"org.jetbrains.kotlin:kotlin-ksp:\${requested.version}\")")
                out.println("            }")
                out.println("        }")
                out.println("    }")
                out.println("    repositories {")
                out.println("        mavenLocal()")
                out.println("        maven(\"https://dl.bintray.com/kotlin/kotlin-eap\")")
                out.println("        google()")
                out.println("        gradlePluginPortal()")
                out.println("    }")
                out.println("}")
                out.println("rootProject.name = \"${moduleArgs.moduleName}\"") // maven-artifactID
            }
        }
        val commonDependencies = mutableSetOf<String>()
        commonDependencies.addAll(moduleArgs.dependenciesCommon)
        val jvmDependencies = mutableSetOf<String>()
        jvmDependencies.addAll(moduleArgs.dependenciesJvm)
        val jsDependencies = mutableSetOf<String>()
        jsDependencies.addAll(moduleArgs.dependenciesJs)
        jsDependencies.removeAll(commonDependencies)
        val nativeDependencies = mutableSetOf<String>()
        nativeDependencies.addAll(moduleArgs.dependenciesNative)
        nativeDependencies.removeAll(commonDependencies)
        for (filename in listOf("src.generated${pathSeparator}build.gradle.kts", "${moduleArgs.moduleFolder}${pathSeparator}build.gradle.kts")) {
            var buildForIDE = filename != "src.generated${pathSeparator}build.gradle.kts"
            if (moduleArgs.ideaBuildfile == IntellijMode.Enable && !buildForIDE) {
                continue
            }
            if (moduleArgs.ideaBuildfile == IntellijMode.Disable && buildForIDE) {
                continue
            }
            File(filename).printWriter().use { out ->
                out.println("import org.jetbrains.kotlin.gradle.tasks.KotlinCompile")
                out.println("buildscript {")
                out.println("    repositories {")
                out.println("        mavenLocal()")
                out.println("        jcenter()")
                out.println("        google()")
                out.println("        mavenCentral()")
                out.println("        maven(\"https://plugins.gradle.org/m2/\")")
                out.println("        maven(\"https://dl.bintray.com/kotlin/kotlin-eap\")")
                out.println("    }")
                out.println("    dependencies {")
                out.println("        classpath(\"org.jetbrains.kotlin:kotlin-gradle-plugin:${moduleArgs.compilerVersion}\")")
                out.println("        classpath(\"com.guardsquare:proguard-gradle:7.1.0-beta3\")")
                out.println("    }")
                out.println("}")
                if (buildForIDE) {
                    for (d in commonDependencies) {
                        if (d.startsWith("luposdate3000")) {
                            var t = d.substring("luposdate3000:".length, d.lastIndexOf(":")).toLowerCase()
                            if (t.contains("#")) {
                                t = t.substring(0, t.indexOf("#"))
                            }
                            out.println("    evaluationDependsOn(\":src:${t}\")")
                        }
                    }
                }
                out.println("plugins {")
                out.println("    id(\"org.jetbrains.kotlin.multiplatform\") version \"${moduleArgs.compilerVersion}\"")
                if (!buildLibrary && moduleArgs.codegenKAPT) {
                    out.println("    id(\"org.jetbrains.kotlin.kapt\") version \"${moduleArgs.compilerVersion}\"")
                }
                if (!buildLibrary && moduleArgs.codegenKSP) {
                    if (moduleArgs.compilerVersion.startsWith("1.4")) {
                        out.println("    id(\"kotlin-ksp\") version \"1.4.0-dev-experimental-20200914\"")
                    } else {
                        return //currently there is no 1.5 plugin from jetbrains
                    }
                }
                if (buildForIDE && !buildLibrary) {
                    out.println("    application")
                }
                out.println("}")
                out.println("repositories {")
                out.println("    mavenLocal()")
                out.println("    jcenter()")
                out.println("    google()")
                out.println("    mavenCentral()")
                out.println("    maven(\"https://plugins.gradle.org/m2/\")")
                out.println("    maven(\"https://dl.bintray.com/kotlin/kotlin-eap\")")
                out.println("}")
                out.println("group = \"luposdate3000\"") // maven-groupID
                out.println("version = \"0.0.1\"") // maven-version
                out.println("apply(plugin = \"maven-publish\")")
                // see /opt/kotlin/compiler/cli/cli-common/src/org/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments.kt
                // or kotlinc -X
                out.println("kotlin {")
                out.println("    explicitApi()") // https://zsmb.co/mastering-api-visibility-in-kotlin/
                out.println("    metadata {")
                out.println("        compilations.forEach{")
                out.println("            it.kotlinOptions {")
                out.println("                freeCompilerArgs += \"-Xopt-in=kotlin.RequiresOptIn\"")
                out.println("                freeCompilerArgs += \"-Xnew-inference\"")
                out.println("                freeCompilerArgs += \"-Xinline-classes\"")
                out.println("            }")
                out.println("        }")
                out.println("    }")
                if (enableJVM) {
                    out.println("    jvm {")
                    out.println("        compilations.forEach{")
                    out.println("            it.kotlinOptions {")
//                    out.println("                jvmTarget= \"14\"")
                    out.println("                jvmTarget= \"1.8\"")
                    out.println("                useIR = true")
                    out.println("                freeCompilerArgs += \"-Xopt-in=kotlin.RequiresOptIn\"")
                    out.println("                freeCompilerArgs += \"-Xno-param-assertions\"")
                    out.println("                freeCompilerArgs += \"-Xnew-inference\"")
                    out.println("                freeCompilerArgs += \"-Xno-receiver-assertions\"")
                    out.println("                freeCompilerArgs += \"-Xno-call-assertions\"")
                    out.println("            }")
                    out.println("        }")
                    out.println("    }")
                }
                if (enableJS) {
                    out.println("    js {")
                    out.println("        moduleName = \"${moduleArgs.modulePrefix}\"")
                    out.println("        browser {")
                    out.println("            compilations.forEach{")
                    out.println("                it.kotlinOptions {")
                    out.println("                    freeCompilerArgs += \"-Xopt-in=kotlin.RequiresOptIn\"")
                    out.println("                    freeCompilerArgs += \"-Xnew-inference\"")
                    out.println("                }")
                    out.println("            }")
                    out.println("            dceTask {")
                    out.println("                dceOptions.devMode = true")
                    out.println("            }")
                    out.println("            testTask {")
                    out.println("                enabled = false")
                    out.println("            }")
                    out.println("        }")
                    out.println("        nodejs {")
                    out.println("            compilations.forEach{")
                    out.println("                it.kotlinOptions {")
                    out.println("                    freeCompilerArgs += \"-Xopt-in=kotlin.RequiresOptIn\"")
                    out.println("                    freeCompilerArgs += \"-Xnew-inference\"")
                    out.println("                }")
                    out.println("            }")
                    out.println("            testTask {")
                    out.println("                enabled = false")
                    out.println("            }")
                    out.println("        }")
                    out.println("        binaries.executable()")
                    out.println("    }")
                }
                if (enableNative) {
                    out.println("    ${moduleArgs.platform}(\"${moduleArgs.platform}\") {")
                    out.println("        compilations.forEach{")
                    out.println("            it.kotlinOptions {")
                    out.println("                freeCompilerArgs += \"-Xopt-in=kotlin.RequiresOptIn\"")
                    out.println("                freeCompilerArgs += \"-Xnew-inference\"")
                    out.println("            }")
                    out.println("        }")
                    out.println("        binaries {")
                    if (buildLibrary) {
                        if (moduleArgs.releaseMode == ReleaseMode.Enable) {
                            out.println("            sharedLib (listOf(RELEASE)){")
                            out.println("                baseName = \"${moduleArgs.modulePrefix}\"")
                            out.println("            }")
                        } else {
                            out.println("            sharedLib (listOf(DEBUG)){")
                            out.println("                baseName = \"${moduleArgs.modulePrefix}\"")
                            out.println("            }")
                        }
                    } else {
                        if (moduleArgs.releaseMode == ReleaseMode.Enable) {
                            out.println("            executable(listOf(RELEASE)) {")
                            out.println("            }")
                        } else {
                            out.println("            executable(listOf(DEBUG)) {")
                            out.println("            }")
                        }
                    }
                    out.println("        }")
                    out.println("    }")
                }
                out.println("    sourceSets {")
                out.println("        val commonMain by getting {")
                out.println("            dependencies {")
                printDependencies(commonDependencies, buildForIDE, out)
                out.println("            }")
                out.println("        }")
                if (enableJVM) {
                    out.println("        val jvmMain by getting {")
                    out.println("            dependencies {")
                    printDependencies(jvmDependencies, buildForIDE, out)
                    if (!buildLibrary && moduleArgs.codegenKAPT) {
                        printDependencies(moduleArgs.dependenciesJvmRecoursive, buildForIDE, out)
                    }
                    if (!buildLibrary && moduleArgs.codegenKSP) {
                        printDependencies(moduleArgs.dependenciesJvmRecoursive, buildForIDE, out)
                        for (dep in moduleArgs.dependenciesJvmRecoursive) {
                            if (buildForIDE) {
                                if (dep.startsWith("luposdate")) {
                                    out.println("                configurations[\"ksp\"].dependencies.add(project.dependencies.create(project(\":src:${dep.toLowerCase().replace("luposdate3000:", "").replace(":0.0.1", "")}\")))")
                                } else {
                                    out.println("                configurations[\"ksp\"].dependencies.add(project.dependencies.create(\"$dep\"))")
                                }
                            } else {
                                out.println("                configurations[\"ksp\"].dependencies.add(project.dependencies.create(\"$dep\"))")
                            }
                        }
                    }
                    out.println("            }")
                    out.println("        }")
                }
                if (enableJS) {
                    out.println("        val jsMain by getting {")
                    out.println("            dependencies {")
                    printDependencies(jsDependencies, buildForIDE, out)
                    out.println("            }")
                    out.println("        }")
                }
                if (enableNative) {
                    out.println("        val ${moduleArgs.platform}Main by getting {")
                    out.println("            dependencies {")
                    printDependencies(nativeDependencies, buildForIDE, out)
                    out.println("            }")
                    out.println("        }")
                }
                out.println("    }")
                if (buildForIDE) {
                    if (!moduleArgs.moduleName.startsWith("Luposdate3000_Shared_")) {
                        out.println("    sourceSets[\"commonMain\"].kotlin.srcDir(\"..${pathSeparatorEscaped}xxx_generated_xxx${pathSeparatorEscaped}${moduleArgs.moduleFolder}${pathSeparatorEscaped}src${pathSeparatorEscaped}commonMain${pathSeparatorEscaped}kotlin\")")
                        out.println("    sourceSets[\"commonMain\"].resources.srcDir(\"..${pathSeparatorEscaped}xxx_generated_xxx${pathSeparatorEscaped}${moduleArgs.moduleFolder}${pathSeparatorEscaped}src${pathSeparatorEscaped}commonMain${pathSeparatorEscaped}resources\")")
                    }
                    if (enableJVM) {
                        if (!moduleArgs.moduleName.startsWith("Luposdate3000_Shared_")) {
                            out.println("    sourceSets[\"jvmMain\"].kotlin.srcDir(\"..${pathSeparatorEscaped}xxx_generated_xxx${pathSeparatorEscaped}${moduleArgs.moduleFolder}${pathSeparatorEscaped}src${pathSeparatorEscaped}jvmMain${pathSeparatorEscaped}kotlin\")")
                            out.println("    sourceSets[\"jvmMain\"].resources.srcDir(\"..${pathSeparatorEscaped}xxx_generated_xxx${pathSeparatorEscaped}${moduleArgs.moduleFolder}${pathSeparatorEscaped}src${pathSeparatorEscaped}jvmMain${pathSeparatorEscaped}resources\")")
                        }
                    }
                    if (enableJS) {
                        if (!moduleArgs.moduleName.startsWith("Luposdate3000_Shared_")) {
                            out.println("    sourceSets[\"jsMain\"].kotlin.srcDir(\"..${pathSeparatorEscaped}xxx_generated_xxx${pathSeparatorEscaped}${moduleArgs.moduleFolder}${pathSeparatorEscaped}src${pathSeparatorEscaped}jsMain${pathSeparatorEscaped}kotlin\")")
                            out.println("    sourceSets[\"jsMain\"].resources.srcDir(\"..${pathSeparatorEscaped}xxx_generated_xxx${pathSeparatorEscaped}${moduleArgs.moduleFolder}${pathSeparatorEscaped}src${pathSeparatorEscaped}jsMain${pathSeparatorEscaped}resources\")")
                        }
                    }
                    if (enableNative) {
                        out.println("    sourceSets[\"${moduleArgs.platform}Main\"].kotlin.srcDir(\"nativeMain${pathSeparatorEscaped}kotlin\")")
                        if (!moduleArgs.moduleName.startsWith("Luposdate3000_Shared_")) {
                            out.println("    sourceSets[\"${moduleArgs.platform}Main\"].kotlin.srcDir(\"..${pathSeparatorEscaped}xxx_generated_xxx${pathSeparatorEscaped}${moduleArgs.moduleFolder}${pathSeparatorEscaped}src${pathSeparatorEscaped}nativeMain${pathSeparatorEscaped}kotlin\")")
                            out.println("    sourceSets[\"${moduleArgs.platform}Main\"].kotlin.srcDir(\"..${pathSeparatorEscaped}xxx_generated_xxx${pathSeparatorEscaped}${moduleArgs.moduleFolder}${pathSeparatorEscaped}src${pathSeparatorEscaped}${moduleArgs.platform}Main${pathSeparatorEscaped}resources\")")
                            out.println("    sourceSets[\"${moduleArgs.platform}Main\"].resources.srcDir(\"..${pathSeparatorEscaped}xxx_generated_xxx${pathSeparatorEscaped}${moduleArgs.moduleFolder}${pathSeparatorEscaped}src${pathSeparatorEscaped}nativeMain${pathSeparatorEscaped}kotlin\")")
                            out.println("    sourceSets[\"${moduleArgs.platform}Main\"].resources.srcDir(\"..${pathSeparatorEscaped}xxx_generated_xxx${pathSeparatorEscaped}${moduleArgs.moduleFolder}${pathSeparatorEscaped}src${pathSeparatorEscaped}${moduleArgs.platform}Main${pathSeparatorEscaped}resources\")")
                        }
                    }
                } else {
                    out.println("    sourceSets[\"commonMain\"].kotlin.srcDir(\"commonMain${pathSeparatorEscaped}kotlin\")")
                    out.println("    sourceSets[\"commonMain\"].resources.srcDir(\"commonMain${pathSeparatorEscaped}resources\")")
                    if (enableJVM) {
                        out.println("    sourceSets[\"jvmMain\"].kotlin.srcDir(\"jvmMain${pathSeparatorEscaped}kotlin\")")
                        out.println("    sourceSets[\"jvmMain\"].resources.srcDir(\"jvmMain${pathSeparatorEscaped}resources\")")
                    }
                    if (enableJS) {
                        out.println("    sourceSets[\"jsMain\"].kotlin.srcDir(\"jsMain${pathSeparatorEscaped}kotlin\")")
                        out.println("    sourceSets[\"jsMain\"].resources.srcDir(\"jsMain${pathSeparatorEscaped}resources\")")
                    }
                    if (enableNative) {
                        out.println("    sourceSets[\"${moduleArgs.platform}Main\"].kotlin.srcDir(\"nativeMain${pathSeparatorEscaped}kotlin\")")
                        out.println("    sourceSets[\"${moduleArgs.platform}Main\"].kotlin.srcDir(\"${moduleArgs.platform}Main${pathSeparatorEscaped}kotlin\")")
                        out.println("    sourceSets[\"${moduleArgs.platform}Main\"].resources.srcDir(\"nativeMain${pathSeparatorEscaped}resources\")")
                        out.println("    sourceSets[\"${moduleArgs.platform}Main\"].resources.srcDir(\"${moduleArgs.platform}Main${pathSeparatorEscaped}resources\")")
                    }
                }
                out.println("}")
                if (!buildLibrary && moduleArgs.codegenKAPT) {
                    out.println("dependencies {")
                    if (buildForIDE) {
                        out.println("    \"kapt\"(project(\":src:luposdate3000_code_generator_kapt\")) // attention to the '\"' around kapt - otherwise it resolves to another function")
                    } else {
                        out.println("    \"kapt\"(\"luposdate3000:Luposdate3000_Code_Generator_KAPT:0.0.1\") // attention to the '\"' around kapt - otherwise it resolves to another function")
                    }
                    out.println("}")
                }
                if (buildForIDE && !buildLibrary) {
                    out.println("application{")
                    out.println("    mainClass.set(\"MainKt\")")
                    out.println("}")
                }
                out.println("tasks.register(\"mydependencies\") {")
                out.println("    dependsOn(\"build\")")
                if (enableJVM) {
                    out.println("    File(\"external_jvm_dependencies\").printWriter().use { out ->")
                    out.println("        for (f in configurations.getByName(\"jvmRuntimeClasspath\").resolve()) {")
                    out.println("            if (!\"\$f\".contains(\"luposdate3000\")) {")
                    out.println("                out.println(\"\$f\")")
                    out.println("            }")
                    out.println("        }")
                    out.println("    }")
                }
                if (enableJS) {
                    out.println("    File(\"external_js_dependencies\").printWriter().use { out ->")
                    out.println("        for (f in configurations.getByName(\"jsRuntimeClasspath\").resolve()) {")
                    out.println("            if (!\"\$f\".contains(\"luposdate3000\")) {")
                    out.println("                out.println(\"\$f\")")
                    out.println("            }")
                    out.println("        }")
                    out.println("    }")
                }
                out.println("}")
                if (!onWindows) {
                    out.println("tasks.register<proguard.gradle.ProGuardTask>(\"proguard\") {")
                    out.println("    dependsOn(\"mydependencies\")")
                    out.println("    injars(\"build/libs/${moduleArgs.moduleName}-jvm-0.0.1.jar\")")
                    out.println("    outjars(\"build/libs/${moduleArgs.moduleName}-jvm-0.0.1-pro.jar\")")
                    out.println("    val javaHome = System.getProperty(\"java.home\")")
                    out.println("    if (System.getProperty(\"java.version\").startsWith(\"1.\")) {")
                    out.println("        libraryjars(\"\$javaHome/lib/rt.jar\")")
                    out.println("    } else {")
                    out.println("        libraryjars(")
                    out.println("            mapOf(")
                    out.println("                \"jarfilter\" to \"!**.jar\",")
                    out.println("                \"filter\" to \"!module-info.class\"")
                    out.println("            ),")
                    out.println("            \"\$javaHome/jmods/java.base.jmod\"")
                    out.println("        )")
                    out.println("    }")
                    out.println("    File(\"external_jvm_dependencies\").printWriter().use { out ->")
                    out.println("        for (f in configurations.getByName(\"jvmRuntimeClasspath\").resolve()) {")
                    out.println("            if (!\"\$f\".contains(\"luposdate3000\")) {")
                    out.println("                out.println(\"\$f\")")
                    out.println("            }")
                    out.println("        }")
                    out.println("    }")
                    out.println("    for (f in configurations.getByName(\"jvmCompileClasspath\").resolve()) {")
                    out.println("        libraryjars(files(\"\$f\"))")
                    out.println("    }")
                    out.println("    printusage(\"usage.pro\")")
                    out.println("    forceprocessing()")
                    out.println("    optimizationpasses(5)")
                    out.println("    allowaccessmodification()")
                    out.println("    dontobfuscate()")
                    out.println("    printconfiguration(\"config.pro.generated\")")
                    out.println("    printmapping(\"build/mapping.txt\")")
                    out.println("    keep(\"@lupos.ProguardKeepAnnotation public class *\")")
                    out.println("    keepclassmembers(\"class * { @lupos.ProguardKeepAnnotation public *; }\")")
                    out.println("    keepclassmembers(\"class * { public <fields>; }\")")
                    out.println("    keep(\"public class MainKt { public static void main(java.lang.String[]); }\")")
                    out.println("}")
                }
            }
        }
        if (moduleArgs.ideaBuildfile == IntellijMode.Disable) {
            File("src.generated${pathSeparator}commonMain${pathSeparator}kotlin${pathSeparator}lupos${pathSeparator}shared$pathSeparator").mkdirs()
        }
        val typeAliasAll = mutableMapOf<String, Pair<String, String>>()
        val typeAliasUsed = mutableMapOf<String, Pair<String, String>>()
        val packageToUseForConfig: String
        if (renameSharedInline) {
            packageToUseForConfig = "lupos.${moduleArgs.moduleName}"
        } else {
            packageToUseForConfig = "lupos.shared_inline"
        }
        if (moduleArgs.releaseMode == ReleaseMode.Enable) {
            typeAliasAll["SanityCheck"] = Pair("SanityCheck", "$packageToUseForConfig.SanityCheckOff")
        } else {
            typeAliasAll["SanityCheck"] = Pair("SanityCheck", "$packageToUseForConfig.SanityCheckOn")
        }
        if (moduleArgs.suspendMode == SuspendMode.Enable) {
            typeAliasAll["Parallel"] = Pair("Parallel", "$packageToUseForConfig.ParallelCoroutine")
            typeAliasAll["ParallelJob"] = Pair("ParallelJob", "$packageToUseForConfig.ParallelCoroutineJob")
            typeAliasAll["ParallelCondition"] = Pair("ParallelCondition", "$packageToUseForConfig.ParallelCoroutineCondition")
            typeAliasAll["ParallelQueue"] = Pair("ParallelQueue<T>", "$packageToUseForConfig.ParallelCoroutineQueue<T>")
            typeAliasAll["MyLock"] = Pair("MyLock", "$packageToUseForConfig.MyCoroutineLock")
            typeAliasAll["MyReadWriteLock"] = Pair("MyReadWriteLock", "$packageToUseForConfig.MyCoroutineReadWriteLock")
        } else {
            typeAliasAll["Parallel"] = Pair("Parallel", "$packageToUseForConfig.ParallelThread")
            typeAliasAll["ParallelJob"] = Pair("ParallelJob", "lupos.shared.ParallelThreadJob")
            typeAliasAll["ParallelCondition"] = Pair("ParallelCondition", "$packageToUseForConfig.ParallelThreadCondition")
            typeAliasAll["ParallelQueue"] = Pair("ParallelQueue<T>", "$packageToUseForConfig.ParallelThreadQueue<T>")
            typeAliasAll["MyLock"] = Pair("MyLock", "lupos.shared.MyThreadLock")
            typeAliasAll["MyReadWriteLock"] = Pair("MyReadWriteLock", "$packageToUseForConfig.MyThreadReadWriteLock")
        }
// selectively copy classes which are inlined from the inline internal module ->
        val classNamesRegex = Regex("\\s*([a-zA-Z0-9_]*)")
        val classNamesFound = mutableMapOf<String, MutableSet<String>>()
        if (!moduleArgs.moduleName.startsWith("Luposdate3000_Shared_")) {
            for (f in File("src${pathSeparator}luposdate3000_shared_inline${pathSeparator}src").walk()) {
                if (f.isFile()) {
                    try {
                        f.forEachLine { it ->
                            var tmp = ""
                            var idxClass = it.indexOf("class")
                            if (idxClass >= 0) {
                                tmp = it.substring(idxClass + 5)
                            } else {
                                var idxObject = it.indexOf("object")
                                if (idxObject >= 0) {
                                    tmp = it.substring(idxObject + 6)
                                } else {
                                    var idxInterface = it.indexOf("interface")
                                    if (idxInterface >= 0) {
                                        tmp = it.substring(idxInterface + 9)
                                    }
                                }
                            }
                            if (tmp.length > 0) {
                                var tmp2 = classNamesRegex.find(tmp)!!.groupValues[1]
                                if (tmp2.startsWith("_")) {
                                    tmp2 = tmp2.substring(1)
                                }
                                if (tmp2.length > 0) {
                                    val tmp3 = classNamesFound[tmp2]
                                    if (tmp3 == null) {
                                        classNamesFound[tmp2] = mutableSetOf(f.toString())
                                    } else {
                                        tmp3.add(f.toString())
                                    }
                                }
                            }
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                } else {
                    if (moduleArgs.ideaBuildfile == IntellijMode.Disable) {
                        val f2 = File(f.toString().replace("src${pathSeparator}luposdate3000_shared_inline${pathSeparator}src", "src.generated"))
                        f2.mkdirs()
                    }
                }
            }
            if (moduleArgs.ideaBuildfile == IntellijMode.Disable) {
                if (copySelevtively) {
                    var changed = true
                    while (changed) {
                        changed = false
                        val classNamesUsed = mutableMapOf<String, MutableSet<String>>()
                        for (f in File("src.generated").walk()) {
                            if (f.isFile()) {
                                try {
                                    f.forEachLine { line ->
                                        val tmpSet = mutableListOf(line)
                                        val tmpAlias = mutableSetOf<String>()
                                        for ((k, v) in typeAliasAll) {
                                            if (line.indexOf(k) >= 0) {
                                                tmpSet.add(v.second)
                                                typeAliasUsed[k] = v
                                                tmpAlias.add(k)
                                            }
                                        }
                                        for (a in tmpAlias) {
                                            typeAliasAll.remove(a)
                                        }
                                        for (it in tmpSet) {
                                            val tmp = mutableSetOf<String>()
                                            for ((k, v) in classNamesFound) {
                                                if (it.indexOf(k) >= 0) {
                                                    classNamesUsed[k] = v
                                                    tmp.add(k)
                                                    changed = true
                                                }
                                            }
                                            for (k in tmp) {
                                                classNamesFound.remove(k)
                                            }
                                        }
                                    }
                                } catch (e: Throwable) {
                                    e.printStackTrace()
                                }
                            }
                        }
                        for ( v in classNamesUsed.values) {
                            for (fname in v) {
                                val src = File(fname)
                                val dest = File(fname.replace("src${pathSeparator}luposdate3000_shared_inline${pathSeparator}src", "src.generated"))
                                copyFileWithReplacement(src, dest, replacementsDefault, sharedInlineReferences)
                                try {
                                    val src2 = File(fname.replace(".kt", "Alias.kt"))
                                    val dest2 = File(fname.replace("src${pathSeparator}luposdate3000_shared_inline${pathSeparator}src", "src.generated").replace(".kt", "Alias.kt"))
                                    copyFileWithReplacement(src2, dest2, replacementsDefault, sharedInlineReferences)
                                } catch (e: Throwable) {
                                    e.printStackTrace()
                                }
                            }
                        }
                        println(classNamesUsed.keys)
                    }
                } else {
                    typeAliasUsed.putAll(typeAliasAll)
                    copyFilesWithReplacement(("src${pathSeparator}luposdate3000_shared_inline${pathSeparator}src"), "src.generated", replacementsDefault, pathSeparator, sharedInlineReferences)
                }
            } else {
                var configPathBase = "src${pathSeparator}xxx_generated_xxx${pathSeparator}${moduleArgs.moduleFolder}${pathSeparator}src"
                var configPath = "${configPathBase}${pathSeparator}commonMain${pathSeparator}kotlin${pathSeparator}lupos${pathSeparator}shared"
                File(configPath).mkdirs()
                typeAliasUsed.putAll(typeAliasAll)
                try {
                    copyFilesWithReplacement(("src${pathSeparator}luposdate3000_shared_inline${pathSeparator}src${pathSeparator}commonMain"), ("${configPathBase}${pathSeparator}commonMain"), replacementsDefault, pathSeparator, sharedInlineReferences)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
                try {
                    copyFilesWithReplacement(("src${pathSeparator}luposdate3000_shared_inline${pathSeparator}src${pathSeparator}jvmMain"), ("${configPathBase}${pathSeparator}jvmMain"), replacementsDefault, pathSeparator, sharedInlineReferences)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
                try {
                    copyFilesWithReplacement(("src${pathSeparator}luposdate3000_shared_inline${pathSeparator}src${pathSeparator}jsMain"), ("${configPathBase}${pathSeparator}jsMain"), replacementsDefault, pathSeparator, sharedInlineReferences)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
                try {
                    copyFilesWithReplacement(("src${pathSeparator}luposdate3000_shared_inline${pathSeparator}src${pathSeparator}nativeMain"), ("${configPathBase}${pathSeparator}nativeMain"), replacementsDefault, pathSeparator, sharedInlineReferences)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
        var configFile: String
        if (moduleArgs.ideaBuildfile == IntellijMode.Disable) {
            val configPath = "src.generated${pathSeparator}commonMain${pathSeparator}kotlin${pathSeparator}lupos${pathSeparator}shared"
            configFile = "${configPath}${pathSeparator}Config-${moduleArgs.moduleName}.kt"
        } else {
            var configPathBase = "src${pathSeparator}xxx_generated_xxx${pathSeparator}${moduleArgs.moduleFolder}${pathSeparator}src"
            var configPath = "${configPathBase}${pathSeparator}commonMain${pathSeparator}kotlin${pathSeparator}lupos${pathSeparator}shared"
            File(configPath).mkdirs()
            configFile = "${configPath}${pathSeparator}Config-${moduleArgs.moduleName}.kt"
            if (renameSharedInline) {
                val localMap = mutableMapOf<String, MutableSet<String>>()
                for (s in sharedInlineReferences) {
                    if (s.contains(".")) {
                        val pkg = "." + s.substring(0, s.lastIndexOf("."))
                        val name = s.substring(pkg.length)
                        var tmp = localMap[pkg]
                        if (tmp == null) {
                            tmp = mutableSetOf<String>()
                            localMap[pkg] = tmp!!
                        }
                        tmp!!.add(name)
                    } else {
                        var tmp = localMap[""]
                        if (tmp == null) {
                            tmp = mutableSetOf<String>()
                            localMap[""] = tmp!!
                        }
                        tmp!!.add(s)
                    }
                }
                var i = 0
                for ((k, v) in localMap) {
                    File("${configPath}${pathSeparator}SharedInlineHelper-${moduleArgs.moduleName}-$i.kt").printWriter().use { out ->
                        out.println("package lupos.shared_inline$k")
                        for (s in v) {
                            out.println("internal typealias $s = $packageToUseForConfig$k.$s")
                        }
                    }
                    i++
                }
            }
        }
        println(typeAliasUsed.keys)
        println()
        // selectively copy classes which are inlined from the inline internal module <-
        val remainingArgs = mutableMapOf<String, String>()
        remainingArgs.putAll(moduleArgs.args)
        File(configFile).printWriter().use { out ->
            out.println("package lupos.shared")
            for ( v in typeAliasUsed.values) {
                out.println("internal typealias ${v.first} = ${v.second}")
            }
            for (f in listOf("${moduleArgs.moduleFolder}${pathSeparator}configOptions", "src${pathSeparator}luposdate3000_shared_inline${pathSeparator}configOptions")) {
                if (File(f).exists()) {
                    File(f).forEachLine {
                        val opt = it.split(",")
                        if (opt.size == 4) {
                            var value = opt[3]
                            val v = remainingArgs[opt[0]]
                            if (v != null) {
                                value = v
                                remainingArgs.remove(opt[0])
                            }
                            if (opt[1] == "typealias") {
                                out.println("public ${opt[1]} ${opt[0]} = $value")
                            } else {
                                out.println("${opt[1]} ${opt[0]}: ${opt[2]} = $value")
                            }
                        }
                    }
                }
            }
        }
        if (remainingArgs.size > 0) {
            for ((k, v) in remainingArgs) {
                println("unknown argument '$k' = '$v'")
            }
            throw Exception("unknown arguments")
        }
        if (moduleArgs.ideaBuildfile == IntellijMode.Disable) {
            if (moduleArgs.inlineMode == InlineMode.Enable) {
                applyInlineEnable()
            } else {
                applyInlineDisable()
            }
            if (moduleArgs.suspendMode == SuspendMode.Enable) {
                applySuspendEnable()
            } else {
                applySuspendDisable()
            }
        }
        File("gradle.properties").copyTo(File("src.generated${pathSeparator}gradle.properties"))
        if (moduleArgs.dryMode == DryMode.Disable) {
            if (onWindows) {
                var path = System.getProperty("user.dir")
                File("$path${pathSeparator}gradle").copyRecursively(File("$path${pathSeparator}src.generated${pathSeparator}gradle"))
                File("gradlew.bat").copyTo(File("src.generated${pathSeparator}gradlew.bat"))
                if (enableJVM) {
                    runCommand(listOf("gradlew.bat", "mydependencies"), File("$path${pathSeparator}src.generated"))
                } else {
                    runCommand(listOf("gradlew.bat", "mydependencies"), File("$path${pathSeparator}src.generated"))
                }
                runCommand(listOf("gradlew.bat", "publishToMavenLocal"), File("$path${pathSeparator}src.generated"))
            } else if (onLinux) {
                if (enableJVM) {
                    runCommand(listOf("../gradlew", "proguard"), File("src.generated"))
                } else {
                    runCommand(listOf("../gradlew", "mydependencies"), File("src.generated"))
                }
                runCommand(listOf("../gradlew", "publishToMavenLocal"), File("src.generated"))
            }
        }
        try {
            File("build-cache${pathSeparator}bin$appendix").mkdirs()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        try {
            File(srcFolder).deleteRecursively()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        try {
            File(buildFolder).deleteRecursively()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        if (moduleArgs.dryMode == DryMode.Disable) {
            try {
                Files.move(Paths.get("src.generated${pathSeparator}build"), Paths.get(buildFolder))
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        if (moduleArgs.ideaBuildfile == IntellijMode.Disable) {
            try {
                Files.move(Paths.get("src.generated"), Paths.get(srcFolder))
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        if (moduleArgs.dryMode == DryMode.Disable) {
            if (enableJVM) {
                try {
                    Files.copy(Paths.get(buildFolder + "${pathSeparator}libs${pathSeparator}${moduleArgs.moduleName}-jvm-0.0.1.jar"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}${moduleArgs.moduleName}-jvm.jar"), StandardCopyOption.REPLACE_EXISTING)
                    if (!onWindows) {
                        Files.copy(Paths.get(buildFolder + "${pathSeparator}libs${pathSeparator}${moduleArgs.moduleName}-jvm-0.0.1-pro.jar"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}${moduleArgs.moduleName}-jvm-pro.jar"), StandardCopyOption.REPLACE_EXISTING)
                    }
                    Files.copy(Paths.get("$srcFolder${pathSeparator}external_jvm_dependencies"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}${moduleArgs.moduleName}-jvm.classpath"), StandardCopyOption.REPLACE_EXISTING)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (enableJS) {
                if (moduleArgs.modulePrefix == moduleArgs.moduleName) {
                    try {
                        Files.copy(Paths.get(buildFolder + "${pathSeparator}js${pathSeparator}packages${pathSeparator}${moduleArgs.modulePrefix}${pathSeparator}kotlin${pathSeparator}${moduleArgs.modulePrefix}.js"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}${moduleArgs.modulePrefix}.js"), StandardCopyOption.REPLACE_EXISTING)
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                    try {
                        Files.copy(Paths.get(buildFolder + "${pathSeparator}js${pathSeparator}packages${pathSeparator}${moduleArgs.modulePrefix}${pathSeparator}kotlin${pathSeparator}${moduleArgs.modulePrefix}.js.map"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}${moduleArgs.modulePrefix}.js.map"), StandardCopyOption.REPLACE_EXISTING)
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                    Files.copy(Paths.get("$srcFolder${pathSeparator}external_js_dependencies"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}${moduleArgs.moduleName}-js.classpath"), StandardCopyOption.REPLACE_EXISTING)
                } else {
                    File("build-cache${pathSeparator}bin$appendix${pathSeparator}${moduleArgs.moduleName}").mkdirs()
                    try {
                        Files.copy(Paths.get(buildFolder + "${pathSeparator}js${pathSeparator}packages${pathSeparator}${moduleArgs.modulePrefix}${pathSeparator}kotlin${pathSeparator}${moduleArgs.modulePrefix}.js"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}${moduleArgs.moduleName}${pathSeparator}${moduleArgs.modulePrefix}.js"), StandardCopyOption.REPLACE_EXISTING)
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                    try {
                        Files.copy(Paths.get(buildFolder + "${pathSeparator}js${pathSeparator}packages${pathSeparator}${moduleArgs.modulePrefix}${pathSeparator}kotlin${pathSeparator}${moduleArgs.modulePrefix}.js.map"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}${moduleArgs.moduleName}${pathSeparator}${moduleArgs.modulePrefix}.js.map"), StandardCopyOption.REPLACE_EXISTING)
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                    Files.copy(Paths.get("$srcFolder${pathSeparator}external_js_dependencies"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}${moduleArgs.moduleName}${pathSeparator}${moduleArgs.modulePrefix}-js.classpath"), StandardCopyOption.REPLACE_EXISTING)
                }
            }
            if (enableNative) {
                if (moduleArgs.platform == "linuxX64") {
                    try {
                        if (buildLibrary) {
                            if (moduleArgs.releaseMode == ReleaseMode.Enable) {
                                Files.copy(Paths.get(buildFolder + "${pathSeparator}bin${pathSeparator}linuxX64${pathSeparator}releaseShared${pathSeparator}lib${moduleArgs.modulePrefix}.so"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}lib${moduleArgs.moduleName}-linuxX64.so"), StandardCopyOption.REPLACE_EXISTING)
                                Files.copy(Paths.get(buildFolder + "${pathSeparator}bin${pathSeparator}linuxX64${pathSeparator}releaseShared${pathSeparator}lib${moduleArgs.modulePrefix}_api.h"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}lib${moduleArgs.moduleName}-linuxX64.h"), StandardCopyOption.REPLACE_EXISTING)
                            } else {
                                Files.copy(Paths.get(buildFolder + "${pathSeparator}bin${pathSeparator}linuxX64${pathSeparator}debugShared${pathSeparator}lib${moduleArgs.modulePrefix}.so"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}lib${moduleArgs.moduleName}-linuxX64.so"), StandardCopyOption.REPLACE_EXISTING)
                                Files.copy(Paths.get(buildFolder + "${pathSeparator}bin${pathSeparator}linuxX64${pathSeparator}debugShared${pathSeparator}lib${moduleArgs.modulePrefix}_api.h"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}lib${moduleArgs.moduleName}-linuxX64.h"), StandardCopyOption.REPLACE_EXISTING)
                            }
                        } else {
                            if (moduleArgs.releaseMode == ReleaseMode.Enable) {
                                Files.copy(Paths.get(buildFolder + "${pathSeparator}bin${pathSeparator}linuxX64${pathSeparator}releaseExecutable${pathSeparator}${moduleArgs.moduleName}.kexe"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}lib${moduleArgs.moduleName}-linuxX64.kexe"), StandardCopyOption.REPLACE_EXISTING)
                            } else {
                                Files.copy(Paths.get(buildFolder + "${pathSeparator}bin${pathSeparator}linuxX64${pathSeparator}debugExecutable${pathSeparator}${moduleArgs.moduleName}.kexe"), Paths.get("build-cache${pathSeparator}bin$appendix${pathSeparator}lib${moduleArgs.moduleName}-linuxX64.kexe"), StandardCopyOption.REPLACE_EXISTING)
                            }
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }
            }
        }
        for (f in listOf(File(File(srcFolder), "gradle.log"), File(File(srcFolder), "gradle.err"))) {
            if (f.exists()) {
                f.forEachLine {
                    println(it)
                }
            }
        }
    } catch (e: Throwable) {
        e.printStackTrace()
        for (f in listOf(File(File("src.generated"), "gradle.log"), File(File("src.generated"), "gradle.err"))) {
            if (f.exists()) {
                f.forEachLine {
                    println(it)
                }
            }
        }
        throw e
    }
}

public fun runCommand(command: List<String>, workingDir: File) {
    val p = ProcessBuilder(command)
        .directory(workingDir)
        .redirectOutput(Redirect.INHERIT)
        .redirectError(Redirect.INHERIT)
        //        .redirectOutput(Redirect.to(File(File("src.generated"), "gradle.log"))) //this disables the warnings?!?
        //        .redirectError(Redirect.to(File(File("src.generated"), "gradle.err"))) //this disables the warnings?!?
        .start()
    p.waitFor()
    if (p.exitValue() != 0) {
        throw Exception("executing '$command' failed")
    }
}
