plugins {
    kotlin("multiplatform") version "1.6.21"
}

group = "io.github.ae-lite"
version = "1.0.0"

repositories {
    mavenCentral()
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
                implementation("com.squareup.okio:okio:3.1.0")
            }
        }
        val nativeTest by getting
    }
}
