package com.example.yandexcup2024.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.yandexcup2024.R
import com.example.yandexcup2024.data.model.PathData

@Composable
fun DrawArea(modifier: Modifier, selectedColor: State<PathData>, pencilSelect: Boolean = false) {
    val bg = ImageBitmap.imageResource(id = R.drawable.canvas_bg)
    val paths = remember { mutableStateListOf(PathData()) }
    var currentPath by remember { mutableStateOf(Path()) }

        Canvas(
            modifier = modifier
                .padding(15.dp)
                .fillMaxSize()
                .background(Color.Gray, shape = RoundedCornerShape(20.dp))
                .pointerInput(pencilSelect) {
                    if (pencilSelect){
                        detectDragGestures(onDragStart = {
                            currentPath = Path()
                        }) { change, dragAmount ->
                            currentPath.moveTo(
                                change.position.x - dragAmount.x,
                                change.position.y - dragAmount.y
                            )
                            currentPath.lineTo(
                                change.position.x,
                                change.position.y
                            )

                            paths.add(
                                selectedColor.value.copy(path = currentPath)
                            )
                        }}}
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawImage(
                image = bg,
                dstSize = IntSize(canvasWidth.toInt(), canvasHeight.toInt()),
            )

            paths.forEach { pathData ->
                drawPath(
                    pathData.path,
                    color = pathData.color,
                    style = Stroke(5f)
                )
            }
        }
    }
