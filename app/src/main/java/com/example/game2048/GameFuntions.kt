package com.example.game2048

import androidx.compose.runtime.MutableState

fun move(
    swipeDirection: SwipeDirection,
    array: List<List<MutableState<Int>>>,
    boardSide: Int,
    onCombine: (List<Pair<Int, Int>>) -> Unit
) {
    when (swipeDirection) {
        SwipeDirection.Right -> moveRight(array, boardSide) {
            onCombine(it)
        }

        SwipeDirection.Left -> moveLeft(array, boardSide) {
            onCombine(it)
        }

        SwipeDirection.Up -> moveUp(array, boardSide) {
            onCombine(it)
        }

        SwipeDirection.Down -> moveDown(array, boardSide) {
            onCombine(it)
        }
    }
}

fun moveRight(
    array: List<List<MutableState<Int>>>,
    boardSize: Int,
    onCombine: (List<Pair<Int, Int>>) -> Unit
) {
    val combinedIndices = mutableListOf<Pair<Int, Int>>()

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
                combinedIndices.add(Pair(row, i))
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
    if (combinedIndices.isNotEmpty()) {
        onCombine(combinedIndices)
    }
}

fun moveLeft(
    array: List<List<MutableState<Int>>>,
    boardSize: Int,
    onCombine: (List<Pair<Int, Int>>) -> Unit
) {
    val combinedIndices = mutableListOf<Pair<Int, Int>>()
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
                combinedIndices.add(Pair(row, i))
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
    if (combinedIndices.isNotEmpty()) {
        onCombine(combinedIndices)
    }
}

fun moveUp(
    array: List<List<MutableState<Int>>>,
    boardSize: Int,
    onCombine: (List<Pair<Int, Int>>) -> Unit
) {
    val combinedIndices = mutableListOf<Pair<Int, Int>>()
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
                combinedIndices.add(Pair(i, column))
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
    if (combinedIndices.isNotEmpty()) {
        onCombine(combinedIndices)
    }
}

fun moveDown(
    array: List<List<MutableState<Int>>>,
    boardSize: Int,
    onCombine: (List<Pair<Int, Int>>) -> Unit
) {
    val combinedIndices = mutableListOf<Pair<Int, Int>>()
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
                combinedIndices.add(Pair(boardSize - 1 - i, column))
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
    if (combinedIndices.isNotEmpty()) {
        onCombine(combinedIndices)
    }
}
