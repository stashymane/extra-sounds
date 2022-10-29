plugins {
    val kotlinVersion: String by System.getProperties()

    java
    kotlin("jvm") version kotlinVersion
    id("fabric-loom") version "1.0-SNAPSHOT"
}

group = "dev.stashy.extrasounds"
version = "3.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.stashy.dev/releases")
    maven("https://jitpack.io")
    maven("https://storage.googleapis.com/devan-maven/")
}

val kotlinVersion: String by System.getProperties()
val minecraftVersion: String by project
val yarnVersion: String by project
val loaderVersion: String by project
val fabricVersion: String by project
val fabricKotlinVersion: String by project
val soundCategoriesVersion: String by project

fun DependencyHandler.includeImplementation(dependencyNotation: Any) {
    modImplementation(dependencyNotation)
    include(dependencyNotation)
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnVersion:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")

    modApi("com.github.Klotzi111:FabricMultiVersionHelper:1.0.1")
    include("com.github.Klotzi111:FabricMultiVersionHelper:1.0.1")

    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion+kotlin.$kotlinVersion")
    includeImplementation("dev.stashy:sound-categories:$soundCategoriesVersion")
    includeImplementation("net.devtech:arrp:0.6.2")
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