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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CleanBorder
import com.example.ui.theme.CleanBorderDashed
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSubtleText
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.CleanSurfaceLow

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
    // Row 1: Breakfast & Lunch
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      CleanMealCard(
        label = "Breakfast",
        isLogged = isBreakfastLogged,
        isSkipped = isBreakfastSkipped,
        onClick = { onMealClick("Breakfast") },
        modifier = Modifier.weight(1f)
      )
      CleanMealCard(
        label = "Lunch",
        isLogged = isLunchLogged,
        isSkipped = isLunchSkipped,
        onClick = { onMealClick("Lunch") },
        modifier = Modifier.weight(1f)
      )
    }

    // Row 2: Dinner & Snack
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      CleanMealCard(
        label = "Dinner",
        isLogged = isDinnerLogged,
        isSkipped = isDinnerSkipped,
        onClick = { onMealClick("Dinner") },
        modifier = Modifier.weight(1f)
      )
      CleanMealCard(
        label = "Snack",
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
  val cardBg = when {
    isLogged -> CleanSurface
    isSkipped -> CleanSurfaceLow
    else -> CleanSurfaceLow
  }

  val borderColor = when {
    isLogged -> CleanBorder
    else -> CleanBorderDashed
  }

  Surface(
    modifier = modifier
      .height(120.dp)
      .clip(RoundedCornerShape(24.dp))
      .clickable { onClick() }
      .border(1.dp, borderColor, RoundedCornerShape(24.dp)),
    shape = RoundedCornerShape(24.dp),
    color = cardBg
  ) {
    Column(
      modifier = Modifier
        .padding(14.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      // Top status pill/icon
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(
            when {
              isLogged -> CleanPrimary
              isSkipped -> CleanSurface
              else -> CleanSurface
            }
          ),
        contentAlignment = Alignment.Center
      ) {
        when {
          isLogged -> {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = "$label Logged",
              tint = Color.White,
              modifier = Modifier.size(16.dp)
            )
          }
          isSkipped -> {
            Icon(
              imageVector = Icons.Default.DoNotDisturbOn,
              contentDescription = "$label Skipped",
              tint = CleanSubtleText,
              modifier = Modifier.size(16.dp)
            )
          }
          else -> {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = "Log $label",
              tint = CleanOnSurfaceVariant,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }

      // Bottom info labels
      Column {
        Text(
          text = label.uppercase(),
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            fontSize = 11.sp
          ),
          color = CleanOnSurfaceVariant
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = when {
            isLogged -> "Logged"
            isSkipped -> "Skipped"
            else -> "Pending..."
          },
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = if (isLogged) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 13.sp
          ),
          color = if (isLogged) CleanOnSurface else CleanSubtleText
        )
      }
    }
  }
}

