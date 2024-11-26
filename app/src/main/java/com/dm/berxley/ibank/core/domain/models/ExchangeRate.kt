package com.dm.berxley.ibank.core.domain.models

data class ExchangeRate(
    val base_currency_code: String,
    val currency_code: String,
    val exchange_rate: Double,
    val buying_rate: Double? = null,
    val selling_rate: Double? = null,
)
