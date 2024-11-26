package com.dm.berxley.ibank.core.data.local.entities

import androidx.room.Entity
import com.dm.berxley.ibank.core.domain.models.ExchangeRate

@Entity
data class ExchangeRateEntity(
    val base_currency_code: String,
    val currency_code: String,
    val exchange_rate: Double,
){
    fun toExchangeRate(): ExchangeRate{
        return ExchangeRate(
            base_currency_code = base_currency_code,
            currency_code = currency_code,
            exchange_rate = exchange_rate,
            buying_rate = exchange_rate - (0.01 * exchange_rate),
            selling_rate = exchange_rate + (0.01 * exchange_rate)
        )
    }
}
