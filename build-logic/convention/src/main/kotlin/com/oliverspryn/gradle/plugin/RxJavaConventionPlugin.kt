package com.oliverspryn.gradle.plugin

import com.oliverspryn.gradle.extension.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RxJavaConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", library("rxandroid"))
                add("implementation", library("rxjava"))
            }
        }
    }
}
