plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`

}

repositories {
    mavenLocal()
    google()
    mavenCentral()
}
object PluginsVersions {
    const val KOTLIN = "1.9.20"
    const val APPLICATION = "8.2.0"

}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("com.android.tools.build:gradle:${PluginsVersions.APPLICATION}")
}