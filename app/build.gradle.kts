plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android")
}

android {
    namespace = "com.myeong.prography"
    compileSdk = Dependencies.Android.COMPILE_SDK

    defaultConfig {
        applicationId = "com.myeong.prography"
        minSdk = Dependencies.Android.MIN_SDK
        targetSdk = Dependencies.Android.COMPILE_SDK
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Android.Compose.COMPOSE_COMPILER
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:holder"))
    implementation(project(":feature:photos"))
    implementation(project(":feature:detail"))

    implementation(project(":core:network"))
    implementation(project(":core:domain"))

    implementation(Dependencies.Android.androidCore)
    implementation(Dependencies.Android.lifecycle)
    implementation(Dependencies.Android.Compose.activity)
    implementation(Dependencies.Android.Compose.runtime)
    implementation(Dependencies.Android.Compose.foundation)
    implementation(Dependencies.Android.Compose.viewModel)
    implementation(Dependencies.Android.Compose.ui)
    implementation(Dependencies.Android.Compose.material)
    implementation(Dependencies.Android.Compose.toolingPreview)
    implementation(Dependencies.JetBrains.Ktor.ktor_clien_core)
    implementation(project(":core:ui"))

}