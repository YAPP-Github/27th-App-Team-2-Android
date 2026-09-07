plugins {
    alias(libs.plugins.neki.android.library)
    alias(libs.plugins.neki.hilt)
}

android {
    namespace = "com.neki.android.core.analytics"
}

dependencies {
    implementation(libs.amplitude.analytics.android)
    implementation(libs.facebook.core)
    implementation(libs.timber)

    testImplementation(libs.junit)
}
