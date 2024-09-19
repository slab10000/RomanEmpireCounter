package com.blas.romanempirecounter.presentation.mainpage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blas.romanempirecounter.domain.model.DayModel
import com.blas.romanempirecounter.domain.usecase.GetLastDayUseCase
import com.blas.romanempirecounter.domain.usecase.InsertDayUseCase
import com.blas.romanempirecounter.presentation.mainpage.MainScreenEvent.CounterOnClick
import com.blas.romanempirecounter.presentation.mainpage.MainScreenEvent.InsertDayEvent
import dagger.hilt.android.lifecycle.HiltViewModel
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
        viewModelScope.launch {
            getLastDay()
            val today = getTodayDate()
            if(lastDay.date == today){
                state.value.counter = lastDay.count?:0
                todayDay = lastDay.copy()
            }else{
                todayDay = DayModel(
                    uid = lastDay.uid + 1,
                    date = today,
                    count = 0
                )
                onCounterClickEvent(0)
            }
        }
    }

    fun onEvent(event: MainScreenEvent){
        when(event){
            is CounterOnClick -> onCounterClickEvent(event.counter)
            is InsertDayEvent -> insertDay() // NO hace falta
        }
    }

    private fun onCounterClickEvent(counter: Int){
        _state.value = state.value.copy(
            counter = counter,
            isAnimationOn = counter == 0
        )
        todayDay = todayDay.copy(count = _state.value.counter)
        insertDay()
    }

    private fun insertDay(){
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

}