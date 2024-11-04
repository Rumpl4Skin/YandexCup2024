package com.example.yandexcup2024.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yandexcup2024.R

@Composable
fun Headers(
    modifier: Modifier = Modifier,
    undoActive: Boolean = false,
    redoActive: Boolean = false,
    playActive: Boolean = false,
    pauseActive: Boolean = false,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onClearCurrentFrame: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = { /* Undo action */onUndo.invoke() }, enabled = undoActive) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .weight(1f),
                painter = painterResource(id = if (undoActive) R.drawable.undo_active else R.drawable.undo_unactive),
                contentDescription = null
            )
        }
        IconButton(onClick = { /* Redo action */onRedo.invoke() }, enabled = redoActive) {
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
            IconButton(onClick = { /* Bin action */ onClearCurrentFrame.invoke() }) {
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