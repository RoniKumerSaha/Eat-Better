package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
  @Query("SELECT * FROM favorites")
  fun getAllFavorites(): Flow<List<FavoriteEntity>>

  @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE foodId = :foodId)")
  fun isFavorite(foodId: String): Flow<Boolean>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addFavorite(favorite: FavoriteEntity)

  @Query("DELETE FROM favorites WHERE foodId = :foodId")
  suspend fun removeFavorite(foodId: String)

  @Query("DELETE FROM favorites")
  suspend fun clearAllFavorites()
}
