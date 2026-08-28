package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.R
import com.example.ui.components.BadgeDetailModal
import com.example.ui.components.ShareCardDialog
import com.example.ui.screens.AchievementsScreen
import com.example.ui.screens.FoodDetailScreen
import com.example.ui.screens.FoodsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LogHistoryScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.CleanBackground
import com.example.ui.theme.CleanBorder
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPillAccent
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.PremiumShadow
import com.example.ui.viewmodel.EatBetterViewModel

enum class NavigationTab(
  val route: String,
  val titleRes: Int,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector
) {
  HOME("home", R.string.nav_home, Icons.Filled.Home, Icons.Outlined.Home),
  FOODS("foods", R.string.nav_library, Icons.Filled.RestaurantMenu, Icons.Outlined.RestaurantMenu),
  PROGRESS("progress", R.string.nav_progress, Icons.Filled.AutoGraph, Icons.Outlined.AutoGraph),
  BADGES("achievements", R.string.nav_badges, Icons.Filled.WorkspacePremium, Icons.Outlined.WorkspacePremium),
  SETTINGS("settings", R.string.nav_settings, Icons.Filled.Settings, Icons.Filled.Settings)
}

@Composable
fun EatBetterApp(
  viewModel: EatBetterViewModel = viewModel()
) {
  val uiState by viewModel.uiState.collectAsState()

  // Full-screen overlays and flows
  when (uiState.currentScreen) {
    "onboarding" -> {
      OnboardingScreen(
        onComplete = { name, goals, breakfast, lunch, dinner, snacks ->
          viewModel.completeOnboarding(name, goals, breakfast, lunch, dinner, snacks)
        }
      )
    }

    "food_detail" -> {
      val food = uiState.selectedFood
      if (food != null) {
        FoodDetailScreen(
          food = food,
          viewModel = viewModel,
          uiState = uiState,
          onNavigateBack = { viewModel.navigateTo("foods") }
        )
      } else {
        viewModel.navigateTo("foods")
      }
    }

    "log_history" -> {
      LogHistoryScreen(
        viewModel = viewModel,
        uiState = uiState,
        onNavigateBack = { viewModel.navigateTo("home") },
        onAddFoodForMeal = { mealType ->
          viewModel.setSelectedMealType(mealType)
          viewModel.navigateTo("foods")
        }
      )
    }

    "settings" -> {
      SettingsScreen(
        viewModel = viewModel,
        uiState = uiState,
        onNavigateBack = { viewModel.navigateTo("home") }
      )
    }

    else -> {
      // Main App Shell with Bottom Navigation Bar
      Scaffold(
        bottomBar = {
          androidx.compose.foundation.layout.Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(CleanSurface)
              .drawBehind {
                drawLine(
                  color = CleanBorder.copy(alpha = 0.5f),
                  start = Offset(0f, 0f),
                  end = Offset(size.width, 0f),
                  strokeWidth = 0.5.dp.toPx()
                )
              }
          ) {
            NavigationBar(
              containerColor = Color.Transparent,
              tonalElevation = 0.dp,
              modifier = Modifier
                .fillMaxWidth()
                .testTag("main_bottom_nav_bar")
            ) {
            val tabs = listOf(
              NavigationTab.HOME,
              NavigationTab.FOODS,
              NavigationTab.PROGRESS,
              NavigationTab.BADGES,
              NavigationTab.SETTINGS
            )

            tabs.forEach { tab ->
              val isSelected = uiState.currentScreen == tab.route
              NavigationBarItem(
                selected = isSelected,
                onClick = { viewModel.navigateTo(tab.route) },
                icon = {
                  Icon(
                    imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                    contentDescription = stringResource(tab.titleRes),
                    modifier = Modifier.size(22.dp)
                  )
                },
                label = {
                  Text(
                    text = stringResource(tab.titleRes),
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                      fontSize = 11.sp
                    )
                  )
                },
                colors = NavigationBarItemDefaults.colors(
                  selectedIconColor = CleanPrimary,
                  selectedTextColor = CleanPrimary,
                  indicatorColor = CleanPillAccent,
                  unselectedIconColor = CleanOnSurfaceVariant.copy(alpha = 0.65f),
                  unselectedTextColor = CleanOnSurfaceVariant.copy(alpha = 0.65f)
                )
              )
            }
          }
          }
          },
        containerColor = CleanBackground
      ) { innerPadding ->

        AnimatedContent(
          targetState = uiState.currentScreen,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "ScreenTransition",
          modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) { targetScreen ->
          when (targetScreen) {
            "home" -> {
              HomeScreen(
                viewModel = viewModel,
                uiState = uiState,
                onNavigateToFoods = { targetMeal ->
                  if (targetMeal != null) {
                    viewModel.setSelectedMealType(targetMeal)
                  }
                  viewModel.navigateTo("foods")
                },
                onNavigateToLogHistory = { viewModel.navigateTo("log_history") },
                onNavigateToSettings = { viewModel.navigateTo("settings") }
              )
            }

            "foods" -> {
              FoodsScreen(
                viewModel = viewModel,
                uiState = uiState,
                onFoodSelected = { food ->
                  viewModel.openFoodDetail(food)
                }
              )
            }

            "progress" -> {
              ProgressScreen(
                viewModel = viewModel,
                uiState = uiState
              )
            }

            "achievements" -> {
              AchievementsScreen(
                viewModel = viewModel,
                uiState = uiState
              )
            }

            else -> {
              HomeScreen(
                viewModel = viewModel,
                uiState = uiState,
                onNavigateToFoods = { viewModel.navigateTo("foods") },
                onNavigateToLogHistory = { viewModel.navigateTo("log_history") },
                onNavigateToSettings = { viewModel.navigateTo("settings") }
              )
            }
          }
        }
      }
    }
  }

  // Dialogs
  if (uiState.showShareDialog) {
    ShareCardDialog(
      displayName = uiState.userSettings.displayName,
      score = uiState.weeklyRecords.firstOrNull()?.score ?: 84,
      streakDays = uiState.streakDays,
      nutritiousPercentage = uiState.weeklyRecords.firstOrNull()?.nutritiousBalancePercentage ?: 80,
      onDismiss = { viewModel.closeShareDialog() }
    )
  }

  uiState.selectedAchievementForModal?.let { achievement ->
    BadgeDetailModal(
      achievement = achievement,
      onDismiss = { viewModel.closeAchievementModal() }
    )
  }
}

