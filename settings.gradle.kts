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

rootProject.name = "My Application"
include(":formregister")

include(":calculator_v2")
project(":calculator_v2").projectDir = File(rootDir, "calculator/")
include(":currencyexchange")
include(":inputnumber")
include(":kotlin")
include(":dssv")
include(":gmail")
include(":play-store")
