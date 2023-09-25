package com.example.game2048.ui

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game2048.boxColors
import com.example.game2048.generateNextNumber
import com.example.game2048.getSwipeModifier
import com.example.game2048.move
import com.example.game2048.textSize
import kotlinx.coroutines.delay
import java.util.Random

@Composable
fun GameBoard(

) {
    val gameStarted = remember { mutableStateOf(false) }
    val boxSize = 100
    val gridSize = 4
    val array = getArray(gridSize, gameStarted)
    val combinedCellList = remember { mutableListOf<Pair<Int, Int>>() }
    Column(
        modifier = getSwipeModifier {
            move(it, array, gridSize) {
                Log.d("Pairs", it.toString())
                combinedCellList.addAll(it)
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
                        Tiles(
                            array[i][j].value,
                            boxSize,
                            shouldAnimate = Pair(i, j) in combinedCellList,
                            combinedCellList
                        )
                    }
            }
        }
    }
}

@Composable
fun Tiles(
    value: Int,
    size: Int,
    shouldAnimate: Boolean,
    combinedCellList: MutableList<Pair<Int, Int>>
) {
    val boxSize = remember { mutableStateOf(size) }
    val alpha by animateFloatAsState(
        targetValue = if (shouldAnimate) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )
    val modifier = if (shouldAnimate) {
        Modifier
            .size((boxSize.value - 20).dp)
            .background(boxColors(value))
            .alpha(alpha)
    } else {
        Modifier
            .size((boxSize.value - 20).dp)
            .background(boxColors(value))
    }

    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        if (value != 0) {
            Text(
                text = value.toString(),
                color = Color.Black,
                fontSize = textSize(value,10).sp
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





