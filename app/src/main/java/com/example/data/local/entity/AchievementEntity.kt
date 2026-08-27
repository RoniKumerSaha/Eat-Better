package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class AchievementEntity(
  @PrimaryKey val id: String,
  val title: String,
  val description: String,
  val category: String, // "Nutrition", "Logging", "Food", "Challenge", "Streak"
  val iconName: String, // Material symbol name or custom icon tag
  val targetCount: Int = 1,
  val currentProgress: Int = 0,
  val isUnlocked: Boolean = false,
  val unlockedAt: Long? = null
)
