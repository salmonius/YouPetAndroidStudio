plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.youpet"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.youpet"
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)

    // Dependencias para pruebas unitarias
    testImplementation(libs.junit)

    // Dependencias para pruebas instrumentadas
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation("androidx.test:core:1.5.0")  // Dependencia añadida
    androidTestImplementation("androidx.test:runner:1.5.2")  // Dependencia añadida
}