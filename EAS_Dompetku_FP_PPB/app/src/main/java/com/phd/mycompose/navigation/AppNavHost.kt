package com.phd.mycompose.navigation

import StartingApp
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.phd.mycompose.Splash
import com.phd.mycompose.screen.FinancialAppScreen1
import com.phd.mycompose.screen.FinancialAppScreen2

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            Splash {
                navController.navigate("financialAppScreen1") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
        composable("financialAppScreen1") {
            FinancialAppScreen1(navController)
        }
        composable("financialAppScreen2") {
            FinancialAppScreen2(navController)
        }
        composable("startingApp") {
            StartingApp()
        }
    }
}






