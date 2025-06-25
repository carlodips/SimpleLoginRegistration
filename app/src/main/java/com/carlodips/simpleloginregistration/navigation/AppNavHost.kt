package com.carlodips.simpleloginregistration.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.carlodips.simpleloginregistration.ui.login.LoginScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    //startDestination: String = ScreenRoute.Login.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ScreenRoute.Login.route
    ) {
        composable(ScreenRoute.Home.route) {
            Text("You are now logged in")

        }

        composable(ScreenRoute.Login.route) {
            LoginScreen()
        }

        composable(ScreenRoute.Register.route) {

        }
    }
}
