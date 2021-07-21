package at.stnwtr.gradle_secrets_plugin

import java.util.*

open class SecretsExtension {
    var throwIfNotFound = false

    internal lateinit var properties: Properties

    fun get(name: String) =
        properties[name]?.toString() ?: (if (throwIfNotFound) error("Secret '$name' not found!") else null)

    fun <T> get(name: String, transformer: (String?) -> T?) = transformer(get(name))

    fun getOrEnv(name: String): String? =
        properties[name]?.toString() ?: System.getenv(name)
        ?: (if (throwIfNotFound) error("Secret '$name' not found!") else null)

    fun <T> getOrEnv(name: String, transformer: (String?) -> T?) = transformer(getOrEnv(name))
}
