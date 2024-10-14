plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp") // Asegúrate de usar la versión correcta
}

android {
    namespace = "com.blas.romanempirecounter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.blas.romanempirecounter"
        minSdk = 26
        targetSdk = 34
        versionCode = 5
        versionName = "1.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // For Glance support
    implementation(libs.androidx.glance)

    // For AppWidgets support
    implementation(libs.androidx.glance.appwidget)

    // For Wear-Tiles support
    implementation(libs.androidx.glance.wear.tiles)

    // For Dagger-Hilt support
    implementation(libs.hilt.android)
    implementation (libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)

    //Extended material icons
    implementation (libs.androidx.material.icons.extended)

    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)

    // To use Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.compiler)

    //usar corrutinas con room
    implementation (libs.room.ktx)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}