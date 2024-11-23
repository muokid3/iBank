package com.dm.berxley.ibank.auth_feature.presentatation.register

import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.berxley.ibank.core.presentation.navigation.Screen
import com.google.firebase.Firebase
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    private val _events = Channel<RegisterEvent>()
    val events = _events.receiveAsFlow()

    private val auth = Firebase.auth

    fun onAction(registerAction: RegisterAction) {
        when (registerAction) {
            is RegisterAction.Register -> {
                if (validateInput(
                        name = registerAction.name,
                        email = registerAction.email,
                        password = registerAction.password,
                        confirmPassword = registerAction.confirmPassword
                    )
                ) {
                    _registerState.update {
                        it.copy(isLoading = true)
                    }
                    //call register function here
                    emailPasswordSignUp(
                        name = registerAction.name,
                        email = registerAction.email,
                        password = registerAction.password
                    )
                }

            }

            is RegisterAction.TermsToggle -> {
                _registerState.update {
                    it.copy(termsAccepted = registerAction.isChecked)
                }
            }
        }
    }

    private fun emailPasswordSignUp(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("EMAILPASSSIGNUP", "createUserWithEmail:success")
                    val user = auth.currentUser
                    //update display name and photo url
                    user?.updateProfile(
                        UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .setPhotoUri(Uri.parse(""))
                            .build()
                    )?.addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            // User profile updated successfully
                            _registerState.update {
                                it.copy(isLoading = false)
                            }
                            //emit channel event to navigate back home
                            viewModelScope.launch {
                                _events.send(RegisterEvent.Navigate(route = Screen.MainAppNavigator.route))
                            }

                        } else {
                            _registerState.update {
                                it.copy(isLoading = false)
                            }
                            // Handle profile update failure

                            Log.w("UPDATE", "Error updating user profile", updateTask.exception)
                            //emit channel event to show error
                            viewModelScope.launch {
                                _events.send(RegisterEvent.OnError(message = "Error: ${updateTask.exception?.message}"))
                            }
                        }

                    }
                } else {
                    _registerState.update {
                        it.copy(isLoading = false)
                    }
                    // If sign in fails, display a message to the user.
                    val errorMessage = "Sign up failed. ${task.exception?.message}"
                    Log.w("EMAILPASSSIGNUP", task.exception)
                    //emit channel event to show error
                    viewModelScope.launch {
                        _events.send(RegisterEvent.OnError(message = errorMessage))
                    }
                }
            }
    }

    private fun validateInput(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        _registerState.update {
            it.copy(
                nameError = null,
                emailError = null,
                passwordError = null,
                conformPasswordError = null
            )
        }

        if (name.isEmpty()) {
            _registerState.update { it.copy(nameError = "Name is required") }
            return false
        }

        if (email.isEmpty()) {
            _registerState.update { it.copy(emailError = "E-Mail is required") }
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _registerState.update { it.copy(emailError = "Enter a valid E-Mail address") }
            return false
        }
        if (password.isEmpty()) {
            _registerState.update { it.copy(passwordError = "Password is required") }
            return false
        }
        if (confirmPassword.isEmpty() || confirmPassword != password) {
            _registerState.update { it.copy(passwordError = "Both passwords must match") }
            return false
        }

        if (!_registerState.value.termsAccepted) {
            val message = "You must accept the terms and conditions before you proceed"
            //emit channel event to show error
            viewModelScope.launch {
                _events.send(RegisterEvent.OnError(message = message))
            }
            return false
        }

        return true
    }


}