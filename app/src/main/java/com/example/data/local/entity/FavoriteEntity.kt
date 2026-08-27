package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
  @PrimaryKey val foodId: String,
  val savedAt: Long = System.currentTimeMillis()
)
