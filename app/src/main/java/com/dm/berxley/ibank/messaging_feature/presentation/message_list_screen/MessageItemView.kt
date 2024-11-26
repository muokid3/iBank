package com.dm.berxley.ibank.messaging_feature.presentation.message_list_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.dm.berxley.ibank.R

@Composable
fun MessageItemView(messageItem: MessageItem, onClick: (action: String) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(messageItem.route)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {

            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current).data(messageItem.senderIconUrl)
                    .build(),
                contentDescription = messageItem.sender,
                placeholder = painterResource(R.drawable.chatting),
                error = painterResource(R.drawable.chatting)
            )

            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(0.7f)
            ) {

                Text(text = messageItem.sender, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = messageItem.lastMessage,
                    fontSize = 16.sp,
                    fontStyle = MaterialTheme.typography.displayMedium.fontStyle
                )
            }
        }

    }


}

data class MessageItem(
    val sender: String, val lastMessage: String, val route: String, val senderIconUrl: String
)

@Preview(showBackground = false)
@Composable
private fun ItemPrev() {
    MessageItemView(
        messageItem = MessageItem(
            sender = "Bank of America",
            lastMessage = "Your authorization code is 12345",
            route = "itemRoute",
            senderIconUrl = ""
        )
    ) { }
}