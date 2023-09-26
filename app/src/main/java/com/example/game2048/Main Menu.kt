package com.example.game2048

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.game2048.ui.GameBoard

@Composable
fun MainMenu(
    dataStore: DataStore<Preferences>,
    infinityMode: MutableState<Boolean>,
    bestScore: MutableState<Int>,
    startGame: MutableState<Boolean>,
    gridSize: MutableState<Int>
) {
    val context = LocalContext.current
    LaunchedEffect(Unit){
        bestScore.value = loadData(context,dataStore)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AnimatedVisibility(!startGame.value) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "2048", fontSize = 100.sp)
                Text(text = "Best Score: ${bestScore.value}", fontSize = 40.sp)
                Spacer(modifier = Modifier.size(100.dp))
                ElevatedButton(onClick = {
                    startGame.value = true
                }) {
                    Text(text = "Start Game", fontSize = 40.sp)
                }
            }
            AnimatedVisibility(startGame.value) {
                GameBoard(infinityMode, gridSize, startGame, bestScore, dataStore, startGame)
            }
        }
    }
}