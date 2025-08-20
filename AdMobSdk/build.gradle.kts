import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

android.buildFeatures.buildConfig = true

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

val localProps = Properties().apply {
    val localFile = rootProject.file("local.properties")
    if (localFile.exists()) {
        load(localFile.inputStream())
    }
}

android {
    namespace = "com.example.admobsdk"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val admobInterstitial = localProps.getProperty("ADMOB_INTERSTITIAL_ID") as String
        buildConfigField("String", "ADMOB_INTERSTITIAL_ID", "\"$admobInterstitial\"")

        val admobNative = localProps.getProperty("ADMOB_NATIVE_AD") as String
        buildConfigField("String", "ADMOB_NATIVE_AD", "\"$admobNative\"")

        val admobApId = localProps.getProperty("ADMOB_APP_ID") as String
        manifestPlaceholders["ADMOB_APP_ID"] = admobApId
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

dependencies {
    implementation(project(":AdSdkApi"))

    implementation(libs.play.services.ads)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}