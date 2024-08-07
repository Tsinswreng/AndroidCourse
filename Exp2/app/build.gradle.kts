plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "com.tsinswreng.exp2"
	compileSdk = 34
	
	defaultConfig {
		applicationId = "com.tsinswreng.exp2"
		minSdk = 26
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
}



dependencies {
	
	implementation("androidx.core:core-ktx:1.13.1")
	implementation("androidx.appcompat:appcompat:1.7.0")
	implementation("com.google.android.material:material:1.12.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("com.google.code.gson:gson:2.10") //json库
	//异步网络请求
	implementation("io.ktor:ktor-client-core:2.3.0")
	implementation("io.ktor:ktor-client-cio:2.3.0")
	implementation("io.ktor:ktor-client-serialization:2.3.0")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0")
	
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}