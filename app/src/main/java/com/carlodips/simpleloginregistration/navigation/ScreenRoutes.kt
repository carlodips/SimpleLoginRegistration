package com.carlodips.simpleloginregistration.navigation

sealed class ScreenRoute(val route: String) {
    data object Login : ScreenRoute("login")

    data object Register : ScreenRoute("register")

    data object Home : ScreenRoute("home")
}
