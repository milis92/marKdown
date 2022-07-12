@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.dokka.plugin) apply false
    alias(libs.plugins.maven.publish.plugin) apply false
}

allprojects {
    //Fail fast if there is no group or properties in gradle.properties
    group = project.property("GROUP_ID")!!
    version = "${project.property("VERSION")!!} ${System.getenv("VERSION_SUFFIX") ?: ""}"
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}