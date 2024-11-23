package com.dm.berxley.ibank.core.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Index
import com.dm.berxley.ibank.core.presentation.navigation.Screen
import com.dm.berxley.ibank.core.presentation.util.FirebaseAuthHelper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    firebaseAuthHelper: FirebaseAuthHelper
): ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    private val _channelEvents = Channel<MainEvent>()
    val channelEvents = _channelEvents.receiveAsFlow()

    init {
        if (firebaseAuthHelper.getCurrentUser() == null){
            _mainState.update {
                it.copy(startDestination = Screen.OnboardingNavigator.route)

            }
            //send event to navigate
            viewModelScope.launch {
                _channelEvents.send(MainEvent.MainNavigate(route = Screen.OnboardingNavigator.route))
            }
        }
    }

    fun setSelectedBottomIndex(index: Int){
        _mainState.update {
            it.copy(selectedBottomIndex = index)
        }
    }

}