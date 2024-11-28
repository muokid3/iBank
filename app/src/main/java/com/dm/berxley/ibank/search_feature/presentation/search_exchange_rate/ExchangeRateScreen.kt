package com.dm.berxley.ibank.search_feature.presentation.search_exchange_rate

import android.icu.text.DecimalFormat
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.ibank.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExchangeRateScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: State,
    onAction: (Actions) -> Unit
) {

    var dropdownExpanded by remember { mutableStateOf(false) }
    var baseCurrency by rememberSaveable { mutableStateOf("Select Base Currency") }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        text = stringResource(R.string.exchange_rate)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
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
                    start = 12.dp,
                    end = 12.dp
                )
        ) {

            //show dropdown
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),
                expanded = dropdownExpanded,
                onExpandedChange = {
                    dropdownExpanded = !dropdownExpanded
                }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    label = { Text(text = stringResource(R.string.base_currency)) },
                    value = baseCurrency,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    onValueChange = {}

                )

                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    state.currencies.forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(text = "${currency.name} (${currency.symbol})") },
                            onClick = {
                                baseCurrency = "${currency.name} (${currency.symbol})"
                                onAction(Actions.BaseCurrencyChange(currency))
                                dropdownExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )

                    }
                }


            }

            if (state.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { CircularProgressIndicator() }
            } else {
                LazyColumn {
                    stickyHeader{
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)) {
                            Text(
                                modifier = Modifier.weight(0.6f),
                                fontWeight = FontWeight.Bold,
                                text = stringResource(R.string.currency)
                            )
                            Text(
                                modifier = Modifier.weight(0.2f),
                                textAlign = TextAlign.End,
                                fontWeight = FontWeight.Bold,
                                text = stringResource(R.string.buying)
                            )

                            Text(
                                modifier = Modifier.weight(0.2f),
                                textAlign = TextAlign.End,
                                fontWeight = FontWeight.Bold,
                                text = stringResource(R.string.selling)
                            )
                        }
                        HorizontalDivider()
                    }
                    itemsIndexed(state.exchangeRates) { _, item ->
                        ItemExchangeRate(exchangeRate = item)
                    }
                }

            }
        }
    }

}


@Preview
@Composable
private fun ExchangePrev() {
    ExchangeRateScreen(
        state = State(
            isLoading = false,
            currencies = emptyList(),
            selectedBaseCurrency = null
        ),
        navController = rememberNavController(),
        onAction = {}
    )
}
