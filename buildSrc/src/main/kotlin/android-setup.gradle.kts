plugins {
    id("com.android.library")
    kotlin("android")
}


android {
    compileSdk = Dependencies.Android.COMPILE_SDK
    namespace = "com.myeong.prography_aos"
    defaultConfig {
        minSdk = Dependencies.Android.MIN_SDK
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/main/AndroidManifest.xml")
            res.srcDirs("src/main/res")
        }
    }
}
