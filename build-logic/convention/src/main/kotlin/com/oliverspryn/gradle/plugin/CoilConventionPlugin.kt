package com.oliverspryn.gradle.plugin

import com.oliverspryn.gradle.extension.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CoilConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", library("coil"))
                add("implementation", library("coil-network"))
            }
        }
    }
}
