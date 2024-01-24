package com.example.klijent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.klijent.baza.ObavezaViewModel
import com.example.klijent.retrofit.ObavezaRetroViewModel
import com.example.klijent.screens.ScreenA
import com.example.klijent.screens.ScreenB
import com.example.klijent.screens.ScreenC
import com.example.klijent.ui.theme.KlijentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var taskReceiver: BroadcastReceiver

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val obavezaViewModel: ObavezaViewModel by viewModels()
            val obavezaRetroViewModel: ObavezaRetroViewModel by viewModels()
            val navHostController: NavHostController = rememberNavController()
            val currentBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentDestination = currentBackStackEntry?.destination
            val currentRoute = currentDestination?.route ?: DestinationA.route

            taskReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.action == Intent.ACTION_SCREEN_ON || intent.action == Intent.ACTION_USER_PRESENT) {
                        obavezaRetroViewModel.createNotificationForNearestTask(context)
                    }
                }
            }
            val filter = IntentFilter().apply {
                addAction(Intent.ACTION_SCREEN_ON)
                addAction(Intent.ACTION_USER_PRESENT)
            }
            val receiverFlags = ContextCompat.RECEIVER_NOT_EXPORTED
            ContextCompat.registerReceiver(this, taskReceiver, filter, receiverFlags)

            KlijentTheme {
                Scaffold {
                    NavHost(
                        navController = navHostController,
                        modifier = Modifier.padding(it),
                        startDestination = DestinationA.route,
                    ) {
                        composable(route = DestinationA.route) {
                            ScreenA(
                                obavezaViewModel = obavezaViewModel,
                                obavezaRetroViewModel = obavezaRetroViewModel,
                                onClickSecond = { navHostController.navigate("${DestinationB.route}/${it}") },
                                onClickThird = { navHostController.navigate(DestinationC.route) }
                            )
                        }
                        composable(
                            route = "${DestinationB.route}/{obaveza}",
                            arguments = listOf(navArgument("obaveza") { type = NavType.IntType })
                        ) {
                            val obaveza = it.arguments?.getInt("obaveza") ?: 1
                            ScreenB(
                                obavezaViewModel = obavezaViewModel,
                                obavezaRetroViewModel = obavezaRetroViewModel,
                                obavezaId = obaveza,
                                onClickFirst = { navHostController.navigateUp() }
                            )
                        }
                        composable(route = DestinationC.route) {
                            ScreenC(
                                obavezaViewModel = obavezaViewModel,
                                obavezaRetroViewModel = obavezaRetroViewModel,
                                onClickFirst = { navHostController.navigateUp() }
                            )
                        }
                    }
                }
            }

            val destination = intent.getStringExtra("destination")
            val obavezaId = intent.getIntExtra("obavezaId", -1)

            LaunchedEffect(navHostController) {
                if (destination == "DestinationB" && obavezaId != -1) {
                    navHostController.navigate("${DestinationB.route}/${obavezaId}")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(taskReceiver)
    }
}