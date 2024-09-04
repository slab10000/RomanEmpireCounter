package com.blas.romanempirecounter.presentation.mainpage

import com.blas.romanempirecounter.domain.model.DayModel

data class MainScreenState (
    var counter: Int = 0,
    val lastSevenDays: List<DayModel> = emptyList()
)