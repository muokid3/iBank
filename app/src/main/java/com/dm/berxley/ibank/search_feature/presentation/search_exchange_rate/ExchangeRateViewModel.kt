package com.dm.berxley.ibank.search_feature.presentation.search_exchange_rate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.berxley.dictionary.core.domain.util.ApiErrorResponse
import com.dm.berxley.dictionary.core.domain.util.Result
import com.dm.berxley.ibank.search_feature.domain.repositories.CurrencyRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ExchangeRateViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _events = Channel<Events>()
    val events = _events.receiveAsFlow()


    init {
        _state.update {
            it.copy(isLoading = true)
        }
        loadCurrencies()
        loadExchangeRates()
    }

    fun onAction(actions: Actions) {
        when (actions) {
            is Actions.BaseCurrencyChange -> {
                _state.update {
                    it.copy(isLoading = true, selectedBaseCurrency = actions.currency)
                }
                loadExchangeRates(actions.currency.code)
            }
        }
    }

    private fun loadCurrencies() {
        viewModelScope.launch {
            currencyRepository.getCurrencies().collect { result ->
                when (result) {
                    is Result.Error -> {
                        val errorMessage = result.error as ApiErrorResponse

                        _events.send(Events.OnError(message = errorMessage.message + errorMessage.info))
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(currencies = result.data)
                        }
                    }
                }
            }
        }
    }

    private fun loadExchangeRates(baseCurrency: String = "USD") {
        //call repository here
        viewModelScope.launch {
            currencyRepository.getExchangeRates(baseCurrencyCode = baseCurrency).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _state.update {
                            it.copy(isLoading = false)
                        }
                        val errorMessage = result.error as ApiErrorResponse
                        _events.send(Events.OnError(message = errorMessage.message + errorMessage.info))
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(isLoading = false, exchangeRates = result.data)
                        }
                    }
                }
            }
        }


    }
}