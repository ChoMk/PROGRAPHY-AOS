package com.myeong.prography.holder

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myeong.prography.ui.R
import com.myeong.prography.holder.sheet.HolderSheetType
import com.myeong.prography.ui.event.SheetEvent
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
@Composable
fun HolderRoute(
    visibleSheetFlow: Flow<SheetEvent>,
    photosContent: @Composable () -> Unit,
    randomContent: @Composable () -> Unit,
    detailSheet: @Composable (photoId: String) -> Unit,
) {
    val viewModel: HolderViewModel = viewModel(
        factory = HolderViewModel.provideFactory(visibleSheetFlow)
    )
    val uiState: HolderUiState = viewModel.uiState.collectAsState().value
    val intentInvoker = remember {
        viewModel.intentInvoker
    }
    val eventInvoker = remember {
        viewModel.eventInvoker
    }
    Box {
        HolderScreen(
            uiState = uiState,
            photosContent = photosContent,
            randomContent = randomContent,
            intentInvoker = intentInvoker
        )
        SheetScreen(
            holderSheetType = uiState.holderSheetType,
            detailSheet = detailSheet
        )
    }
    BackHandler {
        if (uiState.holderSheetType != HolderSheetType.None) {
            eventInvoker(HolderEvent.HideSheet)
        }
    }
}

@Composable
fun SheetScreen(
    holderSheetType: HolderSheetType,
    detailSheet: @Composable (photoId: String) -> Unit,
) {
    when (holderSheetType) {
        is HolderSheetType.None -> Unit
        is HolderSheetType.Detail -> {
            detailSheet(holderSheetType.photoId)
        }
    }
}

@Composable
fun HolderScreen(
    uiState: HolderUiState,
    photosContent: @Composable () -> Unit,
    randomContent: @Composable () -> Unit,
    intentInvoker: (HolderIntent) -> Unit
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
            selectedComponent = uiState.selectedComponent,
            onClickPhotos = {
                intentInvoker(HolderIntent.ClickPhotos)
            },
            onClickRandom = {
                intentInvoker(HolderIntent.ClickRandom)
            }
        )
    }
}

@Preview
@Composable
fun HolderScreenPreview() {
    HolderScreen(
        uiState = HolderUiState(
            selectedComponent = HolderComponent.PHOTOS,
            holderSheetType = HolderSheetType.None
        ),
        photosContent = {
            Text(text = "test photo screen")
        },
        randomContent = {
            Text(text = "test random screen")
        },
        intentInvoker = {

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
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HolderComponent.PHOTOS.name) {
        composable(HolderComponent.PHOTOS.name) {
            photosContent()
        }
        composable(HolderComponent.RANDOM.name) {
            randomContent()
        }
    }
    LaunchedEffect(selectedComponent) {
        val result = navController.popBackStack(selectedComponent.name, inclusive = false)
        if (result.not()) {
            navController.navigate(selectedComponent.name) {
                launchSingleTop = true
            }
        }
    }

}

@Composable
private fun HolderBottom(
    selectedComponent: HolderComponent,
    onClickPhotos: () -> Unit,
    onClickRandom: () -> Unit
) {
    fun HolderComponent.getIconAlpha(selectedComponent: HolderComponent): Float {
        return if (this == selectedComponent) {
            1.0f
        } else {
            0.4f
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(colorResource(id = R.color.bottom_background)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 83.dp, alignment = Alignment.CenterHorizontally)
    ) {
        Image(
            painter = painterResource(id = R.drawable.house),
            contentDescription = "photos",
            modifier = Modifier
                .wrapContentWidth()
                .size(26.dp)
                .clickable(onClick = onClickPhotos),
            alpha = HolderComponent.PHOTOS.getIconAlpha(selectedComponent)
        )
        Image(
            painter = painterResource(id = R.drawable.cards),
            contentDescription = "random",
            modifier = Modifier
                .wrapContentWidth()
                .height(26.dp)
                .clickable(onClick = onClickRandom),
            alpha = HolderComponent.RANDOM.getIconAlpha(selectedComponent)
        )
    }
}
