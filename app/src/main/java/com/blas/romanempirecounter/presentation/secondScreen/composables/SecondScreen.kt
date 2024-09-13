package com.blas.romanempirecounter.presentation.secondScreen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.blas.romanempirecounter.presentation.secondScreen.SecondScreenViewModel

@Composable
fun SecondScreen(
    viewModel: SecondScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        viewModel.getAllDays()
        Text(text = "titulo")

        LazyColumn(
        ){
            items(viewModel.secondState.value.allDays){ day ->
                Text(text = "count = ${day.count}, uuid = ${day.uid}")
                Text("separador")
            }
        }

    }
}