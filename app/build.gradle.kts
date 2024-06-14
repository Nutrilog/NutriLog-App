plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.devToolsKsp)
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("de.undercouch.download")
}

android {
    namespace = "com.nutrilog.app"
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        applicationId = "com.nutrilog.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_BASE_URL", "\"https://capstone-ijhyluyd4q-et.a.run.app/\"")
    }

    tasks.register("printVersionName") {
        doLast {
            val versionName = android.defaultConfig.versionName
            println("v$versionName")
        }
    }

    lint {
        checkReleaseBuilds = false
        abortOnError = true
    }

    buildTypes {
        debug {
            versionNameSuffix = ".dev"
            isDebuggable = true
        }

        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
        viewBinding = true
        buildConfig = true
        mlModelBinding = true
    }

    androidResources {
        noCompress.add("tflite")
    }
}

val ML_DIR by extra("$projectDir/src/main/ml")
val ASSETS_DIR by extra("$projectDir/src/main/assets")

apply {
    from("download_models.gradle")
}

dependencies {
    // UI
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.glide)
    implementation(libs.androidx.activity.activity.ktx)
    implementation(libs.androidx.fragment.fragment.ktx)
    implementation(libs.chip.navigation.bar)
    implementation(libs.powerspinner)
    implementation(libs.circleimageview)
    implementation(libs.aachartcore.kotlin)

    // Kotlin
    implementation(libs.androidx.core.ktx)

    // Log
    implementation(libs.timber)

    // Room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //  Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Coroutines
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    // CameraX
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)

    // Image
    implementation(libs.androidx.exifinterface)

    // ML
    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.gpu)
    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.tensorflow.lite.support)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
