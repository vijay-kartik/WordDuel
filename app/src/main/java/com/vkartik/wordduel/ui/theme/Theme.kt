package com.vkartik.wordduel.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ── M3 colour schemes ─────────────────────────────────────────────────────────

private val DarkColorScheme = darkColorScheme(
    primary = Dark_Primary,
    onPrimary = Dark_OnPrimary,
    primaryContainer = Dark_PrimaryContainer,
    onPrimaryContainer = Dark_OnPrimaryContainer,
    secondary = Dark_Secondary,
    onSecondary = Dark_OnSecondary,
    secondaryContainer = Dark_SecondaryContainer,
    onSecondaryContainer = Dark_OnSecondaryContainer,
    tertiary = Dark_Tertiary,
    onTertiary = Dark_OnTertiary,
    tertiaryContainer = Dark_TertiaryContainer,
    onTertiaryContainer = Dark_OnTertiaryContainer,
    error = Dark_Error,
    onError = Dark_OnError,
    errorContainer = Dark_ErrorContainer,
    onErrorContainer = Dark_OnErrorContainer,
    background = Dark_Background,
    surface = Dark_Surface,
    surfaceContainerLow = Dark_SurfaceContainerLow,
    surfaceContainer = Dark_SurfaceContainer,
    surfaceContainerHigh = Dark_SurfaceContainerHigh,
    surfaceContainerHighest = Dark_SurfaceContainerHighest,
    onSurface = Dark_OnSurface,
    onSurfaceVariant = Dark_OnSurfaceVariant,
    outline = Dark_Outline,
    outlineVariant = Dark_OutlineVariant,
)

private val LightColorScheme = lightColorScheme(
    primary = Light_Primary,
    onPrimary = Light_OnPrimary,
    primaryContainer = Light_PrimaryContainer,
    onPrimaryContainer = Light_OnPrimaryContainer,
    secondary = Light_Secondary,
    onSecondary = Light_OnSecondary,
    secondaryContainer = Light_SecondaryContainer,
    onSecondaryContainer = Light_OnSecondaryContainer,
    tertiary = Light_Tertiary,
    onTertiary = Light_OnTertiary,
    tertiaryContainer = Light_TertiaryContainer,
    onTertiaryContainer = Light_OnTertiaryContainer,
    error = Light_Error,
    onError = Light_OnError,
    errorContainer = Light_ErrorContainer,
    onErrorContainer = Light_OnErrorContainer,
    background = Light_Background,
    surface = Light_Surface,
    surfaceContainerLow = Light_SurfaceContainerLow,
    surfaceContainer = Light_SurfaceContainer,
    surfaceContainerHigh = Light_SurfaceContainerHigh,
    surfaceContainerHighest = Light_SurfaceContainerHighest,
    onSurface = Light_OnSurface,
    onSurfaceVariant = Light_OnSurfaceVariant,
    outline = Light_Outline,
    outlineVariant = Light_OutlineVariant,
)

// ── Game-specific colour tokens ───────────────────────────────────────────────
// Tile states that don't map to standard M3 roles.

@Immutable
data class WordDuelGameColors(
    val tileEmptyBackground: Color,
    val tileEmptyBorder: Color,
    val tileActiveBackground: Color,
    val tileActiveBorder: Color,
    val tileAbsentBackground: Color,
    val tileAbsentContent: Color,
    val tilePresentBackground: Color,
    val tilePresentContent: Color,
    val tileCorrectBackground: Color,
    val tileCorrectContent: Color,
)

private val DarkGameColors = WordDuelGameColors(
    tileEmptyBackground = Dark_SurfaceContainerLow,
    tileEmptyBorder = Dark_Outline,
    tileActiveBackground = Dark_SurfaceContainer,
    tileActiveBorder = Dark_Primary,
    tileAbsentBackground = Dark_SurfaceContainerHighest,
    tileAbsentContent = Dark_OnSurfaceVariant,
    tilePresentBackground = Dark_SecondaryContainer,
    tilePresentContent = Dark_OnSecondaryContainer,
    tileCorrectBackground = Dark_PrimaryContainer,
    tileCorrectContent = Dark_OnPrimaryContainer,
)

private val LightGameColors = WordDuelGameColors(
    tileEmptyBackground = Light_SurfaceContainerLow,
    tileEmptyBorder = Light_Outline,
    tileActiveBackground = Light_SurfaceContainer,
    tileActiveBorder = Light_Primary,
    tileAbsentBackground = Light_SurfaceContainerHighest,
    tileAbsentContent = Light_OnSurfaceVariant,
    tilePresentBackground = Light_SecondaryContainer,
    tilePresentContent = Light_OnSecondaryContainer,
    tileCorrectBackground = Light_PrimaryContainer,
    tileCorrectContent = Light_OnPrimaryContainer,
)

val LocalGameColors = staticCompositionLocalOf { DarkGameColors }

// ── Theme entry point ─────────────────────────────────────────────────────────

@Composable
fun WordDuelTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val gameColors = if (darkTheme) DarkGameColors else LightGameColors

    CompositionLocalProvider(LocalGameColors provides gameColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = WordDuelTypography,
            content = content,
        )
    }
}

// ── Convenience accessor ──────────────────────────────────────────────────────
// Usage: MaterialTheme.gameColors.tileCorrectBackground

val MaterialTheme.gameColors: WordDuelGameColors
    @Composable get() = LocalGameColors.current
