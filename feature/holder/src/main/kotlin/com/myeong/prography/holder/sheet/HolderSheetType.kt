package com.myeong.prography.holder.sheet

/**
 * Created by MyeongKi.
 */
sealed interface HolderSheetType {
    data object None : HolderSheetType
    data class Detail(val photoId: String) : HolderSheetType
}