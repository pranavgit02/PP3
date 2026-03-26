package com.example.pp3.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Create a strict, high-contrast dark palette
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF121212), // Deep dark background
    surface = Color(0xFF1E1E1E),
    surfaceVariant = Color(0xFF333333), // Dark grey for AI chat bubbles
    onPrimary = Color(0xFF121212), // Dark text on the user's primary-colored bubble
    onSurface = Color.White,
    onSurfaceVariant = Color.White // Pure white text for the AI chat bubbles
)

@Composable
fun PP3Theme(
    // Hardcode to true to force Dark Mode
    darkTheme: Boolean = true,
    // Hardcode to false to stop Android from using your wallpaper colors
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // Always use the DarkColorScheme
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}