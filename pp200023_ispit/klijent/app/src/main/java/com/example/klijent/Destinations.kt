package com.example.klijent

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.Radar
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val icon: ImageVector
    val route: String
}

object DestinationA : Destination {
    override val icon: ImageVector = Icons.Default.FileDownload
    override val route: String = "destination_a"
}

object DestinationB : Destination {
    override val icon: ImageVector = Icons.Default.Radar
    override val route: String = "destination_b"
}

object DestinationC : Destination {
    override val icon: ImageVector = Icons.Default.More
    override val route: String = "destination_c"
}

val destinations = listOf(DestinationA, DestinationB, DestinationC)