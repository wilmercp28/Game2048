package com.example.game2048

import androidx.compose.runtime.MutableState

fun checkWinner(array: List<List<MutableState<Int>>>, hasWon: MutableState<Boolean>) {
    for (row in array.indices) {
        for (col in array[row].indices) {
            if (array[col][row].value == 2048) {
                hasWon.value = true
            }
        }
    }
}