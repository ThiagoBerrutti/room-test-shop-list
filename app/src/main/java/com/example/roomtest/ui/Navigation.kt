package com.example.roomtest.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roomtest.ui.detail.DetailScreen
import com.example.roomtest.ui.home.HomeScreen
import kotlinx.coroutines.launch

enum class Routes {
    Home, Detail
}

@Composable
fun JetShoppingNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    val scope = rememberCoroutineScope()
    NavHost(navHostController, startDestination = Routes.Home.name) {
        composable(route = Routes.Home.name) {
            Log.d("Testing","HomeScreen")
            HomeScreen(onNavigate = { id ->
                scope.launch {
                    navHostController.navigate(route = "${Routes.Detail.name}?id=$id")
                }
            })
        }
        composable(route = "${Routes.Detail.name}?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: -1
            Log.d("Testing","Detail: ${id}")
            DetailScreen(id){
                    navHostController.navigateUp()
            }

        }
    }
}