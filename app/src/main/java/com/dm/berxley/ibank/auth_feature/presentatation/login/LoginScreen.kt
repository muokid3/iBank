package com.dm.berxley.ibank.auth_feature.presentatation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.ibank.R
import com.dm.berxley.ibank.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        text = stringResource(R.string.login)
                    )
                },

                )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .verticalScroll(state = rememberScrollState())
        ) {
            Text(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                text = stringResource(R.string.welcome_back)
            )

            Text(text = stringResource(R.string.hello_there_sign_in_to_continue))

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                painter = painterResource(id = R.drawable.login_illustration),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Mail, contentDescription = null)
                },
                label = {
                    Text(text = stringResource(R.string.e_mail))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                isError = !state.emailError.isNullOrEmpty()
            )
            state.emailError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Password, contentDescription = null)
                },
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = stringResource(R.string.show_password)
                            )
                        }
                    } else {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = stringResource(R.string.hide_password)
                            )
                        }
                    }

                },
                label = {
                    Text(text = stringResource(R.string.password))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                isError = !state.passwordError.isNullOrEmpty()
            )

            state.passwordError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.align(Alignment.End),
                onClick = {

                }) {
                Text(text = stringResource(R.string.forgot_password))
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }

            }

            Button(
                onClick = {
                    onAction(LoginAction.EmailPasswordLogin(email = email, password = password))
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.sign_in))
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate(Screen.SignUpScreen.route) {
                        launchSingleTop = true
                    }
                }) {
                Text(text = stringResource(R.string.don_t_have_an_account_sign_up))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.or)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    onAction(LoginAction.GoogleLogin)
                },
                shape = RoundedCornerShape(8.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(14.dp))
                    Image(
                        painterResource(id = R.drawable.icons8_google),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = stringResource(R.string.continue_with_google))
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PrevLogin() {
    LoginScreen(navController = rememberNavController(), onAction = {}, state = LoginState())

}