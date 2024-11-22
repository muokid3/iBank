package com.dm.berxley.ibank.auth_feature.presentatation.register

import java.lang.Error

data class RegisterState (
    val isLoading: Boolean = false,
    val termsAccepted: Boolean = false,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val conformPasswordError: String? = null,

)