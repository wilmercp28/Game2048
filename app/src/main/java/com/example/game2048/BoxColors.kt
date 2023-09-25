package com.example.game2048

import androidx.compose.ui.graphics.Color

fun boxColors(
    value: Int
): Color {
    return when (value) {
        2 -> Color(0xFFEEE4DA) // Light Yellow
        4 -> Color(0xFFEDE0C8) // Light Orange
        8 -> Color(0xFFF2B179) // Orange
        16 -> Color(0xFFF59563) // Dark Orange
        32 -> Color(0xFFF67C5F) // Even Darker Orange
        64 -> Color(0xFFF65E3B) // Dark Red
        128 -> Color(0xFFEDCF72) // Light Yellow
        256 -> Color(0xFFEDCC61) // Light Yellow
        else -> Color.Gray // Default color for unknown values
    }
}
fun textSize(
    value: Int,
    defaultTextSize: Int
): Int{

        return when (value) {
            2 -> defaultTextSize + 2
            4 -> defaultTextSize + 4
            8 -> defaultTextSize + 6
            16 -> defaultTextSize + 8
            32 -> defaultTextSize + 10
            64 -> defaultTextSize + 12
            128 -> defaultTextSize + 14
            256 -> defaultTextSize + 16
            else -> 0
        }
    }
