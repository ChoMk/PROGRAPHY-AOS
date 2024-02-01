package com.myeong.prography.holder.sheet

import model.Photo

/**
 * Created by MyeongKi.
 */
sealed interface VisibleSheetEvent {
    data object HideSheet : VisibleSheetEvent
    data class ShowDetailSheet(val photo: Photo) : VisibleSheetEvent
}