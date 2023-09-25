package com.example.game2048.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.game2048.CombinedCell
import com.example.game2048.boxColors
import com.example.game2048.generateNextNumber
import com.example.game2048.getSwipeModifier
import com.example.game2048.move
import kotlinx.coroutines.delay
import java.util.Random

@Composable
fun GameBoard(

) {
    val gameStarted = remember { mutableStateOf(false) }
    val boxSize = 100
    val gridSize = 4
    val array = getArray(gridSize, gameStarted)
    val combinedCellList = remember { mutableListOf<Int>() }
    Column(
        modifier = getSwipeModifier {
            move(it, array, gridSize) { combinedCell ->
                combinedCellList += array[combinedCell.row][combinedCell.column].value
            }
            generateNextNumber(array)
        }
    ) {
        for (i in 0 until gridSize) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                for (j in 0 until gridSize)
                    Row(
                        modifier = Modifier
                            .size(boxSize.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val isCombined = array[i][j].value in combinedCellList
                        Tiles(array[i][j].value, boxSize, isCombined)
                    }
            }
        }
    }
    combinedCellList.clear()
}

@Composable
fun Tiles(
    value: Int,
    size: Int,
    shouldAnimate: Boolean
) {
    val boxSize = remember { mutableStateOf(size) }
    LaunchedEffect(value) {
        if (shouldAnimate) {
            boxSize.value = 300
            delay(100)
            boxSize.value = size
        }
    }
    Box(
        modifier = Modifier
            .size((boxSize.value - 20).dp)
            .animateContentSize()
            .background(boxColors(value)),
        contentAlignment = Alignment.Center
    ) {
        if (value != 0) {
            Text(
                text = value.toString(),
                color = Color.Black,
            )
        }
    }
}


@Composable
fun getArray(gridSize: Int, gameStarted: MutableState<Boolean>): List<List<MutableState<Int>>> {
    val array = List(gridSize) { row ->
        List(gridSize) { col ->
            remember { mutableStateOf(0) }
        }
    }
    if (!gameStarted.value) {
        val random = Random()
        val indices = mutableSetOf<Pair<Int, Int>>()

        while (indices.size < 5) {
            val randomRow = random.nextInt(gridSize)
            val randomCol = random.nextInt(gridSize)
            val indexPair = Pair(randomRow, randomCol)

            if (indexPair !in indices) {
                array[randomRow][randomCol].value = 2
                indices.add(indexPair)
            }
        }
        gameStarted.value = true
    }

    return array
}





