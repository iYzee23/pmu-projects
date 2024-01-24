package com.example.vezba_l2.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vezba_l2.database.Zapis
import com.example.vezba_l2.database.ZapisViewModel
import com.example.vezba_l2.navigation.Destination1
import com.example.vezba_l2.navigation.Destination2
import com.example.vezba_l2.navigation.Destination3
import com.example.vezba_l2.navigation.destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    navHostController: NavHostController,
    zapisViewModel: ZapisViewModel,
    modifier: Modifier = Modifier
) {
    val currBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currDestination = currBackStackEntry?.destination
    val currRoute = currDestination?.route ?: Destination1.route
    val maxRedni by zapisViewModel.maxRedni.collectAsState(initial = null)

    Scaffold (
        bottomBar = {
            BottomAppBar {
                destinations.forEach {
                    NavigationBarItem(
                        selected = currRoute.startsWith(it.route),
                        onClick = {
                            if (!currRoute.startsWith(it.route)) {
                                zapisViewModel.insert(
                                    Zapis(
                                        redniBrojPosete = if (maxRedni != null) maxRedni!! + 1 else 1,
                                        imeDestinacije = it.route
                                    )
                                )
                            }
                            navHostController.navigate(it.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(Destination1.route) {
                                    saveState = true
                                    inclusive = false
                                }
                            }
                        },
                        icon = {
                           Icon(
                               imageVector = it.icon,
                               contentDescription = null
                           )
                        },
                        label = { Text(text = it.route) }
                    )
                }
            }
        }
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Destination1.route,
            modifier = modifier.padding(it)
        ) {
            composable(
                route = Destination1.route
            ) {
                Screen1(zapisViewModel = zapisViewModel)
            }
            composable(
                route = Destination2.route
            ) {
                Screen2(
                    navHostController = navHostController,
                    onClick = { rut ->
                        zapisViewModel.insert(
                            Zapis(
                                redniBrojPosete = if (maxRedni != null) maxRedni!! + 1 else 1,
                                imeDestinacije = Destination3.route + "/" + rut.toString()
                            )
                        )
                    }
                )
            }
            composable(
                route = Destination3.route + "/{param}",
                arguments = listOf(navArgument("param") { type = NavType.IntType })
            ) {
                val param = it.arguments?.getInt("param") ?: 0
                Screen3(
                    num = param,
                    navHostController = navHostController
                )
            }
        }
    }
}