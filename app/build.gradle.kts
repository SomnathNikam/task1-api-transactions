plugins {
    id("com.android.application")

}

android {
    namespace = "com.example.apitransactionswithbiometrics"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.apitransactionswithbiometrics"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Retrofit Networking Library
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Biometric Library
    implementation("androidx.biometric:biometric:1.2.0-alpha04")

    // EncryptedSharedPreferences Library
    implementation("androidx.security:security-crypto:1.1.0-alpha03")

    // SDP Library for the Screen Sizing in different devices
    implementation("com.intuit.sdp:sdp-android:1.1.1")

    // Okhttp Library for Logs
    implementation("com.squareup.okhttp3:okhttp:4.12.0") // latest as of Apr 2025
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Room Library for the offline Storage
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

}