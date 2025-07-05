package com.oliverspryn.gradle.plugin

import com.android.build.api.dsl.LibraryExtension
import com.oliverspryn.gradle.BuildConfig
import com.oliverspryn.gradle.CentralRepositoryConfig
import com.oliverspryn.gradle.delegate.propertyValue
import com.oliverspryn.gradle.extension.plugins
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
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class CentralRepositoryConventionPlugin : Plugin<Project> {
    @OptIn(ExperimentalEncodingApi::class) // Base64 encoding is experimental
    override fun apply(target: Project) {
        with(target) {
            plugins {
                apply("maven-publish")
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
                        val releaseUri = uri("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
                        val snapshotUri = uri("https://central.sonatype.com/repository/maven-snapshots/")

                        val centralUsername by propertyValue("CENTRAL_REPOSITORY_USERNAME")
                        val centralPassword by propertyValue("CENTRAL_REPOSITORY_PASSWORD")
                        val base64AuthToken = Base64.Default.encode("$centralUsername:$centralPassword".toByteArray())

                        name = "central"
                        url = if (CentralRepositoryConfig.Artifact.VERSION.endsWith("SNAPSHOT")) snapshotUri else releaseUri

                        credentials(HttpHeaderCredentials::class) {
                            name = "Authorization"
                            value = "Bearer $base64AuthToken"
                        }

                        authentication {
                            create<HttpHeaderAuthentication>("header")
                        }
                    }
                }
            }

            extensions.configure<SigningExtension> {
                val gpgSigningKey by propertyValue("GPG_SIGNING_KEY")
                val gpgSigningKeyPassword by propertyValue("GPG_SIGNING_KEY_PASSWORD")

                useInMemoryPgpKeys(gpgSigningKey, gpgSigningKeyPassword)
                sign(extensions.getByType<PublishingExtension>().publications[CentralRepositoryConfig.LIBRARY_NAME])
            }
        }
    }
}
