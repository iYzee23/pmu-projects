package com.example.vezbanje_k1_2022.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun Spinner(
    value: String,
    onSelect: (String) -> Unit,
    options: List<String>,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { isExpanded = !isExpanded },
        ) {
            Text(text = value)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
        }

        // https://m2.material.io/components/menus/android
        // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            offset = DpOffset(0.dp, (-32).dp),
            modifier = Modifier.wrapContentSize(),
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        onSelect(it)
                        isExpanded = false
                    },
                )
            }
        }
    }
}