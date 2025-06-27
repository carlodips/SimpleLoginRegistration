package com.carlodips.simpleloginregistration.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.carlodips.simpleloginregistration.ui.home.HomeScreen
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
            HomeScreen(
                onLogoutUser = {
                    navController.navigate(ScreenRoute.Login.route) {
                        popUpTo(ScreenRoute.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = ScreenRoute.Login.route) {
            LoginScreen(
                navigateTo = { screenRoute ->
                    navController.navigate(screenRoute.route) {
                        if (screenRoute.route == ScreenRoute.Home.route) {
                            navController.navigate(ScreenRoute.Login.route) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    }
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
