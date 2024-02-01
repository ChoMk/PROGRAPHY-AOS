package com.myeong.prography.holder.sheet

/**
 * Created by MyeongKi.
 */
sealed interface HolderSheetType {
    data object None : HolderSheetType
    data object Detail : HolderSheetType
}