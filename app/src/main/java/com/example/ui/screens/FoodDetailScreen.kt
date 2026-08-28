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
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.FoodItem
import com.example.model.PortionOption
import com.example.ui.components.getFoodIcon
import com.example.ui.theme.CleanBackground
import com.example.ui.theme.CleanBorder
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPillAccent
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.CleanSurfaceLow
import com.example.ui.theme.PremiumGradients
import com.example.ui.theme.PremiumShapes
import com.example.ui.theme.PremiumShadow
import com.example.ui.theme.PremiumShadows
import com.example.ui.theme.ScoreGentleWarm
import com.example.ui.theme.ScoreMidOat
import com.example.ui.theme.premiumShadow
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
  // Meal-type identifiers are used as keys for viewModel.setSelectedMealType(),
  // so they must stay English ("Breakfast", "Lunch", "Dinner", "Snack").
  val mealTypes = listOf("Breakfast", "Lunch", "Dinner", "Snack")
  val mealLabels = mapOf(
    "Breakfast" to stringResource(R.string.meal_breakfast),
    "Lunch" to stringResource(R.string.meal_lunch),
    "Dinner" to stringResource(R.string.meal_dinner),
    "Snack" to stringResource(R.string.meal_snack)
  )
  val backfillDates = viewModel.getBackfillDates()
  val activeLogDate = uiState.selectedLogDate.ifBlank { uiState.todayDate }

  var showScoreExplanation by remember { mutableStateOf(false) }

  val scoreColor = when {
    food.baseScore >= 8 -> CleanPrimary
    food.baseScore >= 6 -> ScoreMidOat
    else -> ScoreGentleWarm
  }

  Scaffold(
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = CleanBackground
        ),
        title = {
          Text(
            text = stringResource(R.string.food_detail_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = CleanOnSurface
          )
        },
        navigationIcon = {
          IconButton(onClick = onNavigateBack) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = stringResource(R.string.action_back),
              tint = CleanOnSurface
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
              contentDescription = stringResource(R.string.food_card_favorite_content_description),
              tint = if (food.isFavorite) CleanPrimary else CleanOnSurfaceVariant
            )
          }
        }
      )
    },
    bottomBar = {
      Surface(
        color = CleanSurface,
        tonalElevation = 0.dp,
        modifier = Modifier
          .fillMaxWidth()
          .premiumShadow(
            shadow = PremiumShadow(
              elevation = 12.dp,
              ambientColor = androidx.compose.ui.graphics.Color(0x1F386B40),
              spotColor = androidx.compose.ui.graphics.Color(0x29386B40)
            ),
            shape = RoundedCornerShape(0.dp),
            clip = false
          )
          .drawBehind {
            drawLine(
              color = CleanBorder,
              start = Offset(0f, 0f),
              end = Offset(size.width, 0f),
              strokeWidth = 1.dp.toPx()
            )
          }
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
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
              .premiumShadow(PremiumShadows.Floating, RoundedCornerShape(16.dp), clip = false)
              .testTag("confirm_log_food_button"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = CleanPrimary,
              contentColor = Color.White
            )
          ) {
            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = stringResource(
                R.string.food_detail_log_for_meal,
                mealLabels[uiState.selectedMealType] ?: uiState.selectedMealType,
                selectedPortion.calories
              ),
              style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp, letterSpacing = 0.2.sp),
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
        }
      }
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

      // Hero Food Header Card
      item {
        Surface(
          shape = PremiumShapes.large,
          color = Color.Transparent,
          modifier = Modifier
            .fillMaxWidth()
            .premiumShadow(PremiumShadows.CardHero, PremiumShapes.large)
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(PremiumGradients.HeroSage)
          ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            // Icon in colored container
            Box(
              modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(CleanPillAccent),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = getFoodIcon(food),
                contentDescription = food.name,
                tint = CleanPrimary,
                modifier = Modifier.size(44.dp)
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
              text = food.name,
              style = MaterialTheme.typography.headlineSmall,
              fontWeight = FontWeight.Bold,
              color = CleanOnSurface
            )

            if (!food.bengaliName.isNullOrBlank()) {
              Text(
                text = food.bengaliName,
                style = MaterialTheme.typography.titleMedium,
                color = CleanOnSurfaceVariant
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
                color = CleanPillAccent
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = stringResource(R.string.food_detail_score_format, food.baseScore),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = CleanPrimary
                  )
                }
              }

              Spacer(modifier = Modifier.width(8.dp))

              Surface(
                shape = CircleShape,
                color = CleanSurfaceLow,
                modifier = Modifier
                  .clickable { showScoreExplanation = !showScoreExplanation }
                  .border(1.dp, CleanBorder, CircleShape)
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.HelpOutline,
                    contentDescription = null,
                    tint = CleanOnSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = stringResource(R.string.food_detail_why_this_score),
                    style = MaterialTheme.typography.labelSmall,
                    color = CleanOnSurfaceVariant
                  )
                }
              }
            }

            // Expandable Score Explanation
            AnimatedVisibility(visible = showScoreExplanation) {
              Surface(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 14.dp)
                  .border(1.dp, CleanBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                color = CleanSurfaceLow
              ) {
                Column(modifier = Modifier.padding(16.dp)) {
                  Text(
                    text = stringResource(R.string.food_detail_score_factors),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = CleanOnSurface
                  )
                  Spacer(modifier = Modifier.height(6.dp))
                  food.scoreExplanation.forEach { explanation ->
                    Text(
                      text = explanation,
                      style = MaterialTheme.typography.bodySmall,
                      color = CleanOnSurfaceVariant,
                      lineHeight = 18.sp
                    )
                  }
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
          shape = PremiumShapes.medium,
          color = CleanSurface,
          modifier = Modifier
            .fillMaxWidth()
            .premiumShadow(PremiumShadows.CardSubtle, PremiumShapes.medium)
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.Spa,
              contentDescription = null,
              tint = CleanPrimary,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = food.educationalText,
              style = MaterialTheme.typography.bodyMedium,
              color = CleanOnSurface,
              lineHeight = 20.sp
            )
          }
        }
      }

      // Portion Options
      item {
        Text(
          text = stringResource(R.string.food_detail_select_portion),
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = CleanOnSurface
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
          color = CleanSurface,
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CleanBorder, RoundedCornerShape(20.dp))
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = stringResource(R.string.food_detail_nutritional_breakdown, selectedPortion.name),
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = CleanOnSurface
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              NutrientStat(
                stringResource(R.string.food_detail_calories),
                stringResource(R.string.food_detail_calories_value, selectedPortion.calories)
              )
              NutrientStat(
                stringResource(R.string.food_detail_carbs),
                stringResource(R.string.food_detail_carbs_value, selectedPortion.carbs)
              )
              NutrientStat(
                stringResource(R.string.food_detail_protein),
                stringResource(R.string.food_detail_protein_value, selectedPortion.protein)
              )
              NutrientStat(
                stringResource(R.string.food_detail_fat),
                stringResource(R.string.food_detail_fat_value, selectedPortion.fat)
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              NutrientStat(
                stringResource(R.string.food_detail_fiber),
                stringResource(R.string.food_detail_fiber_value, selectedPortion.fiber)
              )
              NutrientStat(
                stringResource(R.string.food_detail_sugar),
                stringResource(R.string.food_detail_sugar_value, selectedPortion.sugar)
              )
              NutrientStat(
                stringResource(R.string.food_detail_sodium),
                stringResource(R.string.food_detail_sodium_value, selectedPortion.sodium.toInt())
              )
              NutrientStat(
                stringResource(R.string.food_detail_servings),
                stringResource(R.string.food_detail_multiplier_value, selectedPortion.multiplier)
              )
            }
          }
        }
      }

      // Meal Type Selector
      item {
        Text(
          text = stringResource(R.string.food_detail_meal_type),
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = CleanOnSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(mealTypes) { meal ->
            val isSelected = uiState.selectedMealType == meal
            FilterChip(
              selected = isSelected,
              onClick = { viewModel.setSelectedMealType(meal) },
              label = {
                Text(
                  text = mealLabels[meal] ?: meal,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
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
                borderColor = CleanBorder,
                selectedBorderColor = CleanPrimary
              ),
              modifier = Modifier.testTag("meal_type_$meal")
            )
          }
        }
      }

      // Log Date Selector (Backfill support)
      item {
        Text(
          text = stringResource(R.string.food_detail_log_date),
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = CleanOnSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(backfillDates) { (dateStr, label) ->
            val isSelected = activeLogDate == dateStr
            FilterChip(
              selected = isSelected,
              onClick = { viewModel.setSelectedLogDate(dateStr) },
              label = {
                Text(
                  text = label,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
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
                borderColor = CleanBorder,
                selectedBorderColor = CleanPrimary
              ),
              modifier = Modifier.testTag("log_date_$label")
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
    color = if (isSelected) CleanPillAccent else CleanSurface,
    border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) CleanPrimary else CleanBorder)
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
          color = CleanOnSurface
        )
        Text(
          text = stringResource(
            R.string.food_detail_portion_subtitle,
            portion.calories,
            portion.protein,
            portion.fiber
          ),
          style = MaterialTheme.typography.bodySmall,
          color = CleanOnSurfaceVariant
        )
      }

      if (isSelected) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = null,
          tint = CleanPrimary,
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
      color = CleanOnSurface
    )
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = CleanOnSurfaceVariant
    )
  }
}
