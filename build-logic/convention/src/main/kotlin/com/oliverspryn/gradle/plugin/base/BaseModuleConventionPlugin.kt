package com.oliverspryn.gradle.plugin.base

import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.oliverspryn.gradle.BuildConfig
import com.oliverspryn.gradle.extension.android
import com.oliverspryn.gradle.extension.library
import com.oliverspryn.gradle.extension.plugin
import com.oliverspryn.gradle.extension.plugins
import com.oliverspryn.gradle.plugin.model.CustomBuildType
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

abstract class BaseModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins {
                apply(plugin("kotlin"))
                apply(plugin("kotlin-parcelize"))
            }

            android {
                compileSdk = BuildConfig.Android.COMPILE_SDK
                namespace = BuildConfig.App.NAMESPACE

                defaultConfig {
                    compileSdk = BuildConfig.Android.COMPILE_SDK
                    minSdk = BuildConfig.Android.MIN_SDK

                    vectorDrawables {
                        useSupportLibrary = true
                    }

                    BuildConfig.App.DEFAULT_BUILD_CONFIG_FIELDS.forEach { field ->
                        buildConfigField(
                            field.type,
                            field.name,
                            field.formattedValue
                        )
                    }
                }

                compileOptions {
                    if (BuildConfig.Jvm.ENABLE_DESUGARING) {
                        isCoreLibraryDesugaringEnabled = true
                    }

                    sourceCompatibility = BuildConfig.Jvm.VERSION
                    targetCompatibility = BuildConfig.Jvm.VERSION
                }

                // Use withType to workaround https://youtrack.jetbrains.com/issue/KT-55947
                tasks.withType<KotlinJvmCompile>().configureEach {
                    compilerOptions {
                        allWarningsAsErrors.set(BuildConfig.Jvm.KOTLIN_WARNINGS_AS_ERRORS)
                        jvmTarget.set(BuildConfig.Jvm.VERSION_TARGET)

                        // Without this, we get an R8 error.
                        // From: https://github.com/Kotlin/kotlinx.serialization/issues/2145#issuecomment-1653091753
                        freeCompilerArgs.set(listOf(
                            "-Xstring-concat=inline"
                        ))
                    }
                }

                signingConfigs {
                    BuildConfig.App.SIGNING_CONFIGS.forEach { signingConfig ->
                        create(signingConfig.name) {
                            storeFile = file(signingConfig.storeFile.ifBlank { "/" })
                            keyAlias = signingConfig.keyAlias
                            keyPassword = signingConfig.keyPassword
                            storePassword = signingConfig.storePassword
                        }
                    }
                }

                buildTypes {
                    BuildConfig.App.BUILD_TYPES.forEach { buildTypeProperties ->
                        if (buildTypeProperties.isInheritingFromBuildType) {
                            getByName(buildTypeProperties.name) {
                                setupBuildType(
                                    buildTypes = this@buildTypes,
                                    buildType = this,
                                    commonExtension = this@android,
                                    buildTypeProperties = buildTypeProperties
                                )
                            }
                        } else {
                            create(buildTypeProperties.name) {
                                setupBuildType(
                                    buildTypes = this@buildTypes,
                                    buildType = this,
                                    commonExtension = this@android,
                                    buildTypeProperties = buildTypeProperties
                                )
                            }
                        }
                    }
                }

                buildFeatures {
                    buildConfig = true
                }
            }

            if (BuildConfig.Jvm.ENABLE_DESUGARING) {
                dependencies {
                    add("coreLibraryDesugaring", library("desugaring"))
                }
            }
        }
    }

    private fun setupBuildType(
        buildTypes: NamedDomainObjectContainer<out BuildType>,
        buildType: BuildType,
        commonExtension: CommonExtension<*, *, *, *, *, *>,
        buildTypeProperties: CustomBuildType
    ) = with(buildType) {
        buildTypeProperties.initWithOtherPreExistingBuildType?.let {
            initWith(buildTypes.getByName(it))
        }

        enableUnitTestCoverage = buildTypeProperties.enableUnitTestCoverage
        isMinifyEnabled = buildTypeProperties.isMinifyEnabled

        buildTypeProperties.customBuildConfigFields.forEach { buildConfig ->
            buildConfigField(
                buildConfig.type,
                buildConfig.name,
                buildConfig.formattedValue
            )
        }

        buildTypeProperties.proguardFiles?.let { proguard ->
            proguardFiles(
                commonExtension.getDefaultProguardFile(proguard.agpGeneratedRules),
                proguard.internalRules
            )
        }
    }
}
