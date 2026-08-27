package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.DailyRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyRecordDao {
  @Query("SELECT * FROM daily_records WHERE date = :date LIMIT 1")
  fun getDailyRecord(date: String): Flow<DailyRecordEntity?>

  @Query("SELECT * FROM daily_records WHERE date = :date LIMIT 1")
  suspend fun getDailyRecordSync(date: String): DailyRecordEntity?

  @Query("SELECT * FROM daily_records ORDER BY date DESC")
  fun getAllDailyRecords(): Flow<List<DailyRecordEntity>>

  @Query("SELECT * FROM daily_records WHERE date >= :startDate AND date <= :endDate ORDER BY date ASC")
  fun getRecordsRange(startDate: String, endDate: String): Flow<List<DailyRecordEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsertDailyRecord(record: DailyRecordEntity)

  @Query("DELETE FROM daily_records")
  suspend fun clearAllRecords()
}
