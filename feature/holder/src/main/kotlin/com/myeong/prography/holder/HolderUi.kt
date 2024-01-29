package com.myeong.prography.holder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myeong.prography_aos.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * Created by MyeongKi.
 */
@Composable
fun HolderRoute(
    photosContent: @Composable () -> Unit,
    randomContent: @Composable () -> Unit
) {
    val viewModel: HolderViewModel = viewModel()
    val uiState: HolderUiState = viewModel.uiState.collectAsState().value
    HolderScreen(
        uiState = uiState,
        photosContent = photosContent,
        randomContent = randomContent,
    )
}

@Composable
fun HolderScreen(
    uiState: HolderUiState,
    photosContent: @Composable () -> Unit,
    randomContent: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HolderHeader()
        Box(modifier = Modifier.weight(1f)) {
            HolderBody(
                selectedComponent = uiState.selectedComponent,
                photosContent = photosContent,
                randomContent = randomContent
            )
        }
        HolderBottom(
            holderComponent = uiState.holderComponent,
            selectedComponent = uiState.selectedComponent
        )
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Preview
@Composable
fun HolderScreenPreview() {
    HolderScreen(
        uiState = HolderUiState(
            holderComponent = HolderComponent.entries.toImmutableList(),
            selectedComponent = HolderComponent.PHOTOS,
        ),
        photosContent = {
            Text(text = "test photo screen")
        },
        randomContent = {
            Text(text = "test random screen")
        }
    )
}

@Composable
private fun HolderHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .wrapContentWidth()
                .height(24.dp)
                .align(Alignment.Center),
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            color = colorResource(id = R.color.divider),
            thickness = 1.dp,
        )
    }
}

@Composable
private fun HolderBody(
    selectedComponent: HolderComponent,
    photosContent: @Composable () -> Unit,
    randomContent: @Composable () -> Unit
) {
    when (selectedComponent) {
        HolderComponent.PHOTOS -> {
            photosContent()
        }

        HolderComponent.RANDOM -> {
            randomContent()
        }
    }
}

@Composable
private fun HolderBottom(
    holderComponent: ImmutableList<HolderComponent>,
    selectedComponent: HolderComponent
) {

}
