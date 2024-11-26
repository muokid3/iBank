package com.dm.berxley.ibank.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dm.berxley.ibank.core.data.local.entities.CurrencyEntity

@Dao
interface Dao {

    @Upsert
    suspend fun upsertCurrencies(list: List<CurrencyEntity>)

    @Query("SELECT * FROM CurrencyEntity")
    suspend fun getCurrencies(): List<CurrencyEntity>
}