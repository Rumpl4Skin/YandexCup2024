package com.example.yandexcup2024.data.model


import android.graphics.Path
import androidx.compose.ui.graphics.Color


data class PathData (
    val path: Path = Path(),
    val color: Color = Color.Blue,
)