plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.honda"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.example.honda"
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
    androidResources {
        noCompress += "tflite"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }


    buildFeatures {
        viewBinding = true
        mlModelBinding = true
    }



}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.tensorflow.lite.metadata)

    //implementation(libs.tensorflow.lite.support)
    //implementation(libs.tensorflow.lite.support)
    // implementation(libs.tensorflow.lite.support)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("org.tensorflow:tensorflow-lite:2.11.0")
    implementation("org.tensorflow:tensorflow-lite-task-vision:0.3.1")
    implementation ("org.tensorflow:tensorflow-lite-gpu-delegate-plugin:0.3.1")
    implementation ("org.tensorflow:tensorflow-lite-gpu:2.9.0")

    //Camera X

    implementation ("androidx.camera:camera-camera2:1.0.0-beta04")
    implementation ("androidx.camera:camera-lifecycle:1.0.0-beta04")
    implementation ("androidx.camera:camera-view:1.0.0-alpha11")


    //Curvad


}