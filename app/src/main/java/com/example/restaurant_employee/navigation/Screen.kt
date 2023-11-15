package com.example.nonameapp.navigation

import com.example.restaurant_employee.R

sealed class Screen(val route : String, val icon : Int, val title : String) {
    object Tables : Screen("tables_screen",
        title = "Tables", icon = R.drawable.google_icon)

    object Items{
        val list = listOf(
            Tables
        )
    }
}
