package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_entries")
data class FoodEntryEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val foodId: String,
  val foodName: String,
  val categoryId: String,
  val portionId: String,
  val portionName: String,
  val date: String, // "YYYY-MM-DD"
  val mealType: String, // "Breakfast", "Lunch", "Dinner", "Snack"
  val baseScore: Int,
  val scoreMultiplier: Float,
  val calories: Int,
  val carbs: Float,
  val protein: Float,
  val fat: Float,
  val fiber: Float,
  val sugar: Float,
  val sodium: Float,
  val createdAt: Long = System.currentTimeMillis()
)
