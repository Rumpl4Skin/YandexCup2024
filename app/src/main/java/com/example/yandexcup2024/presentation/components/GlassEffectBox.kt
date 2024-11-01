package com.example.yandexcup2024.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GlassEffectBox(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier

            .background(Color.Gray) // Фоновый цвет под размытием
    ) {
        // Фон с размытием
        Box(
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.2f)) // Полупрозрачный белый фон
                .blur(16.dp) // Эффект размытия
        )

        // Содержимое поверх размытого фона
        Box(modifier = Modifier) {
            content()
        }
    }
}
