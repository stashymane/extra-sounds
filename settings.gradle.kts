pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net")
    }
}

rootProject.name = "extrasounds"
include(":shared", ":versioned:v1_19")
