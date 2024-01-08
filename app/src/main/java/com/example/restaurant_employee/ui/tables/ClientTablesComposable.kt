package com.example.restaurant_employee.ui.tables

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.restaurant_employee.R

// Testing
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientTablesComposable(){
    BoxWithConstraints {
        Box(modifier = Modifier.fillMaxSize()){
            val columns = 18f
            val rows = 28f

            val tileWidth = this@BoxWithConstraints.maxWidth / columns
            val tileHeight = this@BoxWithConstraints.maxHeight / rows

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.tables_blue_empty),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            for(row in 0 until rows.toInt()){
                for(column in 0 until columns.toInt()){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(start = tileWidth * column, top = tileHeight * row)
                            .width(tileWidth)
                            .height(tileHeight)
                            .border(width = 0.1.dp, color = MaterialTheme.colorScheme.primary)
                    ){
//                        Text(
//                            text = "$column"
//                        )
                    }
                }
            }

            for(tableUI in ClientTablesDataSource.listOfTables){
                val alpha: Float by rememberInfiniteTransition(label = "anim1").animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = 2000,
                            easing = LinearEasing,
                            delayMillis = 2000
                        ),
                        repeatMode = RepeatMode.Reverse,
                    ), label = "anim1"
                )

                Box(
                    modifier = Modifier
                        .padding(
                            start = tileWidth * tableUI.x,
                            top = tileHeight * tableUI.y
                        )
                        .height(tileHeight * 2)
                        .width(tileWidth * 3)
//                        .background(color = Color.Green.copy(alpha = alpha))
                        .border(width = 1.dp, color = Color.Green.copy(alpha = alpha))
                        .clickable {
                            Log.i("ClientTablesComposable", "onClick Table")
                        }
                ){
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = Color.Green,
                            ) {
                                Text(
                                    text = tableUI.id,
                                    color = Color.Black
                                )
                            }
                        }
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(id = R.drawable.table3x2),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            }
        }
    }
}

@Preview(device = "spec:width=1080px,height=2000px", showSystemUi = true)
@Composable
fun Mi9TReservationPreview(){
    ClientTablesComposable()
}

@Preview(device = "spec:width=768px,height=1366px", showSystemUi = true)
@Composable
fun ReservationPreview2(){
    ClientTablesComposable()
}

@Preview(device = "spec:width=900px,height=1440px", showSystemUi = true)
@Composable
fun ReservationPreview3(){
    ClientTablesComposable()
}