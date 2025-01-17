@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.birlik.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.birlik.R
import com.example.birlik.common.NavParam
import com.example.birlik.common.navigateTo
import com.example.birlik.data.remote.GameHistory
import com.example.birlik.data.remote.UserData
import com.example.birlik.presentation.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavController, userData: UserData, userViewModel: UserViewModel
) {

    Scaffold(topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        }, title = { Text(text = "${userData.username}") })
    }, content = {
        Column(Modifier.padding(it)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column() {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                modifier = Modifier.size(22.dp),
                                painter = painterResource(id = R.drawable.birlik_cash),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = "${userData.cash}", fontSize = 22.sp, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                modifier = Modifier.size(22.dp),
                                painter = painterResource(id = R.drawable.birlik_coin),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = "${userData.coin}", fontSize = 22.sp, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(22.dp),
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "",
                                tint = colorResource(
                                    id = R.color.yellow
                                )
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = "${userData.rating}", fontSize = 22.sp, color = Color.Black)
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(contentAlignment = Alignment.TopCenter) {
                                Image(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape),
                                    painter = rememberImagePainter(data = userData.image),
                                    contentDescription = ""
                                )
                                Image(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape),
                                    painter = rememberImagePainter(data = userData.skinSettings?.tablePicked?.image),
                                    contentDescription = ""
                                )
                            }
                            Image(
                                modifier = Modifier
                                    .size(60.dp),
                                painter = rememberImagePainter(data = userData.skinSettings?.cardBackPicked?.image),
                                contentDescription = "",
                                contentScale = ContentScale.Inside
                            )
                        }

                        Text(text = "${userData.name}", color = Color.Black)
                    }
                    Spacer(modifier = Modifier.size(32.dp))
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.light_grey)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.size(16.dp))
                        Text(text = "Tarixçə")
                        Spacer(modifier = Modifier.size(16.dp))
                        LazyColumn {
                            items(userData.gameHistory.orEmpty().reversed()) {
                                GameHistoryItem(
                                    gameHistory = it,
                                    userViewModel = userViewModel,
                                    navController = navController
                                )
                            }
                        }
                    }

                }

            }

        }

    }, bottomBar = {

    })
}

@SuppressLint("ResourceType")
@Composable
fun GameHistoryItem(
    gameHistory: GameHistory, userViewModel: UserViewModel, navController: NavController
) {
    val allUsers = userViewModel.allUsers.value
    val winnerUserData = allUsers.find { it.username == gameHistory.winner }
    val loserUserData = allUsers.find { it.username == gameHistory.loser }

    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .background(color = colorResource(id = gameHistory.background))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(text = gameHistory.title, color = Color.Black)
            if (gameHistory.winner != "" || gameHistory.loser != "") {
                Text(modifier = Modifier.clickable {
                    navController.popBackStack()
                    navigateTo(
                        navController,
                        "profile_screen",
                        NavParam("userData", winnerUserData ?: UserData())
                    )
                }, text = gameHistory.winner, color = Color.Black)
                Text(
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                        navigateTo(
                            navController,
                            "profile_screen",
                            NavParam("userData", loserUserData ?: UserData())
                        )
                    }, text = gameHistory.loser, color = colorResource(id = R.color.red)
                )
            }
        }
        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
            if (gameHistory.amount != "") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = gameHistory.amount,
                        color = if (gameHistory.amount.toInt() > 0) colorResource(
                            id = R.color.green
                        ) else colorResource(
                            id = R.color.dark_red
                        ),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Image(
                        modifier = Modifier.size(20.dp), painter = painterResource(
                            id = gameHistory.moneyIcon ?: R.drawable.background_blue
                        ), contentDescription = ""
                    )

                }
            }

            if (gameHistory.rating != "") {
                Spacer(modifier = Modifier.size(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = gameHistory.rating,
                        color = if (gameHistory.rating.toInt() > 0) colorResource(
                            id = R.color.green
                        ) else colorResource(
                            id = R.color.dark_red
                        ),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "",
                        tint = colorResource(
                            id = R.color.yellow
                        )
                    )
                }
            }

        }

    }
    Divider(color = Color.LightGray)
}