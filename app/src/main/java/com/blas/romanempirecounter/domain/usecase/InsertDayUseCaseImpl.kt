package com.blas.romanempirecounter.domain.usecase

import com.blas.romanempirecounter.domain.model.DayModel
import com.blas.romanempirecounter.domain.repository.DayRepository

class InsertDayUseCaseImpl(
    private val repository: DayRepository
): InsertDayUseCase {

    override suspend operator fun invoke(day: DayModel){
        repository.insertDay(day)
    }

}