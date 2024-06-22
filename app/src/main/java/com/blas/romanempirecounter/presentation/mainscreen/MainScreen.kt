package com.blas.romanempirecounter.presentation.mainscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.blas.romanempirecounter.R
import com.blas.romanempirecounter.presentation.AutoResizedText
import com.blas.romanempirecounter.presentation.intToRoman

@Composable
fun MainScreen(){
    val counter = remember { mutableIntStateOf(0) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .paint(painterResource(id = R.drawable.corona_de_laureles))
            .clickableWithoutRipple {
                counter.intValue++
            }
            .padding(70.dp),
            contentAlignment = Alignment.Center
        ){
            AnimatedContent(
                targetState = counter.intValue,
                label = "",
                transitionSpec = {
                    fadeIn().togetherWith(fadeOut())
                },
            ) {counterIntValue ->
                AutoResizedText(intToRoman(counterIntValue))
            }

        }
    }
}

fun Modifier.clickableWithoutRipple(
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    onClick: () -> Unit
) = composed(
    factory = {
        this.then(
            Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick() }
            )
        )
    }
)