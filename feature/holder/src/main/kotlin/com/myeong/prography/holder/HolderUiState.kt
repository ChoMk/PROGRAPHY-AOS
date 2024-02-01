package com.myeong.prography.holder

import com.myeong.prography.holder.sheet.HolderSheetType

/**
 * Created by MyeongKi.
 */
data class HolderUiState(
    val selectedComponent: HolderComponent,
    val holderSheetType: HolderSheetType
)