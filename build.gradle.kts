plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.dokka)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.navigation.safeargs) apply false
    alias(libs.plugins.jreleaser)
}

tasks.named("clean") {
    dependsOn(gradle.includedBuild("build-logic").task(":convention:clean"))
}
