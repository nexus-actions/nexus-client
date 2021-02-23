plugins {
    kotlin("multiplatform") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.21"
}

group = "com.gitub.nexus.actions"
version = "1.0.0-SNAPSHOT"

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
        val coroutinesVersion = "1.4.2-native-mt"
        val serializationVersion = "1.0.1"
        val ktorVersion = "1.5.1"

        getByName("nativeMain").dependencies {
            // Coroutines
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            // Ktor client
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-curl:$ktorVersion")
            implementation("io.ktor:ktor-client-auth:$ktorVersion")
            implementation("io.ktor:ktor-client-json:$ktorVersion")
            implementation("io.ktor:ktor-client-serialization:$ktorVersion")
            // Serialization
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
        }
    }
}