package com.carlodips.simpleloginregistration.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.carlodips.simpleloginregistration.ui.login.LoginScreen
import com.carlodips.simpleloginregistration.ui.register.RegisterScreen

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
        composable(route = ScreenRoute.Home.route) {
            Text("You are now logged in")
        }

        composable(route = ScreenRoute.Login.route) {
            LoginScreen(
                navigateTo = { screenRoute ->
                    navController.navigate(screenRoute.route)
                }
            )
        }

        composable(route = ScreenRoute.Register.route) {
            RegisterScreen(
                onPopBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}
