package com.example.game2048

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

fun getScore(array: List<List<MutableState<Int>>>): Int {
    var score = 0
    for (row in array) {
        for (cell in row) {
            score += cell.value
        }
    }

    return score
}