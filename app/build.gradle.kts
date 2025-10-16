
plugins { id("com.android.application"); kotlin("android") version "2.0.0" }
android {
  namespace = "com.revbridge"; compileSdk = 34
  defaultConfig { applicationId = "com.revbridge"; minSdk = 24; targetSdk = 34; versionCode = 1; versionName = "1.0" }
  buildTypes { release { isMinifyEnabled = false; proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro") }
               debug { isDebuggable = true } }
  buildFeatures { viewBinding = true }
}
dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
  implementation("androidx.core:core-ktx:1.13.1")
  implementation("androidx.appcompat:appcompat:1.7.0")
  implementation("com.google.android.material:material:1.12.0")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("com.squareup.okhttp3:okhttp:4.12.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}