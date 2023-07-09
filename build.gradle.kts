plugins {
    val kotlin_version: String by System.getProperties()
    kotlin("jvm") version kotlin_version
    id("fabric-loom") version "1.3-SNAPSHOT"
}

allprojects {
    group = "dev.stashy.extrasounds"
    version = "3.0-SNAPSHOT"

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.stashy.dev/releases")
        maven("https://jitpack.io")
        maven("https://ueaj.dev/maven")
    }
}

subprojects {
    dependencies {
        pluginManager.withPlugin("fabric-loom") {
            modCompileOnly("dev.stashy:MixinSwap:1.0.0-SNAPSHOT")
        }
    }

    tasks {
        val moduleId by project.extra("moduleId")
        val moduleName by project.extra("moduleName")

        withType<ProcessResources> {
            doFirst {
                val props =
                    listOf(
                        "mod_page",
                        "mod_sources",
                        "mod_issues",
                        "mod_license",
                        "fabric_kotlin_version",
                        "soundcategories_version"
                    )
                        .associateWith { project.properties[it] }
                        .plus(
                            mapOf(
                                "moduleId" to project.extra["moduleId"],
                                "moduleName" to project.extra["moduleName"],
                                "version" to project.version
                            )
                        )

                filesMatching("fabric.mod.json") {
                    expand(props)
                }
            }
        }
    }

}

val kotlin_version: String by System.getProperties()
val minecraft_version: String by project.properties
val yarn_version: String by project.properties
val loader_version: String by project.properties
val fabric_version: String by project.properties
val fabric_kotlin_version: String by project.properties
val soundcategories_version: String by project.properties
val arrp_version: String by project.properties
val mixinswap_version: String by project.properties

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings("net.fabricmc:yarn:$yarn_version:v2")
    modImplementation("net.fabricmc:fabric-loader:$loader_version")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabric_version")

    modImplementation("dev.stashy:MixinSwap:$mixinswap_version")?.let { include(it) }

    modImplementation("net.fabricmc:fabric-language-kotlin:$fabric_kotlin_version+kotlin.$kotlin_version")
    modImplementation("net.devtech:arrp:$arrp_version")?.let { include(it) }

    implementation(project(":shared", "namedElements"))?.let { include(it) }
    implementation(project(":versioned:v1_19", "namedElements"))?.let { include(it) }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
