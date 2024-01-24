package com.example.vezbanje_k1_2022.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RadioButtonWithText(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRadioButtonWithText() {
    RadioButtonWithText(
        text = "Izbor",
        selected = true,
        onClick = { },
    )
}