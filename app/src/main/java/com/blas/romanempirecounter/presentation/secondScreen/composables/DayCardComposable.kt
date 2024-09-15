package com.blas.romanempirecounter.presentation.secondScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.blas.romanempirecounter.R

@Composable
fun DayCardComposable(
    modifier: Modifier = Modifier,
    date: String,
    count: String
) {
    Row(
        modifier = Modifier
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .background(
                color = Color(0xffb5c7a4),
                shape = RoundedCornerShape(16.dp)
            )
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
                text = "Date: $date",
                fontFamily = FontFamily(Font(R.font.cinzel))
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Counter: $count",
                fontFamily = FontFamily(Font(R.font.cinzel))
            )
        }
    }
}