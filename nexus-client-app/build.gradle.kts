plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()

    val currentOs = org.gradle.internal.os.OperatingSystem.current()!!
    when {
        currentOs.isMacOsX -> {
            macosX64("native") {
                binaries {
                    executable()
                }
            }
        }
        currentOs.isLinux -> {
            linuxX64("native") {
                binaries {
                    executable()
                }
            }
        }
        else -> error("Operating system ${currentOs.name} not supported.")
    }


    sourceSets {
        getByName("nativeMain").dependencies {
            implementation(project(":nexus-client-lib"))
            implementation("io.ktor:ktor-client-curl:1.5.1")
        }
    }
}