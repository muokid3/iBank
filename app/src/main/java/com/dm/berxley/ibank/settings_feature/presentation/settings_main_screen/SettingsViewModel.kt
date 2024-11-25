package com.dm.berxley.ibank.settings_feature.presentation.settings_main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState = _settingsState.asStateFlow()

    private val _events = Channel<SettingsEvent>()
    val events = _events.receiveAsFlow()


    private val firebaseAuth = Firebase.auth

    init {
        val user = firebaseAuth.currentUser
        user.let {
            _settingsState.update { it.copy(user = user) }
        }
    }

    fun onAction(settingsAction: SettingsAction) {
        when (settingsAction) {
            SettingsAction.Logout -> {
                logOut()
            }

            is SettingsAction.Navigate -> {
                //emit navigation event
                viewModelScope.launch {
                    _events.send(SettingsEvent.Navigate(settingsAction.route))
                }
            }
        }
    }

    private fun logOut() {
        firebaseAuth.signOut()
    }


}