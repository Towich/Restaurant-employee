package com.example.nonameapp.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.restaurant_employee.ui.tables.ClientTablesComposable
import com.example.restaurant_employee.ui.reservation.ReservationScreen

object NavigationRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.Tables)
}

@Composable
fun Navigation(navController: NavHostController, context: Context) {
    NavHost(navController = navController, startDestination = Screen.ClientTables.route) {
        composable(route = Screen.Tables.route) {
            ReservationScreen()
        }
        composable(route = Screen.ClientTables.route) {
            ClientTablesComposable()
        }
    }
}

