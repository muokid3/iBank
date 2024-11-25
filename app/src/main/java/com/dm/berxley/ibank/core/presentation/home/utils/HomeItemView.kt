package com.dm.berxley.ibank.core.presentation.home.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dm.berxley.ibank.R

@Composable
fun HomeItemView(
    homeItem: HomeItem,
    onClick: (String) -> Unit
) {

    Box(modifier = Modifier
        .width(100.dp)
        .clickable { onClick(homeItem.route) }) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 16.dp
                )
            ) {
                Image(
                    painter = painterResource(homeItem.drawableId),
                    contentDescription = homeItem.title,
                    modifier = Modifier.size(50.dp)
                )

            }

            Text(text = homeItem.title)
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun ItemPrev() {
    HomeItemView(
        homeItem = HomeItem(
            drawableId = R.drawable.ic_withdraw,
            title = "Account and Card",
            route = "",
        )
    ) { }
}