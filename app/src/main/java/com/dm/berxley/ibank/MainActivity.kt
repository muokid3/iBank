package com.dm.berxley.ibank

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.ibank.auth_feature.presentatation.login.FirebaseAuthHelper
import com.dm.berxley.ibank.auth_feature.presentatation.login.LoginScreen
import com.dm.berxley.ibank.auth_feature.presentatation.login.LoginViewModel
import com.dm.berxley.ibank.auth_feature.presentatation.register.RegisterScreen
import com.dm.berxley.ibank.core.presentation.home.HomeScreen
import com.dm.berxley.ibank.core.presentation.home.HomeViewModel
import com.dm.berxley.ibank.ui.theme.IBankTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val firebaseAuthHelper by lazy {
        FirebaseAuthHelper(context = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController();
            var startDestination by rememberSaveable { mutableStateOf(Screen.MainAppNavigator.route) }

            LaunchedEffect(key1 = Unit) {
                if (firebaseAuthHelper.getCurrentUser() == null) {
                    startDestination = Screen.OnboardingNavigator.route
                    navController.navigate(startDestination)
                }
            }

            IBankTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    if (startDestination == Screen.MainAppNavigator.route) {
                        //show bottom bar here


                    }
                }) { innerPadding ->

                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = startDestination
                    ) {

                        //onboarding
                        navigation(
                            route = Screen.OnboardingNavigator.route,
                            startDestination = Screen.LoginScreen.route
                        ) {
                            composable(route = Screen.LoginScreen.route) {
                                val loginViewModel = koinViewModel<LoginViewModel>()
                                val state by loginViewModel.loginState.collectAsStateWithLifecycle()

                                LoginScreen(
                                    navController = navController,
                                    state = state,
                                    onAction = { loginAction ->
                                        loginViewModel.onAction(loginAction)
                                    }
                                )
                            }

                            composable(route = Screen.SignUpScreen.route) {
                                RegisterScreen(navController = navController)
                            }

                        }

                        //main app
                        navigation(
                            route = Screen.MainAppNavigator.route,
                            startDestination = Screen.HomeScreen.route
                        ) {
                            composable(route = Screen.HomeScreen.route) {
                                val homeViewModel = koinViewModel<HomeViewModel>()
                                val state by homeViewModel.homeState.collectAsStateWithLifecycle()

                                HomeScreen(
                                    navController = navController,
                                    state = state
                                )
                            }
                        }


                    }
                }
            }
        }
    }
}