package com.oliverspryn.gradle.plugin.model

/**
 * Defines the name of custom R8/ProGuard files used during resource
 * shrinking and code minification. Depending on what kind of module
 * that this is applied to (application or library), there are different
 * expectations.
 *
 * **Application Modules:** Only [agpGeneratedRules] and [internalRules]
 * are used, since there isn't another library consuming the main
 * application module.
 *
 * **Library Modules:** All three properties are used since the main
 * application module will consume the library module and expects to know
 * what rules to apply to the library during compilation of the APK or AAB.
 *
 * Whenever you are using a [ProguardFiles] instance in your
 * [CustomBuildType], be sure that all three properties are specified,
 * unless you don't have any library modules. In that case, you can set
 * [libraryConsumerRules] to an empty string or ignore it altogether.
 *
 * That is because regardless of your [CustomBuildType], the
 * [ProguardFiles] configuration will be applied to all modules in the
 * project, whether they are an application or library module. The build
 * tooling will apply the appropriate rules to the appropriate modules.
 *
 * Also keep in mind that these files are expected to be located in
 * the root directory of the module that they are associated with.
 * Conventionally, all modules should use the same file names for
 * consistency.
 *
 * @param agpGeneratedRules The name of the ProGuard file that is generated
 *     by the Android Gradle Plugin. This file is generated at compile time
 *     automatically, and is described in further detail on
 *     [the Android Developer website](https://developer.android.com/build/shrink-code#configuration-files).
 *     Default is "proguard-android-optimize.txt".
 * @param internalRules The name of the ProGuard file that contains custom
 *     rules for your library or application module. Default is
 *     "proguard-rules.pro".
 * @param libraryConsumerRules The name of the ProGuard file that contains
 *     custom rules for the main application module that consumes your
 *     library module. Default is "consumer-rules.pro".
 * @see CustomBuildType
 */
data class ProguardFiles(
    val agpGeneratedRules: String = "proguard-android-optimize.txt",
    val internalRules: String = "proguard-rules.pro",
    val libraryConsumerRules: String = "consumer-rules.pro"
)
