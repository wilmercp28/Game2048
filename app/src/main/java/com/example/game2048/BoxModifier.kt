package com.example.game2048

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput



@Composable
fun getSwipeModifier(onSwipe: (SwipeDirection) -> Unit): Modifier {
    var direction by remember { mutableStateOf(-1) }
    return Modifier
        .pointerInput(Unit) {
            detectDragGestures(
                onDrag = { change, dragAmount ->
                    change.consume()

                    val (x, y) = dragAmount
                    if (kotlin.math.abs(x) > kotlin.math.abs(y)) {
                        when {
                            x > 0 -> {
                                //right
                                direction = 0
                            }

                            x < 0 -> {
                                // left
                                direction = 1
                            }
                        }
                    } else {
                        when {
                            y > 0 -> {
                                // down
                                direction = 2
                            }

                            y < 0 -> {
                                // up
                                direction = 3
                            }
                        }
                    }

                },
                onDragEnd = {
                    when (direction) {
                        0 -> {
                           onSwipe(SwipeDirection.Right)
                        }

                        1 -> {
                            onSwipe( SwipeDirection.Left)
                        }

                        2 -> {
                            onSwipe (SwipeDirection.Down)
                        }

                        3 -> {
                            onSwipe (SwipeDirection.Up)
                        }
                    }
                }
            )
        }
        .fillMaxSize()
}


enum class SwipeDirection {
    Left, Right, Up, Down
}
