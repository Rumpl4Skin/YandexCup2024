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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yandexcup2024.data.model.CurrentFrame
import com.example.yandexcup2024.data.model.SelectableInstruments
import com.example.yandexcup2024.presentation.components.ColorPalettePanel
import com.example.yandexcup2024.presentation.components.ColorPalleteExtendedPanel
import com.example.yandexcup2024.presentation.components.DrawArea
import com.example.yandexcup2024.presentation.components.Headers
import com.example.yandexcup2024.presentation.components.TabBar
import com.example.yandexcup2024.ui.theme.YandexCup2024Theme
import kotlinx.coroutines.flow.asFlow

class MainActivity : ComponentActivity() {


    private lateinit var viewModel: MainActivityViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            YandexCup2024Theme(darkTheme = true, dynamicColor = false) {

                viewModel = viewModel()

                val paletteVisible by viewModel.paletteVisible.collectAsState()
                val paletteExtendedVisible by viewModel.paletteExtendedVisible.collectAsState()
                val selectedColor = viewModel.selectedColor.collectAsState()
                val selectedInstrument by viewModel.selectedInstrument.collectAsState()

                val currentFrame by viewModel.currentFrame.collectAsState()
                val frames = viewModel.frames.collectAsState()
                val currentIndex by viewModel.currentIndex.collectAsState(0)

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { },
                            actions = {
                                Headers(
                                    undoActive = currentFrame.paths.isNotEmpty(),
                                    redoActive = currentFrame.undonePaths.isNotEmpty(),
                                    binActive = frames.value.isNotEmpty(),
                                    onUndo = viewModel::undo,
                                    onRedo = viewModel::redo,
                                    onClearCurrentFrame = {
                                        if (frames.value.size > 1) {
                                            viewModel.removeCurrentFrame(currentFrame)
                                            currentFrame.paths.clear()
                                            currentFrame.undonePaths.clear()
                                            //viewModel.changeIndex(frames.value.lastIndex)
                                            viewModel.updateCurrentFrame(frames.value[currentIndex])
                                        }
                                        else{
                                            currentFrame.paths.clear()
                                            currentFrame.undonePaths.clear()
                                        }
                                    },
                                    onFrameAdd = {
                                        val newFrame = CurrentFrame()
                                        viewModel.addFrame(newFrame)
                                        viewModel.changeIndex(currentIndex + 1)
                                        viewModel.updateCurrentFrame(newFrame)
                                        currentFrame.paths.clear()
                                        currentFrame.undonePaths.clear()
                                    }

                                )
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
                            pencilSelect = selectedInstrument == SelectableInstruments.Pencil,
                            eraserSelect = selectedInstrument == SelectableInstruments.Erase,
                            colorSelect = selectedInstrument == SelectableInstruments.ColorSelect,
                            onPencilSelectInstrumentClick = {
                                viewModel.updateSelectedInstrument(SelectableInstruments.Pencil)
                                viewModel.updatePaletteVisible(false)
                                viewModel.updatePaletteExtendedVisible(false)
                            },
                            onEraseSelectInstrumentClick = {
                                viewModel.updateSelectedInstrument(SelectableInstruments.Erase)
                                viewModel.updatePaletteVisible(false)
                                viewModel.updatePaletteExtendedVisible(false)
                            },
                            onColorSelectInstrumentClick = {
                                viewModel.updatePaletteVisible(!paletteVisible)
                                viewModel.updatePaletteExtendedVisible(false)
                                viewModel.updateSelectedInstrument(SelectableInstruments.ColorSelect)
                            },
                            selectedColor = selectedColor.value.color
                        )
                    },
                    floatingActionButton = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            AnimatedVisibility(
                                modifier = Modifier.padding(bottom = 15.dp),
                                visible = paletteExtendedVisible,
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
                                    viewModel.updateSelectedColor(selectedColor.value.copy(color = it))
                                    viewModel.updatePaletteExtendedVisible(!paletteExtendedVisible)
                                    viewModel.updatePaletteVisible(!paletteVisible)
                                }
                            }

                            AnimatedVisibility(
                                visible = paletteVisible,
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
                                        viewModel.updatePaletteVisible(!paletteVisible)
                                        viewModel.updateSelectedColor(selectedColor.value.copy(color = it))
                                        viewModel.updatePaletteExtendedVisible(false)

                                    },
                                    onColorPaletteClick = {
                                        viewModel.updatePaletteExtendedVisible(!paletteExtendedVisible)
                                        if (!paletteVisible) {
                                            viewModel.updatePaletteExtendedVisible(false)
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
                            selectedColor = selectedColor,
                            selectedInstruments = viewModel.selectedInstrument,
                            onUpdatePaths = {
                                viewModel.updateCurrentFrame(
                                    currentFrame.copy(paths = it)
                                )
                            },
                            pathsVM = currentFrame.paths,
                            undonePathsVM = currentFrame.undonePaths,
                        )
                    }
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