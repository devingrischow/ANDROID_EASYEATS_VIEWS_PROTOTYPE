plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.android_easyeats_views_prototype"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.android_easyeats_views_prototype"
        minSdk = 26 //ENABLES APP TO BE COMPATIBLE with android phones whose version # is 8 OR HIGHER
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    //Enable View Binding in the app
    buildFeatures {
        viewBinding = true //Allows for views to use Bindings
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-firestore:24.11.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Grid Layout implementation
    implementation("androidx.gridlayout:gridlayout:1.0.0")

    //Navigation and Fragment Implementation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    //Picasso Import
    implementation ("com.squareup.picasso:picasso:2.5.2")


    // ViewModel + Lifecycle components
    val lifecycle_version = "2.7.0"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")


    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")


    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")

    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")

    //Kotlin Co Routines
    val co_routine_version = "1.7.3"
    //Co Routines
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$co_routine_version")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$co_routine_version")

    //CUSTOM RECYCLER VIEW SWIPE DECORATOR
    //from https://www.youtube.com/watch?v=S5e8L7RbCcU
    implementation("com.github.xabaras:RecyclerViewSwipeDecorator:1.4")




}