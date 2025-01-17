package com.example.birlik.common

import android.content.ContentResolver
import android.net.Uri
import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.birlik.presentation.viewmodel.AuthViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.firebase.Timestamp
import me.saket.telephoto.zoomable.ZoomableContentLocation
import me.saket.telephoto.zoomable.rememberZoomableState
import me.saket.telephoto.zoomable.zoomable
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.pow


fun myTime(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("k:mm", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}

data class NavParam(
    val name: String,
    val value: Parcelable
)

fun navigateTo(navController: NavController, dest: String, vararg params: NavParam) {
    for (param in params) {
        navController.currentBackStackEntry?.arguments?.putParcelable(param.name, param.value)
    }
    navController.navigate(dest) {
        popUpTo(dest)
        launchSingleTop = true
    }
}


@Composable
fun MyCheckSignedIn(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val alreadyLoggedIn = remember { mutableStateOf(false) }
    val signedIn = authViewModel.isSignedIn.value
    if (signedIn && !alreadyLoggedIn.value) {
        alreadyLoggedIn.value = true
        navController.navigate("main") {
            popUpTo(0)
        }
    }
}

@Composable
fun MyProgressBar() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.LightGray)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(color = Color.Blue)
    }
}


@Composable
fun ZoomableImg(url:String){

    val state = rememberZoomableState()
    val painter = rememberImagePainter(data = url)

    LaunchedEffect(painter.intrinsicSize) {
        state.setContentLocation(
            ZoomableContentLocation.scaledInsideAndCenterAligned(painter.intrinsicSize)
        )
    }
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            modifier = Modifier
                .fillMaxSize()
                .zoomable(state)
            ,
            painter = painter,
            contentDescription = "",
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
        )
    }

}


@Composable
fun VideoPlayer(url: String, autoPlay: Boolean) {
    val context = LocalContext.current
    val player = SimpleExoPlayer.Builder(context).build()
    val playerView = PlayerView(context)
    val mediaItem = MediaItem.fromUri(url)

    player.setMediaItem(mediaItem)
    playerView.player = player
    playerView.useController = autoPlay
    LaunchedEffect(player){
        player.prepare()
        player.playWhenReady = autoPlay
    }
    
    AndroidView(factory = {
        playerView
    })
}



fun isImageFile(uri: Uri, contentResolver: ContentResolver): Boolean {
    val mimeType = contentResolver.getType(uri)
    return mimeType?.startsWith("image/") == true
}

fun isVideoFile(uri: Uri, contentResolver: ContentResolver): Boolean {
    val mimeType = contentResolver.getType(uri)
    return mimeType?.startsWith("video/") == true
}

fun roundToNearestPowerOfTen(value: Int): Int {
    val magnitude = 10.0.pow(value.toString().length - 1)
    return (value / magnitude).toInt() * magnitude.toInt()
}

fun formatNumberWithK(value: Int): String {
    return when {
        value in 10000..999999 -> "${value / 1000}K"
        value >= 1000000 -> "${value / 1000000}M"
        else -> "$value"
    }
}

fun entryPriceCalculate(entryPrice: Long): Int {
    val sum = entryPrice - entryPrice * 0.2
    return sum.toInt()
}