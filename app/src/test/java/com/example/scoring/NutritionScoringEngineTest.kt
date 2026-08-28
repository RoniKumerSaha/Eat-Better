package com.example.scoring

import com.example.data.local.entity.FoodEntryEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for [NutritionScoringEngine].
 *
 * These tests document the engine's behavior so that future scoring changes can
 * be made confidently. The score is a heuristic wellness indicator — see the
 * engine's KDoc for the medical-advice disclaimer.
 */
class NutritionScoringEngineTest {

  private fun entry(
    baseScore: Int = 9,
    categoryId: String = "meals",
    foodId: String = "test_food",
    mealType: String = "Breakfast",
    multiplier: Float = 1.0f
  ): FoodEntryEntity = FoodEntryEntity(
    foodId = foodId,
    foodName = "Test",
    categoryId = categoryId,
    portionId = "single",
    portionName = "single",
    date = "2026-08-28",
    mealType = mealType,
    baseScore = baseScore,
    scoreMultiplier = multiplier,
    calories = 100,
    carbs = 10f,
    protein = 5f,
    fat = 2f,
    fiber = 1f,
    sugar = 1f,
    sodium = 50f
  )

  @Test
  fun `empty entries returns zero with No Logs Yet label`() {
    val result = NutritionScoringEngine.calculateDailyScore(
      entries = emptyList(),
      challengeCompleted = false,
      goals = emptySet()
    )
    assertEquals(0, result.score)
    assertEquals(0, result.nutritiousBalancePercentage)
    assertEquals("No Logs Yet", result.scoreLabel)
    assertNotNull(result.gentleGuidanceMessage)
    assertNotNull(result.actionableNextStep)
  }

  @Test
  fun `single low-score breakfast gives a low total score`() {
    val result = NutritionScoringEngine.calculateDailyScore(
      entries = listOf(entry(baseScore = 5, mealType = "Breakfast")),
      challengeCompleted = false,
      goals = emptySet(),
      configuredMealsCount = 3
    )
    // Coverage factor with 1 of 3 main meals covered = 0.75 + 0.25*(1/3) ≈ 0.833
    // 5 * 10 * 0.833 ≈ 41.6, rounded to 42.
    assertTrue("expected score <= 60, got ${result.score}", result.score <= 60)
  }

  @Test
  fun `full day of high-score meals with challenge and hydration gives high score`() {
    val result = NutritionScoringEngine.calculateDailyScore(
      entries = listOf(
        entry(baseScore = 9, mealType = "Breakfast"),
        entry(baseScore = 10, mealType = "Lunch", categoryId = "vegetables"),
        entry(baseScore = 9, mealType = "Lunch", categoryId = "fruits"),
        entry(baseScore = 9, mealType = "Dinner"),
        entry(baseScore = 10, mealType = "Snack", foodId = "pure_water", categoryId = "drinks")
      ),
      challengeCompleted = true,
      goals = emptySet(),
      configuredMealsCount = 3
    )
    assertTrue("expected score >= 80, got ${result.score}", result.score >= 80)
    assertTrue("expected Nurturing label, got ${result.scoreLabel}", result.scoreLabel == "Nurturing")
  }

  @Test
  fun `adding fruits and vegetables gives the documented bonus`() {
    val baseline = NutritionScoringEngine.calculateDailyScore(
      entries = listOf(
        entry(baseScore = 8, mealType = "Breakfast"),
        entry(baseScore = 8, mealType = "Lunch"),
        entry(baseScore = 8, mealType = "Dinner")
      ),
      challengeCompleted = false,
      goals = emptySet(),
      configuredMealsCount = 3
    )

    val withFruitAndGreens = NutritionScoringEngine.calculateDailyScore(
      entries = listOf(
        entry(baseScore = 8, mealType = "Breakfast"),
        entry(baseScore = 8, mealType = "Lunch"),
        entry(baseScore = 8, mealType = "Dinner"),
        entry(baseScore = 9, mealType = "Snack", categoryId = "fruits"),
        entry(baseScore = 9, mealType = "Snack", categoryId = "vegetables")
      ),
      challengeCompleted = false,
      goals = emptySet(),
      configuredMealsCount = 3
    )

    assertTrue(
      "expected withFruitAndGreens (${withFruitAndGreens.score}) > baseline (${baseline.score})",
      withFruitAndGreens.score > baseline.score
    )
    assertTrue(
      "expected a +3 diversity bonus; delta was ${withFruitAndGreens.score - baseline.score}",
      withFruitAndGreens.score - baseline.score >= 3
    )
  }

  @Test
  fun `marking a meal as skipped recovers the coverage credit`() {
    // Documenting the deliberate product decision that marking a meal as
    // skipped does NOT lower your daily score. The coverage factor counts
    // skipped meals toward "configured meals covered", so users who explicitly
    // skip a meal out of mindful intent are not punished for it.
    val withoutSkip = NutritionScoringEngine.calculateDailyScore(
      entries = listOf(
        entry(baseScore = 8, mealType = "Breakfast"),
        entry(baseScore = 8, mealType = "Lunch")
      ),
      challengeCompleted = false,
      goals = emptySet(),
      skippedMeals = emptySet(),
      configuredMealsCount = 3
    )

    val withSkip = NutritionScoringEngine.calculateDailyScore(
      entries = listOf(
        entry(baseScore = 8, mealType = "Breakfast"),
        entry(baseScore = 8, mealType = "Lunch")
      ),
      challengeCompleted = false,
      goals = emptySet(),
      skippedMeals = setOf("Dinner"),
      configuredMealsCount = 3
    )

    assertTrue(
      "marking a meal as skipped should restore coverage credit; " +
        "withoutSkip=${withoutSkip.score}, withSkip=${withSkip.score}",
      withSkip.score > withoutSkip.score
    )
  }
}