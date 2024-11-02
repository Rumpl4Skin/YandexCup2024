package com.example.yandexcup2024.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.yandexcup2024.R

@Composable
fun DrawArea(modifier: Modifier, selectedColor: Color, pencilSelect: Boolean = false) {
    val bg = ImageBitmap.imageResource(id = R.drawable.canvas_bg)
    val paths = remember { mutableStateListOf<Pair<Path, Color>>() }
    var currentPath by remember { mutableStateOf(Path()) }
    Box(
        modifier = modifier
            .padding(15.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        currentPath = Path().apply {
                            moveTo(offset.x, offset.y)
                        }
                    },
                    onDrag = { change, _ ->
                        currentPath.lineTo(change.position.x, change.position.y)
                        paths.add(Pair(currentPath, selectedColor))
                    },
                    onDragEnd = {
                        currentPath = Path()
                    }
                )
            }
    ) {
        Canvas(
            modifier = modifier
                .padding(15.dp)
                .fillMaxSize()
                .background(Color.Gray, shape = RoundedCornerShape(20.dp))
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawImage(
                image = bg,
                dstSize = IntSize(canvasWidth.toInt(), canvasHeight.toInt()),
            )

            //отрисовка всех путей
            paths.forEach { (path, color) ->
                drawPath(
                    path = path,
                    color = color,
                    style = Stroke(
                        width = 5f,
                        pathEffect = PathEffect.cornerPathEffect(4f)
                    )
                )
            }

            // Отрисовка текущего пути
            drawPath(
                path = currentPath,
                color = selectedColor,
                style = Stroke(
                    width = 5f,
                    pathEffect = PathEffect.cornerPathEffect(4f)
                )
            )
        }
    }
}