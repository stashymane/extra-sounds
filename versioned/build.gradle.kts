subprojects {
    tasks {
        withType<ProcessResources> {
            from("${rootProject.projectDir}/versioned/resources/fabric.mod.json")
        }
    }
}
