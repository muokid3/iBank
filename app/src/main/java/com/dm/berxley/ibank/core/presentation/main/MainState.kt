package com.dm.berxley.ibank.core.presentation.main

import com.dm.berxley.ibank.core.presentation.navigation.Screen

data class MainState(
    val selectedBottomIndex: Int = 0,
    val startDestination: String = Screen.MainAppNavigator.route
)