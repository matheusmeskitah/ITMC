package com.meskitah.itmc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.meskitah.itmc.core.ui.Blue
import com.meskitah.itmc.core.ui.DarkerBlue
import com.meskitah.itmc.core.ui.DarkerRed
import com.meskitah.itmc.core.ui.DarkerYellow
import com.meskitah.itmc.core.ui.LightBlack
import com.meskitah.itmc.core.ui.Red
import com.meskitah.itmc.core.ui.Yellow

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = Color.White,
    secondary = Red,
    tertiary = Yellow,
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkerBlue,
    onPrimary = Color.White,
    secondary = DarkerRed,
    tertiary = DarkerYellow,
    background = LightBlack,
)

@Composable
fun ITMCTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}