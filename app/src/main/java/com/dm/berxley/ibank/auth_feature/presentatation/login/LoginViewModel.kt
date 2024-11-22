package com.dm.berxley.ibank.auth_feature.presentatation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val firebaseAuthHelper: FirebaseAuthHelper
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    init {
        //check if logged in and update isLoggedIn

    }


    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.EmailPasswordLogin -> {
                val email = action.email
                val password = action.password

                //do login here
            }

            LoginAction.GoogleLogin -> {
                googleLogin()
            }
        }
    }

    private fun googleLogin(){
        viewModelScope.launch {
            firebaseAuthHelper.googleSignIn().collect { result ->
                result.fold(
                    onSuccess = {
                        val authResult = result.getOrNull()
                        authResult?.user?.let { user ->
//                            val name = user.displayName
//                            val email = user.email
//                            val photoUrl = user.photoUrl
                            //navigate to home. send event by channel

                        }
                    },
                    onFailure = {
                        var errorMessage = "Google Sign In failed. "
                        result.exceptionOrNull()?.let {err->
                            errorMessage + err.message
                        }

                        //send error by channel

                    }
                )

            }
        }
    }
}