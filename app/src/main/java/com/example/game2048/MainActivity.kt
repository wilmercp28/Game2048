package com.example.game2048

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.game2048.ui.GameBoard
import com.example.game2048.ui.theme.Game2048Theme
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Score")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Game2048Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val gridSize = remember { mutableStateOf(4) }
                    val infinityMode = remember { mutableStateOf(false) }
                    val bestScore = remember { mutableStateOf(0) }
                    val startGame = remember { mutableStateOf(false) }
                    AnimatedVisibility(startGame.value) {
                        GameBoard(infinityMode,gridSize,startGame,bestScore,dataStore,startGame)
                    }
                    AnimatedVisibility(!startGame.value) {
                        MainMenu(dataStore, infinityMode, bestScore, startGame, gridSize)
                    }
                }
            }
        }
    }
}
