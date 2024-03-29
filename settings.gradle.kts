pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PROGRAPHY-AOS"
include(":app")
include(":core:ui")
include(":core:utils")
include(":core:database")
include(":core:network")
include(":core:domain")
include(":feature:holder")
include(":feature:photos")
include(":feature:detail")
include(":feature:random")