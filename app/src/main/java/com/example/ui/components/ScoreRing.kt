package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanTrack
import com.example.ui.theme.PremiumGradients
import com.example.ui.theme.ScoreGentleWarm
import com.example.ui.theme.ScoreMidOat

@Composable
fun ScoreRing(
  score: Int,
  statusLabel: String,
  modifier: Modifier = Modifier,
  size: Dp = 150.dp,
  strokeWidth: Dp = 10.dp
) {
  val animatedScoreProgress by animateFloatAsState(
    targetValue = (score / 100f).coerceIn(0f, 1f),
    animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
    label = "ScoreProgress"
  )

  val animatedScoreNumber by animateIntAsState(
    targetValue = score.coerceIn(0, 100),
    animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
    label = "ScoreNumber"
  )

  val (activeBrush, glowColor) = when {
    score >= 70 -> PremiumGradients.ScoreHigh to CleanPrimary
    score >= 50 -> PremiumGradients.ScoreMid to ScoreMidOat
    score > 0 -> Brush.verticalGradient(listOf(ScoreGentleWarm, ScoreGentleWarm)) to ScoreGentleWarm
    else -> Brush.verticalGradient(listOf(CleanTrack, CleanTrack)) to CleanTrack
  }

  Box(
    modifier = modifier
      .size(size)
      .testTag("score_ring_hero"),
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.size(size)) {
      val strokePx = strokeWidth.toPx()
      val radius = (size.toPx() - strokePx) / 2f

      // Background Track (light, gentle)
      drawCircle(
        color = CleanTrack.copy(alpha = 0.45f),
        radius = radius,
        style = Stroke(width = strokePx, cap = StrokeCap.Round)
      )

      // Active Progress Arc with gradient
      if (animatedScoreProgress > 0f) {
        drawArc(
          brush = activeBrush,
          startAngle = -90f,
          sweepAngle = 360f * animatedScoreProgress,
          useCenter = false,
          topLeft = androidx.compose.ui.geometry.Offset(strokePx / 2f, strokePx / 2f),
          size = androidx.compose.ui.geometry.Size(size.toPx() - strokePx, size.toPx() - strokePx),
          style = Stroke(width = strokePx, cap = StrokeCap.Round)
        )
      }
    }

    // Subtle inner disc for depth
    Box(
      modifier = Modifier
        .size(size - strokeWidth * 2 - 8.dp)
        .clip(CircleShape)
        .background(glowColor.copy(alpha = 0.05f)),
      contentAlignment = Alignment.Center
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
      ) {
        Text(
          text = stringResource(R.string.score_ring_value, animatedScoreNumber),
          style = MaterialTheme.typography.displayLarge.copy(
            fontSize = 46.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 46.sp,
            letterSpacing = (-1).sp
          ),
          color = CleanOnSurface
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = stringResource(R.string.score_label),
          style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
          ),
          color = CleanOnSurfaceVariant
        )
      }
    }
  }
}