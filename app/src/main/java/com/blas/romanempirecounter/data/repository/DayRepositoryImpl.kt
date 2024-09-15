package com.blas.romanempirecounter.data.repository

import com.blas.romanempirecounter.data.local.dao.DayDao
import com.blas.romanempirecounter.data.local.entity.Day
import com.blas.romanempirecounter.domain.model.DayModel
import com.blas.romanempirecounter.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow
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

    override suspend fun getLastDay(): DayModel {
        return toDayModel(dao.getLastDay()?:Day(0,"",0))
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

    //Se mapea en data porque domain no debe conocer la entidad de Day de la base de datos
    private fun toDayModel(day: Day): DayModel{
        return with(day){
            DayModel(
                uid = uid,
                date = date,
                count = count
            )
        }
    }
}