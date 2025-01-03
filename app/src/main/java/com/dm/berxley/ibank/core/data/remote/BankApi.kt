package com.dm.berxley.ibank.core.data.remote

import com.dm.berxley.ibank.search_feature.data.remote.dtos.CurrenciesDto
import com.dm.berxley.ibank.search_feature.data.remote.dtos.ExchangeRateDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BankApi {

    //api calls here. GET POST etc
    @GET("currencies")
    @Headers("Content-Type: application/json")
    suspend fun getCurrencies(
        @Query("apikey") apikey: String,
    ): CurrenciesDto

    @GET("latest")
    @Headers("Content-Type: application/json")
    suspend fun getExchangeRates(
        @Query("apikey") apikey: String,
        @Query("base_currency") base_currency: String,
    ): ExchangeRateDto


}