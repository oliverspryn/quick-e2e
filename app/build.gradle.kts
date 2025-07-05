import com.oliverspryn.gradle.BuildConfig

plugins {
    alias(libs.plugins.convention.app)

    alias(libs.plugins.convention.coil)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.navigation)
    alias(libs.plugins.convention.network)
    alias(libs.plugins.convention.rxjava)
}

android {
    namespace = "${BuildConfig.App.NAMESPACE}.sample" // Overrides the namespace from the application convention plugin
}

dependencies {
    implementation(project(":quicke2e"))

    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
}
