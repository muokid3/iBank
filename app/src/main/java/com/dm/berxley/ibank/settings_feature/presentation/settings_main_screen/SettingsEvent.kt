package com.dm.berxley.ibank.settings_feature.presentation.settings_main_screen

sealed interface SettingsEvent {
    data class Navigate(val route: String): SettingsEvent
}