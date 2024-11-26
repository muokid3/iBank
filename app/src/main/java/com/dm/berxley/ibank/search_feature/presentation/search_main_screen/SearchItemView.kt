package com.dm.berxley.ibank.search_feature.presentation.search_main_screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dm.berxley.ibank.R

@Composable
fun SearchItemView(searchItem: SearchItem, onClick: (action: String) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(searchItem.title)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Column(
                modifier = Modifier.weight(0.7f)
            ) {

                Text(text = searchItem.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = searchItem.description,
                    fontSize = 16.sp,
                    fontStyle = MaterialTheme.typography.displayMedium.fontStyle
                )
            }
            Image(
                modifier = Modifier.weight(0.3f),
                painter = painterResource(searchItem.imageId),
                contentDescription = searchItem.title
            )
        }

    }

}

data class SearchItem(
    val title: String, val description: String, val route: String, @DrawableRes val imageId: Int
)

@Preview(showBackground = false)
@Composable
private fun ItemPrev() {
    SearchItemView(
        searchItem = SearchItem(
            title = "Branch",
            description = "Search for branch",
            route = "itemRoute",
            imageId = R.drawable.ic_branch
        )
    ) { }
}