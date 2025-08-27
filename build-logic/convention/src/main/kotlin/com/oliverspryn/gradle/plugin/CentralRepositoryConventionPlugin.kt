package com.oliverspryn.gradle.plugin

import com.android.build.api.dsl.LibraryExtension
import com.oliverspryn.gradle.BuildConfig
import com.oliverspryn.gradle.CentralRepositoryConfig
import com.oliverspryn.gradle.delegate.propertyValue
import com.oliverspryn.gradle.extension.plugin
import com.oliverspryn.gradle.extension.plugins
import org.bouncycastle.cms.RecipientId.password
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.authentication.http.BasicAuthentication
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.credentials
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.plugins.signing.SigningExtension
import org.jreleaser.gradle.plugin.JReleaserExtension
import org.jreleaser.model.Active
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.io.path.toPath

class CentralRepositoryConventionPlugin : Plugin<Project> {
    @OptIn(ExperimentalEncodingApi::class) // Base64 encoding is experimental
    override fun apply(target: Project) {
        with(target) {
            plugins {
                apply("maven-publish")
                apply(plugin("jreleaser"))
                apply("signing")
            }

            extensions.configure<LibraryExtension> {
                publishing {
                    singleVariant(BuildConfig.Release.name) {
                        withJavadocJar()
                        withSourcesJar()
                    }
                }
            }

            group = CentralRepositoryConfig.Artifact.GROUP_ID
            version = CentralRepositoryConfig.Artifact.VERSION

            extensions.configure<PublishingExtension> {
                publications {
                    create<MavenPublication>(CentralRepositoryConfig.LIBRARY_NAME) {
                        groupId = CentralRepositoryConfig.Artifact.GROUP_ID
                        artifactId = CentralRepositoryConfig.Artifact.ID
                        version = CentralRepositoryConfig.Artifact.VERSION

                        afterEvaluate {
                            from(components[BuildConfig.Release.name])
                        }

                        pom {
                            name.set(CentralRepositoryConfig.Project.NAME)
                            description.set(CentralRepositoryConfig.Project.DESCRIPTION)
                            url.set(CentralRepositoryConfig.Project.URL)

                            developers {
                                developer {
                                    id.set(CentralRepositoryConfig.Developer.ID)
                                    name.set(CentralRepositoryConfig.Developer.NAME)
                                    url.set(CentralRepositoryConfig.Developer.URL)
                                }
                            }

                            issueManagement {
                                url.set("https://${CentralRepositoryConfig.SCM.URL}/issues")
                            }

                            licenses {
                                license {
                                    name.set(CentralRepositoryConfig.License.NAME)
                                    name.set(CentralRepositoryConfig.License.URL)
                                }
                            }

                            scm {
                                connection.set("scm:git:git://${CentralRepositoryConfig.SCM.URL}.git")
                                developerConnection.set("scm:git:ssh://${CentralRepositoryConfig.SCM.URL}.git")
                                url.set("https://${CentralRepositoryConfig.SCM.URL}")
                            }
                        }
                    }
                }

                repositories {
                    maven {
                        name = "local"
                        url = uri(layout.buildDirectory.dir("staging-deploy"))
                    }
                }
            }

            extensions.configure<SigningExtension> {
                val privateKeyString by propertyValue("GPG_SIGNING_PRIVATE_KEY")
                val keyPassword by propertyValue("GPG_SIGNING_KEY_PASSWORD")

                useInMemoryPgpKeys(privateKeyString, keyPassword)
                sign(extensions.getByType<PublishingExtension>().publications[CentralRepositoryConfig.LIBRARY_NAME])
            }

            extensions.configure<JReleaserExtension> {
                val centralUsername by propertyValue("CENTRAL_REPOSITORY_USERNAME")
                val centralPassword by propertyValue("CENTRAL_REPOSITORY_PASSWORD")

                val publicKeyString by propertyValue("GPG_SIGNING_PUBLIC_KEY")
                val privateKeyString by propertyValue("GPG_SIGNING_PRIVATE_KEY")
                val keyPassword by propertyValue("GPG_SIGNING_KEY_PASSWORD")

                gitRootSearch.set(true)

                signing {
                    active.set(Active.ALWAYS)
                    armored.set(true)

                    publicKey.set(publicKeyString)
                    secretKey.set(privateKeyString)
                    passphrase.set(keyPassword)
                }

                deploy {
                    maven {
                        mavenCentral({
                            register("sonatype") {
                                active.set(Active.ALWAYS)
                                url.set("https://central.sonatype.com/api/v1/publisher")
                                stagingRepository(uri(layout.buildDirectory.dir("staging-deploy")).toPath().toString())

                                username.set(centralUsername)
                                password.set(centralPassword)
                                sign.set(true)
                            }
                        })
                    }
                }
            }
        }
    }
}
