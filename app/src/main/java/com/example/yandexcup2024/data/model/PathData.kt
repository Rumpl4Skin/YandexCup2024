package com.example.yandexcup2024.data.model


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path


data class PathData (
    val path: Path = Path(),
    val color: Color = Color.Unspecified,
    val isErase: Boolean = false,
)