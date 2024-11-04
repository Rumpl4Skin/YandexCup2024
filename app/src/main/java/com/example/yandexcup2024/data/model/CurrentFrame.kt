package com.example.yandexcup2024.data.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Path

data class CurrentFrame(
 val currentPath: Path = Path(),
 val paths: SnapshotStateList<PathData> = mutableStateListOf(),
 val undonePaths: SnapshotStateList<PathData> = mutableStateListOf(),
) {
}