
plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    google()
    mavenCentral()
}
object PluginsVersions {
    const val KOTLIN = "1.9.20"
    const val APPLICATION = "8.2.0"
    const val SQLDELIGHT = "1.5.5"


}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("com.android.tools.build:gradle:${PluginsVersions.APPLICATION}")
    implementation("com.squareup.sqldelight:gradle-plugin:${PluginsVersions.SQLDELIGHT}")
}