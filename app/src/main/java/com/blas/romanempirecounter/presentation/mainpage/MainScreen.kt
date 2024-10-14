package com.blas.romanempirecounter.presentation.mainpage

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.HapticFeedbackConstantsCompat
import com.blas.romanempirecounter.R
import com.blas.romanempirecounter.presentation.composables.AutoResizedText
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blas.romanempirecounter.domain.model.DayModel
import com.blas.romanempirecounter.presentation.mainpage.MainScreenEvent.CounterOnClick
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("RememberReturnType")
@Composable
fun MainScreen(){
    val caesarQuote = remember { mutableStateOf(getRandomCaesarQuote().phrase) }

    //Clipboard
    val clipboard = LocalClipboardManager.current

    //view for haptic feedback
    val view = LocalView.current

    // Obtener el ViewModelStoreOwner actual
    val viewModelStoreOwner = LocalViewModelStoreOwner.current

    // Obtener el ViewModel usando hiltViewModel() con el ViewModelStoreOwner actual
    val viewModel: MainScreenViewModel = hiltViewModel(viewModelStoreOwner!!)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val infiniteChangeSizeTransition = rememberInfiniteTransition(label = "")
            val infiniteChangingPadding = infiniteChangeSizeTransition.animateFloat(
                initialValue = 50.toFloat(),
                targetValue = 40.toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = ""
            )

            /** Variable que cambia de valor cada vez que se clicka
             * hace la animación de agrandarse un poco con el click
             */
            val onClickChangePadding = remember { Animatable(50f) }

            LaunchedEffect(viewModel.state.value.counter) {
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
                modifier = Modifier.padding(horizontal = 40.dp),
                text = "Press to increase / Hold to reset")

            Column(
                modifier = Modifier
                    .padding(
                        if (viewModel.state.value.isAnimationOn) {
                            infiniteChangingPadding.value.dp
                        } else {
                            onClickChangePadding.value.dp
                        }
                    ),
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .paint(painterResource(id = R.drawable.corona_de_laureles))
                    .combinedClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            viewModel.onEvent(CounterOnClick(viewModel.state.value.counter + 1))

                            view.performHapticFeedback(HapticFeedbackConstantsCompat.CONTEXT_CLICK)
                            caesarQuote.value = getRandomCaesarQuote().phrase
                        },
                        onLongClick = {
                            view.performHapticFeedback(HapticFeedbackConstantsCompat.LONG_PRESS)
                            viewModel.onEvent(CounterOnClick(0))
                        }
                    )
                    .padding(95.dp),
                    contentAlignment = Alignment.Center
                ){
                    AnimatedContent(
                        targetState = viewModel.state.value.counter,
                        label = "",
                        transitionSpec = {
                            fadeIn().togetherWith(fadeOut())
                        },
                    ) {counterIntValue ->
                        AutoResizedText(intToRoman(counterIntValue))
                    }

                }
            }

            Box(
                modifier = Modifier.padding(top = 10.dp),
                 contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 120.dp)
                    ,
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedContent(
                        contentAlignment = Alignment.BottomCenter,
                        targetState = caesarQuote.value,
                        transitionSpec = {
                            (slideInHorizontally { height -> height } + fadeIn()).togetherWith(
                                slideOutHorizontally { height -> -height } + fadeOut())
                                .using(
                                    SizeTransform(clip = false)
                                )
                        },
                        label = ""
                    ){ caesarQuote ->
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, end = 50.dp)
                                .clickable { clipboard.setText(AnnotatedString(caesarQuote)) },
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = 30.dp, end = 30.dp)
                                    .align(Alignment.TopCenter),
                                textAlign = TextAlign.Center,
                                text = "\"${caesarQuote}\"",
                                style = TextStyle(fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold),
                                fontFamily = FontFamily(Font(R.font.roboto_slab_thin)),
                                fontSize = MaterialTheme.typography.headlineSmall.fontSize
                            )
                            Icon(
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.TopEnd),
                                imageVector = Icons.Filled.ContentCopy,
                                contentDescription = "")
                        }
                    }
                }
            }
        }
    }


}

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

fun getRandomCaesarQuote(): LatinPhrases {
    val quotes = LatinPhrases.entries.toTypedArray()
    val randomIndex = Random.nextInt(quotes.size)
    return quotes[randomIndex]
}