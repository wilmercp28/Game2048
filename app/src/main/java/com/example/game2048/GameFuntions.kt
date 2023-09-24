package com.example.game2048

import androidx.compose.runtime.MutableState

fun move(
    swipeDirection: SwipeDirection,
    array: List<List<MutableState<Int>>>
) {
    when (swipeDirection) {
        SwipeDirection.Right -> moveRight(array)
        SwipeDirection.Left -> moveLeft(array)
        SwipeDirection.Up -> moveUp(array)
        // Implement Down direction as needed
        SwipeDirection.Down -> moveDown(array)
    }
}

fun moveRight(
    array: List<List<MutableState<Int>>>
) {
    for (row in array) {
        for (i in row.indices.reversed()) {
            if (i > 0 && row[i].value == row[i - 1].value) {
                row[i].value *= 2
                row[i - 1].value = 0
            }
        }
        var nonZeroIndex = row.size - 1
        for (i in row.indices.reversed()) {
            if (row[i].value != 0) {
                row[nonZeroIndex--].value = row[i].value
            }
        }
        for (i in nonZeroIndex downTo 0) {
            row[i].value = 0
        }
    }
}
fun moveLeft(array: List<List<MutableState<Int>>>) {
    for (row in array) {
        // First, combine adjacent numbers from left to right
        for (i in 0 until row.size - 1) {
            if (row[i].value == row[i + 1].value) {
                row[i].value *= 2
                row[i + 1].value = 0
            }
        }
        var nonZeroIndex = 0
        for (i in row.indices) {
            if (row[i].value != 0) {
                row[nonZeroIndex++].value = row[i].value
            }
        }
        for (i in nonZeroIndex until row.size) {
            row[i].value = 0
        }
    }
}
fun moveUp(array: List<List<MutableState<Int>>>) {
    val numRows = array.size
    val numCols = array[0].size

    for (col in 0 until numCols) {
        for (row in 0 until numRows - 1) {
            if (array[row][col].value == array[row + 1][col].value) {
                array[row][col].value *= 2
                array[row + 1][col].value = 0
            }
        }
        var nonZeroIndex = 0
        for (row in 0 until numRows) {
            if (array[row][col].value != 0) {
                array[nonZeroIndex++][col].value = array[row][col].value
            }
        }
        for (row in nonZeroIndex until numRows) {
            array[row][col].value = 0
        }
    }
}
fun moveDown(array: List<List<MutableState<Int>>>) {
    val numRows = array.size
    val numCols = array[0].size

    for (col in 0 until numCols) {
        for (row in numRows - 1 downTo 1) {
            if (array[row][col].value == array[row - 1][col].value) {
                array[row][col].value *= 2
                array[row - 1][col].value = 0
            }
        }
        var nonZeroIndex = numRows - 1
        for (row in numRows - 1 downTo 0) {
            if (array[row][col].value != 0) {
                array[nonZeroIndex--][col].value = array[row][col].value
            }
        }
        for (row in nonZeroIndex downTo 0) {
            array[row][col].value = 0
        }
    }
}