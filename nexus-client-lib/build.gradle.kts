plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    ios()
    linuxX64()
    macosX64()
    mingwX64()
    js(BOTH) {
        browser()
        nodejs()
    }

    sourceSets {
        val coroutinesVersion = "1.4.2-native-mt"
        val serializationVersion = "1.1.0"
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
    }
}