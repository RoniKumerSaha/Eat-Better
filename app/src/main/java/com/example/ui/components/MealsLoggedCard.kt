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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoNotDisturbOn
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
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPillAccent
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSubtleText
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.CleanSurfaceLow
import com.example.ui.theme.PremiumGradients
import com.example.ui.theme.PremiumShapes
import com.example.ui.theme.PremiumShadows
import com.example.ui.theme.premiumShadow

@Composable
fun MealsLoggedCard(
  isBreakfastLogged: Boolean,
  isLunchLogged: Boolean,
  isDinnerLogged: Boolean,
  isSnackLogged: Boolean,
  isBreakfastSkipped: Boolean = false,
  isLunchSkipped: Boolean = false,
  isDinnerSkipped: Boolean = false,
  totalConfiguredMainMeals: Int = 3,
  onMealClick: (String) -> Unit,
  onViewAllLogsClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .testTag("meals_logged_card"),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      CleanMealCard(
        label = stringResource(R.string.meal_breakfast),
        isLogged = isBreakfastLogged,
        isSkipped = isBreakfastSkipped,
        onClick = { onMealClick("Breakfast") },
        modifier = Modifier.weight(1f)
      )
      CleanMealCard(
        label = stringResource(R.string.meal_lunch),
        isLogged = isLunchLogged,
        isSkipped = isLunchSkipped,
        onClick = { onMealClick("Lunch") },
        modifier = Modifier.weight(1f)
      )
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      CleanMealCard(
        label = stringResource(R.string.meal_dinner),
        isLogged = isDinnerLogged,
        isSkipped = isDinnerSkipped,
        onClick = { onMealClick("Dinner") },
        modifier = Modifier.weight(1f)
      )
      CleanMealCard(
        label = stringResource(R.string.meal_snack),
        isLogged = isSnackLogged,
        isSkipped = false,
        isSnack = true,
        onClick = { onMealClick("Snack") },
        modifier = Modifier.weight(1f)
      )
    }
  }
}

@Composable
private fun CleanMealCard(
  label: String,
  isLogged: Boolean,
  isSkipped: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  isSnack: Boolean = false
) {
  // Background swap: logged = saturated green tint, skipped = neutral grey, pending = white card
  val cardBg: Brush = when {
    isLogged -> PremiumGradients.HeroSage
    isSkipped -> PremiumGradients.TileNeutral
    else -> Brush.verticalGradient(listOf(CleanSurface, CleanSurface))
  }

  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val pressScale by animateFloatAsState(
    targetValue = if (isPressed) 0.97f else 1f,
    animationSpec = tween(120, easing = FastOutSlowInEasing),
    label = "MealPress"
  )

  Surface(
    modifier = modifier
      .height(124.dp)
      .scale(pressScale)
      .premiumShadow(
        if (isLogged) PremiumShadows.CardResting else PremiumShadows.CardSubtle,
        PremiumShapes.medium
      )
      .clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = onClick
      ),
    shape = PremiumShapes.medium,
    color = CleanSurface
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(cardBg)
    ) {
    Column(
      modifier = Modifier
        .padding(14.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      // Top status pill
      Box(
        modifier = Modifier
          .size(34.dp)
          .clip(CircleShape)
          .background(
            when {
              isLogged -> CleanPrimary
              isSkipped -> CleanSurface
              else -> CleanPillAccent
            }
          ),
        contentAlignment = Alignment.Center
      ) {
        when {
          isLogged -> {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = "$label ${stringResource(R.string.meal_status_logged)}",
              tint = Color.White,
              modifier = Modifier.size(18.dp)
            )
          }
          isSkipped -> {
            Icon(
              imageVector = Icons.Default.DoNotDisturbOn,
              contentDescription = "$label ${stringResource(R.string.meal_status_skipped)}",
              tint = CleanSubtleText,
              modifier = Modifier.size(16.dp)
            )
          }
          else -> {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = "${stringResource(R.string.action_log_food)} $label",
              tint = CleanPrimary,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }

      Column {
        Text(
          text = label.uppercase(),
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            fontSize = 10.5.sp
          ),
          color = CleanOnSurfaceVariant
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = when {
            isLogged -> stringResource(R.string.meal_status_logged)
            isSkipped -> stringResource(R.string.meal_status_skipped)
            else -> stringResource(R.string.meal_status_pending)
          },
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = if (isLogged) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 13.5.sp,
            letterSpacing = (-0.2).sp
          ),
          color = if (isLogged) CleanOnSurface else CleanSubtleText
        )
      }
    }
    }
  }
}