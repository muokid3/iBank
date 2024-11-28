package com.dm.berxley.ibank.search_feature.data.repositories

import android.util.Log
import com.dm.berxley.dictionary.core.domain.util.ApiErrorResponse
import com.dm.berxley.dictionary.core.domain.util.Error
import com.dm.berxley.dictionary.core.domain.util.Result
import com.dm.berxley.ibank.BuildConfig
import com.dm.berxley.ibank.core.data.local.Dao
import com.dm.berxley.ibank.core.data.local.entities.ExchangeRateEntity
import com.dm.berxley.ibank.core.data.remote.BankApi
import com.dm.berxley.ibank.core.domain.models.Currency
import com.dm.berxley.ibank.core.domain.models.ExchangeRate
import com.dm.berxley.ibank.search_feature.domain.repositories.CurrencyRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
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
        return flow {
            var localRates = dao.getExchangeRates().map { it.toExchangeRate() }
            emit(Result.Success(localRates))

            //get from APi
            try {
                val ratesFromApi = api.getExchangeRates(
                    apikey = BuildConfig.CURRENCY_API_KEY,
                    base_currency = baseCurrencyCode
                )

                //format to entity format only if currency exist in DB
                val formattedRates = ratesFromApi.data.filter {
                    val code = it.key
                    val currency = dao.getCurrencyDetails(code)
                    currency != null

                }.map { item ->
                    val code = item.key
                    val rate = item.value
                    val currency = dao.getCurrencyDetails(code)

                    ExchangeRateEntity(
                        base_currency_code = baseCurrencyCode,
                        currency_code = code,
                        currency_name = currency.name,
                        exchange_rate = rate,
                        id = null
                    )
                }

                //delete all from room
                dao.clearExchangeRates()
                //upsert to room
                dao.upsertExchangeRates(formattedRates)

                //get new rates and re-emit
                localRates = dao.getExchangeRates().map { it.toExchangeRate() }
                emit(Result.Success(localRates))

            } catch (e: HttpException) {
                emit(
                    Result.Error(
                        ApiErrorResponse(
                            message = e.message()
                        )
                    )
                )
            } catch (e: IOException) {
                val errorResp = ApiErrorResponse(
                    message = e.message ?: "Unable to reach server. ",
                    info = "Please check your internet connection and try again later."
                )
                emit(Result.Error(errorResp))
            }
        }
    }
}