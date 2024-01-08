package com.example.restaurant_employee.ui.reservation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.restaurant_employee.R
import com.example.restaurant_employee.ui.tables.TableUIModel
import java.util.UUID

@Composable
fun ReservationScreen() {
    var tablesCount by remember { mutableIntStateOf(0) }
    val tables = remember { mutableStateListOf<TableUIModel>() }
    val columns = 18f
    val rows = 28f

    DraggableScreen(
        modifier = Modifier
    ) {
        BoxWithConstraints {
            Box(modifier = Modifier.fillMaxSize()) {
                val tileWidth: Dp = this@BoxWithConstraints.maxWidth / columns
                val tileHeight: Dp = this@BoxWithConstraints.maxHeight / rows

                // Background
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.tables_blue_empty),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )

                // Cells
                for (row in 0 until rows.toInt()) {
                    for (column in 0 until columns.toInt()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(start = tileWidth * column, top = tileHeight * row)
                                .width(tileWidth)
                                .height(tileHeight)
                                .border(width = 0.5.dp, color = Color.Black)
                        ) {
//                        Text(
//                            text = "$column"
//                        )
                        }
                    }
                }

                // Tables
                for(table in tables){
                    DraggableTable(
                        tileWidth = tileWidth,
                        tileHeight = tileHeight,
                        tableWidthInTiles = table.widthTiles,
                        tableHeightInTiles = table.heightTiles
                    )
                }

//                repeat(tablesCount) {
//
//                }

                // FAB Create table 3x2
                FloatingActionButton(
                    onClick = {
                        tables.add(
                            TableUIModel(
                            id = UUID.randomUUID().toString(),
                            x = 0,
                            y = 0,
                            widthTiles = 3,
                            heightTiles = 2
                        )
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                }

                // FAB Create table 2x2
                FloatingActionButton(
                    onClick = {
                        tables.add(TableUIModel(
                            id = UUID.randomUUID().toString(),
                            x = 0,
                            y = 0,
                            widthTiles = 2,
                            heightTiles = 2
                        ))
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                }
            }
        }
    }
}
//        while (offsetX < maxOffsetX) {
//            while (offsetY < maxOffsetY) {
//                Box(
//                    modifier = Modifier
//                        .offset(
//                            if (offsetX.roundToInt() == startOffset.roundToInt())
//                                offsetX.toDp()
//                            else
//                                (offsetX + (offsetX / offsetStep.roundToInt()) * sizeOfPoints
//                                    .toPxf()
//                                    .roundToInt() / 2).toDp(),
//                            if (offsetY.roundToInt() == startOffset.roundToInt())
//                                offsetY.toDp()
//                            else
//                                (offsetY + (offsetY / offsetStep.roundToInt()) * sizeOfPoints
//                                    .toPxf()
//                                    .roundToInt() / 2).toDp()
//                        )
//                        .size(sizeOfPoints)
//                        .background(MaterialTheme.colorScheme.primary)
//                )
//
//                offsetY += offsetStep
//            }
//            offsetY = startOffset
//            offsetX += offsetStep
//        }






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
    tileWidth: Dp,
    tileHeight: Dp,
    tableWidthInTiles: Int = 1,
    tableHeightInTiles: Int = 1
) {
    DragTarget(
        dataToDrop = "1",
        widthTarget = tileWidth * tableWidthInTiles,
        heightTarget = tileHeight * tableHeightInTiles,
        tableWidthInTiles = tableWidthInTiles,
        tableHeightInTiles = tableHeightInTiles
    ) {
        Box(
            Modifier
                .width(tileWidth * tableWidthInTiles)
                .height(tileHeight * tableHeightInTiles),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = if(tableWidthInTiles == 3) R.drawable.table3x2 else R.drawable.table2x2),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
