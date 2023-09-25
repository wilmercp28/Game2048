package com.example.game2048.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.game2048.CombinedCell
import com.example.game2048.boxColors
import com.example.game2048.generateNextNumber
import com.example.game2048.getSwipeModifier
import com.example.game2048.move
import java.util.Random

@Composable
fun GameBoard(
    onAnimation: (CombinedCell) -> Unit
) {
    val gameStarted = remember { mutableStateOf(false) }
    val boxSize = 100
    val gridSize = 4
    val array = getArray(gridSize, gameStarted)
    Column(
        modifier = getSwipeModifier() {
            move(it, array, gridSize)
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
                        Box(
                            modifier = Modifier
                                .size((boxSize - 20).dp)
                                .animateContentSize()
                                .background(boxColors(array[i][j].value)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (array[i][j].value != 0) {
                                Text(
                                    text = array[i][j].value.toString(),
                                    color = Color.Black
                                )
                            }
                        }
                    }
            }
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




