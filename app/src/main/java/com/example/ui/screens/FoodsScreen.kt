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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FoodItem
import com.example.ui.components.FoodItemCard
import com.example.ui.theme.CleanBackground
import com.example.ui.theme.CleanBorder
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPillAccent
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSubtleText
import com.example.ui.theme.CleanSurface
import com.example.ui.theme.CleanSurfaceLow
import com.example.ui.viewmodel.EatBetterViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodsScreen(
  viewModel: EatBetterViewModel,
  uiState: UiState,
  onFoodSelected: (FoodItem) -> Unit
) {
  val categories = listOf("All", "Bangladesh", "Fruits", "Vegetables", "Meals", "Snacks", "Drinks")

  Scaffold(
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = CleanBackground
        ),
        title = {
          Column {
            Text(
              text = "Food Database",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.5).sp
              ),
              color = CleanOnSurface
            )
            Text(
              text = "${uiState.filteredFoods.size} wholesome foods available",
              style = MaterialTheme.typography.bodySmall,
              color = CleanOnSurfaceVariant
            )
          }
        },
        actions = {
          // Saved Only toggle
          Surface(
            shape = CircleShape,
            color = if (uiState.savedOnly) CleanPillAccent else CleanSurface,
            modifier = Modifier
              .padding(end = 12.dp)
              .clip(CircleShape)
              .border(1.dp, if (uiState.savedOnly) CleanPrimary else CleanBorder, CircleShape)
              .clickable { viewModel.toggleSavedOnly() }
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Saved Only",
                tint = if (uiState.savedOnly) CleanPrimary else CleanOnSurfaceVariant,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Saved",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (uiState.savedOnly) FontWeight.Bold else FontWeight.Medium,
                color = if (uiState.savedOnly) CleanPrimary else CleanOnSurfaceVariant
              )
            }
          }
        }
      )
    },
    containerColor = CleanBackground
  ) { paddingValues ->

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {

      // Search Field
      OutlinedTextField(
        value = uiState.searchQuery,
        onValueChange = { viewModel.setSearchQuery(it) },
        placeholder = { Text("Search foods (e.g. apple, dal, bhat, mach)...", color = CleanSubtleText) },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = "Search", tint = CleanPrimary)
        },
        trailingIcon = {
          if (uiState.searchQuery.isNotEmpty()) {
            IconButton(onClick = { viewModel.setSearchQuery("") }) {
              Icon(Icons.Default.Clear, contentDescription = "Clear search", tint = CleanOnSurfaceVariant)
            }
          }
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedTextColor = CleanOnSurface,
          unfocusedTextColor = CleanOnSurface,
          focusedContainerColor = CleanSurface,
          unfocusedContainerColor = CleanSurface,
          focusedBorderColor = CleanPrimary,
          unfocusedBorderColor = CleanBorder,
          cursorColor = CleanPrimary
        ),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp)
          .testTag("food_search_field")
      )

      // Category Chips Row
      LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(categories) { category ->
          val isSelected = uiState.selectedCategory.equals(category, ignoreCase = true)
          FilterChip(
            selected = isSelected,
            onClick = { viewModel.setSelectedCategory(category) },
            label = {
              Text(
                text = category,
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

      Spacer(modifier = Modifier.height(4.dp))

      // Foods Grid
      if (uiState.filteredFoods.isEmpty()) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "No foods found",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = CleanOnSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Try searching for a different food or reset your category filter.",
              style = MaterialTheme.typography.bodyMedium,
              color = CleanOnSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = {
                viewModel.setSearchQuery("")
                viewModel.setSelectedCategory("All")
              },
              shape = RoundedCornerShape(14.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = CleanPrimary,
                contentColor = Color.White
              )
            ) {
              Text("Reset Filters", fontWeight = FontWeight.Bold, color = Color.White)
            }
          }
        }
      } else {
        LazyVerticalGrid(
          columns = GridCells.Fixed(2),
          contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 90.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier.fillMaxSize()
        ) {
          items(uiState.filteredFoods, key = { it.id }) { food ->
            FoodItemCard(
              food = food,
              onFoodClick = {
                viewModel.openFoodDetail(food)
                onFoodSelected(food)
              },
              onFavoriteToggle = {
                viewModel.toggleFavorite(food.id)
              }
            )
          }
        }
      }
    }
  }
}
