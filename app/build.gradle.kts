plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.gms)
    alias(libs.plugins.secrets)
    alias(libs.plugins.vk.placeholder)
}

android {
    namespace = "com.elpablo.fscslutsky"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.elpablo.fscslutsky"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    secrets {
        propertiesFileName = "secrets.properties"
        defaultPropertiesFileName = "local.properties"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.viewmodel)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity)
    ksp(libs.hilt.compiler)
    //SplashScreen
    implementation(libs.androidx.core.splashscreen)
    //Navigation
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.navigation.hilt)
    //DataStore
    implementation(libs.androidx.datastore)
    //Firebase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.common.ktx)
    implementation(libs.google.firebase.firestore)
    //Coil
    implementation(libs.coil)
    //Google Icons
    implementation(libs.google.icons)
    //AutolinkText
    implementation(libs.autolinktext)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.json)
    //okHTTP3
    implementation(libs.retrofit.okhttp)
    //VK
    implementation(libs.vk.core)
    implementation(libs.vk.api)
    implementation(libs.vk.id)
    implementation(libs.vk.id.support)
    implementation(libs.vk.id.onetap)
    //Media3
    implementation(libs.exoplayer)
    implementation(libs.exoplayer.dash)
    implementation(libs.exoplayer.ui)
    //Glide
    implementation(libs.glide)

    //Desugaring
    coreLibraryDesugaring(libs.desugaring)

    //Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.test)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}