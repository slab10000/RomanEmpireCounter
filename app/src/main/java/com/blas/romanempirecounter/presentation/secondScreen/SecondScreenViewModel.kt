package com.blas.romanempirecounter.presentation.secondScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blas.romanempirecounter.domain.usecase.GetAllDaysUseCase
import com.blas.romanempirecounter.presentation.secondScreen.FilterTypes.SEVEN_DAYS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.Locale
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

    fun onEvent(event: SecondScreenEvent){
        when(event) {
            is SecondScreenEvent.OnDropDownClick -> getTotalCounts(event.filterTypes)
        }
    }

    private fun getAllDays(){
        getDaysJob?.cancel()
        getDaysJob = getAllDaysUseCase().onEach { dayList ->
            _secondState.value = _secondState.value.copy(
                allDays = dayList
            )
            getTotalCounts(SEVEN_DAYS)
        }.launchIn(viewModelScope)
    }

    private fun getTotalCounts(filterType: FilterTypes){
        val today = LocalDate.now()
        val timeFilter = when (filterType) {
            SEVEN_DAYS -> today.minusDays(7)
            FilterTypes.MONTH -> today.minusMonths(1)
            FilterTypes.YEAR -> today.minusYears(1)
        }
        // Usar DateTimeFormatterBuilder para aceptar días y meses con 1 o 2 dígitos
        val formatter = DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE) // Permite 1 o 2 dígitos para el día
            .appendLiteral('-')
            .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE) // Permite 1 o 2 dígitos para el mes
            .appendLiteral('-')
            .appendValue(ChronoField.YEAR, 4) // Espera 4 dígitos para el año
            .toFormatter(Locale.getDefault())


        // Filtrar los objetos que están dentro de los últimos 7 días
        val filteredDays = _secondState.value.allDays.filter { day ->
            val dayDate = LocalDate.parse(day.date, formatter) // Convertir el String a LocalDate
            dayDate.isAfter(timeFilter) || dayDate.isEqual(timeFilter)
        }
        _secondState.value = _secondState.value.copy(totalCount = filteredDays.sumOf { it.count!! })


    }

}