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
    object ExchangeRateScreen : Screen(route = "exchangeRateScreen")


    object SettingsMainScreen : Screen(route = "settingsMainScreen")
    object PasswordScreen : Screen(route = "passwordScreen")
    object TouchIDScreen : Screen(route = "touchIDScreen")
    object LanguagesScreen : Screen(route = "languagesScreen")
    object AppInformationScreen : Screen(route = "appInformationScreen")
    object CustomerCareScreen : Screen(route = "customerCareScreen")

}