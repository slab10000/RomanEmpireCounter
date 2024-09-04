package com.blas.romanempirecounter.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Day(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "count") val count: Int?,
)