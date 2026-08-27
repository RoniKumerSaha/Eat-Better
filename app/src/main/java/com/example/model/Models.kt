package com.example.model

data class PortionOption(
  val id: String,
  val name: String, // e.g. "1 medium banana", "1/2 cup", "250 ml", "1 serving"
  val multiplier: Float, // weight multiplier for score contribution
  val calories: Int,
  val carbs: Float,
  val protein: Float,
  val fat: Float,
  val fiber: Float,
  val sugar: Float,
  val sodium: Float
)

data class FoodItem(
  val id: String,
  val name: String,
  val bengaliName: String? = null,
  val categoryId: String,
  val region: String,
  val baseScore: Int, // 1 to 10
  val educationalText: String,
  val scoreExplanation: List<String>,
  val searchAliases: List<String>,
  val portions: List<PortionOption>,
  val iconSymbol: String = "restaurant",
  val isFavorite: Boolean = false
)

data class DailyChallengeInfo(
  val id: String,
  val title: String,
  val description: String,
  val iconName: String,
  val bonusPoints: Int = 5,
  val requiredCategory: String? = null // e.g. "vegetables", "fruits", "water", "all_meals"
)

data class DayScoreSummary(
  val date: String,
  val score: Int,
  val nutritiousPercentage: Int,
  val loggedMealsCount: Int,
  val totalConfiguredMeals: Int,
  val isBreakfastLogged: Boolean,
  val isLunchLogged: Boolean,
  val isDinnerLogged: Boolean,
  val isSnackLogged: Boolean,
  val isBreakfastSkipped: Boolean,
  val isLunchSkipped: Boolean,
  val isDinnerSkipped: Boolean,
  val challengeCompleted: Boolean,
  val challenge: DailyChallengeInfo,
  val entries: List<LoggedEntryItem>
)

data class LoggedEntryItem(
  val id: Long,
  val foodId: String,
  val foodName: String,
  val categoryId: String,
  val portionName: String,
  val mealType: String,
  val baseScore: Int,
  val scoreMultiplier: Float,
  val calories: Int,
  val protein: Float,
  val carbs: Float,
  val fat: Float,
  val fiber: Float,
  val sugar: Float,
  val sodium: Float,
  val createdAt: Long
)
