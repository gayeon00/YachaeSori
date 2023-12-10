import java.util.Properties

plugins {
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

val localProperties = Properties()
localProperties.load(rootProject.file("local.properties").inputStream())

android {
    namespace = "com.yachae.yachaesori"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yachae.yachaesori"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            localProperties["kakao_native_app_key"] as String
        )
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

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))

    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    //authentication
    implementation("com.google.firebase:firebase-auth-ktx")
    //realtime database
    implementation("com.google.firebase:firebase-database-ktx")
    //cloud storage
    implementation("com.google.firebase:firebase-storage-ktx")
    //functions
    implementation("com.google.firebase:firebase-functions-ktx")
    //카카오 전체 모듈
    implementation("com.kakao.sdk:v2-all:2.18.0")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // lifecycle-viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    implementation("androidx.activity:activity-ktx:1.8.1")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    //hilt
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.49")
    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")

    //viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    //bannerviewpager
    implementation("com.github.zhpanvip:bannerviewpager:3.5.12")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}