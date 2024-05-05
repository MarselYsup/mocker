pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "mocker-ide"

include( ":mocker")
project(":mocker").projectDir = file("../../mocker")