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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.FoodEntryEntity
import com.example.ui.theme.CleanBackground
import com.example.ui.theme.CleanBorder
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPillAccent
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.CleanSurfaceLow
import com.example.ui.theme.ScoreGentleWarm
import com.example.ui.theme.ScoreMidOat
import com.example.ui.viewmodel.EatBetterViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogHistoryScreen(
  viewModel: EatBetterViewModel,
  uiState: UiState,
  onNavigateBack: () -> Unit,
  onAddFoodForMeal: (String) -> Unit
) {
  val activeDate = uiState.activeDate.ifBlank { uiState.todayDate }
  val entriesFlow = remember(activeDate) { viewModel.getEntriesForDate(activeDate) }
  val entries by entriesFlow.collectAsState(initial = emptyList())
  val backfillDates = viewModel.getBackfillDates()

  val mealTypes = listOf("Breakfast", "Lunch", "Dinner", "Snack")

  Scaffold(
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = CleanBackground
        ),
        title = {
          Text(
            text = "Food Log",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.SemiBold,
              letterSpacing = (-0.5).sp
            ),
            color = CleanOnSurface
          )
        },
        navigationIcon = {
          IconButton(onClick = onNavigateBack) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = CleanOnSurface
            )
          }
        }
      )
    },
    containerColor = CleanBackground
  ) { paddingValues ->

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

      // Date Switcher Chips
      item {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(backfillDates) { (dateStr, label) ->
            val isSelected = activeDate == dateStr
            FilterChip(
              selected = isSelected,
              onClick = { viewModel.setActiveDate(dateStr) },
              label = {
                Text(
                  text = label,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
              },
              shape = RoundedCornerShape(12.dp),
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = CleanPrimary,
                selectedLabelColor = Color.White,
                containerColor = CleanSurface,
                labelColor = CleanOnSurfaceVariant
              ),
              border = FilterChipDefaults.filterChipBorder(
                enabled = true,
                selected = isSelected,
                borderColor = if (isSelected) CleanPrimary else CleanBorder,
                borderWidth = 1.dp
              )
            )
          }
        }
      }

      // Grouped by Meal Types
      items(mealTypes) { mealType ->
        val mealEntries = entries.filter { it.mealType == mealType }
        MealGroupSection(
          mealType = mealType,
          entries = mealEntries,
          onAddClick = { onAddFoodForMeal(mealType) },
          onDeleteEntry = { viewModel.deleteEntry(it) }
        )
      }
    }
  }
}

@Composable
private fun MealGroupSection(
  mealType: String,
  entries: List<FoodEntryEntity>,
  onAddClick: () -> Unit,
  onDeleteEntry: (FoodEntryEntity) -> Unit
) {
  Surface(
    shape = RoundedCornerShape(24.dp),
    color = CleanSurface,
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = mealType,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = CleanOnSurface
        )

        IconButton(
          onClick = onAddClick,
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(CleanPillAccent)
        ) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add food to $mealType",
            tint = CleanPrimary,
            modifier = Modifier.size(20.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      if (entries.isEmpty()) {
        Text(
          text = "Nothing logged yet.",
          style = MaterialTheme.typography.bodySmall,
          color = CleanOnSurfaceVariant,
          modifier = Modifier.padding(vertical = 8.dp)
        )
      } else {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          entries.forEach { entry ->
            val scoreColor = when {
              entry.baseScore >= 8 -> CleanPrimary
              entry.baseScore >= 6 -> ScoreMidOat
              else -> ScoreGentleWarm
            }

            Surface(
              shape = RoundedCornerShape(16.dp),
              color = CleanSurfaceLow,
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(scoreColor.copy(alpha = 0.15f)),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "${entry.baseScore}",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = scoreColor
                  )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = entry.foodName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = CleanOnSurface
                  )
                  Text(
                    text = "${entry.portionName} • ${entry.calories} kcal",
                    style = MaterialTheme.typography.bodySmall,
                    color = CleanOnSurfaceVariant
                  )
                }

                IconButton(
                  onClick = { onDeleteEntry(entry) },
                  modifier = Modifier.size(28.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete entry",
                    tint = CleanOnSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
