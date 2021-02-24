plugins {
    val kotlinVersion = "1.4.30"
    kotlin("multiplatform").version(kotlinVersion).apply(false)
    kotlin("plugin.serialization").version(kotlinVersion).apply(false)
}

allprojects {
    group = "con.github.nexus.actions"
    version = "1.0.0-SNAPSHOT"
}