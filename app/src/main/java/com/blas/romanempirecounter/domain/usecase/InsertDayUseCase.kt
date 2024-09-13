package com.blas.romanempirecounter.domain.usecase

import com.blas.romanempirecounter.domain.model.DayModel

interface InsertDayUseCase{
     suspend operator fun invoke(day: DayModel)
}