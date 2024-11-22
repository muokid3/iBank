package com.dm.berxley.ibank.core.presentation.home

import androidx.lifecycle.ViewModel
import com.dm.berxley.ibank.core.presentation.util.FirebaseAuthHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val firebaseAuthHelper: FirebaseAuthHelper
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        //update user details
        val firebaseUser = firebaseAuthHelper.getCurrentUser()

        firebaseUser?.let { user ->
            _homeState.update {
                it.copy(
                    name = user.displayName!!,
                    email = user.email!!,
                    photoUrl = user.photoUrl.toString()
                )
            }
        }
    }
}