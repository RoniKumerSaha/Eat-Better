package com.example.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Premium soft-shadow tokens. Two-layer drop shadows (ambient + spot) give a calm,
 * elevated feel similar to modern wellness apps (Calm, Headspace, etc).
 */
@Immutable
data class PremiumShadow(
  val elevation: Dp,
  val ambientColor: Color,
  val spotColor: Color
)

object PremiumShadows {
  /** Subtle lift — for resting cards on list screens. */
  val CardResting = PremiumShadow(
    elevation = 6.dp,
    ambientColor = Color(0x14386B40), // CleanPrimary @ 8% ambient
    spotColor = Color(0x1F386B40)      // CleanPrimary @ 12% spot
  )

  /** Medium lift — for hero cards like the daily score container. */
  val CardHero = PremiumShadow(
    elevation = 14.dp,
    ambientColor = Color(0x1F386B40),
    spotColor = Color(0x29386B40)
  )

  /** Tiny lift — for inline list rows (logged foods, history items). */
  val CardSubtle = PremiumShadow(
    elevation = 2.dp,
    ambientColor = Color(0x0F386B40),
    spotColor = Color(0x14386B40)
  )

  /** Floating — for FABs, share buttons, overlays. */
  val Floating = PremiumShadow(
    elevation = 10.dp,
    ambientColor = Color(0x29386B40),
    spotColor = Color(0x33386B40)
  )
}

/**
 * Apply a premium two-layer shadow with a clipped rounded shape.
 * Use this for elevated cards instead of Material 3 default `Card`.
 */
@Composable
@ReadOnlyComposable
fun Modifier.premiumShadow(
  shadow: PremiumShadow,
  shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(20.dp),
  clip: Boolean = true
): Modifier {
  val base = this.shadow(
    elevation = shadow.elevation,
    shape = shape,
    ambientColor = shadow.ambientColor,
    spotColor = shadow.spotColor
  )
  return if (clip) base.clip(shape) else base
}