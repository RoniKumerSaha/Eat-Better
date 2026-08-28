package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.CleanPillAccent
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.PremiumShapes
import com.example.ui.theme.PremiumShadows
import com.example.ui.theme.premiumShadow

@Composable
fun CoachingCard(
  guidanceMessage: String,
  nextStepAdvice: String,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .premiumShadow(PremiumShadows.CardSubtle, PremiumShapes.medium)
      .testTag("coaching_card"),
    shape = PremiumShapes.medium,
    color = CleanSurface
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp),
      verticalAlignment = Alignment.Top
    ) {
      Box(
        modifier = Modifier
          .size(42.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(CleanPillAccent),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Spa,
          contentDescription = stringResource(R.string.coaching_section_title),
          tint = CleanPrimary,
          modifier = Modifier.size(22.dp)
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = stringResource(R.string.coaching_section_title),
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.5.sp,
            letterSpacing = (-0.2).sp
          ),
          color = CleanOnSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = guidanceMessage,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 13.sp,
            lineHeight = 19.sp
          ),
          color = CleanOnSurfaceVariant
        )

        if (nextStepAdvice.isNotBlank()) {
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = stringResource(R.string.coaching_next_step, nextStepAdvice),
            style = MaterialTheme.typography.bodySmall.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 12.sp,
              letterSpacing = 0.2.sp
            ),
            color = CleanPrimary
          )
        }
      }
    }
  }
}