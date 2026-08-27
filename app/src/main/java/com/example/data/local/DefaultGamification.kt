package com.example.data.local

import com.example.data.local.entity.AchievementEntity
import com.example.model.DailyChallengeInfo

object DefaultAchievements {
  val list: List<AchievementEntity> = listOf(
    AchievementEntity(
      id = "first_log",
      title = "First Step",
      description = "Logged your very first food entry.",
      category = "Logging",
      iconName = "check_circle",
      targetCount = 1,
      currentProgress = 0,
      isUnlocked = false
    ),
    AchievementEntity(
      id = "consistent_3",
      title = "3-Day Rhythm",
      description = "Logged meals for 3 consecutive days.",
      category = "Streak",
      iconName = "local_fire_department",
      targetCount = 3,
      currentProgress = 0,
      isUnlocked = false
    ),
    AchievementEntity(
      id = "consistent_7",
      title = "Consistent",
      description = "Logged meals for 7 consecutive days.",
      category = "Streak",
      iconName = "local_fire_department",
      targetCount = 7,
      currentProgress = 0,
      isUnlocked = false
    ),
    AchievementEntity(
      id = "consistent_30",
      title = "30-Day Flow",
      description = "Maintained a steady mindful eating habit for 30 days.",
      category = "Streak",
      iconName = "lock",
      targetCount = 30,
      currentProgress = 0,
      isUnlocked = false
    ),
    AchievementEntity(
      id = "perfect_day",
      title = "Perfect Day",
      description = "Reached a 100/100 daily nutrition score.",
      category = "Nutrition",
      iconName = "workspace_premium",
      targetCount = 1,
      currentProgress = 0,
      isUnlocked = false
    ),
    AchievementEntity(
      id = "balanced_score_80",
      title = "Nurtured Body",
      description = "Achieved an 80+ daily nutrition score.",
      category = "Nutrition",
      iconName = "spa",
      targetCount = 1,
      currentProgress = 0,
      isUnlocked = false
    ),
    AchievementEntity(
      id = "fruit_lover_20",
      title = "Fruit Lover",
      description = "Logged 20 servings of wholesome fruits.",
      category = "Food",
      iconName = "nutrition",
      targetCount = 20,
      currentProgress = 0,
      isUnlocked = false
    ),
    AchievementEntity(
      id = "veggie_master_20",
      title = "Veggie Champion",
      description = "Logged 20 servings of fresh vegetables & greens.",
      category = "Food",
      iconName = "eco",
      targetCount = 20,
      currentProgress = 0,
      isUnlocked = false
    ),
    AchievementEntity(
      id = "hydrated_hero",
      title = "Hydrated",
      description = "Logged pure water or coconut water 5 times.",
      category = "Food",
      iconName = "water_drop",
      targetCount = 5,
      currentProgress = 0,
      isUnlocked = false
    ),
    AchievementEntity(
      id = "challenge_conqueror_5",
      title = "Challenge Champ",
      description = "Completed 5 daily wellness challenges.",
      category = "Challenge",
      iconName = "military_tech",
      targetCount = 5,
      currentProgress = 0,
      isUnlocked = false
    ),
    AchievementEntity(
      id = "desi_wholesome",
      title = "Local Flavors",
      description = "Logged 10 authentic Bangladesh wholesome foods.",
      category = "Food",
      iconName = "restaurant_menu",
      targetCount = 10,
      currentProgress = 0,
      isUnlocked = false
    )
  )
}

object DefaultChallenges {
  val list: List<DailyChallengeInfo> = listOf(
    DailyChallengeInfo(
      id = "veggie_boost",
      title = "Add a serving of veggies",
      description = "Include leafy greens, salad, or a vegetable curry with any meal today.",
      iconName = "temp_preferences_custom",
      bonusPoints = 5,
      requiredCategory = "vegetables"
    ),
    DailyChallengeInfo(
      id = "fruit_snack",
      title = "Choose fresh fruit for a snack",
      description = "Swap an afternoon processed snack with an apple, banana, or guava.",
      iconName = "nutrition",
      bonusPoints = 5,
      requiredCategory = "fruits"
    ),
    DailyChallengeInfo(
      id = "pure_hydration",
      title = "Drink pure water or coconut water",
      description = "Stay refreshed and nourish your body with clean, pure hydration.",
      iconName = "water_drop",
      bonusPoints = 5,
      requiredCategory = "drinks"
    ),
    DailyChallengeInfo(
      id = "whole_grain_choice",
      title = "Mindful Grain: Choose Roti or Dal",
      description = "Fuel your day with whole wheat roti or protein-rich lentil soup.",
      iconName = "lunch_dining",
      bonusPoints = 5,
      requiredCategory = "meals"
    ),
    DailyChallengeInfo(
      id = "all_main_meals",
      title = "Log all 3 main meals",
      description = "Check in with your body for breakfast, lunch, and dinner mindfully.",
      iconName = "task_alt",
      bonusPoints = 5,
      requiredCategory = null
    )
  )

  fun getChallengeForDate(dateString: String): DailyChallengeInfo {
    val hash = kotlin.math.abs(dateString.hashCode())
    return list[hash % list.size]
  }
}
