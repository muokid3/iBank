package com.dm.berxley.ibank.auth_feature.presentatation.login

sealed interface LoginAction {
    data class EmailPasswordLogin(val email: String, val password: String): LoginAction
    data object GoogleLogin: LoginAction
}