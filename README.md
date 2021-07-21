# Gradle Secrets Plugin

A small plugin which loads an additional properties file for secret values.

## Why

Using this plugin, you can have an additional file (`secrets.properties`) which can hold secret values like API-keys, tokens, private file paths and so on.

There are already two main ways to define secret values:

1. Environment variables and
2. The `gradle.properties` file which is located in `GRADLE_USER_HOME` which defaults to `USER_HOME/.gradle`.

However, environment variables can be annoying to set up for every new project.
Searching, opening and editing the global gradle properties file isn't that fast and simple as well.
It is also impossible to have the same property name with different values in two or more projects.

## Usage

1. Apply the gradle plugin to your project (Kotlin DSL: `id("at.stnwtr.gradle-secrets-plugin") version "1.0.0"`).
2. Create a new file named `secrets.properties` in the project root.
2. Optionally, create a `secrets.example.properties` file which describes the used secrets.
4. Done! Now you can set values in the `secrets.properties` without publishing the file.

## Examples

An example task in the `build.gradle.kts` (Kotlin DSL):

```kotlin
plugins {
    id("at.stnwtr.gradle-secrets-plugin") version "1.0.0"
}

// Throw an exception if the value was not found - else null is returned
secrets.throwIfNotFound = true // Default 'false'

task("sec-test").doLast {
    // Prints the value of 'key' or throws an error
    // because 'secrets.throwIfNotFound' was set to 'true'
    println(secrets.get("key"))

    // Searches in the 'secrets.properties' first
    // If not found in the file, it checks the environment variables
    // If not found in the environment variables an error is thrown
    // because 'secrets.throwIfNotFound' was set to 'true'
    println(secrets.getOrEnv("key"))

    // Tries parsing the result to an integer
    // Throws if the value was not found
    // because 'secrets.throwIfNotFound' was set to 'true'
    println(secrets.get("key") { it?.toInt() })
}
```

The method `secrets.getOrEnv(String)` can be useful because:

1. When developing locally, you don't have to define environment variables but
2. on pushing to a remote and having a CI/CD configured, the secret values can be applied by the CI/CD as environment variables.

You could also set a local environment variable instead of using the `secrets.properties` file, but if more than one project needs different API-Keys for example, it is easier and more convenient to keep the same variable name and not to configure two environment variables `PROJECT_ALPHA_API_KEY` and `PROJECT_BRAVO_API_KEY`.
