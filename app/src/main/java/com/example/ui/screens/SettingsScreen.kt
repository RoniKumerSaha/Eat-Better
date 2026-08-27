package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.UserSettingsEntity
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
fun SettingsScreen(
  viewModel: EatBetterViewModel,
  uiState: UiState,
  onNavigateBack: () -> Unit
) {
  val settings = uiState.userSettings
  var displayName by remember(settings.displayName) { mutableStateOf(settings.displayName) }
  var breakfastEnabled by remember(settings.breakfastEnabled) { mutableStateOf(settings.breakfastEnabled) }
  var lunchEnabled by remember(settings.lunchEnabled) { mutableStateOf(settings.lunchEnabled) }
  var dinnerEnabled by remember(settings.dinnerEnabled) { mutableStateOf(settings.dinnerEnabled) }
  var snacksEnabled by remember(settings.snacksEnabled) { mutableStateOf(settings.snacksEnabled) }

  val selectedGoals = remember(settings.goals) {
    val list = settings.goals.split(",").map { it.trim() }.filter { it.isNotBlank() }
    mutableStateListOf<String>().apply { addAll(list) }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = CleanBackground
        ),
        title = {
          Text(
            text = "Settings & Privacy",
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
    bottomBar = {
      Surface(
        color = CleanSurface,
        tonalElevation = 0.dp,
        modifier = Modifier
          .fillMaxWidth()
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
              viewModel.updateSettings(
                settings.copy(
                  displayName = displayName.trim(),
                  goals = selectedGoals.joinToString(","),
                  breakfastEnabled = breakfastEnabled,
                  lunchEnabled = lunchEnabled,
                  dinnerEnabled = dinnerEnabled,
                  snacksEnabled = snacksEnabled
                )
              )
              onNavigateBack()
            },
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("save_settings_button"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = CleanPrimary,
              contentColor = Color.White
            )
          ) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = null,
              tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Save Preferences",
              style = MaterialTheme.typography.labelLarge.copy(fontSize = 15.sp),
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

      // Profile Section
      item {
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = CleanSurface,
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Person, contentDescription = null, tint = CleanPrimary)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Profile Name",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = CleanOnSurface
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
              value = displayName,
              onValueChange = { displayName = it },
              placeholder = { Text("e.g. Rahim", color = CleanSubtleText) },
              singleLine = true,
              modifier = Modifier
                .fillMaxWidth()
                .testTag("profile_name_input"),
              shape = RoundedCornerShape(14.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = CleanOnSurface,
                unfocusedTextColor = CleanOnSurface,
                focusedPlaceholderColor = CleanSubtleText,
                unfocusedPlaceholderColor = CleanSubtleText,
                focusedBorderColor = CleanPrimary,
                focusedLabelColor = CleanPrimary,
                unfocusedBorderColor = CleanBorder,
                cursorColor = CleanPrimary,
                focusedContainerColor = CleanSurface,
                unfocusedContainerColor = CleanSurface
              )
            )
          }
        }
      }

      // Goals Section
      item {
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = CleanSurface,
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Text(
              text = "Personal Wellness Goals",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = CleanOnSurface
            )
            Spacer(modifier = Modifier.height(10.dp))

            val allGoals = listOf(
              "Eat healthier overall",
              "Manage weight gently",
              "Improve nutrient variety & fiber",
              "Build positive mindful eating habits"
            )

            allGoals.forEach { goal ->
              val isSelected = selectedGoals.contains(goal)
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable {
                    if (isSelected) selectedGoals.remove(goal)
                    else selectedGoals.add(goal)
                  }
                  .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Checkbox(
                  checked = isSelected,
                  onCheckedChange = { checked ->
                    if (checked) selectedGoals.add(goal) else selectedGoals.remove(goal)
                  },
                  colors = CheckboxDefaults.colors(
                    checkedColor = CleanPrimary,
                    checkmarkColor = Color.White
                  )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = goal,
                  style = MaterialTheme.typography.bodyMedium,
                  color = CleanOnSurface
                )
              }
            }
          }
        }
      }

      // Configured Meals Section
      item {
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = CleanSurface,
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Restaurant, contentDescription = null, tint = CleanPrimary)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Active Meal Schedules",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = CleanOnSurface
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            MealToggleRow("Breakfast", breakfastEnabled) { breakfastEnabled = it }
            MealToggleRow("Lunch", lunchEnabled) { lunchEnabled = it }
            MealToggleRow("Dinner", dinnerEnabled) { dinnerEnabled = it }
            MealToggleRow("Snacks", snacksEnabled) { snacksEnabled = it }
          }
        }
      }

      // Offline & Privacy Guarantee Card
      item {
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = CleanSurfaceLow,
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Security, contentDescription = null, tint = CleanPrimary)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "100% Offline & Private",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = CleanOnSurface
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = "Eat Better operates entirely on your device. Your food logs, nutrition scores, and daily milestones are saved in a local SQLite Room database and are never uploaded to any remote server.",
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

@Composable
private fun MealToggleRow(
  label: String,
  isChecked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodyMedium,
      color = CleanOnSurface
    )
    Switch(
      checked = isChecked,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = Color.White,
        checkedTrackColor = CleanPrimary,
        uncheckedTrackColor = CleanBorder
      )
    )
  }
}

