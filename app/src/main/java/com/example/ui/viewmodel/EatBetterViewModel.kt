package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.DefaultChallenges
import com.example.data.local.entity.AchievementEntity
import com.example.data.local.entity.DailyRecordEntity
import com.example.data.local.entity.FoodEntryEntity
import com.example.data.local.entity.UserSettingsEntity
import com.example.data.repository.NutritionRepository
import com.example.model.DailyChallengeInfo
import com.example.model.FoodItem
import com.example.model.PortionOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class UiState(
  val currentScreen: String = "home", // "onboarding", "home", "foods", "progress", "achievements", "food_detail", "log_history", "settings"
  val activeDate: String = "",
  val todayDate: String = "",
  val streakDays: Int = 7,
  val searchQuery: String = "",
  val selectedCategory: String = "All",
  val savedOnly: Boolean = false,
  val allFoods: List<FoodItem> = emptyList(),
  val filteredFoods: List<FoodItem> = emptyList(),
  val favorites: Set<String> = emptySet(),
  val selectedFood: FoodItem? = null,
  val selectedPortion: PortionOption? = null,
  val selectedMealType: String = "Breakfast",
  val selectedLogDate: String = "",
  val achievements: List<AchievementEntity> = emptyList(),
  val unlockedAchievements: List<AchievementEntity> = emptyList(),
  val selectedAchievementForModal: AchievementEntity? = null,
  val userSettings: UserSettingsEntity = UserSettingsEntity(),
  val showShareDialog: Boolean = false,
  val showCelebrationDialog: Boolean = false,
  val celebrationMessage: String = "",
  val weeklyRecords: List<DailyRecordEntity> = emptyList()
)

private data class PersistenceState(
  val settings: UserSettingsEntity,
  val favorites: Set<String>,
  val achievements: List<AchievementEntity>,
  val records: List<DailyRecordEntity>
)

private data class NavigationAndFilterState(
  val screen: String,
  val activeDate: String,
  val todayDate: String,
  val query: String,
  val category: String,
  val savedOnly: Boolean
)

private data class InteractionState(
  val selectedFood: FoodItem?,
  val selectedPortion: PortionOption?,
  val selectedMealType: String,
  val selectedLogDate: String,
  val selectedAchievementForModal: AchievementEntity?,
  val showShareDialog: Boolean,
  val showCelebrationDialog: Boolean,
  val celebrationMessage: String
)

class EatBetterViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: NutritionRepository
  private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

  private val _todayDate = MutableStateFlow(dateFormat.format(Date()))
  private val _activeDate = MutableStateFlow(dateFormat.format(Date()))
  private val _currentScreen = MutableStateFlow("home")
  private val _searchQuery = MutableStateFlow("")
  private val _selectedCategory = MutableStateFlow("All")
  private val _savedOnly = MutableStateFlow(false)
  private val _selectedFood = MutableStateFlow<FoodItem?>(null)
  private val _selectedPortion = MutableStateFlow<PortionOption?>(null)
  private val _selectedMealType = MutableStateFlow("Breakfast")
  private val _selectedLogDate = MutableStateFlow(dateFormat.format(Date()))
  private val _selectedAchievementForModal = MutableStateFlow<AchievementEntity?>(null)
  private val _showShareDialog = MutableStateFlow(false)
  private val _showCelebrationDialog = MutableStateFlow(false)
  private val _celebrationMessage = MutableStateFlow("")

  init {
    val database = AppDatabase.getDatabase(application, viewModelScope)
    repository = NutritionRepository(database)
    viewModelScope.launch {
      repository.ensureInitialDemoData()
    }
  }

  val allFoodsFlow: Flow<List<FoodItem>> = repository.allFoodsFlow
  val favoritesFlow: Flow<Set<String>> = repository.getAllFavorites().map { it.toSet() }
  val settingsFlow: Flow<UserSettingsEntity> = repository.getUserSettings().map { it ?: UserSettingsEntity() }
  val achievementsFlow: Flow<List<AchievementEntity>> = repository.getAllAchievements()
  val allRecordsFlow: Flow<List<DailyRecordEntity>> = repository.getAllDailyRecords()

  private val persistenceStateFlow: Flow<PersistenceState> = combine(
    settingsFlow,
    favoritesFlow,
    achievementsFlow,
    allRecordsFlow
  ) { settings, favs, achievements, records ->
    PersistenceState(settings, favs, achievements, records)
  }

  private val navigationAndFilterFlow: Flow<NavigationAndFilterState> = combine(
    _currentScreen,
    _activeDate,
    _todayDate,
    _searchQuery,
    combine(_selectedCategory, _savedOnly) { cat, saved -> Pair(cat, saved) }
  ) { screen, activeDate, todayDate, query, categoryAndSaved ->
    NavigationAndFilterState(
      screen = screen,
      activeDate = activeDate,
      todayDate = todayDate,
      query = query,
      category = categoryAndSaved.first,
      savedOnly = categoryAndSaved.second
    )
  }

  private val dialogsStateFlow = combine(
    _selectedAchievementForModal,
    _showShareDialog,
    _showCelebrationDialog,
    _celebrationMessage
  ) { ach, share, celeb, msg ->
    listOf<Any?>(ach, share, celeb, msg)
  }

  private val interactionStateFlow: Flow<InteractionState> = combine(
    _selectedFood,
    _selectedPortion,
    _selectedMealType,
    _selectedLogDate,
    dialogsStateFlow
  ) { food, portion, mealType, logDate, dialogs ->
    @Suppress("UNCHECKED_CAST")
    InteractionState(
      selectedFood = food,
      selectedPortion = portion,
      selectedMealType = mealType,
      selectedLogDate = logDate,
      selectedAchievementForModal = dialogs[0] as? AchievementEntity,
      showShareDialog = dialogs[1] as Boolean,
      showCelebrationDialog = dialogs[2] as Boolean,
      celebrationMessage = dialogs[3] as String
    )
  }

  val uiState: StateFlow<UiState> = combine(
    navigationAndFilterFlow,
    allFoodsFlow,
    persistenceStateFlow,
    interactionStateFlow
  ) { navFilter, foods, persistence, interaction ->
    val effectiveScreen = if (!persistence.settings.onboardingCompleted && navFilter.screen == "home") {
      "onboarding"
    } else navFilter.screen

    val foodsWithFavs = foods.map { food ->
      food.copy(isFavorite = persistence.favorites.contains(food.id))
    }

    val filtered = foodsWithFavs.filter { food ->
      val matchesQuery = navFilter.query.isBlank() ||
          food.name.contains(navFilter.query, ignoreCase = true) ||
          (food.bengaliName?.contains(navFilter.query, ignoreCase = true) == true) ||
          food.searchAliases.any { it.contains(navFilter.query, ignoreCase = true) }

      val matchesCategory = navFilter.category.equals("All", ignoreCase = true) ||
          (navFilter.category.equals("Bangladesh", ignoreCase = true) && food.region.equals("Bangladesh", ignoreCase = true)) ||
          food.categoryId.equals(navFilter.category, ignoreCase = true)

      val matchesSaved = !navFilter.savedOnly || food.isFavorite

      matchesQuery && matchesCategory && matchesSaved
    }

    val unlocked = persistence.achievements.filter { it.isUnlocked }

    val distinctDaysWithLogs = persistence.records.filter { it.score > 0 }.map { it.date }.toSet()
    val streak = calculateStreakCount(navFilter.todayDate, distinctDaysWithLogs)

    UiState(
      currentScreen = effectiveScreen,
      activeDate = navFilter.activeDate,
      todayDate = navFilter.todayDate,
      streakDays = streak.coerceAtLeast(1),
      searchQuery = navFilter.query,
      selectedCategory = navFilter.category,
      savedOnly = navFilter.savedOnly,
      allFoods = foodsWithFavs,
      filteredFoods = filtered,
      favorites = persistence.favorites,
      selectedFood = interaction.selectedFood,
      selectedPortion = interaction.selectedPortion,
      selectedMealType = interaction.selectedMealType,
      selectedLogDate = interaction.selectedLogDate,
      achievements = persistence.achievements,
      unlockedAchievements = unlocked,
      selectedAchievementForModal = interaction.selectedAchievementForModal,
      userSettings = persistence.settings,
      showShareDialog = interaction.showShareDialog,
      showCelebrationDialog = interaction.showCelebrationDialog,
      celebrationMessage = interaction.celebrationMessage,
      weeklyRecords = persistence.records.take(7)
    )
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = UiState()
  )

  fun getEntriesForDate(date: String) = repository.getEntriesForDate(date)
  fun getDailyRecord(date: String) = repository.getDailyRecord(date)

  fun navigateTo(screen: String) {
    _currentScreen.value = screen
  }

  fun setActiveDate(date: String) {
    _activeDate.value = date
    _selectedLogDate.value = date
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun setSelectedCategory(category: String) {
    _selectedCategory.value = category
  }

  fun toggleSavedOnly() {
    _savedOnly.value = !_savedOnly.value
  }

  fun toggleFavorite(foodId: String) {
    viewModelScope.launch {
      repository.toggleFavorite(foodId)
    }
  }

  fun openFoodDetail(food: FoodItem, targetMeal: String? = null) {
    _selectedFood.value = food
    _selectedPortion.value = food.portions.firstOrNull()
    if (targetMeal != null) {
      _selectedMealType.value = targetMeal
    }
    _currentScreen.value = "food_detail"
  }

  fun setSelectedPortion(portion: PortionOption) {
    _selectedPortion.value = portion
  }

  fun setSelectedMealType(mealType: String) {
    _selectedMealType.value = mealType
  }

  fun setSelectedLogDate(date: String) {
    _selectedLogDate.value = date
  }

  fun logCurrentFoodSelection() {
    val food = _selectedFood.value ?: return
    val portion = _selectedPortion.value ?: food.portions.firstOrNull() ?: return
    val mealType = _selectedMealType.value
    val date = _selectedLogDate.value.ifBlank { _todayDate.value }

    viewModelScope.launch {
      repository.logFood(
        food = food,
        portion = portion,
        mealType = mealType,
        date = date
      )
      _currentScreen.value = "home"
    }
  }

  fun toggleMealSkipped(date: String, mealType: String) {
    viewModelScope.launch {
      repository.toggleMealSkipped(date, mealType)
    }
  }

  fun toggleChallengeCompleted(date: String, currentlyCompleted: Boolean) {
    viewModelScope.launch {
      repository.setChallengeCompleted(date, !currentlyCompleted)
    }
  }

  fun deleteEntry(entry: FoodEntryEntity) {
    viewModelScope.launch {
      repository.deleteEntry(entry)
    }
  }

  fun updateEntryPortion(entry: FoodEntryEntity, newPortion: PortionOption) {
    viewModelScope.launch {
      val updated = entry.copy(
        portionId = newPortion.id,
        portionName = newPortion.name,
        scoreMultiplier = newPortion.multiplier,
        calories = newPortion.calories,
        carbs = newPortion.carbs,
        protein = newPortion.protein,
        fat = newPortion.fat,
        fiber = newPortion.fiber,
        sugar = newPortion.sugar,
        sodium = newPortion.sodium
      )
      repository.updateEntry(updated)
    }
  }

  fun completeOnboarding(displayName: String, goals: List<String>, breakfast: Boolean, lunch: Boolean, dinner: Boolean, snacks: Boolean) {
    viewModelScope.launch {
      val current = uiState.value.userSettings
      val updated = current.copy(
        displayName = displayName.trim(),
        goals = goals.joinToString(","),
        breakfastEnabled = breakfast,
        lunchEnabled = lunch,
        dinnerEnabled = dinner,
        snacksEnabled = snacks,
        onboardingCompleted = true
      )
      repository.updateUserSettings(updated)
      _currentScreen.value = "home"
    }
  }

  fun updateSettings(settings: UserSettingsEntity) {
    viewModelScope.launch {
      repository.updateUserSettings(settings)
    }
  }

  fun openAchievementModal(achievement: AchievementEntity) {
    _selectedAchievementForModal.value = achievement
  }

  fun closeAchievementModal() {
    _selectedAchievementForModal.value = null
  }

  fun openShareDialog() {
    _showShareDialog.value = true
  }

  fun closeShareDialog() {
    _showShareDialog.value = false
  }

  fun dismissCelebration() {
    _showCelebrationDialog.value = false
  }

  /**
   * Checks if a date is within the editable backfill window (Today, Yesterday, 2 days ago).
   */
  fun isDateEditable(dateStr: String): Boolean {
    val cal = Calendar.getInstance()
    val today = dateFormat.format(cal.time)
    cal.add(Calendar.DAY_OF_YEAR, -1)
    val yesterday = dateFormat.format(cal.time)
    cal.add(Calendar.DAY_OF_YEAR, -1)
    val twoDaysAgo = dateFormat.format(cal.time)

    return dateStr == today || dateStr == yesterday || dateStr == twoDaysAgo
  }

  fun getBackfillDates(): List<Pair<String, String>> {
    val cal = Calendar.getInstance()
    val today = dateFormat.format(cal.time)
    cal.add(Calendar.DAY_OF_YEAR, -1)
    val yesterday = dateFormat.format(cal.time)
    cal.add(Calendar.DAY_OF_YEAR, -1)
    val twoDaysAgo = dateFormat.format(cal.time)

    return listOf(
      today to "Today",
      yesterday to "Yesterday",
      twoDaysAgo to "2 Days Ago"
    )
  }

  private fun calculateStreakCount(today: String, activeDates: Set<String>): Int {
    if (activeDates.isEmpty()) return 0
    val cal = Calendar.getInstance()
    var streak = 0

    for (i in 0..60) {
      val d = dateFormat.format(cal.time)
      if (activeDates.contains(d)) {
        streak++
      } else if (i > 0) {
        break
      }
      cal.add(Calendar.DAY_OF_YEAR, -1)
    }
    return if (streak > 0) streak else 1
  }
}
