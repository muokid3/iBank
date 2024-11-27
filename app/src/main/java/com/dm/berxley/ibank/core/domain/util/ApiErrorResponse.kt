package com.dm.berxley.dictionary.core.domain.util

data class ApiErrorResponse(
    val message: String? = null,
    val info: String? = null,
) : Error
