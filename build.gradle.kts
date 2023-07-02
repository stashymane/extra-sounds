plugins {
    val kotlinVersion: String by System.getProperties()

    java
    kotlin("jvm") version kotlinVersion
    id("fabric-loom") version "1.0-SNAPSHOT"
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
}

val kotlinVersion: String by System.getProperties()
val minecraftVersion: String by project
val yarnVersion: String by project
val loaderVersion: String by project
val fabricVersion: String by project
val fabricKotlinVersion: String by project
val soundCategoriesVersion: String by project

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnVersion:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")

    modImplementation("dev.stashy:MixinSwap:1.0.0-SNAPSHOT")?.let { include(it) }

    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion+kotlin.$kotlinVersion")
    modImplementation("dev.stashy:sound-categories:$soundCategoriesVersion")
    modImplementation("net.devtech:arrp:0.6.7")?.let { include(it) }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(
                mutableMapOf(
                    "version" to project.version,
                    "fabricKotlinVersion" to fabricKotlinVersion,
                    "soundCategoriesVersion" to soundCategoriesVersion
                )
            )
        }
    }
}
