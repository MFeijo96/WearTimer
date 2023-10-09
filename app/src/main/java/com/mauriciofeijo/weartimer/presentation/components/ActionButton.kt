package com.mauriciofeijo.weartimer.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text

@Composable
fun ActionButton(text: String, enabled: Boolean = true, onClickAction: () -> Unit) {
    Button(modifier = Modifier.padding(end = 10.dp), onClick = onClickAction, enabled = enabled) {
        Text(text = text)
    }
}