package com.myeong.prography.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

/**
 * Created by MyeongKi.
 */

@Composable
fun DefaultIndicator(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .size(20.dp),
        color = colorResource(id = R.color.loading),
    )
}