package com.example.yandexcup2024.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.yandexcup2024.R
import com.example.yandexcup2024.presentation.components.ColorPalettePanel
import com.example.yandexcup2024.presentation.components.ColorPalleteExtendedPanel
import com.example.yandexcup2024.presentation.components.GlassEffectBox
import com.example.yandexcup2024.presentation.components.SelectColor
import com.example.yandexcup2024.ui.theme.YandexCup2024Theme
import com.google.relay.compose.BoxScopeInstanceImpl.align

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            YandexCup2024Theme(darkTheme = true, dynamicColor = false) {

                var palleteVisible by remember { mutableStateOf(false) }
                var palleteExtendedVisible by remember { mutableStateOf(false) }
                var selectedColor by remember { mutableStateOf(Color.Blue) }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { },
                            actions = {
                                Headers()
                            },
                            colors = TopAppBarColors(
                                containerColor = Color.Transparent,
                                scrolledContainerColor = Color.Transparent,
                                navigationIconContentColor = Color.White,
                                titleContentColor = Color.White,
                                actionIconContentColor = Color.White
                            )
                        )
                    },
                    bottomBar = {
                        TabBar(
                            onColorClick = {
                                palleteVisible = !palleteVisible

                                palleteExtendedVisible = false

                            },
                            selectedColor = selectedColor
                        )
                    },
                    floatingActionButton = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            AnimatedVisibility(
                                modifier = Modifier.padding(bottom = 15.dp),
                                visible = palleteExtendedVisible,
                                enter = slideInVertically(
                                    initialOffsetY = { it }, // Начальная позиция выезда элемента (сверху)
                                    animationSpec = tween(durationMillis = 300) // Настройка продолжительности анимации
                                ) + fadeIn(animationSpec = tween(durationMillis = 300)),
                                exit = slideOutVertically(
                                    targetOffsetY = { it }, // Конечная позиция выезда элемента (сверху)
                                    animationSpec = tween(durationMillis = 300) // Настройка продолжительности анимации
                                ) + fadeOut(animationSpec = tween(durationMillis = 300))
                            ) {
                                ColorPalleteExtendedPanel {
                                    selectedColor = it
                                    palleteExtendedVisible = !palleteExtendedVisible
                                    palleteVisible = !palleteVisible
                                }
                            }

                            AnimatedVisibility(
                                visible = palleteVisible,
                                enter = slideInVertically(
                                    initialOffsetY = { it }, // Начальная позиция выезда элемента (сверху)
                                    animationSpec = tween(durationMillis = 300) // Настройка продолжительности анимации
                                ) + fadeIn(animationSpec = tween(durationMillis = 300)),
                                exit = slideOutVertically(
                                    targetOffsetY = { it }, // Конечная позиция выезда элемента (сверху)
                                    animationSpec = tween(durationMillis = 300) // Настройка продолжительности анимации
                                ) + fadeOut(animationSpec = tween(durationMillis = 300))
                            ) {

                                ColorPalettePanel(
                                    onColorClick = {
                                        palleteVisible = !palleteVisible
                                        selectedColor = it
                                        palleteExtendedVisible = false

                                    },
                                    onColorPaletteClick = {
                                        palleteExtendedVisible = !palleteExtendedVisible
                                        if (!palleteVisible) {
                                            palleteExtendedVisible = false
                                        }
                                    }
                                )

                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    content = { innerPadding ->
                        DrawArea(
                            modifier = Modifier.padding(innerPadding),
                            selectedColor = selectedColor
                        )
                    }
                )


            }
        }
    }
}


@Composable
fun TabBar(
    modifier: Modifier = Modifier,
    pencilSelect: Boolean = true,
    eraserSelect: Boolean = false,
    colorSelect: Boolean = false,
    selectedColor: Color = Color.Blue,
    onColorClick: () -> Unit
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
            IconButton(onClick = { }) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.pencil),
                    contentDescription = null,
                    tint = if (pencilSelect) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground
                )
            }
            /* Select eraser tool */
            IconButton(onClick = { }) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.erase),
                    contentDescription = null,
                    tint = if (eraserSelect) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground

                )
            }
            /* Color Select tool */
            IconButton(onClick = { onColorClick.invoke() }) {
                SelectColor(
                    isSelect = colorSelect,
                    selectedColor = selectedColor
                )
            }
        }
    }
}

@Composable
fun Headers(
    modifier: Modifier = Modifier,
    undoActive: Boolean = false,
    redoActive: Boolean = false,
    playActive: Boolean = false,
    pauseActive: Boolean = false,

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = { /* Undo action */ }) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .weight(1f),
                painter = painterResource(id = if (undoActive) R.drawable.undo_active else R.drawable.undo_unactive),
                contentDescription = null
            )
        }
        IconButton(onClick = { /* Redo action */ }) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .weight(1f),
                painter = painterResource(id = if (redoActive) R.drawable.redo_active else R.drawable.redo_unactive),
                contentDescription = null
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { /* Bin action */ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.bin),
                    contentDescription = null
                )
            }
            IconButton(onClick = { /* frame add action */ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.file_plus),
                    contentDescription = null
                )
            }
            IconButton(onClick = { /* layers action */ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.layers),
                    contentDescription = null
                )
            }
        }

        IconButton(onClick = { /* pause action */ }) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .weight(1f),
                painter = painterResource(id = if (pauseActive) R.drawable.pause_active else R.drawable.pause_unactive),
                contentDescription = null
            )
        }
        IconButton(onClick = { /* play action */ }) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .weight(1f),
                painter = painterResource(id = if (playActive) R.drawable.play_active else R.drawable.play_unactive),
                contentDescription = null
            )
        }
    }
}

@Composable
fun DrawArea(modifier: Modifier, selectedColor: Color) {
    val bg = ImageBitmap.imageResource(id = R.drawable.canvas_bg)
    val paths = remember { mutableStateListOf<Pair<Path, Color>>() }
    var currentPath by remember { mutableStateOf(Path()) }
    var drawColor by remember { mutableStateOf(selectedColor) }
    Box(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        currentPath = Path().apply {
                            moveTo(offset.x, offset.y)
                        }
                    },
                    onDrag = { change, _ ->
                        currentPath.lineTo(change.position.x, change.position.y)
                        paths.add(Pair(currentPath, drawColor))
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YandexCup2024Theme {

    }
}