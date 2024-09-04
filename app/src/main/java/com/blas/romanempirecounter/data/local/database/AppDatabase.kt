package com.blas.romanempirecounter.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blas.romanempirecounter.data.local.dao.DayDao
import com.blas.romanempirecounter.data.local.entity.Day

@Database(
    entities = [Day::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase(){

    abstract val dayDao: DayDao

    companion object{
        const val DATABASE_NAME = "days_db"
    }
}