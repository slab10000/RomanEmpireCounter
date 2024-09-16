package com.blas.romanempirecounter.presentation.secondScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blas.romanempirecounter.R
import com.blas.romanempirecounter.presentation.mainpage.intToRoman
import com.blas.romanempirecounter.presentation.secondScreen.SecondScreenViewModel
import com.blas.romanempirecounter.presentation.ui.theme.OliveGreen

@Composable
fun SecondScreen(
    viewModel: SecondScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        /* Header */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(OliveGreen)
                .padding(50.dp)
        ) {
            Image(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .size(70.dp),
                painter = painterResource(id = R.drawable.wax_table),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.size(40.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "TIMES LAST 7 DAYS: ",
                fontFamily = FontFamily(Font(R.font.cinzel)),
                color = Color.White
            )
            Spacer(modifier = Modifier.size(15.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                text = intToRoman(viewModel.secondState.value.totalThisWeek),
                fontFamily = FontFamily(Font(R.font.cinzel)),
                color = Color.White
            )
        }

        LazyColumn(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            itemsIndexed(viewModel.secondState.value.allDays){ index, day ->
                Column {

                }
                DayCardComposable(
                    modifier = Modifier,
                    date = day.date.orEmpty(),
                    count = intToRoman(day.count?:0)
                )
                // Evita agregar el divisor después del último elemento
                if (index < viewModel.secondState.value.allDays.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
            }
        }

    }
}