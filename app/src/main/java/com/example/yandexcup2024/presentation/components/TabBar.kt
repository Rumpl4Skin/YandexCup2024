package com.example.yandexcup2024.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yandexcup2024.R

@Composable
fun TabBar(
    modifier: Modifier = Modifier,
    pencilSelect: Boolean = false,
    eraserSelect: Boolean = false,
    colorSelect: Boolean = false,
    selectedColor: Color = Color.Blue,
    onPencilSelectInstrumentClick: () -> Unit,
    onEraseSelectInstrumentClick: () -> Unit,
    onColorSelectInstrumentClick: () -> Unit,
) {


    BottomAppBar(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 25.dp),
        containerColor = MaterialTheme.colorScheme.background
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            /* Select pencil tool */
            IconButton(onClick = { onPencilSelectInstrumentClick.invoke() }) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.pencil),
                    contentDescription = null,
                    tint = if (pencilSelect) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground
                )
            }
            /* Select eraser tool */
            IconButton(onClick = { onEraseSelectInstrumentClick.invoke() }) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.erase),
                    contentDescription = null,
                    tint = if (eraserSelect) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground

                )
            }
            /* Color Select tool */
            IconButton(onClick = { onColorSelectInstrumentClick.invoke() }) {
                SelectColor(
                    isSelect = colorSelect,
                    selectedColor = selectedColor
                )
            }
        }
    }
}