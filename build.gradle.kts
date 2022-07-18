@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.dokka.plugin) apply false
    alias(libs.plugins.maven.publish.plugin) apply false
    alias(libs.plugins.detekt)
}

allprojects {
    // Fail fast if there is no group or properties in gradle.properties
    group = project.property("GROUP_ID")!!
    version = "${project.property("VERSION")!!} ${System.getenv("VERSION_SUFFIX") ?: ""}"
}

val detektFormatting = libs.detekt.formatting

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        config = rootProject.files("config/detekt/detekt.yml")
    }

    dependencies {
        detektPlugins(detektFormatting)
    }
}

tasks.withType(org.gradle.plugins.signing.Sign::class.java).configureEach sign@{
    tasks.withType(org.gradle.api.publish.maven.tasks.AbstractPublishToMaven::class.java).configureEach publish@{
        this@publish.dependsOn(this@sign)
    }
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}