plugins {
    id("android-setup")
    id("android-compose-setup")
}

dependencies {
    implementation(Dependencies.JetBrains.Kotlin.collectionsImmutable)
    implementation(Dependencies.Android.Compose.viewModel)
}
