plugins {
    id("android-setup")
    id("android-compose-setup")
}

dependencies {
    implementation(Dependencies.Android.Compose.navigation)
    implementation(project(":core:ui"))
}
