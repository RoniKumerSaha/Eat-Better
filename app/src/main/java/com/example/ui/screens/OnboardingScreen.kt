package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CleanBackground
import com.example.ui.theme.CleanBorder
import com.example.ui.theme.CleanOnSurface
import com.example.ui.theme.CleanOnSurfaceVariant
import com.example.ui.theme.CleanPillAccent
import com.example.ui.theme.CleanPrimary
import com.example.ui.theme.CleanSubtleText
import com.example.ui.theme.CleanSurface

@Composable
fun OnboardingScreen(
  onComplete: (displayName: String, goals: List<String>, breakfast: Boolean, lunch: Boolean, dinner: Boolean, snacks: Boolean) -> Unit
) {
  val defaultGoal = stringResource(R.string.onboarding_default_goal)
  val goalEatHealthier = stringResource(R.string.settings_goal_eat_healthier)
  val goalImproveVariety = stringResource(R.string.settings_goal_improve_variety)
  val goalManageWeight = stringResource(R.string.settings_goal_manage_weight)

  var step by remember { mutableStateOf(1) }
  var name by remember { mutableStateOf("") }
  val selectedGoals = remember {
    mutableStateListOf(goalEatHealthier, goalImproveVariety)
  }
  var breakfastChecked by remember { mutableStateOf(true) }
  var lunchChecked by remember { mutableStateOf(true) }
  var dinnerChecked by remember { mutableStateOf(true) }
  var snacksChecked by remember { mutableStateOf(true) }

  Surface(
    modifier = Modifier
      .fillMaxSize()
      .statusBarsPadding()
      .navigationBarsPadding(),
    color = CleanBackground
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)
        .verticalScroll(rememberScrollState()),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
      ) {
        // App Logo / Icon Header
        Box(
          modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(CleanPillAccent),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Spa,
            contentDescription = stringResource(R.string.onboarding_eat_better_content_description),
            tint = CleanPrimary,
            modifier = Modifier.size(38.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = stringResource(R.string.app_name),
          style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.5).sp
          ),
          color = CleanPrimary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = stringResource(R.string.onboarding_tagline),
          style = MaterialTheme.typography.bodyMedium,
          textAlign = TextAlign.Center,
          color = CleanOnSurfaceVariant
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Step 1: Philosophy & Core Concept
        if (step == 1) {
          Surface(
            shape = RoundedCornerShape(24.dp),
            color = CleanSurface,
            modifier = Modifier
              .fillMaxWidth()
              .border(1.dp, CleanBorder, RoundedCornerShape(24.dp))
          ) {
            Column(modifier = Modifier.padding(20.dp)) {
              Text(
                text = stringResource(R.string.onboarding_how_it_works_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = CleanOnSurface
              )
              Spacer(modifier = Modifier.height(12.dp))

              OnboardingBullet(
                title = stringResource(R.string.onboarding_bullet_score_title),
                desc = stringResource(R.string.onboarding_bullet_score_desc)
              )
              Spacer(modifier = Modifier.height(10.dp))
              OnboardingBullet(
                title = stringResource(R.string.onboarding_bullet_offline_title),
                desc = stringResource(R.string.onboarding_bullet_offline_desc)
              )
              Spacer(modifier = Modifier.height(10.dp))
              OnboardingBullet(
                title = stringResource(R.string.onboarding_bullet_gentle_title),
                desc = stringResource(R.string.onboarding_bullet_gentle_desc)
              )
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          // Name Input with explicit visible text colors
          OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.onboarding_name_label), color = CleanOnSurfaceVariant) },
            placeholder = { Text(stringResource(R.string.onboarding_name_placeholder), color = CleanSubtleText) },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("onboarding_name_input"),
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

        // Step 2: Personal Goals & Usual Meals
        if (step == 2) {
          Text(
            text = stringResource(R.string.onboarding_step_two_goals_header),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = CleanOnSurface,
            modifier = Modifier.align(Alignment.Start)
          )
          Spacer(modifier = Modifier.height(10.dp))

          val allGoals = listOf(
            goalEatHealthier,
            goalManageWeight,
            goalImproveVariety
          )

          allGoals.forEach { goal ->
            val isSelected = selectedGoals.contains(goal)
            GoalSelectCard(
              title = goal,
              isSelected = isSelected,
              onClick = {
                if (isSelected) selectedGoals.remove(goal)
                else selectedGoals.add(goal)
              }
            )
            Spacer(modifier = Modifier.height(8.dp))
          }

          Spacer(modifier = Modifier.height(18.dp))

          Text(
            text = stringResource(R.string.onboarding_step_two_meals_header),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = CleanOnSurface,
            modifier = Modifier.align(Alignment.Start)
          )
          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            MealCheckChip(stringResource(R.string.settings_meal_breakfast), breakfastChecked) { breakfastChecked = it }
            MealCheckChip(stringResource(R.string.settings_meal_lunch), lunchChecked) { lunchChecked = it }
          }
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            MealCheckChip(stringResource(R.string.settings_meal_dinner), dinnerChecked) { dinnerChecked = it }
            MealCheckChip(stringResource(R.string.settings_meal_snacks), snacksChecked) { snacksChecked = it }
          }

          Spacer(modifier = Modifier.height(18.dp))

          // Medical advice disclaimer
          Text(
            text = stringResource(R.string.onboarding_disclaimer),
            style = MaterialTheme.typography.bodySmall,
            color = CleanSubtleText,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
          )
        }
      }

      // Bottom Action Button (White text on Green background)
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 24.dp)
      ) {
        Button(
          onClick = {
            if (step == 1) {
              step = 2
            } else {
              onComplete(
                name,
                if (selectedGoals.isEmpty()) listOf(defaultGoal) else selectedGoals.toList(),
                breakfastChecked,
                lunchChecked,
                dinnerChecked,
                snacksChecked
              )
            }
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("onboarding_continue_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = CleanPrimary,
            contentColor = Color.White
          )
        ) {
          Text(
            text = if (step == 1) stringResource(R.string.action_continue) else stringResource(R.string.action_get_started),
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp),
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }
      }
    }
  }
}

