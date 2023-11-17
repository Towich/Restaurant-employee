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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.exyte.animatednavbar.utils.toDp
import com.exyte.animatednavbar.utils.toPxf
import kotlin.math.roundToInt

@Composable
fun ReservationScreen(

) {
    var boxSize by remember { mutableStateOf(Size.Zero)}

    DraggableScreen(
        modifier = Modifier
            .onGloballyPositioned {layoutCoordinates ->
                boxSize = layoutCoordinates.size.toSize()
            }
    ) {
        val configuration = LocalConfiguration.current

        val sizeOfPoints = 4.dp

        // Before: 60.dp
        val sizeOfTable = (configuration.screenWidthDp.dp / 6f)

        var tablesCount by remember { mutableIntStateOf(0) }

        val startOffset = (sizeOfTable / 2 - sizeOfPoints / 2).toPxf()
        val offsetStep: Float = (sizeOfTable - sizeOfPoints / 2).toPxf()

        val maxOffsetX = boxSize.width
        val maxOffsetY = boxSize.height
        Log.i("maxOffset", "x = $maxOffsetX, y = $maxOffsetY")

        var offsetX = startOffset
        var offsetY = startOffset

        while(offsetX < maxOffsetX){
            while(offsetY < maxOffsetY){
                Box(
                    modifier = Modifier
                        .offset(
                            if (offsetX.roundToInt() == startOffset.roundToInt())
                                offsetX.toDp()
                            else
                                (offsetX + (offsetX / offsetStep.roundToInt()) * sizeOfPoints
                                    .toPxf()
                                    .roundToInt() / 2).toDp(),
                            if (offsetY.roundToInt() == startOffset.roundToInt())
                                offsetY.toDp()
                            else
                                (offsetY + (offsetY / offsetStep.roundToInt()) * sizeOfPoints
                                    .toPxf()
                                    .roundToInt() / 2).toDp()
                        )
                        .size(sizeOfPoints)
                        .background(MaterialTheme.colorScheme.primary)
                )

                offsetY += offsetStep
            }
            offsetY = startOffset
            offsetX += offsetStep
        }

        repeat(tablesCount) {
            DraggableTable(sizeOfTable = sizeOfTable)
        }

        FloatingActionButton(
            onClick = { tablesCount++ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
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
            modifier = modifier
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
                            Log.i(
                                "graphicsLayer",
                                state.dragPosition.toString() + state.dragOffset.toString()
                            )
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
                    state.draggableComposable?.invoke((state.dragPosition + state.dragOffset).toString())
                }
            }
        }
    }
}


@Composable
fun DraggableTable(
    sizeOfTable: Dp
){
    DragTarget(
        dataToDrop = "1",
        contentSize = sizeOfTable
    ) {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.primary)
                .size(sizeOfTable),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
            )
        }
    }
}
