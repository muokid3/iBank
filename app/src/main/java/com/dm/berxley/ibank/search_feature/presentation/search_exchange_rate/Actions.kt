package com.dm.berxley.ibank.search_feature.presentation.search_exchange_rate

import com.dm.berxley.ibank.core.domain.models.Currency

sealed interface Actions {
    data class BaseCurrencyChange(val currency: Currency): Actions
}