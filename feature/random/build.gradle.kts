
plugins {
    id("android-setup")
    id("android-compose-setup")
}
dependencies{
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))

    implementation(Dependencies.Coil.compose)
    implementation(Dependencies.JetBrains.Kotlin.collectionsImmutable)
    implementation(Dependencies.Swipe.swipeCard)
}