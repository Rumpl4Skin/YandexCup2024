package com.example.yandexcup2024.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.yandexcup2024.data.model.SelectableInstruments
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityViewModel : ViewModel() {
    private val _paletteVisible = MutableStateFlow(false)
    val paletteVisible = _paletteVisible.asStateFlow()
    private val _paletteExtendedVisible = MutableStateFlow(false)
    val paletteExtendedVisible = _paletteExtendedVisible.asStateFlow()
    private val _selectedColor = MutableStateFlow(Color.Blue)
    val selectedColor = _selectedColor.asStateFlow()
    private val _selectedInstrument = MutableStateFlow(SelectableInstruments.None)
    val selectedInstrument = _selectedInstrument.asStateFlow()

    fun updatePaletteVisible(visibility: Boolean = false) {
        _paletteVisible.update { visibility }
    }

    fun updatePaletteExtendedVisible(visibility: Boolean = false) {
        _paletteExtendedVisible.update { visibility }
    }

    fun updateSelectedColor(newColor: Color = Color.Blue) {
        _selectedColor.update { newColor }
    }

    fun updateSelectedInstrument(newInstrument: SelectableInstruments = SelectableInstruments.None) {
        _selectedInstrument.update { newInstrument }
    }


}