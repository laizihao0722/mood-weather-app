val room_version = "2.6.1"
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.moodweather"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.moodweather"
        minSdk = 30
        targetSdk = 36
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
    implementation("com.google.android.material:material:1.9.0") // Material Design组件
    implementation("androidx.navigation:navigation-fragment:2.5.3") // 导航组件
    implementation("androidx.navigation:navigation-ui:2.5.3")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0") //图表组件

    // Room 数据库
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // ViewModel 和 LiveData (用于架构组件)
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata:2.7.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}