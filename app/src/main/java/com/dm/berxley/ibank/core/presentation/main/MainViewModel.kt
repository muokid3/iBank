package com.dm.berxley.ibank.core.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.berxley.ibank.core.presentation.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    private val _channelEvents = Channel<MainEvent>()
    val channelEvents = _channelEvents.receiveAsFlow()

    init {

        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            if (auth.currentUser == null) {
                _mainState.update {
                    it.copy(startDestination = Screen.OnboardingNavigator.route, isLoggedIn = false, selectedBottomIndex = 0)

                }
                //send event to navigate
                viewModelScope.launch {
                    _channelEvents.send(MainEvent.MainNavigate(route = Screen.OnboardingNavigator.route))
                }
            } else {
                _mainState.update {
                    it.copy(startDestination = Screen.MainAppNavigator.route, isLoggedIn = true)

                }
            }

        }
    }

    fun setSelectedBottomIndex(index: Int) {
        _mainState.update {
            it.copy(selectedBottomIndex = index)
        }
    }

    fun setStartDestination(route: String) {
        _mainState.update {
            it.copy(startDestination = route)
        }
    }

}