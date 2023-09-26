package com.example.game2048

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
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

@Composable
fun WinScreen(
    score: MutableState<Int>,
    hasWon: MutableState<Boolean>,
    array: List<List<MutableState<Int>>>,
    startNumberOfTitles: Int,
    gridSize: Int,
    gameStarted: MutableState<Boolean>,
    gameLost: MutableState<Boolean>,
    bestScore: MutableState<Int>,
    dataStore: DataStore<Preferences>,
    startGame: MutableState<Boolean>
) {
    val context = LocalContext.current
    if (score.value > bestScore.value){
        LaunchedEffect(Unit){
            saveData(score.value,context,dataStore)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(text = "You Won!", fontSize = 70.sp)
        Text(text = "Final score ${score.value}", fontSize = 40.sp)
        ElevatedButton(onClick = {
            cleanArray(startNumberOfTitles, array, gridSize, gameLost)
            score.value = 0
            gameStarted.value = true
            gameLost.value = false
            hasWon.value = false
        }) {
            Text(text = "Restart", fontSize = 50.sp)
        }
        ElevatedButton(onClick = {
            startGame.value = false

        }) {
            Text(text = "Main Menu", fontSize = 30.sp)
        }


    }
}


