package com.dm.berxley.ibank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.ibank.auth_feature.presentatation.login.LoginScreen
import com.dm.berxley.ibank.core.presentation.home.HomeScreen
import com.dm.berxley.ibank.ui.theme.IBankTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController();
            val mainViewModel = koinViewModel<MainViewModel>()
            val startDestination =
                mainViewModel.startDestination.collectAsStateWithLifecycle().value

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
                                LoginScreen(navController = navController)
                            }

                        }

                        //main app
                        navigation(
                            route = Screen.MainAppNavigator.route,
                            startDestination = Screen.HomeScreen.route
                        ) {
                            composable(route = Screen.HomeScreen.route) {
                                HomeScreen(navController = navController)
                            }
                        }


                    }
                }
            }
        }
    }
}