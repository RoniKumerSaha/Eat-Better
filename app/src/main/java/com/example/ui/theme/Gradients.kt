package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Premium gradient brushes for hero surfaces, score rings, and food tile backgrounds.
 * All brushes are vertical / radial and use the existing sage palette
 * for visual cohesion.
 */
object PremiumGradients {

  /** Sage hero card — top brighter, bottom mintier. */
  val HeroSage = Brush.verticalGradient(
    colors = listOf(
      Color(0xFFEAF5DD), // light sage tint
      Color(0xFFD7E8CD)  // CleanPillAccent
    )
  )

  /** Soft warm gradient for badge / achievement hero. */
  val HeroWarm = Brush.verticalGradient(
    colors = listOf(
      Color(0xFFF7FBF4),
      Color(0xFFE8F3E0)
    )
  )

  /** Premium sage-to-deep for the share card / share dialog visuals. */
  val ShareCardPremium = Brush.linearGradient(
    colors = listOf(
      Color(0xFFF9F6F0),
      Color(0xFFE8F2EC),
      Color(0xFFDDEBD5)
    )
  )

  /** Tile background — gentle sage wash that makes category icons pop. */
  val TileSage = Brush.linearGradient(
    colors = listOf(
      Color(0xFFF2F8EE),
      Color(0xFFE8F3E0)
    )
  )

  /** Tile background — soft warm for snacks / sweet / desserts. */
  val TileWarm = Brush.linearGradient(
    colors = listOf(
      Color(0xFFFBF4EA),
      Color(0xFFF1E7D4)
    )
  )

  /** Tile background — cool aqua for drinks / hydrating foods. */
  val TileCool = Brush.linearGradient(
    colors = listOf(
      Color(0xFFEAF3F7),
      Color(0xFFD5E8EE)
    )
  )

  /** Tile background — neutral for vegetables & savory. */
  val TileNeutral = Brush.linearGradient(
    colors = listOf(
      Color(0xFFF1F3EE),
      Color(0xFFE6E9DD)
    )
  )

  /** Score progress gradient (high score, sage → primary). */
  val ScoreHigh = Brush.sweepGradient(
    colors = listOf(
      Color(0xFF386B40), // CleanPrimary
      Color(0xFF538258), // CleanChallengeBadge
      Color(0xFF386B40)
    )
  )

  /** Score progress gradient (medium score, warm honey → amber). */
  val ScoreMid = Brush.sweepGradient(
    colors = listOf(
      Color(0xFFB58E45), // ScoreMidOat
      Color(0xFFD7B26B),
      Color(0xFFB58E45)
    )
  )
}