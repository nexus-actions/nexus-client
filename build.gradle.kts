plugins {
    kotlin("multiplatform") version "1.4.30"
    kotlin("plugin.serialization") version "1.4.30"
}

group = "com.gitub.nexus.actions"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    linuxX64 {
        binaries {
            executable()
        }
    }
    macosX64 {
        binaries {
            executable()
        }
    }

    sourceSets {
        val coroutinesVersion = "1.4.2-native-mt"
        val serializationVersion = "1.0.1"
        val ktorVersion = "1.5.1"

        getByName("commonMain").dependencies {
            // Coroutines
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            // Ktor client
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-auth:$ktorVersion")
            implementation("io.ktor:ktor-client-json:$ktorVersion")
            implementation("io.ktor:ktor-client-serialization:$ktorVersion")
            // Serialization
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
        }
        getByName("linuxX64Main").dependencies {
            implementation("io.ktor:ktor-client-curl:$ktorVersion")
        }
        getByName("macosX64Main").dependencies {
            implementation("io.ktor:ktor-client-curl:$ktorVersion")
        }
    }
}