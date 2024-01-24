package com.example.vezba_l2.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Soap
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val icon: ImageVector
    val route: String
}

object Destination1 : Destination {
    override val icon: ImageVector = Icons.Default.Home
    override val route: String = "destination1"
}

object Destination2 : Destination {
    override val icon: ImageVector = Icons.Default.NearMe
    override val route: String = "destination2"
}

object Destination3 : Destination {
    override val icon: ImageVector = Icons.Default.Soap
    override val route: String = "destination3"
}

val destinations = listOf(Destination1, Destination2)