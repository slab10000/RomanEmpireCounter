package com.blas.romanempirecounter.presentation.secondScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.blas.romanempirecounter.R

@Composable
fun DayCardComposable(
    modifier: Modifier = Modifier,
    date: String,
    count: String,
    isToday: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Image(
            modifier = modifier
                .align(alignment = Alignment.CenterVertically)
                .size(70.dp),
            painter = painterResource(id = R.drawable.icono_estatua_julio_cesar_svg),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.size(10.dp))
        Column(
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if(isToday)"TODAY: " else "Date: $date",
                fontFamily = FontFamily(Font(R.font.cinzel)),
            )
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
                    .background(
                        color = if(isToday) Color(0xffa7d979) else Color(0xff3b3f40),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Counter: $count",
                    fontFamily = FontFamily(Font(R.font.cinzel)),
                    color = if(isToday) Color.Black else Color.White
                )
            }

        }
    }
}