import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.konan.properties.hasProperty
import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    id("com.github.gmazzo.buildconfig")
}

buildConfig {
    useKotlinOutput()

    val buildConfig: java.util.Properties by lazy {
        loadProperties(rootDir.resolve("build_config.properties").path)
    }
    buildConfig
        .stringPropertyNames()
        .stream()
        .forEach { key ->
            buildConfigField("String", key, "\"${buildConfig.getProperty(key)}\"")
        }
}