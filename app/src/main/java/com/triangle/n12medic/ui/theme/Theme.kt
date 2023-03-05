package com.triangle.n12medic.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = primaryColor,
    onPrimary = Color.White,
    primaryVariant = primaryVariantColor,
    secondary = Teal200,
    background = Color.White,
    error = errorColor,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val LightColorPalette = lightColors(
    primary = primaryColor,
    onPrimary = Color.White,
    primaryVariant = primaryVariantColor,
    secondary = Teal200,
    background = Color.White,
    error = errorColor,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun N12MedicTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = LightColorPalette

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White, true)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}