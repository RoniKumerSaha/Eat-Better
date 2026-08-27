package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.AchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
  @Query("SELECT * FROM achievements ORDER BY isUnlocked DESC, id ASC")
  fun getAllAchievements(): Flow<List<AchievementEntity>>

  @Query("SELECT * FROM achievements WHERE isUnlocked = 1 ORDER BY unlockedAt DESC")
  fun getUnlockedAchievements(): Flow<List<AchievementEntity>>

  @Query("SELECT * FROM achievements WHERE id = :id LIMIT 1")
  suspend fun getAchievementById(id: String): AchievementEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAchievements(achievements: List<AchievementEntity>)

  @Update
  suspend fun updateAchievement(achievement: AchievementEntity)

  @Query("SELECT COUNT(*) FROM achievements WHERE isUnlocked = 1")
  fun getUnlockedCount(): Flow<Int>
}
