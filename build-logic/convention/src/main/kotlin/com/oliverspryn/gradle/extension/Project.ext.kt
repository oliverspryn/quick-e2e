package com.oliverspryn.gradle.extension

import com.android.build.api.dsl.AndroidResources
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildFeatures
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.DefaultConfig
import com.android.build.api.dsl.Installation
import com.android.build.api.dsl.ProductFlavor
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.getByType

/**
 * This method effectively assumes that three things must be true before
 * calling:
 * 1. The module's `build.gradle.kts` file applies the appropriate plugin
 *    application or library plugin. In this app, the
 *    [ApplicationConventionPlugin] and [LibraryConventionPlugin] apply the
 *    `android-application` and `android-library` plugins, respectively.
 * 2. If this method is used in either of the above-mentioned plugins, then
 *    (a) they inherit from [BaseModuleConventionPlugin] and (b) they call
 *    `super.apply(target)` after applying either the `android-application`
 *    or `android-library` plugin.
 * 3. Any other plugins which use this method can do so at any time,
 *    assuming that either the application or library plugin is first
 *    applied in the `build.gradle.kts` file before any additional plugins.
 *    Simply speaking, either the [ApplicationConventionPlugin] or
 *    [LibraryConventionPlugin] must be applied first in the `plugins{}`
 *    block.
 */
fun Project.android(
    block: CommonExtension<out BuildFeatures, out BuildType, out DefaultConfig, out ProductFlavor, out AndroidResources, out Installation>.() -> Unit
) = with(this) {
    val isApplicationModule = try {
        extensions.getByType(ApplicationExtension::class.java)
        true
    } catch (e: Exception) {
        false
    }

    val isLibraryModule = try {
        extensions.getByType<LibraryExtension>()
        true
    } catch (e: Exception) {
        false
    }

    val module = if (isApplicationModule) {
        extensions.getByType<ApplicationExtension>()
    } else if (isLibraryModule) {
        extensions.getByType<LibraryExtension>()
    } else {
        throw IllegalStateException("Module type must be either an application module or a library module. Be sure to apply either the AGP application or library plugin before invoking the `android { }` block.")
    }

    with(module) {
        block()
    }
}

fun Project.plugin(alias: String): String = extensions
    .getByType<VersionCatalogsExtension>()
    .named("libs")
    .findPlugin(alias)
    .get()
    .get()
    .pluginId

fun Project.plugins(block: PluginManager.() -> Unit) = with(pluginManager) {
    block()
}

fun Project.library(alias: String) = extensions
    .getByType<VersionCatalogsExtension>()
    .named("libs")
    .findLibrary(alias)
    .get()

fun Project.version(alias: String) = extensions
    .getByType<VersionCatalogsExtension>()
    .named("libs")
    .findVersion(alias)
    .get()
    .toString()
