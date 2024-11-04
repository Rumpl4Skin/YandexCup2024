package com.example.yandexcup2024.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.yandexcup2024.data.model.CurrentFrame
import com.example.yandexcup2024.data.model.PathData
import com.example.yandexcup2024.data.model.SelectableInstruments
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityViewModel : ViewModel() {
    //ui
    private val _paletteVisible = MutableStateFlow(false)
    val paletteVisible = _paletteVisible.asStateFlow()
    private val _paletteExtendedVisible = MutableStateFlow(false)
    val paletteExtendedVisible = _paletteExtendedVisible.asStateFlow()
    private val _selectedInstrument = MutableStateFlow(SelectableInstruments.None)
    val selectedInstrument = _selectedInstrument.asStateFlow()

    //logic
    private val _selectedColor = MutableStateFlow(PathData(color = Color.Blue))
    val selectedColor = _selectedColor.asStateFlow()

    private val _currentFrame = MutableStateFlow(CurrentFrame())
    val currentFrame = _currentFrame.asStateFlow()

    private val _frames = MutableStateFlow(mutableListOf(_currentFrame.value))
    val frames: StateFlow<MutableList<CurrentFrame>> = _frames

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()


    fun updatePaletteVisible(visibility: Boolean = false) {
        _paletteVisible.update { visibility }
    }

    fun updatePaletteExtendedVisible(visibility: Boolean = false) {
        _paletteExtendedVisible.update { visibility }
    }

    fun updateSelectedColor(newPathData: PathData) {
        _selectedColor.update { _selectedColor.value.copy(color = newPathData.color) }
    }

    fun updateSelectedInstrument(newInstrument: SelectableInstruments = SelectableInstruments.None) {
        _selectedInstrument.update { newInstrument }
    }


    fun updateCurrentFrame(newCurrentFrame: CurrentFrame) {
        newCurrentFrame.paths.forEachIndexed() { index, it ->
            if (it.path.isEmpty)
                newCurrentFrame.paths.removeAt(index)
        }
        newCurrentFrame.undonePaths.forEachIndexed() { index, it ->
            if (it.path.isEmpty)
                newCurrentFrame.undonePaths.removeAt(index)
        }
        _currentFrame.update { newCurrentFrame }
    }

    fun undo() { //назад
        if (_currentFrame.value.paths.isNotEmpty()) {
            val lastPath = _currentFrame.value.paths.last()
            _currentFrame.value.undonePaths.add(lastPath)
            _currentFrame.value.paths.remove(lastPath)
        }
    }

    fun redo() { // вперед
        if (_currentFrame.value.undonePaths.isNotEmpty()) {
            val lastUndonePath = _currentFrame.value.undonePaths.last()
            _currentFrame.value.paths.add(lastUndonePath)
            _currentFrame.value.undonePaths.remove(lastUndonePath)
        }
    }

    fun udpateFrames(frames: MutableList<CurrentFrame>) {
        _frames.update { frames }
    }

    fun removeCurrentFrame(frame: CurrentFrame) {

        _frames.value.remove(frame)
        _frames.update {
            _frames.value
        }
        updateCurrentFrame(_frames.value.last())
        _currentIndex.update { _frames.value.lastIndex-1 }
    }

    fun addFrame(frame: CurrentFrame) {
        _frames.update {
            _frames.value.apply {
                it.add(frame)
            }
        }
    }

    fun changeIndex(i: Int) {
        _currentIndex.update { i }
    }

}