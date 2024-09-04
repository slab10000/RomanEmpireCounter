package com.blas.romanempirecounter.presentation.mainpage

sealed class MainScreenEvent {
    data class CounterOnClick(val counter: Int): MainScreenEvent()
}