package com.example.birlik.presentation.screen.auth

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
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.birlik.common.MyCheckSignedIn
import com.example.birlik.presentation.viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    MyCheckSignedIn(navController = navController, authViewModel = authViewModel)
    Box(modifier = Modifier.fillMaxSize()) {
//        Image(
//            contentScale = ContentScale.Crop,
//            painter = painterResource(id = R.drawable.cotam_background),
//            contentDescription = ""
//        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.padding(top = 40.dp)) {
            Text(
                text = "Co Tam \n messenger",
                fontFamily = FontFamily.Serif,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
        Column(Modifier.fillMaxWidth()) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp), onClick = {
                navController.navigate("sign_up")
            }) {
                Text(text = "Sign up", color = Color.White)
            }
            Spacer(modifier = Modifier.size(8.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Already have an account? ", color = Color.White)
                Text(text = "Sign in", modifier = Modifier.clickable {
                    navController.navigate("sign_in")
                }, textDecoration = TextDecoration.Underline, color = Color.White)
            }
        }


    }
}