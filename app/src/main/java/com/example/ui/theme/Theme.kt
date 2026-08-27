package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
  primary = SagePrimary,
  onPrimary = OnPrimary,
  primaryContainer = PrimaryContainer,
  onPrimaryContainer = OnPrimaryContainer,
  secondary = OatSecondary,
  onSecondary = OnSecondary,
  secondaryContainer = SecondaryContainer,
  onSecondaryContainer = OnSecondaryContainer,
  tertiary = CreamTertiary,
  onTertiary = OnTertiary,
  tertiaryContainer = TertiaryContainer,
  onTertiaryContainer = OnTertiaryContainer,
  background = SurfaceWarm,
  onBackground = OnSurfaceDark,
  surface = SurfaceWarm,
  onSurface = OnSurfaceDark,
  surfaceVariant = SurfaceVariant,
  onSurfaceVariant = OnSurfaceVariant,
  surfaceContainer = SurfaceContainer,
  surfaceContainerLow = SurfaceContainerLow,
  surfaceContainerLowest = SurfaceContainerLowest,
  surfaceContainerHigh = SurfaceContainerHigh,
  surfaceContainerHighest = SurfaceContainerHighest,
  outline = OutlineColor,
  outlineVariant = OutlineVariant
)

private val DarkColorScheme = darkColorScheme(
  primary = PrimaryFixedDim,
  onPrimary = OnPrimaryFixed,
  primaryContainer = SagePrimary,
  onPrimaryContainer = PrimaryFixed,
  secondary = SecondaryFixedDim,
  onSecondary = OnSecondaryFixed,
  secondaryContainer = OatSecondary,
  onSecondaryContainer = SecondaryFixed,
  tertiary = TertiaryFixedDim,
  onTertiary = OnTertiaryFixed,
  background = OnSurfaceDark,
  onBackground = SurfaceWarm,
  surface = Color(0xFF282522),
  onSurface = SurfaceWarm,
  surfaceVariant = Color(0xFF383430),
  onSurfaceVariant = Color(0xFFC7C1BB),
  surfaceContainer = Color(0xFF33302C),
  surfaceContainerLow = Color(0xFF2E2B27),
  surfaceContainerLowest = Color(0xFF1E1B18),
  outline = Color(0xFF8C8680)
)

@Composable
fun EatBetterTheme(
  darkTheme: Boolean = false,
  content: @Composable () -> Unit
) {
  // Always use the refined clean light color scheme for consistent high contrast and clarity
  MaterialTheme(
    colorScheme = LightColorScheme,
    typography = Typography,
    content = content
  )
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = false,
  content: @Composable () -> Unit
) {
  EatBetterTheme(darkTheme = false, content = content)
}
