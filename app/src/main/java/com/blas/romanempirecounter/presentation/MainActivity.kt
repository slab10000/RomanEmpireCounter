package com.blas.romanempirecounter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blas.romanempirecounter.presentation.composables.PagerIndicator
import com.blas.romanempirecounter.presentation.mainpage.MainScreen
import com.blas.romanempirecounter.presentation.ui.theme.RomanEmpireCounterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
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
                        val pagerState = rememberPagerState(pageCount = { 2 })
                        Column {
                            Box(
                                modifier = Modifier.weight(1f)
                            ){
                                HorizontalPager(state = pagerState) { page ->
                                    when(page){
                                        0 -> MainScreen()
                                        1 -> Box(modifier = Modifier.fillMaxSize()){ Text(text = "Holiii") }
                                    }
                                }
                            }

                            PagerIndicator(state = pagerState)
                        }
                        
                        //MainScreen()
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