package com.example.game2048

import androidx.compose.ui.graphics.Color

fun boxColors(value: Int): Color {
    return when (value) {
        2 -> Color(0xFFEEE4DA) // Light Yellow
        4 -> Color(0xFFEDE0C8) // Light Orange
        8 -> Color(0xFFF2B179) // Orange
        16 -> Color(0xFFF59563) // Dark Orange
        32 -> Color(0xFFF67C5F) // Even Darker Orange
        64 -> Color(0xFFF65E3B) // Dark Red
        128 -> Color(0xFFEDCF72) // Light Yellow
        256 -> Color(0xFFEDCC61) // Light Yellow
        512 -> Color(0xFFEDC850) // Light Yellow
        1024 -> Color(0xFFEDC53F) // Light Yellow
        2048 -> Color(0xFFEDC22E) // Light Yellow
        4096 -> Color(0xFFEDC01D) // Light Yellow
        8192 -> Color(0xFFEDBF0C) // Light Yellow
        16384 -> Color(0xFFEDBE00) // Light Yellow
        32768 -> Color(0xFFEDBC00) // Light Yellow
        65536 -> Color(0xFFEDBB00) // Light Yellow (or more)
        131072 -> Color(0xFFEDBA00) // Light Yellow
        else -> Color.Gray
    }
}
fun textSize(value: Int, defaultTextSize: Int): Int {
    return when (value) {
        2 -> defaultTextSize + 2
        4 -> defaultTextSize + 4
        8 -> defaultTextSize + 6
        16 -> defaultTextSize + 8
        32 -> defaultTextSize + 10
        64 -> defaultTextSize + 12
        128 -> defaultTextSize + 14
        256 -> defaultTextSize + 16
        512 -> defaultTextSize + 18
        1024 -> defaultTextSize + 19
        2048 -> defaultTextSize + 20
        else -> defaultTextSize + 4
    }
}