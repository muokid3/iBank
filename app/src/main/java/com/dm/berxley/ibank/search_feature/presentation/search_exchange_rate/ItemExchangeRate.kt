package com.dm.berxley.ibank.search_feature.presentation.search_exchange_rate

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dm.berxley.ibank.core.domain.models.ExchangeRate

@Composable
fun ItemExchangeRate(exchangeRate: ExchangeRate) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(
            modifier = Modifier.weight(0.6f),
            text = "${exchangeRate.currency_name}(${exchangeRate.currency_code})"
        )
        Text(
            modifier = Modifier.weight(0.2f),
            textAlign = TextAlign.End,
            text = DecimalFormat("#.##").format(exchangeRate.buying_rate)
        )

        Text(
            modifier = Modifier.weight(0.2f),
            textAlign = TextAlign.End,
            text =  DecimalFormat("#.##").format(exchangeRate.selling_rate)
        )

    }

}


@Preview(showBackground = true)
@Composable
private fun ItemPrev() {
    ItemExchangeRate(
        exchangeRate = ExchangeRate(
            base_currency_code = "USD",
            currency_code = "JPY",
            currency_name = "Japanese Yen",
            exchange_rate = 123.45,
            buying_rate = 122.45,
            selling_rate = 124.45
        )
    )
}