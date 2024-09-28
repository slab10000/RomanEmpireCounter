package com.blas.romanempirecounter.presentation

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blas.romanempirecounter.presentation.composables.PagerIndicator
import com.blas.romanempirecounter.presentation.mainpage.MainScreen
import com.blas.romanempirecounter.presentation.mainpage.MainScreenEvent
import com.blas.romanempirecounter.presentation.mainpage.MainScreenEvent.InitializeDayEvent
import com.blas.romanempirecounter.presentation.mainpage.MainScreenViewModel
import com.blas.romanempirecounter.presentation.secondScreen.composables.SecondScreen
import com.blas.romanempirecounter.presentation.ui.theme.RomanEmpireCounterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainScreenViewModel by viewModels()
    private lateinit var dayChangeReceiver: DayChangeReceiver

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar el BroadcastReceiver con la acción a realizar cuando cambia el día
        dayChangeReceiver = DayChangeReceiver {
            Log.i("mitag", "CAMBIO DE DIA")
            viewModel.onEvent(InitializeDayEvent)
        }

        setContent {
            RomanEmpireCounterTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = AppScreens.MainScreen.name
                ){
                    composable(route = AppScreens.MainScreen.name) {
                        CompositionLocalProvider(value = LocalViewModelStoreOwner provides this@MainActivity) {
                            val pagerState = rememberPagerState(pageCount = { 2 })
                            Column {
                                Box(
                                    modifier = Modifier.weight(1f)
                                ){
                                    HorizontalPager(state = pagerState) { page ->
                                        when(page){
                                            0 -> MainScreen()
                                            1 -> SecondScreen()
                                        }
                                    }
                                }

                                PagerIndicator(state = pagerState)
                            }
                        }
                    }

                    /*composable(route = AppScreens.SecondScreen.name) {
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Green)) {
                            Text(text = "esto es la segunda pantalla")
                        }
                    }*/
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.onEvent(InitializeDayEvent)
        // Registrar el BroadcastReceiver para escuchar el cambio de día
        val filter = IntentFilter(Intent.ACTION_DATE_CHANGED)
        registerReceiver(dayChangeReceiver, filter)
    }

    override fun onPause() {
        super.onPause()

        // Desregistrar el BroadcastReceiver cuando la app ya no esté en primer plano
        unregisterReceiver(dayChangeReceiver)
    }
}