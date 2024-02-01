plugins {
    id("android-setup")
    id("app.cash.sqldelight") version Dependencies.Squareup.SQLDelight.VERSION
}
sqldelight {
    databases {
        create("PrographySqlDelightDatabase") {
            packageName.set("com.myeong.prography.database.photo")
        }
    }
}

dependencies {
    implementation(project(":core:domain"))

    implementation(Dependencies.Squareup.SQLDelight.androidDriver)
    implementation(Dependencies.Squareup.SQLDelight.sqliteDriver)
}
