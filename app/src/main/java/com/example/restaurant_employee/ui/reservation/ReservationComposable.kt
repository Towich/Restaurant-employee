package com.example.restaurant_employee.ui.reservation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun ReservationScreen(

) {
    DraggableScreen(modifier = Modifier) {
        DragTarget(
            dataToDrop = "1"
        ) {
            Box(
                Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .size(100.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DraggableScreen(
    modifier: Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val state = remember {
        DragTargetInfo()
    }

    CompositionLocalProvider(
        LocalDragTargetInfo provides state
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            content()
            if (state.isDragging) {
                var targetSize by remember {
                    mutableStateOf(IntSize.Zero)
                }

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val offset = (state.dragPosition + state.dragOffset)
                            Log.i("graphicsLayer", state.dragPosition.toString() + state.dragOffset.toString())
                            scaleX = 1.3f
                            scaleY = 1.3f
                            alpha = if (targetSize == IntSize.Zero) 0f else 0.9f
                            translationX = offset.x
                            translationY = offset.y
                        }
                        .onGloballyPositioned {
                            targetSize = it.size
                        }
                ) {
                    state.draggableComposable?.invoke((state.dragPosition + state.dragOffset).toString() )
                }
            }
        }
    }
}


//@Composable
//private fun DraggableTable(
//
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        var offsetX by remember { mutableStateOf(0f) }
//        var offsetY by remember { mutableStateOf(0f) }
//        var size by remember { mutableStateOf(50.dp) }
//
//        Box(
//            Modifier
//                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
//                .background(MaterialTheme.colorScheme.primary)
//                .size(100.dp)
//                .pointerInput(Unit) {
//                    detectDragGesturesAfterLongPress(
//                        onDragStart = {
//                            size = 100.dp
//                        },
//                        onDrag = { change, dragAmount ->
//                            change.consume()
//                            offsetX += dragAmount.x
//                            offsetY += dragAmount.y
//                        },
//                        onDragEnd = {
//                            size = 50.dp
//                        }
//                    )
//
//                }
//
//        )
//    }
//}