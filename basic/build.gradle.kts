@file:Suppress("UnstableApiUsage")

import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `kotlin-multiplatform`
    `kotlin-common-conventions`
    alias(libs.plugins.kotlin.dokka.plugin)
    alias(libs.plugins.maven.publish.plugin)
}

kotlin {
    jvm {
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(project.dependencies.enforcedPlatform(testLibs.junit.bom))
                implementation(testLibs.junit.jupiter)
                implementation(testLibs.kotlin.junit)
            }
        }
    }
}

mavenPublishing {
    configure(KotlinMultiplatform(JavadocJar.Dokka("dokkaHtml")))
}

