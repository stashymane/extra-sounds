plugins {
    id("fabric-loom")
}

val loader_version: String by project
val minecraft_version: String by project
val yarn_version: String by project
val soundcategories_version: String by project

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings("net.fabricmc:yarn:$yarn_version:v2")
    modImplementation("net.fabricmc:fabric-loader:$loader_version")

    modApi("dev.stashy.soundcategories:soundcategories:$soundcategories_version")
}
