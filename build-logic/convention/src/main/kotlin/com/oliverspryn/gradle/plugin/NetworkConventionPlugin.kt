package com.oliverspryn.gradle.plugin

import com.oliverspryn.gradle.extension.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class NetworkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", library("moshi"))
                add("implementation", library("moshi-adapters"))
                add("implementation", library("moshi-kotlin"))

                add("implementation", library("retrofit"))
                add("implementation", library("retrofit-moshi"))
                add("implementation", library("retrofit-rxjava"))
            }
        }
    }
}
