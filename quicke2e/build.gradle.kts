import com.oliverspryn.gradle.BuildConfig

plugins {
    alias(libs.plugins.convention.library)

    alias(libs.plugins.convention.centralrepository)
    alias(libs.plugins.convention.dokka)
}

android {
    namespace = BuildConfig.App.NAMESPACE // Overrides the namespace from the library convention plugin
}

dependencies {
    // Uses api() instead of implementation() to expose the libraries to the consumers
    api(libs.appcompat)
}
