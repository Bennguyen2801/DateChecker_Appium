import org.gradle.api.tasks.JavaExec

plugins {
    id("com.android.application") version "8.4.1"
}

android {
    namespace = "com.demo.datechecker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.demo.datechecker"
        minSdk = 21
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

    sourceSets {
        named("main") {
            java.srcDirs("src/main/java")
        }
    }

}


dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.testng.v696)
    implementation(libs.uiautomator.v18)
    implementation(libs.monitor)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.appium.client)
    testImplementation(libs.testng)
    implementation(libs.java.client.v923)
    implementation(libs.java.client.v751)
    implementation(libs.selenium.java)
    testImplementation(libs.testng)
    testImplementation(libs.java.client.v751)
    androidTestImplementation(libs.uiautomator)
    implementation(libs.selenium.java)
    implementation(libs.selenium.support)
    androidTestImplementation(libs.testng)
    implementation(libs.selenium.java)
    androidTestImplementation(libs.testng.v730)
    testImplementation(libs.java.client.v210)
    testImplementation(libs.testng.v696)
}


configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.testng" && requested.name == "testng") {
            useVersion("6.9.6") // Ensure consistent version
            because("We need to enforce a specific version due to dependency constraints")
        }
    }
}

configurations.all {
    resolutionStrategy {
        eachDependency {
            if ((requested.group == "org.jetbrains.kotlin") && (requested.name.startsWith("kotlin-stdlib"))) {
                useVersion("1.8.0")
            }
        }
    }
}

val runAppTaskName = "AppiumAndroidTest"

tasks.register<JavaExec>(runAppTaskName) {
    mainClass.set("test.AppiumAndroidTest")
    classpath = sourceSets["main"].runtimeClasspath
}