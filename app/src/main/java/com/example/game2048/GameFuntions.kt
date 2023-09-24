package com.example.game2048

import androidx.compose.runtime.MutableState

fun move(
    swipeDirection: SwipeDirection,
    array: List<List<MutableState<Int>>>,
    boardSide: Int
) {
    when (swipeDirection) {
        SwipeDirection.Right -> moveRight(array, boardSide)
        SwipeDirection.Left -> moveLeft(array, boardSide)
        SwipeDirection.Up -> moveUp(array, boardSide)
        SwipeDirection.Down -> moveDown(array, boardSide)
    }
}

fun moveRight(
    array: List<List<MutableState<Int>>>,
    boardSize: Int,
) {
    for (row in 0 until boardSize) {
        for (column in boardSize - 2 downTo 0) {  // Start from the second-to-last column
            if (array[column][row].value == 0) {
                // Move non-zero values to the right
                for (index in column until boardSize - 1) {
                    array[index][row].value = array[index + 1][row].value
                }
                array[boardSize - 1][row].value = 0  // Set the rightmost element to 0
            }
        }
    }
}


fun moveLeft(array: List<List<MutableState<Int>>>, boardSide: Int) {
    for (row in 3 downTo 1) {
        for (column in 0 until 4) {
            if (array[column][row].value != 0 && array[column][row - 1].value == 0) {
                array[column][row - 1].value = array[column][row].value
                array[column][row].value = 0
            }
        }
    }
    for (row in 0 until 3) {
        for (column in 0 until 4) {
            if (array[column][row].value == array[column][row + 1].value) {
                array[column][row].value *= 2
                array[column][row + 1].value = 0
            }
        }
    }
    for (row in 3 downTo 1) {
        for (column in 0 until 4) {
            if (array[column][row].value != 0 && array[column][row - 1].value == 0) {
                array[column][row - 1].value = array[column][row].value
                array[column][row].value = 0
            }
        }
    }
}

fun moveUp(array: List<List<MutableState<Int>>>, boardSide: Int) {

}

fun moveDown(array: List<List<MutableState<Int>>>, boardSide: Int) {

}