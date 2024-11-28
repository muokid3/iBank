package com.dm.berxley.ibank.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.dm.berxley.ibank.core.data.local.entities.CurrencyEntity
import com.dm.berxley.ibank.core.data.local.entities.ExchangeRateEntity

@Dao
interface Dao {

    @Upsert
    suspend fun upsertCurrencies(list: List<CurrencyEntity>)

    @Query("SELECT * FROM CurrencyEntity")
    suspend fun getCurrencies(): List<CurrencyEntity>

    @Query("SELECT * FROM CurrencyEntity WHERE code = :code")
    suspend fun getCurrencyDetails(code: String): CurrencyEntity

    @Query("SELECT * FROM ExchangeRateEntity")
    suspend fun getExchangeRates(): List<ExchangeRateEntity>

    @Query("DELETE FROM ExchangeRateEntity")
    suspend fun clearExchangeRates()

    @Upsert
    suspend fun upsertExchangeRates(list: List<ExchangeRateEntity>)
}