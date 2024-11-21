package com.dm.berxley.ibank.auth_feature.presentatation.passwords

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.ibank.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(navController: NavController, modifier: Modifier = Modifier) {

    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var passwordConfirmation by rememberSaveable { mutableStateOf("") }
    var passwordConfirmationVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
                },
                title = {
                    Text(stringResource(R.string.change_password))
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(text = stringResource(R.string.set_a_new_password_that_you_can_easily_remember))
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                label = { Text(text = stringResource(R.string.password)) },
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Password,
                        contentDescription = stringResource(R.string.password)
                    )
                },
                trailingIcon = {
                    if (passwordVisible) {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = stringResource(
                                    R.string.toggle_password_visibility
                                )
                            )
                        }
                    } else {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = stringResource(
                                    R.string.toggle_password_visibility
                                )
                            )
                        }
                    }
                }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordConfirmation,
                label = { Text(text = stringResource(R.string.confirm_password)) },
                onValueChange = { passwordConfirmation = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                visualTransformation = if (passwordConfirmationVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Password,
                        contentDescription = stringResource(R.string.password)
                    )
                },
                trailingIcon = {
                    if (passwordConfirmationVisible) {
                        IconButton(onClick = {
                            passwordConfirmationVisible = !passwordConfirmationVisible
                        }) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = stringResource(
                                    R.string.toggle_password_visibility
                                )
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            passwordConfirmationVisible = !passwordConfirmationVisible
                        }) {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = stringResource(
                                    R.string.toggle_password_visibility
                                )
                            )
                        }
                    }
                }

            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(R.string.change_password))
            }
        }

    }

}


@Preview
@Composable
private fun ChangePassPrev() {
    ChangePasswordScreen(navController = rememberNavController())
}