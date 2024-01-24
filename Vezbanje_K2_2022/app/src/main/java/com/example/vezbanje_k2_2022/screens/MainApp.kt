package com.example.vezbanje_k2_2022.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vezbanje_k2_2022.database.PrimeViewModel
import com.example.vezbanje_k2_2022.navigation.Destination1
import com.example.vezbanje_k2_2022.navigation.Destination2
import com.example.vezbanje_k2_2022.navigation.destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp (
    navHostController: NavHostController,
    viewModel: PrimeViewModel,
    modifier: Modifier = Modifier
) {
    val currBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currDestination = currBackStackEntry?.destination
    val currRoute = currDestination?.route ?: Destination1.route

    Scaffold (
        modifier = modifier,
        bottomBar = {
            BottomAppBar {
                destinations.forEach {
                    NavigationBarItem(
                        selected = currRoute.startsWith(it.route),
                        onClick = {
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
                Screen1(
                    viewModel = viewModel
                )
            }
            composable(
                route = Destination2.route
            ) {
                Screen2(
                    viewModel = viewModel
                )
            }
        }
    }
}