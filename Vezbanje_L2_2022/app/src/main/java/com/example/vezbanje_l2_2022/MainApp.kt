package com.example.vezbanje_l2_2022

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
import androidx.navigation.navArgument
import com.example.vezbanje_l2_2022.database.Poseta
import com.example.vezbanje_l2_2022.database.PosetaViewModel
import com.example.vezbanje_l2_2022.navigation.Destination1
import com.example.vezbanje_l2_2022.navigation.Destination2
import com.example.vezbanje_l2_2022.navigation.Destination3
import com.example.vezbanje_l2_2022.navigation.destinations
import com.example.vezbanje_l2_2022.screens.Screen1
import com.example.vezbanje_l2_2022.screens.Screen2
import com.example.vezbanje_l2_2022.screens.Screen3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    navController: NavHostController,
    viewModel: PosetaViewModel,
    modifier: Modifier = Modifier
) {
    val currBackStackEntry by navController.currentBackStackEntryAsState()
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
                            if (!currRoute.startsWith(it.route))
                                viewModel.insert(Poseta(destinacija = it.route))
                            navController.navigate(it.route) {
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
            navController = navController ,
            startDestination = Destination1.route,
            modifier = Modifier.padding(it)
        ) {
            composable(
                route = Destination1.route
            ) {
                Screen1(viewModel = viewModel)
            }
            composable(
                route = Destination2.route
            ) {
                Screen2(
                    onClick = { numb ->
                        val dst = Destination3.route + "/" + numb.toString()
                        viewModel.insert(Poseta(destinacija = dst))
                        navController.navigate(dst)
                    }
                )
            }
            composable(
                route = Destination3.route + "/{buttonInfo}",
                arguments = listOf(navArgument("buttonInfo") { type = NavType.IntType })
            ) {nbse ->
                val buttonInfo = nbse.arguments?.getInt("buttonInfo") ?: 0
                Screen3(
                    number = buttonInfo,
                    navController = navController
                )
            }
        }
    }
}