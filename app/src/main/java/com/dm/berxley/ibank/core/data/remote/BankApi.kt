package com.dm.berxley.ibank.core.data.remote

import com.dm.berxley.ibank.search_feature.data.remote.dtos.CurrenciesDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BankApi {

    //api calls here. GET POST etc
    @GET("currencies")
    @Headers("Content-Type: application/json")
    suspend fun getCurrencies(@Query("apikey") apikey: String): CurrenciesDto


}