@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../dependencies/libs.versions.toml"))
        }
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}