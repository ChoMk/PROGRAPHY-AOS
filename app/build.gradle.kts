plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.myeong.prography_aos"
    compileSdk = Dependencies.Android.COMPILE_SDK

    defaultConfig {
        applicationId = "com.myeong.prography_aos"
        minSdk = Dependencies.Android.MIN_SDK
        targetSdk = Dependencies.Android.COMPILE_SDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    implementation(Dependencies.Android.androidCore)
    implementation(Dependencies.Android.lifecycle)
    implementation(Dependencies.Android.Compose.activity)
    implementation(Dependencies.Android.Compose.runtime)
    implementation(Dependencies.Android.Compose.foundation)
    implementation(Dependencies.Android.Compose.viewModel)
    implementation(Dependencies.Android.Compose.ui)
    implementation(Dependencies.Android.Compose.material)
    implementation(Dependencies.Android.Compose.toolingPreview)

    testImplementation("junit:junit:4.13.2")

}