package com.blas.romanempirecounter.presentation.mainpage

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blas.romanempirecounter.domain.model.DayModel
import com.blas.romanempirecounter.domain.usecase.GetLastDayUseCase
import com.blas.romanempirecounter.domain.usecase.InsertDayUseCase
import com.blas.romanempirecounter.presentation.mainpage.MainScreenEvent.CounterOnClick
import com.blas.romanempirecounter.presentation.mainpage.MainScreenEvent.InitializeDayEvent
import com.blas.romanempirecounter.presentation.mainpage.MainScreenEvent.InsertDayEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getLastDayUseCase: GetLastDayUseCase,
    private val insertDayUseCase: InsertDayUseCase
): ViewModel(){

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    private var lastDay: DayModel = DayModel(0,"",0)

    private lateinit var todayDay: DayModel

    init {
        initializeDay()
    }

    fun onEvent(event: MainScreenEvent){
        when(event){
            is CounterOnClick -> onCounterClickEvent(event.counter)
            is InsertDayEvent -> onInsertDayEvent()
            is InitializeDayEvent -> initializeDay()
        }
    }

    private fun onCounterClickEvent(counter: Int){
        _state.value = _state.value.copy(
            counter = counter,
            isAnimationOn = counter == 0
        )
        todayDay = todayDay.copy(count = _state.value.counter)
        onInsertDayEvent()
    }

    private fun onInsertDayEvent(){
        viewModelScope.launch {
            insertDayUseCase(todayDay)
        }
    }

    private suspend fun getLastDay(){
        lastDay = getLastDayUseCase()
    }

    private fun getTodayDate(): String{
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return LocalDateTime.now().format(formatter)
    }

    private fun initializeDay(){
        viewModelScope.launch {
            getLastDay()
            val today = getTodayDate()
            if(lastDay.date == today){
                _state.value = _state.value.copy(
                    counter = lastDay.count?:0
                )
                todayDay = lastDay.copy()
            }else{
                _state.value = _state.value.copy(
                    counter = 0
                )
                todayDay = DayModel(
                    uid = lastDay.uid + 1,
                    date = today,
                    count = 0
                )
                onInsertDayEvent()
            }
        }
    }

    fun resetDay(){
        viewModelScope.launch {
            _state.value = _state.value.copy(counter = 0)
        }
    }


}