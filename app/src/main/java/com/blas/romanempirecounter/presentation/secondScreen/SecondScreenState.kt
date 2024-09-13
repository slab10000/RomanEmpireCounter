package com.blas.romanempirecounter.presentation.secondScreen

import com.blas.romanempirecounter.domain.model.DayModel

data class SecondScreenState (
    var counter: Int = 0,
    val allDays: List<DayModel> = emptyList()
)