plugins {
    kotlin("multiplatform") version "1.6.21"
}

group = "io.github.ae-lite"
version = "1.0.0"

buildscript {
    dependencies {
        classpath("com.strumenta.antlr-kotlin:antlr-kotlin-gradle-plugin:d4e3a446a8")
    }
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "io.github.aelite.koala.main"
            }
        }
    }

    sourceSets {
        val nativeMain by getting {
            dependencies {
                api("com.strumenta.antlr-kotlin:antlr-kotlin-runtime:d4e3a446a8")
                implementation("com.squareup.okio:okio:3.1.0")
                kotlin.srcDir("build/generated/src/nativeMain/kotlin")
            }
        }
        val nativeTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks.getByName("compileKotlinNative").dependsOn("generateAntlrParser")
tasks.register<com.strumenta.antlrkotlin.gradleplugin.AntlrKotlinTask>("generateAntlrParser") {
    antlrClasspath = configurations.detachedConfiguration(
        project.dependencies.create("com.strumenta.antlr-kotlin:antlr-kotlin-target:d4e3a446a8")
    )
    maxHeapSize = "64m"
    packageName = "io.github.aelite.koala.parser.antlr.generated"
    arguments = listOf("-visitor", "-no-listener")
    source = project.objects
        .sourceDirectorySet("antlr", "antlr")
        .srcDir("src/nativeMain/antlr").apply {
            include("*.g4")
        }
    outputDirectory = File("build/generated/src/nativeMain/kotlin")
}
