package com.blas.romanempirecounter.presentation.secondScreen

sealed class SecondScreenEvent {
    data class OnDropDownClick(val filterTypes: FilterTypes): SecondScreenEvent()
}