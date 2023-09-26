package com.example.game2048

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LostScreen(
    score: MutableState<Int>,
    gameStarted: MutableState<Boolean>,
    gameLost: MutableState<Boolean>,
    gridSize: Int,
    newEmptyArray: (List<List<MutableState<Int>>>) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(text = "You Lost!", fontSize = 70.sp)
        Text(text = "Final score ${score.value}", fontSize = 40.sp)
        ElevatedButton(onClick = {
            gameStarted.value = true
            gameLost.value = false

        }) {
            Text(text = "Restart", fontSize = 50.sp)
        }
        ElevatedButton(onClick = {

        }) {
            Text(text = "Main Menu", fontSize = 30.sp)
        }


    }
    if (gameLost.value){
        val array = List(gridSize) {
            List(gridSize) {
                remember { mutableStateOf(0) }
            }
        }
        newEmptyArray(array)
    }

}