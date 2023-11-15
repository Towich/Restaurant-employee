package com.example.restaurant_employee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nonameapp.navigation.Navigation
import com.example.nonameapp.navigation.NavigationRouter
import com.example.nonameapp.navigation.Screen
import com.example.nonameapp.ui.bottomNavigation.CustomBottomNavigation
import com.example.restaurant_employee.ui.theme.RestaurantemployeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
            window.navigationBarColor = MaterialTheme.colorScheme.background.toArgb()

            RestaurantemployeeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        bottomBar = {
                            CustomBottomNavigation(currentScreenRoute = NavigationRouter.currentScreen.value.route) { screen ->
                                if (screen.route != NavigationRouter.currentScreen.value.route) {
                                    NavigationRouter.currentScreen.value = screen
                                    navController.navigate(screen.route)
                                }
                            }
                        }
                    ) { contentPadding ->
                        run {
                            Box(modifier = Modifier.padding(contentPadding)) {
                                Navigation(navController, applicationContext)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RestaurantemployeeTheme {
        Greeting("Android")
    }
}