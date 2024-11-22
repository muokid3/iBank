package com.dm.berxley.ibank.auth_feature.presentatation.login

sealed interface LoginEvent {
    data class Navigate(val route: String): LoginEvent
    data class OnError(val message: String): LoginEvent
}