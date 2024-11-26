package com.dm.berxley.ibank.core.domain.models

data class Currency(
    val code: String,
    val symbol: String,
    val symbol_native: String,
    val name: String,
    val name_plural: String,
    val type: String
)