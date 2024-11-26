package com.dm.berxley.ibank.messaging_feature.presentation.message_list_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.ibank.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageListScreen(modifier: Modifier = Modifier, navController: NavController) {


    val items = listOf(
        MessageItem(
            sender = "Bank of America",
            lastMessage = "Your authorization code is 12345",
            route = "itemRoute",
            senderIconUrl = ""
        ),
        MessageItem(
            sender = "Bank of America",
            lastMessage = "Your authorization code is 12345",
            route = "itemRoute",
            senderIconUrl = ""
        ),
        MessageItem(
            sender = "Bank of America",
            lastMessage = "Your authorization code is 12345",
            route = "itemRoute",
            senderIconUrl = ""
        ),
        MessageItem(
            sender = "Bank of America",
            lastMessage = "Your authorization code is 12345",
            route = "itemRoute",
            senderIconUrl = ""
        )
    )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = stringResource(R.string.compose)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = stringResource(R.string.compose), fontSize = 16.sp)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally),
                        text = stringResource(R.string.messaging)
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 12.dp,
                    end = 12.dp
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(12.dp)) }
            items(items.size) { index ->
                val item = items[index]
                MessageItemView(messageItem = item) {
                    navController.navigate(item.route)
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
private fun MessageListScreenPrev() {
    MessageListScreen(navController = rememberNavController())
}