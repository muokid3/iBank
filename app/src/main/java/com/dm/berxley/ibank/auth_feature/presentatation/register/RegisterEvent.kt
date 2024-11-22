package com.dm.berxley.ibank.auth_feature.presentatation.register

sealed interface RegisterEvent {
    data class Navigate(val route: String): RegisterEvent
    data class OnError(val message: String): RegisterEvent
}