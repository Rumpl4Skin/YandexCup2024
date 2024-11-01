package com.example.yandexcup2024.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorPalleteExtendedPanel(onColorClick: (color: Color) -> Unit) {
    val colorList = listOf(
        Color(0xFFFF0000), // Красный
        Color(0xFFFFA500), // Оранжевый
        Color(0xFFFFFF00), // Желтый
        Color(0xFF008000), // Зеленый
        Color(0xFF00FFFF), // Голубой
        Color(0xFF0000FF), // Синий
        Color(0xFF800080), // Фиолетовый
        Color(0xFFFFC0CB), // Розовый
        Color(0xFF000000), // Черный
        Color(0xFFFFFFFF), // Белый
        Color(0xFFA52A2A), // Коричневый
        Color(0xFF808080), // Серый
        Color(0xFFFFD700), // Золотой
        Color(0xFFC0C0C0), // Серебряный
        Color(0xFF800000), // Темно-красный
        Color(0xFFFF4500), // Оранжево-красный
        Color(0xFFDA70D6), // Орхидея
        Color(0xFF2E8B57), // Морской зеленый
        Color(0xFF20B2AA), // Светлый морской зеленый
        Color(0xFF7B68EE), // Средний фиолетовый
        Color(0xFFADFF2F), // Желто-зеленый
        Color(0xFF4B0082), // Индиго
        Color(0xFF00FA9A), // Мятно-зеленый
        Color(0xFFB22222), // Огненно-красный
        Color(0xFF4682B4)  // Стальной синий
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3F),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp)
            .width(277.dp),
    ) {
        items(colorList.size) { index ->
            IconButton(onClick = { onColorClick(colorList[index]) }) {
                SelectColor(selectedColor = colorList[index])
            }

        }

    }
}