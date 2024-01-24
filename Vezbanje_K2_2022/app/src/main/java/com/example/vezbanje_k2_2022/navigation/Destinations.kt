package com.example.vezbanje_k2_2022.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val icon: ImageVector
    val route: String
}

object Destination1 : Destination {
    override val icon: ImageVector = Icons.Default.Home
    override val route: String = "destination1"
}

object Destination2: Destination {
    override val icon: ImageVector = Icons.Default.AccountCircle
    override val route: String = "destination2"
}

val destinations = listOf(Destination1, Destination2)