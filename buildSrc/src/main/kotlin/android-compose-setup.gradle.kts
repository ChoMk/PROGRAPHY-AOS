plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Android.Compose.COMPOSE_COMPILER
    }
}
dependencies{
    implementation(Dependencies.Android.Compose.ui)
    implementation(Dependencies.Android.Compose.foundation)
    implementation(Dependencies.Android.Compose.material)
    implementation(Dependencies.Android.Compose.toolingPreview)
    implementation(Dependencies.Android.Compose.tooling)
    implementation(Dependencies.Android.Compose.viewModel)

}