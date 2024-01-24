package com.example.vezbanje_l3_2022.ui.elements

import androidx.compose.material3.Checkbox
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CheckBoxWithText(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Checkbox (
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}