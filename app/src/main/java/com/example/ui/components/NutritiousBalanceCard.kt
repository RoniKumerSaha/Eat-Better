package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CleanBorder
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.CleanTrack

@Composable
fun NutritiousBalanceCard(
  percentage: Int,
  targetPercentage: Int = 80,
  modifier: Modifier = Modifier
) {
  val animatedProgress by animateFloatAsState(
    targetValue = (percentage / 100f).coerceIn(0f, 1f),
    animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
    label = "BalanceProgress"
  )

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .border(1.dp, CleanBorder, RoundedCornerShape(20.dp))
      .testTag("nutritious_balance_card"),
    shape = RoundedCornerShape(20.dp),
    color = CleanSurface
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Eco,
            contentDescription = null,
            tint = CleanPrimary,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = " Nutritious Balance",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 15.sp
            ),
            color = CleanOnSurface
          )
        }

        Text(
          text = "$percentage%",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
          ),
          color = CleanPrimary
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Clean Progress Bar
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(8.dp)
          .clip(RoundedCornerShape(4.dp))
          .background(CleanTrack)
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth(animatedProgress)
            .fillMaxHeight()
            .clip(RoundedCornerShape(4.dp))
            .background(CleanPrimary)
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "Daily Target: $targetPercentage%",
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
        color = CleanOnSurfaceVariant,
        modifier = Modifier.align(Alignment.End)
      )
    }
  }
}

