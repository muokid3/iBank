package com.dm.berxley.ibank.core.presentation.home.utils

import androidx.annotation.DrawableRes

data class HomeItem(
    @DrawableRes val drawableId: Int,
    val title: String,
    val route: String,
)
