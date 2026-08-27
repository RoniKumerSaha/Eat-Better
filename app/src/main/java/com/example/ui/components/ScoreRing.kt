package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanTrack
import com.example.ui.theme.ScoreGentleWarm
import com.example.ui.theme.ScoreMidOat

@Composable
fun ScoreRing(
  score: Int,
  statusLabel: String,
  modifier: Modifier = Modifier,
  size: Dp = 150.dp,
  strokeWidth: Dp = 8.dp
) {
  val animatedScoreProgress by animateFloatAsState(
    targetValue = (score / 100f).coerceIn(0f, 1f),
    animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
    label = "ScoreProgress"
  )

  val animatedScoreNumber by animateIntAsState(
    targetValue = score.coerceIn(0, 100),
    animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
    label = "ScoreNumber"
  )

  val activeArcColor = when {
    score >= 70 -> CleanPrimary
    score >= 50 -> ScoreMidOat
    score > 0 -> ScoreGentleWarm
    else -> CleanTrack
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

      // Background Track
      drawCircle(
        color = CleanTrack,
        radius = radius,
        style = Stroke(width = strokePx, cap = StrokeCap.Round)
      )

      // Active Progress Arc
      if (animatedScoreProgress > 0f) {
        drawArc(
          color = activeArcColor,
          startAngle = -90f,
          sweepAngle = 360f * animatedScoreProgress,
          useCenter = false,
          style = Stroke(width = strokePx, cap = StrokeCap.Round)
        )
      }
    }

    // Centered Clean Typography
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(8.dp)
    ) {
      Text(
        text = if (score == 0) "0" else "$animatedScoreNumber",
        style = MaterialTheme.typography.displayLarge.copy(
          fontSize = 44.sp,
          fontWeight = FontWeight.Bold,
          lineHeight = 44.sp
        ),
        color = CleanOnSurface
      )

      Spacer(modifier = Modifier.height(2.dp))

      Text(
        text = "SCORE",
        style = MaterialTheme.typography.labelSmall.copy(
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.5.sp
        ),
        color = CleanOnSurfaceVariant
      )
    }
  }
}

