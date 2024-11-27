package com.dm.berxley.ibank.search_feature.presentation.search_exchange_rate

sealed interface Events {
    data class OnError(val message: String): Events
}