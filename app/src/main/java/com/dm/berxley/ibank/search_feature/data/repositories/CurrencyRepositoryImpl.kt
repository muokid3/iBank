package com.dm.berxley.ibank.search_feature.data.repositories

import android.util.Log
import com.dm.berxley.dictionary.core.domain.util.ApiErrorResponse
import com.dm.berxley.dictionary.core.domain.util.Error
import com.dm.berxley.dictionary.core.domain.util.Result
import com.dm.berxley.ibank.BuildConfig
import com.dm.berxley.ibank.core.data.local.Dao
import com.dm.berxley.ibank.core.data.remote.BankApi
import com.dm.berxley.ibank.core.domain.models.Currency
import com.dm.berxley.ibank.core.domain.models.ExchangeRate
import com.dm.berxley.ibank.search_feature.domain.repositories.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class CurrencyRepositoryImpl(
    private val api: BankApi,
    private val dao: Dao
) : CurrencyRepository {
    override suspend fun getCurrencies(): Flow<Result<List<Currency>, Error>> {
        return flow {
            var currenciesList = dao.getCurrencies().map { it.toCurrency() }
            emit(Result.Success(currenciesList))

            //get from API
            try {
                val response = api.getCurrencies(apikey = BuildConfig.CURRENCY_API_KEY)

                dao.upsertCurrencies(response.data.values.map { it.toCurrencyEntity() })
                currenciesList = dao.getCurrencies().map { it.toCurrency() }
                emit(Result.Success(currenciesList))

            } catch (e: HttpException) {
                emit(
                    Result.Error(
                        ApiErrorResponse(
                            message = e.message()
                        )
                    )
                )

            } catch (e: IOException) {
                Log.e("IOEXCEPTION", e.message ?: "No message")
                val errorResp = ApiErrorResponse(
                    message = e.message ?: "Unable to reach server, ",
                    info = "Please check your internet connection and try again later."
                )
                emit(Result.Error(errorResp))
            }


        }
    }

    override suspend fun getExchangeRates(baseCurrencyCode: String): Flow<Result<List<ExchangeRate>, Error>> {
        TODO("Not yet implemented")
    }
}