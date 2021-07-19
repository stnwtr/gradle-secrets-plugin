package at.stnwtr.gradle_secrets_plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*

class SecretsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("secrets", SecretsExtension::class.java)

        val properties = Properties()
        val file = project.file("secrets.properties")

        if (file.exists() && file.canRead()) {
            file.inputStream().use { properties.load(it) }
        } else {
            println("File 'secrets.properties' not found.")
        }

        extension.properties = properties
    }
}