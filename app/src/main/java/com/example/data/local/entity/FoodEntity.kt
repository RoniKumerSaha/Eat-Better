package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodEntity(
  @PrimaryKey val id: String,
  val name: String,
  val bengaliName: String? = null,
  val categoryId: String, // "fruits", "vegetables", "meals", "snacks", "drinks"
  val region: String, // "Bangladesh", "Global", "Universal"
  val baseScore: Int, // 1 to 10
  val educationalText: String,
  val scoreExplanation: String,
  val searchAliases: String,
  val portionsJson: String,
  val iconSymbol: String = "restaurant"
)
