package com.dm.berxley.ibank.search_feature.presentation.search_exchange_rate

import com.dm.berxley.ibank.core.domain.models.Currency
import com.dm.berxley.ibank.core.domain.models.ExchangeRate

data class State(
    val isLoading: Boolean = false,
    val exchangeRates: List<ExchangeRate> = emptyList(),
    val currencies: List<Currency> = emptyList(),
    val selectedBaseCurrency: Currency? = null
)
