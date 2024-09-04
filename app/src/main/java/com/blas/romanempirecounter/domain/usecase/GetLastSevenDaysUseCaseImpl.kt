package com.blas.romanempirecounter.domain.usecase

import com.blas.romanempirecounter.domain.model.DayModel
import com.blas.romanempirecounter.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow

class GetLastSevenDaysUseCaseImpl(
    private val repository: DayRepository
): GetLastSevenDaysUseCase {

    override suspend operator fun invoke(): Flow<List<DayModel>> {
        return repository.getLastSevenDays()
    }

}