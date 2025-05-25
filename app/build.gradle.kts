plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.theempire.fieldform"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.theempire.fieldform"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // database - Room
//    implementation(libs.androidx.room.runtime)
//    ksp(libs.androidx.room.compiler)
//    implementation(libs.androidx.room.ktx)
//    implementation(libs.androidx.room.paging)
//
//    // network - OkHttp + Retrofit
//    implementation(libs.retrofit)

    // serialization
//    implementation(libs.kotlin.serialization)

    // dagger - hilt
    implementation(libs.hilt)
    ksp(libs.hilt.ksp)
    implementation(libs.hilt.navigation)

    // kotlin symbols processor
    implementation(libs.ksp)

    // work manager
    implementation(libs.androidx.hilt.work)

    implementation(project(":feature:visit"))
    implementation(project(":core:di"))
    implementation(project(":core:sync"))

    implementation(project(":core:navigation"))




}