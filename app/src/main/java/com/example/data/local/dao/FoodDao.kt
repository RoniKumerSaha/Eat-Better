package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
  @Query("SELECT * FROM foods ORDER BY name ASC")
  fun getAllFoods(): Flow<List<FoodEntity>>

  @Query("SELECT * FROM foods WHERE id = :id LIMIT 1")
  suspend fun getFoodById(id: String): FoodEntity?

  @Query("SELECT * FROM foods WHERE categoryId = :category ORDER BY name ASC")
  fun getFoodsByCategory(category: String): Flow<List<FoodEntity>>

  @Query("SELECT * FROM foods WHERE name LIKE '%' || :query || '%' OR bengaliName LIKE '%' || :query || '%' OR searchAliases LIKE '%' || :query || '%' ORDER BY name ASC")
  fun searchFoods(query: String): Flow<List<FoodEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertFoods(foods: List<FoodEntity>)

  @Query("SELECT COUNT(*) FROM foods")
  suspend fun getFoodCount(): Int
}
