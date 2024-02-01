package com.myeong.prography.random

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by MyeongKi.
 */

@Composable
fun RandomRoute(viewModel: RandomViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    val intentInvoker = remember {
        viewModel.intentInvoker
    }
    val eventInvoker = remember {
        viewModel.eventInvoker
    }
    LaunchedEffect(true) {
        eventInvoker(RandomEvent.LoadRandomPhotos)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 28.dp)
            .padding(bottom = 44.dp)
    ) {
        PhotoCard()
    }
}

@Composable
fun PhotoCard() {
    Box(modifier = Modifier
        .background(Color.Black)) {

    }
}