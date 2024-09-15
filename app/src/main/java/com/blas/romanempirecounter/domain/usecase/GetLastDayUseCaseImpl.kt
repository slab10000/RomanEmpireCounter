package com.blas.romanempirecounter.domain.usecase

import com.blas.romanempirecounter.domain.model.DayModel
import com.blas.romanempirecounter.domain.repository.DayRepository

class GetLastDayUseCaseImpl(
    private val repository: DayRepository
): GetLastDayUseCase {

    override suspend operator fun invoke(): DayModel{
        return repository.getLastDay()
    }

}