package com.example.game2048

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.game2048.ui.GameBoard
import kotlinx.coroutines.launch

@Composable
fun MainMenu(
    dataStore: DataStore<Preferences>,
    infinityMode: MutableState<Boolean>,
    bestScore: MutableState<Int>,
    startGame: MutableState<Boolean>,
    gridSize: MutableState<Int>
) {
    val scope = rememberCoroutineScope()
    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        bestScore.value = loadData(context, dataStore)
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
                Button(onClick = {
                    scope.launch {
                        saveData(0, context, dataStore)
                        bestScore.value = 0
                    }
                }) {
                    Text(text = "Reset Score")

                }
                Spacer(modifier = Modifier.size(100.dp))
                Text(text = "Infinity Mode")
                Switch(
                    checked = infinityMode.value, onCheckedChange = { infinityMode.value = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                    )
                )
                Text(text = "Grid Size")
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("4")
                        RadioButton(
                            selected = gridSize.value == 4, onClick = { gridSize.value = 4 },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Black,
                                unselectedColor = Color.White
                            )
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("6")
                        RadioButton(
                            selected = gridSize.value == 6, onClick = { gridSize.value = 6 },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Black,
                                unselectedColor = Color.White
                            )
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("8")
                        RadioButton(
                            selected = gridSize.value == 8, onClick = { gridSize.value = 8 },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Black,
                                unselectedColor = Color.White
                            )
                        )
                    }
                }
                ElevatedButton(onClick = {
                    startGame.value = true
                }) {
                    Text(text = "Start Game", fontSize = 40.sp)
                }
                Spacer(modifier = Modifier.size(20.dp))
                ElevatedButton(onClick = {
                    activity?.finish()
                }) {
                    Text(text = "Quit", fontSize = 30.sp)
                }
            }
            AnimatedVisibility(startGame.value) {
                GameBoard(infinityMode, gridSize, startGame, bestScore, dataStore, startGame)
            }
        }
    }
}