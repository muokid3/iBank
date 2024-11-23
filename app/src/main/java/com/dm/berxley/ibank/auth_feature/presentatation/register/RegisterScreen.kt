package com.dm.berxley.ibank.auth_feature.presentatation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.ibank.BuildConfig
import com.dm.berxley.ibank.R
import com.dm.berxley.ibank.core.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    registerState: RegisterState,
    onAction: (RegisterAction) -> Unit
) {

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var passwordConfirmation by rememberSaveable { mutableStateOf("") }
    var passwordConfirmationVisible by rememberSaveable { mutableStateOf(false) }

    val uriHandler = LocalUriHandler.current


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.go_back
                            )
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        text = stringResource(R.string.sign_up)
                    )
                },

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
                .verticalScroll(state = rememberScrollState())
                .imePadding()
        ) {

            Text(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                text = stringResource(R.string.join_us_today)
            )

            Text(text = stringResource(R.string.hello_there_create_a_new_account))

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                painter = painterResource(id = R.drawable.signup_illustration),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = null)
                },
                label = {
                    Text(text = stringResource(R.string.full_name))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                isError = !registerState.nameError.isNullOrEmpty(),
            )
            registerState.nameError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

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
                isError = !registerState.emailError.isNullOrEmpty(),
            )
            registerState.emailError?.let {
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
                    if (passwordVisible) {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = stringResource(R.string.show_password)
                            )
                        }
                    } else {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
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
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                isError = !registerState.passwordError.isNullOrEmpty(),
            )
            registerState.passwordError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }


            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordConfirmation,
                onValueChange = { passwordConfirmation = it },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Password, contentDescription = null)
                },
                trailingIcon = {
                    if (passwordConfirmationVisible) {
                        IconButton(onClick = {
                            passwordConfirmationVisible = !passwordConfirmationVisible
                        }) {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = stringResource(R.string.show_password)
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            passwordConfirmationVisible = !passwordConfirmationVisible
                        }) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = stringResource(R.string.hide_password)
                            )
                        }
                    }

                },
                label = {
                    Text(text = stringResource(R.string.confirm_password))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                visualTransformation = if (passwordConfirmationVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                isError = !registerState.conformPasswordError.isNullOrEmpty(),
            )
            registerState.conformPasswordError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(
                    checked = registerState.termsAccepted,
                    onCheckedChange = {
                        onAction(RegisterAction.TermsToggle(it))
                    })

                val annotatedString = buildAnnotatedString {
                    append(stringResource(R.string.by_creating_an_account_i_agree_to_the))

                    withLink(
                        LinkAnnotation.Url(
                            BuildConfig.TERMS_URL,
                            TextLinkStyles(style = SpanStyle(color = Color.Blue))
                        ) {
                            val url = (it as LinkAnnotation.Url).url
                            uriHandler.openUri(url)
                        }
                    ) {
                        append(stringResource(R.string.terms_and_conditions))
                    }
                }

                Text(text = annotatedString)
            }

            if (registerState.isLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }

            }

            Button(
                onClick = {
                    onAction(
                        RegisterAction.Register(
                            name = name,
                            email = email,
                            password = password,
                            confirmPassword = passwordConfirmation
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(R.string.sign_up))
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    navController.navigate(Screen.LoginScreen.route) {
                        launchSingleTop = true
                    }
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(R.string.already_have_an_account))
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RegisterPrev() {
    RegisterScreen(
        navController = rememberNavController(),
        registerState = RegisterState(),
        onAction = {})
}