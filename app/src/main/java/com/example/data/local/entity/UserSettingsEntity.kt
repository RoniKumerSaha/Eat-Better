package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettingsEntity(
  @PrimaryKey val id: Int = 1,
  val displayName: String = "",
  val goals: String = "Eat healthier overall,Improve nutrition/balance", // Comma-separated
  val breakfastEnabled: Boolean = true,
  val lunchEnabled: Boolean = true,
  val dinnerEnabled: Boolean = true,
  val snacksEnabled: Boolean = true,
  val breakfastReminderTime: String = "08:30",
  val lunchReminderTime: String = "13:30",
  val dinnerReminderTime: String = "20:00",
  val snackReminderTime: String = "16:30",
  val remindersEnabled: Boolean = true,
  val onboardingCompleted: Boolean = false,
  val age: String = "",
  val gender: String = "",
  val height: String = "",
  val weight: String = ""
)
