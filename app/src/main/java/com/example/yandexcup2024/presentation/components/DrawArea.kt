package com.example.yandexcup2024.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.yandexcup2024.R
import com.example.yandexcup2024.data.model.PathData
import com.example.yandexcup2024.data.model.SelectableInstruments
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DrawArea(
    modifier: Modifier,
    selectedColor: State<PathData>,
    selectedInstruments: StateFlow<SelectableInstruments>,
    pathsVM: SnapshotStateList<PathData>,
    undonePathsVM: SnapshotStateList<PathData>,
    onUpdatePaths: (SnapshotStateList<PathData>) -> Unit,
) {
    val bg = ImageBitmap.imageResource(id = R.drawable.canvas_bg)
    val paths = remember { pathsVM }
    val undonePaths = remember { undonePathsVM }
    var currentPath by remember { mutableStateOf(Path()) }
    val backgroundColor = Color.Transparent // Цвет фона
    val selectedInstrument by selectedInstruments.collectAsState()

    Canvas(
        modifier = modifier
            .padding(15.dp)
            .fillMaxSize()
            .background(backgroundColor, shape = RoundedCornerShape(20.dp))
            .pointerInput((selectedInstrument == SelectableInstruments.Pencil) or (selectedInstrument == SelectableInstruments.Erase)) {
                if ((selectedInstrument == SelectableInstruments.Pencil) or (selectedInstrument == SelectableInstruments.Erase)) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentPath = Path().apply { moveTo(offset.x, offset.y) }
                        },
                        onDrag = { change, _ ->
                            currentPath.lineTo(
                                change.position.x,
                                change.position.y
                            )

                        },
                        onDragEnd = {
                            if (!currentPath.isEmpty) {
                                if (selectedInstrument == SelectableInstruments.Erase) {
                                    paths.add(
                                        selectedColor.value.copy(
                                            path = currentPath,
                                            isErase = true
                                        )
                                    )
                                } else {
                                    paths.add(selectedColor.value.copy(path = currentPath))
                                }
                                onUpdatePaths(paths)
                            }
                            currentPath = Path() // Новый путь для следующего рисования
                        }
                    )
                }
            })
    /*{ change, dragAmount ->
            currentPath.moveTo(
                change.position.x - dragAmount.x,
                change.position.y - dragAmount.y
            )
            currentPath.lineTo(
                change.position.x,
                change.position.y
            )

            if (selectedInstrument == SelectableInstruments.Erase) {
                paths.add(
                    selectedColor.value.copy(path = currentPath, isErase = true)
                )
            } else {
                paths.add(
                    selectedColor.value.copy(path = currentPath)
                )

            }
            onUpdatePaths(paths)
            undonePaths.clear()
        }
        }
}
) {*/{
        val canvasWidth = size.width
        val canvasHeight = size.height


        drawImage(
            image = bg,
            dstSize = IntSize(canvasWidth.toInt(), canvasHeight.toInt()),
        )

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)
            paths.forEach { pathData ->
                if (!pathData.isErase) {
                    drawPath(
                        pathData.path,
                        color = pathData.color,
                        style = Stroke(5f)
                    )
                } else {
                    drawPath(
                        pathData.path,
                        color = Color.Transparent,
                        style = Stroke(5f),
                        blendMode = BlendMode.Clear
                    )
                }
            }
            restoreToCount(checkPoint)
        }
    }
}
