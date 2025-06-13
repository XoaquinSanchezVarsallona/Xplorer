// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.56.1" apply false
}

buildscript {
    dependencies {
        classpath(libs.google.services.v432)
    }
}

subprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin" && requested.name == "kotlin-metadata-jvm") {
                useVersion("0.7.0")
                because("kotlin-metadata-jvm:1.9.10 no está disponible públicamente")
            }
        }
    }
}