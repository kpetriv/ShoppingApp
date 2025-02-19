plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.symbol.processor)
}

android {
    namespace = "com.kirilpetriv.shopping"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kirilpetriv.shopping"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        with(resources.pickFirsts) {
            add("META-INF/LICENSE*")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    // Fix for transitive dependency issue with ByteBuddy (Mockk)
    jvmArgs("-Dnet.bytebuddy.experimental=true")
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.viewmodel)
    implementation(libs.koin.androidx.compose)

    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.test.coroutines)
    testImplementation(libs.kotlin.test.junit5)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlin.test.coroutines)
    androidTestImplementation(libs.kotlin.test.junit5)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.room.test)
}