package com.oliverspryn.gradle.plugin.model

import com.oliverspryn.gradle.delegate.envVar
import org.gradle.kotlin.dsl.provideDelegate

/**
 * Creates a custom signing configuration which can be used to sign an APK
 * or AAB for a production release.
 *
 * @param name The name of the signing configuration to create. While it
 *     can be anything, it is recommended to use `camelCase` and name it
 *     according to the [CustomBuildType] that it will be used with.
 * @param storeFilePathEnvVar The name of the environment variable which
 *     contains the path to the Java keystore `.jks` file.
 * @param storePasswordEnvVar The name of the environment variable which
 *     contains the password for unlocking the entire Java keystore which
 *     is specified by `storeFilePathEnvVar`.
 * @param keyAliasEnvVar The name of the environment variable which
 *     contains the key alias within the Java keystore specified by
 *     `storeFilePathEnvVar`.
 * @param keyPasswordEnvVar The name of the environment variable which
 *     contains the password for the key specified by `keyAliasEnvVar`.
 */
abstract class SigningConfigs(
    val name: String,
    storeFilePathEnvVar: String,
    storePasswordEnvVar: String,
    keyAliasEnvVar: String,
    keyPasswordEnvVar: String
) {
    val storeFile: String by envVar(storeFilePathEnvVar)
    val storePassword: String by envVar(storePasswordEnvVar)

    val keyAlias: String by envVar(keyAliasEnvVar)
    val keyPassword: String by envVar(keyPasswordEnvVar)
}
