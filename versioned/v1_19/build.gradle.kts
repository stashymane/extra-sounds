plugins {
    id("fabric-loom")
}

val loaderVersion: String by project
val minecraftVersion: String by project
val yarnVersion: String by project

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnVersion:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
}
