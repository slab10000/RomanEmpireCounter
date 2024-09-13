package com.blas.romanempirecounter.domain.usecase

import com.blas.romanempirecounter.domain.model.DayModel
import com.blas.romanempirecounter.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow

class GetAllDaysUseCaseImpl(
    private val repository: DayRepository
): GetAllDaysUseCase {

    override operator fun invoke(): Flow<List<DayModel>> {
        return repository.getAll()
    }

}