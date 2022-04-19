plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.serialization")
    id("kotlin-parcelize")
}

android {
    compileSdk = compile_version
    buildToolsVersion = build_tools_version
    resourcePrefix = "network_"

    defaultConfig {
        minSdk = min_sdk_version
        targetSdk = target_sdk_version
        // versionCode = 1
        // versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    lint {
        isCheckReleaseBuilds = false
        // Or, if you prefer, you can continue to check for errors in release builds,
        // but continue the build even when errors are found:
        isAbortOnError = false
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to arrayOf("*.jar")))
    implementation("androidx.annotation:annotation:1.2.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlin_coroutines_version")
    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("com.squareup.retrofit2:converter-gson:2.7.1")
    implementation("com.squareup.retrofit2:retrofit:2.7.1")
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.3.0")
    implementation("com.squareup.okio:okio:2.10.0")

    testImplementation(project(":func_unitTests"))
    androidTestImplementation(project(":func_androidTests"))
}