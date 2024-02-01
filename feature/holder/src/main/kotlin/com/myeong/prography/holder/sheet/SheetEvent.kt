package com.myeong.prography.holder.sheet

/**
 * Created by MyeongKi.
 */
sealed interface SheetEvent {
    data object HideSheet : SheetEvent
    data object ShowDetailSheet : SheetEvent
}