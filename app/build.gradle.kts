@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-parcelize")
}

android {
    namespace = "com.evjava.apps_info"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.evjava.apps_info"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val compose_version = "1.3.2"

    // ANDROID
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.ui:ui:1.4.0-alpha01")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0-alpha01")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    implementation("androidx.work:work-runtime:2.7.1")
    implementation("androidx.concurrent:concurrent-futures:1.1.0")
    implementation("androidx.preference:preference-ktx:1.2.0")

    // SERIALIZATION
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    // DECOMPOSE
    implementation("com.arkivanov.decompose:decompose:1.0.0-alpha-04-native-compose")
    implementation("com.arkivanov.decompose:extensions-compose-jetpack:0.8.0")

    // FRAMEWORKS
    implementation("com.badoo.reaktive:reaktive:1.2.2")
    implementation("androidx.compose.material:material:1.3.1")

    // DEBUG, TESTS
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")
    testImplementation("junit:junit:4.13.2")

    // LOGGING
    implementation("io.github.aakira:napier:2.6.1")
}