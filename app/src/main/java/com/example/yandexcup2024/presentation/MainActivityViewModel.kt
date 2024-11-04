package com.example.yandexcup2024.presentation

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.asAndroidPath
import androidx.lifecycle.ViewModel
import com.example.yandexcup2024.data.model.CurrentFrame
import com.example.yandexcup2024.data.model.PathData
import com.example.yandexcup2024.data.model.SelectableInstruments
import kotlinx.coroutines.flow.MutableStateFlow
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
        newCurrentFrame.paths.forEachIndexed() { index, it->
            if (it.path.isEmpty)
                newCurrentFrame.paths.removeAt(index)
        }
        newCurrentFrame.undonePaths .forEachIndexed() { index, it->
            if (it.path.isEmpty)
                newCurrentFrame.undonePaths.removeAt(index)
        }
        _currentFrame.update { newCurrentFrame }
    }

    fun undo() { //назад
        if (_currentFrame.value.paths.isNotEmpty()) {
            var lastPath = _currentFrame.value.paths.last()
            while (lastPath.path.isEmpty){
                _currentFrame.value.paths.remove(lastPath)
                lastPath = _currentFrame.value.paths.last()
            }
            _currentFrame.value.undonePaths.add(lastPath)
            _currentFrame.value.paths.remove(lastPath)
        }
    }

    fun redo() { // вперед
        if (_currentFrame.value.undonePaths.isNotEmpty()) {
            var lastUndonePath = _currentFrame.value.undonePaths.last()
            while (lastUndonePath.path.isEmpty){
                _currentFrame.value.undonePaths.remove(lastUndonePath)
                lastUndonePath = _currentFrame.value.undonePaths.last()
            }
            _currentFrame.value.paths.add(lastUndonePath)
            _currentFrame.value.undonePaths.remove(lastUndonePath)
        }
    }



}