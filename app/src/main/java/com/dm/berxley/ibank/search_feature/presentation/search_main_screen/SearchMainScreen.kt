package com.dm.berxley.ibank.search_feature.presentation.search_main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.ibank.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMainScreen(modifier: Modifier = Modifier, navController: NavController) {

    val items = listOf(
        SearchItem(
            title = stringResource(R.string.branch),
            description = stringResource(R.string.search_for_branch),
            route = "",
            imageId = R.drawable.ic_branch
        ),
        SearchItem(
            title = stringResource(R.string.interest_rate),
            description = stringResource(R.string.search_for_interest_rate),
            route = "",
            imageId = R.drawable.ic_interest_rate
        ),
        SearchItem(
            title = stringResource(R.string.exchange_rate),
            description = stringResource(R.string.search_for_exchange_rate),
            route = "",
            imageId = R.drawable.ic_exchange_rate
        ),
        SearchItem(
            title = stringResource(R.string.exchange),
            description = stringResource(R.string.exchange_amount_of_money),
            route = "",
            imageId = R.drawable.ic_exchange
        )
    )


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally),
                        text = stringResource(R.string.search)
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
                SearchItemView(searchItem = item) {
                    navController.navigate(item.route)
                }
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
private fun PrevSearch() {
    SearchMainScreen(navController = rememberNavController())
}