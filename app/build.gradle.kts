plugins {

    alias(libs.plugins.android.application)

    alias(libs.plugins.kotlin.compose)

    id("org.jetbrains.kotlin.plugin.serialization")

}


android {

    namespace = "com.example.shohojseba"


    compileSdk {

        version = release(37) {

            minorApiLevel = 1

        }

    }


    defaultConfig {

        applicationId = "com.example.shohojseba"

        minSdk = 24

        targetSdk = 37

        versionCode = 1

        versionName = "1.0"


        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"

    }



    buildTypes {

        release {

            optimization {

                enable = false

            }

        }

    }



    compileOptions {

        sourceCompatibility = JavaVersion.VERSION_11

        targetCompatibility = JavaVersion.VERSION_11

    }



    buildFeatures {

        compose = true

    }

}



dependencies {


    // -------------------------
    // Jetpack Compose
    // -------------------------

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.compose.ui)

    implementation(libs.androidx.compose.ui.graphics)

    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation("androidx.navigation:navigation-compose:2.9.0")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.material:material-icons-extended")


    // -------------------------
    // Android Core + ViewModel
    // -------------------------

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0"
    )



    // -------------------------
    // Supabase Kotlin SDK
    // Auth + Customer insert
    // -------------------------

    implementation(
        "io.github.jan-tennert.supabase:auth-kt:3.2.4"
    )


    implementation(
        "io.github.jan-tennert.supabase:postgrest-kt:3.2.4"
    )



    // -------------------------
    // Retrofit REST API
    // Category / Service fetching
    // -------------------------

    implementation(
        "com.squareup.retrofit2:retrofit:2.11.0"
    )


    implementation(
        "com.squareup.retrofit2:converter-gson:2.11.0"
    )



    // -------------------------
    // OkHttp
    // -------------------------

    implementation(
        "com.squareup.okhttp3:okhttp:4.12.0"
    )


    implementation(
        "com.squareup.okhttp3:logging-interceptor:4.12.0"
    )



    // -------------------------
    // Ktor engine for Supabase
    // -------------------------

    implementation(
        "io.ktor:ktor-client-okhttp:3.0.0"
    )



    // -------------------------
    // Kotlin Serialization
    // -------------------------

    implementation(
        "org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3"
    )



    // -------------------------
    // Testing
    // -------------------------

    testImplementation(libs.junit)


    androidTestImplementation(
        platform(libs.androidx.compose.bom)
    )


    androidTestImplementation(
        libs.androidx.compose.ui.test.junit4
    )


    androidTestImplementation(
        libs.androidx.espresso.core
    )


    androidTestImplementation(
        libs.androidx.junit
    )



    // -------------------------
    // Debug
    // -------------------------

    debugImplementation(
        libs.androidx.compose.ui.test.manifest
    )


    debugImplementation(
        libs.androidx.compose.ui.tooling
    )

}