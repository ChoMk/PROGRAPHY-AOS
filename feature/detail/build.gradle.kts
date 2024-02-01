
plugins {
    id("android-setup")
    id("android-compose-setup")
}
dependencies{
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(Dependencies.Coil.compose)
    implementation(Dependencies.JetBrains.Kotlin.collectionsImmutable)
}