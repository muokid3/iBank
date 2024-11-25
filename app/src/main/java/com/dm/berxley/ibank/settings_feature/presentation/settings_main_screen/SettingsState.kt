package com.dm.berxley.ibank.settings_feature.presentation.settings_main_screen

import com.google.firebase.auth.FirebaseUser

data class SettingsState(
    val user: FirebaseUser? = null,
)
