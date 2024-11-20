package com.dm.berxley.ibank

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _startDestination = MutableStateFlow(Screen.OnboardingNavigator.route)
    val startDestination = _startDestination.asStateFlow()

    init {

        //check shared prefs here and change star destination accordingly
        _startDestination.apply {
            Screen.OnboardingNavigator.route
        }

//        _startDestination.apply {
//            Screen.MainAppNavigator.route
//        }
    }


}