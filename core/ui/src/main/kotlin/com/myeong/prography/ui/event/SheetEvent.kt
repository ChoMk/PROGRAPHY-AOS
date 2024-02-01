package com.myeong.prography.ui.event

/**
 * Created by MyeongKi.
 */
sealed interface SheetEvent {
    data object HideSheet : SheetEvent
    data class ShowDetailSheet(val photoId: String) : SheetEvent
}