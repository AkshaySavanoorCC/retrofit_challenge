// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("com.google.devtools.ksp") version "1.8.0-1.0.8" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0")

    }
}
