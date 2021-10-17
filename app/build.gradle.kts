plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization")
    id("kotlin-parcelize")
}

android {
    compileSdk = compile_version
    buildToolsVersion = build_tools_version

    defaultConfig {
        applicationId = "com.water.song.template"
        minSdk = min_sdk_version
        targetSdk = target_sdk_version
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to arrayOf("*.jar")))
    implementation(project(":common"))
    implementation(project(":biz_home"))

    testImplementation(project(":func_unitTests"))
    androidTestImplementation(project(":func_androidTests"))
}