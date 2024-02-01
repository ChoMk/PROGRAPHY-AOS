
plugins {
    id("android-setup")
    id("android-compose-setup")
}
dependencies{
    implementation(project(":core:domain"))
    implementation(Dependencies.Coil.compose)
}