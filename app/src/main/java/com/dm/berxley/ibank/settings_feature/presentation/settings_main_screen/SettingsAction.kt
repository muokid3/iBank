package com.dm.berxley.ibank.settings_feature.presentation.settings_main_screen

sealed interface SettingsAction {
    data object Logout : SettingsAction
    data class Navigate(val route: String) : SettingsAction
}