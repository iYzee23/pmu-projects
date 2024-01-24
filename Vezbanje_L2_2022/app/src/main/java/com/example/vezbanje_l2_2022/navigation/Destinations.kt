package com.example.vezbanje_l2_2022.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val icon: ImageVector
    val route: String
}

object Destination1 : Destination {
    override val icon = Icons.Default.Home
    override val route = "destination1"
}

object Destination2: Destination {
    override val icon = Icons.Default.NearMe
    override val route = "destination2"
}

object Destination3: Destination {
    override val icon = Icons.Default.Favorite
    override val route = "destination3"
}

val destinations = listOf(Destination1, Destination2)