@Composable
private fun OnboardingBullet(title: String, desc: String) {
  Row(verticalAlignment = Alignment.Top) {
    Box(
      modifier = Modifier
        .padding(top = 4.dp)
        .size(6.dp)
        .clip(CircleShape)
        .background(CleanPrimary)
    )
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        color = CleanOnSurface
      )
      Text(
        text = desc,
        style = MaterialTheme.typography.bodySmall,
        color = CleanOnSurfaceVariant
      )
    }
  }
}

@Composable
private fun GoalSelectCard(
  title: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .clickable { onClick() }
      .border(
        width = if (isSelected) 1.5.dp else 1.dp,
        color = if (isSelected) CleanPrimary else CleanBorder,
        shape = RoundedCornerShape(14.dp)
      ),
    shape = RoundedCornerShape(14.dp),
    color = if (isSelected) CleanPillAccent.copy(alpha = 0.5f) else CleanSurface
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
        color = CleanOnSurface
      )

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
private fun MealCheckChip(
  label: String,
  isChecked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Surface(
    modifier = Modifier
      .width(160.dp)
      .clip(RoundedCornerShape(14.dp))
      .clickable { onCheckedChange(!isChecked) }
      .border(
        width = 1.dp,
        color = if (isChecked) CleanPrimary else CleanBorder,
        shape = RoundedCornerShape(14.dp)
      ),
    shape = RoundedCornerShape(14.dp),
    color = if (isChecked) CleanPillAccent.copy(alpha = 0.4f) else CleanSurface
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Checkbox(
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(
          checkedColor = CleanPrimary,
          checkmarkColor = Color.White
        )
      )
      Text(
        text = label,
        style = MaterialTheme.typography.bodyMedium,
        color = CleanOnSurface
      )
    }
  }
}
