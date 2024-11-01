package com.example.yandexcup2024.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yandexcup2024.R

@Composable
fun ColorPalettePanel(onColorClick: (color: Color) -> Unit, onColorPaletteClick: () -> Unit) {

    Row(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3F),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp)
            .width(277.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = { /* Select pencil tool */onColorPaletteClick.invoke() }) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.color_palette),
                contentDescription = null
            )
        }
        val colors = listOf(Color.White, Color.Red, Color.Black, Color.Blue)
        colors.forEach { color ->
            IconButton(onClick = { /* Select pencil tool */onColorClick.invoke(color) }) {
                SelectColor(
                    isSelect = false,
                    selectedColor = color
                )
            }

        }
    }
}