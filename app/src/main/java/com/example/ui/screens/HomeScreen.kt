package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.DefaultChallenges
import com.example.data.local.entity.FoodEntryEntity
import com.example.scoring.NutritionScoringEngine
import com.example.ui.components.ChallengeCard
import com.example.ui.components.MealsLoggedCard
import com.example.ui.components.NutritiousBalanceCard
import com.example.ui.components.ScoreRing
import com.example.ui.theme.CleanBackground
import com.example.ui.theme.CleanBorder
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPillAccent
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanPrimaryContainer
import com.example.ui.theme.CleanSubtleText
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.ScoreGentleWarm
import com.example.ui.theme.ScoreMidOat
import com.example.ui.viewmodel.EatBetterViewModel
import com.example.ui.viewmodel.UiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  viewModel: EatBetterViewModel,
  uiState: UiState,
  onNavigateToFoods: (mealType: String?) -> Unit,
  onNavigateToLogHistory: () -> Unit,
  onNavigateToSettings: () -> Unit
) {
  val activeDate = uiState.activeDate.ifBlank { uiState.todayDate }
  val entriesFlow = remember(activeDate) { viewModel.getEntriesForDate(activeDate) }
  val entries by entriesFlow.collectAsState(initial = emptyList())

  val dailyRecordFlow = remember(activeDate) { viewModel.getDailyRecord(activeDate) }
  val dailyRecord by dailyRecordFlow.collectAsState(initial = null)

  val challenge = remember(activeDate) { DefaultChallenges.getChallengeForDate(activeDate) }
  val isChallengeCompleted = dailyRecord?.challengeCompleted ?: false

  val goals = remember(uiState.userSettings.goals) {
    uiState.userSettings.goals.split(",").map { it.trim() }.toSet()
  }
  val skippedMeals = remember(dailyRecord?.skippedMeals) {
    dailyRecord?.skippedMeals?.split(",")?.filter { it.isNotBlank() }?.toSet() ?: emptySet()
  }

  var configuredMealsCount = 0
  if (uiState.userSettings.breakfastEnabled) configuredMealsCount++
  if (uiState.userSettings.lunchEnabled) configuredMealsCount++
  if (uiState.userSettings.dinnerEnabled) configuredMealsCount++

  val scoreResult = remember(entries, isChallengeCompleted, goals, skippedMeals, configuredMealsCount) {
    NutritionScoringEngine.calculateDailyScore(
      entries = entries,
      challengeCompleted = isChallengeCompleted,
      goals = goals,
      skippedMeals = skippedMeals,
      configuredMealsCount = configuredMealsCount
    )
  }

  val isBreakfastLogged = entries.any { it.mealType == "Breakfast" }
  val isLunchLogged = entries.any { it.mealType == "Lunch" }
  val isDinnerLogged = entries.any { it.mealType == "Dinner" }
  val isSnackLogged = entries.any { it.mealType == "Snack" }

  var showScoreExplainer by remember { mutableStateOf(false) }

  val formattedDate = remember(activeDate) {
    try {
      val parsed = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(activeDate)
      if (parsed != null) {
        SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(parsed)
      } else "Today"
    } catch (_: Exception) {
      "Today"
    }
  }

  val userInitial = remember(uiState.userSettings.displayName) {
    uiState.userSettings.displayName.trim().take(1).uppercase().ifBlank { "A" }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = CleanBackground
        ),
        title = {
          Column {
            Text(
              text = "Eat Better",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                letterSpacing = (-0.5).sp
              ),
              color = CleanOnSurface
            )
            Text(
              text = formattedDate,
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp
              ),
              color = CleanOnSurfaceVariant
            )
          }
        },
        actions = {
          // Streak Pill
          Surface(
            shape = CircleShape,
            color = CleanPillAccent,
            modifier = Modifier.padding(end = 6.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = "Streak",
                tint = CleanPrimary,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "${uiState.streakDays}d",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = CleanPrimary
              )
            }
          }

          // Share Button
          IconButton(
            onClick = { viewModel.openShareDialog() },
            modifier = Modifier.testTag("home_share_button")
          ) {
            Icon(
              imageVector = Icons.Default.Share,
              contentDescription = "Share",
              tint = CleanOnSurfaceVariant
            )
          }

          // User Initial Avatar Circle
          Box(
            modifier = Modifier
              .padding(end = 12.dp, start = 2.dp)
              .size(40.dp)
              .clip(CircleShape)
              .background(CleanPillAccent)
              .clickable { onNavigateToSettings() },
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = userInitial,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
              ),
              color = CleanPrimary
            )
          }
        }
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = { onNavigateToFoods(null) },
        containerColor = CleanPrimary,
        contentColor = Color.White,
        modifier = Modifier
          .padding(bottom = 12.dp)
          .testTag("fab_log_food"),
        shape = CircleShape
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.Add, contentDescription = "Log Food")
          Spacer(modifier = Modifier.width(6.dp))
          Text("Log Food", fontWeight = FontWeight.Bold)
        }
      }
    },
    containerColor = CleanBackground
  ) { paddingValues ->

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 90.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

      // Backfill Day Selector Tabs (Today, Yesterday, 2 Days Ago)
      item {
        val backfillDates = viewModel.getBackfillDates()
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CleanSurface)
            .border(1.dp, CleanBorder, RoundedCornerShape(16.dp))
            .padding(4.dp),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          backfillDates.forEach { (dateStr, label) ->
            val isSelected = activeDate == dateStr
            Surface(
              modifier = Modifier
                .weight(1f)
                .clickable { viewModel.setActiveDate(dateStr) },
              shape = RoundedCornerShape(12.dp),
              color = if (isSelected) CleanPillAccent else Color.Transparent
            ) {
              Box(
                modifier = Modifier.padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = label,
                  style = MaterialTheme.typography.labelMedium,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSelected) CleanPrimary else CleanOnSurfaceVariant
                )
              }
            }
          }
        }
      }

      // Hero Score Card (Clean Minimal Mint Container with Gauge + Guidance)
      item {
        Surface(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("hero_score_card"),
          shape = RoundedCornerShape(32.dp),
          color = CleanPrimaryContainer
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            ScoreRing(
              score = scoreResult.score,
              statusLabel = scoreResult.scoreLabel,
              size = 136.dp,
              strokeWidth = 8.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
              text = scoreResult.scoreLabel,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
              ),
              color = CleanOnSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = "“${scoreResult.gentleGuidanceMessage}”",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic
              ),
              color = CleanOnSurfaceVariant,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Score Explainer Toggle
            Row(
              modifier = Modifier
                .clip(CircleShape)
                .clickable { showScoreExplainer = !showScoreExplainer }
                .padding(horizontal = 10.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.HelpOutline,
                contentDescription = null,
                tint = CleanOnSurfaceVariant,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = if (showScoreExplainer) "Hide details" else "Why this score?",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = CleanOnSurfaceVariant
              )
            }

            AnimatedVisibility(visible = showScoreExplainer) {
              Surface(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 12.dp),
                shape = RoundedCornerShape(16.dp),
                color = CleanSurface
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Text(
                    text = "Understanding Your Nutrition Score",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = CleanOnSurface
                  )
                  Spacer(modifier = Modifier.height(6.dp))
                  Text(
                    text = "• Scores reflect the nutritional density (vitamins, fiber, clean protein, wholesome fats) of each portion.\n• Wholesome variety adds gentle bonuses.\n• Completing your daily challenge adds +5 bonus points.\n• Non-punitive: one less nutrient-dense item won't ruin a wholesome day.",
                    style = MaterialTheme.typography.bodySmall,
                    color = CleanOnSurfaceVariant,
                    lineHeight = 17.sp
                  )
                }
              }
            }
          }
        }
      }

      // Meals Logged Overview (2x2 Clean Minimalist Grid)
      item {
        MealsLoggedCard(
          isBreakfastLogged = isBreakfastLogged,
          isLunchLogged = isLunchLogged,
          isDinnerLogged = isDinnerLogged,
          isSnackLogged = isSnackLogged,
          isBreakfastSkipped = skippedMeals.contains("Breakfast"),
          isLunchSkipped = skippedMeals.contains("Lunch"),
          isDinnerSkipped = skippedMeals.contains("Dinner"),
          totalConfiguredMainMeals = configuredMealsCount,
          onMealClick = { mealType ->
            onNavigateToFoods(mealType)
          },
          onViewAllLogsClick = onNavigateToLogHistory
        )
      }

      // Daily Challenge Banner
      item {
        ChallengeCard(
          challenge = challenge,
          isCompleted = isChallengeCompleted,
          onToggleComplete = {
            viewModel.toggleChallengeCompleted(activeDate, isChallengeCompleted)
          }
        )
      }

      // Nutritious Balance Progress Bar
      item {
        NutritiousBalanceCard(
          percentage = scoreResult.nutritiousBalancePercentage,
          targetPercentage = 80
        )
      }

      // Logged Foods Header & Mini List
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Logged Foods (${entries.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = CleanOnSurface
          )

          if (entries.isNotEmpty()) {
            Row(
              modifier = Modifier
                .clickable { onNavigateToLogHistory() }
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "View all",
                style = MaterialTheme.typography.labelMedium,
                color = CleanPrimary,
                fontWeight = FontWeight.SemiBold
              )
              Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = CleanPrimary,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }

      if (entries.isEmpty()) {
        item {
          Surface(
            modifier = Modifier
              .fillMaxWidth()
              .border(1.dp, CleanBorder, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            color = CleanSurface
          ) {
            Column(
              modifier = Modifier.padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = "No meals logged for this day yet.",
                style = MaterialTheme.typography.bodyMedium,
                color = CleanOnSurfaceVariant
              )
              Spacer(modifier = Modifier.height(12.dp))
              Button(
                onClick = { onNavigateToFoods(null) },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CleanPrimary)
              ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Log Food Now")
              }
            }
          }
        }
      } else {
        items(entries) { entry ->
          LoggedFoodRowItem(
            entry = entry,
            onDelete = { viewModel.deleteEntry(entry) }
          )
        }
      }
    }
  }
}

@Composable
fun LoggedFoodRowItem(
  entry: FoodEntryEntity,
  onDelete: () -> Unit
) {
  val scoreColor = when {
    entry.baseScore >= 8 -> CleanPrimary
    entry.baseScore >= 6 -> ScoreMidOat
    else -> ScoreGentleWarm
  }

  Surface(
    shape = RoundedCornerShape(20.dp),
    color = CleanSurface,
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, CleanBorder, RoundedCornerShape(20.dp))
      .testTag("logged_entry_${entry.id}")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Score badge
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(CleanPillAccent),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "${entry.baseScore}",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = CleanPrimary
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = entry.foodName,
          style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
          fontWeight = FontWeight.SemiBold,
          color = CleanOnSurface
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = "${entry.mealType} • ${entry.portionName} • ${entry.calories} kcal",
          style = MaterialTheme.typography.bodySmall,
          color = CleanOnSurfaceVariant
        )
      }

      IconButton(onClick = onDelete) {
        Icon(
          imageVector = Icons.Default.Delete,
          contentDescription = "Remove entry",
          tint = CleanSubtleText.copy(alpha = 0.6f),
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}

