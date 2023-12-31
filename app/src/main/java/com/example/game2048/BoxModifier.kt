package com.example.game2048

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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
    val minimumDrag = 30
    val damping = 0.9f
    return Modifier
        .pointerInput(Unit) {
            detectDragGestures(
                onDrag = { change, dragAmount ->
                    change.consume()
                    val (x, y) = dragAmount * damping
                    if (kotlin.math.abs(x) > kotlin.math.abs(y)) {
                        when {
                            x > minimumDrag -> {
                                direction = 0
                            }
                            x < -minimumDrag -> {
                                direction = 1
                            }
                        }
                    } else {
                        when {
                            y > minimumDrag -> {
                                // down
                                direction = 2
                            }

                            y < -minimumDrag -> {
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
        .background(MaterialTheme.colorScheme.primary)
}


enum class SwipeDirection {
    Left, Right, Up, Down
}
