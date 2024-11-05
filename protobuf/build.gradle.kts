import com.google.protobuf.gradle.proto

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.com.google.protobuf)
}

android {
    namespace = "com.summer.example.protobuf"
    compileSdk = 35

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        externalNativeBuild {
            cmake {
                //cppFlags("")
                arguments("-DPROTO_BASE_DIR=${projectDir}/src/main/proto")
            }
        }
    }

    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/java")
            proto {
                srcDirs("src/main/proto")
            }
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
    externalNativeBuild {
        cmake {
            path("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

protobuf {
    protoc {
        path = "D:\\Tools\\protoc-26.1-win64\\bin\\protoc.exe"

        generateProtoTasks {
            all().forEach { task->
                task.plugins{
                    create("kotlin")
                    create("java")
                }
            }
        }
    }
}
dependencies {
    implementation(libs.protobuf.kotlin)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}