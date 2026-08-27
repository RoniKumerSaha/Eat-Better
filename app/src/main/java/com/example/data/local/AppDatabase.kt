package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.AchievementDao
import com.example.data.local.dao.DailyRecordDao
import com.example.data.local.dao.FavoriteDao
import com.example.data.local.dao.FoodDao
import com.example.data.local.dao.FoodEntryDao
import com.example.data.local.dao.UserSettingsDao
import com.example.data.local.entity.AchievementEntity
import com.example.data.local.entity.DailyRecordEntity
import com.example.data.local.entity.FavoriteEntity
import com.example.data.local.entity.FoodEntity
import com.example.data.local.entity.FoodEntryEntity
import com.example.data.local.entity.UserSettingsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
  entities = [
    FoodEntity::class,
    FoodEntryEntity::class,
    FavoriteEntity::class,
    DailyRecordEntity::class,
    AchievementEntity::class,
    UserSettingsEntity::class
  ],
  version = 1,
  exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun foodDao(): FoodDao
  abstract fun foodEntryDao(): FoodEntryDao
  abstract fun favoriteDao(): FavoriteDao
  abstract fun dailyRecordDao(): DailyRecordDao
  abstract fun achievementDao(): AchievementDao
  abstract fun userSettingsDao(): UserSettingsDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "eat_better_database"
        )
          .addCallback(object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
              super.onCreate(db)
              INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                  // Populate default foods
                  database.foodDao().insertFoods(PrepopulatedFoods.list)
                  // Populate default achievements
                  database.achievementDao().insertAchievements(DefaultAchievements.list)
                  // Populate default user settings
                  database.userSettingsDao().insertOrUpdate(UserSettingsEntity())
                  // Populate default favorites (e.g. apple, pure water, rui mach)
                  database.favoriteDao().addFavorite(FavoriteEntity("fuji_apple"))
                  database.favoriteDao().addFavorite(FavoriteEntity("pure_water"))
                  database.favoriteDao().addFavorite(FavoriteEntity("palak_shak"))
                }
              }
            }
          })
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
