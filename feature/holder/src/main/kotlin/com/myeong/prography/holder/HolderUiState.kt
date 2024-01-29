package com.myeong.prography.holder

import kotlinx.collections.immutable.ImmutableList

/**
 * Created by MyeongKi.
 */
data class HolderUiState(
    val holderComponent: ImmutableList<HolderComponent>,
    val selectedComponent: HolderComponent
)