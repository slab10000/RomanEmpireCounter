package com.blas.romanempirecounter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blas.romanempirecounter.presentation.mainscreen.MainScreen
import com.blas.romanempirecounter.presentation.mainscreen.getRandomCaesarQuote
import com.blas.romanempirecounter.presentation.ui.theme.RomanEmpireCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RomanEmpireCounterTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = AppScreens.MainScreen.name
                ){
                    composable(route = AppScreens.MainScreen.name) {
                        MainScreen(getRandomCaesarQuote().quote)
                    }

                    composable(route = AppScreens.SecondScreen.name) {
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Green)) {
                            Text(text = "esto es la segunda pantalla")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RomanEmpireCounterTheme {
        Greeting("Android")
    }
}