package com.example.vezbanje_k1_2022


import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.vezbanje_k1_2022.screens.AppLandscape
import com.example.vezbanje_k1_2022.screens.AppPortrait
import com.example.vezbanje_k1_2022.ui.theme.Vezbanje_K1_2022Theme
import com.example.vezbanje_k1_2022.viewmodel.CalculatorState
import com.example.vezbanje_k1_2022.viewmodel.CalculatorViewModel

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    private val calculatorViewModel: CalculatorViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Vezbanje_K1_2022Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var list by rememberSaveable { mutableStateOf(listOf<CalculatorState>()) }
                    var screen by rememberSaveable { mutableStateOf(1) }
                    val conf = LocalConfiguration.current.orientation

                    this.onBackPressedDispatcher.addCallback {
                        if (screen == 2 && conf == Configuration.ORIENTATION_PORTRAIT) screen = 1;
                        else super.onBackPressed()
                    }

                    when (conf) {
                        Configuration.ORIENTATION_PORTRAIT -> AppPortrait(
                            list = list,
                            calculatorViewModel = calculatorViewModel,
                            screen = screen,
                            onChangeScreen = { screen = 3 - screen }
                        ) {
                            list = it
                        }
                        Configuration.ORIENTATION_LANDSCAPE -> AppLandscape(
                            list = list,
                            calculatorViewModel = calculatorViewModel
                        ) {
                            list = it
                        }
                        Configuration.ORIENTATION_SQUARE -> TODO()
                        Configuration.ORIENTATION_UNDEFINED -> TODO()
                    }
                }
            }
        }
    }
}