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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Spa
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FoodItem
import com.example.model.PortionOption
import com.example.ui.components.getFoodIcon
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.SagePrimary
import com.example.ui.theme.ScoreGentleWarm
import com.example.ui.theme.ScoreMidOat
import com.example.ui.viewmodel.EatBetterViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailScreen(
  food: FoodItem,
  viewModel: EatBetterViewModel,
  uiState: UiState,
  onNavigateBack: () -> Unit
) {
  val selectedPortion = uiState.selectedPortion ?: food.portions.firstOrNull() ?: PortionOption("default", "Standard Serving", 1f, 100, 10f, 2f, 1f, 1f, 1f, 10f)
  val mealTypes = listOf("Breakfast", "Lunch", "Dinner", "Snack")
  val backfillDates = viewModel.getBackfillDates()
  val activeLogDate = uiState.selectedLogDate.ifBlank { uiState.todayDate }

  var showScoreExplanation by remember { mutableStateOf(false) }

  val scoreColor = when {
    food.baseScore >= 8 -> SagePrimary
    food.baseScore >= 6 -> ScoreMidOat
    else -> ScoreGentleWarm
  }

  Scaffold(
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
          Text(
            text = "Food Details",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
          )
        },
        navigationIcon = {
          IconButton(onClick = onNavigateBack) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back"
            )
          }
        },
        actions = {
          IconButton(
            onClick = { viewModel.toggleFavorite(food.id) },
            modifier = Modifier.testTag("detail_favorite_button")
          ) {
            Icon(
              imageVector = if (food.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
              contentDescription = "Favorite",
              tint = if (food.isFavorite) SagePrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      )
    },
    bottomBar = {
      Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
          Button(
            onClick = {
              viewModel.logCurrentFoodSelection()
              onNavigateBack()
            },
            modifier = Modifier
              .fillMaxWidth()
              .height(54.dp)
              .testTag("confirm_log_food_button"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SagePrimary)
          ) {
            Icon(Icons.Default.Check, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Log for ${uiState.selectedMealType} (${selectedPortion.calories} kcal)",
              style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp),
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    },
    containerColor = MaterialTheme.colorScheme.background
  ) { paddingValues ->

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

      // Hero Food Header Card
      item {
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = MaterialTheme.colorScheme.surfaceContainerLowest,
          shadowElevation = 2.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            // Icon in colored container
            Box(
              modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(PrimaryContainer.copy(alpha = 0.4f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = getFoodIcon(food.iconSymbol, food.categoryId),
                contentDescription = food.name,
                tint = SagePrimary,
                modifier = Modifier.size(44.dp)
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
              text = food.name,
              style = MaterialTheme.typography.headlineSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )

            if (!food.bengaliName.isNullOrBlank()) {
              Text(
                text = food.bengaliName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Score Pill + Explainer Button
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Surface(
                shape = CircleShape,
                color = scoreColor.copy(alpha = 0.15f)
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "Score: ${food.baseScore}/10",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = scoreColor
                  )
                }
              }

              Spacer(modifier = Modifier.width(8.dp))

              Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier = Modifier.clickable { showScoreExplanation = !showScoreExplanation }
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.HelpOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "Why this score?",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }
            }

            // Expandable Score Explanation
            AnimatedVisibility(visible = showScoreExplanation) {
              Surface(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 14.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLow
              ) {
                Column(modifier = Modifier.padding(16.dp)) {
                  Text(
                    text = "Score Factors",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Spacer(modifier = Modifier.height(6.dp))
                  food.scoreExplanation.forEach { explanation ->
                    Text(
                      text = explanation,
                      style = MaterialTheme.typography.bodySmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                      lineHeight = 18.sp
                    )
                  }
                }
              }
            }
          }
        }
      }

      // Educational Nutrition Text
      item {
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = MaterialTheme.colorScheme.surfaceContainerLow,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.Spa,
              contentDescription = null,
              tint = SagePrimary,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = food.educationalText,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurface,
              lineHeight = 20.sp
            )
          }
        }
      }

      // Portion Options
      item {
        Text(
          text = "Select Portion",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          food.portions.forEach { portion ->
            val isSelected = portion.id == selectedPortion.id
            PortionSelectCard(
              portion = portion,
              isSelected = isSelected,
              onClick = { viewModel.setSelectedPortion(portion) }
            )
          }
        }
      }

      // Nutrition Breakdown for Selected Portion
      item {
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = MaterialTheme.colorScheme.surfaceContainerLowest,
          shadowElevation = 1.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "Nutritional Breakdown (${selectedPortion.name})",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              NutrientStat("Calories", "${selectedPortion.calories} kcal")
              NutrientStat("Carbs", "${selectedPortion.carbs}g")
              NutrientStat("Protein", "${selectedPortion.protein}g")
              NutrientStat("Fat", "${selectedPortion.fat}g")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              NutrientStat("Fiber", "${selectedPortion.fiber}g")
              NutrientStat("Sugar", "${selectedPortion.sugar}g")
              NutrientStat("Sodium", "${selectedPortion.sodium.toInt()}mg")
              NutrientStat("Multiplier", "${selectedPortion.multiplier}x")
            }
          }
        }
      }

      // Meal Type Selector
      item {
        Text(
          text = "Meal Type",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(mealTypes) { meal ->
            val isSelected = uiState.selectedMealType == meal
            FilterChip(
              selected = isSelected,
              onClick = { viewModel.setSelectedMealType(meal) },
              label = { Text(meal) },
              shape = RoundedCornerShape(12.dp),
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = SagePrimary,
                selectedLabelColor = Color.White,
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
              ),
              border = null
            )
          }
        }
      }

      // Log Date Selector (Backfill support)
      item {
        Text(
          text = "Log Date",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(backfillDates) { (dateStr, label) ->
            val isSelected = activeLogDate == dateStr
            FilterChip(
              selected = isSelected,
              onClick = { viewModel.setSelectedLogDate(dateStr) },
              label = { Text(label) },
              shape = RoundedCornerShape(12.dp),
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = SagePrimary,
                selectedLabelColor = Color.White,
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
              ),
              border = null
            )
          }
        }
      }
    }
  }
}

@Composable
private fun PortionSelectCard(
  portion: PortionOption,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .testTag("portion_card_${portion.id}"),
    shape = RoundedCornerShape(14.dp),
    color = if (isSelected) PrimaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surfaceContainerLow,
    border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, SagePrimary) else null
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Text(
          text = portion.name,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "${portion.calories} kcal • ${portion.protein}g protein • ${portion.fiber}g fiber",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      if (isSelected) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = null,
          tint = SagePrimary,
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}

@Composable
private fun NutrientStat(label: String, value: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = value,
      style = MaterialTheme.typography.titleSmall,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface
    )
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}
