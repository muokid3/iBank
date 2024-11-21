package com.dm.berxley.ibank.auth_feature.presentatation.passwords

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.ibank.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordCodeScreen(navController: NavController, modifier: Modifier = Modifier) {

    var code by rememberSaveable { mutableStateOf("") }
    var recipient by rememberSaveable { mutableStateOf("") }


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
                    Text(stringResource(R.string.verify_code))
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

            Text(text = stringResource(R.string.type_your_phone_number_e_mail))
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    modifier = Modifier.weight(0.7f).padding(4.dp),
                    value = code,
                    label = { Text(text = stringResource(R.string.enter_code)) },
                    onValueChange = { code = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    )
                )

                OutlinedButton (
                    onClick = {},
                    modifier = Modifier.weight(0.3f).padding(horizontal = 4.dp, vertical = 10.dp).fillMaxHeight(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = stringResource(R.string.resend))
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(
                R.string.we_sent_you_a_code_to_to_verify_your_account,
                recipient
            ))

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(R.string.this_code_will_expire_in_10_minutes_after_this_message_if_you_did_not_get_the_code_click_the_resend_button_to_resend_it))

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(R.string.continue_))
            }
        }

    }

}


@Preview
@Composable
private fun CodePrev() {
    PasswordCodeScreen(navController = rememberNavController())
}