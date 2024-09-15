package com.blas.romanempirecounter.domain.repository

import com.blas.romanempirecounter.domain.model.DayModel
import kotlinx.coroutines.flow.Flow

//useful to use a interface for testcases
interface DayRepository {

    fun getLastSevenDays(): Flow<List<DayModel>>

    fun getAll(): Flow<List<DayModel>>

    suspend fun getLastDay(): DayModel

    suspend fun insertDay(day: DayModel)

}