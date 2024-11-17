package com.blas.romanempirecounter.presentation.secondScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blas.romanempirecounter.R
import com.blas.romanempirecounter.presentation.composables.AutoResizedText
import com.blas.romanempirecounter.presentation.mainpage.intToRoman
import com.blas.romanempirecounter.presentation.secondScreen.FilterTypes
import com.blas.romanempirecounter.presentation.secondScreen.FilterTypes.MONTH
import com.blas.romanempirecounter.presentation.secondScreen.FilterTypes.SEVEN_DAYS
import com.blas.romanempirecounter.presentation.secondScreen.FilterTypes.YEAR
import com.blas.romanempirecounter.presentation.secondScreen.SecondScreenEvent.OnDropDownClick
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
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                var expanded by remember { mutableStateOf(false) }
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "TIMES LAST ",
                    fontFamily = FontFamily(Font(R.font.cinzel)),
                    color = Color.White
                )
                Button(
                    contentPadding = PaddingValues(
                        start = 10.dp,
                        top = 0.dp,
                        end = 2.dp,
                        bottom = 1.dp
                    ),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonColors(
                        containerColor = Color(0xffa7d979),
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Red
                    ),
                    onClick = {
                        expanded = true
                    }
                ) {
                    var buttonText by remember { mutableStateOf(SEVEN_DAYS) }
                    Row {
                        Text(
                            text = buttonText.value,
                            fontFamily = FontFamily(Font(R.font.cinzel)),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown icon",
                        tint = Color.Black
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        for (filterType in FilterTypes.entries) {
                            DropdownMenuItem(
                                text = { Text(filterType.value) },
                                onClick = {
                                    viewModel.onEvent(OnDropDownClick(filterType))
                                    buttonText = filterType
                                    expanded = false
                                }
                            )
                        }

                    }
                }
            }
            Spacer(modifier = Modifier.size(15.dp))
            AutoResizedText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = intToRoman(viewModel.secondState.value.totalCount),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.cinzel)),
                color = Color.White
            )
        }

        LazyColumn(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            itemsIndexed(viewModel.secondState.value.allDays){ index, day ->
                DayCardComposable(
                    modifier = Modifier,
                    date = day.date.orEmpty(),
                    count = intToRoman(day.count?:0),
                    isToday = index == 0
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