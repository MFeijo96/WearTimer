package com.mauriciofeijo.weartimer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ColumnScope.SoundBar(color: Color = Color.White, isVisibile: Boolean = true) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .weight(0.5f)
            .background(if (isVisibile) color else Color.Transparent)
    ){}
}