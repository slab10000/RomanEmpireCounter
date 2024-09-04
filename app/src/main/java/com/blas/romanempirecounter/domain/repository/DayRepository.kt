package com.blas.romanempirecounter.domain.repository

import com.blas.romanempirecounter.data.local.entity.Day
import com.blas.romanempirecounter.domain.model.DayModel
import kotlinx.coroutines.flow.Flow

//useful to use a interface for testcases
interface DayRepository {

    fun getLastSevenDays(): Flow<List<DayModel>>

    suspend fun insertDay(day: DayModel)

}