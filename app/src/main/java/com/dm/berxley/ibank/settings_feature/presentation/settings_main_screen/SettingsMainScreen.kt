package com.dm.berxley.ibank.settings_feature.presentation.settings_main_screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.dm.berxley.ibank.R
import com.dm.berxley.ibank.core.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsMainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    var openDialog by rememberSaveable { mutableStateOf(false) }

    val items = listOf(
        SettingItem(
            stringResource(R.string.password),
            Icons.AutoMirrored.Filled.ArrowForwardIos,
            Screen.PasswordScreen
        ),
        SettingItem(
            stringResource(R.string.touch_id),
            Icons.AutoMirrored.Filled.ArrowForwardIos,
            Screen.TouchIDScreen
        ),
        SettingItem(
            stringResource(R.string.languages),
            Icons.AutoMirrored.Filled.ArrowForwardIos,
            Screen.LanguagesScreen
        ),
        SettingItem(
            stringResource(R.string.app_info),
            Icons.AutoMirrored.Filled.ArrowForwardIos,
            Screen.AppInformationScreen
        ),
        SettingItem(
            stringResource(R.string.customer_care),
            Icons.AutoMirrored.Filled.ArrowForwardIos,
            Screen.CustomerCareScreen
        ),
        SettingItem(stringResource(R.string.logout), Icons.Default.PowerSettingsNew, null),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        text = stringResource(R.string.settings)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
        ) {
            if (openDialog) {
                ShowConfirmDialog(
                    onDismissRequest = { openDialog = false },
                    onConfirmation = {
                        openDialog = false
                        onAction(SettingsAction.Logout)
                    },
                    dialogText = stringResource(R.string.are_you_sure_you_want_to_log_out_of_the_app),
                    dialogTitle = stringResource(R.string.confirm_logout),
                    icon = Icons.AutoMirrored.Filled.Logout
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    model = ImageRequest
                        .Builder(context = LocalContext.current)
                        .data(state.user?.photoUrl.toString())
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.profile),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    error = painterResource(R.drawable.profile),
                    onError = { error ->
                        Log.e(
                            "ImageLoadingError",
                            "Error loading image: ${error.result.request.data}"
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
                state.user?.displayName?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,

                        )
                }
            }

            items.forEachIndexed { index, settingItem ->
                SettingItemDisplay(settingItem = settingItem) {
                    if (settingItem.link != null) {
                        onAction(SettingsAction.Navigate(settingItem.link.route))
                    } else {
                        openDialog = true
                    }
                }
                if (index < items.size - 1) {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }


}

data class SettingItem(
    val label: String,
    val icon: ImageVector,
    val link: Screen? = null
)

@Composable
fun SettingItemDisplay(
    settingItem: SettingItem,
    onClick: (Screen?) -> Unit
) {

    Box(modifier = Modifier.clickable {
        onClick(settingItem.link)
    }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = settingItem.label)
            Icon(imageVector = settingItem.icon, contentDescription = settingItem.label)
        }
    }

}

@Composable
fun ShowConfirmDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = dialogTitle)
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingsPrev() {
    SettingsMainScreen(
        navController = rememberNavController(),
        state = SettingsState(user = null),
        onAction = { }
    )
}