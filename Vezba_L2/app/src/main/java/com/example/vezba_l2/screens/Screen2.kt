package com.example.vezba_l2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.vezba_l2.navigation.Destination3

@Composable
fun Screen2(
    navHostController: NavHostController,
    onClick: (broj: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ) {
        Button(
            onClick = {
                onClick(1)
                navHostController.navigate(Destination3.route + "/1")
            }
        ) {
            Text(text = "Prvo dugme")
        }
        Button(
            onClick = {
                onClick(2)
                navHostController.navigate(Destination3.route + "/2")
            }
        ) {
            Text(text = "Drugo dugme")
        }
        Button(
            onClick = {
                onClick(3)
                navHostController.navigate(Destination3.route + "/3")
            }
        ) {
            Text(text = "Trece dugme")
        }
    }
}