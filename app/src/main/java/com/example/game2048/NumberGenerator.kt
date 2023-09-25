package com.example.game2048

import androidx.compose.runtime.MutableState
import java.util.Random

fun generateNextNumber(
    array: List<List<MutableState<Int>>>
) {
    val random = Random()
    val emptyCells = mutableListOf<Pair<Int, Int>>()
    for (row in array.indices) {
        for (col in array[row].indices) {
            if (array[row][col].value == 0) {
                emptyCells.add(Pair(row, col))
            }
        }
    }

    if (emptyCells.isNotEmpty()) {
        val randomIndex = random.nextInt(emptyCells.size)
        val (row, col) = emptyCells[randomIndex]
        array[row][col].value = 2
    }


}