package com.dm.berxley.ibank.core.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.ibank.core.presentation.navigation.BankNavigation
import com.dm.berxley.ibank.core.presentation.navigation.BottomBar
import com.dm.berxley.ibank.core.presentation.navigation.Screen
import com.dm.berxley.ibank.core.presentation.util.FirebaseAuthHelper
import com.dm.berxley.ibank.ui.theme.IBankTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val firebaseAuthHelper by lazy {
        FirebaseAuthHelper(context = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel = koinViewModel<MainViewModel>()
            val state = mainViewModel.mainState.collectAsStateWithLifecycle().value

            val navController = rememberNavController();
            val snackbarHostState = remember { SnackbarHostState() }

            val localLifecycle = LocalLifecycleOwner.current
            LaunchedEffect(localLifecycle) {
                localLifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                    withContext(Dispatchers.Main.immediate){

                        mainViewModel.channelEvents.collect{ mainEvent->
                            when(mainEvent){
                                is MainEvent.MainNavigate -> {
                                    navController.navigate(mainEvent.route){
                                        popUpTo(Screen.MainAppNavigator.route) {
                                            inclusive = true
                                        }
                                        popUpTo(Screen.OnboardingNavigator.route){
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }



            IBankTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    bottomBar = {
                        if (state.startDestination == Screen.MainAppNavigator.route) {
                            //show bottom bar here
                            BottomBar(mainViewModel = mainViewModel, navController = navController)
                        }
                    }) { innerPadding ->

                   BankNavigation(
                       navController =  navController,
                       modifier = Modifier.padding(innerPadding),
                       mainState = state,
                       snackbarHostState = snackbarHostState
                   )
                }
            }
        }
    }
}