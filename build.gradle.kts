import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.google.gms) apply false
    alias(libs.plugins.secrets) apply false
    id("vkid.manifest.placeholders") version "1.1.0" apply true
}

vkidManifestPlaceholders {
    val properties = Properties()
    properties.load(file("secrets.properties").inputStream())
    val clientId = properties["VKIDClientID"] ?: error("")
    val clientSecret = properties["VKIDClientSecret"] ?: error("")
    init(
        clientId = clientId.toString(),
        clientSecret = clientSecret.toString(),
    )
    vkidRedirectHost = "vk.com"
    vkidRedirectScheme = "vk51738670"
    vkidClientId = clientId.toString()
    vkidClientSecret = clientSecret.toString()
}