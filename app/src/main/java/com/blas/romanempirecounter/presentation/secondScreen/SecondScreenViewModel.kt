package com.blas.romanempirecounter.presentation.secondScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blas.romanempirecounter.domain.usecase.GetAllDaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondScreenViewModel @Inject constructor(
    private val getAllDaysUseCase: GetAllDaysUseCase,
): ViewModel(){

    private val _secondState = mutableStateOf(SecondScreenState())
    val secondState: State<SecondScreenState> = _secondState

    private var getDaysJob: Job? = null

    init {
        getAllDays()
    }

    fun getAllDays(){
        getDaysJob?.cancel()
        getDaysJob = getAllDaysUseCase().onEach { dayList ->
            _secondState.value = secondState.value.copy(
                allDays = dayList
            )
        }.launchIn(viewModelScope)
    }

}