package com.example.yandexcup2024.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SelectColor(
    isSelect: Boolean = false,
    selectedColor: Color = Color.Blue,

) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(color = selectedColor, shape = CircleShape)
            .border(
                width = 1.dp,
                color = if (isSelect) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground,
                shape = CircleShape
            )

    ) {

    }
}