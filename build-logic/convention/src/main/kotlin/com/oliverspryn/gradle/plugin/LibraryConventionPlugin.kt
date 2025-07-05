package com.oliverspryn.gradle.plugin

import com.android.build.api.dsl.LibraryExtension
import com.oliverspryn.gradle.BuildConfig
import com.oliverspryn.gradle.extension.plugin
import com.oliverspryn.gradle.extension.plugins
import com.oliverspryn.gradle.plugin.base.BaseModuleConventionPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Requires the application of these Gradle plugins in the root
 * `build.gradle.kts` file:
 * - `id("com.android.library") version <latest> apply false`
 *
 * Also requires these dependencies in the `:build-logic:convention`
 * module's `build.gradle.kts` file:
 *
 *     dependencies {
 *         compileOnly("com.android.tools.build:gradle:<latest>")
 *     }
 */
class LibraryConventionPlugin : BaseModuleConventionPlugin() {
    override fun apply(target: Project) {
        with(target) {
            plugins {
                apply(plugin("android-library"))
            }

            super.apply(target) // Must call after registering above plugin

            extensions.configure<LibraryExtension> {
                // The namespace suffix is derived from the module name.
                // So resources inside ":core:module1" must be will be suffixed with "core.module1".
                val namespaceSuffix = path
                    .split("""\W""".toRegex())
                    .drop(1)
                    .distinct()
                    .joinToString(separator = ".")
                    .lowercase()

                namespace = "${BuildConfig.App.NAMESPACE}.$namespaceSuffix"

                // Specific modifications for libraries to the build types created
                // within the super.apply() call
                buildTypes {
                    BuildConfig.App.BUILD_TYPES.forEach { buildTypeProperties ->
                        getByName(buildTypeProperties.name) {
                            buildTypeProperties.signingConfigName?.let {
                                signingConfig = signingConfigs.getByName(it)
                            }

                            buildTypeProperties.proguardFiles?.let { proguard ->
                                consumerProguardFiles(proguard.libraryConsumerRules)
                            }
                        }
                    }
                }
            }
        }
    }
}
