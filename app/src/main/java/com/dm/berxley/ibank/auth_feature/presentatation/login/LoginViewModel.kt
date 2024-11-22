package com.dm.berxley.ibank.auth_feature.presentatation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.berxley.ibank.Screen
import com.dm.berxley.ibank.core.presentation.util.FirebaseAuthHelper
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val firebaseAuthHelper: FirebaseAuthHelper
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val _loginEvents = Channel<LoginEvent>()
    val loginEvents = _loginEvents.receiveAsFlow()

    private val auth = Firebase.auth


    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.EmailPasswordLogin -> {
                val email = action.email
                val password = action.password

                if (isValid(email, password)) {
                    _loginState.update { it.copy(isLoading = true) }
                    emailPasswordLogin(email, password)
                }
            }

            LoginAction.GoogleLogin -> {
                googleLogin()
            }
        }
    }

    private fun googleLogin() {
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
                            _loginEvents.send(LoginEvent.Navigate(route = Screen.MainAppNavigator.route))
                        }
                    },
                    onFailure = {
                        var errorMessage = "Google Sign In failed. "
                        result.exceptionOrNull()?.let { err ->
                            errorMessage += err.message
                        }

                        //send error by channel
                        _loginEvents.send(LoginEvent.OnError(message = errorMessage))

                    }
                )

            }
        }
    }


    private fun isValid(email: String, password: String): Boolean {
        _loginState.update { it.copy(emailError = null, passwordError = null) }
        if (email.isEmpty()) {
            _loginState.update { it.copy(emailError = "E-Mail is required") }
            return false
        }

        if (password.isEmpty()) {
            _loginState.update { it.copy(passwordError = "Password is required") }
            return false
        }

        return true
    }

    private fun emailPasswordLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { result ->
            _loginState.update { it.copy(isLoading = false) }
            if (result.isSuccessful) {
                //emit navigate to home
                viewModelScope.launch {
                    _loginEvents.send(LoginEvent.Navigate(Screen.MainAppNavigator.route))
                }
            } else {
                val errorMessage = "Error: ${result.exception?.message}"
                //emit error message
                viewModelScope.launch {
                    _loginEvents.send(LoginEvent.OnError(errorMessage))
                }
            }

        }
    }
}