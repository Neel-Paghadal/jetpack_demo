package com.example.jetpackcomposedemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackcomposedemo.db.DashboardActivity

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable(
            "medications?username={username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            MedicationScreen(navController,username = username ?: "Guest")
        }

        composable(
            "medicationDetail/{name}/{strength}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("strength") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val strength = backStackEntry.arguments?.getString("strength") ?: ""
            MedicationDetailScreen(navController, medicationName = name, medicationStrength = strength)
        }

        /*composable("dashboard") {
            DashboardActivity()
        }*/
    }
}