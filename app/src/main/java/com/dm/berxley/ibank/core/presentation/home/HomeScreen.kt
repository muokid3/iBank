package com.dm.berxley.ibank.core.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.dm.berxley.ibank.R
import com.dm.berxley.ibank.core.presentation.home.utils.HomeItem
import com.dm.berxley.ibank.core.presentation.home.utils.HomeItemView
import com.dm.berxley.ibank.ui.theme.onPrimaryDark
import com.dm.berxley.ibank.ui.theme.primaryContainerDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    state: HomeState
) {

    val homeItems = listOf(
        HomeItem(
            drawableId = R.drawable.ic_wallet,
            title = stringResource(R.string.account_and_card),
            route = "",
        ),
        HomeItem(
            drawableId = R.drawable.ic_transfer,
            title = stringResource(R.string.transfer),
            route = "",
        ),

        HomeItem(
            drawableId = R.drawable.ic_withdraw,
            title = stringResource(R.string.withdraw),
            route = "",
        ),
        HomeItem(
            drawableId = R.drawable.ic_mobile_prepaid,
            title = stringResource(R.string.mobile),
            route = "",
        ),
        HomeItem(
            drawableId = R.drawable.ic_pay_bill,
            title = stringResource(R.string.pay_bill),
            route = "",
        ),
        HomeItem(
            drawableId = R.drawable.ic_save,
            title = stringResource(R.string.save),
            route = "",
        ),

        HomeItem(
            drawableId = R.drawable.ic_credit_card,
            title = stringResource(R.string.credit),
            route = "",
        ),
        HomeItem(
            drawableId = R.drawable.ic_transactions,
            title = stringResource(R.string.transaction),
            route = "",
        ),

        HomeItem(
            drawableId = R.drawable.ic_contacts,
            title = stringResource(R.string.bene),
            route = "",
        ),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape),
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(state.photoUrl)
                                .build(),
                            placeholder = painterResource(R.drawable.profile),
                            contentDescription = state.name,
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.profile)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Hi, ${state.name}")
                    }

                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = stringResource(R.string.notifications)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .verticalScroll(state = rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            Box {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .padding(horizontal = 32.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 12.dp
                    ),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(10.dp)
                ) {}

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 12.dp
                    ),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error),
                    shape = RoundedCornerShape(10.dp)
                ) {}


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),
                    onClick = {},
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 12.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    shape = RoundedCornerShape(10.dp)

                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        onPrimaryDark,
                                        primaryContainerDark,
                                        MaterialTheme.colorScheme.secondary,
                                        MaterialTheme.colorScheme.tertiary
                                    )
                                )
                            )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = state.name,
                                fontSize = 24.sp
                            )
                            Spacer(Modifier.height(36.dp))
                            Text(
                                text = stringResource(R.string.amazon_platinum),
                                fontSize = 16.sp
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.card_number),
                                fontSize = 20.sp
                            )
                            Spacer(Modifier.height(16.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Max),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "$ 123,546.34",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Image(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(75.dp),
                                    painter = painterResource(R.drawable.ic_mastercard),
                                    contentDescription = stringResource(R.string.card_processor)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
//
//            LazyHorizontalGrid(
//                rows = GridCells.Adaptive(100.dp),
//                contentPadding = PaddingValues(horizontal = 16.dp)
//
//            ) {
//                items(homeItems.size) { index ->
//                    val homeItem = homeItems[index]
//                    HomeItemView(homeItem = homeItem) {
//                        navController.navigate(homeItem.route)
//                    }
//                }
//            }

        }
    }

}


@Preview(showBackground = true)
@Composable
private fun HomePrev() {
    HomeScreen(
        navController = rememberNavController(),
        state = HomeState(name = "John Doe", email = "johdoe@mail.com", photoUrl = null)
    )
}