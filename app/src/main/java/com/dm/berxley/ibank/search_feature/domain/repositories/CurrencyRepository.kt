package com.dm.berxley.ibank.search_feature.domain.repositories

import com.dm.berxley.dictionary.core.domain.util.Error
import com.dm.berxley.dictionary.core.domain.util.Result
import com.dm.berxley.ibank.core.domain.models.Currency
import com.dm.berxley.ibank.core.domain.models.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrencies(): Flow<Result<List<Currency>, Error>>
    suspend fun getExchangeRates(baseCurrencyCode: String): Flow<Result<List<ExchangeRate>, Error>>
}