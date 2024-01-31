plugins {
    id("android-setup")
    kotlin("plugin.serialization")
}
dependencies {
    implementation(project(":core:domain"))
    implementation(Dependencies.JetBrains.Ktor.ktor_clien_core)
    implementation(Dependencies.JetBrains.Ktor.ktor_client_logging)
    implementation(Dependencies.JetBrains.Ktor.ktor_client_serialization)
    implementation(Dependencies.JetBrains.Ktor.ktor_client_negotiation)
    implementation(Dependencies.JetBrains.Ktor.ktor_client_okhttp)
}