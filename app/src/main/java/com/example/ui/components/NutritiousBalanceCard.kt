package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.CleanTrack
import com.example.ui.theme.PremiumShapes
import com.example.ui.theme.PremiumShadows
import com.example.ui.theme.premiumShadow

@Composable
fun NutritiousBalanceCard(
  percentage: Int,
  targetPercentage: Int = 80,
  modifier: Modifier = Modifier
) {
  val animatedProgress by animateFloatAsState(
    targetValue = (percentage / 100f).coerceIn(0f, 1f),
    animationSpec = tween(durationMillis = 1100, easing = FastOutSlowInEasing),
    label = "BalanceProgress"
  )

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .premiumShadow(PremiumShadows.CardResting, PremiumShapes.medium)
      .testTag("nutritious_balance_card"),
    shape = PremiumShapes.medium,
    color = CleanSurface
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          // Icon in tinted pill
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(CleanPrimary.copy(alpha = 0.10f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Eco,
              contentDescription = null,
              tint = CleanPrimary,
              modifier = Modifier.size(18.dp)
            )
          }

          Spacer(modifier = Modifier.size(10.dp))

          Text(
            text = stringResource(R.string.nutritious_balance_title),
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 15.sp,
              letterSpacing = (-0.2).sp
            ),
            color = CleanOnSurface
          )
        }

        Text(
          text = stringResource(R.string.nutritious_balance_percent, percentage),
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            letterSpacing = (-0.5).sp
          ),
          color = CleanPrimary
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Premium gradient progress bar with subtle track
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(10.dp)
          .clip(RoundedCornerShape(5.dp))
          .background(CleanTrack.copy(alpha = 0.5f))
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth(animatedProgress)
            .fillMaxHeight()
            .clip(RoundedCornerShape(5.dp))
            .background(
              androidx.compose.ui.graphics.Brush.horizontalGradient(
                colors = listOf(
                  CleanPrimary.copy(alpha = 0.7f),
                  CleanPrimary
                )
              )
            )
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = stringResource(R.string.nutritious_balance_target, targetPercentage),
        style = MaterialTheme.typography.labelSmall.copy(
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium
        ),
        color = CleanOnSurfaceVariant,
        modifier = Modifier.align(Alignment.End)
      )
    }
  }
}