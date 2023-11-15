package com.example.restaurant_employee.ui.reservation

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
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
import androidx.compose.ui.unit.IntOffset
import org.w3c.dom.Text
import kotlin.math.roundToInt

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

@Composable
fun <T> DragTarget(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    content: @Composable ((text: String) -> Unit)
){
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    var currentOriginPos by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current

    Box(
        modifier = modifier
            .offset{ IntOffset(currentOriginPos.x.roundToInt(), currentOriginPos.y.roundToInt()) }
            .onGloballyPositioned {
                currentPosition = it.localToWindow(Offset.Zero)
            }
            .pointerInput(Unit){
                detectDragGesturesAfterLongPress(
                    onDragStart = {
                        currentState.dataToDrop = dataToDrop
                        currentState.isDragging = true
                        currentState.dragPosition = currentOriginPos
//                        currentOriginOffset += it
                        currentState.draggableComposable = content
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
                    },
                    onDragEnd = {

                        currentState.dragOffset = Offset(
                            (currentState.dragOffset.x.roundToInt() / 200 * 200).toFloat(),
                            (currentState.dragOffset.y.roundToInt() / 200 * 200).toFloat()
                        )
                        currentOriginPos += currentState.dragOffset
                        currentState.dragOffset = Offset.Zero
                        currentState.isDragging = false
                    },
                    onDragCancel = {

                    },
                )
            }
    ){
        Log.i("recompositionOfContent", currentPosition.x.roundToInt().toString() + " " + currentPosition.y.roundToInt())
        Log.i("recompositionOfContentOrigin", currentOriginPos.x.roundToInt().toString() + " " + currentOriginPos.y.roundToInt())
        if(!currentState.isDragging) {
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