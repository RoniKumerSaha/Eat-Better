package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_records")
data class DailyRecordEntity(
  @PrimaryKey val date: String, // "YYYY-MM-DD"
  val score: Int = 0,
  val nutritiousBalancePercentage: Int = 0,
  val challengeId: String = "veggie_boost",
  val challengeCompleted: Boolean = false,
  val skippedMeals: String = "", // Comma-separated meal names: e.g. "Breakfast"
  val lastCalculatedAt: Long = System.currentTimeMillis()
)
