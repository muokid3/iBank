package com.dm.berxley.ibank.core.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.dm.berxley.ibank.core.presentation.main.MainViewModel

@Composable
fun BottomBar(navController: NavHostController, mainViewModel: MainViewModel) {

    val items = listOf<BottomItem>(
        BottomItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomItem("Search", Icons.Filled.Search, Icons.Outlined.Search),
        BottomItem("Messaging", Icons.Filled.ChatBubble, Icons.Outlined.ChatBubble),
        BottomItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
    )

    val state = mainViewModel.mainState.collectAsStateWithLifecycle().value
    NavigationBar {
        items.forEachIndexed { index, bottomItem ->

            NavigationBarItem(
                selected = state.selectedBottomIndex == index,
                onClick = {
                    when (index) {
                        0 -> {
                            mainViewModel.setSelectedBottomIndex(index)
                            navController.navigate(Screen.HomeScreen.route)
                        }

                        1 -> {
                            mainViewModel.setSelectedBottomIndex(index)
                            navController.navigate(Screen.SearchMainScreen.route)
                        }

                        2 -> {
                            mainViewModel.setSelectedBottomIndex(index)
                            navController.navigate(Screen.MessagesListScreen.route)
                        }

                        3 -> {
                            mainViewModel.setSelectedBottomIndex(index)
                            navController.navigate(Screen.SettingsMainScreen.route)
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (state.selectedBottomIndex == index) bottomItem.selectedIcon else bottomItem.unselectedIcon,
                        contentDescription = bottomItem.title
                    )
                },
                label = { Text(text = bottomItem.title) },
                alwaysShowLabel = false
            )

        }
    }

}

data class BottomItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)