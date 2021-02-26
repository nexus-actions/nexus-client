plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    `maven-publish`
    signing
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

val sonatypeUsername: String? = (project.properties["com.github.nexus-actions.sonatype.username"] as String?) ?: System.getenv("NEXUS_ACTIONS_SONATYPE_USERNAME")
val sonatypePassword: String? = (project.properties["com.github.nexus-actions.sonatype.password"] as String?) ?: System.getenv("NEXUS_ACTIONS_SONATYPE_PASSWORD")
val sonatypeStagingRepositoryId: String? = (project.properties["com.github.nexus-actions.sonatype.repositoryId"] as String?) ?: System.getenv("NEXUS_ACTIONS_SONATYPE_REPOSITORY_ID")
if(sonatypeUsername != null && sonatypePassword != null) {
    publishing {
        publications {
            repositories {
                maven {
                    name="ossStaging"
                    val releasesRepoUrl by lazy {
                        sonatypeStagingRepositoryId?.let { id ->
                            uri("https://oss.sonatype.org/service/local/staging/deployByRepositoryId/$id/")
                        } ?: error("Missing parameter: 'com.github.nexus-actions.sonatype.repositoryId'.")
                    }
                    val snapshotsRepoUrl by lazy { uri("https://oss.sonatype.org/content/repositories/snapshots/") }
                    setUrl(provider {
                        if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                    })
                    credentials {
                        username = sonatypeUsername
                        password = sonatypePassword
                    }
                }
            }
        }
    }
} else {
    project.logger.warn("Skipping 'maven-publish' configuration: missing parameter(s) 'com.github.nexus-actions.sonatype.username' / 'com.github.nexus-actions.sonatype.password'.")
}

// Empty javadoc ; TODO replace with Dokka
val javadocJar = tasks.register("javadocJar", Jar::class.java) {
    archiveClassifier.set("javadoc")
}

publishing.publications.withType<MavenPublication>().configureEach {
    artifact(javadocJar)
    pom {
        name.set(project.name)
        description.set("Kotlin/Multiplatform Nexus API consumer")
        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        url.set("https://github.com/nexus-actions/nexus-client")
        issueManagement {
            system.set("Github")
            url.set("https://github.com/nexus-actions/nexus-client/issues")
        }
        scm {
            connection.set("https://github.com/nexus-actions/nexus-client.git")
            url.set("https://github.com/nexus-actions/nexus-client")
        }
        developers {
            developer {
                name.set("Martin Bonnin")
                email.set("martin@mbonnin.net")
            }
            developer {
                name.set("Romain Boisselle")
                email.set("romain@kodein.net")
            }
        }
    }
}

val signingKey: String? = (project.properties["com.github.nexus-actions.signing.key"] as String?) ?: System.getenv("NEXUS_ACTIONS_GPG_PRIVATE_KEY")
val signingPassword: String? = (project.properties["com.github.nexus-actions.signing.password"] as String?) ?: System.getenv("NEXUS_ACTIONS_GPG_PRIVATE_PASSWORD")
if(signingKey != null && signingPassword != null) {
    signing {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
} else{
    project.logger.warn("Skipping 'signing' configuration: missing parameter(s) 'com.github.nexus-actions.signing.key' / 'com.github.nexus-actions.signing.password'.")
}