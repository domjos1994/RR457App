plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.library)
    alias(libs.plugins.kapt.library)
}

android {
    namespace = "de.dojodev.rr457app"
    compileSdk = rootProject.extra["sdk_compile"] as Int

    defaultConfig {
        applicationId = "de.dojodev.rr457app"
        // sdk
        minSdk = rootProject.extra["sdk_min"] as Int
        targetSdk = rootProject.extra["sdk_compile"] as Int

        // app
        versionCode = rootProject.extra["version"] as Int
        versionName = rootProject.extra["version_name"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = rootProject.extra["minify"] as Boolean
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = rootProject.extra["java_jvm"] as JavaVersion
        targetCompatibility = rootProject.extra["java_jvm"] as JavaVersion
    }
    kotlinOptions {
        jvmTarget = rootProject.extra["java_version"] as String
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // projects
    implementation(project(":data"))

    // ui
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraint)
    implementation(libs.navigation.compose)

    // coil
    implementation(libs.coil)
    implementation(libs.coil.svg)

    // preferences
    implementation(libs.compose.preference)
    implementation(libs.compose.preference.datastore)
    implementation(libs.androidx.preference)

    // hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)

    // testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}