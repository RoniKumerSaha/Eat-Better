package com.example.scoring

import com.example.data.local.entity.FoodEntryEntity
import kotlin.math.min
import kotlin.math.roundToInt

object NutritionScoringEngine {

  data class ScoreCalculationResult(
    val score: Int, // 0 - 100
    val nutritiousBalancePercentage: Int, // 0 - 100
    val scoreLabel: String, // "Balanced", "Nurturing", "Mindful", "Good Start", "No Logs"
    val gentleGuidanceMessage: String,
    val actionableNextStep: String
  )

  /**
   * Calculates 0-100 Nutrition Score and coaching feedback.
   */
  fun calculateDailyScore(
    entries: List<FoodEntryEntity>,
    challengeCompleted: Boolean,
    goals: Set<String>,
    skippedMeals: Set<String> = emptySet(),
    configuredMealsCount: Int = 3
  ): ScoreCalculationResult {
    if (entries.isEmpty()) {
      return ScoreCalculationResult(
        score = 0,
        nutritiousBalancePercentage = 0,
        scoreLabel = "No Logs Yet",
        gentleGuidanceMessage = "Welcome! Tap the + button to log your first meal of the day.",
        actionableNextStep = "Start by logging your breakfast or morning water."
      )
    }

    var totalWeightedPoints = 0.0
    var totalWeight = 0.0
    var nutritiousWeight = 0.0
    var lessNutritiousWeight = 0.0

    var fruitsCount = 0
    var veggiesCount = 0
    var waterLogged = false
    val loggedMealTypes = mutableSetOf<String>()

    for (entry in entries) {
      val portionMultiplier = entry.scoreMultiplier.coerceAtLeast(0.3f)
      val baseScoreScaled = entry.baseScore * 10.0 // Scaled to 10 - 100 scale

      // Weight factor
      val weight = portionMultiplier.toDouble()
      totalWeightedPoints += (baseScoreScaled * weight)
      totalWeight += weight

      if (entry.baseScore >= 6) {
        nutritiousWeight += weight
      } else {
        lessNutritiousWeight += weight
      }

      if (entry.categoryId == "fruits") fruitsCount++
      if (entry.categoryId == "vegetables") veggiesCount++
      if (entry.foodId == "pure_water" || entry.foodId == "green_coconut_water") waterLogged = true
      loggedMealTypes.add(entry.mealType)
    }

    // Weighted average quality (10 to 100)
    val averageQuality = if (totalWeight > 0) totalWeightedPoints / totalWeight else 0.0

    // Meal coverage bonus/factor
    val effectiveLoggedMainMeals = loggedMealTypes.count { it != "Snack" }
    val effectiveSkippedCount = skippedMeals.size
    val totalCovered = min(effectiveLoggedMainMeals + effectiveSkippedCount, configuredMealsCount)
    val coverageFactor = if (configuredMealsCount > 0) {
      0.75 + (0.25 * (totalCovered.toDouble() / configuredMealsCount))
    } else 1.0

    var rawScore = averageQuality * coverageFactor

    // Challenge bonus (+5 pts)
    if (challengeCompleted) {
      rawScore += 5.0
    }

    // Gentle diversity bonus (+2 for greens/fruits + water)
    if (veggiesCount > 0 && fruitsCount > 0) {
      rawScore += 3.0
    }
    if (waterLogged) {
      rawScore += 2.0
    }

    val finalScore = min(100, rawScore.roundToInt()).coerceAtLeast(0)

    val nutritiousPercentage = if (totalWeight > 0) {
      min(100, ((nutritiousWeight / totalWeight) * 100).roundToInt())
    } else 0

    val scoreLabel = when {
      finalScore >= 85 -> "Nurturing"
      finalScore >= 70 -> "Balanced"
      finalScore >= 50 -> "Mindful"
      finalScore > 0 -> "Good Start"
      else -> "No Logs Yet"
    }

    // Coaching Guidance Logic
    val isWeightManagement = goals.contains("Manage weight")
    val guidance: String
    val nextStep: String

    when {
      finalScore >= 85 -> {
        guidance = if (veggiesCount > 0) {
          "Excellent variety today! You're nourishing your body with wholesome, fiber-rich choices."
        } else {
          "Great job logging your meals today! You're on track for a high score."
        }
        nextStep = "Keep leaning into those fresh greens and stay hydrated with pure water."
      }
      finalScore >= 70 -> {
        guidance = if (loggedMealTypes.contains("Breakfast")) {
          "Great start on your day! Your choices are balanced and supporting steady energy."
        } else {
          "Solid balance today! You're making thoughtful choices that fuel your body well."
        }
        nextStep = if (veggiesCount == 0) {
          "Try adding a side of colorful vegetables or leafy greens to your next meal."
        } else if (!waterLogged) {
          "Remember to hydrate with a refreshing glass of water or green coconut water."
        } else {
          "Keep listening to your natural fullness cues for dinner."
        }
      }
      finalScore >= 50 -> {
        guidance = "You're building mindful awareness of your meals. Remember, one meal doesn't define your day."
        nextStep = if (isWeightManagement) {
          "For your next choice, consider a high-protein or high-fiber food like dal, boiled egg, or an apple."
        } else {
          "Consider balancing your next meal with a fresh fruit or light vegetable curry."
        }
      }
      else -> {
        guidance = "Every log is a step toward mindful eating. Be proud of taking time to check in with yourself."
        nextStep = "Try adding a serving of fresh fruit or seasonal vegetables next."
      }
    }

    return ScoreCalculationResult(
      score = finalScore,
      nutritiousBalancePercentage = nutritiousPercentage,
      scoreLabel = scoreLabel,
      gentleGuidanceMessage = guidance,
      actionableNextStep = nextStep
    )
  }
}
