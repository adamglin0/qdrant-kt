import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.binaryCompatibilityValidator)
    alias(libs.plugins.testBalloon)
   alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidLibrary {
        namespace = "com.adamglin.qdrantkt"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()

        minSdk = libs.versions.androidMinSdk.get().toInt()
        lint.targetSdk = libs.versions.androidTargetSdk.get().toInt()

        compilations.configureEach {
            compilerOptions.configure {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }
    }

    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    macosX64()
    macosArm64()

    js(IR) {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.annotation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.contentnegotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
        }
        commonTest.dependencies {
            implementation(libs.testBalloon.framework.core)
            implementation(kotlin("test"))
            implementation(libs.ktor.client.mock)
        }
    }
}

mavenPublishing {
    coordinates(
        groupId = "com.adamglin",
        artifactId = "qdrant-kt",
        version = "1.0.3"
    )
    pom {
        name.set("qdrant-kt")
        description.set("draw a continuous roundedCornerShape in compose multiplatform.")
        url.set("https://github.com/adamglin0/qdrant-kt")
        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                name.set("adamglin")
                email.set("dev@adamglin.com")
            }
        }
        issueManagement {
            system.set("Github")
            url.set("https://github.com/adamglin0/qdrant-kt/issues")
        }
        scm {
            connection.set("https://github.com/adamglin0/qdrant-kt.git")
            url.set("https://github.com/adamglin0/qdrant-kt")
        }
    }
    publishToMavenCentral(true)
    signAllPublications()
}
