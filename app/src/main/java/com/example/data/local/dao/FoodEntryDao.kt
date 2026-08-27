package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.FoodEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodEntryDao {
  @Query("SELECT * FROM food_entries WHERE date = :date ORDER BY createdAt ASC")
  fun getEntriesForDate(date: String): Flow<List<FoodEntryEntity>>

  @Query("SELECT * FROM food_entries WHERE date = :date ORDER BY createdAt ASC")
  suspend fun getEntriesForDateSync(date: String): List<FoodEntryEntity>

  @Query("SELECT * FROM food_entries ORDER BY createdAt DESC")
  fun getAllEntries(): Flow<List<FoodEntryEntity>>

  @Query("SELECT * FROM food_entries WHERE date >= :startDate AND date <= :endDate ORDER BY date ASC, createdAt ASC")
  fun getEntriesDateRange(startDate: String, endDate: String): Flow<List<FoodEntryEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertEntry(entry: FoodEntryEntity): Long

  @Update
  suspend fun updateEntry(entry: FoodEntryEntity)

  @Delete
  suspend fun deleteEntry(entry: FoodEntryEntity)

  @Query("DELETE FROM food_entries WHERE id = :id")
  suspend fun deleteEntryById(id: Long)

  @Query("DELETE FROM food_entries")
  suspend fun clearAllEntries()

  @Query("SELECT COUNT(DISTINCT date) FROM food_entries")
  fun getTotalDistinctDaysLogged(): Flow<Int>

  @Query("SELECT COUNT(*) FROM food_entries WHERE categoryId = :categoryId")
  fun getCategoryLogCount(categoryId: String): Flow<Int>
}
