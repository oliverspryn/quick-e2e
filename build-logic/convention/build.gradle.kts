import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    `kotlin-dsl`
}

group = "com.oliverspryn.gradle"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on the device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    compileOnly(libs.agp)
    compileOnly(libs.dokka)
    compileOnly(libs.kotlin)
    compileOnly(libs.jreleaser)
}

gradlePlugin {
    plugins {
        register("app") {
            id = "convention.app"
            implementationClass = "com.oliverspryn.gradle.plugin.ApplicationConventionPlugin"
        }

        register("centralrepository") {
            id = "convention.centralrepository"
            implementationClass = "com.oliverspryn.gradle.plugin.CentralRepositoryConventionPlugin"
        }

        register("coil") {
            id = "convention.coil"
            implementationClass = "com.oliverspryn.gradle.plugin.CoilConventionPlugin"
        }

        register("dokka") {
            id = "convention.dokka"
            implementationClass = "com.oliverspryn.gradle.plugin.DokkaConventionPlugin"
        }

        register("hilt") {
            id = "convention.hilt"
            implementationClass = "com.oliverspryn.gradle.plugin.HiltConventionPlugin"
        }

        register("library") {
            id = "convention.library"
            implementationClass = "com.oliverspryn.gradle.plugin.LibraryConventionPlugin"
        }

        register("navigation") {
            id = "convention.navigation"
            implementationClass = "com.oliverspryn.gradle.plugin.NavigationConventionPlugin"
        }

        register("network") {
            id = "convention.network"
            implementationClass = "com.oliverspryn.gradle.plugin.NetworkConventionPlugin"
        }

        register("rxjava") {
            id = "convention.rxjava"
            implementationClass = "com.oliverspryn.gradle.plugin.RxJavaConventionPlugin"
        }
    }
}
