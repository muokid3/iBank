package com.dm.berxley.ibank.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dm.berxley.ibank.core.domain.models.Currency

@Entity
data class CurrencyEntity(
    @PrimaryKey
    val code: String,
    val symbol: String,
    val symbol_native: String,
    val name: String,
    val name_plural: String,
    val type: String
) {
    fun toCurrency(): Currency {
        return Currency(
            code = code,
            symbol = symbol,
            symbol_native = symbol_native,
            name = name,
            name_plural = name_plural,
            type = type
        )
    }
}
