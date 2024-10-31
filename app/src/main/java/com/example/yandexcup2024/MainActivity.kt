package com.example.yandexcup2024

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yandexcup2024.ui.theme.YandexCup2024Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            YandexCup2024Theme(darkTheme = true) {

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
                ) { innerPadding ->
                    DrawArea(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun TabBar(modifier: Modifier = Modifier) {
    BottomAppBar {
        IconButton(onClick = { /* Select pencil tool */ }) {
            Icon(Icons.Default.Create, contentDescription = "Pencil")
        }
        IconButton(onClick = { /* Select eraser tool */ }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Eraser")
        }
        IconButton(onClick = { /* Additional tool */ }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Additional Tool")
        }
    }
}

@Composable
fun Headers(modifier: Modifier = Modifier) {
    Row {
        IconButton(onClick = { /* Undo action */ }) {
            Icon(painter = painterResource(id = R.drawable.bin), contentDescription = null)
        }
        IconButton(onClick = { /* Redo action */ }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Redo")
        }
        IconButton(onClick = { /* Select color */ }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Select Color")
        }
    }
}

@Composable
fun DrawArea(modifier: Modifier) {
    Canvas(
        modifier = modifier
            .padding(15.dp)
            .fillMaxSize()
            .background(Color.Gray, shape = RoundedCornerShape(20.dp))
    ) {
        // Drawing logic here
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YandexCup2024Theme {

    }
}