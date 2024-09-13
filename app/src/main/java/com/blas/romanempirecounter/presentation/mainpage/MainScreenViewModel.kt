package com.blas.romanempirecounter.presentation.mainpage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blas.romanempirecounter.domain.model.DayModel
import com.blas.romanempirecounter.domain.usecase.GetAllDaysUseCase
import com.blas.romanempirecounter.domain.usecase.InsertDayUseCase
import com.blas.romanempirecounter.presentation.mainpage.MainScreenEvent.CounterOnClick
import com.blas.romanempirecounter.presentation.mainpage.MainScreenEvent.InsertDayEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getAllDaysUseCase: GetAllDaysUseCase,
    private val insertDayUseCase: InsertDayUseCase
): ViewModel(){

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    private var getDaysJob: Job? = null

    init {
        getAllDays()
    }

    fun onEvent(event: MainScreenEvent){
        when(event){
            is CounterOnClick -> onCounterClickEvent(event.counter)
            is InsertDayEvent -> onInsertDayEvent(event.day)
        }
    }

    private fun onCounterClickEvent(counter: Int){
        _state.value = state.value.copy(counter = counter)
        //viewModelScope.launch { getLastSevenDaysUseCase }
    }

    private fun onInsertDayEvent(day: DayModel){
        viewModelScope.launch {
            insertDayUseCase(day)
        }
    }

    private fun getAllDays(){
        getDaysJob?.cancel()
        getDaysJob = getAllDaysUseCase().onEach { dayList ->
            _state.value = state.value.copy(
                allDays = dayList
            )
        }.launchIn(viewModelScope)
    }

}