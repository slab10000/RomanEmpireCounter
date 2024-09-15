package com.blas.romanempirecounter.presentation.secondScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blas.romanempirecounter.presentation.mainpage.intToRoman
import com.blas.romanempirecounter.presentation.secondScreen.SecondScreenViewModel

@Composable
fun SecondScreen(
    viewModel: SecondScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        viewModel.getAllDays()

        LazyColumn(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            items(viewModel.secondState.value.allDays){ day ->
                Column {

                }
                DayCardComposable(
                    modifier = Modifier,
                    date = day.date.orEmpty(),
                    count = intToRoman(day.count?:0)
                )
                Spacer(modifier = Modifier.size(20.dp))
            }
        }

    }
}