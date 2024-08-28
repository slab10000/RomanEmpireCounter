package com.blas.romanempirecounter.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.blas.romanempirecounter.presentation.AppScreens
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    state: PagerState
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(AppScreens.entries.size) { i ->
            val pageOffSet = ((state.currentPage - i)
                    + state.currentPageOffsetFraction).absoluteValue
            Box(
                modifier = Modifier
                    .size(32.dp),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    Modifier
                        .size(
                            lerp(14.dp, 32.dp, 1f - pageOffSet.coerceIn(0f,1f))
                        )
                        .blur(
                            radius = lerp(0.dp, 16.dp, 1f - pageOffSet.coerceIn(0f,1f)),
                            edgeTreatment = BlurredEdgeTreatment.Unbounded,
                        )
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(FIRST_BLUR_COLOR),
                                    Color(SECOND_BLUR_COLOR),
                                )
                            ),
                            shape = CircleShape
                        )
                )
                Box(
                    Modifier
                        .size(
                            lerp(14.dp, 22.dp, 1f - pageOffSet.coerceIn(0f,1f))
                        )
                        .background(
                            color = Color.Black,
                            shape = CircleShape,
                        )
                )
            }
        }
    }
}

private const val FIRST_BLUR_COLOR = 0xFF69b580 // Green
private const val SECOND_BLUR_COLOR = 0xFF92705a // Brown
