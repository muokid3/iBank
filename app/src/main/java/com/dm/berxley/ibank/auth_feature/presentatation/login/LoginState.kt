package com.dm.berxley.ibank.auth_feature.presentatation.login

data class LoginState(
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
)
