package com.blas.romanempirecounter.presentation.mainpage

import com.blas.romanempirecounter.domain.model.DayModel

sealed class MainScreenEvent {
    data class CounterOnClick(val counter: Int): MainScreenEvent()
    data class InsertDayEvent(val day: DayModel) : MainScreenEvent()
}