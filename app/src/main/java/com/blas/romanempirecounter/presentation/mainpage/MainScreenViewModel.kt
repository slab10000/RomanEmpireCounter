package com.blas.romanempirecounter.presentation.mainpage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blas.romanempirecounter.domain.usecase.GetLastSevenDaysUseCase
import com.blas.romanempirecounter.presentation.mainpage.MainScreenEvent.CounterOnClick
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getLastSevenDaysUseCase: GetLastSevenDaysUseCase
): ViewModel(){

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    fun onEvent(event: MainScreenEvent){
        when(event){
            is CounterOnClick -> onCounterClickEvent(event.counter)
        }
    }

    fun onCounterClickEvent(counter: Int){
        _state.value = state.value.copy(counter = counter)
        //viewModelScope.launch { getLastSevenDaysUseCase }
    }

    init {
        viewModelScope.launch { getLastSevenDaysUseCase() }
    }
}