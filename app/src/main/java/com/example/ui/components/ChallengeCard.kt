package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.DailyChallengeInfo
import com.example.ui.theme.CleanChallengeBadge
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.PremiumShapes
import com.example.ui.theme.PremiumShadows
import com.example.ui.theme.premiumShadow

@Composable
fun ChallengeCard(
  challenge: DailyChallengeInfo,
  isCompleted: Boolean,
  onToggleComplete: () -> Unit,
  modifier: Modifier = Modifier
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val pressScale by animateFloatAsState(
    targetValue = if (isPressed) 0.98f else 1f,
    animationSpec = tween(140, easing = FastOutSlowInEasing),
    label = "ChallengePress"
  )

  // Premium gradient: deep sage → mint
  val cardBrush = if (isCompleted) {
    Brush.horizontalGradient(
      colors = listOf(
        CleanPrimary,
        Color(0xFF538258)
      )
    )
  } else {
    Brush.horizontalGradient(
      colors = listOf(
        Color(0xFF2C5632),
        CleanPrimary
      )
    )
  }

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .scale(pressScale)
      .premiumShadow(PremiumShadows.CardHero, PremiumShapes.medium)
      .clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = onToggleComplete
      )
      .testTag("daily_challenge_card"),
    shape = PremiumShapes.medium,
    color = Color.Transparent
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(cardBrush)
        .padding(20.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Box(
          modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.18f)),
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

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = stringResource(R.string.challenge_section_label),
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.8.sp
            ),
            color = Color.White.copy(alpha = 0.75f)
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = challenge.title,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 15.sp,
              letterSpacing = (-0.2).sp
            ),
            color = Color.White
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Toggle pill with check
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(if (isCompleted) Color.White else Color.White.copy(alpha = 0.18f)),
          contentAlignment = Alignment.Center
        ) {
          if (isCompleted) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = stringResource(R.string.challenge_complete),
              tint = CleanPrimary,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}