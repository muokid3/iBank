package com.dm.berxley.ibank.core.presentation.main

sealed interface MainEvent {
    data class MainNavigate(val route: String) : MainEvent
}