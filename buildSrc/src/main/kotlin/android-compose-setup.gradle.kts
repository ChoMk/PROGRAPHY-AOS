plugins {
    id("com.android.library")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Android.Compose.COMPOSE_COMPILER
    }
}
