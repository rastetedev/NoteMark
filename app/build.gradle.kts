import org.gradle.kotlin.dsl.implementation
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.kotlinx.serialization)
}


android {
    namespace = "com.raulastete.notemark"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.raulastete.notemark"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val securePropertiesFile = project.rootProject.file("secure.properties")
        val properties = Properties()
        properties.load(securePropertiesFile.inputStream())

        val baseUrl = properties.getProperty("BASE_URL") ?: ""
        val authorizationEmail = properties.getProperty("EMAIL") ?: ""

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = baseUrl
            )
            buildConfigField(
                type = "String",
                name = "EMAIL",
                value = authorizationEmail
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
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.timber)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    debugImplementation(libs.androidx.ui.tooling)

    implementation(libs.bundles.koin.compose)
    implementation(libs.bundles.ktor)

    implementation(libs.kotlinx.datetime)

    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.security.crypto)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)
}