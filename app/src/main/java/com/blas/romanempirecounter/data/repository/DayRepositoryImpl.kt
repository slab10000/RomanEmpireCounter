package com.blas.romanempirecounter.data.repository

import com.blas.romanempirecounter.data.local.dao.DayDao
import com.blas.romanempirecounter.data.local.entity.Day
import com.blas.romanempirecounter.domain.model.DayModel
import com.blas.romanempirecounter.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class DayRepositoryImpl(
    private val dao: DayDao
): DayRepository {

    override fun getLastSevenDays(): Flow<List<DayModel>> {
        return toDayModelFlowList(dao.getLastSevenDays())
    }

    override fun getAll(): Flow<List<DayModel>> {
        return toDayModelFlowList(dao.getAll())
    }

    override suspend fun insertDay(day: DayModel) {
        dao.insertAll(
            Day(
                uid = day.uid,
                count = day.count,
                date = day.date
            )
        )
    }

    private fun toDayModelFlowList(dayFlowList: Flow<List<Day>>): Flow<List<DayModel>>{
        return dayFlowList.map { dayList ->
            dayList.map { day ->
                // Aquí realizas el mapeo de cada objeto Day a DayModel
                DayModel(
                    uid = day.uid,
                    count = day.count,
                    date = day.date
                    // Agrega aquí más propiedades según tu modelo
                )
            }
        }
    }
}