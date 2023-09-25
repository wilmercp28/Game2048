package com.example.game2048

import androidx.compose.runtime.MutableState

fun move(
    swipeDirection: SwipeDirection,
    array: List<List<MutableState<Int>>>,
    boardSide: Int,
    onAnimation: (List<CombinedCell>) -> Unit
) {
    when (swipeDirection) {
        SwipeDirection.Right -> moveRight(array, boardSide) {
            onAnimation(it)
        }

        SwipeDirection.Left -> moveLeft(array, boardSide) {

        }

        SwipeDirection.Up -> moveUp(array, boardSide) {

        }

        SwipeDirection.Down -> moveDown(array, boardSide) {

        }
    }
}

fun moveRight(
    array: List<List<MutableState<Int>>>,
    boardSize: Int,
    onAnimation: (List<CombinedCell>) -> Unit
) {
    val combinedCells = mutableListOf<CombinedCell>()
    for (row in array.indices) {
        val temp = mutableListOf<Int>()
        for (column in array[row].indices) {
            val value = array[row][column].value
            if (value != 0) {
                temp.add(value)
            }
        }
        while (temp.size < boardSize) {
            temp.add(0, 0)
        }
        for (i in boardSize - 1 downTo 1) {
            if (temp[i] == temp[i - 1] && temp[i] != 0) {
                temp[i] *= 2
                temp[i - 1] = 0
                combinedCells.add(CombinedCell(row, i))
            }
        }
        val result = mutableListOf<Int>()
        for (value in temp) {
            if (value != 0) {
                result.add(value)
            }
        }
        while (result.size < boardSize) {
            result.add(0, 0)
        }

        for (column in array[row].indices) {
            array[row][column].value = result[column]
        }
    }
    onAnimation(combinedCells)
}

fun moveLeft(
    array: List<List<MutableState<Int>>>,
    boardSize: Int,
    onAnimation: (CombinedCell) -> Unit
) {
    for (row in array.indices) {
        val temp = mutableListOf<Int>()
        for (column in array[row].indices) {
            val value = array[row][column].value
            if (value != 0) {
                temp.add(value)
            }
        }
        while (temp.size < boardSize) {
            temp.add(0)
        }
        for (i in 0 until boardSize - 1) {
            if (temp[i] == temp[i + 1] && temp[i] != 0) {
                temp[i] *= 2
                temp[i + 1] = 0
                onAnimation(CombinedCell(row, i))
            }
        }
        val result = mutableListOf<Int>()
        for (value in temp) {
            if (value != 0) {
                result.add(value)
            }
        }
        while (result.size < boardSize) {
            result.add(0)
        }

        for (column in array[row].indices) {
            array[row][column].value = result[column]
        }
    }
}

fun moveUp(
    array: List<List<MutableState<Int>>>,
    boardSize: Int,
    onAnimation: (CombinedCell) -> Unit
) {
    for (column in 0 until boardSize) {
        val temp = mutableListOf<Int>()
        for (row in 0 until boardSize) {
            val value = array[row][column].value
            if (value != 0) {
                temp.add(value)
            }
        }
        for (i in 0 until temp.size - 1) {
            if (temp[i] == temp[i + 1] && temp[i] != 0) {
                temp[i] *= 2
                temp[i + 1] = 0
                onAnimation(CombinedCell(column, i))
            }
        }
        temp.removeAll { it == 0 }
        while (temp.size < boardSize) {
            temp.add(0)
        }

        for (row in 0 until boardSize) {
            array[row][column].value = temp[row]
        }
    }
}

fun moveDown(
    array: List<List<MutableState<Int>>>,
    boardSize: Int,
    onAnimation: (CombinedCell) -> Unit
) {
    for (column in 0 until boardSize) {
        val temp = mutableListOf<Int>()
        for (row in (boardSize - 1) downTo 0) {
            val value = array[row][column].value
            if (value != 0) {
                temp.add(value)
            }
        }
        for (i in 0 until temp.size - 1) {
            if (temp[i] == temp[i + 1] && temp[i] != 0) {
                temp[i] *= 2
                temp[i + 1] = 0
                onAnimation(CombinedCell(column, i))
            }
        }
        temp.removeAll { it == 0 }
        while (temp.size < boardSize) {
            temp.add(0)
        }
        for (row in (boardSize - 1) downTo 0) {
            array[row][column].value = temp[boardSize - 1 - row]
        }
    }
}

data class CombinedCell(
    val row: Int,
    val column: Int,
)
data class TilePosition(
    val initialRow: Int,
    val initialColumn: Int,
    val finalRow: Int,
    val finalColumn: Int
)