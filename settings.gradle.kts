pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "FieldForm"
include(":app")
include(":core:data")
include(":core:domain")
include(":core:di")
include(":core:network")
include(":core:model")
include(":core:local")
include(":feature:visit")
include(":core:navigation")
include(":core:sync")
