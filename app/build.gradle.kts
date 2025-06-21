plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.xplorer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.xplorer"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}
val roomVersion = "2.6.1"

dependencies {
    implementation("androidx.lifecycle:lifecycle-process:2.6.1")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.dagger:hilt-android:2.49")
    implementation(libs.googleid)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.lifecycle.process)
    kapt("com.google.dagger:hilt-android-compiler:2.49")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit:converter-gson:2.0.0-beta2")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")



    implementation("androidx.biometric:biometric:1.2.0-alpha05")

    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("androidx.credentials:credentials:1.2.0-beta01")

    // ... dependencias de prueba ...
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0")
}