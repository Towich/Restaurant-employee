package com.example.restaurant_employee.ui.tables

data class TableUIModel(
    val id: String,
    val x: Int,
    val y: Int,
    val widthTiles: Int = 3,
    val heightTiles: Int = 2
)
