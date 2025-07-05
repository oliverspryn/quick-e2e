package com.oliverspryn.gradle.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.oliverspryn.gradle.BuildConfig
import com.oliverspryn.gradle.extension.plugin
import com.oliverspryn.gradle.extension.plugins
import com.oliverspryn.gradle.plugin.base.BaseModuleConventionPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Requires the application of these Gradle plugins in the root
 * `build.gradle.kts` file:
 * - `id("com.android.application") version <latest> apply false`
 *
 * Also requires these dependencies in the `:build-logic:convention`
 * module's `build.gradle.kts` file:
 *
 *     dependencies {
 *         compileOnly("com.android.tools.build:gradle:<latest>")
 *     }
 */
class ApplicationConventionPlugin : BaseModuleConventionPlugin() {
    override fun apply(target: Project) {
        with(target) {
            plugins {
                apply(plugin("android-application"))
            }

            super.apply(target) // Must call after registering above plugin

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = BuildConfig.App.APPLICATION_ID
                    targetSdk = BuildConfig.Android.TARGET_SDK

                    versionCode = BuildConfig.App.VERSION_CODE
                    versionName = BuildConfig.App.VERSION_NAME
                }

                // Specific modifications for applications to the build types created
                // within the super.apply() call
                buildTypes {
                    BuildConfig.App.BUILD_TYPES.forEach { buildTypeProperties ->
                        getByName(buildTypeProperties.name) {
                            isDebuggable = buildTypeProperties.isDebuggable
                            isShrinkResources = buildTypeProperties.isShrinkResources

                            buildTypeProperties.applicationIdSuffix?.let {
                                applicationIdSuffix = it
                            }

                            buildTypeProperties.signingConfigName?.let {
                                signingConfig = signingConfigs.getByName(it)
                            }
                        }
                    }
                }
            }
        }
    }
}
