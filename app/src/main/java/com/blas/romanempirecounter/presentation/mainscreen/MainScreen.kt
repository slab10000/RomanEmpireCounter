package com.blas.romanempirecounter.presentation.mainscreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.blas.romanempirecounter.R
import com.blas.romanempirecounter.presentation.AutoResizedText
import kotlin.random.Random

@SuppressLint("RememberReturnType")
@Composable
fun MainScreen(
    caesarQuote : String
){
    val counter = remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val infiniteChangeSizeTransition = rememberInfiniteTransition(label = "")
        val infiniteChangingPadding = infiniteChangeSizeTransition.animateFloat(
            initialValue = 50.toFloat(),
            targetValue = 45.toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(1500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = ""
        )
        var hasToChangeSize by remember {
            mutableStateOf(true)
        }

        val onClickChangePadding = remember { Animatable(50f) }

        LaunchedEffect(counter.intValue) {
            onClickChangePadding.animateTo(
                targetValue = 45f,
                animationSpec = tween(100)
            )
            onClickChangePadding.animateTo(
                targetValue = 50f,
                animationSpec = tween(100)
            )
        }

        AutoResizedText(
            modifier = Modifier.padding(start = 30.dp, end = 30.dp,top = 30.dp),
            text = "Roman Empire Counter")

        AutoResizedText(
            modifier = Modifier.padding(horizontal = 80.dp),
            text = "Pulsa para incrementar")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    if (hasToChangeSize) {
                        infiniteChangingPadding.value.dp
                    } else {
                        onClickChangePadding.value.dp
                    }
                ),
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .paint(painterResource(id = R.drawable.corona_de_laureles))
                .clickableWithoutRipple {
                    counter.intValue++
                    hasToChangeSize = false
                }
                .padding(50.dp),
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
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "- $caesarQuote",
                style = TextStyle(fontStyle = FontStyle.Italic),
                fontFamily = FontFamily(Font(R.font.cinzel))
            )
        }
    }

}

@SuppressLint("UnnecessaryComposedModifier")
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

fun intToRoman(num: Int): String {
    if(num == 0) return "-"

    val romanNumerals = listOf(
        Pair(1000, "M"), Pair(900, "CM"), Pair(500, "D"), Pair(400, "CD"),
        Pair(100, "C"), Pair(90, "XC"), Pair(50, "L"), Pair(40, "XL"),
        Pair(10, "X"), Pair(9, "IX"), Pair(5, "V"), Pair(4, "IV"), Pair(1, "I")
    )

    var number = num
    val stringBuilder = StringBuilder()

    for ((value, symbol) in romanNumerals) {
        while (number >= value) {
            stringBuilder.append(symbol)
            number -= value
        }
    }

    return stringBuilder.toString()
}

fun getRandomCaesarQuote(): CaesarQuotes {
    val quotes = CaesarQuotes.entries.toTypedArray()
    val randomIndex = Random.nextInt(quotes.size)
    return quotes[randomIndex]
}