package com.example.game2048.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
import com.example.game2048.LostScreen
import com.example.game2048.SwipeDirection
import com.example.game2048.boxColors
import com.example.game2048.generateNextNumber
import com.example.game2048.getScore
import com.example.game2048.getSwipeModifier
import com.example.game2048.move
import com.example.game2048.textSize
import kotlinx.coroutines.delay
import java.util.Random
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameBoard() {
    val gameStarted = remember { mutableStateOf(false) }
    val gridSize = 4
    var array = getArray(gridSize, gameStarted)
    val combinedCellList = remember { mutableListOf<Pair<Int, Int>>() }
    val direction = remember { mutableStateOf<SwipeDirection?>(null) }
    val score = remember { mutableStateOf(0) }
    score.value = getScore(array)
    val minScoreTextSize = 16
    val maxScoreTextSize = 40
    val textScoreSizeRange = maxScoreTextSize - minScoreTextSize
    val normalizedScore = (score.value.toFloat() / maxScoreTextSize.toFloat()).coerceIn(0f, 1f)
    val fontSize = (minScoreTextSize + textScoreSizeRange * normalizedScore).sp
    val gameLost = remember { mutableStateOf(true) }
    AnimatedVisibility(
        gameLost.value,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        LostScreen(score, gameStarted, gameLost,gridSize){
            array = it
        }
    }
    AnimatedVisibility(
        !gameLost.value,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        BoxWithConstraints {
            val screenWidth = constraints.maxWidth
            val boxSize = screenWidth / gridSize * 0.3f
            Column(
                modifier = getSwipeModifier { swipeDirection ->
                    direction.value = swipeDirection
                    move(swipeDirection, array, gridSize) {
                        combinedCellList.addAll(it)
                    }
                    generateNextNumber(array, gameLost)
                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = score.value.toString(),
                    fontSize = fontSize
                )
                for (i in 0 until gridSize) {
                    Row(
                        modifier = Modifier
                    ) {
                        for (j in 0 until gridSize) {
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
                fontSize = textSize(value, 10).sp
            )
        }
    }
}


@Composable
fun getArray(gridSize: Int, gameStarted: MutableState<Boolean>): List<List<MutableState<Int>>> {
    val array = List(gridSize) {
        List(gridSize) {
            remember { mutableStateOf(0) }
        }
    }
    if (!gameStarted.value) {
        val random = Random()
        val indices = mutableSetOf<Pair<Int, Int>>()

        while (indices.size < 2) {
            val randomRow = random.nextInt(gridSize)
            val randomCol = random.nextInt(gridSize)
            val indexPair = Pair(randomRow, randomCol)

            if (indexPair !in indices) {
                val randomNumber = 2

                array[randomRow][randomCol].value = randomNumber
                indices.add(indexPair)
            }
        }
        gameStarted.value = true
    }

    return array
}




