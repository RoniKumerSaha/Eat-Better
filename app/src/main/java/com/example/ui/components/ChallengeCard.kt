package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DailyChallengeInfo
import com.example.ui.theme.CleanChallengeBadge
import com.example.ui.theme.CleanPrimary

@Composable
fun ChallengeCard(
  challenge: DailyChallengeInfo,
  isCompleted: Boolean,
  onToggleComplete: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(24.dp))
      .clickable { onToggleComplete() }
      .testTag("daily_challenge_card"),
    shape = RoundedCornerShape(24.dp),
    color = CleanPrimary,
    shadowElevation = 2.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // Left Icon Badge
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(CleanChallengeBadge),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.LocalFireDepartment,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(24.dp)
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      // Center Challenge Text
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "DAILY CHALLENGE",
          style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
          ),
          color = Color.White.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = challenge.title,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
          ),
          color = Color.White
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      // Right Toggle Pill
      Box(
        modifier = Modifier
          .size(28.dp)
          .clip(CircleShape)
          .background(if (isCompleted) Color.White else Color.Transparent)
          .border(2.dp, Color.White.copy(alpha = 0.4f), CircleShape),
        contentAlignment = Alignment.Center
      ) {
        if (isCompleted) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Challenge Complete",
            tint = CleanPrimary,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}

