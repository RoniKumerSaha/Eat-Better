package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.AchievementEntity
import com.example.ui.components.getBadgeIcon
import com.example.ui.theme.CleanBackground
import com.example.ui.theme.CleanBorder
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPillAccent
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.CleanSurfaceLow
import com.example.ui.viewmodel.EatBetterViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(
  viewModel: EatBetterViewModel,
  uiState: UiState
) {
  val unlockedCount = uiState.unlockedAchievements.size
  val totalCount = uiState.achievements.size

  Scaffold(
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = CleanBackground
        ),
        title = {
          Column {
            Text(
              text = "Badges & Milestones",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.5).sp
              ),
              color = CleanOnSurface
            )
            Text(
              text = "Celebrate mindful eating milestones",
              style = MaterialTheme.typography.bodySmall,
              color = CleanOnSurfaceVariant
            )
          }
        }
      )
    },
    containerColor = CleanBackground
  ) { paddingValues ->

    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 90.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {

      // Header Banner
      item(span = { GridItemSpan(2) }) {
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = CleanSurface,
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(CleanPillAccent),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.WorkspacePremium,
                contentDescription = null,
                tint = CleanPrimary,
                modifier = Modifier.size(28.dp)
              )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
              Text(
                text = "$unlockedCount of $totalCount Badges",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = CleanOnSurface
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Earned through small daily mindful choices.",
                style = MaterialTheme.typography.bodySmall,
                color = CleanOnSurfaceVariant
              )
            }
          }
        }
      }

      // Badge Grid Items
      items(uiState.achievements, key = { it.id }) { badge ->
        BadgeGridCard(
          badge = badge,
          onClick = { viewModel.openAchievementModal(badge) }
        )
      }
    }
  }
}

@Composable
private fun BadgeGridCard(
  badge: AchievementEntity,
  onClick: () -> Unit
) {
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(24.dp))
      .clickable { onClick() }
      .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
      .testTag("badge_card_${badge.id}"),
    shape = RoundedCornerShape(24.dp),
    color = CleanSurface
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .size(60.dp)
          .clip(CircleShape)
          .background(
            if (badge.isUnlocked) CleanPillAccent
            else CleanSurfaceLow
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = if (badge.isUnlocked) getBadgeIcon(badge.iconName) else Icons.Default.Lock,
          contentDescription = badge.title,
          tint = if (badge.isUnlocked) CleanPrimary else CleanOnSurfaceVariant.copy(alpha = 0.5f),
          modifier = Modifier.size(30.dp)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = badge.title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = CleanOnSurface,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = badge.description,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
        color = CleanOnSurfaceVariant,
        textAlign = TextAlign.Center,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Status pill
      Box(
        modifier = Modifier
          .clip(CircleShape)
          .background(
            if (badge.isUnlocked) CleanPillAccent
            else CleanSurfaceLow
          )
          .padding(horizontal = 10.dp, vertical = 3.dp)
      ) {
        Text(
          text = if (badge.isUnlocked) "✓ Unlocked" else "${badge.currentProgress}/${badge.targetCount}",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.SemiBold),
          color = if (badge.isUnlocked) CleanPrimary else CleanOnSurfaceVariant
        )
      }
    }
  }
}
