package com.dm.berxley.ibank.auth_feature.presentatation.register

sealed interface RegisterAction {
    data class Register(val name: String, val email: String, val password: String, val confirmPassword: String): RegisterAction
    data class TermsToggle(val isChecked: Boolean): RegisterAction
}