plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.serialization")
    id("kotlin-parcelize")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(fileTree("dir" to "libs", "include" to arrayOf("*.jar")))
    api("androidx.core:core-ktx:1.6.0")
    api("androidx.appcompat:appcompat:1.3.1")
    api("androidx.activity:activity:1.3.1")
    api("androidx.activity:activity-ktx:1.3.1")
    api("androidx.fragment:fragment:1.3.6")
    api("androidx.fragment:fragment-ktx:1.3.6")
    api("androidx.lifecycle:lifecycle-process:2.3.1")
    api("com.google.android.material:material:1.4.0")

    api("androidx.constraintlayout:constraintlayout:$constraint_layout_version")

    api("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    api("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlin_coroutines_version")
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")
    api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.13.0")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")

    api("com.tencent:mmkv-static:1.2.10")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}