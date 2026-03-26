// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // NEW: This is the missing line!
    // It tells the project exactly which version of the library builder to use for the C++ engine.
    alias(libs.plugins.android.library) apply false
}