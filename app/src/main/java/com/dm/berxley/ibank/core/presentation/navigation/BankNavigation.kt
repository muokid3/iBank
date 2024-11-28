package com.dm.berxley.ibank.core.presentation.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.dm.berxley.ibank.auth_feature.presentatation.login.LoginEvent
import com.dm.berxley.ibank.auth_feature.presentatation.login.LoginScreen
import com.dm.berxley.ibank.auth_feature.presentatation.login.LoginViewModel
import com.dm.berxley.ibank.auth_feature.presentatation.register.RegisterEvent
import com.dm.berxley.ibank.auth_feature.presentatation.register.RegisterScreen
import com.dm.berxley.ibank.auth_feature.presentatation.register.RegisterViewModel
import com.dm.berxley.ibank.core.presentation.home.HomeScreen
import com.dm.berxley.ibank.core.presentation.home.HomeViewModel
import com.dm.berxley.ibank.core.presentation.main.MainState
import com.dm.berxley.ibank.messaging_feature.presentation.message_list_screen.MessageListScreen
import com.dm.berxley.ibank.search_feature.presentation.search_exchange_rate.Events
import com.dm.berxley.ibank.search_feature.presentation.search_exchange_rate.ExchangeRateScreen
import com.dm.berxley.ibank.search_feature.presentation.search_exchange_rate.ExchangeRateViewModel
import com.dm.berxley.ibank.search_feature.presentation.search_main_screen.SearchMainScreen
import com.dm.berxley.ibank.settings_feature.presentation.settings_main_screen.SettingsMainScreen
import com.dm.berxley.ibank.settings_feature.presentation.settings_main_screen.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun BankNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainState: MainState,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = mainState.startDestination,
    ) {

        //onboarding
        navigation(
            route = Screen.OnboardingNavigator.route,
            startDestination = Screen.LoginScreen.route
        ) {
            composable(route = Screen.LoginScreen.route) {
                val loginViewModel = koinViewModel<LoginViewModel>()
                val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

                val lifecycleOwner = LocalLifecycleOwner.current
                LaunchedEffect(lifecycleOwner.lifecycle) {
                    lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        withContext(Dispatchers.Main.immediate) {
                            loginViewModel.loginEvents.collect { event ->
                                when (event) {
                                    is LoginEvent.Navigate -> {
                                        navController.navigate(event.route) {
                                            popUpTo(Screen.OnboardingNavigator.route) {
                                                inclusive = true
                                            }
                                        }
                                    }

                                    is LoginEvent.OnError -> {
                                        snackbarHostState.showSnackbar(
                                            message = event.message,
                                            duration = SnackbarDuration.Long
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                LoginScreen(navController = navController,
                    state = loginState,
                    onAction = { loginViewModel.onAction(it) })
            }

            composable(route = Screen.SignUpScreen.route) {
                val registerViewModel = koinViewModel<RegisterViewModel>()
                val state by registerViewModel.registerState.collectAsStateWithLifecycle()

                val lifecycleOwner = LocalLifecycleOwner.current
                LaunchedEffect(lifecycleOwner) {
                    lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        withContext(Dispatchers.Main.immediate) {
                            registerViewModel.events.collect { event ->
                                when (event) {
                                    is RegisterEvent.Navigate -> {
                                        navController.navigate(event.route) {
                                            popUpTo(Screen.OnboardingNavigator.route) {
                                                inclusive = true
                                            }
                                        }
                                    }

                                    is RegisterEvent.OnError -> {
                                        snackbarHostState.showSnackbar(
                                            message = event.message,
                                            duration = SnackbarDuration.Long
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
                RegisterScreen(navController = navController,
                    registerState = state,
                    onAction = { registerViewModel.onAction(it) })
            }

        }

        //main app
        navigation(
            route = Screen.MainAppNavigator.route,
            startDestination = Screen.HomeScreen.route
        ) {
            composable(route = Screen.HomeScreen.route) {
                val homeViewModel = koinViewModel<HomeViewModel>()
                val homeState by homeViewModel.homeState.collectAsStateWithLifecycle()

                HomeScreen(
                    navController = navController, state = homeState
                )
            }

            composable(route = Screen.MessagesListScreen.route) {
                MessageListScreen(
                    navController = navController
                )
            }

            composable(route = Screen.SearchMainScreen.route) {
                SearchMainScreen(
                    navController = navController
                )
            }

            composable(route = Screen.ExchangeRateScreen.route) {
                val exchangeRateViewModel = koinViewModel<ExchangeRateViewModel>()
                val exchangeRateState by exchangeRateViewModel.state.collectAsStateWithLifecycle()

                val lifecycleOwner = LocalLifecycleOwner.current
                LaunchedEffect(lifecycleOwner.lifecycle) {
                    lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        withContext(Dispatchers.Main.immediate) {
                            exchangeRateViewModel.events.collect { event ->
                                when (event) {
                                    is Events.OnError -> {
                                        snackbarHostState.showSnackbar(
                                            message = event.message
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                ExchangeRateScreen(
                    navController = navController,
                    state = exchangeRateState,
                    onAction = exchangeRateViewModel::onAction
                )
            }

            composable(route = Screen.SettingsMainScreen.route) {
                val settingsViewModel = koinViewModel<SettingsViewModel>()
                val settingsState by settingsViewModel.settingsState.collectAsStateWithLifecycle()
                SettingsMainScreen(
                    navController = navController,
                    state = settingsState,
                    onAction = { settingsViewModel.onAction(it) }
                )
            }
        }


    }
}