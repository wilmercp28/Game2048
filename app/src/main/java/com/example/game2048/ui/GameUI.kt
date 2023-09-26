package com.example.game2048.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.game2048.LostScreen
import com.example.game2048.SwipeDirection
import com.example.game2048.WinScreen
import com.example.game2048.boxColors
import com.example.game2048.checkWinner
import com.example.game2048.cleanArray
import com.example.game2048.generateNextNumber
import com.example.game2048.getSwipeModifier
import com.example.game2048.move
import com.example.game2048.textSize
import kotlinx.coroutines.delay
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameBoard(
    infinityMode: MutableState<Boolean>,
    gridSize: MutableState<Int>,
    startGame: MutableState<Boolean>,
    bestScore: MutableState<Int>,
    dataStore: DataStore<Preferences>,
    startGame1: MutableState<Boolean>
) {
    val gameStarted = remember { mutableStateOf(false) }
    val startNumberOfTitles = 2
    var array = List(gridSize.value) { List(gridSize.value) { remember { mutableStateOf(0) } } }
    val combinedCellList = remember { mutableListOf<Pair<Int, Int>>() }
    val direction = remember { mutableStateOf<SwipeDirection?>(null) }
    val score = remember { mutableStateOf(0) }
    val gameLost = remember { mutableStateOf(false) }
    val hasWon = remember { mutableStateOf(false) }
    if (!gameStarted.value){
        generateNextNumber(array,gameLost)
        generateNextNumber(array,gameLost)
        gameStarted.value = true
    }
    AnimatedVisibility(
        hasWon.value,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        WinScreen(
            score,
            hasWon,
            array,
            startNumberOfTitles,
            gridSize.value,
            gameStarted,
            gameLost,
            bestScore,
            dataStore,
            startGame
        )
    }
    AnimatedVisibility(
        gameLost.value,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        LostScreen(
            score,
            gameStarted,
            gameLost,
            gridSize.value,
            startNumberOfTitles,
            array,
            bestScore,
            dataStore,
            startGame
        )
    }
    AnimatedVisibility(
        !gameLost.value,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        BoxWithConstraints {
            val screenWidth = constraints.maxWidth
            val boxSize = screenWidth / gridSize.value * 0.3f
            Column(
                modifier = getSwipeModifier { swipeDirection ->
                    direction.value = swipeDirection
                    move(swipeDirection, array, gridSize.value, score) {
                        combinedCellList.addAll(it)
                        if (infinityMode.value) {
                            checkWinner(array, hasWon)
                        }
                    }
                    generateNextNumber(array, gameLost)
                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedScoreBoard(score)
                Spacer(modifier = Modifier.size(20.dp))
                for (i in 0 until gridSize.value) {
                    Row(
                        modifier = Modifier
                    ) {
                        for (j in 0 until gridSize.value) {
                            Box(
                                modifier = Modifier
                                    .size(boxSize.dp),
                                contentAlignment = Alignment.Center
                            )
                            {
                                Tiles(
                                    array[i][j].value,
                                    200,
                                    Pair(i, j),
                                    combinedCellList,
                                    boxSize.toInt()
                                )
                            }
                        }

                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                ElevatedButton(onClick = {
                    cleanArray(startNumberOfTitles, array, gridSize.value, gameLost)
                    score.value = 0
                    gameStarted.value = true
                    gameLost.value = false
                }) {
                    Text(
                        text = "Restart",
                        fontSize = 20.sp,

                        )

                }
                Spacer(modifier = Modifier.size(20.dp))
                ElevatedButton(onClick = {
                    cleanArray(startNumberOfTitles, array, gridSize.value, gameLost)
                    score.value = 0
                    gameStarted.value = true
                    gameLost.value = false
                    startGame.value = false
                }) {
                    Text(
                        text = "Main Menu",
                        fontSize = 20.sp,

                        )

                }
            }

        }
    }
}


@Composable
fun Tiles(
    value: Int,
    animationDelay: Int,
    animationPair: Pair<Int, Int>,
    combinedCellList: MutableList<Pair<Int, Int>>,
    singleBoxSize: Int,

    ) {
    val shouldAnimate = animationPair in combinedCellList
    val boxSize = remember { mutableStateOf(singleBoxSize) }
    if (shouldAnimate) {
        LaunchedEffect(Unit) {
            boxSize.value = singleBoxSize / 2
            delay((animationDelay / 2).toDuration(DurationUnit.MILLISECONDS))
            boxSize.value = singleBoxSize
            combinedCellList.remove(animationPair)
        }
    }
    Box(
        modifier = Modifier
            .animateContentSize()
            .size(width = (boxSize.value / 1.1).dp, height = (boxSize.value / 1.1).dp)
            .background(boxColors(value)),
        contentAlignment = Alignment.Center
    ) {
        if (value != 0) {
            Text(
                text = value.toString(),
                color = Color.Black,
                fontSize = textSize(value, 8).sp
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedScoreBoard(score: MutableState<Int>) {
    val minScoreTextSize = 16
    val maxScoreTextSize = 40
    val textScoreSizeRange = maxScoreTextSize - minScoreTextSize
    val normalizedScore = (score.value.toFloat() / maxScoreTextSize.toFloat()).coerceIn(0f, 1f)
    val fontSize = (minScoreTextSize + textScoreSizeRange * normalizedScore).sp

    Box(
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = score.value,
            transitionSpec = {
                slideInVertically { height -> height } + fadeIn() with
                        slideOutVertically { height -> -height } + fadeOut()
            }
        ) { newScore ->
            Text(
                text = newScore.toString(),
                fontSize = fontSize
            )
        }
    }
}








