/**
 * @author MyeongKi
 */

object Dependencies {
    object Android {
        const val COMPILE_SDK = 34
        const val MIN_SDK = 26
        private const val MATERIAL = "1.9.0"
        private const val SPLASH = "1.0.0"
        private const val APPCOMPAT = "1.6.1"
        private const val ANDROID_CORE = "1.12.0"
        private const val LIFECYCLE = "2.6.2"
        private const val NAVIGATION = "2.7.2"
        private const val ACTIVITY = "1.7.2"

        object Compose {
            private const val COMPOSE = "1.5.4"
            const val COMPOSE_COMPILER = "1.5.4"
            private const val COMPOSE_PAGING = "3.3.0-alpha02"
            private const val COMPOSE_NAVIGATION = "2.7.6"
            const val foundation = "androidx.compose.foundation:foundation:${COMPOSE}"
            const val runtime = "androidx.compose.runtime:runtime:${COMPOSE}"
            const val ui = "androidx.compose.ui:ui:${COMPOSE}"
            const val material = "androidx.compose.material:material:${COMPOSE}"
            const val activity = "androidx.activity:activity-compose:${ACTIVITY}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${LIFECYCLE}"
            const val animation = "androidx.compose.animation:animation:${COMPOSE}"
            const val tooling = "androidx.compose.ui:ui-tooling:${COMPOSE}"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${COMPOSE}"
            const val paging = "androidx.paging:paging-compose:${COMPOSE_PAGING}"
            const val navigation = "androidx.navigation:navigation-compose:${COMPOSE_NAVIGATION}"
        }

        const val androidCore = "androidx.core:core-ktx:${ANDROID_CORE}"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${LIFECYCLE}"
    }

    object Coil {
        private const val VERSION = "2.5.0"
        const val compose = "io.coil-kt:coil-compose:$VERSION"
    }

    object JetBrains {
        object Kotlin {
            private const val COLLECTIONS_IMMUTABLE_VERSION = "0.3.5"
            private const val SERIALIZATION_VERSION = "1.6.0"
            const val KOTLIN_VERSION = "1.9.20"
            const val collectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:$COLLECTIONS_IMMUTABLE_VERSION"
            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$SERIALIZATION_VERSION"
        }

        object Coroutines {
            private val VERSION get() = "1.7.3"
            val core get() = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$VERSION"
        }

        object Ktor {
            private val VERSION get() = "2.3.6"
            val ktor_clien_core get() = "io.ktor:ktor-client-core:$VERSION"
            val ktor_client_okhttp get() = "io.ktor:ktor-client-okhttp:$VERSION"
            val ktor_client_logging get() = "io.ktor:ktor-client-logging:$VERSION"
            val ktor_client_serialization get() = "io.ktor:ktor-serialization-kotlinx-json:$VERSION"
            val ktor_client_negotiation get() = "io.ktor:ktor-client-content-negotiation:$VERSION"
        }
    }
    object Squareup {
        object SQLDelight {
            const val VERSION = "2.0.0"
            const val androidDriver = "app.cash.sqldelight:android-driver:$VERSION"
            const val sqliteDriver = "app.cash.sqldelight:sqlite-driver:$VERSION"
        }
    }

    object Swipe{
        private const val VERSION = "0.1.0"
        const val swipeCard = "com.alexstyl.swipeablecard:swipeablecard:${VERSION}"
    }
}