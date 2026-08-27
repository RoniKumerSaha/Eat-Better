package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.entity.AchievementEntity
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.SagePrimary

@Composable
fun BadgeDetailModal(
  achievement: AchievementEntity,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(28.dp),
      color = MaterialTheme.colorScheme.surface,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("badge_detail_modal")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Icon Circle
        Box(
          modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(
              if (achievement.isUnlocked) PrimaryContainer.copy(alpha = 0.4f)
              else MaterialTheme.colorScheme.surfaceVariant
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (achievement.isUnlocked) getBadgeIcon(achievement.iconName) else Icons.Default.Lock,
            contentDescription = null,
            tint = if (achievement.isUnlocked) SagePrimary else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(40.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = achievement.title,
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = achievement.description,
          style = MaterialTheme.typography.bodyMedium,
          textAlign = TextAlign.Center,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Unlock status pill
        Box(
          modifier = Modifier
            .clip(CircleShape)
            .background(
              if (achievement.isUnlocked) SagePrimary.copy(alpha = 0.15f)
              else MaterialTheme.colorScheme.surfaceContainerHigh
            )
            .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
          Text(
            text = if (achievement.isUnlocked) "✓ Unlocked" else "In Progress (${achievement.currentProgress}/${achievement.targetCount})",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = if (achievement.isUnlocked) SagePrimary else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
          onClick = onDismiss,
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
        ) {
          Text(
            text = "Close",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
      }
    }
  }
}

fun getBadgeIcon(iconName: String): ImageVector {
  return when (iconName) {
    "workspace_premium" -> Icons.Default.WorkspacePremium
    "local_fire_department" -> Icons.Default.LocalFireDepartment
    "eco" -> Icons.Default.Eco
    "water_drop" -> Icons.Default.WaterDrop
    "military_tech" -> Icons.Default.MilitaryTech
    "restaurant_menu" -> Icons.Default.RestaurantMenu
    "check_circle" -> Icons.Default.CheckCircle
    "spa" -> Icons.Default.Spa
    else -> Icons.Default.WorkspacePremium
  }
}
