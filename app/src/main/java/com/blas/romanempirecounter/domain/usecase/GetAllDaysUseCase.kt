package com.blas.romanempirecounter.domain.usecase

import com.blas.romanempirecounter.domain.model.DayModel
import kotlinx.coroutines.flow.Flow

interface GetAllDaysUseCase{
     operator fun invoke(): Flow<List<DayModel>>
}