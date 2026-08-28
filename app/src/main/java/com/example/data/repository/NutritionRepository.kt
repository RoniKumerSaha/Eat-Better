package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.DefaultAchievements
import com.example.data.local.DefaultChallenges
import com.example.data.local.PrepopulatedFoods
import com.example.data.local.entity.AchievementEntity
import com.example.data.local.entity.DailyRecordEntity
import com.example.data.local.entity.FavoriteEntity
import com.example.data.local.entity.FoodEntity
import com.example.data.local.entity.FoodEntryEntity
import com.example.data.local.entity.UserSettingsEntity
import com.example.model.DailyChallengeInfo
import com.example.model.FoodItem
import com.example.model.PortionOption
import com.example.scoring.NutritionScoringEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NutritionRepository(private val db: AppDatabase) {

  private val foodDao = db.foodDao()
  private val entryDao = db.foodEntryDao()
  private val favDao = db.favoriteDao()
  private val recordDao = db.dailyRecordDao()
  private val achievementDao = db.achievementDao()
  private val settingsDao = db.userSettingsDao()

  init {
    CoroutineScope(Dispatchers.IO).launch {
      foodDao.insertFoods(PrepopulatedFoods.list)
      achievementDao.insertAchievements(DefaultAchievements.list)
    }
  }

  val allFoodsFlow: Flow<List<FoodItem>> = foodDao.getAllFoods().map { list ->
    list.map { parseFoodEntity(it) }
  }

  fun getFoodsByCategory(category: String): Flow<List<FoodItem>> {
    return if (category.lowercase() == "all") {
      allFoodsFlow
    } else {
      foodDao.getFoodsByCategory(category.lowercase()).map { list ->
        list.map { parseFoodEntity(it) }
      }
    }
  }

  fun searchFoods(query: String): Flow<List<FoodItem>> {
    return if (query.isBlank()) {
      allFoodsFlow
    } else {
      foodDao.searchFoods(query.trim()).map { list ->
        list.map { parseFoodEntity(it) }
      }
    }
  }

  suspend fun getFoodById(id: String): FoodItem? {
    val entity = foodDao.getFoodById(id) ?: return null
    val isFav = favDao.isFavorite(id).firstOrNull() ?: false
    return parseFoodEntity(entity).copy(isFavorite = isFav)
  }

  fun getAllFavorites(): Flow<List<String>> {
    return favDao.getAllFavorites().map { list -> list.map { it.foodId } }
  }

  suspend fun toggleFavorite(foodId: String) = withContext(Dispatchers.IO) {
    val isFav = favDao.isFavorite(foodId).firstOrNull() ?: false
    if (isFav) {
      favDao.removeFavorite(foodId)
    } else {
      favDao.addFavorite(FavoriteEntity(foodId))
    }
  }

  fun getEntriesForDate(date: String): Flow<List<FoodEntryEntity>> {
    return entryDao.getEntriesForDate(date)
  }

  fun getDailyRecord(date: String): Flow<DailyRecordEntity?> {
    return recordDao.getDailyRecord(date)
  }

  fun getAllDailyRecords(): Flow<List<DailyRecordEntity>> {
    return recordDao.getAllDailyRecords()
  }

  fun getUserSettings(): Flow<UserSettingsEntity?> {
    return settingsDao.getUserSettings()
  }

  suspend fun updateUserSettings(settings: UserSettingsEntity) = withContext(Dispatchers.IO) {
    settingsDao.insertOrUpdate(settings)
  }

  /**
   * Wipe all user-generated data and reset onboarding so the user starts
   * fresh. Keeps the seeded foods and default achievements on disk.
   */
  suspend fun clearAllUserData() = withContext(Dispatchers.IO) {
    entryDao.clearAllEntries()
    recordDao.clearAllRecords()
    favDao.clearAllFavorites()
    achievementDao.clearAllAchievements()
    settingsDao.clearAllSettings()
    // Re-seed default achievements so the Badges screen still has locked entries.
    achievementDao.insertAchievements(DefaultAchievements.list)
  }

  fun getAllAchievements(): Flow<List<AchievementEntity>> {
    return achievementDao.getAllAchievements()
  }

  fun getUnlockedAchievements(): Flow<List<AchievementEntity>> {
    return achievementDao.getUnlockedAchievements()
  }

  suspend fun logFood(
    food: FoodItem,
    portion: PortionOption,
    mealType: String,
    date: String
  ): Long = withContext(Dispatchers.IO) {
    val entry = FoodEntryEntity(
      foodId = food.id,
      foodName = food.name,
      categoryId = food.categoryId,
      portionId = portion.id,
      portionName = portion.name,
      date = date,
      mealType = mealType,
      baseScore = food.baseScore,
      scoreMultiplier = portion.multiplier,
      calories = portion.calories,
      carbs = portion.carbs,
      protein = portion.protein,
      fat = portion.fat,
      fiber = portion.fiber,
      sugar = portion.sugar,
      sodium = portion.sodium
    )
    val id = entryDao.insertEntry(entry)
    recalculateDailyScore(date)
    checkAndUpdateAchievements()
    id
  }

  suspend fun updateEntry(entry: FoodEntryEntity) = withContext(Dispatchers.IO) {
    entryDao.updateEntry(entry)
    recalculateDailyScore(entry.date)
    checkAndUpdateAchievements()
  }

  suspend fun deleteEntry(entry: FoodEntryEntity) = withContext(Dispatchers.IO) {
    entryDao.deleteEntry(entry)
    recalculateDailyScore(entry.date)
    checkAndUpdateAchievements()
  }

  suspend fun deleteEntryById(id: Long, date: String) = withContext(Dispatchers.IO) {
    entryDao.deleteEntryById(id)
    recalculateDailyScore(date)
    checkAndUpdateAchievements()
  }

  suspend fun toggleMealSkipped(date: String, mealType: String) = withContext(Dispatchers.IO) {
    val record = recordDao.getDailyRecordSync(date) ?: DailyRecordEntity(
      date = date,
      challengeId = DefaultChallenges.getChallengeForDate(date).id
    )
    val currentSkipped = record.skippedMeals.split(",").filter { it.isNotBlank() }.toMutableSet()
    if (currentSkipped.contains(mealType)) {
      currentSkipped.remove(mealType)
    } else {
      currentSkipped.add(mealType)
    }
    val updated = record.copy(skippedMeals = currentSkipped.joinToString(","))
    recordDao.upsertDailyRecord(updated)
    recalculateDailyScore(date)
    checkAndUpdateAchievements()
  }

  suspend fun setChallengeCompleted(date: String, completed: Boolean) = withContext(Dispatchers.IO) {
    val record = recordDao.getDailyRecordSync(date) ?: DailyRecordEntity(
      date = date,
      challengeId = DefaultChallenges.getChallengeForDate(date).id
    )
    val updated = record.copy(challengeCompleted = completed)
    recordDao.upsertDailyRecord(updated)
    recalculateDailyScore(date)
    checkAndUpdateAchievements()
  }

  suspend fun recalculateDailyScore(date: String) = withContext(Dispatchers.IO) {
    val entries = entryDao.getEntriesForDateSync(date)
    val record = recordDao.getDailyRecordSync(date) ?: DailyRecordEntity(
      date = date,
      challengeId = DefaultChallenges.getChallengeForDate(date).id
    )
    val settings = settingsDao.getUserSettingsSync() ?: UserSettingsEntity()

    val goals = settings.goals.split(",").map { it.trim() }.toSet()
    val skippedMeals = record.skippedMeals.split(",").filter { it.isNotBlank() }.toSet()

    var configuredMealsCount = 0
    if (settings.breakfastEnabled) configuredMealsCount++
    if (settings.lunchEnabled) configuredMealsCount++
    if (settings.dinnerEnabled) configuredMealsCount++

    // Auto-check challenge
    val challenge = DefaultChallenges.getChallengeForDate(date)
    var autoChallengeCompleted = record.challengeCompleted
    if (!autoChallengeCompleted && challenge.requiredCategory != null) {
      if (entries.any { it.categoryId == challenge.requiredCategory || (challenge.requiredCategory == "drinks" && (it.foodId == "pure_water" || it.foodId == "green_coconut_water")) }) {
        autoChallengeCompleted = true
      }
    }

    val result = NutritionScoringEngine.calculateDailyScore(
      entries = entries,
      challengeCompleted = autoChallengeCompleted,
      goals = goals,
      skippedMeals = skippedMeals,
      configuredMealsCount = configuredMealsCount
    )

    val updatedRecord = record.copy(
      score = result.score,
      nutritiousBalancePercentage = result.nutritiousBalancePercentage,
      challengeCompleted = autoChallengeCompleted,
      lastCalculatedAt = System.currentTimeMillis()
    )
    recordDao.upsertDailyRecord(updatedRecord)
  }

  suspend fun checkAndUpdateAchievements() = withContext(Dispatchers.IO) {
    val allEntries = entryDao.getAllEntries().firstOrNull() ?: emptyList()
    val allRecords = recordDao.getAllDailyRecords().firstOrNull() ?: emptyList()

    val totalLogs = allEntries.size
    val distinctDates = allEntries.map { it.date }.distinct().size
    val fruitsCount = allEntries.count { it.categoryId == "fruits" }
    val veggiesCount = allEntries.count { it.categoryId == "vegetables" }
    val waterCount = allEntries.count { it.foodId == "pure_water" || it.foodId == "green_coconut_water" }
    val desiCount = allEntries.count { it.foodId in listOf("moshur_dal", "palak_shak", "begun_bhaji", "rui_mach_jhol", "ilish_mach_bhaja_shorshe", "khichuri_bhuna", "atta_ruti", "green_coconut_water", "guava_peyara", "alu_bhorta") }
    val completedChallengesCount = allRecords.count { it.challengeCompleted }
    val maxScore = allRecords.maxOfOrNull { it.score } ?: 0

    // Unlock "First Step"
    if (totalLogs >= 1) unlockAchievement("first_log", 1)
    // Streak milestones
    if (distinctDates >= 3) unlockAchievement("consistent_3", distinctDates)
    if (distinctDates >= 7) unlockAchievement("consistent_7", distinctDates)
    if (distinctDates >= 30) unlockAchievement("consistent_30", distinctDates)
    // Nutrition milestones
    if (maxScore >= 100) unlockAchievement("perfect_day", 1)
    if (maxScore >= 80) unlockAchievement("balanced_score_80", 1)
    // Food milestones
    if (fruitsCount >= 20) unlockAchievement("fruit_lover_20", fruitsCount)
    if (veggiesCount >= 20) unlockAchievement("veggie_master_20", veggiesCount)
    if (waterCount >= 5) unlockAchievement("hydrated_hero", waterCount)
    if (desiCount >= 10) unlockAchievement("desi_wholesome", desiCount)
    if (completedChallengesCount >= 5) unlockAchievement("challenge_conqueror_5", completedChallengesCount)
  }

  private suspend fun unlockAchievement(id: String, progress: Int) {
    val existing = achievementDao.getAchievementById(id) ?: return
    if (!existing.isUnlocked) {
      val isNowUnlocked = progress >= existing.targetCount
      achievementDao.updateAchievement(
        existing.copy(
          currentProgress = progress,
          isUnlocked = isNowUnlocked,
          unlockedAt = if (isNowUnlocked) System.currentTimeMillis() else null
        )
      )
    }
  }

  /**
   * Initializes initial mock demo data (yesterday, day before yesterday, today)
   * so first launch immediately exhibits the rich 7-day streak, scores, charts, and badges!
   */
  suspend fun ensureInitialDemoData() = withContext(Dispatchers.IO) {
    val foodCount = foodDao.getFoodCount()
    if (foodCount == 0) {
      foodDao.insertFoods(PrepopulatedFoods.list)
      achievementDao.insertAchievements(DefaultAchievements.list)
      settingsDao.insertOrUpdate(UserSettingsEntity(displayName = "Rahim"))
    }

    val totalEntries = entryDao.getAllEntries().firstOrNull() ?: emptyList()
    if (totalEntries.isEmpty()) {
      val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
      val cal = Calendar.getInstance()

      // Today
      val todayStr = dateFormat.format(cal.time)

      // Yesterday
      cal.add(Calendar.DAY_OF_YEAR, -1)
      val yesterdayStr = dateFormat.format(cal.time)

      // 2 Days Ago
      cal.add(Calendar.DAY_OF_YEAR, -1)
      val twoDaysAgoStr = dateFormat.format(cal.time)

      // 3 to 6 days ago for historical trend
      val pastDates = mutableListOf<String>()
      for (i in 3..6) {
        cal.add(Calendar.DAY_OF_YEAR, -1)
        pastDates.add(dateFormat.format(cal.time))
      }

      // Populate 2 days ago
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "atta_ruti",
          foodName = "Whole Wheat Roti (Atta Ruti)",
          categoryId = "meals",
          portionId = "two_ruti",
          portionName = "2 Rotis (70g)",
          date = twoDaysAgoStr,
          mealType = "Breakfast",
          baseScore = 9,
          scoreMultiplier = 1.0f,
          calories = 150,
          carbs = 30f,
          protein = 6f,
          fat = 1f,
          fiber = 5f,
          sugar = 0.6f,
          sodium = 120f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "boiled_egg",
          foodName = "Whole Egg (Boiled / Poached)",
          categoryId = "meals",
          portionId = "two_eggs",
          portionName = "2 Large Eggs (100g)",
          date = twoDaysAgoStr,
          mealType = "Breakfast",
          baseScore = 10,
          scoreMultiplier = 1.0f,
          calories = 148,
          carbs = 0.8f,
          protein = 12.6f,
          fat = 10f,
          fiber = 0f,
          sugar = 0.4f,
          sodium = 130f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "rui_mach_jhol",
          foodName = "Rohu Fish Curry with Veggies (Rui Macher Jhol)",
          categoryId = "meals",
          portionId = "one_piece_gravy",
          portionName = "1 Fish Piece with Gravy (150g)",
          date = twoDaysAgoStr,
          mealType = "Lunch",
          baseScore = 9,
          scoreMultiplier = 0.8f,
          calories = 160,
          carbs = 5f,
          protein = 18f,
          fat = 7f,
          fiber = 1.5f,
          sugar = 1f,
          sodium = 240f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "steamed_white_rice",
          foodName = "Steamed Rice (Sada Bhat)",
          categoryId = "meals",
          portionId = "medium",
          portionName = "Medium serving (1.5 cups / 225g)",
          date = twoDaysAgoStr,
          mealType = "Lunch",
          baseScore = 7,
          scoreMultiplier = 1.0f,
          calories = 290,
          carbs = 64f,
          protein = 6f,
          fat = 0.6f,
          fiber = 0.9f,
          sugar = 0.1f,
          sodium = 3f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "palak_shak",
          foodName = "Spinach Shak Bhaji (Palong / Lal Shak)",
          categoryId = "vegetables",
          portionId = "med_katori",
          portionName = "Medium Serving (1 cup)",
          date = twoDaysAgoStr,
          mealType = "Lunch",
          baseScore = 10,
          scoreMultiplier = 1.0f,
          calories = 85,
          carbs = 7f,
          protein = 4.2f,
          fat = 4.8f,
          fiber = 4f,
          sugar = 1f,
          sodium = 190f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "moshur_dal",
          foodName = "Red Lentil Soup (Moshur Dal)",
          categoryId = "meals",
          portionId = "regular_bowl",
          portionName = "1 Regular Bowl (1 cup / 200ml)",
          date = twoDaysAgoStr,
          mealType = "Dinner",
          baseScore = 9,
          scoreMultiplier = 1.0f,
          calories = 150,
          carbs = 24f,
          protein = 10f,
          fat = 2.5f,
          fiber = 7f,
          sugar = 1.5f,
          sodium = 210f
        )
      )

      // Populate Yesterday (Score ~88)
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "fuji_apple",
          foodName = "Fuji Apple",
          categoryId = "fruits",
          portionId = "medium",
          portionName = "1 Medium Apple (180g)",
          date = yesterdayStr,
          mealType = "Breakfast",
          baseScore = 9,
          scoreMultiplier = 1.0f,
          calories = 95,
          carbs = 25f,
          protein = 0.5f,
          fat = 0.3f,
          fiber = 4.4f,
          sugar = 19f,
          sodium = 2f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "greek_yogurt_plain",
          foodName = "Greek Yogurt (Plain / Tok Doi)",
          categoryId = "meals",
          portionId = "one_cup",
          portionName = "1 Cup (200g)",
          date = yesterdayStr,
          mealType = "Breakfast",
          baseScore = 10,
          scoreMultiplier = 1.0f,
          calories = 130,
          carbs = 7f,
          protein = 18f,
          fat = 2f,
          fiber = 0f,
          sugar = 6f,
          sodium = 80f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "khichuri_bhuna",
          foodName = "Bhuna Khichuri (Rice & Lentil Pot)",
          categoryId = "meals",
          portionId = "medium_plate",
          portionName = "Medium Plate (320g)",
          date = yesterdayStr,
          mealType = "Lunch",
          baseScore = 8,
          scoreMultiplier = 1.0f,
          calories = 410,
          carbs = 66f,
          protein = 14f,
          fat = 10.5f,
          fiber = 6.2f,
          sugar = 2.5f,
          sodium = 450f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "begun_bhaji",
          foodName = "Fried Eggplant (Begun Bhaji)",
          categoryId = "vegetables",
          portionId = "two_slices",
          portionName = "2 Slices (100g)",
          date = yesterdayStr,
          mealType = "Lunch",
          baseScore = 7,
          scoreMultiplier = 1.0f,
          calories = 130,
          carbs = 8f,
          protein = 1.6f,
          fat = 10.4f,
          fiber = 3f,
          sugar = 4f,
          sodium = 160f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "green_coconut_water",
          foodName = "Green Coconut Water (Daab)",
          categoryId = "drinks",
          portionId = "glass",
          portionName = "1 Glass (250ml)",
          date = yesterdayStr,
          mealType = "Snack",
          baseScore = 10,
          scoreMultiplier = 1.0f,
          calories = 45,
          carbs = 9f,
          protein = 1f,
          fat = 0.2f,
          fiber = 1.1f,
          sugar = 6f,
          sodium = 60f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "mixed_vegetable_labra",
          foodName = "Mixed Vegetable (Labra / Sobji Curry)",
          categoryId = "vegetables",
          portionId = "medium_bowl",
          portionName = "1 Medium Bowl (250g)",
          date = yesterdayStr,
          mealType = "Dinner",
          baseScore = 9,
          scoreMultiplier = 1.0f,
          calories = 140,
          carbs = 20f,
          protein = 4f,
          fat = 5.2f,
          fiber = 6f,
          sugar = 5f,
          sodium = 260f
        )
      )

      // Populate Today (Score 84)
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "atta_ruti",
          foodName = "Whole Wheat Roti (Atta Ruti)",
          categoryId = "meals",
          portionId = "two_ruti",
          portionName = "2 Rotis (70g)",
          date = todayStr,
          mealType = "Breakfast",
          baseScore = 9,
          scoreMultiplier = 1.0f,
          calories = 150,
          carbs = 30f,
          protein = 6f,
          fat = 1f,
          fiber = 5f,
          sugar = 0.6f,
          sodium = 120f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "boiled_egg",
          foodName = "Whole Egg (Boiled / Poached)",
          categoryId = "meals",
          portionId = "two_eggs",
          portionName = "2 Large Eggs (100g)",
          date = todayStr,
          mealType = "Breakfast",
          baseScore = 10,
          scoreMultiplier = 1.0f,
          calories = 148,
          carbs = 0.8f,
          protein = 12.6f,
          fat = 10f,
          fiber = 0f,
          sugar = 0.4f,
          sodium = 130f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "palak_shak",
          foodName = "Spinach Shak Bhaji (Palong / Lal Shak)",
          categoryId = "vegetables",
          portionId = "med_katori",
          portionName = "Medium Serving (1 cup)",
          date = todayStr,
          mealType = "Breakfast",
          baseScore = 10,
          scoreMultiplier = 1.0f,
          calories = 85,
          carbs = 7f,
          protein = 4.2f,
          fat = 4.8f,
          fiber = 4f,
          sugar = 1f,
          sodium = 190f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "rui_mach_jhol",
          foodName = "Rohu Fish Curry with Veggies (Rui Macher Jhol)",
          categoryId = "meals",
          portionId = "one_piece_gravy",
          portionName = "1 Fish Piece with Gravy (150g)",
          date = todayStr,
          mealType = "Lunch",
          baseScore = 9,
          scoreMultiplier = 0.8f,
          calories = 160,
          carbs = 5f,
          protein = 18f,
          fat = 7f,
          fiber = 1.5f,
          sugar = 1f,
          sodium = 240f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "steamed_white_rice",
          foodName = "Steamed Rice (Sada Bhat)",
          categoryId = "meals",
          portionId = "medium",
          portionName = "Medium serving (1.5 cups / 225g)",
          date = todayStr,
          mealType = "Lunch",
          baseScore = 7,
          scoreMultiplier = 1.0f,
          calories = 290,
          carbs = 64f,
          protein = 6f,
          fat = 0.6f,
          fiber = 0.9f,
          sugar = 0.1f,
          sodium = 3f
        )
      )
      entryDao.insertEntry(
        FoodEntryEntity(
          foodId = "moshur_dal",
          foodName = "Red Lentil Soup (Moshur Dal)",
          categoryId = "meals",
          portionId = "regular_bowl",
          portionName = "1 Regular Bowl (1 cup / 200ml)",
          date = todayStr,
          mealType = "Dinner",
          baseScore = 9,
          scoreMultiplier = 1.0f,
          calories = 150,
          carbs = 24f,
          protein = 10f,
          fat = 2.5f,
          fiber = 7f,
          sugar = 1.5f,
          sodium = 210f
        )
      )

      // Past records for streak
      val historicalScores = listOf(78, 82, 85, 76)
      pastDates.forEachIndexed { index, dateStr ->
        val s = historicalScores.getOrElse(index) { 80 }
        recordDao.upsertDailyRecord(
          DailyRecordEntity(
            date = dateStr,
            score = s,
            nutritiousBalancePercentage = 75,
            challengeCompleted = true
          )
        )
      }

      // Recalculate recent dates
      recalculateDailyScore(twoDaysAgoStr)
      recalculateDailyScore(yesterdayStr)
      recalculateDailyScore(todayStr)

      // Evaluate achievements against the seeded data so badges unlock correctly
      // on first launch (previously hardcoded unlock calls skipped the engine and
      // left `currentProgress = 0` on every badge).
      checkAndUpdateAchievements()
    }
  }

  private fun parseFoodEntity(entity: FoodEntity): FoodItem {
    val portionsList = mutableListOf<PortionOption>()
    try {
      val jsonArray = JSONArray(entity.portionsJson)
      for (i in 0 until jsonArray.length()) {
        val obj = jsonArray.getJSONObject(i)
        portionsList.add(
          PortionOption(
            id = obj.getString("id"),
            name = obj.getString("name"),
            multiplier = obj.getDouble("multiplier").toFloat(),
            calories = obj.getInt("calories"),
            carbs = obj.getDouble("carbs").toFloat(),
            protein = obj.getDouble("protein").toFloat(),
            fat = obj.getDouble("fat").toFloat(),
            fiber = obj.getDouble("fiber").toFloat(),
            sugar = obj.getDouble("sugar").toFloat(),
            sodium = obj.getDouble("sodium").toFloat()
          )
        )
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }

    return FoodItem(
      id = entity.id,
      name = entity.name,
      bengaliName = entity.bengaliName,
      categoryId = entity.categoryId,
      region = entity.region,
      baseScore = entity.baseScore,
      educationalText = entity.educationalText,
      scoreExplanation = entity.scoreExplanation.split("\n").filter { it.isNotBlank() },
      searchAliases = entity.searchAliases.split(",").map { it.trim() },
      portions = portionsList,
      iconSymbol = entity.iconSymbol
    )
  }
}
