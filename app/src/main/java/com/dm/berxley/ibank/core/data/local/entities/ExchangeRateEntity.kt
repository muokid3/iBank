package com.dm.berxley.ibank.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dm.berxley.ibank.core.domain.models.ExchangeRate

@Entity
data class ExchangeRateEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val base_currency_code: String,
    val currency_code: String,
    val currency_name: String,
    val exchange_rate: Double,
) {
    fun toExchangeRate(): ExchangeRate {
        return ExchangeRate(
            base_currency_code = base_currency_code,
            currency_code = currency_code,
            currency_name = currency_name,
            exchange_rate = exchange_rate,
            buying_rate = exchange_rate - (0.01 * exchange_rate),
            selling_rate = exchange_rate + (0.01 * exchange_rate)
        )
    }
}
