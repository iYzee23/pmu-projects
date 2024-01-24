package com.example.vezbanje_k2_2022

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.vezbanje_k2_2022.database.PrimeViewModel
import com.example.vezbanje_k2_2022.screens.MainApp
import com.example.vezbanje_k2_2022.ui.theme.Vezbanje_K2_2022Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vezbanje_K2_2022Theme {
                val navHostController = rememberNavController()
                val viewModel: PrimeViewModel by viewModels()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp(
                        navHostController = navHostController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}