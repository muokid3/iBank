package com.dm.berxley.ibank.search_feature.data.remote.dtos

import com.dm.berxley.ibank.core.data.local.entities.CurrencyEntity

data class CurrencyDto(
    val code: String,
    val decimal_digits: Int,
    val name: String,
    val name_plural: String,
    val rounding: Int,
    val symbol: String,
    val symbol_native: String,
    val type: String
) {
    fun toCurrencyEntity(): CurrencyEntity {
        return CurrencyEntity(
            code = code,
            symbol = symbol,
            symbol_native = symbol_native,
            name = name,
            name_plural = name_plural,
            type = type
        )
    }
}