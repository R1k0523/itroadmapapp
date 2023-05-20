plugins {
    id("com.android.application") version "8.0.1" apply false
    id("com.android.library") version "8.0.1" apply false
    kotlin("android") version "1.8.20" apply false
    id("com.google.devtools.ksp") version "1.8.20-1.0.11" apply false
    kotlin("plugin.serialization") version "1.8.20" apply false
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
    }
}
task<Delete>("clean") {
    delete(rootProject.buildDir)
}