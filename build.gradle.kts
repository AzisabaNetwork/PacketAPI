plugins {
    java
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

allprojects {
    apply {
        plugin("java")
        plugin("java-library")
        plugin("maven-publish")
        plugin("com.github.johnrengelman.shadow")
    }

    group = "net.azisaba"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/public/") }
        maven { url = uri("https://repo.azisaba.net/repository/maven-public/") }
    }

    dependencies {
        // annotations
        compileOnly("org.jetbrains:annotations:24.0.1")
    }

    tasks {
        test {
            useJUnitPlatform()
        }

        processResources {
            duplicatesStrategy = DuplicatesStrategy.INCLUDE

            from(sourceSets.main.get().resources.srcDirs) {
                filter(
                    org.apache.tools.ant.filters.ReplaceTokens::class,
                    mapOf("tokens" to mapOf("version" to project.version.toString()))
                )
                filteringCharset = "UTF-8"
            }
        }

        shadowJar {
            exclude("org/slf4j/**")
            relocate("org.jetbrains", "net.azisaba.playersync.lib.org.jetbrains")
            relocate("org.intellij", "net.azisaba.playersync.lib.org.intellij")
        }

        compileJava {
            options.encoding = "UTF-8"
        }
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(8))

        withSourcesJar()
        withJavadocJar()
    }

    publishing {
        repositories {
            maven {
                name = "repo"
                credentials(PasswordCredentials::class)
                url = uri(
                    if (project.version.toString().endsWith("SNAPSHOT"))
                        project.findProperty("deploySnapshotURL") ?: System.getProperty(
                            "deploySnapshotURL",
                            "https://repo.azisaba.net/repository/maven-snapshots/"
                        )
                    else
                        project.findProperty("deployReleasesURL") ?: System.getProperty(
                            "deployReleasesURL",
                            "https://repo.azisaba.net/repository/maven-releases/"
                        )
                )
            }
        }

        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
            }
        }
    }
}
