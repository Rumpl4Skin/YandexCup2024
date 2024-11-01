package com.example.yandexcup2024.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.yandexcup2024.R
import com.example.yandexcup2024.presentation.components.ColorPalettePanel
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

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { },
                            actions = {
                                Headers()
                            }
                        )
                    },
                    bottomBar = {
                        TabBar()
                    },
                    floatingActionButton = {
                        ColorPalettePanel({})
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    content = { innerPadding ->
                        DrawArea(modifier = Modifier.padding(innerPadding))
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
) {


    BottomAppBar(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 25.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { /* Select pencil tool */ }) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.pencil),
                    contentDescription = null,
                    tint = if (pencilSelect) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = { /* Select eraser tool */ }) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.erase),
                    contentDescription = null,
                    tint = if (eraserSelect) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground

                )
            }
            IconButton(onClick = { /* Color Select tool */ }) {
                SelectColor(
                    isSelect = colorSelect,
                    selectedColor = Color.Blue
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
fun DrawArea(modifier: Modifier) {
    val bg = ImageBitmap.imageResource(id = R.drawable.canvas_bg)
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

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YandexCup2024Theme {

    }
}