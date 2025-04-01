plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "com.example.veterinaryclinic"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.veterinaryclinic"
        minSdk = 24
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.recyclerview)

    //relex
    implementation(libs.circleindicator)

    //navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //retrofit
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)

    //Glide
    implementation(libs.glide)
    kapt("com.github.bumptech.glide:compiler:4.12.0")

    //Paging
    implementation(libs.androidx.paging.runtime.ktx)

    implementation(libs.material)

    implementation(libs.firebase.bom)

    implementation(libs.firebase.crashlytics)

    implementation(libs.firebase.analytics)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.fragment)

    implementation(libs.androidx.room.rxjava2)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    val paging_version = "3.2.1"

    implementation(libs.androidx.paging.runtime.ktx.v321)

    // alternatively - without Android dependencies for tests
    testImplementation(libs.androidx.paging.common.ktx)


    implementation(libs.androidx.swiperefreshlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}