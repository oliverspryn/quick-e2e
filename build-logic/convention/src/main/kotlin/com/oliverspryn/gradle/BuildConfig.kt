package com.oliverspryn.gradle

import com.oliverspryn.gradle.plugin.model.BuildConfigField
import com.oliverspryn.gradle.plugin.model.CustomBuildType
import com.oliverspryn.gradle.plugin.model.SigningConfigs
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object BuildConfig {
    private object DeveloperCoreConfig {
        // region App Version

        // ==================================================================
        // Hey developers! This is where you can configure the app's version.
        // vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv

        const val VERSION_CODE = 1
        const val VERSION_NAME = CentralRepositoryConfig.Artifact.VERSION

        // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        // Make sure to update both of these values before publishing.
        // ==================================================================

        // endregion

        // region SDKs and Supported Android API Versions

        const val COMPILE_SDK = 35
        const val MIN_SDK = 21
        const val TARGET_SDK = COMPILE_SDK

        // endregion

        // region Build Types and Common Build Config Fields

        val BUILD_TYPES = listOf(
            Debug,
            Release
        )

        val DEFAULT_BUILD_CONFIG_FIELDS: List<BuildConfigField> = emptyList()

        // Includes "debug" for free as part of the AGP plugin.
        // Thus, only one for release is needed, if planning to release to the Play Store.
        val SIGNING_CONFIGS: List<SigningConfigs> = emptyList()

        // endregion

        // region Unit Tests

        val UNIT_TEST_IGNORE_PATTERN = listOf(
            // One-offs
            "**/*Activity*.*",
            "**/App.*",

            // Android Files
            "**/BuildConfig*.*",
            "**/R*.*",
            "**/R$*.*",

            // Dagger Hilt
            "**/di/**",
            "**/*_App*.*",
            "**/*_Factory*.*",
            "**/*_GeneratedInjector*.*",
            "**/*_HiltModules*.*",
            "**/*_MembersInjector*.*",
            "**/*Hilt_*.*",

            // Jetpack Compose
            "**/*SampleData*.*",
            "**/*Preview*.*",
            "**/*TestTags*.*"
        )

        // endregion
    }

    // =============================================================================================
    // Everything from above is meant for you to easily configure the app.
    // Your settings from above will percolate down to the rest of the config below.
    //
    // There really shouldn't be any need to change anything below this line.
    // If you do, you're probably changing some of the more fundamental aspects of the app.
    //
    // So, I guess the correct phrases would be:
    //   - "Fool of a Took!" ~ Gandalf
    //   - "Here be dragons." ~ Unknown
    //   - "Live long and prosper." ~ Spock
    //   - "May the force be with you." ~ Obi-Wan Kenobi
    //   - "I have a bad feeling about this." ~ Han Solo
    // =============================================================================================

    object Android {
        const val COMPILE_SDK = DeveloperCoreConfig.COMPILE_SDK
        const val MIN_SDK = DeveloperCoreConfig.MIN_SDK
        const val TARGET_SDK = DeveloperCoreConfig.TARGET_SDK
    }

    object App {
        /**
         * An identifier to uniquely identify the app on a device and on the Google
         * Play Store.
         *
         * Once this identifier is set, it cannot be changed for the lifetime of
         * the app.
         */
        const val APPLICATION_ID = "com.oliverspryn.android.quicke2e"

        /**
         * This is the suffix that will be appended to the [APPLICATION_ID] when
         * the application is built for non-release variants.
         *
         * Effectively becomes: `com.oliverspryn.android.quicke2e.dev`
         */
        const val APPLICATION_ID_NON_RELEASE_SUFFIX = ".dev"

        /**
         * Indicates each of the build types that are available for the
         * application.
         *
         * Any customBuildConfigFields overrides the default build configuration
         * fields, specified in [DEFAULT_BUILD_CONFIG_FIELDS].
         */
        val BUILD_TYPES = DeveloperCoreConfig.BUILD_TYPES

        /**
         * Indicates the default build configuration fields that are available
         * for all build types, unless specifically overridden within the
         * customBuildConfigFields of one of the [BUILD_TYPES].
         */
        val DEFAULT_BUILD_CONFIG_FIELDS = DeveloperCoreConfig.DEFAULT_BUILD_CONFIG_FIELDS

        /**
         * This is effectively the base package name for the application. This
         * should not be confused with the [APPLICATION_ID], which is used to
         * uniquely identify the application on the device and in the Google Play
         * Store.
         *
         * That identifier was set years ago and cannot now be changed, but this
         * namespace has no impact on the app's identifiers.
         */
        const val NAMESPACE = APPLICATION_ID

        /**
         * Indicates each of the custom signing configurations that are available
         * for the application.
         *
         * This is particularly useful for release builds, which require a custom
         * signing key for release to the Google Play Store.
         */
        val SIGNING_CONFIGS = DeveloperCoreConfig.SIGNING_CONFIGS

        /**
         * The version code of the application.
         *
         * Used to uniquely identify the release iteration on the Google Play
         * Store.
         */
        const val VERSION_CODE = DeveloperCoreConfig.VERSION_CODE

        /**
         * The version name of the application.
         *
         * Often following a semantic versioning scheme (X.Y.Z) but is not enforced
         * and can be any identifier or versioning scheme that is useful for the
         * company.
         */
        const val VERSION_NAME = DeveloperCoreConfig.VERSION_NAME

        val UNIT_TEST_IGNORE_PATTERN = DeveloperCoreConfig.UNIT_TEST_IGNORE_PATTERN
    }

    object Jvm {
        const val ENABLE_DESUGARING = true

        const val KOTLIN_WARNINGS_AS_ERRORS = true

        /**
         * Up to Java 11 APIs are available through desugaring:
         * - https://developer.android.com/studio/write/java11-minimal-support-table
         * - https://developer.android.com/studio/write/java8-support
         * - https://developer.android.com/studio/write/java8-support-table
         *
         * However, we only need Java 8 APIs for the time being. We'll update this
         * to Java 11 if and when we need to.
         *
         * Ensure that [ENABLE_DESUGARING] is enabled above for modern APIs to be
         * available.
         *
         * Enabling desugaring and importing the appropriate dependencies seems to
         * cause an R8 warning during a minified build. The details are kept in
         * the following issue: https://issuetracker.google.com/issues/189264383
         */
        val VERSION = JavaVersion.VERSION_1_8

        /** Should be kept in sync with the [VERSION]. */
        val VERSION_TARGET = JvmTarget.JVM_1_8
    }

    // region Build Types

    object Debug : CustomBuildType(
        isInheritingFromBuildType = true,
        name = "debug"
    )

    object Release : CustomBuildType(
        enableUnitTestCoverage = false,
        isInheritingFromBuildType = true,
        name = "release"
    )

    // endregion
}
