plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.theempire.fielform.di"
    compileSdk = 35

    defaultConfig {
        minSdk = 27

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Kotlin Serialization
    implementation(libs.kotlin.serialization)

    // di - Dagger - Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.ksp)

    // navigation - Navigation
    implementation(libs.hilt.navigation)
    implementation(libs.navigation.compose)

    // Kotlin Symbols Processor
    implementation(libs.ksp)

    // network - retrofit
    implementation(libs.retrofit)
    implementation(libs.kotlin.json.converter)

    // local modules
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:local"))

    // network - Logger Interceptor
    implementation(libs.logging.interceptor)

    // database - Room ORM
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // sync - WorkManager
    implementation(libs.androidx.hilt.work)
}