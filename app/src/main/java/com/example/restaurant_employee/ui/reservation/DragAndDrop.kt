package com.example.restaurant_employee.ui.reservation

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

@Composable
fun <T> DragTarget(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    widthTarget: Dp,
    heightTarget: Dp,
    tableWidthInTiles: Int = 1,
    tableHeightInTiles: Int = 1,
    content: @Composable ((text: String) -> Unit)
){
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    var currentOriginPos by remember { mutableStateOf(Offset.Zero) }
    var isDraggingLocal by remember { mutableStateOf(false) }
    val currentState = LocalDragTargetInfo.current

    Box(
        modifier = modifier
            .offset{ IntOffset(currentOriginPos.x.roundToInt(), currentOriginPos.y.roundToInt()) }
            .onGloballyPositioned {
                currentPosition = it.localToWindow(Offset.Zero)
            }
            .pointerInput(Unit){
                detectDragGestures (
                    onDragStart = {
                        currentState.dataToDrop = dataToDrop
                        currentState.isDragging = true
                        isDraggingLocal = true
                        currentState.dragPosition = currentOriginPos
                        currentState.draggableComposable = content
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
                    },
                    onDragEnd = {
                        // Round table by cells
                        currentState.dragOffset = Offset(
                            ((currentState.dragOffset.x / (widthTarget.roundToPx().toFloat() / tableWidthInTiles)).roundToInt() * widthTarget.roundToPx() / tableWidthInTiles).toFloat(),
                            ((currentState.dragOffset.y / (heightTarget.roundToPx().toFloat() / tableHeightInTiles)).roundToInt() * heightTarget.roundToPx() / tableHeightInTiles).toFloat()
                        )

                        currentOriginPos += currentState.dragOffset
                        currentState.dragOffset = Offset.Zero
                        currentState.isDragging = false
                        isDraggingLocal = false
                    },
                    onDragCancel = {

                    },
                )
            }
    ){
        Log.i("recompositionOfContent", currentPosition.x.roundToInt().toString() + " " + currentPosition.y.roundToInt())
        Log.i("recompositionOfContentOrigin", currentOriginPos.x.roundToInt().toString() + " " + currentOriginPos.y.roundToInt())
        if(!isDraggingLocal) {
            content(currentOriginPos.x.toString() + " " + currentOriginPos.y.toString())
        }
    }
}

internal class DragTargetInfo{
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var draggableComposable by mutableStateOf<@Composable ((text: String) -> Unit)?>(null)
    var dataToDrop by mutableStateOf<Any?>(null)
}