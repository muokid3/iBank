package com.dm.berxley.ibank.core.presentation.navigation

sealed class Screen(
    val route: String
) {
    object OnboardingNavigator : Screen(route = "onboardingNavigator")
    object LoginScreen : Screen(route = "loginScreen")
    object SignUpScreen : Screen(route = "signUpScreen")
    object ForgotPasswordScreen : Screen(route = "forgotPasswordScreen")
    object ForgotPasswordOTPScreen : Screen(route = "forgotPasswordOTPScreen")
    object ChangePasswordScreen : Screen(route = "changePasswordScreen")
    object ChangePasswordSuccessScreen : Screen(route = "changePasswordSuccessScreen")

    object MainAppNavigator : Screen(route = "mainAppNavigator")
    object HomeScreen : Screen(route = "homeScreen")
    object MessagesListScreen : Screen(route = "messagesListScreen")
    object SearchMainScreen : Screen(route = "searchMainScreen")
    object SettingsMainScreen : Screen(route = "settingsMainScreen")

}