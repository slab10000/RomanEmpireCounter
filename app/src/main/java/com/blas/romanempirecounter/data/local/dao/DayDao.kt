package com.blas.romanempirecounter.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blas.romanempirecounter.data.local.entity.Day
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao{

    //Using a flow makes the value be updated if the table changes
    @Query("SELECT * FROM day")
    fun getAll(): Flow<List<Day>>

    @Query("SELECT * FROM day WHERE uid IN (:dayIds)")
    fun getAllByIds(dayIds: IntArray): Flow<List<Day>>

    @Query("SELECT * FROM day ORDER BY uid DESC LIMIT 7")
    fun getLastSevenDays(): Flow<List<Day>>

    @Query("SELECT * FROM day WHERE date LIKE :date")
    suspend fun findByDate(date: String): Day

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(day: Day)

    @Delete
    suspend fun delete(vararg days: Day)
}