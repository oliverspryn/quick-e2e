package com.oliverspryn.gradle.delegate

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import org.gradle.api.Project

class GradlePropertyDelegate(
    private val project: Project,
    private val variableName: String? = null
) : ReadOnlyProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        val nameToUse = variableName ?: property.name
        val outcome = project.findProperty(nameToUse) as String?
        return outcome ?: ""
    }
}

/**
 * Gets a Gradle property in one of two ways:
 * ```kotlin
 * val propertyName by propertyValue() // Fetches the Gradle Property value of "propertyName" as a String
 * val property by propertyValue("propertyName") // Fetches the Gradle Property value of "propertyName" as a String
 * ```
 *
 * @param propertyName The name of the property to fetch. If `null`, the
 *     name of the Kotlin variable will be used.
 * @return The value of the Gradle property or an empty string if it is
 *     not set.
 */
fun Project.propertyValue(propertyName: String? = null) =
    GradlePropertyDelegate(this, propertyName)
