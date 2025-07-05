package com.oliverspryn.gradle.plugin

import com.oliverspryn.gradle.extension.library
import com.oliverspryn.gradle.extension.plugin
import com.oliverspryn.gradle.extension.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Requires the application of these Gradle plugins in the root
 * `build.gradle.kts` file:
 * - `id("androidx.navigation.safeargs.kotlin") version <latest> apply
 *   false`
 */
class NavigationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins {
                apply(plugin("navigation-safeargs"))
            }

            dependencies {
                add("implementation", library("navigation-ktx"))
                add("implementation", library("navigation-ui-ktx"))
            }
        }
    }
}
