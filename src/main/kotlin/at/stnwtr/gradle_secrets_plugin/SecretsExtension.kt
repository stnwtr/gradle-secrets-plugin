package at.stnwtr.gradle_secrets_plugin

import java.util.*

open class SecretsExtension {
    internal lateinit var properties: Properties

    fun get(name: String) = properties[name]?.toString() ?: error("Secret '$name' not found!")

    fun <T> get(name: String, transformer: (String) -> T): T {
        return transformer(get(name))
    }
}
