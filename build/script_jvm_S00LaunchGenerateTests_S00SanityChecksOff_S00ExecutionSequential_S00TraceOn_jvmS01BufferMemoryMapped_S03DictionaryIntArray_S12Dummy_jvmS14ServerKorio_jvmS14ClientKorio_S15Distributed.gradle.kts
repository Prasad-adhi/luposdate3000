buildscript {
    repositories {
        jcenter()
        google()
        mavenLocal()
        mavenCentral()
        maven("http://oss.sonatype.org/content/repositories/snapshots")
        maven("https://plugins.gradle.org/m2/")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
        classpath("org.jetbrains.kotlin:kotlin-frontend-plugin:0.0.26")
        classpath("com.moowork.gradle:gradle-node-plugin:1.2.0")
    }
}
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.61"
    application
}
application {
    mainClassName = "MainKt"
}
repositories {
    jcenter()
    google()
    mavenLocal()
    mavenCentral()
    maven("http://dl.bintray.com/kotlin/kotlin-eap-1.2")
    maven("https://kotlin.bintray.com/kotlinx")
}
project.buildDir = file("build/build_jvm_S00LaunchGenerateTests_S00SanityChecksOff_S00ExecutionSequential_S00TraceOn_jvmS01BufferMemoryMapped_S03DictionaryIntArray_S12Dummy_jvmS14ServerKorio_jvmS14ClientKorio_S15Distributed")
dependencies {
    implementation("com.benasher44:uuid:0.0.7")
    implementation("com.soywiz.korlibs.klock:klock:1.7.0")
    implementation("com.soywiz.korlibs.korio:korio:1.9.9-SNAPSHOT")
    implementation("com.soywiz.korlibs.krypto:krypto:1.9.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.70")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-common:1.3.70")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.70")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.70")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
}
sourceSets["main"].java.srcDir("src/commonMain/kotlin")
sourceSets["main"].java.srcDir("src/commonS00ExecutionSequentialMain/kotlin")
sourceSets["main"].java.srcDir("src/commonS00LaunchGenerateTestsMain/kotlin")
sourceSets["main"].java.srcDir("src/commonS00ResultFlowFastMain/kotlin")
sourceSets["main"].java.srcDir("src/commonS00SanityChecksOffMain/kotlin")
sourceSets["main"].java.srcDir("src/commonS00TraceOnMain/kotlin")
sourceSets["main"].java.srcDir("src/commonS01BufferDiskbasedMain/kotlin")
sourceSets["main"].java.srcDir("src/commonS03DictionaryIntArrayMain/kotlin")
sourceSets["main"].java.srcDir("src/commonS05HashMapMain/kotlin")
sourceSets["main"].java.srcDir("src/commonS12DummyMain/kotlin")
sourceSets["main"].java.srcDir("src/commonS15DistributedMain/kotlin")
sourceSets["main"].java.srcDir("src/jvmMain/kotlin")
sourceSets["main"].java.srcDir("src/jvmS01BufferMemoryMappedMain/kotlin")
sourceSets["main"].java.srcDir("src/jvmS14ClientKorioMain/kotlin")
sourceSets["main"].java.srcDir("src/jvmS14ServerKorioMain/kotlin")
