package com.oliverspryn.gradle.plugin

import com.oliverspryn.gradle.extension.plugin
import com.oliverspryn.gradle.extension.plugins
import com.oliverspryn.gradle.CentralRepositoryConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.dokka.gradle.formats.DokkaPublication

class DokkaConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins {
                apply(plugin("dokka"))
            }

            extensions.configure<DokkaExtension> {
                dokkaPublications.named("html", DokkaPublication::class.java) {
                    failOnWarning.set(true)
                    suppressInheritedMembers.set(true)

                    outputDirectory.set(layout.buildDirectory.dir("dokka/html"))
                }

                moduleName.set(CentralRepositoryConfig.LIBRARY_NAME)
                moduleVersion.set(CentralRepositoryConfig.Artifact.VERSION)
            }
        }
    }
}
